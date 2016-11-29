package com.mch.bean;

/**
 * @author Camilo
 * 12/08/2016
 * Se mapean las columnas de la tabla TRIBUTARIA, 
 * para crear un insert dinamico.
 */
public class TributariaBean {
	
	private String  tabla = "TRIBUTARIA";
	private	String	chCODIGO	;
	private	String	chPROC_RETENCION	;
	private	float	dbVALOR_AFC	;
	private	float	dbVALOR_INTERES_VIVIENDA	;
	private	String	chPORCENTAJE_FIJO	;
	private	float	dbVALOR_EDUCACION	;
	private	float	dbVALOR_AFP	;
	private	float	dbEXENTO30	;
	private	float	dbEXENTO15	;
	private	float	dbPROMEDIO	;
	private	float	dbPROMEDIO_ANO_ANTERIOR	;
	private	float	dbVALOR_DEP_A_CARGO	;
	private	byte	boESCONTRIBUYENTE	;
	

	@Override
	public String toString() {
		return "TributariaBean [" + tabla + ", " + chCODIGO + ", "
				+ chPROC_RETENCION + ", " + dbVALOR_AFC + ", "
				+ dbVALOR_INTERES_VIVIENDA + ", " + chPORCENTAJE_FIJO + ", "
				+ dbVALOR_EDUCACION + ", " + dbVALOR_AFP + ", " + dbEXENTO30
				+ ", " + dbEXENTO15 + ", " + dbPROMEDIO + ", "
				+ dbPROMEDIO_ANO_ANTERIOR + ", " + dbVALOR_DEP_A_CARGO + ", "
				+ boESCONTRIBUYENTE + "]";
	}

	public String getChCODIGO() {
		return chCODIGO;
	}

	public TributariaBean setChCODIGO(String chCODIGO) {
		this.chCODIGO = chCODIGO;
		return this;
	}

	public String getChPROC_RETENCION() {
		return chPROC_RETENCION;
	}

	public TributariaBean setChPROC_RETENCION(String chPROC_RETENCION) {
		this.chPROC_RETENCION = chPROC_RETENCION;
		return this;
	}

	public float getDbVALOR_AFC() {
		return dbVALOR_AFC;
	}

	public TributariaBean setDbVALOR_AFC(float dbVALOR_AFC) {
		this.dbVALOR_AFC = dbVALOR_AFC;
		return this;
	}

	public float getDbVALOR_INTERES_VIVIENDA() {
		return dbVALOR_INTERES_VIVIENDA;
	}

	public TributariaBean setDbVALOR_INTERES_VIVIENDA(float dbVALOR_INTERES_VIVIENDA) {
		this.dbVALOR_INTERES_VIVIENDA = dbVALOR_INTERES_VIVIENDA;
		return this;
	}

	public String getChPORCENTAJE_FIJO() {
		return chPORCENTAJE_FIJO;
	}

	public TributariaBean setChPORCENTAJE_FIJO(String chPORCENTAJE_FIJO) {
		this.chPORCENTAJE_FIJO = chPORCENTAJE_FIJO;
		return this;
	}

	public float getDbVALOR_EDUCACION() {
		return dbVALOR_EDUCACION;
	}

	public TributariaBean setDbVALOR_EDUCACION(float dbVALOR_EDUCACION) {
		this.dbVALOR_EDUCACION = dbVALOR_EDUCACION;
		return this;
	}

	public float getDbVALOR_AFP() {
		return dbVALOR_AFP;
	}

	public TributariaBean setDbVALOR_AFP(float dbVALOR_AFP) {
		this.dbVALOR_AFP = dbVALOR_AFP;
		return this;
	}

	public float getDbEXENTO30() {
		return dbEXENTO30;
	}

	public TributariaBean setDbEXENTO30(float dbEXENTO30) {
		this.dbEXENTO30 = dbEXENTO30;
		return this;
	}

	public float getDbEXENTO15() {
		return dbEXENTO15;
	}

	public TributariaBean setDbEXENTO15(float dbEXENTO15) {
		this.dbEXENTO15 = dbEXENTO15;
		return this;
	}

	public float getDbPROMEDIO() {
		return dbPROMEDIO;
	}

	public TributariaBean setDbPROMEDIO(float dbPROMEDIO) {
		this.dbPROMEDIO = dbPROMEDIO;
		return this;
	}

	public float getDbPROMEDIO_ANO_ANTERIOR() {
		return dbPROMEDIO_ANO_ANTERIOR;
	}

	public TributariaBean setDbPROMEDIO_ANO_ANTERIOR(float dbPROMEDIO_ANO_ANTERIOR) {
		this.dbPROMEDIO_ANO_ANTERIOR = dbPROMEDIO_ANO_ANTERIOR;
		return this;
	}

	public float getDbVALOR_DEP_A_CARGO() {
		return dbVALOR_DEP_A_CARGO;
	}

	public TributariaBean setDbVALOR_DEP_A_CARGO(float dbVALOR_DEP_A_CARGO) {
		this.dbVALOR_DEP_A_CARGO = dbVALOR_DEP_A_CARGO;
		return this;
	}

	public byte getBoESCONTRIBUYENTE() {
		return boESCONTRIBUYENTE;
	}

	public TributariaBean setBoESCONTRIBUYENTE(byte boESCONTRIBUYENTE) {
		this.boESCONTRIBUYENTE = boESCONTRIBUYENTE;
		return this;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
}