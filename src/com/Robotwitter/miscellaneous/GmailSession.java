/**
 * 
 */

package com.Robotwitter.miscellaneous;


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
	
	
	
	/**
	 * @param username
	 * @param password
	 */
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
				this.props.getGmailProperties(),
				new GmailAuthenticator(this.username, this.password));
	}
	
	public Session getSession() {
		if (null == this.session) {
			initSession();
		}
		return this.session;
	}
}
