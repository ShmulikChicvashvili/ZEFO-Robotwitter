
package com.robotwitter.webapp.control.login;


import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;




/**
 * @author AmirDrutin
 */

/** Simple implementation of a login controller. */
public class LoginController implements ILoginController
{
	
	@Override
	public Status authenticate(final String email, final String password)
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
	
	/**
	 * final Notification notification = new Notification( "Login Attempt",
	 * "With email \"" + email + "\" and password \"" + password + "\".");
	 * //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 * notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
	 * notification.setDelayMsec(-1); notification.show(Page.getCurrent());
	 * return true;
	 */
}
