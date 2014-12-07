
package com.robotwitter.webapp.messages;


import java.util.Locale;




/**
 * A provider of {@link IMessagesContainer} instances. This interface all allows
 * controlling the provided messages container's locale.
 *
 * @author Hagai Akibayov
 */
public interface IMessagesProvider
{
	
	/**
	 * Gets a messages container given its ID.
	 *
	 * @param id
	 *            the ID of the desired messages container
	 *
	 * @return the messages container mapped to the given ID
	 */
	IMessagesContainer get(MessagesContainerID id);
	
	
	/**
	 * Sets the locale of all messages containers.
	 *
	 * @param locale
	 *            the desired locale
	 */
	void set(Locale locale);
}
