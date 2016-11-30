package com.mch.actividades;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;

import com.mch.bean.BancosBean;
import com.mch.bean.BasicaBean;
import com.mch.bean.ContratoBean;
import com.mch.bean.CptosHistAuBean;
import com.mch.bean.SegSocialBean;
import com.mch.bean.TributariaBean;
import com.mch.dao.ImportarTrabajadoresDao;
import com.mch.dao.InsertarDatosPorBeanDao;
import com.mch.excepciones.ExcepcionMch;

/**
 * @author Camilo
 * 29/06/2016
 */
public class ActividadImportarTrabajadoresNuevos {

	private static final short TAMANO_FILA_MINIMO = 30, INDICE_INICIO_DEVENGOS=26;
	private DecimalFormat formatocedula = new DecimalFormat("#########");
	private SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-MM-dd");
	private ImportarTrabajadoresDao consultas = new ImportarTrabajadoresDao();
	private Date fechaIngreso = new Date();

	/**
	 * @param archivo
	 * @param nombreArchivo
	 * @throws ExcepcionMch
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ParseException 
	 */
	public ActividadImportarTrabajadoresNuevos(List<Object[]> informacionExcel, String nombreArchivo, String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, JSONException, IOException, ParseException{
		List<Object> informacion = new ArrayList<Object>();
		String cedula = null, filaTitulos = "";


		boolean filaValida = false;
		int f = 0, codigoValidacion = 0;
		for(Object[] filaObject : informacionExcel){
			String fila = "";
			System.out.println(".................");
			for(int a = 0 ; a < filaObject.length ; a++){
				System.out.println(filaObject[a]);
				fila = fila + filaObject[a];
				if((a+1) < filaObject.length)
					fila = fila + "\t";
			}
			System.out.println(".................");

			f++;
			if(fila.toLowerCase().contains("cedula")){
				filaValida = true;
				filaTitulos = fila.toLowerCase();
				continue;
			}
			if(filaValida == true){
				if(fila.replace(" ", "").replace("\t", "").replace("NULL", "").length() == 0){
					continue;
				}
				System.out.println(">--<");
				System.out.println(fila);
				System.out.println(fila.split("\t").length+" --- "+TAMANO_FILA_MINIMO);
				if(fila.split("\t").length < TAMANO_FILA_MINIMO){
					codigoValidacion = 1;
					break;
				}

				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				//         Validaciones Basicas
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				if(fila.split("\t")[0].replace("NULL", "").replace(" ", "").equals("")){
					throw new ExcepcionMch("Hay un registro con el campo cédula vacío, en la fila: "+f);
				}else{
					cedula = formatocedula.format(Double.parseDouble(fila.split("\t")[0].replace("NULL", "").replace(" ", "")));
					if(consultas.validarCedula(cedula, DB, negocio) == false){
						throw new ExcepcionMch("La cédula "+cedula+" ya existe en la Base de Datos, en la fila: "+f);
					}
				}

				informacion.add(crearBasicaBean(fila,cedula,f, DB, negocio));
				informacion.add(crearSegSocialBean(fila,cedula,f, DB, negocio));
				informacion.add(crearTributariaBean(fila,cedula,f, DB, negocio));
				informacion.add(crearBancosBean(fila,cedula,f));
				informacion.add(crearContratoBean(fila,cedula,f));
				for(CptosHistAuBean concepto: crearListaCptosHistAuBean(fila,cedula,filaTitulos,f, DB, negocio)){
					informacion.add(concepto);
				}
			}
		}

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//      Validaciones para el archivo
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		if(codigoValidacion == 1){
			throw new ExcepcionMch("El archivo: "+nombreArchivo+" no cumple con la cantidad de columnas necesarias para ser procesado, se encontró error en la fila número: "+f);
		}
		if(filaValida == false){
			throw new ExcepcionMch("No se pudo procesar el archivo: "+nombreArchivo);
		}
		//			if(pagina.length == 0){
		//				throw new ExcepcionMch("No se pudo encontrar información en el archivo: "+nombreArchivo);
		//			}

		new InsertarDatosPorBeanDao().insertarInfo(informacion, DB, negocio);
	}

	/**
	 * @param fila
	 * @param DB
	 * @return BasicaBean
	 * @throws ExcepcionMch
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private BasicaBean crearBasicaBean(String fila, String cedula,  int numeroFila, String DB, String negocio) throws ExcepcionMch, ParseException, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//        Objeto para el insert
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		BasicaBean basica = new BasicaBean();

		String columnas[] = fila.split("\t"),
				fechaNacimiento = columnas[5].replaceAll("[\n,\r,\t]", "").replace(" ", ""),
				centro = columnas[7].replaceAll("\\D", "").replace("NULL", ""),
				ciudad = columnas[9].replaceAll("\\D", "").replace("NULL", ""),
				cargo  = columnas[10].substring(0, (columnas[10].indexOf("*")==-1?columnas[10].length():columnas[10].indexOf("*"))).replace("NULL", "");

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//            Validaciones
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		if(columnas[1].replace("NULL", "").replace(" ", "").equals("")&&columnas[2].replace("NULL", "").replace(" ", "").equals("")){
			throw new ExcepcionMch("No se encontraron apellidos, para la cédula: "+cedula+", fila: "+numeroFila);
		}
		if(columnas[3].replace("NULL", "").replace(" ", "").equals("")&&columnas[3].replace("NULL", "").replace(" ", "").equals("")){
			throw new ExcepcionMch("No se encontraron nombres, para la cédula: "+cedula+", fila: "+numeroFila);
		}
		if(cargo.replace("NULL", "").replace(" ", "").equals("")){
			throw new ExcepcionMch("El cargo no puede estar vacío, para la cédula: "+cedula+", fila: "+numeroFila);
		}
		if(ciudad.replace("NULL", "").replace(" ", "").equals("")){
			throw new ExcepcionMch("La ciudad no puede estar vacía, para la cédula: "+cedula+", fila: "+numeroFila);
		}
		if(fechaNacimiento.replace("NULL", "").replace(" ", "").equals("")){
			throw new ExcepcionMch("La fecha de nacimiento no puede estar vacía, para la cédula: "+cedula+", fila: "+numeroFila);
		}
		if(centro.replace("NULL", "").replace(" ", "").equals("")){
			throw new ExcepcionMch("El centro de costo no puede estar vacío para la cédula: "+cedula+", fila: "+numeroFila);
		}
		if(columnas[18].replace("NULL", "").replace(" ", "").equals("")){
			throw new ExcepcionMch("El fondo de cesantías no puede estar vacío para la cédula: "+cedula+", fila: "+numeroFila);
		}
		boolean validacionCentro = consultas.validarCentroCosto(centro, DB, negocio),
				validacionCargo  = consultas.validarCargo(cargo, DB, negocio),
				validacionCiudad = consultas.validarCiudad(ciudad, DB, negocio);

		if(validacionCentro == false){
			throw new ExcepcionMch("El centro de costo: "+centro+" es invalido para la cédula: "+cedula+", fila: "+numeroFila);
		}
		if(validacionCargo  == false){
			throw new ExcepcionMch("El cargo: "+cargo+" es invalido para la cédula: "+cedula+", fila: "+numeroFila);
		}
		if(validacionCiudad == false){
			throw new ExcepcionMch("El codigo de ciudad: "+ciudad+" es invalido para la cédula: "+cedula+", fila: "+numeroFila);
		}

		/*
		 * Se obtiene el año actual y se le resta 15,
		 * esto es para validar que el trabajador al
		 * menos tiene 15 años de edad, si hay que 
		 * aumentar el rango aca es donde se debe cambiar.
		 */
		int nanoMinimo = Calendar.getInstance().get(Calendar.YEAR)-15;
		if(formatoFecha.parse(fechaNacimiento).after(formatoFecha.parse(nanoMinimo+"-01-01"))){
			throw new ExcepcionMch("Fecha de nacimiento invalida: "+fechaNacimiento+" para la cédula: "+cedula);
		}

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Se crea el objeto con la info necesaria para el insert 
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		basica.setChCODIGO(cedula)
		.setChNOMBRE(columnas[1].replace("NULL", "")+" "+columnas[2].replace("NULL", "")+" "+columnas[3].replace("NULL", "")+" "+columnas[4].replace("NULL", ""))
		.setChCENTRO(centro)
		.setChNIT(cedula)
		.setChNIT_TIPO("CC")
		.setDtFECHA_ING(formatoFecha.format(fechaIngreso))
		.setDtFECHA_RET(formatoFecha.format(formatoFecha.parse("1899-12-30")))
		.setChESTADO_CIA("Activo")
		.setChFORMA_PAGO("Sueldo")
		.setChESTADO_CIVIL("Soltero")
		.setChCARGO(cargo)
		.setChDIRECCION("")
		.setChTELEFONO("")
		.setChSITIO_NAC(ciudad)
		.setDtFECHA_NAC(formatoFecha.format(formatoFecha.parse(fechaNacimiento)))
		.setChGENERO("-")//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ PENDIENTE ! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
		.setChTIPO_CONTRATO("Indefinido Ley50")
		.setDtFECHA_CNTO(formatoFecha.format(fechaIngreso))
		.setDtFECHA_VTO(formatoFecha.format(formatoFecha.parse("1899-12-30")))
		.setChFONDO_CESANTIA((columnas[18].indexOf("*")==-1?columnas[18]:columnas[18].substring(0,columnas[18].indexOf("*"))))
		.setChCODIGO_INTERNO(cedula)
		.setBoSINDICALIZADO((byte)0)
		.setBoPACTO_COLECT((byte)0)
		.setChCIUDAD_RESIDENCIA(ciudad)
		.setDtFECHA_CONTINUIDAD(formatoFecha.format(fechaIngreso))
		.setChPRIMER_APELLIDO(columnas[1].replace("NULL", ""))
		.setChSEGUNDO_APELLIDO(columnas[2].replace("NULL", ""))
		.setChPRIMER_NOMBRE(columnas[3].replace("NULL", ""))
		.setChSEGUNDO_NOMBRE(columnas[4].replace("NULL", ""))
		.setBoSABADO_HABIL((byte)0)
		.setChCIUDAD_CEDULA(ciudad)
		.setChTIPO_SANGRE("")
		.setChVEREDA("0101")
		.setChCELULAR("")
		.setChCORREO_ELECTRONICO("N")
		.setChSUCURSAL("001")
		.setChACTIVIDAD("0")
		.setChUNIDAD("0")
		.setChTIPO_EMPLEADO("1")
		.setChNacionalidad("169");	

		return basica;
	}

