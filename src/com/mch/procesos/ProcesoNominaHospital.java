package com.mch.procesos;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import net.lingala.zip4j.exception.ZipException;
import net.sf.jasperreports.engine.JRException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mch.actividades.ActividadCargarArchivo;
import com.mch.actividades.ActividadEnviarCorreo;
import com.mch.actividades.ActividadImportarTrabajadoresNuevos;
import com.mch.actividades.ActividadLeerCorreo;
import com.mch.bean.ArchivoBean;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioCargarArchivo;
import com.mch.propiedades.servicios.PropiedadServicioEnviarCorreo;
import com.mch.propiedades.servicios.PropiedadServicioInsertarLog;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 28/11/2016
 */
public class ProcesoNominaHospital  implements Job{

	//---------------- <CONFIGURACIONES INICIALES> -------------------//

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *Negocio con el cual se ejecuta todo el proceso, este 
	 *es el que se busca en el archivo de propiedades para
	 *determinar atributos.
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	private static String NEGOCIO = "NominaHospital";

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *Nombre del negocio que hay que llamar en caso de 
	 *ocurrir un error.
	 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	private static String SOPORTE = "SoporteMCH";


	//----------------------------------------------------------------//	


	//------------ <CONFIGURACIONES LLAMADO SERVICIOS> ---------------//

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * Actividades usadas por el proceso.
	 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	private ActividadLeerCorreo actividadLeerCorreo = new ActividadLeerCorreo();
	private ActividadCargarArchivo actividadCargarArchivo = new ActividadCargarArchivo();
	private ActividadEnviarCorreo actividadEnviarCorreo = new ActividadEnviarCorreo();
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * Propiedades necesarias para invocar los diferentes 
	 * servicio.
	 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	private PropiedadServicioEnviarCorreo propiedadServicioEnviarCorreo = new PropiedadServicioEnviarCorreo();
	//----------------------------------------------------------------//	

	private ArchivoBean archivoBean = null;
	private String rutaArchivosTemporales = null;;


