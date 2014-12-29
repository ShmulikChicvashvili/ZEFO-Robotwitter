
package com.robotwitter.webapp.control.login;


import com.google.inject.Inject;

import com.robotwitter.management.EmailPasswordRetriever;
import com.robotwitter.management.IEmailPasswordRetriever;




/**
 * Simple implementation of a password retrieval controller.
 *
 * @author Amir Drutin
 */
public class EmailPasswordRetrievalController
	implements
		IPasswordRetrievalController
{
	
	/**
	 * Instantiates a new email password retrieval controller.
	 *
	 * @param emailRetriever
	 *            the email password retriever
	 */
	@Inject
	public EmailPasswordRetrievalController(
		IEmailPasswordRetriever emailRetriever)
	{
		this.emailRetriever = emailRetriever;
	}


	@Override
	public final Status retrieve(final String email)
	{
		final EmailPasswordRetriever.ReturnStatus result =
			emailRetriever.retrievePasswordByMail(email);
		switch (result)
		{
			case SUCCESS:
				return Status.SUCCESS;
			case USER_DOESNT_EXIST:
				return Status.USER_DOESNT_EXIST;
			default:
				return Status.FAILURE;
		}
	}



	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The email password retriever. */
	private final IEmailPasswordRetriever emailRetriever;
}
