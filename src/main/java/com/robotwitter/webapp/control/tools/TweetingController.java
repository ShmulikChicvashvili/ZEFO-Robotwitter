
package com.robotwitter.webapp.control.tools;


import java.util.List;

import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.Preference;




/**
 * Implementation of the tweeting controller interface.
 *
 * @author Doron Hogery
 * @author Amir Drutin
 */
public class TweetingController implements ITweetingController
{
	
	/**
	 * @param preference
	 */
	public TweetingController()
	{
		preference = new NumberedPreference();
	}
	
	
	@Override
	public final List<String> breakTweet(String tweet)
	{
		return preference.generateTweet(tweet);
	}


	@Override
	public final List<String> previewTweet(String tweet)
	{
		
		return breakTweet(tweet);
	}



	Preference preference;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
