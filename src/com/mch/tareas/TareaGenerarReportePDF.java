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
		ruta = ruta+"reportes/facturaSanRafael/facturaSanRafael.jasper";
		System.out.println(ruta);
		try {
			byte[] bytes =  JasperRunManager.runReportToPdf(ruta, new HashMap<String, Object>(), connection);
			
			InputStream in = new ByteArrayInputStream(bytes);
			UtilMCH.escribirArchivoTemporalDesdeInputStream("prueba.pdf", in, "reportes", true);

		
		} catch (Exception e) {
			throw new RuntimeException("It's not possible to generate the pdf report.", e);
		} finally {
			if (connection != null) {
				try { connection.close(); } catch (Exception e) {}
			}
		}
	}
}
