package com.mch.actividades;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.mail.MessagingException;

import net.lingala.zip4j.exception.ZipException;
import net.sf.jasperreports.engine.JRException;

import org.json.JSONException;

import com.mch.bean.ArchivoBean;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadGenerarReporte;
import com.mch.propiedades.servicios.PropiedadServicioReporteHTML;
import com.mch.tareas.TareaEnviarArchivoRest;
import com.mch.tareas.TareaGeneradorZip;
import com.mch.tareas.TareaGenerarReportePDF;
import com.mch.tareas.TareaPeticion;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 12/09/2016
 */
public class ActividadGenerarReportes  extends TareaEnviarArchivoRest{

	/**
	 * Metodod que genera un reporte en PDF
	 * y lo agrega a un Zip y retorna la ruta
	 * en donde escribio el archivo.
	 * @param nombreReporte
	 * @return String con al ruta del zip
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ExcepcionMch
	 * @throws JRException
	 * @throws IOException
	 * @throws ZipException
	 * @throws InterruptedException
	 */
	public String generarReportesZip(String nombreReporte, String pass, Map<String, Object> p, String DB) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException, IOException, ZipException, InterruptedException{
		long t = System.currentTimeMillis();
		TareaGenerarReportePDF generadorPDF = new TareaGenerarReportePDF();
		TareaGeneradorZip generadorZip = new TareaGeneradorZip();
		String r = null, retorno;

		try{
			r = generadorPDF.generarPDF(nombreReporte, t, p, DB);
			retorno = generadorZip.crearZipPorRuta1(r, nombreReporte, t, pass);
		}finally{
			generadorPDF = null;
			generadorZip = null;
			r = null;
		}
		return retorno;
	}

	/**
	 * Metodo que genera un reporte en PDF con contraseña
	 * @param nombreReporte
	 * @param pass
	 * @param p
	 * @return String con la ruta en donde escribió el reporte
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ExcepcionMch
	 * @throws JRException
	 * @throws IOException
	 */
	//	public String generarReporte(String nombreReporte, String pass, Map<String, Object> p, String DB) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException, IOException{
	//		TareaGenerarReportePDF generadorPDF = new TareaGenerarReportePDF();
	//		long t = System.currentTimeMillis();
	//		String r = null;
	//		try{
	//			r = generadorPDF.generarPDF(nombreReporte, t, p, DB);
	//		}finally{
	//			generadorPDF = null;
	//		}
	//		return r;
	//	}

	/**
	 * 
	 * @param nombreReporte
	 * @param pass
	 * @param p
	 * @param DB
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ExcepcionMch
	 * @throws JRException
	 * @throws IOException
	 */
	public String generarReporte(String nombreReporte, String pass, Map<String, Object> p, String DB) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException, IOException{
		TareaGenerarReportePDF generadorPDF = new TareaGenerarReportePDF();
		long t = System.currentTimeMillis();
		String r = null;
		try{
			r = generadorPDF.generarPDF(nombreReporte, t, p, pass, DB);
		}finally{
			generadorPDF = null;
		}
		return r;
	}


	/**
	 * 
	 * @param prop
	 * @return
	 * @throws ExcepcionMch
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 * @throws IOException
	 */
	public String generarReporteHTML(PropiedadServicioReporteHTML prop) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		if((prop.getConsulta()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto consulta");
		if((prop.getDataBase()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto dataBase");
		TareaPeticion tarea = new TareaPeticion();
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(prop);
		return tarea.POST(generarRutaPeticion("servicioReporteHTML", p)); 
	}

	public Object generarReporteExcelXlsx(File archivo, PropiedadGenerarReporte prop) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException, MessagingException{

		if((prop.getNegocio()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto negocio");
		if((prop.getDataBase()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto dataBase");
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(prop);
		
		String URL = generarRutaPeticion("servicioGenerarReporteXlsx",p);
System.out.println(URL);
		return enviarArchivo2(URL, new ArchivoBean(archivo));
	}


public static void main(String[] args) {
	try{
	Object e = new ActividadGenerarReportes().generarReporteExcelXlsx(new File("E:\\davivienda.xls"), new PropiedadGenerarReporte("SanRafael", "SanRafael"));
	System.out.println(e);
	}catch(Exception e){
		e.printStackTrace();
	}
}
	//	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, ExcepcionMch, JRException, ZipException, InterruptedException {
	//		Map<String, Object> p = new HashMap<String, Object> ();
	//		p.put("rutaImagen", UtilMCH.getRutaProyecto().replace("bin", "imagenes"));
	//		String r = new ActividadGenerarReportes().generarReportesZip("facturaSanRafael","sanrafael", p, "SanRafael");
	//		System.out.println(r);
	//	}
}
