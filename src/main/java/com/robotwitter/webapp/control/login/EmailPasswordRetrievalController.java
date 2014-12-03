
package com.robotwitter.webapp.control.login;


import javax.mail.MessagingException;

import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.management.EmailPasswordRetriever;
import com.robotwitter.management.RetrievelMailBuilder;
import com.robotwitter.management.UserDoesntExistException;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.EmailSender;
import com.robotwitter.miscellaneous.GmailSession;




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
	public EmailPasswordRetrievalController(
		EmailPasswordRetriever emailRetriever)
	{
		this.emailRetriever = emailRetriever;
	}	
	
	@Override
	public void retrieve(final String email)
	{
		emailRetriever.retrievePasswordByMail(email);
	}
	
}
