
package com.robotwitter.webapp.control.login;


import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;




/**
 * Simple implementation of a login controller.
 *
 * @author Amir Drutin
 */
public class LoginController implements ILoginController
{

	@Override
	public final Status authenticate(final String email, final String password)
	{
		final Injector injector = Guice.createInjector(new MySQLDBUserModule());
		final IDatabaseUsers db = injector.getInstance(MySqlDatabaseUser.class);
		final DBUser user = db.get(email);
		if (user != null)
		{
			if (user.getPassword().equals(password)) { return Status.SUCCESS; }
			return Status.AUTHENTICATION_FAILURE;
		}
		return Status.USER_DOESNT_EXIST;
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