	/**
	 * @param fila
	 * @param cedula
	 * @param numeroFila
	 * @return SegSocialBean
	 * @throws ExcepcionMch
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public SegSocialBean crearSegSocialBean(String fila, String cedula, int numeroFila, String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//        Objeto para el insert
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		SegSocialBean segSocial = new SegSocialBean();
		String columnas[] = fila.split("\t"),
				EPS    = (columnas[16].indexOf("*") == -1 ? columnas[16] : columnas[16].substring(0,columnas[16].indexOf("*"))),
				AFP    = (columnas[17].indexOf("*") == -1 ? columnas[17] : columnas[17].substring(0,columnas[17].indexOf("*")));

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//            Validaciones
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


		if(EPS.replace("NULL", "").replace(" ", "").equals("")){
			throw new ExcepcionMch("Hay un registro con el campo EPS vacío, en la fila: "+numeroFila);
		}
		if(AFP.replace("NULL", "").replace(" ", "").equals("")){
			throw new ExcepcionMch("Hay un registro con el campo Fondo Pensión (AFP) vacío, en la fila: "+numeroFila);
		}

		boolean validacionEPS = consultas.validarEntidad(EPS.replace(" ", ""), DB, negocio),
				validacionAFP = consultas.validarEntidad(AFP.replace(" ", ""), DB, negocio);

		if(validacionEPS == false){
			throw new ExcepcionMch("La EPS: "+EPS+" no es valida, fila: "+numeroFila);
		}
		if(validacionAFP == false){
			throw new ExcepcionMch("El Aporte Fondo Pensión (AFP): "+AFP+" no es valida, fila: "+numeroFila);
		}

		segSocial.setChCODIGO(cedula)
		.setDtFECHACAMBIO(formatoFecha.format(fechaIngreso))
		.setChCOTIZANTE("1")
		.setChEPS(EPS)
		.setDtFECHA_CAMBIO_EPS(formatoFecha.format(fechaIngreso))
		.setChAFP(AFP)
		.setDtFECHA_CAMBIO_AFP(formatoFecha.format(fechaIngreso))
		.setChARP("14-23")
		.setDtFECHA_CAMBIO_ARP(formatoFecha.format(fechaIngreso))
		.setChEPS_ANTERIOR("SIN")
		.setChAFP_ANTERIOR("SIN")
		.setChARP_ANTERIOR("SIN")
		.setBoCONTINUIDAD_DOCENTE((byte)0)
		.setChTIPO_PENSION("0")
		.setChTIPO_PENSIONADO("0")
		.setBoPENSION_COMPARTIDA((byte)0)
		.setBoRESIDE_EXTERIOR((byte)0)
		.setBoPAGO_EXTERIOR((byte)0)
		.setChSUBTIPO_COTIZANTE("0")
		.setChCAJA_COMPENSACION("CCF56")
		.setChFACTOR_RIESGOS("003")
		.setDtFECHA_CAMBIO_RIESGO(formatoFecha.format(fechaIngreso))
		.setChFACTOR_RIESGOS_ANTERIOR("1")
		.setInCONSECUTIVO(1)
		.setInExtranjero(1);

		return segSocial;
	}

	/**
	 * @param fila
	 * @param cedula
	 * @param numeroFila
	 * @return TributariaBean
	 * @throws ExcepcionMch
	 */
	public TributariaBean crearTributariaBean(String fila, String cedula, int numeroFila, String DB, String negocio) throws ExcepcionMch{
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//        Objeto para el insert
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		TributariaBean tributaria = new TributariaBean();

		tributaria.setChCODIGO(cedula)
		.setChPROC_RETENCION("1")
		.setDbVALOR_AFC(0)
		.setDbVALOR_INTERES_VIVIENDA(0)
		.setChPORCENTAJE_FIJO("0")
		.setDbVALOR_EDUCACION(0)
		.setDbVALOR_AFP(0)
		.setDbEXENTO30(0)
		.setDbEXENTO15(0)
		.setDbPROMEDIO(0)
		.setDbPROMEDIO_ANO_ANTERIOR(0)
		.setDbVALOR_DEP_A_CARGO(0)
		.setBoESCONTRIBUYENTE((byte)0);

		return tributaria;
	}

