/**
 * 
 */

package com.robotwitter.twitter;


import java.sql.Timestamp;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.primitives.DBFollower;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;




/**
 * @author Itay
 *
 */
public class FollowerStoreListener implements UserStreamListener
{
	public FollowerStoreListener(
		IDatabaseFollowers followersDB,
		IDatabaseNumFollowers numFollowersDB,
		Long userID)
	{
		this.followersDB = followersDB;
		this.numFollowersDB = numFollowersDB;
		this.userID = userID;
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
			updateFollowersDatabase(recipient);
		}
	}


	/* (non-Javadoc) @see
	 * twitter4j.StreamListener#onException(java.lang.Exception) */
	@Override
	public void onException(Exception ex)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onFavorite(twitter4j.User, twitter4j.User,
	 * twitter4j.Status) */
	@Override
	public void onFavorite(User source, User target, Status favoritedStatus)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.UserStreamListener#onFollow(twitter4j.User,
	 * twitter4j.User) */
	@Override
	public void onFollow(User source, User followedUser)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.UserStreamListener#onFriendList(long[]) */
	@Override
	public void onFriendList(long[] friendIds)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.StatusListener#onScrubGeo(long, long) */
	@Override
	public void onScrubGeo(long userId, long upToStatusId)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.StatusListener#onStallWarning(twitter4j.StallWarning) */
	@Override
	public void onStallWarning(StallWarning warning)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.StatusListener#onStatus(twitter4j.Status) */
	@Override
	public void onStatus(Status status)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.StatusListener#onTrackLimitationNotice(int) */
	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.UserStreamListener#onUnblock(twitter4j.User,
	 * twitter4j.User) */
	@Override
	public void onUnblock(User source, User unblockedUser)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUnfavorite(twitter4j.User, twitter4j.User,
	 * twitter4j.Status) */
	@Override
	public
		void
		onUnfavorite(User source, User target, Status unfavoritedStatus)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUnfollow(twitter4j.User, twitter4j.User) */
	@Override
	public void onUnfollow(User source, User unfollowedUser)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListCreation(twitter4j.User,
	 * twitter4j.UserList) */
	@Override
	public void onUserListCreation(User listOwner, UserList list)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListDeletion(twitter4j.User,
	 * twitter4j.UserList) */
	@Override
	public void onUserListDeletion(User listOwner, UserList list)
	{
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListUpdate(twitter4j.User,
	 * twitter4j.UserList) */
	@Override
	public void onUserListUpdate(User listOwner, UserList list)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserProfileUpdate(twitter4j.User) */
	@Override
	public void onUserProfileUpdate(User updatedUser)
	{
		// TODO Auto-generated method stub
		
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
	 * @param followingUser
	 */
	private void updateFollowersDatabase(User followingUser)
	{
		DBFollower follower = buildFollowerFromUser(followingUser);
		if (followersDB.isExists(follower.getFollowerId())) {
			followersDB.update(follower);
		} else {
			followersDB.insert(follower);
		}
	}
	
	
	
	private IDatabaseFollowers followersDB;
	
	private IDatabaseNumFollowers numFollowersDB;
	
	private Long userID;
	
}
