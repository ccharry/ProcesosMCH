package com.mch.propiedades.servicios;
/**
 * @author Camilo
 * 12/09/2016
 */
public class PropiedadServicioCargarArchivo {

	private String dataBase;
	private String tabla;
	private String negocio;
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Este atributo le indica al servicio que el
	//excel que va a leer debe tener el mismo 
	//tipado que la tabla a la cual se va a cargar
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private boolean validarTipoDatos = false;
	
	public PropiedadServicioCargarArchivo(){}

	public PropiedadServicioCargarArchivo(String dataBase, String tabla, String negocio, boolean validarTipoDatos){
		this.dataBase = dataBase;
		this.tabla = tabla;
		this.negocio = negocio;
		this.validarTipoDatos = validarTipoDatos;
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

	public boolean isValidarTipoDatos() {
		return validarTipoDatos;
	}

	public void setValidarTipoDatos(boolean validarTipoDatos) {
		this.validarTipoDatos = validarTipoDatos;
	}

}
