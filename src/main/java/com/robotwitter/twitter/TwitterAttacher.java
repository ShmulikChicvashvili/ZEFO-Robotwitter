
package com.robotwitter.twitter;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBTwitterAccount;




/**
 * @author Itay
 *
 */
public class TwitterAttacher implements ITwitterAttacher
{
	@Inject
	public TwitterAttacher(final IDatabaseTwitterAccounts db)
	{
		twitterDB = db;
	}
	
	
	/* (non-Javadoc) @see
	 * twitter.ITwitterAttacher#attachAccount(twitter.TwitterAccount,
	 * java.lang.String) */
	@Override
	public void attachAccount(
		final String userEmail,
		final TwitterAccount account,
		final String pin) throws IllegalPinException
	{
		if (pin.length() != CORRECT_PIN_LENGTH) { throw new IllegalPinException(
			"The Pin " + pin + " is illegal!"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		Twitter twitter = account.getTwitter();
		try
		{
			final AccessToken accessToken = twitter.getOAuthAccessToken(pin);
			final DBTwitterAccount dbAccount =
				new DBTwitterAccount(
					userEmail,
					accessToken.getToken(),
					accessToken.getTokenSecret(),
					twitter.getId());
			if (!twitterDB.isExists(twitter.getId()))
			{
				twitterDB.insert(dbAccount);
			}
			account.setAttached(true);
		} catch (final TwitterException e)
		{
			// TODO: display error page to user.
			e.printStackTrace();
		}

	}
	
	
	/* (non-Javadoc) @see
	 * twitter.ITwitterAttacher#getAuthorizationURL(twitter.TwitterAccount) */
	@Override
	public String getAuthorizationURL(final TwitterAccount account)
	{
		final Twitter twitter = account.getTwitter();
		try
		{
			final RequestToken requestToken = twitter.getOAuthRequestToken();
			final String $ = requestToken.getAuthorizationURL(); // TODO: find a
			// way
			// to get the url
			// from the
			// configuration.
			return $;
		} catch (final TwitterException e)
		{
			// TODO: display error page to user.
			e.printStackTrace();
		}
		return null; // TODO: what to return here in case of error?
	}
	
	
	
	private static final int CORRECT_PIN_LENGTH = 7;
	
	IDatabaseTwitterAccounts twitterDB;
}
