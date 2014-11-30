/**
 *
 */

package com.robotwitter.twitter;


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
		final ConfigurationBuilder cb = new ConfigurationBuilder(); // TODO:
																	// what to
		// do with this
		// "new"?
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		conf = cb.build();
		conf.getOAuthRequestTokenURL();
	}
	
	
	public Configuration getConfiguration()
	{
		return conf;
	}
	
	
	
	// TODO: allow to change and configure these values
	String consumerKey = "0kkK9O83YyDRC3HkOP97HFiIi"; //$NON-NLS-1$
	
	String consumerSecret =
		"13wJ8y3gu4epaM9vqJFuWUa0MNE8IfCDYmBdcKE0NfT3RWbM9M"; //$NON-NLS-1$
	
	Configuration conf;
}
