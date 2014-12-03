/**
 *
 */

package com.robotwitter.management;


import java.util.ArrayList;

import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.database.primitives.DatabaseType;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.IEmailSender;
import com.robotwitter.miscellaneous.ReturnStatus;




/**
 * @author Itay
 * 
 *         The class handles retrieving the passwords of users via email, it is
 *         connected to the user database and the web (using the db and the
 *         mailSender).
 */
public class EmailPasswordRetriever
{
	
	/**
	 * @param systemEmail
	 *            the email of the Robotwitter system
	 * @param mailBuilder
	 *            a class that can build retrieval mails
	 * @param mailSender
	 *            a class that can send mails
	 * @param db
	 *            the user database
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
		this.userDB = db;
	}
	
	
	/**
	 * The function attempts to send the user whose mail is userEmail his
	 * password, using the mail service provided by mailSender, and building the
	 * mail using the service of mailBuilder.
	 * 
	 * @param userEmail
	 *            the email of the user whose password we want to retrieve
	 * @return the return status of the function. Success if it send the mail
	 *         correctly and fail (with a proper error message) if something
	 *         went wrong with building or sending the mail.
	 */
	public ReturnStatus retrievePasswordByMail(final String userEmail)
	{
		final ArrayList<DatabaseType> userArray = this.userDB.get(userEmail);
		if (userArray.size() == 0) { return new ReturnStatus(
			false,
			"The user doesn't exist in the system."); }
		DBUser user = (DBUser) userArray.get(0);
		final EmailMessage retrivalMail =
			this.mailBuilder.buildRetrievalEmail(
				this.systemEmail,
				user.getEMail(),
				user.getPassword());
		
		return this.mailSender.sendEmail(retrivalMail);
	}
	
	
	
	RetrievelMailBuilder mailBuilder;
	
	IEmailSender mailSender;
	
	MySqlDatabaseUser userDB;
	
	String systemEmail;
}
