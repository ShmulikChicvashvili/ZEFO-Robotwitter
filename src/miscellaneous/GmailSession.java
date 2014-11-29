/**
 * 
 */

package miscellaneous;


import javax.mail.PasswordAuthentication;
import javax.mail.Session;




/**
 * @author Itay
 *
 */
public class GmailSession implements IEmailSession
{
	String username;
	
	String password;
	
	GmailProperties props;
	
	Session session;
	
	
	
	public GmailSession(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.props = new GmailProperties();
	}
	
	
	public void initSession()
	{
		this.session =
			Session.getInstance(
				props.getGmailProperties(),
				new GmailAuthenticator(this.username, this.password));
	}
	
	public Session getSession() {
		if (null == session) {
			initSession();
		}
		return session;
	}
}
