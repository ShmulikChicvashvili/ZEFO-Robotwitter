/**
 * 
 */
package twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * @author Itay
 *
 */
public class TwitterAccount
{

	//TODO: find something more elegant then this composition.
	Twitter twitter;
	Boolean isAttached;
	
	public TwitterAccount(TwitterFactory tf) {
		this.twitter = tf.getInstance();
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
	public void setAttached(boolean b)
	{
		isAttached = b;
		
	}	

	
}
