package com.mch.actividades;

import java.io.IOException;
import java.sql.SQLException;

import net.lingala.zip4j.exception.ZipException;
import net.sf.jasperreports.engine.JRException;

import com.mch.excepciones.ExcepcionMch;
import com.mch.tareas.TareaGeneradorZip;
import com.mch.tareas.TareaGenerarReportePDF;

/**
 * @author Camilo
 * 12/09/2016
 */
public class ActividadGenerarReportesZip {

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
	public String generarReportesZip(String nombreReporte, String pass) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException, IOException, ZipException, InterruptedException{
		long t = System.currentTimeMillis();
		TareaGenerarReportePDF generadorPDF = new TareaGenerarReportePDF();
		TareaGeneradorZip generadorZip = new TareaGeneradorZip();
		String r = null, retorno;
		
		try{
			r = generadorPDF.generarPDF(nombreReporte, t);
			retorno = generadorZip.crearZipPorRuta1(r, nombreReporte, t, pass);
		}finally{
			generadorPDF = null;
			generadorZip = null;
			r = null;
		}
		return retorno;
	}
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, ExcepcionMch, JRException, ZipException, InterruptedException {
		String r = new ActividadGenerarReportesZip().generarReportesZip("facturaSanRafael","sanrafael");
		System.out.println(r);
	}
}
