
package com.robotwitter.webapp.messages;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.robotwitter.webapp.ViewMap;




/**
 * A simple implementation of {@link IMessagesProvider} using
 * {@link java.util.HashMap}.
 *
 * @author Hagai Akibayov
 */
public class MessagesProvider implements IMessagesProvider
{

	/**
	 * Instantiates a new messages provider.
	 *
	 * @param views
	 *            a mapping of all accessible views
	 */
	public MessagesProvider(ViewMap views)
	{
		containers = new HashMap<>();
		views.keySet().forEach(
			name -> containers.put(name, new ViewMessagesContainer(name)));
	}


	@Override
	public final IMessagesContainer get(String name)
	{
		return containers.get(name);
	}


	@Override
	public final void set(Locale locale)
	{
		containers.forEach((name, container) -> container.set(locale));
		
	}
	
	
	
	/** A mapping from the container's name to its provided instance. */
	Map<String, IMessagesContainer> containers;
}
