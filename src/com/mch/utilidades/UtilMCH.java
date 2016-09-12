package com.mch.utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


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
	 * de un objeto inputStream
	 * @param nombreArchivo
	 * @param in
	 * @return String, ruta del archivo en disco
	 */
	public static String escribirArchivoTemporalDesdeInputStream(String nombreArchivo, InputStream in, String carptaTemp, boolean replaceBin){
		File f = null;
		try{
			String ruta = UtilMCH.getRutaProyecto()+"/"+carptaTemp+"/"+nombreArchivo;
			if(replaceBin == true)
				ruta = ruta.replace("bin/", "");
			f=new File(ruta);
			System.out.println(UtilMCH.getRutaProyecto()+"/"+carptaTemp+"/"+nombreArchivo+" -----");
			OutputStream salida=new FileOutputStream(f);
			byte[] buf =new byte[1024];
			int len;
			while((len=in.read(buf))>0){
				salida.write(buf,0,len);
			}
			salida.close();
			in.close();
			System.out.println("Se realizo la conversion con exito");
		}catch(IOException e){
			System.out.println("Se produjo el error : "+e.toString());
		}
		return f.toPath().toString();
	}
}
