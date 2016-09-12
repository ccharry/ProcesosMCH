package com.mch.configuraciones;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mch.excepciones.ExcepcionMch;

/**
 * @author Camilo
 * 07/09/2016
 */
public class PoolInstanciasConexion {

	private static PoolInstanciasConexion instancia = null;
	private static List<ConfigConnSQLServer> instancias = null;
	private static List<Conexion> conexiones = null;
	private static final int TAMANO_MAXIMO = 10;

	/**
	 * Constructor privado
	 */
	private PoolInstanciasConexion(){
		instancias = new ArrayList<ConfigConnSQLServer>();
		conexiones = new ArrayList<Conexion>();
	}

	/**
	 * Metodo que retorna una unica
	 * instancia de esta clase
	 * @return this
	 */
	public synchronized static PoolInstanciasConexion getInstancia(){
		if(instancia == null){
			instancia = new PoolInstanciasConexion();
		}
		return instancia;
	}

	/**
	 * Metodo que valida si hay una conexion
	 * libre, si la encuentra la retorna, sino
	 * crea una nueva instancia y la retorna, la
	 * finalidad de este metodo es reutilizar las
	 * instancias, con el fin de mantener varias
	 * instancias apuntando a diferentes bases de
	 * datos.
	 * @param DB
	 * @return Conexion
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ExcepcionMch 
	 */
	public synchronized Conexion getConexionLibre(String DB) throws ClassNotFoundException, SQLException, ExcepcionMch{
		for(int c = 0 ; c < instancias.size() ; c++){
			if(instancias.get(c).getDB().equals(DB) && conexiones.get(c).isLibre() == true){
				conexiones.get(c).setLibre(false);
				return conexiones.get(c);
			}
		}
		if((instancias.size()+1) > TAMANO_MAXIMO ){
			throw new ExcepcionMch("Pool LLeno, intente nuevamente.");
		}
		ConfigConnSQLServer c = new ConfigConnSQLServer(DB);
		Conexion con = new Conexion(c.getConexion());
		instancias.add(c);
		conexiones.add(con);
		return con;
	}

	/**
	 * Metodo que retorna la cantidad de
	 * instancias creadas.
	 * @return int
	 */
	public synchronized int getTotalInstanciasEnPool(){
		return instancias.size();
	}
	
	/**
	 * Metodo que cierra todas las conexiones
	 * creadas y elimina las instancias creadas.
	 * @throws SQLException
	 */
	public synchronized void vaciarPool() throws SQLException{
		for(Conexion c: conexiones){
			if(c.getCon().isClosed() == false)
				c.getCon().close();
			c = null;
		}
		conexiones.clear();
		instancias.clear();
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ExcepcionMch {
		PoolInstanciasConexion pic = PoolInstanciasConexion.getInstancia();
		for(int a = 0 ; a < 21 ; a++){
			Conexion d = pic.getConexionLibre("SanRafael");
			System.out.println(d.getCon()+" --- ");
			d.setLibre(true);
		}
		System.out.println(pic.getTotalInstanciasEnPool());
	}
}
