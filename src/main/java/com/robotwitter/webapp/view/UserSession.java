
package com.robotwitter.webapp.view;


import javax.servlet.http.Cookie;

import com.vaadin.server.VaadinSession;

import com.robotwitter.webapp.util.Cookies;




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
	 */
	public UserSession(VaadinSession session)
	{
		this.session = session;
		
		// Check if the user is remembered (using a cookie)
		Cookie cookie = Cookies.get(EMAIL_COOKIE);
		if (cookie != null)
		{
			// If the user chose to be remembered, sign the user in again.
			// this will also refresh the cookie's duration.
			sign(cookie.getValue(), true);
		}
	}


	@Override
	public final String get()
	{
		return (String) session.getAttribute(EMAIL_ATTRIBUTE);
	}


	@Override
	public final boolean isSigned()
	{
		return get() != null;
	}


	@Override
	public final void sign(String email, boolean remember)
	{
		if (isSigned()) { return; }
		
		session.setAttribute(EMAIL_ATTRIBUTE, email);
		
		// If remember is not set, the duration of the cookie will be set to -1,
		// meaning it will stored for this browsing session only
		int duration = remember ? REMEMBER_USER_DURATION : -1;
		Cookies.set(EMAIL_COOKIE, email, duration);
	}


	@Override
	public final void unsign()
	{
		session.setAttribute(EMAIL_ATTRIBUTE, null);
		Cookies.remove(EMAIL_COOKIE);
	}



	/**
	 * The signed in user email address session attribute.
	 * <p>
	 * Contains the user's email address (as {@link String}), or
	 * <code>null</code> if the user is not signed in.
	 */
	private static final String EMAIL_ATTRIBUTE = "UserSession.email"; //$NON-NLS-1$

	/** The signed in user email address cookie. */
	private static final String EMAIL_COOKIE = "UserSession.email"; //$NON-NLS-1$

	/** The amount of time to remember the user for (in seconds). */
	private static final int REMEMBER_USER_DURATION = 3600 * 24 * 30; // 30 days

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The current browsing session. */
	VaadinSession session;
}
