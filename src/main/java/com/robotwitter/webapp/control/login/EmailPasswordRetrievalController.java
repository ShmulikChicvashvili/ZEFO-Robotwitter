
package com.robotwitter.webapp.control.login;


import javax.mail.MessagingException;

import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.robotwitter.management.EmailPasswordRetriever;




/** Simple implementation of a password retrieval controller. */
public class EmailPasswordRetrievalController
	implements
		PasswordRetrievalController
{
	
	private EmailPasswordRetriever emailRetriever;
	
	
	
	/**
	 * @param emailRetriever
	 *            The EmailRetriever class which handles the password retrieval
	 *            service
	 */
	@Inject
	public EmailPasswordRetrievalController(
		EmailPasswordRetriever emailRetriever)
	{
		this.emailRetriever = emailRetriever;
	}
	
	
	@Override
	public ReturnStatus retrieve(final String email)
	{
		try
		{
			this.emailRetriever.retrievePasswordByMail(email);
		} catch (Exception e)
		{	
			// Empty block
		}
	}
	
}
