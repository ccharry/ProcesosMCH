package com.mch.propiedades.servicios;

import java.io.File;

/**
 * @author Camilo Beltrán
 * @since 21/03/2017
 */
public class PropiedadGenerarReporte {

	private String dataBase;
	private String negocio;
	
	
	public PropiedadGenerarReporte(String dataBase, String negocio) {
		super();
		this.dataBase = dataBase;
		this.negocio = negocio;
	}
	
	public String getDataBase() {
		return dataBase;
	}
	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}
	public String getNegocio() {
		return negocio;
	}
	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}
	
}
