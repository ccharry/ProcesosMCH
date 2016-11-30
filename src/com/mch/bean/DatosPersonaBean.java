package com.mch.bean;

/**
 * @author Camilo
 * 14/07/2016
 */
public class DatosPersonaBean {

	private String chCentro;
	private String chNombre;
	private String chCodigo;
	
	
	@Override
	public String toString() {
		return "DatosPersonaBean [chCentro=" + chCentro + ", chNombre="
				+ chNombre + ", chCodigo=" + chCodigo + "]";
	}
	
	
	public String getChCentro() {
		return chCentro;
	}
	public DatosPersonaBean setChCentro(String chCentro) {
		this.chCentro = chCentro;
		return this;
	}
	public String getChNombre() {
		return chNombre;
	}
	public DatosPersonaBean setChNombre(String chNombre) {
		this.chNombre = chNombre;
		return this;
	}
	public String getChCodigo() {
		return chCodigo;
	}
	public DatosPersonaBean setChCodigo(String chCodigo) {
		this.chCodigo = chCodigo;
		return this;
	}
	
}
