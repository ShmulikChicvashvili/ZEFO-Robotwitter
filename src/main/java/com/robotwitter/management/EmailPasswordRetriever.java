/**
 *
 */

package com.robotwitter.management;


import javax.mail.MessagingException;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.IEmailSender;




/**
 * @author Itay
 *
 */
public class EmailPasswordRetriever
{
	/**
	 *
	 */
	@Inject	
	public EmailPasswordRetriever(
		@Assisted final String systemEmail,
		final RetrievelMailBuilder mailBuilder,
		final IEmailSender mailSender,
		final IDatabaseUsers db)
	{
		this.systemEmail = systemEmail;
		this.mailBuilder = mailBuilder;
		this.mailSender = mailSender;
		this.userDB = db;
	}
	
	
	public void retrievePasswordByMail(final String userEmail)
		throws UserDoesntExistException,
		MessagingException
	{
		// if (!this.userDB.isExists(userEmail)) { throw new
		// UserDoesntExistException(
		// "The user doesnt exist in the database!"); }

		final DBUser user = this.userDB.get(userEmail); 
		// change
		// MySqlDatabaseUser to
		// return DBUser
		final EmailMessage retrivalMail =
			this.mailBuilder.buildRetrievalEmail(
				this.systemEmail,
				user.getEMail(),
				user.getPassword());

		this.mailSender.sendEmail(retrivalMail);
	}
	
	
	
	RetrievelMailBuilder mailBuilder;

	IEmailSender mailSender;
	
	IDatabaseUsers userDB;
	
	String systemEmail;
}
