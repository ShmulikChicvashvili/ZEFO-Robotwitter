
package com.robotwitter.webapp.control.login;


/** Simple implementation of a password retrieval controller. */
public class EmailPasswordRetrievalController
implements
IPasswordRetrievalController
{
	@Override
	public final Status retrieve(final String email)
	{
		return Status.USER_DOESNT_EXIST;
	}
}
