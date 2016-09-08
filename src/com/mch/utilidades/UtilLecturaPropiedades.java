package com.mch.utilidades;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mch.tareas.TareaConvertInputStreamTo;

/**
 * @author Camilo
 * 31/08/2016
 */
public class UtilLecturaPropiedades {

	private static UtilLecturaPropiedades instancia = null;
	
	private UtilLecturaPropiedades(){}
	
	public static UtilLecturaPropiedades getInstancia(){
		if(instancia == null)
			instancia = new UtilLecturaPropiedades();
		return instancia;
	}
	
	
	
	/**
	 * @param propiedad
	 * @return
	 * @throws Exception
	 */
	public JSONObject getPropJson(String atributo, String valor) throws Exception {
		InputStream in = null;
		
		try {
			String propFileName = "configuraciones.json";
			in = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (in != null){
				JSONArray o = new JSONArray(TareaConvertInputStreamTo.string(in));
				for(int a = 0;a<o.length();a++){
					if(o.getJSONObject(a).isNull(atributo) == true)
						continue;
					if(o.getJSONObject(a).get(atributo).equals(valor)){
						return o.getJSONObject(a); 
					}
				}
				throw new FileNotFoundException("No se pudo encontrar el atributo : "+valor);
			}
			else 
				throw new FileNotFoundException("No se pudo encontrar el archivo : " + propFileName);

		} finally {
			in.close();
		}
	}
	public static void main(String[] args) throws Exception {
		JSONObject r = new UtilLecturaPropiedades().getPropJson("negocio","sanRafael");
		System.out.println(r);
		System.out.println(4<<1);
	}
}
