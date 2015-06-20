package com.robotwitter.webapp.control.tools;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.primitives.DBTweetPostingPreferences;
import com.robotwitter.posting.BasicPreference;
import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.PostfixPreference;
import com.robotwitter.posting.Preference;
import com.robotwitter.posting.PrefixPreference;
import com.robotwitter.posting.TweetPostingPreferenceType;
import com.robotwitter.webapp.control.account.IAccountController;
import com.robotwitter.webapp.control.tools.tweeting.Tweet;

/**
 * Implementation of the tweeting controller interface.
 *
 * @author Shmulik
 * @author Itay Khazon
 * @author Amir Drutin
 * 
 *         EDITED by Amir Drutin 04/05/15
 */
public class TweetingController implements ITweetingController {

	/**
	 * TESTS!!!
	 */
	
	/**
	 * @param preference
	 */
	@Inject
	public TweetingController(IDatabaseTweetPostingPreferences db,
			IAccountController accountController) {
		preferencesDB = db;
		this.accountContoller = accountController;

		String email = accountController.getEmail();

		DBTweetPostingPreferences preferences = preferencesDB.get(email);
		/* different func */
		if (preferences == null) {
			preference = new BasicPreference();
			preferences = new DBTweetPostingPreferences(email, null, null, null);
			preferences.setPostingPreference(TweetPostingPreferenceType.BASIC);
			preferencesDB.insert(preferences);
		} else {
			TweetPostingPreferenceType prefType = preferences
					.getPostingPreference();
			if (prefType == TweetPostingPreferenceType.BASIC) {
				preference = new BasicPreference();
			} else if (prefType == TweetPostingPreferenceType.NUMBERED) {
				preference = new NumberedPreference();
			} else if (prefType == TweetPostingPreferenceType.PREFIX) {
				preference = new PrefixPreference(preferences.getPrefix());
			} else if (prefType == TweetPostingPreferenceType.SUFFIX) {
				preference = new PostfixPreference(preferences.getPostfix());
			}
		}
	}

	public TweetPostingPreferenceType getPreference() {
		String email = this.accountContoller.getEmail();
		return preferencesDB.get(email).getPostingPreference();
	}

	@Override
	public final List<String> breakTweet(String tweet) {
		return preference.generateTweet(tweet);
	}

	
	/**
	 * Messages should be hard coded strings.
	 */
	/*
	 * (non-Javadoc) @see
	 * com.robotwitter.webapp.control.tools.ITweetingController
	 * #getOptionalResponses
	 * (com.robotwitter.webapp.control.tools.tweeting.Tweet)
	 */
	@Override
	public List<String> getOptionalResponses(Tweet tweet) {
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
	public final List<String> previewTweet(String tweet) {

		return breakTweet(tweet);
	}
	
	@Override
	public void setPrefix(String prefix){
		/* TODO: insert prefix to DB */
	}

	@Override
	public void setSuffix(String suffix){
		/* TODO: insert suffix to DB */
	}
	
	@Override
	public void getPrefix(String prefix){
		/* TODO: get prefix from DB */
	}

	@Override
	public void getSuffix(String suffix){
		/* TODO: get suffix from DB */
	}
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	IDatabaseTweetPostingPreferences preferencesDB;

	Preference preference;

	private IAccountController accountContoller;
}
