package com.mch.tareas;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
		ruta = ruta+"reportes/"+nombreReporte+"/"+nombreReporte+".jasper";
		File file = new File(ruta);
		if(file.isFile() == false){
			throw new ExcepcionMch("No se encontro el reporte "+nombreReporte+", recuerde que el nombre de la carpeta debe ser igual al nombre del reporte.");
		}
		Connection connection = PoolInstanciasConexion.getInstancia().getConexionLibre("SanRafael").getCon();
		byte[] bytes =  JasperRunManager.runReportToPdf(ruta, parametros, connection);
		InputStream in = new ByteArrayInputStream(bytes);
		ruta = UtilMCH.escribirArchivoTemporalDesdeInputStream(nombreReporte+".pdf", in, "temporales", true, time);
		ruta =  ruta.replace(nombreReporte+".pdf","");
		return ruta;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JRException, ExcepcionMch, IOException {
		Map<String, Object> p = new HashMap<String, Object> ();
		p.put("rutaImagen", UtilMCH.getRutaProyecto().replace("bin", "imagenes"));
		String a = new TareaGenerarReportePDF().generarPDF("facturaSanRafael", System.currentTimeMillis(), p);
		System.out.println(a);
	}
}
