package com.mch.actividades;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.json.JSONException;

import com.mch.bean.ArchivoBean;
import com.mch.excepciones.ExcepcionMch;
import com.mch.propiedades.servicios.PropiedadServicioEnviarCorreo;
import com.mch.tareas.TareaEnviarArchivoRest;
import com.mch.utilidades.UtilMCH;

/**
 * @author Camilo
 * 05/09/2016
 */
public class ActividadEnviarCorreo extends TareaEnviarArchivoRest{

	/**
	 * 
	 * @param prop
	 * @return String en formato JSON
	 * @throws ExcepcionMch 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws MessagingException 
	 */
	public String enviarEmail(PropiedadServicioEnviarCorreo prop, boolean isEspecial) throws ExcepcionMch, IllegalArgumentException, IllegalAccessException, JSONException, IOException, MessagingException {
		if (prop.getAsunto() == null)
			throw new ExcepcionMch("No se encontró el asunto");
		if (prop.getMensaje() == null)
			throw new ExcepcionMch("No se encontró el mensaje");
		if (prop.getDestinatario() == null)
			throw new ExcepcionMch("No se encontraron destinatarios");

		if (prop.getAsunto().trim().equals(""))
			throw new ExcepcionMch("No se encontró el asunto");
		if (prop.getMensaje().trim().equals(""))
			throw new ExcepcionMch("No se encontró el mensaje");
		if (prop.getDestinatario().trim().equals(""))
			throw new ExcepcionMch("No se encontraron destinatarios");

		String ePattern = "^[a-zA-Z0-9.!#%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";
		Pattern pc = Pattern.compile(ePattern);
		Matcher m = pc.matcher(prop.getDestinatario().trim());
		if(m.matches() == false){
			throw new ExcepcionMch("Formato de correo inválido: "+prop.getDestinatario());
		}
		Map<String, Object> p = UtilMCH.generarMapPorPropiedad(prop);
		String servicio = (isEspecial == true ? "servicioEnviarCorreoEspecial" : "servicioEnviarCorreo");
		String s = generarRutaPeticion(servicio, p);
		return enviarArchivo(s , prop.getArchivos());
	}