	/**
	 * Metodo principal del proceso.
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String emailActual = null, asuntoActual = null;
		try {
			insertarLog(NEGOCIO, "Inició el proceso de Nomina Hospital Tomas Uribe", "Hospital Tomas");

			JSONObject obj = actividadLeerCorreo.leerCorreo(NEGOCIO);
			System.out.println(obj);
			if(obj.isNull("info")){
				throw new JobExecutionException(obj.toString());
			}
			JSONArray array = obj.getJSONArray("info");
			System.out.println("1");
			for(int a = 0 ; a < array.length(); a++){
				try{
					emailActual = array.getJSONObject(a).getString("remitente");
					asuntoActual = array.getJSONObject(a).getString("asunto");
					rutaArchivosTemporales = array.getJSONObject(a).getString("ruta");
					System.out.println(new File(rutaArchivosTemporales).listFiles()[0]);
					List<Object[]> informacion = actividadCargarArchivo.leerArchivoExcel(new File(rutaArchivosTemporales).listFiles()[0],  new PropiedadServicioCargarArchivo(UtilMCH.getDataBaseName(NEGOCIO), "TEMP", NEGOCIO, false));
					System.out.println("2");		
					if( informacion.size() == 0){
						enviarCorreo(null, generarTablaMensaje("No se pudo leer el archivo: "+new File(rutaArchivosTemporales).listFiles()[0].getName()), array.getJSONObject(a), NEGOCIO);
						System.out.println("3");
						continue;
					}
					try{
						System.out.println("4");
						new ActividadImportarTrabajadoresNuevos(informacion, new File(rutaArchivosTemporales).listFiles()[0].getName(), UtilMCH.getDataBaseName(NEGOCIO), NEGOCIO);
						System.out.println("5");
						enviarCorreo(null, "Proceso realizado con exíto",  array.getJSONObject(a), NEGOCIO);
						System.out.println("6");
					}catch(ExcepcionMch e){
						System.out.println("7");
						e.printStackTrace();
						enviarCorreo(null, generarTablaMensaje(e.getMessage().split(";")), array.getJSONObject(a), NEGOCIO);
					}
					System.out.println("8");
				}catch(Exception e){
					enviarCorreo("", "Ha ocurrido un error en el proceso de Hospital Tomas: <br> <br> "+e.getMessage(), new JSONObject().put("remitente", UtilMCH.getEmailSoporte(SOPORTE)).put("asunto", "¡ERROR! Hospital Tomas"), "SoporteMCH");
					enviarCorreo("", "Ha ocurrido un error interno, estamos trabajando para resolverlo, pronto nos comunicaremos con usted.", new JSONObject().put("remitente", emailActual).put("asunto", asuntoActual), NEGOCIO);
					insertarLog(NEGOCIO, "Termina proceso con errores: "+e.getMessage(), "Hospital Tomas");
					e.printStackTrace();
				}
			}
			if(rutaArchivosTemporales != null){
				if(!rutaArchivosTemporales.toLowerCase().replace(" ", "").replace("null", "").equals("")){	
					rutaArchivosTemporales = rutaArchivosTemporales.substring(0, rutaArchivosTemporales.lastIndexOf("\\")).replace(":", "$");
					//String ruta = UtilMCH.getRutaProyecto().replace("bin/", "temporales");

				}
			}
			insertarLog(NEGOCIO, "Termina proceso sin errores", "Hospital Tomas");
		} catch (Exception e) {
			try {
				enviarCorreo("", "Ha ocurrido un error en el proceso de Nomina Hospital Tomas Uribe: <br> <br> "+e.getMessage(), new JSONObject().put("remitente", UtilMCH.getEmailSoporte(SOPORTE)).put("asunto", "¡ERROR! Nomina Hospital Tomas"), "SoporteMCH");
				enviarCorreo("", "Ha ocurrido un error interno, estamos trabajando para resolverlo, pronto nos comunicaremos con usted.", new JSONObject().put("remitente", emailActual).put("asunto", asuntoActual), NEGOCIO);
				insertarLog(NEGOCIO, "Termina proceso con errores: "+e.getMessage(), "Hospital Tomas");
				//invocarProcedimiento("", NEGOCIO, PROCEDIMIENTO_ELIMINAR_TEMPORAL);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new JobExecutionException(e1);
			}
			e.printStackTrace();
		}finally{
			actividadLeerCorreo = null;
			actividadCargarArchivo = null;
			actividadEnviarCorreo = null;
			propiedadServicioEnviarCorreo = null;
			archivoBean = null;

			eliminarCarpetasTemporales(rutaArchivosTemporales);
		}

	}



	/**
	 * Metodo que invoca un servicio que
	 * envia correos electrónicos. 
	 * @param rutaZip
	 * @param mensaje
	 * @param obj
	 * @return String
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 * @throws ExcepcionMch
	 * @throws IOException
	 * @throws MessagingException
	 */
	private String enviarCorreo(String rutaZip,String mensaje, JSONObject obj, String negocio) throws IllegalArgumentException, IllegalAccessException, JSONException, ExcepcionMch, IOException, MessagingException{
//		System.out.println(obj);
		if(obj.isNull("remitente") || obj.isNull("asunto")){
			return null;
		}
		insertarLog(negocio, "Envia correo", "Hospital Tomas");
		rutaZip = (rutaZip+"").replace("null", "").replace(" ", "");
		if(!rutaZip.equals("")){
			List<ArchivoBean> archivos = new ArrayList<ArchivoBean>();
			archivoBean = new ArchivoBean(new File(rutaZip));
			archivos.add(archivoBean);
			propiedadServicioEnviarCorreo.setArchivos(archivos);
		}else if(rutaZip.equals("")){
			propiedadServicioEnviarCorreo.setArchivos(null);
		}
		propiedadServicioEnviarCorreo.setAsunto(obj.getString("asunto"));
		propiedadServicioEnviarCorreo.setDestinatario(obj.getString("remitente"));
		propiedadServicioEnviarCorreo.setMensaje(mensaje);
		propiedadServicioEnviarCorreo.setNegocio(negocio);
		return actividadEnviarCorreo.enviarEmail(propiedadServicioEnviarCorreo);
	}

