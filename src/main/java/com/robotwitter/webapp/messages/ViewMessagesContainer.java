
package com.robotwitter.webapp.messages;


import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * Contains natural language messages loaded from a ".properties" file resource
 * bundle for a single view based on the view's name.
 *
 * @author Hagai Akibayov
 */
public class ViewMessagesContainer implements IMessagesContainer
{

	/**
	 * Instantiates the views messages container.
	 *
	 * @param name
	 *            the view's name
	 */
	public ViewMessagesContainer(String name)
	{
		String base = getClass().getPackage().getName() + '.' + name;
		resource = ResourceBundle.getBundle(base, Locale.getDefault());
	}


	@Override
	public final String get(final String key)
	{
		try
		{
			return resource.getString(key);
		} catch (final MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}
	
	
	@Override
	public final void set(Locale locale)
	{
		resource =
			ResourceBundle.getBundle(resource.getBaseBundleName(), locale);
	}



	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The messages resource bundle properties container. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	private ResourceBundle resource;
}
