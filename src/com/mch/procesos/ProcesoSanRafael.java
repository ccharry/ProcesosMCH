package com.mch.procesos;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mch.actividades.ActividadCargarArchivo;
import com.mch.actividades.ActividadLeerCorreo;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioCargarArchivo;
import com.mch.utilidades.UtilMCH;

public class ProcesoSanRafael implements Job{

	private ActividadLeerCorreo actividadLeerCorreo = new ActividadLeerCorreo();
	private ActividadCargarArchivo actividadCargarArchivo = new ActividadCargarArchivo();
	private PropiedadServicioCargarArchivo propServicioCargarArchivo = new PropiedadServicioCargarArchivo();
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			JSONObject p = new ActividadLeerCorreo().leerCorreo("SanRafael");
			System.out.println(p);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public JSONObject leerCorreo(String negocio) throws JSONException, IOException {
		return actividadLeerCorreo.leerCorreo(negocio);
	}

	public void cargarArchivosDB(JSONArray ruta, String negocio) throws JSONException, IOException, MessagingException, IllegalArgumentException, IllegalAccessException, ExcepcionMch{
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Se modifican los parametros que espera el servicio
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		propServicioCargarArchivo.setNegocio(negocio);
		propServicioCargarArchivo.setTabla("FACTURAS_TEMP");
		propServicioCargarArchivo.setDataBase(UtilMCH.getDataBaseName(negocio));

		for(int a = 0 ; a < ruta.length() ; a++){
			String b = actividadCargarArchivo.cargarArchivosABaseDatos(new File(ruta.getJSONObject(a).getString("ruta")).listFiles(), propServicioCargarArchivo);
			System.out.println(b);
		}

	}

	public static void main(String[] args) throws JSONException, IOException, MessagingException, IllegalArgumentException, IllegalAccessException, ExcepcionMch{
		ProcesoSanRafael p = new ProcesoSanRafael();
		JSONObject obj = p.leerCorreo("SanRafael");
		p.cargarArchivosDB(obj.getJSONArray("info"), "SanRafael");
	}
}
