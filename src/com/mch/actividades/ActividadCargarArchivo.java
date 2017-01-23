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
		List<ArchivoBean> f = new ArrayList<ArchivoBean>();
		for(File a : files)
			f.add(new ArchivoBean(a));
		String URL = generarRutaPeticion("servicioCargarArchivoExcel",p);
		return enviarArchivo(URL, f);
	}


	/**
	 * 
	 * @param files
	 * @param config
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 * @throws IOException
	 * @throws ExcepcionMch
	 * @throws MessagingException
	 */
	public List<Object[]> leerArchivoExcel(File files, PropiedadServicioCargarArchivo config) throws IllegalArgumentException, IllegalAccessException, JSONException, IOException, ExcepcionMch, MessagingException{
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(config);
		List<ArchivoBean> f = new ArrayList<ArchivoBean>();
		f.add(new ArchivoBean(files));
		String URL = generarRutaPeticion("servicioLeerArchivoExcel",p);
		return organizarInformacion(enviarArchivo(URL, f));
	}

	/**
	 * @param archivo
	 * @param config
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 * @throws IOException
	 * @throws ExcepcionMch
	 * @throws MessagingException
	 */
	public Object enviarArchivo(File archivo, PropiedadServicioCargarArchivo config) throws IllegalArgumentException, IllegalAccessException, JSONException, IOException, ExcepcionMch, MessagingException{
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(config);
		String URL = generarRutaPeticion("servicioCargarArchivoExcelHoras",p);
		return enviarArchivo2(URL, new ArchivoBean(archivo));
	}
	
	/**
	 * Metodo que organiza la información
	 * que llega en un formato para ponerla
	 * en una matriz.
	 * @param info
	 * @return
	 */
	private List<Object[]> organizarInformacion(String info){

		System.out.println("--------------------------------");
		System.out.println(info);
		System.out.println("--------------------------------");
		List<Object[]> retorno = new ArrayList<Object[]>();

		String[] filas = info.split("\n");

		for(String f : filas){
			String[] columnasTemp = f.split("\t");
			Object[] columnas = new Object[columnasTemp.length];
			for(int c = 0 ; c < columnasTemp.length ; c++){
				columnas[c] = columnasTemp[c];
			}
			retorno.add(columnas);
		}
		return retorno;
	}


	public static void main(String[] args) {
		//		List<Object[]> a = new ActividadCargarArchivo().cargarArchivosABaseDatos(new File("E:\\ARCHIVOBASE\\p").listFiles(), new PropiedadServicioCargarArchivo("Pru_SanRafael", "FACTURAS_TEMP", "SanRafael", true));
		//		for(Object[] b : a){
		//			for(Object c : b){
		//				System.out.print(c+" ");
		//			}
		//			System.out.println("---------------");
		//		}
		try{
			String x =  new ActividadCargarArchivo().cargarArchivosABaseDatos(new File("E:\\ARCHIVOBASE\\p").listFiles(), new PropiedadServicioCargarArchivo("Pru_SanRafael", "FACTURAS_TEMP", "SanRafael", true));
			System.out.println(x);
		}catch(Exception e){

		}
	}
}
