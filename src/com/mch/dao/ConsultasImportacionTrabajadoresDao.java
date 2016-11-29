package com.mch.dao;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mch.actividades.ActividadEjecutarConsulta;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadesServicioEjecutarConsulta;

/**
 * @author Camilo
 * 29/11/20166
 */
public class ConsultasImportacionTrabajadoresDao {

	private String SQL = "";
	private ActividadEjecutarConsulta actividadEjecutarConsulta = new ActividadEjecutarConsulta();
	private PropiedadesServicioEjecutarConsulta prop = new PropiedadesServicioEjecutarConsulta("", "", "");
	private JSONArray resultado = null;

	/**
	 * Metodo que valida que el centro de costo es valido.
	 * @param centro
	 * @return boolean
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ExcepcionMch 
	 */
	public boolean validarCentroCosto(String centro, String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{

		SQL = "SELECT COUNT(*)r FROM CENTRO_COSTO WHERE CHCENTRO = '"+centro+"'";
		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);
		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		JSONObject r = resultado.getJSONObject(0); 
		if(!r.isNull("error"))
			throw new ExcepcionMch("Ha ocurrido un error al ejecutar la consulta: "+r.getString("mensaje"));

		if(r.getInt("r") == 1)
			return true;

		return false;
	}

	/**
	 * Metodo que valida que el cargo sea valido.
	 * @param cargo
	 * @return boolean
	 * @throws ExcepcionMch 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public boolean validarCargo(String cargo, String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		SQL = "SELECT COUNT(*)r FROM CARGOS WHERE CHCARGO = '"+cargo+"'";
		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);

		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		JSONObject r = resultado.getJSONObject(0); 
		if(!r.isNull("error"))
			throw new ExcepcionMch("Ha ocurrido un error al ejecutar la consulta: "+r.getString("mensaje"));

		if(r.getInt("r") == 1)
			return true;

		return false;
	}

	/**
	 * Metodo que valida que el codigo de la ciudad sea valido.
	 * @param ciudad
	 * @param DB
	 * @return boolean
	 * @throws ExcepcionMch 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public boolean validarCiudad(String ciudad, String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		SQL = "SELECT COUNT(*)r FROM CIUDADES WHERE CHLLAVE = '"+ciudad+"'";

		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);

		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		JSONObject r = resultado.getJSONObject(0); 
		if(!r.isNull("error"))
			throw new ExcepcionMch("Ha ocurrido un error al ejecutar la consulta: "+r.getString("mensaje"));

		if(r.getInt("r") == 1)
			return true;


		return false;
	}
	/**
	 * Metodo que valida que el codigo de la ciudad sea valido.
	 * @param ciudad
	 * @param DB
	 * @return boolean
	 * @throws ExcepcionMch 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public boolean validarEntidad(String entidad, String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		SQL = "SELECT count(*)r FROM ENTIDADES WHERE CHCODIGO_ENTIDAD = REPLACE('"+entidad+"',' ','') ";

		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);

		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		JSONObject r = resultado.getJSONObject(0); 
		if(!r.isNull("error"))
			throw new ExcepcionMch("Ha ocurrido un error al ejecutar la consulta: "+r.getString("mensaje"));

		if(r.getInt("r") == 1)
			return true;



		return false;
	}

	/**
	 * @param cedula
	 * @param DB
	 * @return boolean
	 * @throws ExcepcionMch 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public boolean validarCedula(String cedula, String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		SQL = "SELECT COUNT(*)r FROM BASICA WHERE CHCODIGO = REPLACE('"+cedula+"',' ','') ";

		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);

		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		JSONObject r = resultado.getJSONObject(0); 
		if(!r.isNull("error"))
			throw new ExcepcionMch("Ha ocurrido un error al ejecutar la consulta: "+r.getString("mensaje"));

		if(r.getInt("r") == 0)
			return true;

		return false;
	}
	/**
	 * @param cedula
	 * @param DB
	 * @return boolean
	 * @throws ExcepcionMch 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public String validarObtenerConcepto(String concepto[], String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		SQL = "SELECT CHCODIGO_CONCEPTO FROM CPTOS_TIPO WHERE CHTIPO_EMPLEADO = '1'";
		String condicion = "";
		for(String c : concepto){
			if(!c.replace(" ", "").equals("")){
				condicion+=" AND LOWER(CHDESCRIPCION_CONCEPTO) LIKE '%"+c+"%' ";
			}
		}
		SQL+=condicion+" ORDER BY CAST(CHCODIGO_CONCEPTO AS NUMERIC) ASC";

		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);

		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		if(resultado.length() == 0)
			throw new ExcepcionMch("No se encontraron datos al ejecutar la consulta, esto fue lo que devolvió el servicio: "+resultado);

		JSONObject r = resultado.getJSONObject(0); 
		if(!r.isNull("error"))
			throw new ExcepcionMch("Ha ocurrido un error al ejecutar la consulta: "+r.getString("mensaje"));

		if(r.isNull("error"))
			return r.getString("CHCODIGO_CONCEPTO");
		else
			return null;


	}

	/**
	 * Metodo que retorna el Salario mínimo
	 * @param DB
	 * @return
	 * @throws ExcepcionMch
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public double obtenerSalarioMinimo(String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		SQL = "SELECT CAST(SUBSTRING(CHDESCRIPCION,86,10)AS NUMERIC)SALARIO FROM PARAMETROS WHERE CHCODIGO = '108' ";

		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);

		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		JSONObject r = resultado.getJSONObject(0); 
		if(!r.isNull("error"))
			throw new ExcepcionMch("Ha ocurrido un error al ejecutar la consulta: "+r.getString("mensaje"));

		if(r.getInt("SALARIO") == 1)
			return r.getDouble("SALARIO");

		return 0;
	}
}