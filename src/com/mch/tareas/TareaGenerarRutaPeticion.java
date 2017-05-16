package com.mch.tareas;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.mch.excepciones.ExcepcionMch;
import com.mch.utilidades.UtilJwt;
import com.mch.utilidades.UtilLecturaPropiedades;
import com.mch.utilidades.UtilMCH;
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
	public String generarRutaPeticion(String servicio, Map<String,Object> parametros) throws JSONException, IOException, ExcepcionMch {
		if(parametros.get("negocio") == null){
			throw new ExcepcionMch("No se encontró el atributo negocio.");
		}
		
		JSONObject c = UtilLecturaPropiedades.getInstancia().getPropJson("configuracionGeneral","si");
		JSONObject empresa = UtilLecturaPropiedades.getInstancia().getPropJson("negocio", (String)parametros.get("negocio"));
	
		/*
		 * "server" : "192.168.1.18",
			"username" : "sa",
			"password" : "1qazXSW2"
		 * */
		
		JSONObject configuracionDB= UtilLecturaPropiedades.getInstancia().getPropJson("configuracionesDB").getJSONObject("configuracionesDB");
		System.out.println(configuracionDB);
		Properties prop = new Properties();
		prop.put("server", configuracionDB.getString("server"));
		prop.put("username", configuracionDB.getString("username"));
		prop.put("password", configuracionDB.getString("password"));
		prop.put("database", empresa.getString("dataBase"));
		prop.put("dbms", "SQLSERVER");
		String cadena = UtilMCH.encriptar(prop.toString());
		parametros.put("infoComunReportes", cadena);
		
		
		
		String token = UtilJwt.getInstancia().generarToken(empresa.getString("negocio"),parametros);
		
		String ipServidor  = (String)c.get("ipServidor"),
				puerto     = (String)c.getString("puerto"),
				mapeoClase = (String)c.get(servicio),
				proyecto   = (String)c.get("proyecto"),
				protocolo  = (String)c.get("protocolo");
		return protocolo+"://"+ipServidor+":"+puerto+"/"+proyecto+"/rest/"+mapeoClase+"/"+token;
	}
	public static void main(String[] args) throws Exception{
		JSONObject o = UtilLecturaPropiedades.getInstancia().getPropJson("configuracionesDB");
		Properties prop = new Properties();
		prop.put("database", "");
		prop.put("username", "");
		prop.put("password", "");
		prop.put("dbms", "");
		prop.put("server", "");
		
	
		
		 System.out.println(o);
	}
	
}
