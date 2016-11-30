package com.mch.bean;
/**
 * @author Camilo
 * 15/07/2016
 */
public class NovedadHorasExtrasUnidades {
	
	private String tabla = "NOVEDADES_LIQ";
	private String chLiquidacion = null;
	private String chCodigo = null;
	private String chCentro = null;
	private String chCentroLiquidacion = null;
	private int chActividad = 0;
	private int chUnidad = 0;
	private String dtFecha_Novedad = null;
	private int chCodigo_Concepto = 0;
	private int dbUnidades = 0;
	private Double dbValor = 0.0;
	private int chControl = 0;
	private String chUsuario  = null;
	private String dtFechaLiquidacion = null;
	private int chNumero_Contrato = 0;
	private String chIdentificador = null;
	private String chPeriodo = null;
	private int chtipoEmpleado = 1;


	@Override
	public String toString() {
		return "NovedadHorasExtrasUnidades [tabla=" + tabla
				+ ", chLiquidacion=" + chLiquidacion + ", chCodigo=" + chCodigo
				+ ", chCentro=" + chCentro + ", chCentroLiquidacion="
				+ chCentroLiquidacion + ", chActividad=" + chActividad
				+ ", chUnidad=" + chUnidad + ", dtFecha_Novedad="
				+ dtFecha_Novedad + ", chCodigo_Concepto=" + chCodigo_Concepto
				+ ", dbUnidades=" + dbUnidades + ", dbValor=" + dbValor
				+ ", chControl=" + chControl + ", chUsuario=" + chUsuario
				+ ", dtFechaLiquidacion=" + dtFechaLiquidacion
				+ ", chNumero_Contrato=" + chNumero_Contrato
				+ ", chIdentificador=" + chIdentificador + ", chPeriodo="
				+ chPeriodo + ", chtipoEmpleado=" + chtipoEmpleado + "]";
	}
	
	public String getChLiquidacion() {
		return chLiquidacion;
	}
	public NovedadHorasExtrasUnidades setChLiquidacion(String chLiquidacion) {
		this.chLiquidacion = chLiquidacion;
		return this;
	}
	public String getChCodigo() {
		return chCodigo;
	}
	public NovedadHorasExtrasUnidades setChCodigo(String chCodigo) {
		this.chCodigo = chCodigo;
		return this;
	}
	public String getChCentro() {
		return chCentro;
	}
	public NovedadHorasExtrasUnidades setChCentro(String chCentro) {
		this.chCentro = chCentro;
		return this;
	}
	public String getChCentroLiquidacion() {
		return chCentroLiquidacion;
	}
	public NovedadHorasExtrasUnidades setChCentroLiquidacion(String chCentroLiquidacion) {
		this.chCentroLiquidacion = chCentroLiquidacion;
		return this;
	}
	public int getChActividad() {
		return chActividad;
	}
	public NovedadHorasExtrasUnidades setChActividad(int chActividad) {
		this.chActividad = chActividad;
		return this;
	}
	public int getChUnidad() {
		return chUnidad;
	}
	public NovedadHorasExtrasUnidades setChUnidad(int chUnidad) {
		this.chUnidad = chUnidad;
		return this;
	}
	public String getDtFecha_Novedad() {
		return dtFecha_Novedad;
	}
	public NovedadHorasExtrasUnidades setDtFecha_Novedad(String dtFecha_Novedad) {
		this.dtFecha_Novedad = dtFecha_Novedad;
		return this;
	}
	public int getChCodigo_Concepto() {
		return chCodigo_Concepto;
	}
	public NovedadHorasExtrasUnidades setChCodigo_Concepto(int chCodigo_Concepto) {
		this.chCodigo_Concepto = chCodigo_Concepto;
		return this;
	}
	public int getDbUnidades() {
		return dbUnidades;
	}
	public NovedadHorasExtrasUnidades setDbUnidades(int dbUnidades) {
		this.dbUnidades = dbUnidades;
		return this;
	}
	public Double getDbValor() {
		return dbValor;
	}
	public NovedadHorasExtrasUnidades setDbValor(Double dbValor) {
		this.dbValor = dbValor;
		return this;
	}
	public int getChControl() {
		return chControl;
	}
	public NovedadHorasExtrasUnidades setChControl(int chControl) {
		this.chControl = chControl;
		return this;
	}
	public String getChUsuario() {
		return chUsuario;
	}
	public NovedadHorasExtrasUnidades setChUsuario(String chUsuario) {
		this.chUsuario = chUsuario;
		return this;
	}
	public String getDtFechaLiquidacion() {
		return dtFechaLiquidacion;
	}
	public NovedadHorasExtrasUnidades setDtFechaLiquidacion(String dtFechaLiquidacion) {
		this.dtFechaLiquidacion = dtFechaLiquidacion;
		return this;
	}
	public int getChNumero_Contrato() {
		return chNumero_Contrato;
	}
	public NovedadHorasExtrasUnidades setChNumero_Contrato(int chNumero_Contrato) {
		this.chNumero_Contrato = chNumero_Contrato;
		return this;
	}
	public String getChIdentificador() {
		return chIdentificador;
	}
	public NovedadHorasExtrasUnidades setChIdentificador(String chIdentificador) {
		this.chIdentificador = chIdentificador;
		return this;
	}
	public String getChPeriodo() {
		return chPeriodo;
	}
	public NovedadHorasExtrasUnidades setChPeriodo(String chPeriodo) {
		this.chPeriodo = chPeriodo;
		return this;
	}
	public int getChtipoEmpleado() {
		return chtipoEmpleado;
	}
	public NovedadHorasExtrasUnidades setChtipoEmpleado(int chtipoEmpleado) {
		this.chtipoEmpleado = chtipoEmpleado;
		return this;
	}
}
