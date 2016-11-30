package com.mch.actividades;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;

import org.json.JSONException;

import com.mch.bean.DatosPersonaBean;
import com.mch.bean.NovedadHorasExtrasUnidades;
import com.mch.dao.ImportarExcelHorasExtrasDao;
import com.mch.dao.InsertarDatosPorBeanDao;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioCargarArchivo;
import com.mch.utilidades.UtilMCH;


/**
 * @author Camilo
 * 15/07/2016
 */
public class ActividadImportarExcelHorasExtras {


	private ImportarExcelHorasExtrasDao consultar = new ImportarExcelHorasExtrasDao();
	List<String[]>conceptos = new ArrayList<String[]>();

	/**
	 * @param in
	 * @param chLiquidacion
	 * @param periodo
	 * @param token
	 * @throws IOException 
	 * @throws NullPointerException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Throwable
	 */
	public ActividadImportarExcelHorasExtras(List<Object[]> informacionExcel, String chLiquidacion, String periodo, String DB, String negocio) throws ExcepcionMch, NullPointerException, IOException, ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{

		/*
		 * Se validan los conceptos que pueda encontrar,
		 * si encuentra un concepto invalido, retorna una
		 * excepcion.
		 */
		encotrarCodigoConceptoHoras(informacionExcel, DB, negocio);


		List<NovedadHorasExtrasUnidades>novedadesHorasExtras = new ArrayList<NovedadHorasExtrasUnidades>();
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		String fecha = format.format(new Date());

		boolean isValid = false;

		Map<String, DatosPersonaBean> trabajadores = consultar.consultarTodosLosTrabajadoresActivos(DB, negocio);
		int filaTemp = 0;
		for(Object[] objFila : informacionExcel){
			String a = "";
			for(int of = 0 ; of < objFila.length ; of++){
				a = a + objFila[of];
				if(of < objFila.length)
					a = a + "\t";
			}


			filaTemp++;
			String validacionTemp = a.toUpperCase(); 
			if(validacionTemp.contains("CEDULA") && validacionTemp.contains("NOMBRE") ){
				isValid = true;
				continue;
			}
			if(validacionTemp.contains("SUB") && validacionTemp.contains("TOTAL") ){
				isValid = false;
			}
			if(isValid == true){

				//				System.out.println("---> "+a);
				if(a.toUpperCase().contains("DOMINICALES") || a.toUpperCase().contains("ORDINARIAS") || a.toUpperCase().contains("NOCTURNAS") || a.toUpperCase().contains("DIURNAS") || a.toUpperCase().contains("ADICIONALES")){
					continue;
				}
				String[] b = a.split("\t");

				if( b[0].toUpperCase().contains("NULL") && b[1].toUpperCase().contains("NULL") && b[2].toUpperCase().contains("NULL") && b[3].toUpperCase().contains("NULL")){
					continue;
				}
				if(b[0].toUpperCase().contains("NULL")){
					throw new ExcepcionMch("No se importaron datos, porque hay un trabajador sin cédula, por favor validar la linea: "+filaTemp);
				}					

				if(b.length > 6){
					//					System.out.println(a);
					DatosPersonaBean trabajador = trabajadores.get(num.format(new BigDecimal(b[0])).replace(",", "").replace(" ", ""));
					if(trabajador==null){
						String numeroTrabajador = num.format(new BigDecimal(b[0])).replace(",", "").replace(" ", "");
						throw new ExcepcionMch("No se importaron datos, porque el trabajador : "+numeroTrabajador+" no existe, por favor validar.");
					}
					for(String[] c : conceptos){
						int posicion = Integer.parseInt(c[0]);
						if(!b[posicion].contains("NULL") && !b[posicion].replace(" ", "").replace("\n", "").equals("")){
							//							System.out.println((Integer.parseInt(b[posicion].replace(".0", ""))*60)+" - "+b[posicion]+" ----- "+b[0]+" --- "+posicion+" --- "+c[0]+" -- "+c[1]+" -- "+c[2]);
							NovedadHorasExtrasUnidades novedad = new NovedadHorasExtrasUnidades()
							.setChLiquidacion(chLiquidacion)
							.setChCodigo(num.format(new BigDecimal(b[0])).replace(",", ""))
							.setChCentro(trabajador.getChCentro())
							.setChCentroLiquidacion(trabajador.getChCentro())
							.setChActividad(0)
							.setChUnidad(0)
							.setDtFecha_Novedad(fecha)
							.setChCodigo_Concepto(Integer.parseInt(c[1].replace(".0", "")))
							.setChControl(0)
							.setDbValor(0.0)
							.setChUsuario("MC")
							.setDtFechaLiquidacion(fecha)
							.setChNumero_Contrato(0)
							.setChIdentificador("1")
							.setChPeriodo(periodo)
							.setChtipoEmpleado(1);

							if(c[2].equals("20")){
								novedad.setDbUnidades((Integer.parseInt(b[posicion].replace(".0", ""))*60));
							}else{
								novedad.setDbUnidades((Integer.parseInt(b[posicion].replace(".0", ""))));
							}
							if(consultar.consultarDuplicidadInformacion(novedad, DB, negocio) == true)
								throw new ExcepcionMch("El trabajador "+novedad.getChCodigo()+" ya fue importado para la liquidación: "+novedad.getChLiquidacion());
							novedadesHorasExtras.add(novedad);
						}
					}
				}
			}
		}
		if(novedadesHorasExtras.size() == 0){
			throw new ExcepcionMch("No se pudo leer el archivo");
		}
		new InsertarDatosPorBeanDao().insertarInfo(novedadesHorasExtras, DB, negocio);
	}

