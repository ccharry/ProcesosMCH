package com.mch.tareas;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.mch.bean.ArchivoBean;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Camilo
 * 06/09/2016
 */
public class TareaEnviarArchivoRest extends TareaGenerarRutaPeticion{

	/**
	 * Metodo encargado de enviar un archivo
	 * de acuerdo a la URL especificada
	 * @param URL
	 * @param archivos
	 * @return String 
	 * @throws IOException
	 * @throws MessagingException
	 */
	protected String enviarArchivo(String URL, List<ArchivoBean>archivos) throws IOException, MessagingException{
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
	 * Metodo que envia un archivo al servicio, y retorna
	 * un Object que dependiendo del servicio se puede tipar
	 * de diferentes formas.
	 * @param URL
	 * @param archivo
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	@SuppressWarnings({ "resource", "unused" })
	protected Object enviarArchivo2(String URL, ArchivoBean archivo) throws IOException, MessagingException{
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource resource = client.resource(URL);
		MimeMultipart request = new MimeMultipart();
		MimeBodyPart mb = null;
		String n = archivo.getNombreArchivo();
		Object retorno = null;


		if(archivo != null){
			mb = new MimeBodyPart();
			mb.attachFile(archivo.getArchivo());
			mb.setFileName(n);
			request.addBodyPart(mb);
		}else{
			InputStream in = new ByteArrayInputStream("".getBytes("UTF-8"));
			mb = new MimeBodyPart(in);
			request.addBodyPart(mb);
		}
		ClientResponse response = resource
				.entity(request, "multipart/form-data")
				.post(ClientResponse.class);


		InputStream in = response.getEntityInputStream();

		if(response.getHeaders().containsKey("Content-Length")){
			File f = Files.createTempFile(n.substring(0, n.lastIndexOf(".")), n.substring(n.lastIndexOf("."))).toFile();
			byte[] t = new byte[in.available()];
			in.read(t);
			new FileOutputStream(f).write(t);
			retorno = f;
		}else{
			Scanner s = new Scanner(in).useDelimiter("\\A");
			retorno = (s.hasNext() ? s.next() : "");
		}

		return retorno;
	}


	public static void main(String[] args) throws IOException, MessagingException {
		String url = "http://192.168.1.27:8081/ServiciosMCH/rest/cargarArchivo/cargarArchivoHoras/SDF";
		Object a = new TareaEnviarArchivoRest().enviarArchivo2(url, new ArchivoBean(new File("E:/ARCHIVOBASE/Liq Rec Médicos y Especialistas julio.xlsx")));
		System.out.println(a);
	}
}
