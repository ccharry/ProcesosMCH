package com.mch.bean;


/**
 * @author Camilo
 * 16/08/2016
 * Se mapean las columnas de la tabla CPTOS_HIST_AU, 
 * para crear un insert dinamico.
 */
public class CptosHistAuBean {
	
	private String  tabla = "CPTOS_HIST_AU";
	private	String	chCODIGO	;
	private	String	chCODIGO_CONCEPTO	;
	private	String	dtFECHA_INCREMENTO	;
	private	String	chIDENTIFICADOR	;
	private	String	chFRECUENCIA	;
	private	byte	boVALOR_PORCENTAJE	;
	private	String	chREFERENCIA	;
	private	String	dbVALOR_PAGO	;
	private	float	dbSALDO	;
	private	String	chTIPO_EMPLEADO	;
	private	String	dtFECHA_NOVEDAD	;

	@Override
	public String toString() {
		return "CptosHistAuBean [" + tabla + ", " + chCODIGO + ", "
				+ chCODIGO_CONCEPTO + ", " + dtFECHA_INCREMENTO + ", "
				+ chIDENTIFICADOR + ", " + chFRECUENCIA + ", "
				+ boVALOR_PORCENTAJE + ", " + chREFERENCIA + ", "
				+ dbVALOR_PAGO + ", " + dbSALDO + ", " + chTIPO_EMPLEADO + ", "
				+ dtFECHA_NOVEDAD + "]";
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getChCODIGO() {
		return chCODIGO;
	}

	public CptosHistAuBean setChCODIGO(String chCODIGO) {
		this.chCODIGO = chCODIGO;
		return this;
	}

	public String getChCODIGO_CONCEPTO() {
		return chCODIGO_CONCEPTO;
	}

	public CptosHistAuBean setChCODIGO_CONCEPTO(String chCODIGO_CONCEPTO) {
		this.chCODIGO_CONCEPTO = chCODIGO_CONCEPTO;
		return this;
	}

	public String getDtFECHA_INCREMENTO() {
		return dtFECHA_INCREMENTO;
	}

	public CptosHistAuBean setDtFECHA_INCREMENTO(String dtFECHA_INCREMENTO) {
		this.dtFECHA_INCREMENTO = dtFECHA_INCREMENTO;
		return this;
	}

	public String getChIDENTIFICADOR() {
		return chIDENTIFICADOR;
	}

	public CptosHistAuBean setChIDENTIFICADOR(String chIDENTIFICADOR) {
		this.chIDENTIFICADOR = chIDENTIFICADOR;
		return this;
	}

	public String getChFRECUENCIA() {
		return chFRECUENCIA;
	}

	public CptosHistAuBean setChFRECUENCIA(String chFRECUENCIA) {
		this.chFRECUENCIA = chFRECUENCIA;
		return this;
	}

	public byte getBoVALOR_PORCENTAJE() {
		return boVALOR_PORCENTAJE;
	}

	public CptosHistAuBean setBoVALOR_PORCENTAJE(byte boVALOR_PORCENTAJE) {
		this.boVALOR_PORCENTAJE = boVALOR_PORCENTAJE;
		return this;
	}

	public String getChREFERENCIA() {
		return chREFERENCIA;
	}

	public CptosHistAuBean setChREFERENCIA(String chREFERENCIA) {
		this.chREFERENCIA = chREFERENCIA;
		return this;
	}

	public String getDbVALOR_PAGO() {
		return dbVALOR_PAGO;
	}

	public CptosHistAuBean setDbVALOR_PAGO(String dbVALOR_PAGO) {
		this.dbVALOR_PAGO = dbVALOR_PAGO;
		return this;
	}

	public float getDbSALDO() {
		return dbSALDO;
	}

	public CptosHistAuBean setDbSALDO(float dbSALDO) {
		this.dbSALDO = dbSALDO;
		return this;
	}

	public String getChTIPO_EMPLEADO() {
		return chTIPO_EMPLEADO;
	}

	public CptosHistAuBean setChTIPO_EMPLEADO(String chTIPO_EMPLEADO) {
		this.chTIPO_EMPLEADO = chTIPO_EMPLEADO;
		return this;
	}

	public String getDtFECHA_NOVEDAD() {
		return dtFECHA_NOVEDAD;
	}

	public CptosHistAuBean setDtFECHA_NOVEDAD(String dtFECHA_NOVEDAD) {
		this.dtFECHA_NOVEDAD = dtFECHA_NOVEDAD;
		return this;
	}

}