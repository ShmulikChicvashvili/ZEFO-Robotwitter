
package com.robotwitter.webapp.control.registration;


import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBUser;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 *
 * @author Amir Drutin
 */
public class RegistrationController implements IRegistrationController
{
	
	/**
	 * Instantiates a new registration controller stub.
	 *
	 * @param dbUsers
	 *            the users database
	 */
	@Inject
	public RegistrationController(IDatabaseUsers dbUsers)
	{
		this.dbUsers = dbUsers;
	}
	
	
	@Override
	public final Status register(String email, String password)
	{
		final DBUser user = new DBUser(email, password);
		final SqlError err = dbUsers.insert(user);

		switch (err)
		{
			case SUCCESS:
				return Status.SUCCESS;
			case ALREADY_EXIST:
				return Status.USER_ALREADY_EXISTS;
			case INVALID_PARAMS:
				return Status.FAILURE;
			default:
				assert false;
				return null;
		}
	}
	
	
	
	/** The users database. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	IDatabaseUsers dbUsers;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
