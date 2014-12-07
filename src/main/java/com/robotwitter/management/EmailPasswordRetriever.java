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
		ERROR_SENDING_EMAIL,
		
		/** Could not build the retrieval mail, something is wrong with the system */
		ERROR_BUILDING_EMAIL
	}
	
	
	
	/**
	 * @param systemEmail 
	 * @param mailBuilder 
	 * @param mailSender 
	 * @param db 
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
		this.userDB = db;
	}


	@Override
	public ReturnStatus retrievePasswordByMail(final String userEmail)
	{
		final DBUser user = this.userDB.get(userEmail);
		if(user == null) {
			return ReturnStatus.USER_DOESNT_EXIST;
		}
		final EmailMessage retrivalMail =
			this.mailBuilder.buildRetrievalEmail(
				this.systemEmail,
				user.getEMail(),
				user.getPassword());
		
		if(retrivalMail == null) {
			return ReturnStatus.ERROR_BUILDING_EMAIL;
		}
		
		try
		{
			this.mailSender.sendEmail(retrivalMail);
		} catch (AddressException e)
		{
			return ReturnStatus.INVALID_EMAIL;
		} catch (MessagingException e)
		{
			return ReturnStatus.ERROR_SENDING_EMAIL;
		}
		
		return ReturnStatus.SUCCESS;
	}



	private IRetrievalMailBuilder mailBuilder;
	
	private IEmailSender mailSender;

	private IDatabaseUsers userDB;

	private String systemEmail;
}
