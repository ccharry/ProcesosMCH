package com.mch.bean;

import java.util.List;

/**
 * @author Camilo
 * 05/09/2016
 */
public class PropiedadesEnvioCorreoBean {

	private String destinatario = null;
	private String asunto = null;
	private String mensaje = null;
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
	public PropiedadesEnvioCorreoBean setDestinatario(String destinatario) {
		this.destinatario = destinatario;
		return this;
	}

	public PropiedadesEnvioCorreoBean setAsunto(String asunto) {
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
	public PropiedadesEnvioCorreoBean setMensaje(String mensaje) {
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
	public PropiedadesEnvioCorreoBean setArchivos(List<ArchivoBean> archivos) {
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
}
