package com.mch.actividades;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mch.propiedades.servicios.PropiedadesPeticion;
import com.mch.tareas.TareaPeticion;

/**
 * @author Camilo
 * 12/09/2016
 */
public class ActividadLeerCorreo {

	/**
	 * Metodo que invoca un servicio que 
	 * le llega como parametro un token,
	 * el servicio desencripta el token y
	 * obtiene los parametros necesarios
	 * para leer el correo definido en el
	 * archivo de propiedades que está en
	 * el proyecto ServiciosMCH.
	 * @param negocio
	 * @return JSONObject con la informacion del retorno del servicio
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws Exception
	 */
	public JSONObject leerCorreo(String negocio) throws JSONException, IOException {
		return new TareaPeticion().GET(new PropiedadesPeticion(negocio).generarRutaPeticion());
	}
	
	public static void main(String[] args) {
		ActividadLeerCorreo ac = new ActividadLeerCorreo();
		try {
			System.out.println(ac.leerCorreo("san").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
