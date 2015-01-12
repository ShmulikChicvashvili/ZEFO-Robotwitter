/**
 *
 */

package com.robotwitter.twitter;


import twitter4j.Twitter;
import twitter4j.TwitterFactory;




/**
 * @author Itay
 *
 */
public class TwitterAccount
{
	
	public TwitterAccount(final TwitterFactory tf)
	{
		twitterConnector = tf.getInstance();
		attached = false;
	}
	
	
	/**
	 * @return
	 */
	public Twitter getTwitter()
	{
		return twitterConnector;
	}
	
	
	public Boolean isAttached()
	{
		return attached;
		
	}
	
	
	/**
	 * @param b
	 */
	public void setAttached(final boolean b)
	{
		attached = b;
		
	}
	
	public void setTwitter(final Twitter twitterConnector) {
		this.twitterConnector = twitterConnector;
	}
	
	
	
	Twitter twitterConnector;
	
	Boolean attached;
	
}
