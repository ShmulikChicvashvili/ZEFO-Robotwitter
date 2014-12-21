package com.robotwitter.webapp.control.registration;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.interfaces.returnValues.InsertError;
import com.robotwitter.database.primitives.DBUser;




/**
 * @author AmirDrutin
 */

/** Simple implementation of a login controller. */
public class RegistrationController implements IRegistrationController
{
	
	@Override
	public Status register(final String email, final String password)
	{
		final Injector injector = Guice.createInjector(new MySQLDBUserModule());
		final IDatabaseUsers db = injector.getInstance(MySqlDatabaseUser.class);
		final DBUser newUser = new DBUser(email, password);
		if (db.get(email) != null)
		{
			return Status.USER_ALREADY_EXISTS;
		}
		InsertError err = db.insert(newUser);
		if(err == InsertError.INVALID_PARAMS)
		{
			return Status.FAILURE;
		}
		if(err == InsertError.ALREADY_EXIST)
		{
			return Status.USER_ALREADY_EXISTS;
		}
		return Status.SUCCESS;
	}
}
