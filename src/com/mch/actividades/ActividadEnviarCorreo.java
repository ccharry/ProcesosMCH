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
		
		parametros.put("asunto", prop.getAsunto());
		parametros.put("mensaje", prop.getMensaje());
		parametros.put("destinatario", prop.getDestinatario());
		
//		List<String>p = generarParametros(prop);
//		StringBuilder URL = new StringBuilder();
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Se codifican los parametros en formato
		//UTF-8 para que sean compatibles algunos
		//caracteres en la petición POST
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		for(String a:p){
//			
//			a = a.replace("/", "&");
//			URL.append("/"+URLEncoder.encode(a, "UTF-8").replace("+", "%20"));
//		}
		
		return enviarArchivo(generarRutaPeticion("servicioEnviarCorreo", negocio,parametros), prop.getArchivos());
	}

//	private List<String> generarParametros(PropiedadesEnvioCorreoBean prop) throws Throwable{
//		List<String> r = new ArrayList<String>();
//		String temp1 = (prop.getDestinatario()+"").replaceAll("[\n,\r,\t]", "").replace(" ", "").replace("null", ""),
//				temp2 = (prop.getAsunto()+"").replaceAll("[\n,\r,\t]", "").replace(" ", "").replace("null", ""),
//				temp3 = (prop.getMensaje()+"").replaceAll("[\n,\r,\t]", "").replace(" ", "").replace("null", ""),
//				temp4 = "";
//
//		if(temp1.equals("") && (prop.getDestinatarios() == null || prop.getDestinatarios().size() == 0))
//			throw new Throwable("No se encontraron destinatarios");
//		if(temp2.equals(""))
//			throw new Throwable("No se encontraro el asunto");
//		if(temp3.equals(""))
//			throw new Throwable("No se encontraro el mensaje");
//
//		if(!temp1.equals(""))
//			r.add(prop.getDestinatario());
//
//		if(prop.getDestinatarios() != null){
//			for(int a = 0 ; a < prop.getDestinatarios().size() ; a++)
//				temp4+=","+prop.getDestinatarios().get(a);
//			r.add(temp4);
//		}
//		r.add(prop.getAsunto());
//		r.add(prop.getMensaje());
//		return r;
//	}

	public static void main(String[] args) throws Throwable {
		PropiedadesEnvioCorreoBean a = new PropiedadesEnvioCorreoBean();
		List<ArchivoBean>archivos = new ArrayList<ArchivoBean>();
		archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/0-convenciones_codigo_java.pdf")));
//		archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/03163695394 RECAUDO JUNIO 2016.csv")));
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add("nariza@sistematizando.com");
		destinatarios.add("ecastro@sistematizando.com");
		
		a.setAsunto("Probando envío de correo")
		.setDestinatario("nariza@sistematizando.com;ecastro@sistematizando.com,cbeltran@sistematizando.com")
		.setMensaje("<h1>PRUEBA2</h1><p>Hola como está...........asdfasdf...................., esto es uns PRUEBA desde Java</p><br><h2 style='background:red'>prueba STYLE</h2>")
		.setArchivos(archivos);
		//				.setArchivo(new ArchivoBean("1Liq Rec Emfermeria Abril - PRUEBA.xlsx",new File("E:/ARCHIVOBASE/1Liq Rec Emfermeria Abril - PRUEBA.xlsx")));
		String r = new ActividadEnviarCorreo().enviarEmail(a,"SanRafael");
		System.out.println(r);
	}
}
