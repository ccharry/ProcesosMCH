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
import com.mch.actividades.ActividadInvocarProcedimiento;
import com.mch.actividades.ActividadLeerCorreo;
import com.mch.bean.ArchivoBean;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioCargarArchivo;
import com.mch.propiedades.servicios.PropiedadServicioEnviarCorreo;
import com.mch.propiedades.servicios.PropiedadServicioInvocarProcedimiento;
import com.mch.utilidades.UtilMCH;

public class ProcesoSanRafael implements Job{

	private static final String NEGOCIO = "SanRafael";
	private static final String TABLA = "FACTURAS_TEMP";
	private static final String NOMBRE_REPORTE = "facturaSanRafael";
	private static final String PASSWORD_ZIP = "sanrafael";

	private ActividadLeerCorreo actividadLeerCorreo = new ActividadLeerCorreo();
	private ActividadCargarArchivo actividadCargarArchivo = new ActividadCargarArchivo();
	private ActividadInvocarProcedimiento actividadInvocarProcedimiento = new ActividadInvocarProcedimiento();
	private ActividadGenerarReportesZip actividadGenerarReportesZip = new ActividadGenerarReportesZip();
	private ActividadEnviarCorreo actividadEnviarCorreo = new ActividadEnviarCorreo();
	private PropiedadServicioCargarArchivo propServicioCargarArchivo = new PropiedadServicioCargarArchivo();
	private PropiedadServicioInvocarProcedimiento propiedadServicioInvocarProcedimiento = new PropiedadServicioInvocarProcedimiento();
	private PropiedadServicioEnviarCorreo propiedadServicioEnviarCorreo = new PropiedadServicioEnviarCorreo();
	private ArchivoBean archivoBean = null;


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			JSONObject p = new ActividadLeerCorreo().leerCorreo("SanRafael");
			System.out.println(p);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public JSONObject leerCorreo(String negocio) throws JSONException, IOException {
		return actividadLeerCorreo.leerCorreo(negocio);
	}

	public void cargarArchivosDB(JSONObject ruta, String negocio, String tabla) throws JSONException, IOException, MessagingException, IllegalArgumentException, IllegalAccessException, ExcepcionMch{
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Se modifican los parametros que espera el servicio
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		propServicioCargarArchivo.setNegocio(negocio);
		propServicioCargarArchivo.setTabla(tabla);
		propServicioCargarArchivo.setDataBase(UtilMCH.getDataBaseName(negocio));

		String b = actividadCargarArchivo.cargarArchivosABaseDatos(new File(ruta.getString("ruta")).listFiles(), propServicioCargarArchivo);
		System.out.println(b);
	}

	public String  invocarProcedimientoValidaciones(String email, String negocio) throws JSONException, IllegalArgumentException, IllegalAccessException, IOException, ExcepcionMch{
		propiedadServicioInvocarProcedimiento.setParametros(email);
		propiedadServicioInvocarProcedimiento.setProcedimiento("procValidacionesFacturas");
		propiedadServicioInvocarProcedimiento.setDataBase(UtilMCH.getDataBaseName(negocio));
		propiedadServicioInvocarProcedimiento.setNegocio(negocio);
		return actividadInvocarProcedimiento.invocarProcedimiento(propiedadServicioInvocarProcedimiento);
	}

	public String generarReporteZip(String nombreReporte, String pass) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException, IOException, ZipException, InterruptedException{
		Map<String, Object> p = new HashMap<String, Object> ();
		p.put("rutaImagen", UtilMCH.getRutaProyecto().replace("bin", "imagenes"));
		return actividadGenerarReportesZip.generarReportesZip(nombreReporte,pass, p);
	}

	public String enviarCorreo(String rutaZip, JSONObject obj) throws IllegalArgumentException, IllegalAccessException, JSONException, ExcepcionMch, IOException, MessagingException{
		List<ArchivoBean> archivos = new ArrayList<ArchivoBean>();
		archivoBean = new ArchivoBean(new File(rutaZip));
		archivos.add(archivoBean);
		propiedadServicioEnviarCorreo.setAsunto(obj.getString("asunto"));
		propiedadServicioEnviarCorreo.setDestinatario(obj.getString("destinatario"));
		propiedadServicioEnviarCorreo.setMensaje("Se adjuntan reportes");
		propiedadServicioEnviarCorreo.setNegocio(NEGOCIO);
		propiedadServicioEnviarCorreo.setArchivos(archivos);
		return actividadEnviarCorreo.enviarEmail(propiedadServicioEnviarCorreo);
	}


	public static void main(String[] args) throws JSONException, IOException, MessagingException, IllegalArgumentException, IllegalAccessException, ExcepcionMch, ClassNotFoundException, SQLException, JRException, ZipException, InterruptedException{
		ProcesoSanRafael p = new ProcesoSanRafael();
		JSONObject obj = p.leerCorreo(NEGOCIO);
		JSONArray array = obj.getJSONArray("info");
		for(int a = 0 ; a < array.length(); a++){
			p.cargarArchivosDB(array.getJSONObject(a), NEGOCIO, TABLA);
			String r = p.invocarProcedimientoValidaciones(array.getJSONObject(a).getString("destinatario"), NEGOCIO).trim().toLowerCase(), rutaZip = null;
			if(r.equals("ok")){
				rutaZip = p.generarReporteZip(NOMBRE_REPORTE, PASSWORD_ZIP);
				System.out.println(rutaZip);
				p.enviarCorreo(rutaZip, array.getJSONObject(a));
			}
			System.out.println(r);
		}
	}
}
