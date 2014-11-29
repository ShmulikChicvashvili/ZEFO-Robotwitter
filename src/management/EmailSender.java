/**
 * 
 */

package management;


import javax.mail.*;
import javax.mail.internet.*;




public class EmailSender implements IEmailSender
{
	Session session;
		
	/* (non-Javadoc) @see management.IEmailSender#sendEmail(management.Email) */
	public void sendEmail(Email mail)
	{	
		try
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
			
		} catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}
		
	}
	
	
	/* (non-Javadoc) @see
	 * management.IEmailSender#startSession(management.IEmailSession) */
	public void startSession(IEmailSession session)
	{
		this.session = session.getSession();
	}
	
}
