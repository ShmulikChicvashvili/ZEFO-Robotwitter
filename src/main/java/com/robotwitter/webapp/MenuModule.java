
package com.robotwitter.webapp;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.robotwitter.webapp.control.account.ITwitterConnectorController;
import com.robotwitter.webapp.control.account.TwitterConnectorController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.messages.IMessagesProvider;




/**
 * A module for resolving all menu dependencies using Guice.
 *
 * @author Hagai Akibayov
 */
public class MenuModule extends AbstractModule
{

	/**
	 * Instantiates a new menu module.
	 *
	 * @param menus
	 *            a mapping of all available menus
	 * @param messagesProvider
	 *            the provider of messages containers for the menus
	 */
	public MenuModule(MenuMap menus, IMessagesProvider messagesProvider)
	{
		this.menus = menus;
		this.messagesProvider = messagesProvider;
	}
	
	
	/**
	 * Binds an instance of {@link IMessagesContainer} to instances of a given
	 * {@link com.vaadin.navigator.View} given their name.
	 * <p>
	 * The given menu class must contain a named dependency for the messages
	 * container with the given name. The messages provider should also be able
	 * to provide a messages container of the same name.
	 *
	 * @param name
	 *            the menu's name
	 */
	private void bindMessagesContainer(String name)
	{
		bind(IMessagesContainer.class)
			.annotatedWith(Names.named(name))
			.toInstance(messagesProvider.get(name));
	}


	@Override
	protected final void configure()
	{
		// Bind message containers
		menus.keySet().forEach(name -> bindMessagesContainer(name));

		// Bind controllers
		bind(ITwitterConnectorController.class).to(
			TwitterConnectorController.class);
	}



	/** A mapping of all available menus. */
	private final MenuMap menus;

	/** Provides messages containers for the menus. */
	IMessagesProvider messagesProvider;

}
