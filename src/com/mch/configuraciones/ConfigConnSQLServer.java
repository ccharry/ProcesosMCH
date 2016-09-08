package com.mch.configuraciones;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * 
 * @author Camilo
 * 03/05/2016
 * Clase singleton que posee un metodo que retorna una conexion
 *
 */
public class ConfigConnSQLServer {
	private static ConfigConnSQLServer INSTANCIA_ = null;
	//	private static Connection CONN_ = null;

	private SQLServerDataSource dsBD1;
	/**
	 * Constructor Privado
	 */
	private ConfigConnSQLServer(){
		dsBD1 = new SQLServerDataSource();
		dsBD1.setUser("sa");
		dsBD1.setPassword("1qazXSW2");
		dsBD1.setServerName("192.168.1.18");
		dsBD1.setPortNumber(1433);
		dsBD1.setDatabaseName("SanRafael");
	}


	/**
	 * Metodo que crea una instacia si no se ha creado,
	 * si ya se creo, retorna la instancia existente
	 * @return this
	 */
	public synchronized static ConfigConnSQLServer getInstancia(){
		if(INSTANCIA_ == null){
			INSTANCIA_ = new ConfigConnSQLServer();
		}
		return INSTANCIA_;
	}

	/**
	 * Metodo que retorna la conexion a la DB
	 * @return Connection conexion a la DB
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public synchronized Connection getConexion() throws ClassNotFoundException, SQLException{
		Logger.getLogger(getClass().getName()).log( Level.INFO, "=> URL : "+dsBD1.getDatabaseName());
		return  dsBD1.getConnection();
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection con = ConfigConnSQLServer.getInstancia().getConexion();
		System.out.println(con.getClientInfo());
	}

}
