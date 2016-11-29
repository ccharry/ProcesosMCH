package com.mch.bean;
/**
 * @author Camilo
 * 12/08/2016
 * Se mapean las columnas de la tabla BANCOS, 
 * para crear un insert dinamico.
 */
public class BancosBean {
	
	private String  tabla = "BANCOS";
	private	String	chCODIGO	;
	private	String	dtFECHA_ASIGNACION	;
	private	String	chCUENTA_NUMERO	;
	private	String	chCODIGO_BANCO_CIA	;
	private	String	chCODIGO_BANCO_EMPLEADO	;
	private	String	chTIPO_CUENTA	;
	private	String	chCLASE_CUENTA	;
	private	byte	boESTADO_CUENTA	;
	

	@Override
	public String toString() {
		return "BancosBean [" + tabla + ", " + chCODIGO + ", "
				+ dtFECHA_ASIGNACION + ", " + chCUENTA_NUMERO + ", "
				+ chCODIGO_BANCO_CIA + ", " + chCODIGO_BANCO_EMPLEADO + ", "
				+ chTIPO_CUENTA + ", " + chCLASE_CUENTA + ", "
				+ boESTADO_CUENTA + "]";
	}

	public String getChCODIGO() {
		return chCODIGO;
	}

	public BancosBean setChCODIGO(String chCODIGO) {
		this.chCODIGO = chCODIGO;
		return this;
	}

	public String getDtFECHA_ASIGNACION() {
		return dtFECHA_ASIGNACION;
	}

	public BancosBean setDtFECHA_ASIGNACION(String dtFECHA_ASIGNACION) {
		this.dtFECHA_ASIGNACION = dtFECHA_ASIGNACION;
		return this;
	}

	public String getChCUENTA_NUMERO() {
		return chCUENTA_NUMERO;
	}

	public BancosBean setChCUENTA_NUMERO(String chCUENTA_NUMERO) {
		this.chCUENTA_NUMERO = chCUENTA_NUMERO;
		return this;
	}

	public String getChCODIGO_BANCO_CIA() {
		return chCODIGO_BANCO_CIA;
	}

	public BancosBean setChCODIGO_BANCO_CIA(String chCODIGO_BANCO_CIA) {
		this.chCODIGO_BANCO_CIA = chCODIGO_BANCO_CIA;
		return this;
	}

	public String getChCODIGO_BANCO_EMPLEADO() {
		return chCODIGO_BANCO_EMPLEADO;
	}

	public BancosBean setChCODIGO_BANCO_EMPLEADO(String chCODIGO_BANCO_EMPLEADO) {
		this.chCODIGO_BANCO_EMPLEADO = chCODIGO_BANCO_EMPLEADO;
		return this;
	}

	public String getChTIPO_CUENTA() {
		return chTIPO_CUENTA;
	}

	public BancosBean setChTIPO_CUENTA(String chTIPO_CUENTA) {
		this.chTIPO_CUENTA = chTIPO_CUENTA;
		return this;
	}

	public String getChCLASE_CUENTA() {
		return chCLASE_CUENTA;
	}

	public BancosBean setChCLASE_CUENTA(String chCLASE_CUENTA) {
		this.chCLASE_CUENTA = chCLASE_CUENTA;
		return this;
	}

	public byte getBoESTADO_CUENTA() {
		return boESTADO_CUENTA;
	}

	public BancosBean setBoESTADO_CUENTA(byte boESTADO_CUENTA) {
		this.boESTADO_CUENTA = boESTADO_CUENTA;
		return this;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
}
