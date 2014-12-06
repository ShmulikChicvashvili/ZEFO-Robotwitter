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
	public TwitterAppConfiguration()
	{
		//FIXME: fix this baaaah code
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(this.consumerKey);
		cb.setOAuthConsumerSecret(this.consumerSecret);
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
		cb.setOAuthConsumerKey(this.consumerKey);
		cb.setOAuthConsumerSecret(this.consumerSecret);
		cb.setApplicationOnlyAuthEnabled(true);
		cb.setOAuth2TokenType(token.getTokenType());
		cb.setOAuth2AccessToken(token.getAccessToken());
		
		this.conf = cb.build();
	}
	
	
	public Configuration getConfiguration()
	{
		return this.conf;
	}
	
	
	
	// TODO: allow to change and configure these values
	String consumerKey = "0kkK9O83YyDRC3HkOP97HFiIi"; //$NON-NLS-1$
	
	String consumerSecret =
		"13wJ8y3gu4epaM9vqJFuWUa0MNE8IfCDYmBdcKE0NfT3RWbM9M"; //$NON-NLS-1$
	
	Configuration conf;
}
