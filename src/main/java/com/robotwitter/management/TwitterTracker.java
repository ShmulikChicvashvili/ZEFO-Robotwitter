/**
 * 
 */
package com.robotwitter.management;

import java.util.HashMap;

import twitter4j.UserStreamListener;

import com.robotwitter.twitter.IUserTracker;

/**
 * @author Itay, Shmulik
 *
 */
public class TwitterTracker implements ITwitterTracker
{
	public TwitterTracker() {
		trackerArray = new HashMap<Long,IUserTracker>();
	}




	/* (non-Javadoc) @see com.robotwitter.management.ITwitterTracker#addListenerToTracker(java.lang.Long, java.lang.Long, twitter4j.UserStreamListener) */
	@Override
	public Status addListenerToTracker(
		Long userID,
		UserStreamListener listener)
	{
		if(!trackerArray.containsKey(userID)) {
			return Status.TRACKER_DOESNT_EXIST;
		}
		trackerArray.get(userID).addListener(listener);
		return Status.SUCCESS;
	}
	

	
	
	/* (non-Javadoc) @see com.robotwitter.management.ITwitterTracker#addUserTracker(com.robotwitter.twitter.IUserTracker) */
	@Override
	public Status addUserTracker(IUserTracker tracker)
	{
		Long userID = tracker.getTrackedUser();
		if(trackerArray.containsKey(userID)) {
			return Status.TRACKER_ALREADY_EXISTS;
		}
		trackerArray.put(userID, tracker);
		return Status.SUCCESS;
	}
	

	
	
	/* (non-Javadoc) @see com.robotwitter.management.ITwitterTracker#removeListenerFromTracker(java.lang.Long, java.lang.Long) */
	@Override
	public Status removeListenerFromTracker(Long userID, UserStreamListener listener)
	{
		if(!trackerArray.containsKey(userID)) {
			return Status.TRACKER_DOESNT_EXIST;
		}
		trackerArray.get(userID).removeListener(null);
		return Status.SUCCESS;
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.management.ITwitterTracker#removeUserTracker(java.lang.Long) */
	@Override
	public Status removeUserTracker(Long userID)
	{
		if(trackerArray.containsKey(userID)) {
			return Status.TRACKER_DOESNT_EXIST;
		}
		trackerArray.remove(userID);
		return Status.SUCCESS;
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.management.ITwitterTracker#startTracker(java.lang.Long) */
	@Override
	public Status startTracker(Long userID)
	{
		if(!trackerArray.containsKey(userID)) {
			return Status.TRACKER_DOESNT_EXIST;
		}
		trackerArray.get(userID).beginTrack();
		return Status.SUCCESS;
	}




	/* (non-Javadoc) @see com.robotwitter.management.ITwitterTracker#stopTracker(java.lang.Long) */
	@Override
	public Status stopTracker(Long userID)
	{
		if(trackerArray.containsKey(userID)) {
			return Status.TRACKER_DOESNT_EXIST;
		}
		trackerArray.get(userID).stopTrack();
		return Status.SUCCESS;
	}




	private HashMap<Long, IUserTracker> trackerArray;
	
}
