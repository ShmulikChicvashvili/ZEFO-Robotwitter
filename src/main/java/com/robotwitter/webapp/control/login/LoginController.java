/*
 *
 */

package com.robotwitter.webapp.control.login;


import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * Simple implementation of a login controller.
 *
 * @author Amir Drutin
 */
public class LoginController implements ILoginController
{

	/**
	 * Instantiates a new login controller.
	 *
	 * @param dbUsers
	 *            the users database
	 */
	@Inject
	public LoginController(IDatabaseUsers dbUsers)
	{
		this.dbUsers = dbUsers;
	}
	
	
	@Override
	public final Status authenticate(final String email, final String password)
	{
		final DBUser user = dbUsers.get(email);
		if (user != null)
		{
			if (user.getPassword().equals(password)) { return Status.SUCCESS; }
			return Status.AUTHENTICATION_FAILURE;
		}
		return Status.USER_DOESNT_EXIST;
	}
	
	
	
	/** The users database. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	IDatabaseUsers dbUsers;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
