package com.mch.actividades;

import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadesServicioEjecutarConsulta;
import com.mch.tareas.TareaEnviarArchivoRest;
import com.mch.tareas.TareaPeticion;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 28/11/2016
 */
public class ActividadEjecutarConsulta extends TareaEnviarArchivoRest{

	/**
	 * @param prop
	 * @return
	 * @throws ExcepcionMch
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 * @throws IOException
	 */
	public JSONArray ejecutarConsulta(PropiedadesServicioEjecutarConsulta prop) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		if((prop.getConsulta()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto consulta");
		if((prop.getDataBase()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto dataBase");
		if((prop.getNegocio()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto negocio");
		TareaPeticion tarea = new TareaPeticion();
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(prop);
		String r = tarea.POST(generarRutaPeticion("servicioConsultar", p));
		JSONArray retorno = new JSONArray(r);
		return retorno;
	}
	/**
	 * @param prop
	 * @return
	 * @throws ExcepcionMch
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 * @throws IOException
	 */
	public JSONArray ejecutarInsert(PropiedadesServicioEjecutarConsulta prop) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		if((prop.getConsulta()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto consulta");
		if((prop.getDataBase()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto dataBase");
		if((prop.getNegocio()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto negocio");
		TareaPeticion tarea = new TareaPeticion();
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(prop);
		String r = tarea.POST(generarRutaPeticion("servicioInsertar", p));
		JSONArray retorno = new JSONArray(r);
		return retorno;
	}
	
	
	public JSONArray ejecutarQuery(PropiedadesServicioEjecutarConsulta prop) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		if((prop.getConsulta()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto consulta");
		if((prop.getDataBase()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto dataBase");
		if((prop.getNegocio()+"").replace("null", "").replace(" ", "").equals(""))
			throw new ExcepcionMch("No se encontró el artibuto negocio");
		TareaPeticion tarea = new TareaPeticion();
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(prop);
		String r = tarea.POST(generarRutaPeticion("servicioQuery", p));
		JSONArray retorno = new JSONArray(r);
		return retorno;
	}
	
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, JSONException, ExcepcionMch, IOException {
		PropiedadesServicioEjecutarConsulta p = new PropiedadesServicioEjecutarConsulta("MCH", "SELECT COUNT(*)D FROasM FACTURAS", "FacturacionMch");
		System.out.println(new ActividadEjecutarConsulta().ejecutarConsulta(p));
	}
}
