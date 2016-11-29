package com.mch.bean;


/**
 * @author Camilo
 * 12/08/2016
 * Se mapean las columnas de la tabla SEG_SOCIAL, 
 * para crear un insert dinamico.
 */
public class SegSocialBean {

	private String  tabla = "SEG_SOCIAL";
	private	String	chCODIGO	;
	private	String	dtFECHACAMBIO	;
	private	String	chCOTIZANTE	;
	private	String	chEPS	;
	private	String	dtFECHA_CAMBIO_EPS	;
	private	String	chAFP	;
	private	String	dtFECHA_CAMBIO_AFP	;
	private	String	chARP	;
	private	String	dtFECHA_CAMBIO_ARP	;
	private	String	chEPS_ANTERIOR	;
	private	String	chAFP_ANTERIOR	;
	private	String	chARP_ANTERIOR	;
	private	byte	boCONTINUIDAD_DOCENTE	;
	private	String	chTIPO_PENSION	;
	private	String	chTIPO_PENSIONADO	;
	private	byte	boPENSION_COMPARTIDA	;
	private	byte	boRESIDE_EXTERIOR	;
	private	byte	boPAGO_EXTERIOR	;
	private	String	chSUBTIPO_COTIZANTE	;
	private	String	chCAJA_COMPENSACION	;
	private	String	chFACTOR_RIESGOS	;
	private	String	dtFECHA_CAMBIO_RIESGO	;
	private	String	chFACTOR_RIESGOS_ANTERIOR	;
	private	int	inCONSECUTIVO	;
	private	int	inExtranjero	;


	
	@Override
	public String toString() {
		return "SegSocialBean [" + tabla + ", " + chCODIGO + ", "
				+ dtFECHACAMBIO + ", " + chCOTIZANTE + ", " + chEPS + ", "
				+ dtFECHA_CAMBIO_EPS + ", " + chAFP + ", " + dtFECHA_CAMBIO_AFP
				+ ", " + chARP + ", " + dtFECHA_CAMBIO_ARP + ", "
				+ chEPS_ANTERIOR + ", " + chAFP_ANTERIOR + ", "
				+ chARP_ANTERIOR + ", " + boCONTINUIDAD_DOCENTE + ", "
				+ chTIPO_PENSION + ", " + chTIPO_PENSIONADO + ", "
				+ boPENSION_COMPARTIDA + ", " + boRESIDE_EXTERIOR + ", "
				+ boPAGO_EXTERIOR + ", " + chSUBTIPO_COTIZANTE + ", "
				+ chCAJA_COMPENSACION + ", " + chFACTOR_RIESGOS + ", "
				+ dtFECHA_CAMBIO_RIESGO + ", " + chFACTOR_RIESGOS_ANTERIOR
				+ ", " + inCONSECUTIVO + ", " + inExtranjero + "]";
	}
	
