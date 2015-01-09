/**
 * 
 */

package com.robotwitter.twitter;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.primitives.DBFollower;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserMentionEntity;
import twitter4j.UserStreamListener;




/**
 * @author Itay, Shmulik
 *
 */
public class FollowerStoreListener implements UserStreamListener
{
	@Inject
	public FollowerStoreListener(
		IDatabaseFollowers followersDB)
	{
		this.followersDB = followersDB;
		
		lastUpdated = null;
		
		updateFollowersBarrier = new HashMap<Long, DBFollower>();
	}
	
	
	/* (non-Javadoc) @see twitter4j.UserStreamListener#onBlock(twitter4j.User,
	 * twitter4j.User) */
	@Override
	public void onBlock(User source, User blockedUser)
	{
		// Nothing to do here
		
	}
	
	/* (non-Javadoc) @see twitter4j.UserStreamListener#onDeletionNotice(long,
	 * long) */
	@Override
	public void onDeletionNotice(long directMessageId, long userId)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.StatusListener#onDeletionNotice(twitter4j.StatusDeletionNotice) */
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onDirectMessage(twitter4j.DirectMessage) */
	@Override
	public void onDirectMessage(DirectMessage directMessage)
	{
		User recipient = directMessage.getRecipient();
		User sender = directMessage.getSender();
		
		// This means that I sent a message to him, hence he follows me.
		if (recipient.getId() != userID && sender.getId() == userID)
		{
			updateFollowers(recipient);
			updateNumFollowers(sender);
		}
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.StreamListener#onException(java.lang.Exception) */
	@Override
	public void onException(Exception ex)
	{
		// Nothing to do here
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onFavorite(twitter4j.User, twitter4j.User,
	 * twitter4j.Status) */
	@Override
	public void onFavorite(User source, User target, Status favoritedStatus)
	{
		if (target.getId() == userID && source.getId() != userID)
		{	
			updateFollowers(source);
			updateNumFollowers(target);
		}
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.UserStreamListener#onFollow(twitter4j.User,
	 * twitter4j.User) */
	@Override
	public void onFollow(User source, User followedUser)
	{
		if (followedUser.getId() == userID && source.getId() != userID)
		{
			updateFollowers(source);
			updateNumFollowers(followedUser);
		}
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.UserStreamListener#onFriendList(long[]) */
	@Override
	public void onFriendList(long[] friendIds)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.StatusListener#onScrubGeo(long, long) */
	@Override
	public void onScrubGeo(long userId, long upToStatusId)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.StatusListener#onStallWarning(twitter4j.StallWarning) */
	@Override
	public void onStallWarning(StallWarning warning)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.StatusListener#onStatus(twitter4j.Status) */
	@Override
	public void onStatus(Status status)
	{
		final User source = status.getUser();
		if (source.getId() != userID)
		{
			for (final UserMentionEntity userMentioned : status
				.getUserMentionEntities())
			{
				if (userMentioned.getId() == userID)
				{
					updateFollowers(source);
					break;
				}
			}

			if (status.isRetweet()
				&& status.getRetweetedStatus().getUser().getId() == userID)
			{
				updateFollowers(source);
				updateNumFollowers(status.getRetweetedStatus().getUser());
			}
		}
		
		if(source.getId() == userID) {
			updateNumFollowers(source);
		}
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.StatusListener#onTrackLimitationNotice(int) */
	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.UserStreamListener#onUnblock(twitter4j.User,
	 * twitter4j.User) */
	@Override
	public void onUnblock(User source, User unblockedUser)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUnfavorite(twitter4j.User, twitter4j.User,
	 * twitter4j.Status) */
	@Override
	public
		void
		onUnfavorite(User source, User target, Status unfavoritedStatus)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUnfollow(twitter4j.User, twitter4j.User) */
	@Override
	public void onUnfollow(User source, User unfollowedUser)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListCreation(twitter4j.User,
	 * twitter4j.UserList) */
	@Override
	public void onUserListCreation(User listOwner, UserList list)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListDeletion(twitter4j.User,
	 * twitter4j.UserList) */
	@Override
	public void onUserListDeletion(User listOwner, UserList list)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListMemberAddition(twitter4j.User,
	 * twitter4j.User, twitter4j.UserList) */
	@Override
	public void onUserListMemberAddition(
		User addedMember,
		User listOwner,
		UserList list)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListMemberDeletion(twitter4j.User,
	 * twitter4j.User, twitter4j.UserList) */
	@Override
	public void onUserListMemberDeletion(
		User deletedMember,
		User listOwner,
		UserList list)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListSubscription(twitter4j.User,
	 * twitter4j.User, twitter4j.UserList) */
	@Override
	public void onUserListSubscription(
		User subscriber,
		User listOwner,
		UserList list)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListUnsubscription(twitter4j.User,
	 * twitter4j.User, twitter4j.UserList) */
	@Override
	public void onUserListUnsubscription(
		User subscriber,
		User listOwner,
		UserList list)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListUpdate(twitter4j.User,
	 * twitter4j.UserList) */
	@Override
	public void onUserListUpdate(User listOwner, UserList list)
	{
		// Nothing to do here
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserProfileUpdate(twitter4j.User) */
	@Override
	public void onUserProfileUpdate(User updatedUser)
	{
		// Nothing to do here
		
	}
	
	
	public void setUser(Long userID) {
		this.userID = userID;
	}
	
	
	private DBFollower buildFollowerFromUser(User user)
	{
		return new DBFollower(
			user.getId(),
			user.getName(),
			user.getScreenName(),
			user.getDescription(),
			user.getFollowersCount(),
			user.getFriendsCount(),
			user.getLocation(),
			user.getFavouritesCount(),
			user.getLang(),
			user.isVerified(),
			new Timestamp(user.getCreatedAt().getTime()),
			user.getProfileImageURL());
	}
	
	
	/**
	 * @param follower
	 */
	private void flushFollowerToDatabase(Long follower)
	{
		if (followersDB.isExists(follower))
		{
			followersDB.update(updateFollowersBarrier.get(follower));
		} else
		{
			followersDB.insert(updateFollowersBarrier.get(follower));
		}
		
	}
	
	
	/**
	 * 
	 */
	private void flushUpdates()
	{
		for (Long follower : updateFollowersBarrier.keySet())
		{
			flushFollowerToDatabase(follower);
		}
		lastUpdated = new Timestamp(new Date().getTime());
		
		updateFollowersBarrier = new HashMap<Long, DBFollower>();
		
		
	}
	
	
	/**
	 * @param timestamp
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private boolean sameDaySinceUpdate(Timestamp timestamp)
	{
		return lastUpdated != null
			&& timestamp.getDay() == lastUpdated.getDay();
	}
	
	
	/**
	 * @param followingUser
	 */
	private void updateFollowers(User followingUser)
	{
		DBFollower follower = buildFollowerFromUser(followingUser);
		updateFollowersBarrier.put(followingUser.getId(), follower);
		
		Timestamp now = new Timestamp(new Date().getTime());
		if (!sameDaySinceUpdate(now))
		{
			flushUpdates();
		}
	}
	
	
	/**
	 * @param myUser
	 */
	private void updateNumFollowers(User myUser)
	{
		Timestamp now = new Timestamp(new Date().getTime());
		if (!sameDaySinceUpdate(now))
		{
			flushUpdates();
		}
	}
	
	
	private HashMap<Long, DBFollower> updateFollowersBarrier;
	
	private Timestamp lastUpdated;
	
	private IDatabaseFollowers followersDB;
	
	private Long userID;
	
}
