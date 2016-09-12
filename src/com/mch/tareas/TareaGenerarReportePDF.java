package com.mch.tareas;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import com.mch.configuraciones.PoolInstanciasConexion;
import com.mch.excepciones.ExcepcionMch;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 09/09/2016
 */
public class TareaGenerarReportePDF {

	public String generarPDF(String nombreReporte) throws ClassNotFoundException, SQLException, ExcepcionMch, JRException{
		Connection connection = PoolInstanciasConexion.getInstancia().getConexionLibre("SanRafael").getCon();
		String ruta = UtilMCH.getRutaProyecto().replace("bin/", "");
		ruta = ruta+"reportes/"+nombreReporte+"/"+nombreReporte+".jasper";
		File file = new File(ruta);
		if(file.isFile() == false){
			throw new ExcepcionMch("No se encontro el reporte "+nombreReporte+", recuerde que el nombre de la carpeta debe ser igual al nombre del reporte.");
		}
		byte[] bytes =  JasperRunManager.runReportToPdf(ruta, new HashMap<String, Object>(), connection);
		
		InputStream in = new ByteArrayInputStream(bytes);
		UtilMCH.escribirArchivoTemporalDesdeInputStream("prueba.pdf", in, "reportes", true);
		
		return null;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JRException, ExcepcionMch {
		Connection connection = PoolInstanciasConexion.getInstancia().getConexionLibre("SanRafael").getCon();
		String ruta = UtilMCH.getRutaProyecto().replace("bin/", "");
		ruta = ruta+"reportes/Factura_San_Rafael.jasper";
		System.out.println(ruta);
		try {
			byte[] bytes =  JasperRunManager.runReportToPdf(ruta, new HashMap<String, Object>(), connection);
			
			InputStream in = new ByteArrayInputStream(bytes);
			UtilMCH.escribirArchivoTemporalDesdeInputStream("prueba.pdf", in, "reportes", true);
//		
//			String reportName = "reportes/Factura_San_Rafael";
//			Map<String, Object> parameters = new HashMap<String, Object>();
//
//			// compiles jrxml
////			JasperCompileManager.compileReport(new FileInputStream(new File("")));
//			// fills compiled report with parameters and a connection
//			JasperPrint print = JasperFillManager.fillReport(ruta, parameters, connection);
//			// exports report to pdf
//			JRExporter exporter = new JRPdfExporter();
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(reportName + ".pdf")); // your output goes here
//			
//			exporter.exportReport();
//			String print = JasperFillManager.fillReportToFile(ruta, null,
//					  connection);
//			
//			JRExporter exporter = new JRPdfExporter();
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("prueba" + ".pdf")); // your output goes here
		} catch (Exception e) {
			throw new RuntimeException("It's not possible to generate the pdf report.", e);
		} finally {
			// it's your responsibility to close the connection, don't forget it!
			if (connection != null) {
				try { connection.close(); } catch (Exception e) {}
			}
		}
	}
}