	/**
	 * @param fila
	 * @param cedula
	 * @param numeroFila
	 * @return BancosBean
	 * @throws ExcepcionMch
	 */
	public BancosBean crearBancosBean(String fila, String cedula, int numeroFila) throws ExcepcionMch{

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//        Objeto para el insert
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		BancosBean banco = new BancosBean();

		String columnas[] = fila.split("\t"),
				noCuenta = formatocedula.format(Double.parseDouble(columnas[14].replace(" ", "")));

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//            Validaciones
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		if(noCuenta.length()<=6){
			throw new ExcepcionMch("El número de cuenta: "+noCuenta+" no cumple con un tamaño mínimo válido, en la fila: "+numeroFila);
		}
		if(noCuenta.length()>20){
			throw new ExcepcionMch("El número de cuenta: "+noCuenta+" excede el tamaño máximo válido, en la fila: "+numeroFila);
		}
		if(!columnas[13].contains("051")){
			throw new ExcepcionMch("El código de banco: "+columnas[13]+" no es válido, en la fila: "+numeroFila);
		}
		System.out.println("=> ----- "+columnas[13]);
		banco.setChCODIGO(cedula)
		.setDtFECHA_ASIGNACION(formatoFecha.format(fechaIngreso))
		.setChCUENTA_NUMERO(noCuenta)
		.setChCODIGO_BANCO_CIA("51")
		.setChCODIGO_BANCO_EMPLEADO("051")
		.setChTIPO_CUENTA("Cuenta de Ahorro")
		.setChCLASE_CUENTA("Propia")
		.setBoESTADO_CUENTA((byte)1);

		return banco;
	}

