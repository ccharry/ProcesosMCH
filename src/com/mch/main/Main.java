package com.mch.main;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.mch.procesos.ProcesoSanRafael;
import com.mch.tareas.TareaCompilarReporte;
import com.mch.utilidades.UtilLecturaPropiedades;
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
	 */
	public static void main(String[] args) throws SchedulerException, URISyntaxException, JSONException, IOException {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Informacion de arranque");
		TareaCompilarReporte compilar = new TareaCompilarReporte();
		String reporte = "facturaSanRafael";
		Logger.getLogger(Main.class.getName()).log(Level.INFO, compilar.compilar(reporte));
		int tiempo = UtilLecturaPropiedades.getInstancia().getPropJson("tiempoEjecucionEnMinutos").getInt("tiempoEjecucionEnMinutos");
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Tiempo de ejecucion en minutos: "+tiempo);
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Esta configuracion se puede modificar en el archivo configuraciones.json que está dentro del JAR");
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "NOTA: Recuerde extraer las carpetas de los recuros que estan dentro del JAR para el correcto funcionamiento");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		
		
		JobDetail job = JobBuilder.newJob(ProcesoSanRafael.class).withIdentity("procesoSanRafael", "group1").build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("triggerProcesoSanRafael", "group1")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(tiempo).repeatForever())
				.build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}
}
