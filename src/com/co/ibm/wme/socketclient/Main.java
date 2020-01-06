package com.co.ibm.wme.socketclient;

import com.co.ibm.wme.sender.Correo;

public class Main {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		LectorPropiedades lectorPropiedades = SingletonLectorPropiedades.createInstance(args[0]);

        FileManager fm = new FileManager();
        fm.connectToSocket();
        
        if(fm.fileExists()){
        	System.out.println("El archivo existe, enviando correo");
        	String cuerpoCorreo = fm.getFileContent();
        	Correo correo = new Correo(cuerpoCorreo);
        }
        
	}

}
