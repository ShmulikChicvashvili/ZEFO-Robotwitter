/**
 * 
 */

package com.robotwitter.test;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.management.EmailPasswordRetriever;
import com.robotwitter.management.EmailPasswordRetriever.ReturnStatus;
import com.robotwitter.management.EmailPasswordRetrieverModule;
import com.robotwitter.management.RetrievalMailBuilder;
import com.robotwitter.management.RetrievalMailBuilderModule;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.EmailSender;
import com.robotwitter.miscellaneous.GmailSenderModule;
import com.robotwitter.miscellaneous.GmailSession;




/**
 * @author Itay
 *
 */
public class IntegratedEmailPasswordRetrieverTest
{
	EmailPasswordRetriever pwRetriever;
	
	
	
	private class RetrievelMailBuilderMock extends RetrievalMailBuilder
	{
		
		/**
		 * @param templatePath
		 * @param reader
		 */
		public RetrievelMailBuilderMock(String templatePath)
		{
			super(templatePath);
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
		final Injector injector =
			Guice.createInjector(
				new MySQLDBUserModule(),
				new RetrievalMailBuilderModule(),
				new EmailPasswordRetrieverModule(),
				new GmailSenderModule());
		this.pwRetriever = injector.getInstance(EmailPasswordRetriever.class);
		
	}
	
	
	@Test
	public void test()
	{
		
		assertEquals(ReturnStatus.SUCCESS,this.pwRetriever.retrievePasswordByMail("itaykhazon@gmail.com"));
	}
	
}
