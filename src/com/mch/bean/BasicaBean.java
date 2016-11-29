package com.mch.bean;


/**
 * @author Camilo
 * 12/08/2016
 * 
 * Se mapean las columnas de la tabla
 * BASICA, para crear un insert dinamico
 */
public class BasicaBean {
	
	private String  tabla = "BASICA";
	private	String	chCODIGO			;
	private	String	chNOMBRE			;
	private	String	chCENTRO			;
	private	String	chNIT				;
	private	String	chNIT_TIPO			;
	private	String	dtFECHA_ING			;
	private	String	dtFECHA_RET			;
	private	String	chESTADO_CIA		;
	private	String	chFORMA_PAGO		;
	private	String	chESTADO_CIVIL		;
	private	String	chCARGO				;
	private	String	chDIRECCION			;
	private	String	chTELEFONO			;
	private	String	chSITIO_NAC			;
	private	String	dtFECHA_NAC			;
	private	String	chGENERO			;
	private	String	chTIPO_CONTRATO		;
	private	String	dtFECHA_CNTO		;
	private	String	dtFECHA_VTO			;
	private	String	chFONDO_CESANTIA	;
	private	String	chCODIGO_INTERNO	;
	private	byte	boSINDICALIZADO		;
	private	byte	boPACTO_COLECT		;
	private	String	chCIUDAD_RESIDENCIA	;
	private	String	dtFECHA_CONTINUIDAD	;
	private	String	chPRIMER_APELLIDO	;
	private	String	chSEGUNDO_APELLIDO	;
	private	String	chPRIMER_NOMBRE		;
	private	String	chSEGUNDO_NOMBRE	;
	private	byte	boSABADO_HABIL		;
	private	String	chCIUDAD_CEDULA		;
	private	String	chTIPO_SANGRE		;
	private	String	chVEREDA			;
	private	String	chCELULAR			;
	private	String	chCORREO_ELECTRONICO;
	private	String	chSUCURSAL			;
	private	String	chACTIVIDAD			;
	private	String	chUNIDAD			;
	private	String	chTIPO_EMPLEADO		;
	private	String	chNacionalidad		;


	@Override
	public String toString() {
		return "BasicaBean [" + tabla + ", " + chCODIGO + ", " + chNOMBRE
				+ ", " + chCENTRO + ", " + chNIT + ", " + chNIT_TIPO + ", "
				+ dtFECHA_ING + ", " + dtFECHA_RET + ", " + chESTADO_CIA + ", "
				+ chFORMA_PAGO + ", " + chESTADO_CIVIL + ", " + chCARGO + ", "
				+ chDIRECCION + ", " + chTELEFONO + ", " + chSITIO_NAC + ", "
				+ dtFECHA_NAC + ", " + chGENERO + ", " + chTIPO_CONTRATO + ", "
				+ dtFECHA_CNTO + ", " + dtFECHA_VTO + ", " + chFONDO_CESANTIA
				+ ", " + chCODIGO_INTERNO + ", " + boSINDICALIZADO + ", "
				+ boPACTO_COLECT + ", " + chCIUDAD_RESIDENCIA + ", "
				+ dtFECHA_CONTINUIDAD + ", " + chPRIMER_APELLIDO + ", "
				+ chSEGUNDO_APELLIDO + ", " + chPRIMER_NOMBRE + ", "
				+ chSEGUNDO_NOMBRE + ", " + boSABADO_HABIL + ", "
				+ chCIUDAD_CEDULA + ", " + chTIPO_SANGRE + ", " + chVEREDA
				+ ", " + chCELULAR + ", " + chCORREO_ELECTRONICO + ", "
				+ chSUCURSAL + ", " + chACTIVIDAD + ", " + chUNIDAD + ", "
				+ chTIPO_EMPLEADO + ", " + chNacionalidad + "]";
	}

	public String getChCODIGO() {
		return chCODIGO;
	}

	public BasicaBean setChCODIGO(String chCODIGO) {
		this.chCODIGO = chCODIGO;
		return this;
	}

	public String getChNOMBRE() {
		return chNOMBRE;
	}

	public BasicaBean setChNOMBRE(String chNOMBRE) {
		this.chNOMBRE = chNOMBRE;
		return this;
	}

	public String getChCENTRO() {
		return chCENTRO;
	}

	public BasicaBean setChCENTRO(String chCENTRO) {
		this.chCENTRO = chCENTRO;
		return this;
	}

	public String getChNIT() {
		return chNIT;
	}

	public BasicaBean setChNIT(String chNIT) {
		this.chNIT = chNIT;
		return this;
	}

	public String getChNIT_TIPO() {
		return chNIT_TIPO;
	}

