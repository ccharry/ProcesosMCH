package com.mch.actividades;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mch.bean.ArchivoBean;
import com.mch.bean.PropiedadesEnvioCorreoBean;
import com.mch.tareas.TareaEnviarArchivoRest;

/**
 * @author Camilo
 * 05/09/2016
 */
public class ActividadEnviarCorreo extends TareaEnviarArchivoRest{

	/**
	 * 
	 * @param prop
	 * @return String en formato JSON
	 * @throws Throwable 
	 */
	public String enviarEmail(PropiedadesEnvioCorreoBean prop, String negocio) throws Throwable{
		Map<String,Object> parametros = new HashMap<String, Object>();
		
		if (prop.getAsunto().trim().equals("")){
			throw new Exception("No se encontró el asunto");
		}
		if (prop.getMensaje().trim().equals("")){
			throw new Exception("No se encontró el mensaje");
		}
		if (prop.getDestinatario().trim().equals("")){
			throw new Exception("No se encontraron destinatarios");
		}
		parametros.put("asunto", prop.getAsunto());
		parametros.put("mensaje", prop.getMensaje());
		parametros.put("destinatario", prop.getDestinatario());
		String s = generarRutaPeticion("servicioEnviarCorreo", negocio, parametros);
		return enviarArchivo(s , prop.getArchivos());
	}

	public static void main(String[] args) throws Throwable {
		PropiedadesEnvioCorreoBean a = new PropiedadesEnvioCorreoBean();
		List<ArchivoBean>archivos = new ArrayList<ArchivoBean>();
		archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/0-convenciones_codigo_java.pdf")));
//		archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/03163695394 RECAUDO JUNIO 2016.csv")));
		a.setAsunto("Probando envío de correo")
		.setDestinatario("nariza@sistematizando.com")
		.setMensaje("<h1>PRUEBA2</h1><p>Hola como está...........asdfasdf...................., esto es uns PRUEBA desde Java</p><br><h2 style='background:red'>prueba STYLE</h2>")
		.setArchivos(archivos);
		//				.setArchivo(new ArchivoBean("1Liq Rec Emfermeria Abril - PRUEBA.xlsx",new File("E:/ARCHIVOBASE/1Liq Rec Emfermeria Abril - PRUEBA.xlsx")));
		String r = new ActividadEnviarCorreo().enviarEmail(a,"san");
		System.out.println(r);
	}
}
