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
		twitter = tf.getInstance();
		isAttached = false;
	}
	
	
	/**
	 * @return
	 */
	public Twitter getTwitter()
	{
		return twitter;
	}
	
	
	/**
	 * @param b
	 */
	public void setAttached(final boolean b)
	{
		isAttached = b;

	}
	
	
	
	// TODO: find something more elegant then this composition.
	Twitter twitter;
	
	Boolean isAttached;
	
}