	public BasicaBean setChNIT_TIPO(String chNIT_TIPO) {
		this.chNIT_TIPO = chNIT_TIPO;
		return this;
	}

	public String getDtFECHA_ING() {
		return dtFECHA_ING;
	}

	public BasicaBean setDtFECHA_ING(String dtFECHA_ING) {
		this.dtFECHA_ING = dtFECHA_ING;
		return this;
	}

	public String getDtFECHA_RET() {
		return dtFECHA_RET;
	}

	public BasicaBean setDtFECHA_RET(String dtFECHA_RET) {
		this.dtFECHA_RET = dtFECHA_RET;
		return this;
	}

	public String getChESTADO_CIA() {
		return chESTADO_CIA;
	}

	public BasicaBean setChESTADO_CIA(String chESTADO_CIA) {
		this.chESTADO_CIA = chESTADO_CIA;
		return this;
	}

	public String getChFORMA_PAGO() {
		return chFORMA_PAGO;
	}

	public BasicaBean setChFORMA_PAGO(String chFORMA_PAGO) {
		this.chFORMA_PAGO = chFORMA_PAGO;
		return this;
	}

	public String getChESTADO_CIVIL() {
		return chESTADO_CIVIL;
	}

	public BasicaBean setChESTADO_CIVIL(String chESTADO_CIVIL) {
		this.chESTADO_CIVIL = chESTADO_CIVIL;
		return this;
	}

	public String getChCARGO() {
		return chCARGO;
	}

	public BasicaBean setChCARGO(String chCARGO) {
		this.chCARGO = chCARGO;
		return this;
	}

	public String getChDIRECCION() {
		return chDIRECCION;
	}

	public BasicaBean setChDIRECCION(String chDIRECCION) {
		this.chDIRECCION = chDIRECCION;
		return this;
	}

	public String getChTELEFONO() {
		return chTELEFONO;
	}

	public BasicaBean setChTELEFONO(String chTELEFONO) {
		this.chTELEFONO = chTELEFONO;
		return this;
	}

	public String getChSITIO_NAC() {
		return chSITIO_NAC;
	}

	public BasicaBean setChSITIO_NAC(String chSITIO_NAC) {
		this.chSITIO_NAC = chSITIO_NAC;
		return this;
	}

	public String getDtFECHA_NAC() {
		return dtFECHA_NAC;
	}

	public BasicaBean setDtFECHA_NAC(String dtFECHA_NAC) {
		this.dtFECHA_NAC = dtFECHA_NAC;
		return this;
	}

	public String getChGENERO() {
		return chGENERO;
	}

	public BasicaBean setChGENERO(String chGENERO) {
		this.chGENERO = chGENERO;
		return this;
	}

	public String getChTIPO_CONTRATO() {
		return chTIPO_CONTRATO;
	}

	public BasicaBean setChTIPO_CONTRATO(String chTIPO_CONTRATO) {
		this.chTIPO_CONTRATO = chTIPO_CONTRATO;
		return this;
	}

	public String getDtFECHA_CNTO() {
		return dtFECHA_CNTO;
	}

	public BasicaBean setDtFECHA_CNTO(String dtFECHA_CNTO) {
		this.dtFECHA_CNTO = dtFECHA_CNTO;
		return this;
	}

	public String getDtFECHA_VTO() {
		return dtFECHA_VTO;
	}

	public BasicaBean setDtFECHA_VTO(String dtFECHA_VTO) {
		this.dtFECHA_VTO = dtFECHA_VTO;
		return this;
	}

	public String getChFONDO_CESANTIA() {
		return chFONDO_CESANTIA;
	}

	public BasicaBean setChFONDO_CESANTIA(String chFONDO_CESANTIA) {
		this.chFONDO_CESANTIA = chFONDO_CESANTIA;
		return this;
	}

	public String getChCODIGO_INTERNO() {
		return chCODIGO_INTERNO;
	}

	public BasicaBean setChCODIGO_INTERNO(String chCODIGO_INTERNO) {
		this.chCODIGO_INTERNO = chCODIGO_INTERNO;
		return this;
	}

	public byte getBoSINDICALIZADO() {
		return boSINDICALIZADO;
	}

	public BasicaBean setBoSINDICALIZADO(byte boSINDICALIZADO) {
		this.boSINDICALIZADO = boSINDICALIZADO;
		return this;
	}

	public byte getBoPACTO_COLECT() {
		return boPACTO_COLECT;
	}

	public BasicaBean setBoPACTO_COLECT(byte boPACTO_COLECT) {
		this.boPACTO_COLECT = boPACTO_COLECT;
		return this;
	}

