
package com.robotwitter.webapp.control.login;


import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.IDatabase;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.primitives.DBUser;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme; 

/**
 * @author AmirDrutin
 */

/** Simple implementation of a login controller. */
public class LoginAuthenticator implements ILoginAuthenticator
{

	@Override
	public boolean authenticate(final String email, final String password)
	{
		final Injector injector = Guice.createInjector(new MySQLDBUserModule());
		final IDatabase db = injector.getInstance(MySqlDatabaseUser.class);
		if ((DBUser)db.get(email).get(0)!= null)
		{
			final Notification notification =
					new Notification(
						"Login SUCCESSFUL", "With email \"" + email + "\" and password \"" + password + "\"."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
				notification.setDelayMsec(-1);
				notification.show(Page.getCurrent());
				return true;
		}
		else
		{
			final Notification notification =
					new Notification(
						"User does not exist!"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
				notification.setDelayMsec(-1);
				notification.show(Page.getCurrent());
				return false;
		}
	}
	
	 /**final Notification notification =
			new Notification(
				"Login Attempt", "With email \"" + email + "\" and password \"" + password + "\"."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
		notification.setDelayMsec(-1);
		notification.show(Page.getCurrent());
		return true;*/
}
