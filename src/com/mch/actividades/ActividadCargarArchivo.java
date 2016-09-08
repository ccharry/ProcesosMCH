package com.mch.actividades;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mch.bean.ArchivoBean;
import com.mch.tareas.TareaEnviarArchivoRest;

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
	 * @throws Exception
	 */
	public String cargarArchivoABaseDatos(File file, String tabla, String negocio) throws Exception{
		List<ArchivoBean> f = new ArrayList<ArchivoBean>();
		f.add(new ArchivoBean(file));
		String URL = generarRutaPeticion("servicioCargarArchivoExcel",negocio)+"/"+tabla;
		System.out.println(URL);
		return enviarArchivo(URL, f);
	}
	
	/**
	 * Metodo que invoca el servicio encargado
	 * de importar multiples archivos a una 
	 * tabla que llega como parametro.
	 * @param file
	 * @param tabla
	 * @throws Exception
	 */
	public String cargarArchivosABaseDatos(List<File> files, String tabla, String negocio) throws Exception{
		List<ArchivoBean> f = new ArrayList<ArchivoBean>();
		for(File a : files)
			f.add(new ArchivoBean(a));
		String URL = generarRutaPeticion("servicioCargarArchivoExcel",negocio)+"/"+tabla;
		return enviarArchivo(URL, f);
	}
	
	public static void main(String[] args) throws Exception {
		String a = new ActividadCargarArchivo().cargarArchivoABaseDatos(new File("E:/ARCHIVOS/FACTURACION  AGOSTO  2016 (1).xlsx"), "TEMP_FACTURAS","sanRafael");
		System.out.println(a);
	}
}
