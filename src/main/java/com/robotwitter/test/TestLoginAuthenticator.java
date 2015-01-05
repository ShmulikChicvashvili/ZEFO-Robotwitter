
package com.robotwitter.test;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.webapp.control.login.ILoginController;
import com.robotwitter.webapp.control.login.ILoginController.Status;
import com.robotwitter.webapp.control.login.LoginController;




/**
 * @author AmirDrutin
 *
 */

public class TestLoginAuthenticator
{
	@SuppressWarnings("nls")
	@Test
	public void test()
	{
		final Injector injector =
			Guice.createInjector(new DatabaseTestModule());
		final IDatabaseUsers db = injector.getInstance(MySqlDatabaseUser.class);
		login = new LoginController(db);
		final DBUser user = new DBUser("amir.drutin@gmail.com", "amir1");
		if (!db.isExists("amir.drutin@gmail.com"))
		{
			db.insert(user);
		}
		ILoginController.Status result =
			login.authenticate("amir.drutin@gmail.com", "amir");
		assertEquals(Status.SUCCESS, result);
		
		result = login.authenticate("amir.drutin@gmail.com", "wrong");
		assertEquals(Status.AUTHENTICATION_FAILURE, result);
		
		result = login.authenticate("yossi@gmail.com", "amir");
		assertEquals(Status.USER_DOESNT_EXIST, result);
		
		result = login.authenticate("yossi@gmail.com", "wrong");
		assertEquals(Status.USER_DOESNT_EXIST, result);
	}



	/**
	 * Testing login authentication
	 */
	LoginController login;
}
