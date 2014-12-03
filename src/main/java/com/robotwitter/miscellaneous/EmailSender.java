/**
 *
 */

package com.robotwitter.miscellaneous;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
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
	public ReturnStatus sendEmail(final EmailMessage mail)
	{
		final Session mailSession = session.getSession();
		final Message message = new MimeMessage(mailSession);
		try
		{
			message.setFrom(new InternetAddress(mail.getEmailAddressFrom()));
			message.setText(mail.getMsgText());
			message.setRecipients(
				Message.RecipientType.TO,
				InternetAddress.parse(mail.getEmailAddressTo()));
			message.setSubject(mail.getMsgSubject());
			
			Transport.send(message); // Send email message
		} catch (AddressException e)
		{
			return new ReturnStatus(
				false,
				"We don't understand your email address, are you sure its typed correctly?");
		} catch (MessagingException e)
		{
			return new ReturnStatus(
				false,
				"Something went wrong with the email service, please try again in a few minutes.");
		}
		return new ReturnStatus(true, "Your mail was sent!");
	}
	
	
	
	IEmailSession session;
	
}