	/**
	 * 
	 * @param token
	 * @param filas
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Throwable
	 */
	private void encotrarCodigoConceptoHoras(List<Object[]> filas, String DB, String negocio) throws ClassNotFoundException, SQLException, IOException, ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException{

		/*
		 * Se hace esta parte para acomodar la variable
		 * entrada 'filas' para no alterar la logica que
		 * se implementó anteriormente.
		 */
		String[] filasTemp = new String[filas.size()];
		for(int f = 0 ; f < filas.size() ; f++){
			String filaTemp = "";
			for(int a = 0 ; a < filas.get(f).length ; a++){
				filaTemp = filaTemp + filas.get(f)[a].toString();
				if(a < filas.get(f).length)
					filaTemp = filaTemp + "\t";
			}
			filasTemp[f] = filaTemp;
		}

		for(int a = 0 ; a < filasTemp.length ; a ++){

			String fila = filasTemp[a];
			String filaTemp = fila.toUpperCase();
			if(filaTemp.contains("CEDULA") || filaTemp.contains("NOMBRE")){
				String[]palabras = filasTemp[a-1].split("\t") ;
				for(int b = 0 ; b < palabras.length ; b ++){
					palabras[b] = palabras[b].toUpperCase().replace(".0", "");
					if(!palabras[b].contains("NULL")){
						String chProcedimiento = consultar.consultarConceptoHoras(palabras[b], DB, negocio);
						if(chProcedimiento == null){
							throw new ExcepcionMch("El concepto : "+palabras[b]+" no existe, por favor validar.");
						}
						conceptos.add(new String[]{String.valueOf(b), palabras[b], chProcedimiento});
						//						conceptos.put(String.valueOf(b), new String[]{palabras[b], chProcedimiento});
					}
				}
			}
		}

	}

	
	public static void main(String[] args) throws NullPointerException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ExcepcionMch, IOException, SQLException, JSONException, MessagingException {
		ActividadCargarArchivo actividadCargarArchivo = new ActividadCargarArchivo();
		List<Object[]> info = actividadCargarArchivo.leerArchivoExcel(new File("E:\\ARCHIVOS_PRUEBA_IMPORTACION\\NvExt2016050601- Novedades Horas Rec y Extras Mayo 2016 Liquidacion mensual ok.xlsx"),  new PropiedadServicioCargarArchivo(UtilMCH.getDataBaseName("NominaHospital"), "TEMP", "NominaHospital", false));
		new ActividadImportarExcelHorasExtras(info, "2016110130", "201611", "NmwHospitalPru", "NominaHospital");
	}
	//	public static void main(String[] args) throws Throwable{
	//		//		List<String[]> paginas = new LectorExcel().leerExcel(new XSSFWorkbook (new FileInputStream(new File("E:/horasExtras.xlsx"))),false);
	//		//		new ImportadorExcelHorasExtras().encotrarCodigoConceptoHoras("eyJhbGciOiJIUzI1NiJ9.eyJVU1VBUklPXyI6IkFETUlOIiwiREJOQU1FXyI6Ik5td0hvc3BpdGFsUHJ1IiwiRU1QUkVTQV8iOiJQSERUVVUiLCJDRURVTEFfIjoiMTExIn0.5jhi43l8gTGuXNex56YvbLuGtvzVPxCEHr_c-G34mDQ",paginas.get(0));
	//		InputStream in = new FileInputStream(new File("E:/horasExtras.xlsx"));
	//		new ActividadImportarExcelHorasExtras(in, "2016010130", "201606", "eyJhbGciOiJIUzI1NiJ9.eyJVU1VBUklPXyI6IkFETUlOIiwiREJOQU1FXyI6Ik5td0hvc3BpdGFsUHJ1IiwiRU1QUkVTQV8iOiJQSERUVVUiLCJDRURVTEFfIjoiMTExIn0.5jhi43l8gTGuXNex56YvbLuGtvzVPxCEHr_c-G34mDQ");
	//		//		for(String a : paginas.get(0)){
	//		//			System.out.println(a);
	//		//		}
	//	}
}
