/**
 *
 */

package com.robotwitter.management;


import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.IEmailSender;




/**
 * @author Itay
 *
 */
public class EmailPasswordRetriever implements IEmailPasswordRetriever
{
	/** Status codes returned by this class. */
	public enum ReturnStatus
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
	@Inject
	public EmailPasswordRetriever(
		@Named("System Email") final String systemEmail,
		final IRetrievalMailBuilder mailBuilder,
		final IEmailSender mailSender,
		final IDatabaseUsers db)
	{
		this.systemEmail = systemEmail;
		this.mailBuilder = mailBuilder;
		this.mailSender = mailSender;
		userDB = db;
	}


	@Override
	public ReturnStatus retrievePasswordByMail(final String userEmail)
	{
		final DBUser user = userDB.get(userEmail);
		if(user == null) {
			return ReturnStatus.USER_DOESNT_EXIST;
		}
		final EmailMessage retrivalMail =
			mailBuilder.buildRetrievalEmail(
				systemEmail,
				user.getEMail(),
				user.getPassword());
		
		try
		{
			mailSender.sendEmail(retrivalMail);
		} catch (AddressException e)
		{
			return ReturnStatus.INVALID_EMAIL;
		} catch (MessagingException e)
		{
			return ReturnStatus.ERROR_SENDING_EMAIL;
		}
		
		return ReturnStatus.SUCCESS;
	}



	IRetrievalMailBuilder mailBuilder;
	
	IEmailSender mailSender;

	IDatabaseUsers userDB;

	String systemEmail;
}
