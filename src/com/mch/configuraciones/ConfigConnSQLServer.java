package com.mch.configuraciones;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
/**
 * @author Camilo
 * 03/05/2016
 * Clase que posee un metodo que retorna una conexion
 */
public class ConfigConnSQLServer {

	private String DB = null;
	private SQLServerDataSource dsBD1 = null;

	public ConfigConnSQLServer(String DB){
		dsBD1 = new SQLServerDataSource();
		dsBD1.setUser("sa");
		dsBD1.setPassword("1qazXSW2");
		dsBD1.setServerName("192.168.1.18");
		dsBD1.setPortNumber(1433);
		dsBD1.setDatabaseName(DB);
		this.DB = DB;
	}

	/**
	 * Metodo que retorna la conexion a la DB
	 * @return Connection conexion a la DB
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public  Connection getConexion() throws ClassNotFoundException, SQLException{
		Logger.getLogger(getClass().getName()).log( Level.INFO, "=> URL : "+dsBD1.getDatabaseName());
		return  dsBD1.getConnection();
	}

	public String getDB() {
		return DB;
	}
	public void setDB(String dB) {
		DB = dB;
	}
}
