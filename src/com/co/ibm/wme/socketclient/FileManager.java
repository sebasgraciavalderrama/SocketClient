package com.co.ibm.wme.socketclient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class FileManager {
	
	private static LectorPropiedades lectorPropiedades=SingletonLectorPropiedades.getInstance();
    public final static int SOCKET_PORT = Integer.parseInt( lectorPropiedades.BuscarPropiedades("socketPort") );
    //public final static String SERVER = "127.0.0.1";  // localhost
    public final static String SERVER = lectorPropiedades.BuscarPropiedades("serverIpAddress");
    public final static String FILE_TO_RECEIVED = lectorPropiedades.BuscarPropiedades("fileDestinationRoute");

    public final static int FILE_SIZE = 6022386;
	
	public void connectToSocket() throws Exception{
		
		int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
        sock = new Socket(SERVER, SOCKET_PORT);
        System.out.println("Conectando...");

        // receive file
        byte [] mybytearray  = new byte [FILE_SIZE];
        InputStream is = sock.getInputStream();
        fos = new FileOutputStream(FILE_TO_RECEIVED);
        bos = new BufferedOutputStream(fos);
        bytesRead = is.read(mybytearray,0,mybytearray.length);
        current = bytesRead;

        do {
             bytesRead =
                is.read(mybytearray, current, (mybytearray.length-current));
            if(bytesRead >= 0) current += bytesRead;
        } while(bytesRead > -1);

        bos.write(mybytearray, 0 , current);
        bos.flush();
        System.out.println("Archivo " + FILE_TO_RECEIVED
            + " obtenido (" + current + " bytes leidos)");
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
		
	}
	
	public boolean fileExists(){
		
		File f = new File(lectorPropiedades.BuscarPropiedades("socketFileRoute"));
		if(f.exists() && !f.isDirectory()) { 
			return true;
		}
		return false;
	}
	
	public String getFileContent() throws Exception{
		
		String cuerpo = "";
		
		File f = new File(lectorPropiedades.BuscarPropiedades("socketFileRoute"));
        Scanner scanner = new Scanner(f);
        
        while(scanner.hasNextLine()){
        	cuerpo += scanner.nextLine() + "\n";
        }
		
		return cuerpo;
	}

}
