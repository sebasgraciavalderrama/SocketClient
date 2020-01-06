package com.co.ibm.wme.socketclient;

import java.io.IOException;

public class SingletonLectorPropiedades {

	private static LectorPropiedades instance = null;

	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private SingletonLectorPropiedades() {
	}

	public static LectorPropiedades getInstance() {
		return instance;
	}
	public static LectorPropiedades createInstance(String args) throws IOException {
		if(instance == null) {
			instance = new LectorPropiedades(args);
		}
		return instance;
	}
}
