package com.mch.excepciones;
/**
 * @author Camilo
 * 06/09/2016
 */
public class ExcepcionMch extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcepcionMch(){
		super();
	}
	public ExcepcionMch(String message){
		super(message);
	}
	public ExcepcionMch(Throwable message){
		super(message);
	}
}