	public static void main(String[] args) {
		try{
			PropiedadServicioEnviarCorreo a = new PropiedadServicioEnviarCorreo();
			List<ArchivoBean>archivos = new ArrayList<ArchivoBean>();
			archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/0-convenciones_codigo_java.pdf")));
			archivos.add(new ArchivoBean(new File("C:/Users/MCH sistematizando/Downloads/03163695394 RECAUDO JUNIO 2016.csv")));
			a.setAsunto("Probando envío de correo")
			.setDestinatario("cbeltran@sistematizando.com")
			//		.setMensaje("<table border=\"1\"><tr><td>EMPRESA</td><td>FACTURA</td><td>FECHA_INICIO</td><td>VENCIMIENTO</td><td>VALOR</td><td>IVA</td><td>DESC1</td><td>DESC2</td><td>DESC3</td><td>DESC4</td><td>DESC5</td><td>PARA</td><td>MIGO</td></tr><tr><td>TULUA</td><td>106391</td><td>2016-12-06</td><td>2017-12-30</td><td>4900000.00</td><td>16</td><td>Prestacion de Servicio - Contrato No. 1200-06-02-329-16</td><td>Outsoursing de nomina</td><td>Correspondiente a:   Mes de Noviembre de 2016</td><td>null</td><td>null</td><td>null</td><td>null</td></tr><tr><td>TULUA</td><td>106392</td><td>2016-12-06</td><td>2017-12-30</td><td>4900000.00</td><td>16</td><td>Prestacion de Servicio - Contrato No. 1200-06-02-329-16</td><td>Outsoursing de nomina</td><td>Correspondiente a:   Mes de Noviembre de 2016</td><td>null</td><td>null</td><td>null</td><td>null</td></tr><tr><td>FIDUCOLOMBIA</td><td>106388</td><td>2016-12-05</td><td>2017-12-30</td><td>3492986.00</td><td>16</td><td>Prueba</td><td>Prueba 2</td><td>Prueba 3</td><td>Diciembre de  2016</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>5001660184</td></tr><tr><td>FIDUCOLOMBIA</td><td>106389</td><td>2016-12-05</td><td>2017-12-30</td><td>3821500.00</td><td>16</td><td>Prueba</td><td>Prueba 3</td><td>Prueba 2</td><td>Diciembre de  2016</td><td>Anexo A - Orden de Compra/Servicio No. 4800333987</td><td>Luis Fernando Ladino</td><td>5001635143</td></tr><tr><td>FIDUCOLOMBIA</td><td>106390</td><td>2016-12-05</td><td>2017-12-30</td><td>292600.00</td><td>16</td><td>Prueba</td><td>Prueba 4</td><td>Prueba 1</td><td>Facturas emitidas en Noviembre:  67</td><td>Facturas emitidas en Diciembre: 66</td><td>Albert Russy</td><td>5001659043</td></tr><tr><td>FIDUCOLOMBIA</td><td>106384</td><td>2016-12-05</td><td>2017-12-30</td><td>3492986.00</td><td>16</td><td>Prueba</td><td>Prueba 2</td><td>Prueba 3</td><td>Diciembre de  2016</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>5001660184</td></tr><tr><td>FIDUCOLOMBIA</td><td>106385</td><td>2016-12-05</td><td>2017-12-30</td><td>3821500.00</td><td>16</td><td>Prueba</td><td>Prueba 3</td><td>Prueba 2</td><td>Diciembre de  2016</td><td>Anexo A - Orden de Compra/Servicio No. 4800333987</td><td>Luis Fernando Ladino</td><td>5001635143</td></tr><tr><td>FIDUCOLOMBIA</td><td>106386</td><td>2016-12-05</td><td>2017-12-30</td><td>292600.00</td><td>16</td><td>Prueba</td><td>Prueba 4</td><td>Prueba 1</td><td>Facturas emitidas en Noviembre:  67</td><td>Facturas emitidas en Diciembre: 66</td><td>Albert Russy</td><td>5001659043</td></tr><tr><td>FIDUCOLOMBIA</td><td>106394</td><td>2016-12-05</td><td>2017-12-30</td><td>3492986.00</td><td>16</td><td>Prueba</td><td>Prueba 2</td><td>Prueba 3</td><td>Diciembre de  2016</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>5001660184</td></tr><tr><td>FIDUCOLOMBIA</td><td>106395</td><td>2016-12-05</td><td>2017-12-30</td><td>3821500.00</td><td>16</td><td>Prueba</td><td>Prueba 3</td><td>Prueba 2</td><td>Diciembre de  2016</td><td>Anexo A - Orden de Compra/Servicio No. 4800333987</td><td>Luis Fernando Ladino</td><td>5001635143</td></tr><tr><td>FIDUCOLOMBIA</td><td>106396</td><td>2016-12-05</td><td>2017-12-30</td><td>292600.00</td><td>16</td><td>Prueba</td><td>Prueba 4</td><td>Prueba 1</td><td>Facturas emitidas en Noviembre:  67</td><td>Facturas emitidas en Diciembre: 66</td><td>Albert Russy</td><td>5001659043</td></tr><tr><td>FIDUCOLOMBIA</td><td>106380</td><td>2016-12-05</td><td>2017-12-30</td><td>3492986.00</td><td>16</td><td>Ref: Contrato de Arrendamiento del Software de Cartera financiera Charry Web para: La administracin y gentin de Cartera de</td><td>P.A. UNIANDES</td><td>Servicio correspondiente al mes de</td><td>Diciembre de  2016</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>null</td></tr><tr><td>FIDUCOLOMBIA</td><td>106381</td><td>2016-12-05</td><td>2017-12-30</td><td>3821500.00</td><td>16</td><td>Ref: Contrato de Arrendamiento del Software de Cartera financiera Charry Web para: La administracin y gentin de Cartera de</td><td>Fiduciaria Bancolombia S.A.</td><td>Servicio correspondiente al mes de</td><td>Diciembre de  2016</td><td>Anexo A - Orden de Compra/Servicio No. 4800333987</td><td>Luis Fernando Ladino</td><td>null</td></tr><tr><td>FIDUCOLOMBIA</td><td>106382</td><td>2016-12-05</td><td>2017-12-30</td><td>194600.00</td><td>16</td><td>Ref: Contrato de Arrendamiento del Software de Cartera financiera Charry Web para: La Facturacion del</td><td>Centro comercial San Rafael</td><td>Servicio correspondiente a Oct  y  Nov de 2016</td><td>Facturas Emitidas desde la 10001 a la 10139</td><td>Anexo M - Orden de Compra/Servicio No.</td><td>Albert Russy</td><td>null</td></tr><tr><td>FIDUCOLOMBIA</td><td>106398</td><td>2016-12-05</td><td>2017-12-30</td><td>3492986.00</td><td>16</td><td>Prueba</td><td>Prueba 2</td><td>Prueba 3</td><td>Diciembre de  2016</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>5001660184</td></tr><tr><td>FIDUCOLOMBIA</td><td>106399</td><td>2016-12-05</td><td>2017-12-30</td><td>3821500.00</td><td>16</td><td>Prueba</td><td>Prueba 3</td><td>Prueba 2</td><td>Diciembre de  2016</td><td>Anexo A - Orden de Compra/Servicio No. 4800333987</td><td>Luis Fernando Ladino</td><td>5001635143</td></tr><tr><td>FIDUCOLOMBIA</td><td>106400</td><td>2016-12-05</td><td>2017-12-30</td><td>292600.00</td><td>16</td><td>Prueba</td><td>Prueba 4</td><td>Prueba 1</td><td>Facturas emitidas en Noviembre:  67</td><td>Facturas emitidas en Diciembre: 66</td><td>Albert Russy</td><td>5001659043</td></tr><tr><td>FIDUCOLOMBIA</td><td>106402</td><td>2016-12-05</td><td>2017-12-30</td><td>3492986.00</td><td>16</td><td>Prueba</td><td>Prueba 2</td><td>Prueba 3</td><td>Diciembre de  2016</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>5001660184</td></tr><tr><td>FIDUCOLOMBIA</td><td>106403</td><td>2016-12-05</td><td>2017-12-30</td><td>3821500.00</td><td>16</td><td>Prueba</td><td>Prueba 3</td><td>Prueba 2</td><td>Diciembre de  2016</td><td>Anexo A - Orden de Compra/Servicio No. 4800333987</td><td>Luis Fernando Ladino</td><td>5001635143</td></tr><tr><td>FIDUCOLOMBIA</td><td>106401</td><td>2016-12-01</td><td>2017-12-30</td><td>3729109.00</td><td>16</td><td>Prueba</td><td>Prueba 1</td><td>Prueba 4</td><td>Retroactivo:                Oct 2016                              $236,123</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>5001660184</td></tr><tr><td>FIDUCOLOMBIA</td><td>106397</td><td>2016-12-01</td><td>2017-12-30</td><td>3729109.00</td><td>16</td><td>Prueba</td><td>Prueba 1</td><td>Prueba 4</td><td>Retroactivo:                Oct 2016                              $236,123</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>5001660184</td></tr><tr><td>FIDUCOLOMBIA</td><td>106379</td><td>2016-12-01</td><td>2017-12-30</td><td>4300000.00</td><td>16</td><td>Ref: Contrato de Arrendamiento del Software de Cartera financiera Charry Web para: La administracin y gentin de Cartera de</td><td>Terminames Aeroportuarias de Oriente</td><td>Servicio correspondiente al mes de</td><td>Diciembre de 2016</td><td>Anexo L - Orden de Compra/Servicio No. 4800645849</td><td>Daniel Humberto Vargas</td><td>null</td></tr><tr><td>FIDUCOLOMBIA</td><td>106387</td><td>2016-12-01</td><td>2017-12-30</td><td>3729109.00</td><td>16</td><td>Prueba</td><td>Prueba 1</td><td>Prueba 4</td><td>Retroactivo:                Oct 2016                              $236,123</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>5001660184</td></tr><tr><td>FIDUCOLOMBIA</td><td>106393</td><td>2016-12-01</td><td>2017-12-30</td><td>3729109.00</td><td>16</td><td>Prueba</td><td>Prueba 1</td><td>Prueba 4</td><td>Retroactivo:                Oct 2016                              $236,123</td><td>Orden de Compra/Servicio No. 4800333991</td><td>Luis Guillermo Castaeda</td><td>5001660184</td></tr></table>")
			.setMensaje("aaj")
			.setArchivos(null);
			a.setNegocio("FacturacionMch");
			//		//				.setArchivo(new ArchivoBean("1Liq Rec Emfermeria Abril - PRUEBA.xlsx",new File("E:/ARCHIVOBASE/1Liq Rec Emfermeria Abril - PRUEBA.xlsx")));
			String r = new ActividadEnviarCorreo().enviarEmail(a, false);
			System.out.println(r);
		}catch(Exception e){

		}
	}
}
