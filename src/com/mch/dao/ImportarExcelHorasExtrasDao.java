package com.mch.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.mch.actividades.ActividadEjecutarConsulta;
import com.mch.bean.DatosPersonaBean;
import com.mch.bean.NovedadHorasExtrasUnidades;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadesServicioEjecutarConsulta;

/**
 * @author Camilo
 * 29/11/2016
 */
public class ImportarExcelHorasExtrasDao {

	private String SQL = "";
	private ActividadEjecutarConsulta actividadEjecutarConsulta = new ActividadEjecutarConsulta();
	private PropiedadesServicioEjecutarConsulta prop = new PropiedadesServicioEjecutarConsulta("", "", "");
	private JSONArray resultado = null;




	/**
	 * Metodo que retorna una lista de tipo DatosPersonaBean,
	 * @param token
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ExcepcionPortal 
	 */
	public Map<String , DatosPersonaBean> consultarTodosLosTrabajadoresActivos(String DB, String negocio)  throws ClassNotFoundException, SQLException, IOException, ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException{
		Map<String , DatosPersonaBean> datos = new HashMap<String, DatosPersonaBean>();
		SQL = "SELECT CHCODIGO, CHNOMBRE, CHCENTRO FROM BASICA";

		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);
		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		for(int a = 0 ; a < resultado.length() ; a++){
			DatosPersonaBean persona = new DatosPersonaBean()
			.setChCentro(resultado.getJSONObject(a).getString("CHCENTRO"))
			.setChCodigo(resultado.getJSONObject(a).getString("CHCODIGO"))
			.setChNombre(resultado.getJSONObject(a).getString("CHNOMBRE"));
			datos.put(resultado.getJSONObject(a).getString("CHCODIGO").replace(" ", ""), persona);
		}
		return datos;
	}

	/**
	 * @param concepto
	 * @param DB
	 * @param negocio
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws ExcepcionMch
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 */
	public String consultarConceptoHoras(String concepto, String DB, String negocio)  throws ClassNotFoundException, SQLException, IOException, ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException{
		SQL = "SELECT COUNT(*)CONTEO, CHPROCEDIMIENTO FROM CPTOS_TIPO WHERE CHCODIGO_CONCEPTO = '"+concepto+"' AND CHTIPO_EMPLEADO = '1' GROUP BY CHPROCEDIMIENTO";

		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);

		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		if(resultado.length() > 0){
			if(resultado.getJSONObject(0).getInt("CONTEO") == 0){
				return null;
			}else if(resultado.getJSONObject(0).getInt("CONTEO") == 1){
				return resultado.getJSONObject(0).getString("CHPROCEDIMIENTO");
			}
		}

		return null;
	}
	
	/**
	 * Metodo que valida si el trabajador
	 * ya existe en la tabla NOVEDADES_LIQ
	 * para evitar el error de primary key.
	 * @param insert
	 * @param DB
	 * @param negocio
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 * @throws ExcepcionMch
	 * @throws IOException
	 */
	public boolean consultarDuplicidadInformacion(NovedadHorasExtrasUnidades insert, String DB, String negocio) throws IllegalArgumentException, IllegalAccessException, JSONException, ExcepcionMch, IOException{
		SQL = " SELECT COUNT(*)r "; 
		SQL = SQL+" FROM NOVEDADES_LIQ ";
		SQL = SQL+" WHERE chLIQUIDACION = '"+insert.getChLiquidacion()+"' ";
		SQL = SQL+" AND chCODIGO = '"+insert.getChCodigo()+"' ";
		SQL = SQL+" AND chCENTRO_LIQUIDACION = '"+insert.getChCentroLiquidacion()+"' ";
		SQL = SQL+" AND dtFECHA_NOVEDAD = '"+insert.getDtFecha_Novedad()+"' ";
		SQL = SQL+" AND chCODIGO_CONCEPTO = '"+insert.getChCodigo_Concepto()+"' ";
		SQL = SQL+" AND chIDENTIFICADOR = '"+insert.getChIdentificador()+"' ";
		
		prop.setConsulta(SQL);
		prop.setDataBase(DB);
		prop.setNegocio(negocio);

		resultado = actividadEjecutarConsulta.ejecutarConsulta(prop);
		
		if(resultado.getJSONObject(0).getInt("r") == 0){
			return false;
		}
		
		return true;
	}
	

}
