/**
 * 
 */
package management;

import javax.mail.MessagingException;

/**
 * @author Itay
 *
 */
public interface IEmailSender
{	
	public void startSession(IEmailSession session);
	
	public void sendEmail(Email mail) throws MessagingException;
}
