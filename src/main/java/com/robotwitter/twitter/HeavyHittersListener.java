/**
 *
 */

package com.robotwitter.twitter;


import java.util.ArrayList;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserMentionEntity;
import twitter4j.UserStreamListener;

import com.robotwitter.statistics.IHeavyHitters;




/**
 * @author Shmulik, Itay.
 *
 */
@SuppressWarnings("boxing")
public class HeavyHittersListener implements UserStreamListener
{

	/**
	 * @param heavyHittersListnerFactory
	 *            The factory which will create for us the handler
	 * @param userID
	 *            The user id we track
	 */
	public HeavyHittersListener(
		HeavyHittersListnerFactory heavyHittersListnerFactory,
		Long userID)
	{
		heavyHittersHandler = heavyHittersListnerFactory.getInstance();
		this.userID = userID;
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
		User recipient = directMessage.getRecipient();
		User sender = directMessage.getSender();
		if(recipient.getId() != userID && sender.getId() == userID) {			
			heavyHittersHandler.onDirectMessage(recipient.getId());
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
		}
	}


	@Override
	public void onFollow(User source, User followedUser)
	{
		if (followedUser.getId() == userID && source.getId() != userID)
		{
			heavyHittersHandler.onFollow(source.getId());
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
					break;
				}
			}

			if (status.isRetweet()
				&& status.getRetweetedStatus().getUser().getId() == userID)
			{
				heavyHittersHandler.onRetweetedStatus(source.getId());
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



	/**
	 * The user we track
	 */
	Long userID;
	
	/**
	 * Heavy hitters handler
	 */
	IHeavyHitters heavyHittersHandler;
}
