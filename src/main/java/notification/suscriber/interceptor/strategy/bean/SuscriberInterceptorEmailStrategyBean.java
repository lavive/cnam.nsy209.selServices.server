package notification.suscriber.interceptor.strategy.bean;

import java.util.List;
import java.util.Properties;

import javax.ejb.Singleton;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import dao.entity.MemberEntity;
import notification.suscriber.interceptor.strategy.local.SuscriberInterceptorEmailStrategy;
import notification.suscriber.local.SuscriberLocal;

/**
 * Bean sending notification by email
 * 
 * @author lavive
 * 
 * note: to implement, this code is only test
 *
 */

@Singleton
public class SuscriberInterceptorEmailStrategyBean implements SuscriberInterceptorEmailStrategy {

	@Override
	public void execute(SuscriberLocal suscriber) {
		List<MemberEntity> membersToSend = suscriber.getMembersToNotify();
		
		for(MemberEntity member:membersToSend){
			if(member.getEmail() != null){
				String to = member.getEmail();
				String subject = suscriber.getNotification().getTitle();
				String text = suscriber.getNotification().getText();
				sendEmail(to,subject,text);
			}
		}

	}
	
	private void sendEmail(String to,String subject,String text){
		
		// Recipient's email ID needs to be mentioned.
	    //String to = "vivien.sere@gmail.com";
	
	    // Sender's email ID needs to be mentioned
	    String from = "bureau@SELdeSave.fr";
	    final String username = "bureau";//change accordingly
	    final String password = "password";//change accordingly
	
	    // Assuming you are sending email through gmail
	    String host = "smtp.gmail.com";
	
	    Properties props = new Properties();
	    props.put("mail.smtp.host", host);
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
	
	    // Get the Session object.
	    Session session = Session.getInstance(props,
	       new javax.mail.Authenticator() {
	          protected PasswordAuthentication getPasswordAuthentication() {
	             return new PasswordAuthentication(username, password);
		   }
	       });
	
	    try {
		   // Create a default MimeMessage object.
		   Message message = new MimeMessage(session);
		
		   // Set From: header field of the header.
		   message.setFrom(new InternetAddress(from));
		
		   // Set To: header field of the header.
		   message.setRecipients(Message.RecipientType.TO,
	             InternetAddress.parse(to));
		
		   // Set Subject: header field
		   message.setSubject(subject);
		
		   // Now set the actual message
		   message.setText(text);
	
		   // Send message
		   Transport.send(message);
	
		   System.out.println("Sent message successfully....");
	
	    } catch (MessagingException e) {
	       throw new RuntimeException(e);
	    }
	 }

}
