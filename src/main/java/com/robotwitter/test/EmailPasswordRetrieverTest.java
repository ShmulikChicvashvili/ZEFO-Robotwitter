/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.management.EmailPasswordRetriever;
import com.robotwitter.management.EmailPasswordRetriever.ReturnStatus;
import com.robotwitter.management.RetrievalMailBuilder;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.EmailSender;
import com.robotwitter.miscellaneous.GmailSession;




/**
 * @author Itay
 *
 */
public class EmailPasswordRetrieverTest
{
	@Before
	public void before()
	{
		final EmailMessage email =
			new EmailMessage("robotwitter.app@gmail.com", //$NON-NLS-1$
				"itaykhazon@gmail.com"); //$NON-NLS-1$
		email.setSubject("Your Robotwitter account password"); //$NON-NLS-1$
		email
		.setText("Oops, forgot your password?\nDon't worry, we got you covered!\n\nYour password is: your_password123\n\nHave a pleasant day,\n\tRobotwitter."); //$NON-NLS-1$
		
		final GmailSession gSession =
			new GmailSession("robotwitter.app", "robotwitter123"); //$NON-NLS-1$ //$NON-NLS-2$
		final EmailSender sender = new EmailSender(gSession);
		
		final RetrievalMailBuilder builder =
			Mockito.mock(RetrievalMailBuilder.class);
		Mockito.when(builder.buildRetrievalEmail("robotwitter.app@gmail.com", //$NON-NLS-1$
			"itaykhazon@gmail.com", //$NON-NLS-1$
			"your_password123")).thenReturn(email); //$NON-NLS-1$
		
		final MySqlDatabaseUser db = Mockito.mock(MySqlDatabaseUser.class);
		// Mockito.when(db.isExists("itaykhazon@gmail.com")).thenReturn(true);
		final DBUser user =
			new DBUser("itaykhazon@gmail.com", "your_password123");
		Mockito.when(db.get("itaykhazon@gmail.com")).thenReturn(user);
		
		retriever = new EmailPasswordRetriever("robotwitter.app@gmail.com", //$NON-NLS-1$
			builder,
			sender,
			db);
	}
	
	
	@Test
	public void test()
	{
		
		ReturnStatus result =
			retriever.retrievePasswordByMail("itaykhazon@gmail.com");
		assertEquals(result, ReturnStatus.SUCCESS);
	}
	
	
	
	EmailPasswordRetriever retriever;
	
}