	/**
	 * Metodo que crea una tabla HTML
	 * con los mensaje que llegan en
	 * un arreglo, puede ser un arreglo
	 * de String o puede ser un objeto
	 * de tipo JSONArray
	 * @param inconsistencias
	 * @return String
	 */
	private String generarTablaMensaje(Object inconsistencias){
		StringBuilder mensaje = new StringBuilder("<p style='color: red;font-weight: bold;'>Terminó el proceso, no se importó información porque se han presentado las siguientes inconsistencias:</p> <br><br>");
		mensaje.append("<table border='1'>");
		mensaje.append("<thead><tr><th>Mensaje</th></td></thead>");
		mensaje.append("<tbody>");
		if(inconsistencias instanceof String[]){
			for(String i : (String[])inconsistencias){
				mensaje.append("<tr><td>"+i.trim()+"</td></tr>");
			}
		}else if(inconsistencias instanceof JSONArray){
			JSONArray j = (JSONArray)inconsistencias;
			for(int a = 0 ; a < j.length() ; a++){
				mensaje.append("<tr><td>"+j.getString(a).trim()+"</td></tr>");
			}
		}else if(inconsistencias instanceof String){
			mensaje.append("<tr><td>"+inconsistencias+"</td></tr>");
		}
		mensaje.append("</tbody>");
		mensaje.append("</table>");
		return mensaje.toString();
	}

	/**
	 * Metodo que elimina todos los archivos temporales.
	 * @param ruta
	 */
	private void eliminarCarpetasTemporales(String ruta){
		try{
			File f = new File(ruta);
			for(File a : f.listFiles()){
				if(a.isDirectory())
					eliminarCarpetasTemporales(a.getAbsolutePath());
				else if(a.isFile() && !a.getName().contains(".txt"))
					a.delete();
			}
			if(f.isDirectory() && !f.getName().equals("temporales"))
				f.delete();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void insertarLog(String negocio, String mensaje, String proceso) throws JSONException, IllegalArgumentException, IllegalAccessException, IOException, ExcepcionMch{
		PropiedadServicioInsertarLog propiedadServicioInsertarLog = new PropiedadServicioInsertarLog();
		propiedadServicioInsertarLog.setDataBase(UtilMCH.getDataBaseName(negocio));
		propiedadServicioInsertarLog.setMensaje(mensaje);
		propiedadServicioInsertarLog.setProceso(proceso);
		propiedadServicioInsertarLog.setNegocio(negocio);
		//JSONObject a = new JSONObject(ActividadInsertar.log(propiedadServicioInsertarLog));
		//		System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,, LOG ,,,,,,,,,,,,,,,,,,,,,,,");
		//		System.out.println(a);
		//		if(!a.isNull("error"))
		//			throw new ExcepcionMch(a.toString());
	}

	public static void main(String[] args) throws JSONException, IOException, MessagingException, IllegalArgumentException, IllegalAccessException, ExcepcionMch, ClassNotFoundException, SQLException, JRException, ZipException, InterruptedException, JobExecutionException{
		//		String a = "\\192.168.2.5\\C$\\TOMCAT_7\\webapps\\ServiciosMCH\\temporales\\20160913-1473802099989";
		//		a = a.substring(0, a.lastIndexOf("\\"));
		//		System.out.println(a);
		ProcesoNominaHospital p = new ProcesoNominaHospital();
		//		p.generarReporte("facturaMch", "FacturacionMch");
		p.execute(null);
		//		JSONObject o = new JSONObject();
		//		o.put("asunto", "hola");
		//		System.out.println(o);
		//		File f = new File("E:/RepositorioGITPortal/ProcesosMCH/temporales/temporal_1473782431014/facturaSanRafael_1473782431014.zip");
		//		String a = f.getAbsolutePath();
		//		a = a.substring(0, a.lastIndexOf("\\"));
		//		System.out.println(a);
	}

}
