package com.mch.utilidades;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Camilo
 * 07/09/2016
 */
public class UtilJwt {

	private static UtilJwt instancia = null;

	private UtilJwt(){}


	public static UtilJwt getInstancia(){
		if(instancia == null){
			instancia = new UtilJwt();
		}
		return instancia;
	}

	public String generarToken(String DB){
		Calendar date = Calendar.getInstance();
		long h= date.getTimeInMillis();
		Date expiracion=new Date(h + (10 * 60000)); // 10 MINUTOS
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dataBase", DB);
		return Jwts.builder().signWith(SignatureAlgorithm.HS256,("ProcesosMCH".getBytes())).setClaims(map).setExpiration(expiracion).compact();
	}
}
