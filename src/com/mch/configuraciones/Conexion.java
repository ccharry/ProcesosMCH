package com.mch.configuraciones;

import java.sql.Connection;

/**
 * @author Camilo
 * 07/09/2016
 */
public class Conexion {
	
	/*
	 * Conexion unica por cada instancia
	 * creada de esta clase.
	 */
	private Connection con = null;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Variable que indica la instancia
	// 
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private boolean libre = true;

	public Conexion(Connection con){
		this.con = con;
	}

	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	}
	public boolean isLibre() {
		return libre;
	}
	public void setLibre(boolean libre) {
		this.libre = libre;
	}
}
