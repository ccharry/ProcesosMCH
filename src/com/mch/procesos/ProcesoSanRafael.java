package com.mch.procesos;

import java.io.File;

import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mch.bean.PropiedadesPeticionBean;
import com.mch.tareas.TareaPeticion;

public class ProcesoSanRafael implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			JSONObject p = new TareaPeticion().GET(new PropiedadesPeticionBean("sanRafael").generarRutaPeticion());
			System.out.println(p);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		try {
//			JSONObject p = new UtilPeticion().GET(new PropiedadesPeticionBean("sanRafael").generarRutaPeticion());
//			System.out.println(p);
			File f = new File("\\\\192.168.2.5\\c$\\RECAUDOS_BRINKS_EXCEL_MARZO_2016.xlsx");
			System.out.println(f.isFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
