
package com.robotwitter.webapp.control.account;


import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import com.vaadin.server.VaadinServlet;

import com.robotwitter.classification.TweetClassifierListener;
import com.robotwitter.management.ITwitterTracker;
import com.robotwitter.twitter.FollowerIdsBackfiller;
import com.robotwitter.twitter.FollowerStoreListener;
import com.robotwitter.twitter.HeavyHittersListener;
import com.robotwitter.twitter.ITwitterAttacher;
import com.robotwitter.twitter.IUserTracker;
import com.robotwitter.twitter.IllegalPinException;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.twitter.UserTracker;
import com.robotwitter.webapp.Configuration;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * Stub implementation.
 *
 * @author Itay Khazon
 */
public class TwitterConnectorController implements ITwitterConnectorController
{
	
	/**
	 * Instantiates a new twitter connector controller.
	 * 
	 * @param tracker
	 *            the tracker that gets information from twitter
	 * @param attacher
	 *            the attacher
	 * @param factory
	 *            the factory that creates new Twitter instances
	 */
	@Inject
	public TwitterConnectorController(
		ITwitterTracker tracker,
		ITwitterAttacher attacher,
		@Named("User based factory") TwitterFactory factory)
	{
		this.attacher = attacher;
		this.tracker = tracker;
		
		tf = factory;
		twitterAccount = null;
		id = -1;
		screenname = null;
	}
	
	
	/**
	 * Connect.
	 *
	 * @param email
	 *            the email
	 * @param pin
	 *            the pin
	 * @return the status
	 */
	@Override
	public final Status connect(String email, String pin)
	{
		try
		{
			attacher.attachAccount(email, twitterAccount, pin);
		} catch (IllegalPinException e)
		{
			return Status.PIN_IS_INCORRECT;
		}
		
		try
		{
			id = twitterAccount.getTwitter().getId();
			screenname = twitterAccount.getTwitter().getScreenName();
			track();
		} catch (IllegalStateException | TwitterException e)
		{
			e.printStackTrace();
			return Status.FAILURE;
		}
		
		return Status.SUCCESS;
	}
	
	
	@Override
	public final String getConnectionURL()
	{
		twitterAccount = new TwitterAccount(tf);
		return attacher.getAuthorizationURL(twitterAccount);
	}
	
	
	@Override
	public final long getID()
	{
		return id;
	}
	
	
	@Override
	public final String getScreenname()
	{
		return screenname;
	}
	
	
	private void track()
	{
		System.out.println("trying to track " + id);
		Injector injector =
			(Injector) VaadinServlet
				.getCurrent()
				.getServletContext()
				.getAttribute(Configuration.INJECTOR);
		IUserTracker userTracker = injector.getInstance(IUserTracker.class);
		((UserTracker) tracker).setUser(id);
		
		HeavyHittersListener hhListener =
			injector.getInstance(HeavyHittersListener.class);
		hhListener.setUser(id);
		FollowerStoreListener dbListener =
			injector.getInstance(FollowerStoreListener.class);
		dbListener.setUser(id);
		TweetClassifierListener classifier = injector.getInstance(TweetClassifierListener.class);
		classifier.setUser(id);
		
		FollowerIdsBackfiller backfiller = injector.getInstance(FollowerIdsBackfiller.class);
		backfiller.setUser(id);
		
		
		userTracker.addListener(dbListener);
		userTracker.addListener(hhListener);
		userTracker.addListener(classifier);
		userTracker.addBackfiller(backfiller);
		
		tracker.addUserTracker(userTracker);
		tracker.startTracker(id);
	}
	
	
	
	/** The tracker. */
	private ITwitterTracker tracker;
	
	/** The tf. */
	private TwitterFactory tf;
	
	/** The recently connected account's ID. */
	private long id;
	
	/** The recently connected account's screenname. */
	private String screenname;
	
	/** The twitter account. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	private TwitterAccount twitterAccount;
	
	/** The attacher. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	private ITwitterAttacher attacher;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
}
