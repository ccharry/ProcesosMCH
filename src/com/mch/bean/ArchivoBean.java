package com.mch.bean;

import java.io.File;

/**
 * @author Camilo
 * 05/09/2016
 */
public class ArchivoBean {

	private String nombreArchivo;
	private File archivo;

	public ArchivoBean(File archivo){
		this.nombreArchivo = archivo.getName();
		this.archivo = archivo;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public File getArchivo() {
		return archivo;
	}
	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}
}
