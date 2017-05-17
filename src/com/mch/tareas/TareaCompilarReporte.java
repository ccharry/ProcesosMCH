/**
 * 
 */
package com.mch.tareas;



import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import com.mch.utilidades.UtilMCH;

/**
 * @author Juan Camilo
 *
 */
public class TareaCompilarReporte {


	public static void main(String[] args) {
		TareaCompilarReporte compilar = new TareaCompilarReporte();
		String reporte = "facturaDevisab";
		System.out.println(compilar.compilar(reporte));
		

	}

	
	public String compilar(String reporte){
		String respuesta ="";
		
		String ruta = UtilMCH.getRutaProyecto().replace("bin", "reportes/");
		
		String origen = ruta+"reportes/"+reporte+"/"+reporte+".jrxml";
		String destino = ruta+"reportes/"+reporte+"/"+reporte+".jasper";
//		String origen = "E:\\RepositorioGITPortal\\ProcesosMCH\\reportes\\facturaSanRafael\\facturaSanRafael.jrxml";
//		String destino = "E:\\RepositorioGITPortal\\ProcesosMCH\\reportes\\facturaSanRafael\\facturaSanRafael.jasper";
//		
//		System.out.println(origen+"\n"+destino);
		
		  try{ 
		      JasperCompileManager.compileReportToFile(origen,destino);
		      respuesta = "Reporte Compilado con exito";
		    }     
		    catch (JRException e) {
		    	System.out.println("error"+e.getMessage());    	
		    	respuesta = "No se pudo compilar el reporte (" + reporte + ") ERROR--> " + e.getMessage();
		      throw new RuntimeException("No se pudo compilar el reporte (" + reporte + ") ERROR--> " + e.getMessage());
		    }
		
		return respuesta;
	}
	
}
