package com.mch.actividades;

import java.io.IOException;

import org.json.JSONException;

import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioInvocarProcedimiento;
import com.mch.tareas.TareaGenerarRutaPeticion;
import com.mch.tareas.TareaPeticion;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 12/09/2016
 */
public class ActividadInvocarProcedimiento extends TareaGenerarRutaPeticion{
	
	private TareaPeticion tarea = new TareaPeticion();
	
	/**
	 * @param prop
	 * @return String con el retorno del procedimiento.
	 * @throws IOException
	 * @throws ExcepcionMch
	 * @throws JSONException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public String invocarProcedimiento(PropiedadServicioInvocarProcedimiento prop) throws IOException, ExcepcionMch, JSONException, IllegalArgumentException, IllegalAccessException {
		String r = null, URL = null;
		try{
			URL = generarRutaPeticion("servicioInvocarProcedimiento", UtilMCH.generarMapPorPropiedad(prop));
			r = tarea.POST(URL);
		}finally{
			tarea = null;
		}
		return r;
	}

}
