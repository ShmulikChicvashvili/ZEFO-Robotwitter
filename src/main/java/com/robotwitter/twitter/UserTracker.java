/**
 * 
 */
package com.robotwitter.twitter;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBTwitterAccount;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;

/**
 * @author Itay, Shmulik
 *
 */
public class UserTracker implements IUserTracker
{
	@Inject
	public UserTracker(@Named("User based factory") TwitterStreamFactory factory, IDatabaseTwitterAccounts db) {
		twitterAccountsDB = db;
		userAccount = null;
		stream = factory.getInstance();
	}
	
	/* (non-Javadoc) @see com.robotwitter.twitter.IUserTracker#addListener(twitter4j.UserStreamListener) */
	@Override
	public void addListener(UserStreamListener listener)
	{
		stream.addListener(listener);
	}


	/* (non-Javadoc) @see com.robotwitter.twitter.IUserTracker#beginTrack() */
	@Override
	public void beginTrack()
	{
		stream.user();
	}
	/* (non-Javadoc) @see com.robotwitter.twitter.IUserTracker#getTrackedUser() */
	@Override
	public Long getTrackedUser()
	{
		return userAccount.getUserId();
	}


	/* (non-Javadoc) @see com.robotwitter.twitter.IUserTracker#removeListener(twitter4j.UserStreamListener) */
	@Override
	public void removeListener(UserStreamListener listener)
	{
		stream.removeListener(listener);
	}
	
	public void setUser(Long userID) {
		userAccount = twitterAccountsDB.get(userID);
		if(userAccount == null) {
			throw new RuntimeException("Tried to track a user that doesn't exist!");
		}
		stream.setOAuthAccessToken(new AccessToken(userAccount.getToken(), userAccount.getPrivateToken()));
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.twitter.IUserTracker#stopTrack() */
	@Override
	public void stopTrack()
	{
		stream.cleanUp();
	}
	
	
	private IDatabaseTwitterAccounts twitterAccountsDB;
	
	
	private TwitterStream stream;


	private DBTwitterAccount userAccount;
	
}
