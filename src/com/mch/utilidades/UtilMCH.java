package com.mch.utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;


/**
 * @author Camilo
 * 05/09/2016
 */
public class UtilMCH {

	private static String ruta = null;

	public static String getRutaProyecto() {
		if(ruta == null){
			//			ruta = UtilMCH.class.getClassLoader().getResource("").getPath().replace("/classes", "").replace("/WEB-INF", "").replaceFirst("/", "");
			//			System.out.println("------------.................................---------");
			//			ClassLoader loader = UtilMCH.class.getClassLoader();
			//	        System.out.println(loader.getResource("utilidades/UtilMCH.class"));
			File f = new File(System.getProperty("java.class.path"));
			File dir = f.getAbsoluteFile().getParentFile();
			String path = dir.toString();
			if(path.split(";").length > 2){
				//				ruta = path.substring(0, path.indexOf(";"))+"/reportes/";
				ruta = path.substring(0, path.indexOf(";"));
			}else{
				ruta = (path+"/ProcesosMCH/bin/");
			}
		}
		if(!ruta.contains(":")){
			ruta = "/"+ruta;
		}
		return ruta;
	}

	/**
	 * Metodo que escribe un archivo dependiendo 
	 * de un objeto InputStream
	 * @param nombreArchivo
	 * @param in
	 * @return String, ruta del archivo en disco
	 */
	public static String escribirArchivoTemporalDesdeInputStream(String nombreArchivo, InputStream in, String carptaTemp, boolean replaceBin, long time) throws IOException{
		File f = null, ft = null;
		String ruta = UtilMCH.getRutaProyecto()+"/"+carptaTemp+"/temporal_"+time+"/"+nombreArchivo,
				rutaTemp = UtilMCH.getRutaProyecto()+"/"+carptaTemp+"/temporal_"+time;

		if(replaceBin == true){
			ruta = ruta.replace("bin/", "");
			rutaTemp = rutaTemp.replace("bin/", "");
		}
		ft = new File(rutaTemp);
		ft.mkdir();

		f = new File(ruta);

		OutputStream salida=new FileOutputStream(f);
		byte[] buf =new byte[1024];
		int len;
		while((len=in.read(buf))>0){
			salida.write(buf,0,len);
		}
		salida.close();
		in.close();
		Logger.getLogger(UtilMCH.class.getName()).log(Level.INFO,"Se realizo la conversion con exito");
		return f.toPath().toString();
	}

	/**
	 * Metodo crea un Map con los parametros
	 * definidos en la clase de configuracion
	 * de propiedades, se hace una reflection
	 * sobre la instancia que llega como parametro.
	 * @param obj
	 * @return Map<String, Object> con la informacion de los parametros
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object>generarMapPorPropiedad(Object obj) throws IllegalArgumentException, IllegalAccessException{
		Map<String, Object> r = new HashMap<String, Object>();
		Class<?> c = obj.getClass();
		Field[] v = c.getDeclaredFields();
		for(Field a : v){
			a.setAccessible(true);
			if( !(a.getType().isInterface()) && !(a.getType().isArray())){
				r.put(a.getName(), a.get(obj));
			}
		}
		return r;
	}

	/**
	 * ADD @author Camilo
	 *     @since  27/04/2017
	 * @param texto
	 * @return
	 */
	public static String encriptar(String texto) {

		String secretKey = "qualityinfosolutions"; //llave para encriptar datos
		String base64EncryptedString = "";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception ex) {
		}
		return base64EncryptedString;
	}


	public static String getDataBaseName(String negocio) throws JSONException, IOException{
		return UtilLecturaPropiedades.getInstancia().getPropJson("negocio", negocio).getString("dataBase");
	}

	public static String getEmailSoporte(String negocio) throws JSONException, IOException{
		return UtilLecturaPropiedades.getInstancia().getPropJson("negocio", negocio).getString("correo");
	}

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		System.out.println(getRutaProyecto());
	}
}
