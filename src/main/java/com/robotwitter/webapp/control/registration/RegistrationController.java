package com.robotwitter.webapp.control.registration;


import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBUser;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * @author AmirDrutin
 */

/** Simple implementation of a login controller. */
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
	public Status register(final String email, final String password)
	{
		final DBUser newUser = new DBUser(email, password);
		if(!email.matches(".*@.*[.].*$")){
			return Status.BAD_EMAIL;
		}
		if(!(password.length()>=8)){
			return Status.SHORT_PASSWORD;
		}
		if(password.equals(password.toLowerCase())){
			return Status.UPPER_CASE_PASSWORD;
		}
		if(password.equals(password.toUpperCase())){
			return Status.LOWER_CASE_PASSWORD;
		}
		if (dbUsers.get(email) != null)
		{
			return Status.USER_ALREADY_EXISTS;
		}
		SqlError err = dbUsers.insert(newUser);
		switch (err){
		case ALREADY_EXIST: return Status.USER_ALREADY_EXISTS;
		case INVALID_PARAMS: return Status.FAILURE;	
		default: return Status.SUCCESS;
		}
	}
	
	/** The users database. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	IDatabaseUsers dbUsers;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}