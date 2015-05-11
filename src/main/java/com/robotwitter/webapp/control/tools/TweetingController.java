
package com.robotwitter.webapp.control.tools;


import java.util.ArrayList;
import java.util.List;

import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.Preference;
import com.robotwitter.webapp.control.general.Tweet;




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
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.tools.ITweetingController
	 * #getOptionalResponses
	 * (com.robotwitter.webapp.control.tools.tweeting.Tweet) */
	@Override
	public List<String> getOptionalResponses(Tweet tweet)
	{
		// TODO Auto-generated method stub
		List<String> responses = new ArrayList<>();
		responses
			.add("Hi, we are sorry you feel this way. We are doing the best to be helpful.");
		responses
			.add("Hey, thank you for your response we will do our best to improve!");
		responses.add("FUCK OFF YA' CHUBBY COON, YA' WRINKLY BITCH!!");
		
		return responses;
	}
	
	
	@Override
	public final List<String> previewTweet(String tweet)
	{
		
		return breakTweet(tweet);
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	Preference preference;
}
