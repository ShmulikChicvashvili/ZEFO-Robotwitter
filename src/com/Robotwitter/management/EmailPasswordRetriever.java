/**
 * 
 */

package com.Robotwitter.management;


import javax.mail.MessagingException;

import com.Robotwitter.Database.MySqlDatabaseUser;
import com.Robotwitter.DatabasePrimitives.DBUser;
import com.Robotwitter.miscellaneous.EmailMessage;
import com.Robotwitter.miscellaneous.IEmailSender;




/**
 * @author Itay
 *
 */
public class EmailPasswordRetriever
{
	RetrievelMailBuilder mailBuilder;
	
	IEmailSender mailSender;
	
	MySqlDatabaseUser userDB;
	
	String systemEmail;
	
	
	
	/**
	 * 
	 */
	public EmailPasswordRetriever(
		String systemEmail,
		RetrievelMailBuilder mailBuilder,
		IEmailSender mailSender,
		MySqlDatabaseUser db)
	{
		this.systemEmail = systemEmail;
		this.mailBuilder = mailBuilder;
		this.mailSender = mailSender;
		this.userDB = db;
	}
	
	
	public void retrievePasswordByMail(String userEmail)
		throws UserDoesntExistException, MessagingException
	{
		if (!this.userDB.isExists(userEmail)) { throw new UserDoesntExistException(
			"The user doesnt exist in the database!"); }
		
		DBUser user = (DBUser) this.userDB.get(userEmail); // FIXME: change
														// MySqlDatabaseUser to
														// return DBUser
		EmailMessage retrivalMail =
			this.mailBuilder.buildRetrievalEmail(
				this.systemEmail,
				user.getEMail(),
				user.getPassword());
		
		this.mailSender.sendEmail(retrivalMail);
	}
}
