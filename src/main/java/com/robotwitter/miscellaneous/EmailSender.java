/**
 *
 */

package com.robotwitter.miscellaneous;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class EmailSender implements IEmailSender
{
	public EmailSender(final IEmailSession session)
	{
		this.session = session;
	}
	
	
	/* (non-Javadoc) @see management.IEmailSender#sendEmail(management.Email) */
	@Override
	public void sendEmail(final EmailMessage mail) throws MessagingException
	{
		final Session mailSession = session.getSession();
		final Message message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(mail.getEmailAddressFrom()));
		message.setText(mail.getMsgText());
		message.setRecipients(
			Message.RecipientType.TO,
			InternetAddress.parse(mail.getEmailAddressTo()));
		message.setSubject(mail.getMsgSubject());
		
		Transport.send(message); // Send email message
	}
	
	
	
	IEmailSession session;

}
