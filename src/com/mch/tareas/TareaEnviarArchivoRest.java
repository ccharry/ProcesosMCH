package com.mch.tareas;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.json.JSONException;
import org.json.JSONObject;

import com.mch.bean.ArchivoBean;
import com.mch.excepciones.ExcepcionMch;
import com.mch.utilidades.UtilJwt;
import com.mch.utilidades.UtilLecturaPropiedades;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Camilo
 * 06/09/2016
 */
public class TareaEnviarArchivoRest {

	/**
	 * Metodod encargado de enviar un archivo
	 * de acuerdo a la URL especificada
	 * @param URL
	 * @param archivos
	 * @return String 
	 * @throws IOException
	 * @throws MessagingException
	 */
	public String enviarArchivo(String URL, List<ArchivoBean>archivos) throws IOException, MessagingException{
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource resource = client.resource(URL);
		MimeMultipart request = new MimeMultipart();
		MimeBodyPart mb = null;

		if(archivos != null){
			for(int a = 0 ; a < archivos.size() ; a++){
				mb = new MimeBodyPart();
				mb.attachFile(archivos.get(a).getArchivo());
				mb.setFileName(archivos.get(a).getNombreArchivo());;
				request.addBodyPart(mb);
			}
			if(archivos.size() == 0){
				InputStream in = new ByteArrayInputStream("".getBytes("UTF-8"));
				mb = new MimeBodyPart(in);
				request.addBodyPart(mb);
			}
		}else{
			InputStream in = new ByteArrayInputStream("".getBytes("UTF-8"));
			mb = new MimeBodyPart(in);
			request.addBodyPart(mb);
		}

		String response = resource
				.entity(request, "multipart/form-data")
				.accept("application/json")
				.post(String.class);
		return response;
	}

	/**
	 * Metodo que genera la ruta de la petición
	 * de acuerdo al archivo de propiedades.
	 * @param servicio
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws ExcepcionMch 
	 */
	public String generarRutaPeticion(String servicio, Map<String,Object> parametros) throws JSONException, IOException, ExcepcionMch {
		if(parametros.get("negocio") == null){
			throw new ExcepcionMch("No se encontró el atributo negocio.");
		}
		
		JSONObject c = UtilLecturaPropiedades.getInstancia().getPropJson("configuracionGeneral","si");
		JSONObject empresa = UtilLecturaPropiedades.getInstancia().getPropJson("negocio", (String)parametros.get("negocio"));
		
		
		String token = UtilJwt.getInstancia().generarToken(empresa.getString("negocio"),parametros);
		
		String ipServidor  = (String)c.get("ipServidor"),
				puerto     = (String)c.getString("puerto"),
				mapeoClase = (String)c.get(servicio),
				proyecto   = (String)c.get("proyecto"),
				protocolo  = (String)c.get("protocolo");
		System.out.println(protocolo+"://"+ipServidor+":"+puerto+"/"+proyecto+"/rest/"+mapeoClase+"/"+token);
		return protocolo+"://"+ipServidor+":"+puerto+"/"+proyecto+"/rest/"+mapeoClase+"/"+token;
	}


}
