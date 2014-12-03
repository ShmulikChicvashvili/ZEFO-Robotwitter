
package com.robotwitter.webapp.view.login;


import java.util.MissingResourceException;
import java.util.ResourceBundle;




/**
 * @author hesos_000
 *
 */
public class Messages
{
	/**
	 * @param key
	 *            The message's key
	 * @return The message mapped to the given key.
	 */
	public static String get(final String key)
	{
		try
		{
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}



	/** The messages resource bundle name. */
	private static final String BUNDLE_NAME =
		"com.robotwitter.webapp.view.login.messages"; //$NON-NLS-1$

	/** The messages resource bundle. */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
		.getBundle(BUNDLE_NAME);
}
