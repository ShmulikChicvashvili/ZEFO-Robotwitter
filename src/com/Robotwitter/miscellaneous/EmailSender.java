/**
 * 
 */

package com.Robotwitter.miscellaneous;


import javax.mail.*;
import javax.mail.internet.*;




public class EmailSender implements IEmailSender
{
	Session session;
	
	
	
	/* (non-Javadoc) @see management.IEmailSender#sendEmail(management.Email) */
	public void sendEmail(EmailMessage mail) throws MessagingException
	{
		
		Message message = new MimeMessage(this.session);
		message.setFrom(new InternetAddress(mail.getEmailAddressFrom()));
		message.setContent(mail.getMsgText(), "text/html");
		message.setRecipients(
			Message.RecipientType.TO,
			InternetAddress.parse(mail.getEmailAddressTo()));
		message.setSubject(mail.getMsgSubject());
	
		Transport.send(message); // Send email message
		
		System.out.println("sent email successfully!");
		
	}
	
	
	/* (non-Javadoc) @see
	 * management.IEmailSender#startSession(management.IEmailSession) */
	public void startSession(IEmailSession session)
	{
		this.session = session.getSession();
	}
	
}
