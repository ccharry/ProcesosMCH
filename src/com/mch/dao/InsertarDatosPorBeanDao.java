package com.mch.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mch.actividades.ActividadEjecutarConsulta;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadesServicioEjecutarConsulta;

/**
 * @author Camilo
 * 15/07/2016
 */
public class InsertarDatosPorBeanDao {



	private ActividadEjecutarConsulta actividadEjecutarConsulta = new ActividadEjecutarConsulta();
	private PropiedadesServicioEjecutarConsulta prop = new PropiedadesServicioEjecutarConsulta("", "", "");

	/**
	 * Metodo que mapea la clase que llego como
	 * parametro para crear un insert dinamico.
	 * Es importante que el BEAN tenga mapeado
	 * todas las columnas de la tabla a la cual
	 * se quiere hacer el insert.
	 * @param bean
	 * @param token
	 * @return boolean
	 * @throws ExcepcionMch
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public boolean insertarInfo(List<?> beans, String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, JSONException, IOException{
		JSONArray inserts = new JSONArray();
		for(Object bean :beans){
			Class<?> obj = bean.getClass();

			String tabla = encontrarTabla(bean);
			if(tabla == null){
				throw new ExcepcionMch("=> No se pudo encontrar la variable tabla, para la clase : "+obj.getPackage()+"."+obj.getName());
			}

			String parametros = "";
			for(int a = 0 ; a < obj.getDeclaredFields().length-1 ; a++){
				parametros += "?" + (a+2 < obj.getDeclaredFields().length ? ",":"");
			}
			String SQL = "INSERT INTO "+tabla+" VALUES("+parametros+")";
			System.out.println(SQL);

			for(Field a : obj.getDeclaredFields()){
				a.setAccessible(true);
				if(!a.getName().toUpperCase().equals("TABLA")){
					//						stmt.setObject(indiceSet, a.get(bean));
					if(a.get(bean) instanceof Integer || a.get(bean) instanceof Double || a.get(bean) instanceof Float){
						SQL = SQL.replaceFirst("[?]", String.valueOf(a.get(bean)));
					}else if(a.get(bean) instanceof String || a.get(bean) instanceof java.util.Date || a.get(bean) instanceof java.sql.Date){
						SQL = SQL.replaceFirst("[?]", "'"+String.valueOf(a.get(bean))+"'");
					}else{
						SQL = SQL.replaceFirst("[?]", "'"+String.valueOf(a.get(bean))+"'");
					}
				}
			}
			System.out.println("Agrega Insert: "+SQL);
			inserts.put(SQL);
		}

		prop.setConsulta(inserts.toString());
		prop.setDataBase(DB);
		prop.setNegocio(negocio);
		JSONArray retorno = actividadEjecutarConsulta.ejecutarInsert(prop);
		if(retorno.length() == 0)
			throw new ExcepcionMch("Ha ocurrido un error al ejecutar el insert, retorno del servicio: "+retorno);
		JSONObject r = retorno.getJSONObject(0);
		if(!r.isNull("error"))
			throw new ExcepcionMch("Ha ocurrido un error al ejecutar el insert, retorno del servicio: "+r.getString("error"));

		return true;
	}

	/**
	 * Metodo que recorre el Bean que llegó como parametro
	 * para tratar de encontrar la variable tabla, esta
	 * variable almacena la tabla a donde se tiene que 
	 * hacer el insert.
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private String encontrarTabla(Object bean) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{

		Class<?> obj = bean.getClass();
		for(Field a : obj.getDeclaredFields()){
			a.setAccessible(true);
			if(a.getName().toUpperCase().equals("TABLA")){
				return (String) a.get(bean);
			}
		}
		return null;
	}
public static void main(String[] args) {
	String a="insert into values(?,?,?)";
	a = a.replaceFirst("[?]", "123");
	System.out.println(a);
}

	//		public static void main(String[] args) throws ExcepcionMch {
	//			new InsertarDatosPorBeanDao().insertarInfo(new NovedadDaviviendaBean(), "eyJhbGciOiJIUzI1NiJ9.eyJVU1VBUklPXyI6IkFETUlOIiwiREJOQU1FXyI6Ik5td0hvc3BpdGFsUHJ1IiwiRU1QUkVTQV8iOiJQSERUVVUiLCJDRURVTEFfIjoiMTExIn0.5jhi43l8gTGuXNex56YvbLuGtvzVPxCEHr_c-G34mDQ");
	//			//																		 eyJhbGciOiJIUzI1NiJ9.eyJVU1VBUklPXyI6IkFETUlOIiwiREJOQU1FXyI6Ik5td0hvc3BpdGFsUHJ1IiwiRU1QUkVTQV8iOiJQSERUVVUiLCJDRURVTEFfIjoiMTExIn0.5jhi43l8gTGuXNex56YvbLuGtvzVPxCEHr_c-G34mDQ
	//		}
}
