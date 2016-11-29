package com.mch.propiedades.servicios;
/**
 * @author Camilo
 * 28/11/2016
 */
public class PropiedadesServicioEjecutarConsulta {
	
	private String dataBase;
	private String consulta;
	private String negocio;
	

	@SuppressWarnings("unused")
	private PropiedadesServicioEjecutarConsulta(){};
	
	public PropiedadesServicioEjecutarConsulta(String dataBase, String consulta, String negocio) {
		super();
		this.dataBase = dataBase;
		this.consulta = consulta;
		this.negocio = negocio;
	}
	
	
	public String getDataBase() {
		return dataBase;
	}
	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}
	public String getConsulta() {
		return consulta;
	}
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public String getNegocio() {
		return negocio;
	}

	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}
	
}
