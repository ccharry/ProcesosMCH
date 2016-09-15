package com.mch.tareas;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mch.excepciones.ExcepcionMch;
import com.mch.utilidades.UtilJwt;
import com.mch.utilidades.UtilLecturaPropiedades;
/**
 * @author Camilo
 * 12/09/2016
 */
public class TareaGenerarRutaPeticion {
	
	
	
	/**
	 * Metodo que genera la ruta de la petición
	 * de acuerdo al archivo de propiedades.
	 * @param servicio
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws ExcepcionMch 
	 */
	protected String generarRutaPeticion(String servicio, Map<String,Object> parametros) throws JSONException, IOException, ExcepcionMch {
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
		return protocolo+"://"+ipServidor+":"+puerto+"/"+proyecto+"/rest/"+mapeoClase+"/"+token;
	}
}
