
package com.robotwitter.webapp;


import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySqlConnectionEstablisherModule;
import com.robotwitter.database.MySqlDBModule;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBTwitterAccount;
import com.robotwitter.management.EmailPasswordRetrieverModule;
import com.robotwitter.management.RetrievalMailBuilderModule;
import com.robotwitter.management.TwitterTracker;
import com.robotwitter.miscellaneous.GmailSenderModule;
import com.robotwitter.statistics.UserListenerModule;
import com.robotwitter.twitter.FollowerIdsBackfiller;
import com.robotwitter.twitter.FollowerStoreListener;
import com.robotwitter.twitter.HeavyHittersListener;
import com.robotwitter.twitter.IUserTracker;
import com.robotwitter.twitter.UserTracker;
import com.robotwitter.twitter.UserTrackerModule;
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
				new MySqlConnectionEstablisherModule(),
				new MySqlDBModule(),
				new ConfigurationModule(),
				new MenuModule(menus, messagesProvider),
				new ViewModule(views, messagesProvider),
				new ControllerModule(),
				new GmailSenderModule(),
				new EmailPasswordRetrieverModule(),
				new RetrievalMailBuilderModule(),
				new UserTrackerModule(),
				new UserListenerModule());
		
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
	 * Created and guiced up by Itay and Shmulik. Initialises the user trackers
	 * and starts them.
	 */
	private void initialiseUserTracking()
	{
		accountsTracker = new TwitterTracker();
		IDatabaseTwitterAccounts accountsDB =
			injector.getInstance(IDatabaseTwitterAccounts.class);
		ArrayList<DBTwitterAccount> accounts = accountsDB.getAllAccounts();
		if (accounts == null) { return; }
		
		for (DBTwitterAccount account : accounts)
		{
			track(account);
		}
		
	}


	/** Initialises the view factory. */
	private void initialiseViewFactory()
	{
		viewFactory = new GuiceViewFactory(views, injector);
	}
	
	
	/**
	 * @param account
	 */
	private void track(DBTwitterAccount account)
	{
		System.out.println("trying to track " + account.getUserId());
		IUserTracker tracker = injector.getInstance(IUserTracker.class);
		((UserTracker) tracker).setUser(account.getUserId());
		
		HeavyHittersListener hhListener =
			injector.getInstance(HeavyHittersListener.class);
		hhListener.setUser(account.getUserId());
		FollowerStoreListener dbListener =
			injector.getInstance(FollowerStoreListener.class);
		dbListener.setUser(account.getUserId());
		
		FollowerIdsBackfiller backfiller = injector.getInstance(FollowerIdsBackfiller.class);
		backfiller.setUser(account.getUserId());
		
		tracker.addListener(dbListener);
		tracker.addListener(hhListener);
		tracker.addBackfiller(backfiller);
		accountsTracker.addUserTracker(tracker);
		accountsTracker.startTracker(account.getUserId());
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
