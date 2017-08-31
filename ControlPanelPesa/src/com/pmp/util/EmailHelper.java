package com.pmp.util;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class EmailHelper {
	// private static final String SMTP_HOST_NAME = "mail.pesa.com.br";//192.168.2.12//dominio pesa.local
	 private static final String SMTP_HOST_NAME = "mail.pesa.com.br";//192.168.2.12//dominio pesa.local
	 private static final String SMTP_AUTH_USER = "control_dse@pesa.com.br";
	 private static final String SMTP_AUTH_PWD  = "w8o#ay3*";
//	 private static final String SMTP_HOST_NAME = "192.168.2.12";
//	 private static final String SMTP_AUTH_USER = "control_dse@pesa.com.br";
//	 private static final String SMTP_AUTH_PWD  = "w8o#ay3*";

	public boolean sendSimpleMail (String msg, String subject, String to, File file){
		try{
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			//props.put("mail.smtp.auth", "true");
			//props.put("mail.smtp.starttls.enable", "true");
			//props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			//props.put("mail.smtp.port", "465");

			
			Authenticator auth = new SMTPAuthenticator();
			Session mailSession = Session.getDefaultInstance(props);
			// uncomment for debugging infos to stdout
			// mailSession.setDebug(true);
			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);
			//message.setContent(msg, "text/plain");
			message.setSubject(new String(subject.getBytes("UTF-8"), "UTF-8"));
			message.setFrom(new InternetAddress("control_dse@pesa.com.br"));
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));
//		     Cria a primeira parte do email (com o corpo do texto)  
			MimeBodyPart mbp1 = new MimeBodyPart();  
			message.setContent(new String(msg.getBytes("UTF-8"), "UTF-8"), "text/html");
			
			//Cria a segunda parte do email  
			MimeBodyPart mbp2 = new MimeBodyPart(); 
			//Atacha o arquivo  
			FileDataSource fds = new FileDataSource(file);  
			mbp2.setDataHandler(new DataHandler(fds));  
			mbp2.setFileName(fds.getName());
			
			//Cria um multipart adicionando aquelas 2 primeiras partes  
			Multipart mp = new MimeMultipart();  
			mp.addBodyPart(mbp1);  
			mp.addBodyPart(mbp2);  
			message.setContent(mp);
			transport.connect();

			transport.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));
			transport.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
		
	public boolean sendSimpleMail(String msg, String subject, String to) {
		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			//props.put("mail.smtp.auth", "true");
			// props.put("mail.smtp.starttls.enable", "true");
			// props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			// props.put("mail.smtp.port", "465");

			Authenticator auth = new SMTPAuthenticator();
			Session mailSession = Session.getDefaultInstance(props, auth);
			// uncomment for debugging infos to stdout
			// mailSession.setDebug(true);
			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(new String(subject.getBytes("UTF-8"), "UTF-8"));
			message.setContent(new String(msg.getBytes("UTF-8"), "UTF-8"), "text/html");
			message.setFrom(new InternetAddress("control_dse@pesa.com.br"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			transport.connect();

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
		
	public static void main(String[] args) {
		//File file = new File("C:/Documents and Settings/RDR/Meus documentos/Downloads/bibliografia.pdf");
		new EmailHelper().sendSimpleMail("opa", "teste", "cioletti.rodrigo@gmail.com");
	}
}
