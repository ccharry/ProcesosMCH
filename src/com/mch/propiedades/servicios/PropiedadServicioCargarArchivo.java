package com.mch.propiedades.servicios;
/**
 * @author Camilo
 * 12/09/2016
 */
public class PropiedadServicioCargarArchivo {

	private String dataBase;
	private String tabla;
	private String negocio;

	public PropiedadServicioCargarArchivo(){}

	public PropiedadServicioCargarArchivo(String dataBase, String tabla, String negocio){
		this.dataBase = dataBase;
		this.tabla = tabla;
		this.negocio = negocio;
	}

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getNegocio() {
		return negocio;
	}

	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}

}
