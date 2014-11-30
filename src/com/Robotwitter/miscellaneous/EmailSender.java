/**
 * 
 */

package com.Robotwitter.miscellaneous;


import javax.mail.*;
import javax.mail.internet.*;




public class EmailSender implements IEmailSender
{
	IEmailSession session;
	
	public EmailSender(IEmailSession session) {
		this.session = session;
	}
	
	/* (non-Javadoc) @see management.IEmailSender#sendEmail(management.Email) */
	public void sendEmail(EmailMessage mail) throws MessagingException
	{
		Session mailSession = this.session.getSession();
		Message message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(mail.getEmailAddressFrom()));
		message.setContent(mail.getMsgText(), "text/html"); //$NON-NLS-1$
		message.setRecipients(
			Message.RecipientType.TO,
			InternetAddress.parse(mail.getEmailAddressTo()));
		message.setSubject(mail.getMsgSubject());
	
		Transport.send(message); // Send email message
	}
	
}
