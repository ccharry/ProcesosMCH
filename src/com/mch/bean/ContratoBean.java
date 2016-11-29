package com.mch.bean;
/**
 * @author Camilo
 * 12/08/2016
 * Se mapean las columnas de la tabla CONTRATO, 
 * para crear un insert dinamico.
 */
public class ContratoBean {
	
	private String  tabla = "CONTRATO";
	private	String	chCODIGO	;
	private	String	dtFECHA_INGRESO	;
	private	String	dtFECHA_RETIRO	;
	private	String	chTIPO_CONTRATO	;
	private	String	chNUMERO_CONTRATO	;
	private	String	chOBSERVACION_1	;
	private	String	chOBSERVACION_2	;
	

	@Override
	public String toString() {
		return "ContratoBean [" + tabla + ", " + chCODIGO + ", "
				+ dtFECHA_INGRESO + ", " + dtFECHA_RETIRO + ", "
				+ chTIPO_CONTRATO + ", " + chNUMERO_CONTRATO + ", "
				+ chOBSERVACION_1 + ", " + chOBSERVACION_2 + "]";
	}

	public String getChCODIGO() {
		return chCODIGO;
	}

	public ContratoBean setChCODIGO(String chCODIGO) {
		this.chCODIGO = chCODIGO;
		return this;
	}

	public String getDtFECHA_INGRESO() {
		return dtFECHA_INGRESO;
	}

	public ContratoBean setDtFECHA_INGRESO(String dtFECHA_INGRESO) {
		this.dtFECHA_INGRESO = dtFECHA_INGRESO;
		return this;
	}

	public String getDtFECHA_RETIRO() {
		return dtFECHA_RETIRO;
	}

	public ContratoBean setDtFECHA_RETIRO(String dtFECHA_RETIRO) {
		this.dtFECHA_RETIRO = dtFECHA_RETIRO;
		return this;
	}

	public String getChTIPO_CONTRATO() {
		return chTIPO_CONTRATO;
	}

	public ContratoBean setChTIPO_CONTRATO(String chTIPO_CONTRATO) {
		this.chTIPO_CONTRATO = chTIPO_CONTRATO;
		return this;
	}

	public String getChNUMERO_CONTRATO() {
		return chNUMERO_CONTRATO;
	}

	public ContratoBean setChNUMERO_CONTRATO(String chNUMERO_CONTRATO) {
		this.chNUMERO_CONTRATO = chNUMERO_CONTRATO;
		return this;
	}

	public String getChOBSERVACION_1() {
		return chOBSERVACION_1;
	}

	public ContratoBean setChOBSERVACION_1(String chOBSERVACION_1) {
		this.chOBSERVACION_1 = chOBSERVACION_1;
		return this;
	}

	public String getChOBSERVACION_2() {
		return chOBSERVACION_2;
	}

	public ContratoBean setChOBSERVACION_2(String chOBSERVACION_2) {
		this.chOBSERVACION_2 = chOBSERVACION_2;
		return this;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
}
