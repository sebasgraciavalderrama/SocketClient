package com.co.ibm.wme.socketclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class LectorPropiedades {

	private Properties propiedades;

	//Constructor
	public  LectorPropiedades(String ubicacion) throws IOException {
		InputStream in=null;
		try
		{
			in= new FileInputStream(ubicacion);
			propiedades = new Properties();
			propiedades.load(in);
		}
		catch (Exception e) {
			System.out.println("		Error Clase LectorPropiedades lectura de propiedades");
			System.out.println(e);
			e.printStackTrace();
		} finally {
			in.close();
		}
	}

	public String BuscarPropiedades(final String propiedad)
	{
		return propiedades.getProperty(propiedad);
	}

} 



