package com.mch.main;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import com.mch.procesos.ProcesoSanRafael;
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
	 */
	public static void main(String[] args) throws SchedulerException {
		JobDetail job = JobBuilder.newJob(ProcesoSanRafael.class).withIdentity("procesoSanRafael", "group1").build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("triggerProcesoSanRafael", "group1")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever())
				.build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}
}
