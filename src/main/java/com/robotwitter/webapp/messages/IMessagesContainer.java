
package com.robotwitter.webapp.messages;


import java.io.Serializable;
import java.util.Locale;




/**
 * Represents a container of natural language messages.
 *
 * @author Hagai Akibayov
 */
public interface IMessagesContainer extends Serializable
{
	/**
	 * Gets a message given its key.
	 *
	 * @param key
	 *            the key for the desired message
	 *
	 * @return The message mapped to the given key.
	 */
	String get(String key);
	
	
	/**
	 * Sets the messages' locale.
	 *
	 * @param locale
	 *            the desired locale of messages
	 */
	void set(Locale locale);
}
