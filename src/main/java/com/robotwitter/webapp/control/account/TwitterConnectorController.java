
package com.robotwitter.webapp.control.account;


import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.robotwitter.management.ITwitterTracker;
import com.robotwitter.twitter.ITwitterAttacher;
import com.robotwitter.twitter.IllegalPinException;
import com.robotwitter.twitter.TwitterAccount;

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
	 * @param factory the factory that creates new Twitter instances
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
			// TODO: add the tracking of the user here! 
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
