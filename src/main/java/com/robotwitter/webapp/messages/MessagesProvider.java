
package com.robotwitter.webapp.messages;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.robotwitter.webapp.ViewsMap;
import com.robotwitter.webapp.general.General;




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
	public MessagesProvider(ViewsMap views)
	{
		containers = new HashMap<>();
		add(General.MESSAGES);
		views.keySet().forEach(name -> add(name));
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


	/**
	 * Adds a messages container to the provider by name.
	 *
	 * @param name
	 *            the messages container's name
	 */
	private void add(String name)
	{
		containers.put(name, new ViewMessagesContainer(name));
	}
	
	
	
	/** A mapping from the container's name to its provided instance. */
	Map<String, IMessagesContainer> containers;
}
