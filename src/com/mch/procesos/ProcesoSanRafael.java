package com.mch.procesos;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mch.actividades.ActividadGenerarReportesZip;
import com.mch.actividades.ActividadInsertar;
import com.mch.actividades.ActividadInvocarProcedimiento;
import com.mch.actividades.ActividadLeerCorreo;
import com.mch.bean.ArchivoBean;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioCargarArchivo;
import com.mch.propiedades.servicios.PropiedadServicioEnviarCorreo;
import com.mch.propiedades.servicios.PropiedadServicioInsertarLog;
import com.mch.propiedades.servicios.PropiedadServicioInvocarProcedimiento;
import com.mch.utilidades.UtilMCH;
/**
 * @author Camilo
 * 13/09/2016
 */
public class ProcesoSanRafael implements Job{

	private static String NEGOCIO = "san";
	private static String TABLA = "FACTURAS_TEMP";
	private static String NOMBRE_REPORTE = "facturaSanRafael";
	private static String PASSWORD_ZIP = "sanrafael";
	private static String PROCEDIMIENTO_VALIDACIONES = "procValidacionesFacturas";
	private static String PROCEDIMIENTO_MOVER_A_HISTORICO = "procMoverAHistorico";
	private static String PROCEDIMIENTO_ELIMINAR_TEMPORAL = "procEliminarTemporal";
	private static String SOPORTE = "SoporteMCH";

	private ActividadLeerCorreo actividadLeerCorreo = new ActividadLeerCorreo();
	private ActividadCargarArchivo actividadCargarArchivo = new ActividadCargarArchivo();
	private ActividadInvocarProcedimiento actividadInvocarProcedimiento = new ActividadInvocarProcedimiento();
	private ActividadGenerarReportesZip actividadGenerarReportesZip = new ActividadGenerarReportesZip();
	private ActividadEnviarCorreo actividadEnviarCorreo = new ActividadEnviarCorreo();
	private PropiedadServicioCargarArchivo propServicioCargarArchivo = new PropiedadServicioCargarArchivo();
	private PropiedadServicioInvocarProcedimiento propiedadServicioInvocarProcedimiento = new PropiedadServicioInvocarProcedimiento();
	private PropiedadServicioEnviarCorreo propiedadServicioEnviarCorreo = new PropiedadServicioEnviarCorreo();
	private ArchivoBean archivoBean = null;

