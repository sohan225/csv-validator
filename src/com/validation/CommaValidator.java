package com.validation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;


public class CommaValidator {

	private SendEmailNotification emailSender = new SendEmailNotification();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			int columnNumber;
			Options options = new Options();
			options.addOption("c", "config", true, "csv file name");
			options.addOption("n", "colNum", true, "Number of Columns in file");
			System.out.println("Comma validator program started...");
			CommandLineParser parser = new PosixParser();
			CommandLine cl = parser.parse(options, args, false);
			if(args.length == 0){
				return;
			}
			columnNumber = Integer.parseInt(cl.getOptionValue("n"));
			String fileName = cl.getOptionValue("c", null);
			if (fileName == null){
				return;
			}
			/*File csvFileName = new File(fileName);
			if(!csvFileName.exists()){
				return;
			}*/
			processFile(fileName, columnNumber);
			System.out.println("Comma validator program end...");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private static void processFile(String fileName, int columnNumber){
		try{
			String line;
			boolean flag=false;
			int counter=0;
			SendEmailNotification emailSender = new SendEmailNotification();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
			while((line = br.readLine()) != null){
				String [] columnData = line.split(Constants.COMMA_DELEMITER);
				if(columnData.length != columnNumber){
					counter++;
					flag = true;
				}
			}
			br.close();
			if(flag == true){
				System.out.println("There are "+ counter +" records are in incorrect format.");
				System.out.println("Sending email...");
				//sendMail(counter);
				emailSender.sendMail("sohan.225@gmail.com", "prasenjitnit2011@gmail.com",
						"Processed file states", "There are "+ counter +" records incorrect !");
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*private static void sendMail(int numberOfRedcords){
		
		Properties props = new Properties();  
		  props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class",  
		            "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465");  
		   
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication("sohan.225@gmail.com","9858022609");//change accordingly  
		   }  
		  });  
		   
		  //compose message  
		  try {  
		   MimeMessage message = new MimeMessage(session);  
		   message.setFrom(new InternetAddress("sohan.225@gmail.com"));//change accordingly  
		   message.addRecipient(Message.RecipientType.TO,new InternetAddress("prasenjitnit2011@gmail.com"));  
		   message.setSubject("Processed file states");  
		   message.setText("There are "+ numberOfRedcords +" records incorrect !");  
		     
		   //send message  
		   Transport.send(message);  
		  
		   System.out.println("message sent successfully");  
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}*/

}
