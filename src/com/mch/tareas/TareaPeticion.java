package com.mch.tareas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.mch.excepciones.ExcepcionMch;

/**
 * @author Camilo
 * 31/08/2016
 */
public class TareaPeticion {

	

	/**
	 * Metodo que envía una petición GET
	 * @param request
	 * @throws IOException 
	 * @throws Exception
	 */
	public JSONObject GET(String request) throws IOException {
		BufferedReader rd = null;
		try {
			request = request.replace("\\", "*");
			Logger.getLogger(getClass().getName()).log(Level.INFO,"=>Entró a enviar petición GET, URL : "+request);
			URL url = new URL(request);
			URLConnection conn2 = url.openConnection();
			return new JSONObject(TareaConvertInputStreamTo.string(conn2.getInputStream()));
		} finally {
			if (rd != null) 
				rd.close();
		}
	}
	
	/**
	 * Metodo que envia una petición POST
	 * @param request
	 * @return String
	 * @throws IOException 
	 * @throws ExcepcionMch 
	 */
	public String POST(String request) throws IOException, ExcepcionMch {
		Logger.getLogger(getClass().getName()).log(Level.INFO,"=>Entró a enviar petición POST, URL : "+request);
		StringBuffer response = new StringBuffer();
		URL obj = null;
		HttpURLConnection con = null;
		OutputStream os = null;
		try{
			obj = new URL(request);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			// For POST only - START
			con.setDoOutput(true);
			os = con.getOutputStream();
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			} else {
				throw new ExcepcionMch("POST request not worked");
			}
		}finally{
			obj = null;
			con = null;
			os = null;
		}
		return response.toString();
	}

	public static void main(String[] args) throws Exception {
		String a = new TareaPeticion().POST("http://192.168.2.5:8089/ServiciosMCH/rest/invocarProcedimiento/sdfsdf/PRUEBAP/1453");
		System.out.println(a);
	}
}
