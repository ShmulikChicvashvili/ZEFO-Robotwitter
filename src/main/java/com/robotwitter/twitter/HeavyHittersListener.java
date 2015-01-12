/**
 *
 */

package com.robotwitter.twitter;


import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.robotwitter.database.interfaces.IDatabaseHeavyHitters;
import com.robotwitter.statistics.IHeavyHitters;




/**
 * @author Shmulik, Itay.
 *
 */
@SuppressWarnings("boxing")
public class HeavyHittersListener implements UserStreamListener
{
	
	/**
	 * @param heavyHitters
	 *            the heavy hitters algorithm handler
	 */
	@Inject
	public HeavyHittersListener(
		IHeavyHitters heavyHitters,
		IDatabaseHeavyHitters db)
	{
		heavyHittersHandler = heavyHitters;
		this.db = db;
		userID = null;
		lastUpdated = null;
	}
	
	
	/**
	 * @return The current heavy hitters
	 */
	public ArrayList<Long> getHeavyHitters()
	{
		return heavyHittersHandler.getCurrentHeavyHitters();
	}
	
	
	@Override
	public void onBlock(User source, User blockedUser)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onDeletionNotice(long directMessageId, long userId)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onDirectMessage(DirectMessage directMessage)
	{
		final User recipient = directMessage.getRecipient();
		final User sender = directMessage.getSender();
		if (recipient.getId() != userID && sender.getId() == userID)
		{
			heavyHittersHandler.onDirectMessage(recipient.getId());
			updateHeavyHitters();
		}
	}
	
	
	@Override
	public void onException(Exception ex)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onFavorite(User source, User target, Status favoritedStatus)
	{
		if (target.getId() == userID && source.getId() != userID)
		{
			heavyHittersHandler.onFavorite(source.getId());
			updateHeavyHitters();
		}
	}
	
	
	@Override
	public void onFollow(User source, User followedUser)
	{
		if (followedUser.getId() == userID && source.getId() != userID)
		{
			heavyHittersHandler.onFollow(source.getId());
			updateHeavyHitters();
		}
	}
	
	
	@Override
	public void onFriendList(long[] friendIds)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onScrubGeo(long userId, long upToStatusId)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onStallWarning(StallWarning warning)
	{
		// For our purposes we don't need this.
	}
	
	
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
					heavyHittersHandler.onMentioned(source.getId());
					updateHeavyHitters();
					break;
				}
			}
			
			if (status.isRetweet()
				&& status.getRetweetedStatus().getUser().getId() == userID)
			{
				heavyHittersHandler.onRetweetedStatus(source.getId());
				updateHeavyHitters();
			}
		}
		
	}
	
	
	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUnblock(User source, User unblockedUser)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public
		void
		onUnfavorite(User source, User target, Status unfavoritedStatus)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUnfollow(User source, User followedUser)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUserListCreation(User listOwner, UserList list)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUserListDeletion(User listOwner, UserList list)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUserListMemberAddition(
		User addedMember,
		User listOwner,
		UserList list)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUserListMemberDeletion(
		User deletedMember,
		User listOwner,
		UserList list)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUserListSubscription(
		User subscriber,
		User listOwner,
		UserList list)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUserListUnsubscription(
		User subscriber,
		User listOwner,
		UserList list)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUserListUpdate(User listOwner, UserList list)
	{
		// For our purposes we don't need this.
	}
	
	
	@Override
	public void onUserProfileUpdate(User updatedUser)
	{
		// For our purposes we don't need this.
	}
	
	
	public void setUser(Long userID)
	{
		this.userID = userID;
	}
	
	
	private boolean sameDaySinceUpdate(Timestamp timestamp)
	{
		return lastUpdated != null
			&& timestamp.getDay() == lastUpdated.getDay()
			&& false; // FIXME: holy mother of fixmes!
	}
	
	
	private void updateHeavyHitters()
	{
		if (!sameDaySinceUpdate(new Timestamp(new Date().getTime())))
		{
			db.insert(userID, getHeavyHitters());
		}
	}
	
	
	
	private final Timestamp lastUpdated;
	
	private final IDatabaseHeavyHitters db;
	
	/**
	 * The user we track
	 */
	Long userID;
	
	/**
	 * Heavy hitters handler
	 */
	IHeavyHitters heavyHittersHandler;
}
