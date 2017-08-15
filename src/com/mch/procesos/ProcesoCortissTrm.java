package com.mch.procesos;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mch.actividades.ActividadEnviarCorreo;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioEnviarCorreo;
import com.mch.tareas.TareaGenerarRutaPeticion;
import com.mch.tareas.TareaPeticion;
import com.mch.utilidades.UtilMCH;

/**
 * 
 * @author Camilo Beltrán
 * @since 15/08/2017
 */
public class ProcesoCortissTrm extends TareaGenerarRutaPeticion implements Job {

	private static final String CORREO = "cbeltran@sistematizando.com";
	
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *Negocio con el cual se ejecuta todo el proceso, este 
	 *es el que se busca en el archivo de propiedades para
	 *determinar atributos.
	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	private static String NEGOCIO = "Cortiss";
	private ActividadEnviarCorreo actividadEnviarCorreo = new ActividadEnviarCorreo();
	private PropiedadServicioEnviarCorreo propiedadServicioEnviarCorreo = new PropiedadServicioEnviarCorreo();



	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		TareaPeticion tarea = null;
		String URL = null;
		try{
			tarea = new TareaPeticion();
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("dataBase", UtilMCH.getDataBaseName(NEGOCIO));
			parametros.put("negocio", NEGOCIO);
			URL = generarRutaPeticion("servicionTrm", parametros);
			System.out.println("----------------> "+URL);
			JSONObject retorno = new JSONObject(tarea.POST(URL));
			System.out.println("-->>>> "+retorno);
			if(retorno != null && retorno.has("mensaje")){
				enviarCorreo(retorno.getString("mensaje"), NEGOCIO);
			}

		}catch(Exception e){
			e.printStackTrace();
			enviarCorreo("Ocurrió un error en el proceso de CORTISS TRM DIARIA: <br> "+e.getMessage(), NEGOCIO);
		}finally{
			tarea = null;
		}
	}


	/**
	 * Metodo que invoca un servicio que
	 * envia correos electrónicos. 
	 * @param rutaZip
	 * @param mensaje
	 * @param obj
	 * @return String
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 * @throws ExcepcionMch
	 * @throws IOException
	 * @throws MessagingException
	 */
	private String enviarCorreo(String mensaje, String negocio){

		String retorno = "";
		try{
			propiedadServicioEnviarCorreo.setArchivos(null);

			propiedadServicioEnviarCorreo.setAsunto("Información automatización TRM diaria CORTISS");
			propiedadServicioEnviarCorreo.setDestinatario(CORREO);
			propiedadServicioEnviarCorreo.setMensaje(mensaje);
			propiedadServicioEnviarCorreo.setNegocio(negocio);
			retorno = actividadEnviarCorreo.enviarEmail(propiedadServicioEnviarCorreo, false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return retorno;
	}


	public static void main(String[] args) throws JobExecutionException {
		new ProcesoCortissTrm().execute(null);;
	}

}
