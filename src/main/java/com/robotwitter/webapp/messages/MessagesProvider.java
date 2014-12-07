
package com.robotwitter.webapp.messages;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.robotwitter.webapp.messages.login.LoginMessagesContainer;




/**
 * A simple implementation using {@link java.util.HashMap}.
 *
 * @author Hagai Akibayov
 */
public class MessagesProvider implements IMessagesProvider
{
	
	/** Instantiates a new messages provider. */
	public MessagesProvider()
	{
		containers = new HashMap<>();
		containers.put(MessagesContainerID.LOGIN, new LoginMessagesContainer());
	}
	
	
	@Override
	public final IMessagesContainer get(MessagesContainerID id)
	{
		return containers.get(id);
	}
	
	
	@Override
	public final void set(Locale locale)
	{
		containers.forEach((name, container) -> container.set(locale));

	}



	/** A mapping from the container's name to its provided instance. */
	Map<MessagesContainerID, IMessagesContainer> containers;
}
