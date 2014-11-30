/**
 *
 */

package com.robotwitter.miscellaneous;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;




/**
 * @author Itay
 *
 */
public class GmailAuthenticator extends Authenticator
{
	public GmailAuthenticator(final String username, final String password)
	{
		this.username = username;
		this.password = password;
	}
	
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(username, password);
	}
	
	
	
	String username;

	String password;
}
