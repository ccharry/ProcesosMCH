package com.mch.propiedades.servicios;

import java.util.List;

import com.mch.bean.ArchivoBean;

/**
 * @author Camilo
 * 05/09/2016
 */
public class PropiedadServicioEnviarCorreo {

	private String destinatario = null;
	private String asunto = null;
	private String mensaje = null;
	private String negocio = null; 
	private String dataBase = null;
	private List<ArchivoBean>archivos = null;
	
	/**
	 * Cuando se quiere enviar un correo a
	 * un solo destinatario se modifica
	 * este atributo.
	 * <h2>¡No hay que modificar los atributos que no va a usar!</h2>
	 * <br>
	 * @param destinatario
	 * @return this
	 */
	public PropiedadServicioEnviarCorreo setDestinatario(String destinatario) {
		this.destinatario = destinatario;
		return this;
	}

	public PropiedadServicioEnviarCorreo setAsunto(String asunto) {
		this.asunto = asunto;
		return this;
	}

	/**
	 * Se puede enviar un mensaje en formato HTML
	 * o simplemente un texto.
	 * <h2>¡No hay que modificar los atributos que no va a usar!</h2>
	 * <br>
	 * @param mensaje
	 * @return this
	 */
	public PropiedadServicioEnviarCorreo setMensaje(String mensaje) {
		this.mensaje = mensaje;
		return this;
	}

	/**
	 * Si el necesita enviar un correo con
	 * varios archivos adjuntos, hay que 
	 * modificar este atributo con un List
	 * de tipo InputStream.
	 * <h2>¡No hay que modificar los atributos que no va a usar!</h2>
	 * <br> 
	 * @param archivos
	 * @return
	 */
	public PropiedadServicioEnviarCorreo setArchivos(List<ArchivoBean> archivos) {
		this.archivos = archivos;
		return this;
	}

	public String getDestinatario() {
		return destinatario;
	}
	public String getAsunto() {
		return asunto;
	}
	public String getMensaje() {
		return mensaje;
	}

	public List<ArchivoBean> getArchivos() {
		return archivos;
	}

	public String getNegocio() {
		return negocio;
	}

	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}
	
}
