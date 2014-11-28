
package twitter;


import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;




/**
 * @author Itay
 *
 */
public class TwitterAttacher implements ITwitterAttacher
{
	
	private static final int CORRECT_PIN_LENGTH = 7;


	/* (non-Javadoc) @see
	 * twitter.ITwitterAttacher#getAuthorizationURL(twitter.TwitterAccount) */
	public String getAuthorizationURL(TwitterAccount account)
	{
		Twitter twitter = account.getTwitter();
		try
		{
			RequestToken requestToken = twitter.getOAuthRequestToken();
			String $ = requestToken.getAuthorizationURL(); // TODO: find a way
															// to get the url
															// from the
															// configuration.
			return $;
		} catch (TwitterException e)
		{
			// TODO: display error page to user.
			e.printStackTrace();
		}
		return null; // TODO: what to return here in case of error?
	}
	
	
	/* (non-Javadoc) @see
	 * twitter.ITwitterAttacher#attachAccount(twitter.TwitterAccount,
	 * java.lang.String) */
	public void attachAccount(TwitterAccount account, String pin) throws IllegalPinException
	{
		if(pin.length() != CORRECT_PIN_LENGTH) {
			throw new IllegalPinException("The Pin "+pin+" is illegal!");
		}
		Twitter twitter = account.getTwitter();
		try
		{
			RequestToken requestToken = twitter.getOAuthRequestToken();
			AccessToken accessToken =
				twitter.getOAuthAccessToken(requestToken, pin);
			account.setAttached(true);
		} catch (TwitterException e)
		{
			// TODO: display error page to user.
			e.printStackTrace();
		}
		
	}
}
