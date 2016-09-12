package com.mch.tareas;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.IOUtils;

/**
 * @author Camilo
 * 01/07/2016
 */
public class TareaGeneradorZip {

	/**
	 * Metodo que crea un archivo Zip de acuerdo
	 * a los archivos que se encuentren en una 
	 * ruta temporal.
	 * @param reportes
	 * @param ruta
	 * @param liquidacion
	 * @param empresa
	 * @throws ZipException
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public InputStream crearZipPorRuta( String ruta, String liquidacion, String empresa) throws ZipException, InterruptedException, IOException{
		String rutaTemp = ruta+"/"+liquidacion+".zip"; 
		File archivo = new File(rutaTemp);

		while(archivo.isFile()){
			rutaTemp = ruta+"/"+liquidacion+(int)(Math.random()*25+1)+".zip";
			archivo = new File(rutaTemp);
		}

		ZipFile zipFile = new ZipFile(rutaTemp);

		ArrayList<File> archivos = new ArrayList<File>();


		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 
		parameters.setEncryptFiles(true);
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		parameters.setPassword(empresa);


		for(File a : new File(ruta).listFiles()){
			archivos.add(a);
		}

		zipFile.addFiles(archivos, parameters);

		InputStream inputTemp = new FileInputStream(new File(rutaTemp));
		byte[] aa = IOUtils.toByteArray(inputTemp);

		inputTemp.close();

		return new ByteArrayInputStream(aa);
	}


	/**
	 * Metodo que crea un Zip de acuerdo a<br>
	 * la lista que llega como parametro,
	 * esta lista es de tipo Object[], en<br>
	 * la posicion 0 almacena el nombre del
	 * archivo, en la posicion 1 almacena el<br>
	 * InputStream del archivo que se quiere
	 * agregar.
	 * @param archivos
	 * @param ruta
	 * @param empresa
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public void crearZipPorListaInputStream(List<Object[]>archivos, String ruta, long timer) throws ZipException, IOException{

		String rutaTemp = ruta+"/Reportes"+timer+".zip";
		ZipFile zipFile = new ZipFile(rutaTemp);
		for(Object[] obj : archivos){
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setFileNameInZip((String)obj[0]);
			parameters.setSourceExternalStream(true);
			zipFile.addStream((InputStream)obj[1], parameters);
		}
	}

}
