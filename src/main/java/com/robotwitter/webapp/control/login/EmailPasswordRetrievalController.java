
package com.robotwitter.webapp.control.login;


import com.google.inject.Inject;

import com.robotwitter.management.IEmailPasswordRetriever;




/** Simple implementation of a password retrieval controller. */
public class EmailPasswordRetrievalController
	implements
		IPasswordRetrievalController
{
	
	/**
	 * @param emailRetriever
	 *            The EmailRetriever class which handles the password retrieval
	 *            service
	 */
	@Inject
	public EmailPasswordRetrievalController(
		IEmailPasswordRetriever emailRetriever)
	{
		this.emailRetriever = emailRetriever;
	}


	@Override
	public Status retrieve(final String email)
	{
		return Status.SUCCESS;
		// final EmailPasswordRetriever.ReturnStatus result =
		// emailRetriever.retrievePasswordByMail(email);
		// switch (result)
		// {
		// case SUCCESS:
		// return Status.SUCCESS;
		// case USER_DOESNT_EXIST:
		// return Status.USER_DOESNT_EXIST;
		// default:
		// return Status.FAILURE;
		// }
	}



	private final IEmailPasswordRetriever emailRetriever;
}
