package com.mch.utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Camilo
 * 05/09/2016
 */
public class UtilMCH {

	private static String ruta = null;

	public static String getRutaProyecto() {
		if(ruta == null){
			ruta = UtilMCH.class.getClassLoader().getResource("").getPath().replace("/classes", "").replace("/WEB-INF", "").replaceFirst("/", "");
		}
		if(!ruta.contains(":")){
			ruta = "/"+ruta;
		}
		return ruta;
	}

	/**
	 * Metodo que escribe un archivo dependiendo 
	 * de un objeto InputStream
	 * @param nombreArchivo
	 * @param in
	 * @return String, ruta del archivo en disco
	 */
	public static String escribirArchivoTemporalDesdeInputStream(String nombreArchivo, InputStream in, String carptaTemp, boolean replaceBin, long time) throws IOException{
		File f = null, ft = null;
		String ruta = UtilMCH.getRutaProyecto()+"/"+carptaTemp+"/temporal_"+time+"/"+nombreArchivo,
				rutaTemp = UtilMCH.getRutaProyecto()+"/"+carptaTemp+"/temporal_"+time;
		if(replaceBin == true){
			ruta = ruta.replace("bin/", "");
			rutaTemp = rutaTemp.replace("bin/", "");
		}
		ft = new File(rutaTemp);
		ft.mkdir();

		f = new File(ruta);

		OutputStream salida=new FileOutputStream(f);
		byte[] buf =new byte[1024];
		int len;
		while((len=in.read(buf))>0){
			salida.write(buf,0,len);
		}
		salida.close();
		in.close();
		Logger.getLogger(UtilMCH.class.getName()).log(Level.INFO,"Se realizo la conversion con exito");
		return f.toPath().toString();
	}
}
