package com.mch.propiedades.servicios; 
/**
 * @author Camilo
 * 12/09/2016
 */
public class PropiedadServicioInvocarProcedimiento {

	private String procedimiento;
	private String parametros;
	private String dataBase;
	private String negocio;
	
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
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
