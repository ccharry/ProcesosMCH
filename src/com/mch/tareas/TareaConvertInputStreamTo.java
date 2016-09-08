package com.mch.tareas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Camilo
 * 01/09/2016
 */
public class TareaConvertInputStreamTo {

	/**
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String string(InputStream is) throws IOException {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return sb.toString();

	}
}
