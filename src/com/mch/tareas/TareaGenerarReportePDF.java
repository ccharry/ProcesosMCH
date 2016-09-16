package com.mch.tareas;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import com.mch.configuraciones.Conexion;
import com.mch.configuraciones.PoolInstanciasConexion;
import com.mch.excepciones.ExcepcionMch;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 09/09/2016
 */
public class TareaGenerarReportePDF {

	private Conexion con = null;


	/**
	 * Metododo que genera un reporte en PDF y lo
	 * escribe en una ruta.
	 * @param nombreReporte
	 * @param time
	 * @return String con la ruta en donde escribio el reporte
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ExcepcionMch
	 * @throws JRException
	 * @throws IOException
	 */
	public String generarPDF(String nombreReporte, long time, Map<String, Object>parametros) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException, IOException{
		String ruta = UtilMCH.getRutaProyecto().replace("bin/", "");
		try{
			ruta = ruta+"reportes/"+nombreReporte+"/"+nombreReporte+".jasper";
			File file = new File(ruta);
			if(file.isFile() == false){
				throw new ExcepcionMch("No se encontro el reporte "+nombreReporte+", recuerde que el nombre de la carpeta debe ser igual al nombre del reporte.");
			}
			con = PoolInstanciasConexion.getInstancia().getConexionLibre("SanRafael");
			byte[] bytes =  JasperRunManager.runReportToPdf(ruta, parametros, con.getCon());
			InputStream in = new ByteArrayInputStream(bytes);
			ruta = UtilMCH.escribirArchivoTemporalDesdeInputStream(nombreReporte+".pdf", in, "temporales", true, time);
			ruta =  ruta.replace(nombreReporte+".pdf","");
		}finally{
			con.setLibre(true);
		}
		return ruta;
	}
	
	
	public String generarPDF(String nombreReporte, long time, Map<String, Object>parametros, String pass, String DB) throws ClassNotFoundException, JRException, SQLException, ExcepcionMch{
		long t = System.currentTimeMillis();

		String ruta = UtilMCH.getRutaProyecto().replace("bin/", ""),
				ruta2 = ruta+"temporales/temporal_"+t+"/";
		
		new File(ruta2).mkdir();
		
		JasperReport jr=JasperCompileManager.compileReport(ruta+"reportes/"+nombreReporte+"/"+nombreReporte+".jrxml");
		JasperPrint jp=JasperFillManager.fillReport(jr,parametros,PoolInstanciasConexion.getInstancia().getConexionLibre(DB).getCon());
		JRPdfExporter exporter = new JRPdfExporter();       
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new File(ruta2+nombreReporte+".pdf"));
		exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, pass);
		exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, pass);
		exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
		exporter.exportReport();
		return ruta2+"/"+nombreReporte+".pdf";
	}
	

	public static void main(String[] args) throws ClassNotFoundException, SQLException, JRException, ExcepcionMch, IOException {
//		System.out.println(UtilMCH.getRutaProyecto());
//		String ruta = UtilMCH.getRutaProyecto().replace("bin/", "");
//		System.out.println(ruta+"temporales");
		
		Map<String, Object> p = new HashMap<String, Object> ();
		p.put("rutaImagen", UtilMCH.getRutaProyecto().replace("bin", "imagenes"));
		String a = new TareaGenerarReportePDF().generarPDF("facturaSanRafael", System.currentTimeMillis(), p, "123", "sanRafael");
		System.out.println(a);
//		
//		
//		JasperReport jr=JasperCompileManager.compileReport(ruta+"reportes/facturaSanRafael/facturaSanRafael.jrxml");
//		JasperPrint jp=JasperFillManager.fillReport(jr,p,PoolInstanciasConexion.getInstancia().getConexionLibre("SanRafael").getCon());
//		JRPdfExporter exporter = new JRPdfExporter();       
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//		exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new File(ruta+"reportes/facturaSanRafael/facturaSanRafael.pdf"));
//		exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, "hi");
//		exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, "hi");
//		exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
//		exporter.exportReport();
//		String a = new TareaGenerarReportePDF().generarPDF("facturaSanRafael", System.currentTimeMillis(), p);
//		System.out.println(a);
	}
}
