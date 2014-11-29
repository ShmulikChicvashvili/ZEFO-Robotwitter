/**
 * 
 */
package management;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Itay
 *
 */
public class GmailAuthenticator extends Authenticator
{	
	String username;
	String password;
	
	public GmailAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(this.username, this.password);
	}
}