	/**
	 * @param fila
	 * @param cedula
	 * @param numeroFila
	 * @return ContratoBean
	 * @throws ExcepcionMch
	 * @throws ParseException 
	 */
	public ContratoBean crearContratoBean(String fila, String cedula, int numeroFila) throws ExcepcionMch, ParseException{
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//        Objeto para el insert
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		ContratoBean contrato = new ContratoBean();

		contrato.setChCODIGO(cedula)
		.setDtFECHA_INGRESO(formatoFecha.format(fechaIngreso))
		.setDtFECHA_RETIRO(formatoFecha.format(formatoFecha.parse("1899-12-30")))
		.setChTIPO_CONTRATO("Indefinido Ley50")
		.setChNUMERO_CONTRATO("1")
		.setChOBSERVACION_1("")
		.setChOBSERVACION_2("");

		return contrato;
	}

	/**
	 * @param fila
	 * @param cedula
	 * @param numeroFila
	 * @return List<CptosHistAuBean>
	 * @throws ExcepcionMch
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public List<CptosHistAuBean> crearListaCptosHistAuBean(String fila, String cedula, String filaTitulos, int numeroFila, String DB, String negocio) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException{
		List<CptosHistAuBean> conceptos = new ArrayList<CptosHistAuBean>();
		String columnas[] = fila.split("\t"),
				titulos[] = filaTitulos.split("\t");
		boolean encontroSueldo = false;
		float valorSueldo = -1;
		for(int a = INDICE_INICIO_DEVENGOS ; a < columnas.length ; a++){
			if(!columnas[a].replace(" ", "").replace("NULL", "").equals("")){
				columnas[a] = columnas[a].trim();
				String codigoConcepto = consultas.validarObtenerConcepto(titulos[a].split(" "), DB, negocio);
				if(codigoConcepto == null){
					throw new ExcepcionMch("No se pudo encontrar el concepto: "+titulos[a]);
				}
				if(codigoConcepto.replace(" ", "").equals("1")){
					encontroSueldo = true;	
					valorSueldo = Float.parseFloat(columnas[a]);
				}if(codigoConcepto.replace(" ", "").equals("27")){
					valorSueldo = -1;
				}
				CptosHistAuBean cptosHistAuBean = new CptosHistAuBean();
				cptosHistAuBean.setChCODIGO(cedula)
				.setChCODIGO_CONCEPTO(codigoConcepto)
				.setDtFECHA_INCREMENTO(formatoFecha.format(fechaIngreso))
				.setChIDENTIFICADOR("0")
				.setChFRECUENCIA("3")
				.setBoVALOR_PORCENTAJE((byte)0)
				.setChREFERENCIA("0")
				.setDbVALOR_PAGO(String.valueOf(Float.parseFloat(columnas[a])))
				.setDbSALDO(0)
				.setChTIPO_EMPLEADO("1")
				.setDtFECHA_NOVEDAD(formatoFecha.format(fechaIngreso));
				conceptos.add(cptosHistAuBean);
			}
		}
		if(encontroSueldo == false){
			throw new ExcepcionMch("No se pudo encontrar el concepto Sueldo en la fila: "+numeroFila);
		}else if(valorSueldo <= (consultas.obtenerSalarioMinimo(DB, negocio)*2) && valorSueldo != -1){
			CptosHistAuBean cptosHistAuBean = new CptosHistAuBean();
			Double valor = Double.parseDouble("1788"); //PRIMA ALIMENTACION
			cptosHistAuBean.setChCODIGO(cedula)
			.setChCODIGO_CONCEPTO("27")
			.setDtFECHA_INCREMENTO(formatoFecha.format(fechaIngreso))
			.setChIDENTIFICADOR("0")
			.setChFRECUENCIA("3")
			.setBoVALOR_PORCENTAJE((byte)0)
			.setChREFERENCIA("0")
			.setDbVALOR_PAGO(String.valueOf(valor.floatValue()))
			.setDbSALDO(0)
			.setChTIPO_EMPLEADO("1")
			.setDtFECHA_NOVEDAD(formatoFecha.format(fechaIngreso));
			conceptos.add(cptosHistAuBean);
		}
		return conceptos;
	}
	//	public static void main(String[] args){
	//		long t = System.currentTimeMillis();
	//		try{
	//			new ActividadImportarTrabajadoresNuevos(new FileInputStream("E:/ARCHIVOS_PRUEBA_IMPORTACION/NvIng2016060101 - Novedades Ingresos JUNIO 2016 Liquidacion mensual.xlsx"),"NvIng2016060101 - Novedades Ingresos JUNIO 2016 Liquidacion mensual.xlsx", t);
	//		}catch(ExcepcionMch e){
	//			e.printStackTrace();
	//		}
	//	}
}