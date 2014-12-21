
package com.robotwitter.webapp.control.account;


import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.google.inject.Inject;

import com.robotwitter.twitter.ITwitterAttacher;
import com.robotwitter.twitter.IllegalPinException;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.twitter.TwitterAppConfiguration;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * Stub implementation.
 *
 * @author Itay Khazon
 */
public class TwitterConnectorController implements ITwitterConnectorController
{
	
	/**
	 * Instantiates a new twitter connector controller stub.
	 *
	 * @param attacher
	 *            the attacher
	 * @param conf
	 *            the configuration of the application, to communicate with
	 *            twitter
	 */
	@Inject
	public TwitterConnectorController(
		ITwitterAttacher attacher,
		TwitterAppConfiguration conf)
	{
		this.attacher = attacher;

		tf = new TwitterFactory(conf.getUserConfiguration());
		twitterAccount = new TwitterAccount(tf);
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
		} catch (IllegalStateException | TwitterException e)
		{
			e.printStackTrace();
			return Status.FAILURE;
		}

		// FIXME: quick and dirty fix, we need to repair this!
		twitterAccount = new TwitterAccount(tf);
		
		return Status.SUCCESS;
	}
	
	
	@Override
	public final String getConnectionURL()
	{
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
