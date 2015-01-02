/**
 * 
 */
package com.robotwitter.twitter;

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
	public UserTracker(TwitterStreamFactory factory, IDatabaseTwitterAccounts db, Long  userID) {
		userAccount = db.get(userID);
		if(userAccount == null) {
			throw new RuntimeException("Tried to track a user that doesn't exist!");
		}
		stream = factory.getInstance(new AccessToken(userAccount.getToken(), userAccount.getPrivateToken()));
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
	
	/* (non-Javadoc) @see com.robotwitter.twitter.IUserTracker#removeListener(twitter4j.UserStreamListener) */
	@Override
	public void removeListener(UserStreamListener listener)
	{
		stream.removeListener(listener);
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.twitter.IUserTracker#stopTrack() */
	@Override
	public void stopTrack()
	{
		stream.cleanUp();
	}
	
	
	private TwitterStream stream;
	
	
	private DBTwitterAccount userAccount;
	
}