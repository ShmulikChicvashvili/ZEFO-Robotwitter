/**
 *
 */

package com.robotwitter.miscellaneous;


import javax.mail.Session;




/**
 * @author Itay
 *
 */
public class GmailSession implements IEmailSession
{
	/**
	 * @param username
	 * @param password
	 */
	public GmailSession(final String username, final String password)
	{
		this.username = username;
		this.password = password;
		props = new GmailProperties();
	}
	
	
	@Override
	public Session getSession()
	{
		if (null == session)
		{
			initSession();
		}
		return session;
	}
	
	
	@Override
	public void initSession()
	{
		session =
			Session.getInstance(
				props.getGmailProperties(),
				new GmailAuthenticator(username, password));
	}
	
	
	
	String username;
	
	String password;
	
	GmailProperties props;

	Session session;
}
