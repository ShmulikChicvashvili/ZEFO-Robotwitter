
package com.robotwitter.webapp;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.robotwitter.webapp.control.automate.CannedTweetsController;
import com.robotwitter.webapp.control.automate.ICannedTweetsController;
import com.robotwitter.webapp.control.login.EmailPasswordRetrievalController;
import com.robotwitter.webapp.control.login.ILoginController;
import com.robotwitter.webapp.control.login.IPasswordRetrievalController;
import com.robotwitter.webapp.control.login.LoginController;
import com.robotwitter.webapp.control.registration.IRegistrationController;
import com.robotwitter.webapp.control.registration.RegistrationController;
import com.robotwitter.webapp.general.General;
import com.robotwitter.webapp.general.PasswordValidator;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.messages.IMessagesProvider;
import com.robotwitter.webapp.util.AbstractPasswordValidator;




/**
 * A module for resolving all view dependencies using Guice.
 *
 * @author Itay Khazon
 */
public class ViewModule extends AbstractModule
{
	
	/**
	 * Instantiates a new view module.
	 *
	 * @param views
	 *            a mapping of all accessible views
	 * @param messagesProvider
	 *            the provider of messages containers for the views
	 */
	public ViewModule(ViewMap views, IMessagesProvider messagesProvider)
	{
		this.views = views;
		this.messagesProvider = messagesProvider;
	}


	/**
	 * Binds an instance of {@link IMessagesContainer} to instances of a given
	 * {@link com.vaadin.navigator.View} given their name.
	 * <p>
	 * The given view class must contain a named dependency for the messages
	 * container with the given name. The messages provider should also be able
	 * to provide a messages container of the same name.
	 *
	 * @param name
	 *            the view's name
	 */
	private void bindMessagesContainer(String name)
	{
		// ignore empty name (mapped to the default view)
		if ("".equals(name)) { return; }

		bind(IMessagesContainer.class)
		.annotatedWith(Names.named(name))
		.toInstance(messagesProvider.get(name));
	}
	
	
	@Override
	protected final void configure()
	{
		// Bind message containers
		views.keySet().forEach(name -> bindMessagesContainer(name));
		bindMessagesContainer(General.MESSAGES);
		
		// Bind all non-generic dependencies
		bind(IPasswordRetrievalController.class).to(
			EmailPasswordRetrievalController.class);
		
		bind(AbstractPasswordValidator.class)
		.to(PasswordValidator.class)
		.asEagerSingleton();
		
		// Bind controllers
		bind(ILoginController.class).to(LoginController.class);
		bind(IRegistrationController.class).to(RegistrationController.class);
		bind(ICannedTweetsController.class).to(CannedTweetsController.class);
	}
	
	
	
	/** A mapping of all accessible views. */
	private final ViewMap views;
	
	/** Provides messages containers for the views. */
	IMessagesProvider messagesProvider;
	
}
