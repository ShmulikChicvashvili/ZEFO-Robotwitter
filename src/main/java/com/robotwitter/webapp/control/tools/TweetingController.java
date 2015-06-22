package com.robotwitter.webapp.control.tools;


import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBTweetPostingPreferences;
import com.robotwitter.posting.BasicPreference;
import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.PostfixPreference;
import com.robotwitter.posting.Preference;
import com.robotwitter.posting.PrefixPreference;
import com.robotwitter.posting.TweetPostingPreferenceType;
import com.robotwitter.webapp.control.account.IAccountController;
import com.robotwitter.webapp.control.tools.tweeting.Tweet;
import com.robotwitter.webapp.control.general.Tweet;





/**
 * Implementation of the tweeting controller interface.
 *
 * @author Shmulik
 * @author Itay Khazon
 * @author Amir Drutin
 * 
 *         EDITED by Amir Drutin 04/05/15
 */

public class TweetingController implements ITweetingController
{

	
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
	
	public final List<String> previewTweet(String tweet)
	{
		return breakTweet(tweet);
	}
	
	@Override
	public SqlError setPreference(TweetPostingPreferenceType preference) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public SqlError setPrefix(String prefix){
		DBTweetPostingPreferences postingPreferences = preferencesDB.get(accountContoller.getEmail());
		postingPreferences.setPostingPreference(TweetPostingPreferenceType.PREFIX);
		postingPreferences.setPrefix(prefix);
		SqlError err = preferencesDB.update(postingPreferences);
		if(err == SqlError.DOES_NOT_EXIST){
			return preferencesDB.insert(postingPreferences);
		};
		return err;
	}

	@Override
	public SqlError setSuffix(String suffix){
		DBTweetPostingPreferences postingPreferences = preferencesDB.get(accountContoller.getEmail());
		if(postingPreferences == null){
			postingPreferences = new DBTweetPostingPreferences();
		}
		postingPreferences.setPostingPreference(TweetPostingPreferenceType.SUFFIX);
		postingPreferences.setPostfix(suffix);
		SqlError err = preferencesDB.update(postingPreferences);
		if(err == SqlError.DOES_NOT_EXIST){
			return preferencesDB.insert(postingPreferences);
		};
		return err;
	}
	

	@Override
	public String getPrefix(){
		DBTweetPostingPreferences postingPreferences = preferencesDB.get(accountContoller.getEmail());
		if(postingPreferences != null){
			return "";
		}
		return postingPreferences.getPrefix();
	}

	@Override
	public String getSuffix(){
		DBTweetPostingPreferences postingPreferences = preferencesDB.get(accountContoller.getEmail());
		if(postingPreferences != null){
			return "";
		}
		return postingPreferences.getPostfix();
	}


	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;


	IDatabaseTweetPostingPreferences preferencesDB;

	Preference preference;

	private IAccountController accountContoller;

}
