package com.mch.actividades;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.lingala.zip4j.exception.ZipException;
import net.sf.jasperreports.engine.JRException;

import com.mch.excepciones.ExcepcionMch;
import com.mch.tareas.TareaGeneradorZip;
import com.mch.tareas.TareaGenerarReportePDF;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 12/09/2016
 */
public class ActividadGenerarReportes {

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
	public String generarReportesZip(String nombreReporte, String pass, Map<String, Object> p) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException, IOException, ZipException, InterruptedException{
		long t = System.currentTimeMillis();
		TareaGenerarReportePDF generadorPDF = new TareaGenerarReportePDF();
		TareaGeneradorZip generadorZip = new TareaGeneradorZip();
		String r = null, retorno;
		
		try{
			r = generadorPDF.generarPDF(nombreReporte, t, p);
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
	public String generarReporte(String nombreReporte, String pass, Map<String, Object> p) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException, IOException{
		TareaGenerarReportePDF generadorPDF = new TareaGenerarReportePDF();
		long t = System.currentTimeMillis();
		String r = null;
		try{
			r = generadorPDF.generarPDF(nombreReporte, t, p);
		}finally{
			generadorPDF = null;
		}
		return r;
	}
	
	
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
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, ExcepcionMch, JRException, ZipException, InterruptedException {
		Map<String, Object> p = new HashMap<String, Object> ();
		p.put("rutaImagen", UtilMCH.getRutaProyecto().replace("bin", "imagenes"));
		String r = new ActividadGenerarReportes().generarReportesZip("facturaSanRafael","sanrafael", p);
		System.out.println(r);
	}
}
