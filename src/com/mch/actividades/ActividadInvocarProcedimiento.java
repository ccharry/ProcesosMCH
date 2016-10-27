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
		TareaPeticion tarea = null;
		String r = null, URL = null;
		try{
			tarea = new TareaPeticion();
			URL = generarRutaPeticion("servicioInvocarProcedimiento", UtilMCH.generarMapPorPropiedad(prop));
			r = tarea.POST(URL);
		}finally{
			tarea = null;
		}
		return r;
	}
	
	public static void main(String[] args) {
		ActividadInvocarProcedimiento acProcedimiento = new ActividadInvocarProcedimiento();
		PropiedadServicioInvocarProcedimiento procedimiento  = new PropiedadServicioInvocarProcedimiento();
		procedimiento.setNegocio("FacturacionMch");
		procedimiento.setDataBase("MCH");
		procedimiento.setProcedimiento("procDisable");
		procedimiento.setParametros("");
		try {
			System.out.println(acProcedimiento.invocarProcedimiento(procedimiento));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcepcionMch e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