	private String mensaje = null, rutaArchivosTemporales = null;;
	private JSONObject objTemp = null;


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String emailActual = null, asuntoActual = null;
		try {
			insertarLog(NEGOCIO, "Inici� el proceso de San Rafael", "San Rafael");

			JSONObject obj = actividadLeerCorreo.leerCorreo(NEGOCIO);
			if(obj.isNull("info")){
				throw new JobExecutionException(obj.toString());
			}
			JSONArray array = obj.getJSONArray("info");
			for(int a = 0 ; a < array.length(); a++){
				emailActual = array.getJSONObject(a).getString("destinatario");
				asuntoActual = array.getJSONObject(a).getString("asunto");

				mensaje = cargarArchivosDB(array.getJSONObject(a), NEGOCIO, TABLA);
				rutaArchivosTemporales = array.getJSONObject(a).getString("ruta");
				objTemp = new JSONObject(mensaje);
				if( !objTemp.isNull("errores")){
					if(objTemp.getJSONArray("errores").length() > 0){
						enviarCorreo(null, generarTablaMensaje(objTemp.getJSONArray("errores")), array.getJSONObject(a), NEGOCIO);
						invocarProcedimiento("", NEGOCIO, PROCEDIMIENTO_ELIMINAR_TEMPORAL);
						continue;
					}
				}

				String r = invocarProcedimiento(array.getJSONObject(a).getString("destinatario"), NEGOCIO, PROCEDIMIENTO_VALIDACIONES).trim().toLowerCase(), rutaZip = null;
				if(r.equals("ok")){
					rutaZip = generarReporteZip(NOMBRE_REPORTE, PASSWORD_ZIP);
					r = invocarProcedimiento("", NEGOCIO, PROCEDIMIENTO_MOVER_A_HISTORICO).trim().toLowerCase();
					enviarCorreo(rutaZip,"Proceso realizado con ex�to, se adjunta archivo ZIP con el reporte correspondiente.",  array.getJSONObject(a), NEGOCIO);

				}else{
					enviarCorreo(null, generarTablaMensaje(r.split(";")), array.getJSONObject(a), NEGOCIO);
				}
			}
			if(rutaArchivosTemporales != null){
				if(!rutaArchivosTemporales.toLowerCase().replace(" ", "").replace("null", "").equals("")){	
					rutaArchivosTemporales = rutaArchivosTemporales.substring(0, rutaArchivosTemporales.lastIndexOf("\\")).replace(":", "$");
					//String ruta = UtilMCH.getRutaProyecto().replace("bin/", "temporales");

				}
			}
			insertarLog(NEGOCIO, "Termina proceso sin errores", "San Rafael");
		} catch (Exception e) {
			try {
				enviarCorreo("", "Ha ocurrido un error interno, estamos trabajando para resolverlo, pronto nos comunicaremos con usted.", new JSONObject().put("destinatario", emailActual).put("asunto", asuntoActual), NEGOCIO);
				enviarCorreo("", "Ha ocurrido un error en el proceso de San Rafael: <br> <br> "+e.getMessage(), new JSONObject().put("destinatario", UtilMCH.getEmailSoporte(SOPORTE)).put("asunto", "�ERROR! San Rafael"), "SoporteMCH");
				insertarLog(NEGOCIO, "Termina proceso con errores: "+e.getMessage(), "San Rafael");
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new JobExecutionException(e1);
			}
			e.printStackTrace();
		}finally{
			actividadLeerCorreo = null;
			actividadCargarArchivo = null;
			actividadInvocarProcedimiento= null;
			actividadGenerarReportesZip = null;
			actividadEnviarCorreo = null;
			propServicioCargarArchivo = null;
			propiedadServicioInvocarProcedimiento = null;
			propiedadServicioEnviarCorreo = null;
			archivoBean = null;

			eliminarCarpetasTemporales(rutaArchivosTemporales);
		}

	}


	/**
	 * Metodo que invoca un servicio que
	 * carga archivos excel a la DB, el 
	 * servicio valida la estructura, de 
	 * acuerdo a la tabla a donde se va a
	 * cargar la informaci�n.
	 * @param ruta
	 * @param negocio
	 * @param tabla
	 * @return String con el mensaje que retorna el servicio
	 * @throws JSONException
	 * @throws IOException
	 * @throws MessagingException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ExcepcionMch
	 */
	private String cargarArchivosDB(JSONObject ruta, String negocio, String tabla) throws JSONException, IOException, MessagingException, IllegalArgumentException, IllegalAccessException, ExcepcionMch{
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Se modifican los parametros que espera el servicio
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		propServicioCargarArchivo.setNegocio(negocio);
		propServicioCargarArchivo.setTabla(tabla);
		propServicioCargarArchivo.setDataBase(UtilMCH.getDataBaseName(negocio));
		return actividadCargarArchivo.cargarArchivosABaseDatos(new File(ruta.getString("ruta")).listFiles(), propServicioCargarArchivo);
	}

	/**
	 * Metodod que invoca un servicio
	 * que ejecuta un procedimiento 
	 * almacenado.
	 * @param parametros
	 * @param negocio
	 * @param procedimiento
	 * @return String con el mensaje que retorna el procedimiento
	 * @throws JSONException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws ExcepcionMch
	 */
	private String invocarProcedimiento(String parametros, String negocio, String procedimiento) throws JSONException, IllegalArgumentException, IllegalAccessException, IOException, ExcepcionMch{
		propiedadServicioInvocarProcedimiento.setParametros(parametros);
		propiedadServicioInvocarProcedimiento.setProcedimiento(procedimiento);
		propiedadServicioInvocarProcedimiento.setDataBase(UtilMCH.getDataBaseName(negocio));
		propiedadServicioInvocarProcedimiento.setNegocio(negocio);
		return actividadInvocarProcedimiento.invocarProcedimiento(propiedadServicioInvocarProcedimiento);
	}

	/**
	 * Metodo que crea un reporte en PDF
	 * y lo comprime en un archivo ZIP.
	 * @param nombreReporte
	 * @param pass
	 * @return String con la ruta en donde escribi� el ZIP
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ExcepcionMch
	 * @throws JRException
	 * @throws IOException
	 * @throws ZipException
	 * @throws InterruptedException
	 */
	private String generarReporteZip(String nombreReporte, String pass) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException, IOException, ZipException, InterruptedException{
		Map<String, Object> p = new HashMap<String, Object> ();
		p.put("rutaImagen", UtilMCH.getRutaProyecto().replace("bin", "imagenes"));
		return actividadGenerarReportesZip.generarReportesZip(nombreReporte,pass, p);
	}

	/**
	 * Metodo que invoca un servicio que
	 * envia correos electr�nicos. 
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
		insertarLog(negocio, "Envia correo", "San Rafael");
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
		propiedadServicioEnviarCorreo.setDestinatario(obj.getString("destinatario"));
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
		StringBuilder mensaje = new StringBuilder("<p style='color: red;font-weight: bold;'>Termin� el proceso, no se import� informaci�n porque se han presentado las siguientes inconsistencias:</p> <br><br>");
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
		JSONObject a = new JSONObject(ActividadInsertar.log(propiedadServicioInsertarLog));
		if(!a.isNull("error"))
			throw new ExcepcionMch(a.toString());
	}

	public static void main(String[] args) throws JSONException, IOException, MessagingException, IllegalArgumentException, IllegalAccessException, ExcepcionMch, ClassNotFoundException, SQLException, JRException, ZipException, InterruptedException, JobExecutionException{
		//		String a = "\\192.168.2.5\\C$\\TOMCAT_7\\webapps\\ServiciosMCH\\temporales\\20160913-1473802099989";
		//		a = a.substring(0, a.lastIndexOf("\\"));
		//		System.out.println(a);
		ProcesoSanRafael p = new ProcesoSanRafael();
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
