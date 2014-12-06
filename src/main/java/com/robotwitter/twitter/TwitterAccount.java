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
		this.twitter = tf.getInstance();
		this.attached = false;
	}
	
	
	/**
	 * @return
	 */
	public Twitter getTwitter()
	{
		return this.twitter;
	}
	
	
	/**
	 * @param b
	 */
	public void setAttached(final boolean b)
	{
		this.attached = b;
		
	}
	
	
	public Boolean isAttached()
	{
		return this.attached;
		
	}
	
	
	
	// TODO: find something more elegant then this composition.
	Twitter twitter;
	
	Boolean attached;
	
}
