package com.mch.actividades;

import java.io.IOException;

import org.json.JSONException;

import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioInsertarLog;
import com.mch.tareas.TareaGenerarRutaPeticion;
import com.mch.tareas.TareaPeticion;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 14/09/2016
 */
public class ActividadInsertar{
	
	/**
	 * Metodod que envia una petición
	 * POST que invoca un servicio que
	 * inserta un log.
	 * @param prop
	 * @return
	 * @throws JSONException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws ExcepcionMch
	 */
	public static String log(PropiedadServicioInsertarLog prop) throws JSONException, IllegalArgumentException, IllegalAccessException, IOException, ExcepcionMch{
		TareaPeticion tarea = null;
		String r = null, URL = null;
		try{
			tarea = new TareaPeticion();
			URL = new TareaGenerarRutaPeticion().generarRutaPeticion("servicioInsertarLog", UtilMCH.generarMapPorPropiedad(prop));
			r = tarea.POST(URL);
		}finally{
			tarea = null;
		}
		return r;
	}
	
}
