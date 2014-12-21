
package com.robotwitter.webapp.util;


import javax.servlet.http.Cookie;

import com.vaadin.server.VaadinService;




/**
 * A wrapper for handling browser cookies.
 *
 * @author Hagai Akibayov
 *
 */
public final class Cookies
{
	/**
	 * Retrieves a cookie.
	 *
	 * @param name
	 *            the cookie's name
	 *
	 * @return the retrieved cookie, or <code>null</code> if no cookie by the
	 *         given <code>name</code> exists.
	 */
	public static Cookie get(String name)
	{
		// Retrieve all cookies from the request
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

		// Iterate to find cookie by its name
		for (Cookie cookie : cookies)
		{
			if (name.equals(cookie.getName())) { return cookie; }
		}

		// Return null if no cookie by the given name was found
		return null;
	}


	/**
	 * Removes a cookie.
	 *
	 * @param name
	 *            the cookie's name
	 */
	public static void remove(String name)
	{
		Cookie cookie = get(name);
		if (cookie == null) { return; }
		cookie.setValue("");  //$NON-NLS-1$
		cookie.setMaxAge(0);
		cookie.setPath("/"); //$NON-NLS-1$
		VaadinService.getCurrentResponse().addCookie(cookie);
	}


	/**
	 * Sets a cookie.
	 *
	 * @param name
	 *            the cookie's name
	 * @param value
	 *            the cookie's value
	 * @param duration
	 *            the duration in seconds the cookie shall persist (i.e., the
	 *            cookie's max age)
	 */
	public static void set(String name, String value, int duration)
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(duration);
		cookie.setPath("/"); //$NON-NLS-1$
		VaadinService.getCurrentResponse().addCookie(cookie);
	}


	/**
	 * Initialisation is prohibited.
	 *
	 * @throws Exception
	 *             always thrown
	 */
	private Cookies() throws Exception
	{
		throw new Exception("Initialisation of class " //$NON-NLS-1$
			+ this.getClass().getName()
			+ " is prohibited"); //$NON-NLS-1$
	}
}
