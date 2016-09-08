package com.mch.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mch.utilidades.UtilLecturaPropiedades;


/**
 * @author Camilo
 * 01/09/2016
 */
public class PropiedadesPeticionBean {
	
	private String protocolo 		= "http";
	private String IPServidor;
	private String puerto;
	private String proyecto 	    = "ServiciosMCH";
	private String packageServicios = "rest";
	private String mapeoClase;
	private List<String> parametros = new ArrayList<String>();
	private UtilLecturaPropiedades lp = UtilLecturaPropiedades.getInstancia();
	/**
	 * Se asignana propiedades de acuerdo
	 * a la configuración en el JSON.
	 * @param negocio
	 * @throws Exception
	 */
	public PropiedadesPeticionBean(String negocio) throws Exception{
		JSONObject o = lp.getPropJson("negocio",negocio),
				   c = lp.getPropJson("configuracionGeneral","si");
		
		IPServidor = (String)c.get("ipServidor");
		puerto     = (String)c.getString("puerto");
		mapeoClase = (String)c.get("servicioLeerCorreo");
		JSONArray a = o.getJSONArray("parametrosServicio");
		for(int i = 0 ; i < a.length() ; i ++){
			JSONObject temp = a.getJSONObject(i);
			Iterator<String> k = temp.keys();
			while(k.hasNext())
				parametros.add(temp.getString(k.next()));
		}
			
	}
	
	/**
	 * Metodo que crea una cadena para realizar la petición,
	 * se definen valores prederterminados
	 * @return String con la cadena para realizar la petición
	 */
	public String generarRutaPeticion(){
		StringBuilder r = new StringBuilder();
		r.append(protocolo)
		.append("://")
		.append(IPServidor)
		.append(":")
		.append(puerto)
		.append("/")
		.append(proyecto)
		.append("/")
		.append(packageServicios)
		.append("/")
		.append(mapeoClase);
		for(String a : parametros)
			r.append("/"+a);
		return r.toString();
	}
	
	public PropiedadesPeticionBean setProtocolo(String protocolo) {
		this.protocolo = protocolo;
		return this;
	}
	public PropiedadesPeticionBean setDireccion(String direccion) {
		this.IPServidor = direccion;
		return this;
	}
	public PropiedadesPeticionBean setPuerto(String puerto) {
		this.puerto = puerto;
		return this;
	}
	public PropiedadesPeticionBean setProyecto(String proyecto) {
		this.proyecto = proyecto;
		return this;
	}
	public PropiedadesPeticionBean setPackageServicios(String packageServicios) {
		this.packageServicios = packageServicios;
		return this;
	}
	public PropiedadesPeticionBean setMapeoClase(String mapeoClase) {
		this.mapeoClase = mapeoClase;
		return this;
	}
	public PropiedadesPeticionBean setParametros(List<String> parametros) {
		this.parametros = parametros;
		return this;
	} 
	public static void main(String[] args) throws Exception {
		String a = new PropiedadesPeticionBean("sanRafael").generarRutaPeticion();
		System.out.println(a);
	}
}