	public String getChCODIGO() {
		return chCODIGO;
	}
	public SegSocialBean setChCODIGO(String chCODIGO) {
		this.chCODIGO = chCODIGO;
		return this;
	}
	public String getDtFECHACAMBIO() {
		return dtFECHACAMBIO;
	}
	public SegSocialBean setDtFECHACAMBIO(String dtFECHACAMBIO) {
		this.dtFECHACAMBIO = dtFECHACAMBIO;
		return this;
	}
	public String getChCOTIZANTE() {
		return chCOTIZANTE;
	}
	public SegSocialBean setChCOTIZANTE(String chCOTIZANTE) {
		this.chCOTIZANTE = chCOTIZANTE;
		return this;
	}
	public String getChEPS() {
		return chEPS;
	}
	public SegSocialBean setChEPS(String chEPS) {
		this.chEPS = chEPS;
		return this;
	}
	public String getDtFECHA_CAMBIO_EPS() {
		return dtFECHA_CAMBIO_EPS;
	}
	public SegSocialBean setDtFECHA_CAMBIO_EPS(String dtFECHA_CAMBIO_EPS) {
		this.dtFECHA_CAMBIO_EPS = dtFECHA_CAMBIO_EPS;
		return this;
	}
	public String getChAFP() {
		return chAFP;
	}
	public SegSocialBean setChAFP(String chAFP) {
		this.chAFP = chAFP;
		return this;
	}
	public String getDtFECHA_CAMBIO_AFP() {
		return dtFECHA_CAMBIO_AFP;
	}
	public SegSocialBean setDtFECHA_CAMBIO_AFP(String dtFECHA_CAMBIO_AFP) {
		this.dtFECHA_CAMBIO_AFP = dtFECHA_CAMBIO_AFP;
		return this;
	}
	public String getChARP() {
		return chARP;
	}
	public SegSocialBean setChARP(String chARP) {
		this.chARP = chARP;
		return this;
	}
	public String getDtFECHA_CAMBIO_ARP() {
		return dtFECHA_CAMBIO_ARP;
	}
	public SegSocialBean setDtFECHA_CAMBIO_ARP(String dtFECHA_CAMBIO_ARP) {
		this.dtFECHA_CAMBIO_ARP = dtFECHA_CAMBIO_ARP;
		return this;
	}
	public String getChEPS_ANTERIOR() {
		return chEPS_ANTERIOR;
	}
	public SegSocialBean setChEPS_ANTERIOR(String chEPS_ANTERIOR) {
		this.chEPS_ANTERIOR = chEPS_ANTERIOR;
		return this;
	}
	public String getChAFP_ANTERIOR() {
		return chAFP_ANTERIOR;
	}
	public SegSocialBean setChAFP_ANTERIOR(String chAFP_ANTERIOR) {
		this.chAFP_ANTERIOR = chAFP_ANTERIOR;
		return this;
	}
	public String getChARP_ANTERIOR() {
		return chARP_ANTERIOR;
	}
	public SegSocialBean setChARP_ANTERIOR(String chARP_ANTERIOR) {
		this.chARP_ANTERIOR = chARP_ANTERIOR;
		return this;
	}
	public byte getBoCONTINUIDAD_DOCENTE() {
		return boCONTINUIDAD_DOCENTE;
	}
	public SegSocialBean setBoCONTINUIDAD_DOCENTE(byte boCONTINUIDAD_DOCENTE) {
		this.boCONTINUIDAD_DOCENTE = boCONTINUIDAD_DOCENTE;
		return this;
	}
	public String getChTIPO_PENSION() {
		return chTIPO_PENSION;
	}
	public SegSocialBean setChTIPO_PENSION(String chTIPO_PENSION) {
		this.chTIPO_PENSION = chTIPO_PENSION;
		return this;
	}
	public String getChTIPO_PENSIONADO() {
		return chTIPO_PENSIONADO;
	}
	public SegSocialBean setChTIPO_PENSIONADO(String chTIPO_PENSIONADO) {
		this.chTIPO_PENSIONADO = chTIPO_PENSIONADO;
		return this;
	}
	public byte getBoPENSION_COMPARTIDA() {
		return boPENSION_COMPARTIDA;
	}
	public SegSocialBean setBoPENSION_COMPARTIDA(byte boPENSION_COMPARTIDA) {
		this.boPENSION_COMPARTIDA = boPENSION_COMPARTIDA;
		return this;
	}
	public byte getBoRESIDE_EXTERIOR() {
		return boRESIDE_EXTERIOR;
	}
	public SegSocialBean setBoRESIDE_EXTERIOR(byte boRESIDE_EXTERIOR) {
		this.boRESIDE_EXTERIOR = boRESIDE_EXTERIOR;
		return this;
	}
	public byte getBoPAGO_EXTERIOR() {
		return boPAGO_EXTERIOR;
	}
	public SegSocialBean setBoPAGO_EXTERIOR(byte boPAGO_EXTERIOR) {
		this.boPAGO_EXTERIOR = boPAGO_EXTERIOR;
		return this;
	}
	public String getChSUBTIPO_COTIZANTE() {
		return chSUBTIPO_COTIZANTE;
	}
	public SegSocialBean setChSUBTIPO_COTIZANTE(String chSUBTIPO_COTIZANTE) {
		this.chSUBTIPO_COTIZANTE = chSUBTIPO_COTIZANTE;
		return this;
	}
	public String getChCAJA_COMPENSACION() {
		return chCAJA_COMPENSACION;
	}
	public SegSocialBean setChCAJA_COMPENSACION(String chCAJA_COMPENSACION) {
		this.chCAJA_COMPENSACION = chCAJA_COMPENSACION;
		return this;
	}
	public String getChFACTOR_RIESGOS() {
		return chFACTOR_RIESGOS;
	}
	public SegSocialBean setChFACTOR_RIESGOS(String chFACTOR_RIESGOS) {
		this.chFACTOR_RIESGOS = chFACTOR_RIESGOS;
		return this;
	}
	public String getDtFECHA_CAMBIO_RIESGO() {
		return dtFECHA_CAMBIO_RIESGO;
	}
	public SegSocialBean setDtFECHA_CAMBIO_RIESGO(String dtFECHA_CAMBIO_RIESGO) {
		this.dtFECHA_CAMBIO_RIESGO = dtFECHA_CAMBIO_RIESGO;
		return this;
	}
	public String getChFACTOR_RIESGOS_ANTERIOR() {
		return chFACTOR_RIESGOS_ANTERIOR;
	}
	public SegSocialBean setChFACTOR_RIESGOS_ANTERIOR(String chFACTOR_RIESGOS_ANTERIOR) {
		this.chFACTOR_RIESGOS_ANTERIOR = chFACTOR_RIESGOS_ANTERIOR;
		return this;
	}
	public int getInCONSECUTIVO() {
		return inCONSECUTIVO;
	}
	public SegSocialBean setInCONSECUTIVO(int inCONSECUTIVO) {
		this.inCONSECUTIVO = inCONSECUTIVO;
		return this;
	}
	public int getInExtranjero() {
		return inExtranjero;
	}
	public SegSocialBean setInExtranjero(int inExtranjero) {
		this.inExtranjero = inExtranjero;
		return this;
	}
	public String getTabla() {
		return tabla;
	}
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
}
