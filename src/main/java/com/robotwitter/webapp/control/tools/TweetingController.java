
package com.robotwitter.webapp.control.tools;


import java.util.ArrayList;
import java.util.List;

import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.Preference;
import com.robotwitter.webapp.control.tools.tweeting.Tweet;




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
		.add("Hi, This is robotwitter and I'm urging you to follow us on twitter!!!!");
		responses
		.add("Hey, have you checked our cool twitter page? it's a lot of fun and code!");
		responses.add("Nah, you don't really wanna tweet this...");

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
