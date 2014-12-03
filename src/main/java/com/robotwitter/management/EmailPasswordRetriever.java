/**
 *
 */

package com.robotwitter.management;


import javax.mail.MessagingException;

import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.IEmailSender;




/**
 * @author Itay
 *
 */
public class EmailPasswordRetriever
{
	/** Status codes returned by this class. */
	enum ReturnStatus
	{
		/** Operation succeeded. */
		SUCCESS,

		/** Received an email address of invalid form. */
		INVALID_EMAIL,

		/** The received email address is not attached to any existing user. */
		USER_DOESNT_EXIST,
		
		/** A communication error has occurred while trying to send the email. */
		ERROR_SENDING_EMAIL
	}
	
	
	
	/**
	 *
	 */
	public EmailPasswordRetriever(
		final String systemEmail,
		final RetrievelMailBuilder mailBuilder,
		final IEmailSender mailSender,
		final MySqlDatabaseUser db)
	{
		this.systemEmail = systemEmail;
		this.mailBuilder = mailBuilder;
		this.mailSender = mailSender;
		userDB = db;
	}


	public void retrievePasswordByMail(final String userEmail)
		throws UserDoesntExistException,
		MessagingException
	{
		// if (!this.userDB.isExists(userEmail)) { throw new
		// UserDoesntExistException(
		// "The user doesnt exist in the database!"); }
		
		final DBUser user = (DBUser) userDB.get(userEmail).get(0); // FIXME:
		// change
		// MySqlDatabaseUser to
		// return DBUser
		final EmailMessage retrivalMail =
			mailBuilder.buildRetrievalEmail(
				systemEmail,
				user.getEMail(),
				user.getPassword());
		
		mailSender.sendEmail(retrivalMail);
	}



	RetrievelMailBuilder mailBuilder;
	
	IEmailSender mailSender;

	MySqlDatabaseUser userDB;

	String systemEmail;
}
