package com.mch.main;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.mch.excepciones.ExcepcionMch;
import com.mch.procesos.ProcesoFacturacionMch;
import com.mch.procesos.ProcesoSanRafael;
import com.mch.tareas.TareaCompilarReporte;
import com.mch.tareas.TareaGenerarRutaPeticion;
import com.mch.tareas.TareaPeticion;
import com.mch.utilidades.UtilLecturaPropiedades;
import com.mch.utilidades.UtilMCH;
/**
 * @author Camilo
 * 31/08/2016
 */
public class Main {
	/**
	 * Raiz principal de la tarea, en esta clase 
	 * se mapean todas las tareas que se quieren
	 * ejecutar.
	 * @param args
	 * @throws SchedulerException
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws ExcepcionMch 
	 */
	public static void main(String[] args) throws SchedulerException, URISyntaxException, JSONException, IOException, ExcepcionMch {

		TareaCompilarReporte compilar = new TareaCompilarReporte();
		TareaGenerarRutaPeticion rutaPeticion = new TareaGenerarRutaPeticion();
		TareaPeticion tarea = new TareaPeticion();
		Map<String, Object> o = new HashMap<String, Object>();
		o.put("dataBase", "SanRafael");
		o.put("negocio", "SanRafael");
		String reporte = "facturaSanRafael", reporte2 = "facturaMch", rutaPing = null;
		
		boolean v = new File(UtilMCH.getRutaProyecto().replace("bin/", "")).isDirectory();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Informacion de arranque");
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Ruta del proyecto:");
		Logger.getLogger(Main.class.getName()).log(Level.INFO, UtilMCH.getRutaProyecto().replace("bin/", ""));
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Ruta valida: "+v);
		if(v == false){
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "NOTA: Recuerde extraer las carpetas de los recuros que estan dentro del JAR para el correcto funcionamiento");
			throw new ExcepcionMch("No se encontro la ruta "+UtilMCH.getRutaProyecto().replace("bin/", ""));
		}
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Realizando Ping a servidor: ");
		rutaPing = rutaPeticion.generarRutaPeticion("servicioPing", o);
		System.err.println(rutaPing);
		JSONObject r = new JSONObject(tarea.POST(rutaPing));
		if(!r.isNull("error")){
			throw new ExcepcionMch("Ha ocurrido un error al relizar el ping a la dirección: "+rutaPing);
		}
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Rotorno Ping: "+r);
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Compilado reporte:");
		Logger.getLogger(Main.class.getName()).log(Level.INFO, compilar.compilar(reporte));
		Logger.getLogger(Main.class.getName()).log(Level.INFO, compilar.compilar(reporte2));
		int tiempo = UtilLecturaPropiedades.getInstancia().getPropJson("tiempoEjecucionEnMinutos").getInt("tiempoEjecucionEnMinutos");
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Tiempo de ejecucion en minutos: "+tiempo);
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Esta configuracion se puede modificar en el archivo configuraciones.json que está dentro del JAR");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");



		JobDetail job = JobBuilder.newJob(ProcesoSanRafael.class).withIdentity("procesoSanRafael", "group1").build();
		Trigger trigger = TriggerBuilder.newTrigger() .withIdentity("triggerProcesoSanRafael", "group1") .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(tiempo).repeatForever()).build();
		
		JobDetail job2 = JobBuilder.newJob(ProcesoFacturacionMch.class).withIdentity("procesoFacturacion", "group2").build();
		Trigger trigger2 = TriggerBuilder.newTrigger()
				.withIdentity("triggerProcesoFacturacion", "group2")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(tiempo+1).repeatForever())
				.build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
		scheduler.scheduleJob(job2, trigger2);
	}
}
