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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
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
	public String generarPDF(String nombreReporte, long time, Map<String, Object>parametros, String DB) throws ExcepcionMch {

		String ruta = UtilMCH.getRutaProyecto().replace("bin", "reportes");

		ruta = ruta+"/reportes/"+nombreReporte+"/"+nombreReporte+".jasper";

		try {
			File file = new File(ruta);
			System.out.println("-----------------------------------------------");
			System.out.println(file);

			if(file.isFile() == false){
				throw new ExcepcionMch("No se encontro el reporte "+nombreReporte+", recuerde que el nombre de la carpeta debe ser igual al nombre del reporte.");
			}
			con = PoolInstanciasConexion.getInstancia().getConexionLibre(DB);
			byte[] bytes;
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println(parametros+" ---- "+con.getCon());
			bytes = JasperRunManager.runReportToPdf(ruta, parametros, con.getCon());
			InputStream in = new ByteArrayInputStream(bytes);
			ruta = UtilMCH.escribirArchivoTemporalDesdeInputStream(nombreReporte+".pdf", in, "temporales", true, time);
			ruta =  ruta.replace(nombreReporte+".pdf","");
		} catch (JRException e) {
			e.printStackTrace();
			throw new ExcepcionMch("Ha ocurrido un error al momento de generar el reporte en PDF: "+e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ExcepcionMch("Ha ocurrido un error al momento de generar el reporte en PDF: "+e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionMch("Ha ocurrido un error al momento de generar el reporte en PDF: "+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExcepcionMch("Ha ocurrido un error al momento de generar el reporte en PDF: "+e.getMessage());
		}finally{
			if(con != null)
				con.setLibre(true);
		}
		return ruta;
	}


	public String generarPDF(String nombreReporte, long time, Map<String, Object>parametros, String pass, String DB) throws ExcepcionMch {
		long t = System.currentTimeMillis();

		String ruta = UtilMCH.getRutaProyecto().replace("bin", "reportes/"),
				ruta2 = ruta+"temporales/temporal_"+t+"/";
		try {

			new File(ruta2).mkdir();
			//			JasperReport jr=JasperCompileManager.compileReport(ruta+"reportes/"+nombreReporte+"/"+nombreReporte+".jrxml");
			//			JasperReport jr=JasperCompileManager.compileReport(ruta+"reportes/"+nombreReporte+"/"+nombreReporte+".jrxml");
			JasperPrint jp = JasperFillManager.fillReport(ruta+"reportes/"+nombreReporte+"/"+nombreReporte+".jasper",parametros,PoolInstanciasConexion.getInstancia().getConexionLibre(DB).getCon());;
			jp.setName("");
			JRPdfExporter exporter = new JRPdfExporter();       
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new File(ruta2+nombreReporte+".pdf"));
			if(!(pass+"").replace("null", "").equals("")){
				exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, pass);
				exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, pass);
			}
			exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
			exporter.exportReport();
		} catch (ClassNotFoundException e) {
			//			e.printStackTrace();
			throw new ExcepcionMch("Ha ocurrido un error al momento de generar el reporte en PDF: "+e.getMessage());
		} catch (JRException e) {
			//			e.printStackTrace();
			throw new ExcepcionMch("Ha ocurrido un error al momento de generar el reporte en PDF: "+e.getMessage());
		} catch (SQLException e) {
			//			e.printStackTrace();
			throw new ExcepcionMch("Ha ocurrido un error al momento de generar el reporte en PDF: "+e.getMessage());
		} catch (ExcepcionMch e) {
			//			e.printStackTrace();
			throw new ExcepcionMch("Ha ocurrido un error al momento de generar el reporte en PDF: "+e.getMessage());
		}
		return ruta2+"/"+nombreReporte+".pdf";
	}


	public static void main(String[] args) {
		//		System.out.println(UtilMCH.getRutaProyecto());
		//		String ruta = UtilMCH.getRutaProyecto().replace("bin/", "");
		//		System.out.println(ruta+"temporales");

		Map<String, Object> p = new HashMap<String, Object> ();
		p.put("rutaImagen", UtilMCH.getRutaProyecto().replace("bin", "imagenes"));
		System.out.println(p);
		String a;
		try {
			//tring nombreReporte, long time, Map<String, Object>parametros
			a = new TareaGenerarReportePDF().generarPDF("facturaSanRafael", 123, p, "sanRafael");
			System.out.println(a);
			System.out.println(new File(a).length());;
		} catch (ExcepcionMch e) {
			e.printStackTrace();
		}
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
