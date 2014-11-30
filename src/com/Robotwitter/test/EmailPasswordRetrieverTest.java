/**
 * 
 */

package com.Robotwitter.test;


import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.Robotwitter.Database.ConnectionEstablisher;
import com.Robotwitter.Database.MySQLConEstablisher;
import com.Robotwitter.Database.MySqlDatabaseUser;
import com.Robotwitter.DatabasePrimitives.DBUser;
import com.Robotwitter.management.EmailPasswordRetriever;
import com.Robotwitter.management.RetrievelMailBuilder;
import com.Robotwitter.management.UserDoesntExistException;
import com.Robotwitter.miscellaneous.EmailMessage;
import com.Robotwitter.miscellaneous.EmailSender;
import com.Robotwitter.miscellaneous.GmailSession;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;




/**
 * @author Itay
 *
 */
public class EmailPasswordRetrieverTest
{
	EmailPasswordRetriever retriever;
	
	
	
	@Before
	public void before()
	{
		EmailMessage email = new EmailMessage("robotwitter.app@gmail.com", //$NON-NLS-1$
			"itaykhazon@gmail.com"); //$NON-NLS-1$
		email.setSubject("Your Robotwitter account password"); //$NON-NLS-1$
		email
			.setText("Oops, forgot your password?\nDon't worry, we got you covered!\n\nYour password is: your_password123\n\nHave a pleasant day,\n\tRobotwitter."); //$NON-NLS-1$
		
		GmailSession gSession =
			new GmailSession("robotwitter.app", "robotwitter123"); //$NON-NLS-1$ //$NON-NLS-2$
		EmailSender sender = new EmailSender(gSession);
		
		RetrievelMailBuilder builder = Mockito.mock(RetrievelMailBuilder.class);
		Mockito.when(builder.buildRetrievalEmail("robotwitter.app@gmail.com", //$NON-NLS-1$
			"itaykhazon@gmail.com", //$NON-NLS-1$
			"your_password123")).thenReturn(email); //$NON-NLS-1$
		
		MySqlDatabaseUser db = Mockito.mock(MySqlDatabaseUser.class);
		Mockito.when(db.isExists("itaykhazon@gmail.com")).thenReturn(true);
		Mockito.when(db.get("itaykhazon@gmail.com")).thenReturn(
			new DBUser("itaykhazon@gmail.com", "your_password123"));
		
		this.retriever =
			new EmailPasswordRetriever("robotwitter.app@gmail.com", //$NON-NLS-1$
				builder,
				sender,
				db);
	}
	
	
	@Test
	public void test()
	{
		try
		{
			this.retriever.retrievePasswordByMail("itaykhazon@gmail.com");
		} catch (UserDoesntExistException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
