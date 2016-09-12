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
import com.mch.propiedades.servicios.PropiedadesEnvioCorreoBean;
import com.mch.tareas.TareaEnviarArchivoRest;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 05/09/2016
 */
public class ActividadEnviarCorreo extends TareaEnviarArchivoRest{

	/**
	 * 
	 * @param prop
	 * @return String en formato JSON
	 * @throws ExcepcionMch 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws MessagingException 
	 */
	public String enviarEmail(PropiedadesEnvioCorreoBean prop, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException, MessagingException {

		if (prop.getAsunto().trim().equals("")){
			throw new ExcepcionMch("No se encontró el asunto");
		}
		if (prop.getMensaje().trim().equals("")){
			throw new ExcepcionMch("No se encontró el mensaje");
		}
		if (prop.getDestinatario().trim().equals("")){
			throw new ExcepcionMch("No se encontraron destinatarios");
		}

		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(prop);
		String s = generarRutaPeticion("servicioEnviarCorreo", p);
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
