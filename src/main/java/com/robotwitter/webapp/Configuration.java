
package com.robotwitter.webapp;


import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamListener;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySqlDBUserModule;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBTwitterAccount;
import com.robotwitter.management.EmailPasswordRetrieverModule;
import com.robotwitter.management.ITwitterTracker.Status;
import com.robotwitter.management.RetrievalMailBuilderModule;
import com.robotwitter.management.TwitterTracker;
import com.robotwitter.miscellaneous.GmailSenderModule;
import com.robotwitter.statistics.HeavyHitters;
import com.robotwitter.twitter.HeavyHittersListener;
import com.robotwitter.twitter.HeavyHittersListnerFactory;
import com.robotwitter.twitter.IUserTracker;
import com.robotwitter.twitter.TwitterAppConfiguration;
import com.robotwitter.twitter.TwitterAttacherModule;
import com.robotwitter.twitter.UserTracker;
import com.robotwitter.webapp.control.ControllerModule;
import com.robotwitter.webapp.messages.MessagesProvider;




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
		menus = new MenuMap();
		views = new ViewMap();
		
		initialiseMessagesProvider();
		initialiseInjector();
		initialiseMenuFactory();
		initialiseViewFactory();
		
		initialiseUserTracking();
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
		context.setAttribute(INJECTOR, injector);
		context.setAttribute(MENU_FACTORY, menuFactory);
		context.setAttribute(VIEW_FACTORY, viewFactory);
	}
	
	
	/** Initialises the injector. */
	private void initialiseInjector()
	{
		injector =
			Guice.createInjector(
				new ConfigurationModule(),
				new MenuModule(menus, messagesProvider),
				new ViewModule(views, messagesProvider),
				new ControllerModule(),
				new GmailSenderModule(),
				new EmailPasswordRetrieverModule(),
				new RetrievalMailBuilderModule(),
				new MySqlDBUserModule(),
				new TwitterAttacherModule());
		
	}
	
	
	/** Initialises the menu factory. */
	private void initialiseMenuFactory()
	{
		menuFactory = new GuiceMenuFactory(menus, injector);
	}
	
	
	/** Initialises the messages provider. */
	private void initialiseMessagesProvider()
	{
		messagesProvider = new MessagesProvider(menus, views);
	}
	
	
	/**
	 * 
	 */
	//FIXME: this is gross. fix this ASAP!
	private void initialiseUserTracking()
	{
		accountsTracker = new TwitterTracker();
		IDatabaseTwitterAccounts accountsDB =
			injector.getInstance(IDatabaseTwitterAccounts.class);
		ArrayList<DBTwitterAccount> accounts = accountsDB.getAllAccounts();
		if (accounts == null) { return; }
		
		TwitterStreamFactory factory =
			new TwitterStreamFactory(
				new TwitterAppConfiguration().getUserConfiguration());
		for (DBTwitterAccount account : accounts)
		{
			IUserTracker tracker =
				new UserTracker(factory, accountsDB, account.getUserId());
			Status result = accountsTracker.addUserTracker(tracker);
			if (result != Status.SUCCESS)
			{
				System.err.println("woops, everything is horrible!");
			}
			UserStreamListener hhListener =
				new HeavyHittersListener(new HeavyHittersListnerFactory(
					new HeavyHitters(100, 10)), account.getUserId());
//			UserStreamListener dbListener =
//				new FollowerStoreListener(new MySqlFollower
		}
		
	}
	
	
	/** Initialises the view factory. */
	private void initialiseViewFactory()
	{
		viewFactory = new GuiceViewFactory(views, injector);
	}
	
	
	
	private TwitterTracker accountsTracker;
	
	/** The injector resolving all dependencies of Robotwitter classes. */
	private Injector injector;
	
	/** The menu factory attribute's name. */
	public static final String MENU_FACTORY = "MenuFactory";
	
	/** The view factory attribute's name. */
	public static final String VIEW_FACTORY = "ViewFactory";
	
	/** The injector attribute's name. */
	public static final String INJECTOR = "Injector";
	
	/** The menu factory, used for creation of menus during a client session. */
	GuiceMenuFactory menuFactory;
	
	/** A mapping of all available menus. */
	MenuMap menus;
	
	/** Provides messages containers for the views. */
	MessagesProvider messagesProvider;
	
	/** The view factory, used for creation of views during a client session. */
	GuiceViewFactory viewFactory;
	
	/** A mapping of all accessible views. */
	ViewMap views;
}
