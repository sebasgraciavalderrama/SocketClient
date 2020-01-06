package com.co.ibm.wme.sender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
//import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMessage;

import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;
//import org.codemonkey.simplejavamail.TransportStrategy;


import com.co.ibm.wme.socketclient.LectorPropiedades;
import com.co.ibm.wme.socketclient.SingletonLectorPropiedades;

public class Correo
{				
	public Correo(String cuerpoCorreo) throws IOException
	{

		Email email = new Email();


		//archivo de propiedades
		LectorPropiedades lectorPropiedades=SingletonLectorPropiedades.getInstance();

		String listaCorreos=lectorPropiedades.BuscarPropiedades("lista_correo_infovalmer");
		String cliente=lectorPropiedades.BuscarPropiedades("cliente");
		String dominioCorreo=lectorPropiedades.BuscarPropiedades("dominio");
		String correoAdmin=lectorPropiedades.BuscarPropiedades("correoAdmin");
		String password=lectorPropiedades.BuscarPropiedades("password");
		int puertoCorreo=Integer.parseInt(lectorPropiedades.BuscarPropiedades("puerto"));
		String asunto=lectorPropiedades.BuscarPropiedades("asunto");
		String ruta_Adjunto=lectorPropiedades.BuscarPropiedades("ruta_archivo_pdf");
		//flag para saber si envia o no correo
		Boolean envioCorreo=Boolean.parseBoolean(lectorPropiedades.BuscarPropiedades("envioCorreo"));
		//Boolean envioPushOver=Boolean.parseBoolean(lectorPropiedades.BuscarPropiedades("envioPushOver"));
		//String nombrePushOver=lectorPropiedades.BuscarPropiedades("nombrePushOver");
		//String correoPushOver=lectorPropiedades.BuscarPropiedades("correoPushOver");


		//Variables de la clase

		//Parceo el Adjunto para poderlo enviar segun requisito de clase
		//		String filename = ruta_Adjunto+nombreArchivo;
		//DataSource source = new FileDataSource(filename); 

		int year = Calendar.getInstance().get(Calendar.YEAR);

		String cuerpoCorreoStr="";
		@SuppressWarnings("unused")
		int i=0;
		
		cuerpoCorreoStr = cuerpoCorreo;
		
		/*

		//Leo del texto curpo y lo coloco en un String
		BufferedReader br = new BufferedReader(new FileReader(cuerpoCorreo));
		try {
			StringBuilder sb = new StringBuilder();
			String lineaCuerpoCorreo = br.readLine();

			while (lineaCuerpoCorreo != null) {
				sb.append(lineaCuerpoCorreo);
				sb.append(System.lineSeparator());
				lineaCuerpoCorreo = br.readLine();
			}
			cuerpoCorreoStr = sb.toString();
		} finally {
			br.close();
		} 
		
		*/


		Date fecha = Calendar.getInstance().getTime();
		//System.out.println("		Creando cuerpo de Correo");
		System.out.println("		Enviando correo con cuerpo Correo:  " + cuerpoCorreoStr);


		//email.setFromAddress("Monitoreo IBM ARGOS", "repositorio.wme@gmail.com");
		email.setFromAddress("Monitoreo IBM "+cliente, correoAdmin);

		//archivo para sacar la lista de correo
		try {
			File input = new File(listaCorreos);
			BufferedReader lector = new BufferedReader(new FileReader(input));
			String linea = lector.readLine();
			while (linea != null) 
			{				
				String nombreDestinatario = linea.split(";")[0];										
				String correoDestinatario = linea.split(";")[1];
				//System.out.println(		nombreDestinatario +" "+correoDestinatario);
				i++; //incremento

				email.addRecipient(nombreDestinatario, correoDestinatario, MimeMessage.RecipientType.TO);
				linea = lector.readLine();
			}

			lector.close();


		} catch (Exception e) {
			System.out.println("		Error Clase monitor lectura de destinatario");
			System.out.println(e);
			e.printStackTrace();
		}		

		if(Boolean.parseBoolean(lectorPropiedades.BuscarPropiedades("flag_alias_correo"))){
			email.setFromAddress(lectorPropiedades.BuscarPropiedades("alias_correo_nombre"),lectorPropiedades.BuscarPropiedades("alias_correo_direccion"));
		} 
		//email.addRecipient("Adolfo Reyes", "aereyes@co.ibm.com", MimeMessage.RecipientType.TO);
		
		String titulo= lectorPropiedades.BuscarPropiedades("titulo_html");

		//Asunto
		email.setSubject(cliente + ": " + asunto);

		email.setTextHTML("<body  style='font-family: arial; font-size: 14px;'><h3>"+titulo+" "+cliente +"</h3><p>Se genera un reporte</p><p>Fecha: " + fecha + "</p><p><pre>"+  cuerpoCorreoStr   +"</pre></p><p>..::IBM Colombia::..</p><p>..::"+year+"::..</p></body>");

		//Tiene adjunto
		//if (flagAdjunto.equals("true") || flagAdjunto.equals("TRUE") ){
			//System.out.println("		Variable de correo en TRUE se envia Adjunto en el correo");
			//email.addAttachment(nombreArchivo, source);
		//}
		//else {
			//System.out.println("		Variable de correo en FALSE no se envia Adjunto en el correo");
		//}	


		//new Mailer("mail.adolfoereyes.net", Integer.valueOf(26), "ibm@adolfoereyes.net", "6156336").sendMail(email);



		
			new Mailer(dominioCorreo, Integer.valueOf(puertoCorreo), correoAdmin, password).sendMail(email);
		
		

		//otros metodos de envio de correo
		//gmail por ssl
		//new Mailer("smtp.gmail.com", Integer.valueOf(465), "repositorio.wme@gmail.com", "wme123456", TransportStrategy.SMTP_SSL).sendMail(email);

		//gmail por tls
		//new Mailer("smtp.gmail.com", Integer.valueOf(587), "repositorio.wme@gmail.com", "wme123456", TransportStrategy.SMTP_TLS).sendMail(email);

		//gmail por tls
		//new Mailer("smtp.gmail.com", Integer.valueOf(587), "repositorio.wme@gmail.com", "wme123456", TransportStrategy.SMTP_TLS).sendMail(email);


		System.out.println("		Fin envio correo");   
	}
}
