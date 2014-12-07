
package com.robotwitter.webapp.messages;


import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;




/**
 * Contains natural language messages loaded from a ".properties" file resource
 * bundle.
 *
 * @author Hagai Akibayov
 */
public abstract class AbstractResourceBundleMessagesContainer
implements
IMessagesContainer
{
	
	@Override
	public final String get(final String key)
	{
		try
		{
			if (resource != null) { return resource.getString(key); }
			return "!!!AbstractResourceBundleMessagesContainerUninitialised!!!"; //$NON-NLS-1$
		} catch (final MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}
	
	
	@Override
	public final void set(Locale locale)
	{
		if (resource == null || locale == null) { return; }
		resource =
			ResourceBundle.getBundle(resource.getBaseBundleName(), locale);
	}


	/**
	 * Sets the resource bundle's base and locale.
	 *
	 * @param base
	 *            the base of the resource bundle, or null for default
	 * @param locale
	 *            the desired locale of the resource bundle, or null for default
	 */
	public final void set(String base, Locale locale)
	{
		String bundleBase = base;
		Locale bundleLocale = locale;
		if (base == null)
		{
			bundleBase = getClass().getPackage().getName() + ".messages"; //$NON-NLS-1$
		}
		if (locale == null)
		{
			bundleLocale = Locale.getDefault();
		}
		resource = ResourceBundle.getBundle(bundleBase, bundleLocale);

	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The messages resource bundle properties container. */
	private ResourceBundle resource;
}
