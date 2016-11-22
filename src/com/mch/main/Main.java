package com.mch.main;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
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


	private static Map<String, String> archivos = new HashMap<String, String>();
	private static TareaCompilarReporte compilar = new TareaCompilarReporte();
	
	private static void encontrarReportes(String rutaBase){
		File ar = new File(rutaBase);
		if(ar.isDirectory()){
			File[] aa = ar.listFiles();
			for(int a = 0 ; a < aa.length ; a++){
				if(aa[a].isDirectory()){
					encontrarReportes(aa[a].toString());
				}else{
					String nombre = aa[a].toString().substring(aa[a].toString().lastIndexOf("\\")+1, aa[a].toString().lastIndexOf("."));
					archivos.put(nombre, nombre);
				}
			}
		}
	}
	
	public static void compilarReportes(){
		Collection<String> c = archivos.values();
		Object[] a = c.toArray();
		for(Object o : a){
			Logger.getLogger(Main.class.getName()).log(Level.INFO, compilar.compilar(o.toString()));
		}
	}


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

		TareaGenerarRutaPeticion rutaPeticion = new TareaGenerarRutaPeticion();
		TareaPeticion tarea = new TareaPeticion();
		Map<String, Object> o = new HashMap<String, Object>();
		o.put("dataBase", "SanRafael");
		o.put("negocio", "SanRafael");
		String rutaPing = null;

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
//		Logger.getLogger(Main.class.getName()).log(Level.INFO, compilar.compilar(reporte));
//		Logger.getLogger(Main.class.getName()).log(Level.INFO, compilar.compilar(reporte2));
		encontrarReportes(UtilMCH.getRutaProyecto().replace("bin", "reportes"));
		compilarReportes();
		int tiempo = UtilLecturaPropiedades.getInstancia().getPropJson("tiempoEjecucionEnMinutos").getInt("tiempoEjecucionEnMinutos");
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Tiempo de ejecucion en minutos: "+tiempo);
		Logger.getLogger(Main.class.getName()).log(Level.INFO, "Esta configuracion se puede modificar en el archivo configuraciones.json que está dentro del JAR");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");



		JobDetail job = JobBuilder.newJob(ProcesoSanRafael.class).withIdentity("procesoSanRafael", "group1").build();
		Trigger trigger = TriggerBuilder.newTrigger() .withIdentity("triggerProcesoSanRafael", "group1") .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(tiempo).repeatForever()).build();

		JobDetail job2 = JobBuilder.newJob(ProcesoFacturacionMch.class).withIdentity("procesoFacturacion", "group2").build();
		Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("triggerProcesoFacturacion", "group2").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(tiempo+1).repeatForever()).build();
		
//		JobDetail job3 = JobBuilder.newJob(ProcesoPruebasSanRafael.class).withIdentity("procesoPruebasSanRafael", "group1").build();
//		Trigger trigger3 = TriggerBuilder.newTrigger() .withIdentity("triggerProcesoSanRafael", "group1") .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(tiempo+2).repeatForever()).build();
//		
//		JobDetail job4 = JobBuilder.newJob(ProcesoPruebasFacturacionMch.class).withIdentity("procesoPruebasFacturacion", "group2").build();
//		Trigger trigger4 = TriggerBuilder.newTrigger().withIdentity("triggerProcesoFacturacion", "group2").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(tiempo+3).repeatForever()).build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
		scheduler.scheduleJob(job2, trigger2);
//		scheduler.scheduleJob(job3, trigger3);
//		scheduler.scheduleJob(job4, trigger4);
	}
}
