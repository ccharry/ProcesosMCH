package com.mch.actividades;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.json.JSONException;

import com.mch.bean.ArchivoBean;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioEnviarCorreo;
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
	public String enviarEmail(PropiedadServicioEnviarCorreo prop) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException, MessagingException {
		if (prop.getAsunto() == null)
			throw new ExcepcionMch("No se encontró el asunto");
		if (prop.getMensaje() == null)
			throw new ExcepcionMch("No se encontró el mensaje");
		if (prop.getDestinatario() == null)
			throw new ExcepcionMch("No se encontraron destinatarios");

		if (prop.getAsunto().trim().equals(""))
			throw new ExcepcionMch("No se encontró el asunto");
		if (prop.getMensaje().trim().equals(""))
			throw new ExcepcionMch("No se encontró el mensaje");
		if (prop.getDestinatario().trim().equals(""))
			throw new ExcepcionMch("No se encontraron destinatarios");

		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern pc = Pattern.compile(ePattern);
		Matcher m = pc.matcher(prop.getDestinatario().trim());
		if(m.matches() == false){
			throw new ExcepcionMch("Formato de correo inválido: "+prop.getDestinatario());
		}
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(prop);
		String s = generarRutaPeticion("servicioEnviarCorreo", p);
		System.out.println(s);
		return enviarArchivo(s , prop.getArchivos());
	}

	public static void main(String[] args) throws Throwable {
		PropiedadServicioEnviarCorreo a = new PropiedadServicioEnviarCorreo();
		List<ArchivoBean>archivos = new ArrayList<ArchivoBean>();
		archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/0-convenciones_codigo_java.pdf")));
		archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/03163695394 RECAUDO JUNIO 2016.csv")));
		a.setAsunto("Probando envío de correo")
		.setDestinatario("ccamilo2303@gmail.com")
		.setMensaje("<h1>PRUEBA2</h1><p>Hola como está...........asdfasdf...................., esto es uns PRUEBA desde Java</p><br><h2 style='background:red'>prueba STYLE</h2>")
		.setArchivos(null);
		a.setNegocio("SanRafael");
		//		//				.setArchivo(new ArchivoBean("1Liq Rec Emfermeria Abril - PRUEBA.xlsx",new File("E:/ARCHIVOBASE/1Liq Rec Emfermeria Abril - PRUEBA.xlsx")));
		String r = new ActividadEnviarCorreo().enviarEmail(a);
		System.out.println(r);
	}
}
