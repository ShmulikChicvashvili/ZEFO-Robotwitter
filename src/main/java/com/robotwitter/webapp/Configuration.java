
package com.robotwitter.webapp;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.management.EmailPasswordRetrieverModule;
import com.robotwitter.management.RetrievalMailBuilderModule;
import com.robotwitter.miscellaneous.GmailSenderModule;
import com.robotwitter.webapp.messages.MessagesProvider;
import com.robotwitter.webapp.view.GuiceViewFactory;




/**
 * Represents a web-application configuration.
 * <p>
 * This class is instantiated once on the application's startup and the instance
 * then becomes available widely throughout the entire application as the
 * application's configuration.
 *
 * @author Hagai Akibayov
 */
@WebListener
public class Configuration implements ServletContextListener
{
	
	/** Instantiates a new configuration. */
	public Configuration()
	{
		views = new ViewMap();
		
		initialiseMessagesProvider();
		initialiseViewFactory();
	}
	
	
	@Override
	public final void contextDestroyed(ServletContextEvent event)
	{
		// Do nothing
	}
	
	
	@Override
	public final void contextInitialized(ServletContextEvent event)
	{
		// In order to pass the view factory to the navigation UI, we have to
		// set a global context attribute. The reason is that the servlet
		// creates new UIs using only the nullary constructor, which means
		// dependencies cannot be injected.
		
		final ServletContext context = event.getServletContext();
		context.setAttribute(VIEW_FACTORY, viewFactory);
	}
	
	
	/** Initialises the messages provider. */
	private void initialiseMessagesProvider()
	{
		messagesProvider = new MessagesProvider(views);
	}
	
	
	/** Initialises the view factory. */
	private void initialiseViewFactory()
	{
		final ViewsModule viewsModule =
			new ViewsModule(views, messagesProvider);
		// FIXME: merge all these modules into a single module... apparently
		// thats a good idea...
		final Injector injector =
			Guice.createInjector(
				viewsModule,
				new GmailSenderModule(),
				new EmailPasswordRetrieverModule(),
				new RetrievalMailBuilderModule(),
				new MySQLDBUserModule());
		viewFactory = new GuiceViewFactory(views, injector);
	}
	
	
	
	/** The view factory attribute's name. */
	public static final String VIEW_FACTORY = "ViewFactory"; //$NON-NLS-1$
	
	/** A mapping of all accessible views. */
	ViewMap views;
	
	/** Provides messages containers for the views. */
	MessagesProvider messagesProvider;
	
	/** The view factory, used for creation of views during a client session. */
	GuiceViewFactory viewFactory;
}
