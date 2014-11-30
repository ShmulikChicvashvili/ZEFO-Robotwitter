
package com.robotwitter.webapp.control.login;


import javax.mail.MessagingException;

import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.IDatabase;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.primitives.DBUser;
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
	@Override
	public boolean authenticate(final String email)
	{
		final Injector injector = Guice.createInjector(new MySQLDBUserModule());
		final IDatabase db = injector.getInstance(MySqlDatabaseUser.class);
		
		return db.isExists(new DBUser(email, "dummy"));
	}
	
	
	@Override
	public void retrieve(final String email)
	{
		final Injector injector = Guice.createInjector(new MySQLDBUserModule());
		final IDatabase db = injector.getInstance(MySqlDatabaseUser.class);

		final EmailMessage mailToSend =
			new EmailMessage("robotwitter.app@gmail.com", //$NON-NLS-1$
				email);
		mailToSend.setSubject("Your Robotwitter account password"); //$NON-NLS-1$
		mailToSend
			.setText("Oops, forgot your password?\nDon't worry, we got you covered!\n\nYour password is: your_password123\n\nHave a pleasant day,\n\tRobotwitter."); //$NON-NLS-1$
		
		final GmailSession gSession =
			new GmailSession("robotwitter.app", "robotwitter123"); //$NON-NLS-1$ //$NON-NLS-2$
		final EmailSender sender = new EmailSender(gSession);
		
		final RetrievelMailBuilder builder =
			Mockito.mock(RetrievelMailBuilder.class);
		Mockito.when(builder.buildRetrievalEmail("robotwitter.app@gmail.com", //$NON-NLS-1$
			"itaykhazon@gmail.com", //$NON-NLS-1$
			"your_password123")).thenReturn(mailToSend); //$NON-NLS-1$
		
		final EmailPasswordRetriever retriever =
			new EmailPasswordRetriever("robotwitter.app@gmail.com", //$NON-NLS-1$
				builder,
				sender,
				(MySqlDatabaseUser) db);
		
		try
		{
			retriever.retrievePasswordByMail(email);
		} catch (final UserDoesntExistException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
