
package com.robotwitter.webapp.control.login;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;




/**
 * @author AmirDrutin 
 * 
 * Simple implementation of a login controller.
 */
public class LoginControllerImpl implements LoginController
{
	IDatabaseUsers db;
	
	/**
	 * @param userDB
	 */
	@Inject
	public LoginControllerImpl(IDatabaseUsers db)
	{
		this.db = db;
	}

	@Override
	public boolean authenticate(final String email, final String password)
	{
		DBUser user = this.db.get(email);
		if (user == null)
		{
			//FIXME: dont need that crap here
			final Notification notification =
				new Notification("User doesn't exist!"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			notification.setStyleName(ValoTheme.NOTIFICATION_ERROR);
			notification.setDelayMsec(-1);
			notification.show(Page.getCurrent());
			
			return false;
			
		} else if(user.getPassword().equals(password))
		{
			//FIXME: dont need that crap here
			final Notification notification =
				new Notification(
					"Login SUCCESSFUL", "With email \"" + email + "\" and password \"" + password + "\"."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
			notification.setDelayMsec(-1);
			notification.show(Page.getCurrent());
			
			return true;
		}
		
		//FIXME: dont need that crap here
		final Notification notification =
			new Notification("User password is incorrect!"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		notification.setStyleName(ValoTheme.NOTIFICATION_ERROR);
		notification.setDelayMsec(-1);
		notification.show(Page.getCurrent());
		return false;
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
