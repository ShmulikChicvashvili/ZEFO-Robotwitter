/**
 * 
 */
package com.robotwitter.statistics;

import java.util.ArrayList;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.User;

/**
 * @author Shmulik
 *
 */
public class HeavyHitters implements IHeavyHitters
{
	
	/* (non-Javadoc) @see com.robotwitter.statistics.IHeavyHitters#getCurrentHeavyHitters() */
	@Override
	public ArrayList<Long> getCurrentHeavyHitters()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.statistics.IHeavyHitters#onDirectMessage(twitter4j.DirectMessage) */
	@Override
	public void onDirectMessage(DirectMessage directMessage)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.statistics.IHeavyHitters#onFavorite(twitter4j.User, twitter4j.User, twitter4j.Status) */
	@Override
	public void onFavorite(User source, User target, Status favoritedStatus)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.statistics.IHeavyHitters#onFollow(twitter4j.User, twitter4j.User) */
	@Override
	public void onFollow(User source, User followedUser)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.statistics.IHeavyHitters#onMentioned(twitter4j.Status) */
	@Override
	public void onMentioned(Status status)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.statistics.IHeavyHitters#onRetweetedStatus(twitter4j.Status) */
	@Override
	public void onRetweetedStatus(Status status)
	{
		// TODO Auto-generated method stub
		
	}
	
}