	public String getChCIUDAD_RESIDENCIA() {
		return chCIUDAD_RESIDENCIA;
	}

	public BasicaBean setChCIUDAD_RESIDENCIA(String chCIUDAD_RESIDENCIA) {
		this.chCIUDAD_RESIDENCIA = chCIUDAD_RESIDENCIA;
		return this;
	}

	public String getDtFECHA_CONTINUIDAD() {
		return dtFECHA_CONTINUIDAD;
	}

	public BasicaBean setDtFECHA_CONTINUIDAD(String dtFECHA_CONTINUIDAD) {
		this.dtFECHA_CONTINUIDAD = dtFECHA_CONTINUIDAD;
		return this;
	}

	public String getChPRIMER_APELLIDO() {
		return chPRIMER_APELLIDO;
	}

	public BasicaBean setChPRIMER_APELLIDO(String chPRIMER_APELLIDO) {
		this.chPRIMER_APELLIDO = chPRIMER_APELLIDO;
		return this;
	}

	public String getChSEGUNDO_APELLIDO() {
		return chSEGUNDO_APELLIDO;
	}

	public BasicaBean setChSEGUNDO_APELLIDO(String chSEGUNDO_APELLIDO) {
		this.chSEGUNDO_APELLIDO = chSEGUNDO_APELLIDO;
		return this;
	}

	public String getChPRIMER_NOMBRE() {
		return chPRIMER_NOMBRE;
	}

	public BasicaBean setChPRIMER_NOMBRE(String chPRIMER_NOMBRE) {
		this.chPRIMER_NOMBRE = chPRIMER_NOMBRE;
		return this;
	}

	public String getChSEGUNDO_NOMBRE() {
		return chSEGUNDO_NOMBRE;
	}

	public BasicaBean setChSEGUNDO_NOMBRE(String chSEGUNDO_NOMBRE) {
		this.chSEGUNDO_NOMBRE = chSEGUNDO_NOMBRE;
		return this;
	}

	public byte getBoSABADO_HABIL() {
		return boSABADO_HABIL;
	}

	public BasicaBean setBoSABADO_HABIL(byte boSABADO_HABIL) {
		this.boSABADO_HABIL = boSABADO_HABIL;
		return this;
	}

	public String getChCIUDAD_CEDULA() {
		return chCIUDAD_CEDULA;
	}

	public BasicaBean setChCIUDAD_CEDULA(String chCIUDAD_CEDULA) {
		this.chCIUDAD_CEDULA = chCIUDAD_CEDULA;
		return this;
	}

	public String getChTIPO_SANGRE() {
		return chTIPO_SANGRE;
	}

	public BasicaBean setChTIPO_SANGRE(String chTIPO_SANGRE) {
		this.chTIPO_SANGRE = chTIPO_SANGRE;
		return this;
	}

	public String getChVEREDA() {
		return chVEREDA;
	}

	public BasicaBean setChVEREDA(String chVEREDA) {
		this.chVEREDA = chVEREDA;
		return this;
	}

	public String getChCELULAR() {
		return chCELULAR;
	}

	public BasicaBean setChCELULAR(String chCELULAR) {
		this.chCELULAR = chCELULAR;
		return this;
	}

	public String getChCORREO_ELECTRONICO() {
		return chCORREO_ELECTRONICO;
	}

	public BasicaBean setChCORREO_ELECTRONICO(String chCORREO_ELECTRONICO) {
		this.chCORREO_ELECTRONICO = chCORREO_ELECTRONICO;
		return this;
	}

	public String getChSUCURSAL() {
		return chSUCURSAL;
	}

	public BasicaBean setChSUCURSAL(String chSUCURSAL) {
		this.chSUCURSAL = chSUCURSAL;
		return this;
	}

	public String getChACTIVIDAD() {
		return chACTIVIDAD;
	}

	public BasicaBean setChACTIVIDAD(String chACTIVIDAD) {
		this.chACTIVIDAD = chACTIVIDAD;
		return this;
	}

	public String getChUNIDAD() {
		return chUNIDAD;
	}

	public BasicaBean setChUNIDAD(String chUNIDAD) {
		this.chUNIDAD = chUNIDAD;
		return this;
	}

	public String getChTIPO_EMPLEADO() {
		return chTIPO_EMPLEADO;
	}

	public BasicaBean setChTIPO_EMPLEADO(String chTIPO_EMPLEADO) {
		this.chTIPO_EMPLEADO = chTIPO_EMPLEADO;
		return this;
	}

	public String getChNacionalidad() {
		return chNacionalidad;
	}

	public BasicaBean setChNacionalidad(String chNacionalidad) {
		this.chNacionalidad = chNacionalidad;
		return this;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
	
}