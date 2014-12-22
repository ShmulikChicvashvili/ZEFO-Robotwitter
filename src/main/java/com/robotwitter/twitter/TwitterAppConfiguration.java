/**
 *
 */

package com.robotwitter.twitter;


import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;




/**
 * @author Itay
 *
 */
public class TwitterAppConfiguration
{
	
	/**
	 * @return
	 */
	public Configuration getAppConfiguration()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setApplicationOnlyAuthEnabled(true);
		OAuth2Token token = null;
		try
		{
			token =
				new TwitterFactory(cb.build()).getInstance().getOAuth2Token();
		} catch (TwitterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setApplicationOnlyAuthEnabled(true);
		cb.setOAuth2TokenType(token.getTokenType());
		cb.setOAuth2AccessToken(token.getAccessToken());
		
		return cb.build();
	}


	public Configuration getUserConfiguration()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		
		return cb.build();
	}



	public static final String USER_BASED = "TwitterAppConfiguration.user-base";
	
	// TODO: allow to change and configure these values
	String consumerKey = "wTlnDZNxIsFU65JnBnCyxlEDH"; //$NON-NLS-1$
	
	String consumerSecret =
		"soeJE6y7YiYRH9LRqoAf9r1sVgCAokXG3gIC1KYEu9g4cIWmt0"; //$NON-NLS-1$
	
	Configuration conf;
}
