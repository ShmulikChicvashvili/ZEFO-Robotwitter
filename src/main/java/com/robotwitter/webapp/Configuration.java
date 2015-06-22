
package com.robotwitter.webapp;


import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.classification.TweetClassifierListener;
import com.robotwitter.database.MySqlConnectionEstablisherModule;
import com.robotwitter.database.MySqlDBModule;
import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBFollower;
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
import com.robotwitter.twitter.TwitterAppConfiguration;
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


	private DBFollower buildFollowerFromUser(User user)
	{
		String desc = user.getDescription();
		if (user.getDescription() == null)
		{
			desc = "";
		}
		String location = user.getLocation();
		if (user.getLocation() == null)
		{
			location = "";
		}
		return new DBFollower(
			user.getId(),
			user.getName(),
			user.getScreenName(),
			desc,
			user.getFollowersCount(),
			user.getFriendsCount(),
			location,
			user.getFavouritesCount(),
			user.getLang(),
			user.isVerified(),
			new Timestamp(user.getCreatedAt().getTime()),
			user.getOriginalProfileImageURL());
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
		IDatabaseFollowers followersDB =
			injector.getInstance(IDatabaseFollowers.class);
		ArrayList<DBTwitterAccount> accounts = accountsDB.getAllAccounts();
		if (accounts == null) { return; }

		final long[] ids = new long[accounts.size()];
		for (int i = 0; i < accounts.size(); i++)
		{
			ids[i] = accounts.get(i).getUserId();
		}

		ResponseList<User> userList = null;
		try
		{
			final TwitterFactory tf =
				new TwitterFactory(
					new TwitterAppConfiguration().getAppConfiguration());
			Twitter connector = tf.getInstance();
			userList = connector.lookupUsers(ids);
		} catch (final TwitterException e)
		{
			e.printStackTrace();
		}
		
		for (User user: userList) {
			followersDB.insert(buildFollowerFromUser(user));
		}

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
		TweetClassifierListener classifier =
			injector.getInstance(TweetClassifierListener.class);
		classifier.setUser(account.getUserId());

		FollowerIdsBackfiller backfiller =
			injector.getInstance(FollowerIdsBackfiller.class);
		backfiller.setUser(account.getUserId());
		
		tracker.addListener(dbListener);
		tracker.addListener(hhListener);
		tracker.addListener(classifier);
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
