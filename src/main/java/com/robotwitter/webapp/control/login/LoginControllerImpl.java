
package com.robotwitter.webapp.control.login;


import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;




/** Simple implementation of a login controller. */
public class LoginControllerImpl implements ILoginController
{

	@Override
	public final Status authenticate(final String email, final String password)
	{
		final Notification notification =
			new Notification(
				"Login Attempt", "With email \"" + email + "\" and password \"" + password + "\"."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
		notification.setDelayMsec(-1);
		notification.show(Page.getCurrent());
		return Status.AUTHENTICATION_FAILURE;
	}

}
