/**
 * 
 */

package com.robotwitter.test;


import static org.junit.Assert.*;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.management.EmailPasswordRetriever;
import com.robotwitter.management.RetrievelMailBuilder;
import com.robotwitter.management.UserDoesntExistException;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.EmailSender;
import com.robotwitter.miscellaneous.GmailSession;
import com.robotwitter.miscellaneous.TemplateMail;
import com.robotwitter.miscellaneous.TemplateMailReader;




/**
 * @author Itay
 *
 */
public class IntegratedEmailPasswordRetrieverTest
{
	EmailPasswordRetriever pwRetriever;
	
	
	
	private class RetrievelMailBuilderMock extends RetrievelMailBuilder
	{
		
		/**
		 * @param templatePath
		 * @param reader
		 */
		public RetrievelMailBuilderMock(
			String templatePath,
			TemplateMailReader reader)
		{
			super(templatePath, reader);
			// TODO Auto-generated constructor stub
		}
		
		
		@Override
		public EmailMessage buildRetrievalEmail(
			final String systemEmail,
			final String userEmail,
			final String password)
		{
			EmailMessage $ = new EmailMessage(systemEmail, userEmail);
			$.setSubject("Your Robotwitter account password");
			$
				.setText("Oops, forgot your password?\nDon't worry, we got you covered!\n\nYour password is: "
					+ password
					+ "\n\nHave a pleasant day,\n\tRobotwitter.");
			return $;
		}
		
	}
	
	
	
	@Before
	public void before()
	{
		final Injector injector = Guice.createInjector(new MySQLDBUserModule());
		final IDatabaseUsers db = injector.getInstance(MySqlDatabaseUser.class);
		final DBUser shmulikTheMan = new DBUser("shmulikjkech@gmail.com", "sh");
		if (!db.isExists("shmulikjkech@gmail.com"))
		{
			db.insert(shmulikTheMan);
		}
		
		final GmailSession gSession =
			new GmailSession("robotwitter.app", "robotwitter123"); //$NON-NLS-1$ //$NON-NLS-2$
		final EmailSender sender = new EmailSender(gSession);
		
		RetrievelMailBuilder builder = new RetrievelMailBuilderMock(null, null);
		
		this.pwRetriever =
			new EmailPasswordRetriever(
				"robotwitter.app@gmail.com",
				builder,
				sender,
				(MySqlDatabaseUser) db);
		
	}
	
	
	@Test
	public void test()
	{
		
		this.pwRetriever.retrievePasswordByMail("shmulikjkech@gmail.com");
	}
	
}
