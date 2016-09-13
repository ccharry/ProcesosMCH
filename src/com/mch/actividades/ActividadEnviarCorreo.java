package com.mch.actividades;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;

import org.json.JSONException;

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

		if (prop.getAsunto().trim().equals("")){
			throw new ExcepcionMch("No se encontr� el asunto");
		}
		if (prop.getMensaje().trim().equals("")){
			throw new ExcepcionMch("No se encontr� el mensaje");
		}
		if (prop.getDestinatario().trim().equals("")){
			throw new ExcepcionMch("No se encontraron destinatarios");
		}

		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(prop);
		System.out.println(prop.getArchivos()+" ... antes ... "+p);
		String s = generarRutaPeticion("servicioEnviarCorreo", p);
		return enviarArchivo(s , prop.getArchivos());
	}

	public static void main(String[] args) throws Throwable {
		PropiedadServicioEnviarCorreo a = new PropiedadServicioEnviarCorreo();
//		List<ArchivoBean>archivos = new ArrayList<ArchivoBean>();
//		archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/0-convenciones_codigo_java.pdf")));
		//		archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/03163695394 RECAUDO JUNIO 2016.csv")));
		a.setAsunto("Probando env�o de correo")
		.setDestinatario("ccamilo2303@gmail.com")
		.setMensaje("<h1>PRUEBA2</h1><p>Hola como est�...........asdfasdf...................., esto es uns PRUEBA desde Java</p><br><h2 style='background:red'>prueba STYLE</h2>")
		.setArchivos(null);
		a.setNegocio("SanRafael");
		//				.setArchivo(new ArchivoBean("1Liq Rec Emfermeria Abril - PRUEBA.xlsx",new File("E:/ARCHIVOBASE/1Liq Rec Emfermeria Abril - PRUEBA.xlsx")));
		String r = new ActividadEnviarCorreo().enviarEmail(a);
		System.out.println(r);
	}
}
