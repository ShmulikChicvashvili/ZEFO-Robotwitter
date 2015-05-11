/**
 *
 */

package com.robotwitter.classification;


import java.sql.Timestamp;
import java.util.Date;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserMentionEntity;
import twitter4j.UserStreamListener;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseResponses;
import com.robotwitter.database.primitives.DBFollower;
import com.robotwitter.database.primitives.DBResponse;




/**
 * @author Itay
 *
 */
public class TweetClassifierListener implements UserStreamListener
{

	@Inject
	public TweetClassifierListener(
		IDatabaseResponses db,
		IDatabaseFollowers followersDB,
		ITweetClassifier classifier)
	{
		this.classifier = classifier;
		this.db = db;
		this.followersDB = followersDB;
		userID = null;
	}

	/* (non-Javadoc) @see twitter4j.UserStreamListener#onBlock(twitter4j.User,
	 * twitter4j.User) */
	@Override
	public void onBlock(User source, User blockedUser)
	{}


	/* (non-Javadoc) @see twitter4j.UserStreamListener#onDeletionNotice(long,
	 * long) */
	@Override
	public void onDeletionNotice(long directMessageId, long userId)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.StatusListener#onDeletionNotice(twitter4j.StatusDeletionNotice) */
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onDirectMessage(twitter4j.DirectMessage) */
	@Override
	public void onDirectMessage(DirectMessage directMessage)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.StreamListener#onException(java.lang.Exception) */
	@Override
	public void onException(Exception ex)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onFavorite(twitter4j.User, twitter4j.User,
	 * twitter4j.Status) */
	@Override
	public void onFavorite(User source, User target, Status favoritedStatus)
	{}


	/* (non-Javadoc) @see twitter4j.UserStreamListener#onFollow(twitter4j.User,
	 * twitter4j.User) */
	@Override
	public void onFollow(User source, User followedUser)
	{}


	/* (non-Javadoc) @see twitter4j.UserStreamListener#onFriendList(long[]) */
	@Override
	public void onFriendList(long[] friendIds)
	{}


	/* (non-Javadoc) @see twitter4j.StatusListener#onScrubGeo(long, long) */
	@Override
	public void onScrubGeo(long userId, long upToStatusId)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.StatusListener#onStallWarning(twitter4j.StallWarning) */
	@Override
	public void onStallWarning(StallWarning warning)
	{}


	/* (non-Javadoc) @see twitter4j.StatusListener#onStatus(twitter4j.Status) */
	@Override
	public void onStatus(Status status)
	{
		if (isLegitimateResponse(status))
		{
			System.out.println("trying to insert a response to the DB");
			db.insert(buildDBResponseFromStatus(status));
			followersDB.update(buildFollowerFromUser(status.getUser()));
			// FIXME Perhaps we should also alert something if this is a bad one
		}

	}

	/* (non-Javadoc) @see twitter4j.StatusListener#onTrackLimitationNotice(int) */
	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses)
	{}
	
	/* (non-Javadoc) @see twitter4j.UserStreamListener#onUnblock(twitter4j.User,
	 * twitter4j.User) */
	@Override
	public void onUnblock(User source, User unblockedUser)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUnfavorite(twitter4j.User, twitter4j.User,
	 * twitter4j.Status) */
	@Override
	public
	void
	onUnfavorite(User source, User target, Status unfavoritedStatus)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUnfollow(twitter4j.User, twitter4j.User) */
	@Override
	public void onUnfollow(User source, User unfollowedUser)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListCreation(twitter4j.User,
	 * twitter4j.UserList) */
	@Override
	public void onUserListCreation(User listOwner, UserList list)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListDeletion(twitter4j.User,
	 * twitter4j.UserList) */
	@Override
	public void onUserListDeletion(User listOwner, UserList list)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListMemberAddition(twitter4j.User,
	 * twitter4j.User, twitter4j.UserList) */
	@Override
	public void onUserListMemberAddition(
		User addedMember,
		User listOwner,
		UserList list)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListMemberDeletion(twitter4j.User,
	 * twitter4j.User, twitter4j.UserList) */
	@Override
	public void onUserListMemberDeletion(
		User deletedMember,
		User listOwner,
		UserList list)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListSubscription(twitter4j.User,
	 * twitter4j.User, twitter4j.UserList) */
	@Override
	public void onUserListSubscription(
		User subscriber,
		User listOwner,
		UserList list)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListUnsubscription(twitter4j.User,
	 * twitter4j.User, twitter4j.UserList) */
	@Override
	public void onUserListUnsubscription(
		User subscriber,
		User listOwner,
		UserList list)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserListUpdate(twitter4j.User,
	 * twitter4j.UserList) */
	@Override
	public void onUserListUpdate(User listOwner, UserList list)
	{}


	/* (non-Javadoc) @see
	 * twitter4j.UserStreamListener#onUserProfileUpdate(twitter4j.User) */
	@Override
	public void onUserProfileUpdate(User updatedUser)
	{}


	public void setUser(Long userID)
	{
		this.userID = userID;
	}


	/**
	 * @param status
	 * @return
	 */
	private DBResponse buildDBResponseFromStatus(Status status)
	{
		String classification = classifier.classify(status.getText());
		return new DBResponse(
			userID,
			status.getUser().getId(),
			status.getId(),
			new Timestamp(new Date().getTime()),
			status.getText(),
			classification,
			classification.equals("pos"));
	}


	private DBFollower buildFollowerFromUser(User user)
	{
		String desc = user.getDescription();
		if (user.getDescription() == null)
		{
			desc = "";
		}
		String location = user.getLocation();
		if (user.getLocation() == null)
		{
			location = "";
		}
		return new DBFollower(
			user.getId(),
			user.getName(),
			user.getScreenName(),
			desc,
			user.getFollowersCount(),
			user.getFriendsCount(),
			location,
			user.getFavouritesCount(),
			user.getLang(),
			user.isVerified(),
			new Timestamp(user.getCreatedAt().getTime()),
			user.getBiggerProfileImageURL());
	}


	/**
	 * @param status
	 * @return
	 */
	private boolean isLegitimateResponse(Status status)
	{
		boolean isTagged = false;
		for (UserMentionEntity taggedUser: status.getUserMentionEntities()) {
			if(taggedUser.getId() == userID) {
				isTagged = true;
			}
		}
		return isTagged && !userID.equals(status.getUser().getId());
	}


	private IDatabaseFollowers followersDB;



	private ITweetClassifier classifier;

	private IDatabaseResponses db;

	private Long userID;

}
