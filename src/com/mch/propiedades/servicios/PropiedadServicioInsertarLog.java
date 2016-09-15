package com.mch.propiedades.servicios;
/**
 * @author Camilo
 * 14/09/2016
 */
public class PropiedadServicioInsertarLog {

	private String dataBase;
	private String proceso;
	private String mensaje;
	private String negocio;
	
	public String getDataBase() {
		return dataBase;
	}
	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String procaeso) {
		this.proceso = procaeso;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getNegocio() {
		return negocio;
	}
	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}
}
