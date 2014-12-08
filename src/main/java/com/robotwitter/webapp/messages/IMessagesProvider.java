
package com.robotwitter.webapp.messages;


import java.util.Locale;




/**
 * A provider of {@link IMessagesContainer} instances.
 * <p>
 * This interface all allows controlling the provided messages container's
 * locale using {@link #set}.
 *
 * @author Hagai Akibayov
 */
public interface IMessagesProvider
{

	/**
	 * Gets a messages container given its name.
	 *
	 * @param name
	 *            the name of the desired messages container
	 *
	 * @return the messages container mapped to the given ID
	 */
	IMessagesContainer get(String name);


	/**
	 * Sets the locale of all messages containers.
	 *
	 * @param locale
	 *            the desired locale
	 */
	void set(Locale locale);
}
