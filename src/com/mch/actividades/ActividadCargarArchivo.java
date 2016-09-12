package com.mch.actividades;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.json.JSONException;

import com.mch.bean.ArchivoBean;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioCargarArchivo;
import com.mch.tareas.TareaEnviarArchivoRest;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 06/09/2016
 */
public class ActividadCargarArchivo extends TareaEnviarArchivoRest{

	/**
	 * Metodo que invoca el servicio encargado
	 * de importar un archivo a una tabla que
	 * llega como parametro.
	 * @param file
	 * @param tabla
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws MessagingException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ExcepcionMch 
	 */
	public String cargarArchivoABaseDatos(File file, PropiedadServicioCargarArchivo config) throws JSONException, IOException, MessagingException, IllegalArgumentException, IllegalAccessException, ExcepcionMch{
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(config);
		List<ArchivoBean> f = new ArrayList<ArchivoBean>();
		f.add(new ArchivoBean(file));
		String URL = generarRutaPeticion("servicioCargarArchivoExcel",p);
		System.out.println(URL);
		return enviarArchivo(URL, f);
	}
	
	/**
	 * Metodo que invoca el servicio encargado
	 * de importar multiples archivos a una 
	 * tabla que llega como parametro.
	 * @param file
	 * @param tabla
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws MessagingException 
	 * @throws ExcepcionMch 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public String cargarArchivosABaseDatos(List<File> files, PropiedadServicioCargarArchivo config) throws JSONException, IOException, MessagingException, ExcepcionMch, IllegalArgumentException, IllegalAccessException{
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(config);
		List<ArchivoBean> f = new ArrayList<ArchivoBean>();
		for(File a : files)
			f.add(new ArchivoBean(a));
		String URL = generarRutaPeticion("servicioCargarArchivoExcel",p);
		return enviarArchivo(URL, f);
	}
	
	/**
	 * Metodo que invoca el servicio encargado
	 * de importar multiples archivos a una 
	 * tabla que llega como parametro.
	 * @param file
	 * @param tabla
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws MessagingException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ExcepcionMch 
	 */
	public String cargarArchivosABaseDatos(File[] files, PropiedadServicioCargarArchivo config) throws JSONException, IOException, MessagingException, IllegalArgumentException, IllegalAccessException, ExcepcionMch {
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(config);
		System.out.println(p);
		List<ArchivoBean> f = new ArrayList<ArchivoBean>();
		for(File a : files)
			f.add(new ArchivoBean(a));
		String URL = generarRutaPeticion("servicioCargarArchivoExcel",p);
		return enviarArchivo(URL, f);
	}
	
//	public static void main(String[] args) throws Exception {
//		String a = new ActividadCargarArchivo().cargarArchivoABaseDatos(new File("C:\\Users\\Admin\\Desktop\\TEMP/FACTURACION  AGOSTO  2016 (1).xlsx"), "FACTURAS_TEMP","SanRafael","SanRafael");
//		System.out.println(a);
//	}
}
