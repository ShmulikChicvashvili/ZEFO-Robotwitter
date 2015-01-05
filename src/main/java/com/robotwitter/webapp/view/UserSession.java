
package com.robotwitter.webapp.view;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Cookie;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import com.robotwitter.webapp.control.account.IAccountController;
import com.robotwitter.webapp.control.account.IAccountController.Status;
import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.util.Cookies;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;
import com.robotwitter.webapp.view.login.LoginView;




/**
 * An implementation of {@link IUserSession} using browsing session and cookies.
 *
 * @author Hagai Akibayov
 *
 */
public class UserSession implements IUserSession
{

	/**
	 * Instantiates a new user session.
	 *
	 * @param session
	 *            the current browsing session
	 * @param accountController
	 *            the user's account controller
	 */
	public UserSession(
		VaadinSession session,
		IAccountController accountController)
	{
		this.session = session;
		this.accountController = accountController;
		isSigned = false;
		observingTwitterAccountChange = new LinkedList<>();
		
		// Check if the user is remembered (using a cookie)
		Cookie cookie = Cookies.get(EMAIL_COOKIE);
		if (cookie != null && !"".equals(cookie.getValue()))
		{
			// If the user chose to be remembered, sign the user in again.
			// this will also refresh the cookie's duration.
			sign(cookie.getValue(), true);
		}
	}


	@Override
	public final void activateTwitterAccount(long id)
	{
		// Attempt to activate
		Status status = accountController.activateTwitterAccount(id);
		if (status != Status.SUCCESS)
		{
			// If the given Twitter account doesn't exist, activate a random one
			activateRandomTwitterAccount();
			return;
		}
		
		// Remember the active account with a cookie
		rememberActiveTwitterAccount();

		// Notify observers
		for (RobotwitterCustomComponent c : observingTwitterAccountChange)
		{
			c.activateTwitterAccount(id);
		}
	}


	@Override
	public final void clearActiveTwitterAccountObservers()
	{
		observingTwitterAccountChange.clear();
	}


	@Override
	public final IAccountController getAccountController()
	{
		return accountController;
	}
	
	
	@Override
	public final boolean isSigned()
	{
		return isSigned;
	}
	
	
	@Override
	public final void observeActiveTwitterAccount(
		RobotwitterCustomComponent component)
	{
		observingTwitterAccountChange.add(component);
	}
	
	
	@Override
	public final void sign(String email, boolean remember)
	{
		if (isSigned) { return; }

		if (accountController.connect(email) != Status.SUCCESS)
		{
			unsign();
			return;
		}
		
		session.setAttribute(EMAIL_ATTRIBUTE, email);
		
		// If remember is not set, the duration of the cookie will be set to -1,
		// meaning it will stored for this browsing session only
		int duration = remember ? REMEMBER_USER_DURATION : -1;
		Cookies.set(EMAIL_COOKIE, email, duration);
		
		// Check if the active Twitter account is remembered (using a cookie)
		Cookie cookie = Cookies.get(ACTIVE_TWITTER_COOKIE);
		if (cookie != null)
		{
			long id = 0;
			try
			{
				id = Long.parseLong(cookie.getValue());
			} catch (NumberFormatException e)
			{
				activateRandomTwitterAccount();
			}
			activateTwitterAccount(id);
		} else
		{
			activateRandomTwitterAccount();
		}

		isSigned = true;
	}
	
	
	@Override
	public final void unsign()
	{
		session.setAttribute(EMAIL_ATTRIBUTE, null);
		Cookies.remove(EMAIL_COOKIE);
		accountController.disconnect();
		isSigned = false;
		
		// Navigate back to login page
		UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
	}


	/** Activates a random connected Twitter account. */
	private void activateRandomTwitterAccount()
	{
		Collection<ITwitterAccountController> twitterAccounts =
			accountController.getTwitterAccounts();
		if (twitterAccounts.size() > 0)
		{
			accountController.activateTwitterAccount(accountController
				.getTwitterAccounts()
				.iterator()
				.next()
				.getID());

			// Remember the active account with a cookie
			rememberActiveTwitterAccount();
		}
	}


	/** Remembers the active twitter account using a cookie. */
	private void rememberActiveTwitterAccount()
	{
		ITwitterAccountController account =
			accountController.getActiveTwitterAccount();
		if (account != null)
		{
			Cookies.set(
				ACTIVE_TWITTER_COOKIE,
				String.valueOf(account.getID()),
				REMEMBER_ACTIVE_TWITTER_DURATION);
		}
	}
	
	
	
	/**
	 * The signed in user email address session attribute.
	 * <p>
	 * Contains the user's email address (as {@link String}), or
	 * <code>null</code> if the user is not signed in.
	 */
	private static final String EMAIL_ATTRIBUTE = "UserSession.email";

	/** The signed in user email address cookie. */
	private static final String EMAIL_COOKIE = "UserSession.email";

	/** The active Twitter account cookie. */
	private static final String ACTIVE_TWITTER_COOKIE =
		"UserSession.active-twitter-account";

	/** The default maximum cookie age (time till expiration). */
	private static final int COOKIE_MAX_AGE = 3600 * 24 * 30; // 30 days
	
	/** The amount of time to remember the user for (in seconds). */
	private static final int REMEMBER_USER_DURATION = COOKIE_MAX_AGE;

	/** The amount of time to remember the user for (in seconds). */
	private static final int REMEMBER_ACTIVE_TWITTER_DURATION =
		COOKIE_MAX_AGE * 1000; // Remember forever
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The current browsing session. */
	private VaadinSession session;
	
	/** The current user's account controller. */
	private IAccountController accountController;
	
	/** The list of observing component for an active Twitter Account change. */
	private List<RobotwitterCustomComponent> observingTwitterAccountChange;
	
	/** <code>true</code> if the user is currently signed in. */
	private boolean isSigned;
}
