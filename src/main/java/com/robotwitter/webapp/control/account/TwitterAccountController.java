
package com.robotwitter.webapp.control.account;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseHeavyHitters;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.primitives.DBFollower;
import com.robotwitter.database.primitives.DBFollowersNumber;
import com.robotwitter.posting.TweetPostService;
import com.robotwitter.twitter.TwitterAccount;




/**
 * A simple implementation of the Twitter account controller interface.
 *
 * @author Doron Hogery
 */
public class TwitterAccountController implements ITwitterAccountController
{
	/**
	 * Instantiates a new twitter account controller.
	 *
	 * @param id
	 *            the Twitter account's ID
	 * @param name
	 *            the Twitter account's name
	 * @param screenname
	 *            the Twitter account's screenname
	 * @param image
	 *            the Twitter account's image
	 * @param numFollowersDB
	 *            The number of followers database
	 * @param heavyhittersDB
	 *            The heavy hitters database
	 * @param followersDB
	 *            The followers database
	 */
	public TwitterAccountController(
		long id,
		String name,
		String screenname,
		String image,
		IDatabaseNumFollowers numFollowersDB,
		IDatabaseHeavyHitters heavyhitterDB,
		IDatabaseFollowers followersDB)
	{
		this.id = id;
		this.name = name;
		this.screenname = screenname;
		this.image = image;
		this.numFollowersDB = numFollowersDB;
		this.heavyhitterDB = heavyhitterDB;
		this.followersDB = followersDB;
		allfollowers = followersDB.getFollowers(this.id);
		
	}
	
	
	@Override
	public final Map<Date, Integer> getAmountOfFollowers(Date from, Date to)
	{
		final Map<Date, Integer> followersBetween = new HashMap<>();
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		if (dbfollowers == null) { return followersBetween; }
		for (final DBFollowersNumber follower : dbfollowers)
		{
			final Date d = new Date(follower.getDate().getTime());
			if (from == null && to == null)
			{
				followersBetween.put(
					d,
					Integer.valueOf(follower.getNumFollowers()));
			} else if (from == null)
			{
				if (follower.getDate().toInstant().compareTo(to.toInstant()) <= 0)
				{
					followersBetween.put(
						d,
						Integer.valueOf(follower.getNumFollowers()));
				}
			} else if (to == null)
			{
				if (follower.getDate().toInstant().compareTo(from.toInstant()) >= 0)
				{
					followersBetween.put(
						d,
						Integer.valueOf(follower.getNumFollowers()));
				}
			} else
			{
				if (follower.getDate().toInstant().compareTo(from.toInstant()) >= 0
					&& follower.getDate().toInstant().compareTo(to.toInstant()) <= 0)
				{
					followersBetween.put(
						d,
						Integer.valueOf(follower.getNumFollowers()));
				}
			}
		}
		return followersBetween;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.account.ITwitterAccountController
	 * #getCurrentMaximumTweetLength() */
	@Override
	public int getCurrentMaximumTweetLength()
	{
		return 15 * 140;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.account.ITwitterAccountController
	 * #getFollowersAmountByDisplayedLanguage() */
	@Override
	public Map<String, Integer> getFollowersAmountByDisplayedLanguage()
	{
		final Map<String, Integer> m = new HashMap<String, Integer>();
		if (allfollowers != null)
		{
			for (final DBFollower follower : allfollowers)
			{
				final String lang = follower.getLanguage();
				final Integer langCounter = m.get(lang);
				m.put(lang, langCounter == null ? 1 : langCounter + 1);
			}
		}
		return m;
	}
	
	
	@Override
	public void getFollowersAmountByTheirFollowersAmount(
		int subdivisions,
		List<Integer> amounts,
		List<Integer> separators)
	{
		amounts.clear();
		separators.clear();
		separatorsByPath(subdivisions, separators, FINDFOLLOWERS);
		followersAmountByPath(separators, amounts, FINDFOLLOWERS);
	}
	
	
	@Override
	public void getFollowersAmountByTheirFollowingAmount(
		int subdivisions,
		List<Integer> amounts,
		List<Integer> separators)
	{
		amounts.clear();
		separators.clear();
		separatorsByPath(subdivisions, separators, FINDFOLLOWING);
		followersAmountByPath(separators, amounts, FINDFOLLOWING);
		
	}
	
	
	@Override
	public final long getID()
	{
		return id;
	}
	
	
	@Override
	public final String getImage()
	{
		return image;
	}
	
	
	@Override
	public final int getLastKnownAmountOfFollowers()
	{
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		if (dbfollowers == null || dbfollowers.isEmpty()) { return NOFOLLOWERSINFO; }
		return lastUpdateInDatabase().getNumFollowers();
	}
	
	
	@Override
	public final int getLastKnownAmountOfGainedFollowers()
	{
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		if (dbfollowers == null || dbfollowers.isEmpty()) { return NOFOLLOWERSINFO; }
		return lastUpdateInDatabase().getNumJoined();
	}
	
	
	@Override
	public final int getLastKnownAmountOfLostFollowers()
	{
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		
		if (dbfollowers == null || dbfollowers.isEmpty()) { return NOFOLLOWERSINFO; }
		return lastUpdateInDatabase().getNumLeft();
	}
	
	
	@Override
	public final List<TwitterFollower> getMostInfluentialFollowers()
	{
		final List<TwitterFollower> list = new LinkedList<>();
		final ArrayList<Long> list2 = heavyhitterDB.get(id);
		if (list2 != null)
		{
			for (final Long iterator : list2)
			{
				final DBFollower follower = followersDB.get(iterator);
				if (follower != null)
				{
					final TwitterFollower tempfollower =
						new TwitterFollower(
							iterator,
							follower.getName(),
							follower.getScreenName(),
							follower.getDescription(),
							follower.getFollowers(),
							follower.getFollowing(),
							follower.getLocation(),
							follower.getFavorites(),
							follower.getLanguage(),
							follower.getIsCelebrity(),
							follower.getJoined(),
							follower.getPicture());
					list.add(tempfollower);
				}
			}
		}
		return list;
	}
	
	
	@Override
	public final String getName()
	{
		return name;
	}
	
	
	@Override
	public final String getScreenname()
	{
		return screenname;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.account.ITwitterAccountController
	 * #postTweetsAsSingleTweet(java.util.List) */
	@Override
	public Status postTweetsAsSingleTweet(List<String> tweets)
	{
		switch (tweetPostService.post(tweets))
		{
			case SUCCESS:
				return Status.SUCCESS;

			default:
				return Status.FAILURE;
		}
	}
	
	
	/* This function will create a list that will say how many followers are
	 * there for each separator */
	public void setTwitterAccount(TwitterAccount twitterAccount)
	{
		this.twitterAccount = twitterAccount;
		tweetPostService = new TweetPostService(twitterAccount);
	}
	
	
	private void followersAmountByPath(
		List<Integer> separators,
		List<Integer> followersAmount,
		int path)
	{
		boolean start = true;
		if (separators.size() == 0)
		{
			if (allfollowers != null && allfollowers.size() != 0)
			{
				if (path == FINDFOLLOWERS)
				{
					separators.add(allfollowers.get(0).getFollowers());
				} else
				{
					separators.add(allfollowers.get(0).getFollowing());
				}
				followersAmount.add(allfollowers.size());
			}
			return;
		}
		Integer prev = separators.get(0);
		for (final Integer sep : separators)
		{
			if (start)
			{
				start = false;
			} else
			{
				if (prev >= sep) { throw new RuntimeException(
					"The list isn't strictly monotonically increasing"); }
			}
		}
		prev = Integer.MIN_VALUE;
		Integer count = 0;
		Integer temp;
		separators.add(Integer.MAX_VALUE);
		for (final Integer next : separators)
		{
			if (allfollowers != null)
			{
				for (final DBFollower follower : allfollowers)
				{
					if (path == FINDFOLLOWERS)
					{
						temp = follower.getFollowers();
					} else
					{
						temp = follower.getFollowing();
					}
					if (prev <= temp && temp < next)
					{
						count++;
					}
				}
				prev = next;
				followersAmount.add(count);
				count = 0;
			} else
			{
				break;
			}
		}
		separators.remove(separators.size() - 1);
	}
	
	
	/* This function will create the separators and will insert them into
	 * separators array */
	private final DBFollowersNumber lastUpdateInDatabase()
	{
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		if (dbfollowers.isEmpty()) { return null; }
		DBFollowersNumber latest = null;
		boolean firstTime = true;
		
		for (final DBFollowersNumber follower : dbfollowers)
		{
			if (firstTime)
			{
				firstTime = false;
				latest = follower;
			} else
			{
				if (follower.getDate().after(latest.getDate()))
				{
					latest = follower;
				}
			}
		}
		return latest;
	}
	
	
	private void separatorsByPath(
		int subdivision,
		List<Integer> separators,
		int path)
	{
		Integer minCount, maxCount;
		maxCount = Integer.MIN_VALUE;
		minCount = Integer.MAX_VALUE;
		if (allfollowers != null && allfollowers.size() != 0)
		{
			for (final DBFollower follower : allfollowers)
			{
				Integer temp;
				if (path == FINDFOLLOWERS)
				{
					temp = follower.getFollowers();
				} else
				{
					temp = follower.getFollowing();
				}
				if (maxCount < temp)
				{
					maxCount = temp;
				}
				if (minCount > temp)
				{
					minCount = temp;
				}
			}
		} else
		{
			return;
		}
		if (maxCount.equals(minCount)) { return; }
		final double divide = (double) (maxCount - minCount) / subdivision;
		
		for (int i = 1; i < subdivision; i++)
		{
			final int addNew = (int) (minCount + i * divide);
			if (separators.isEmpty())
			{
				separators.add(addNew);
			} else if (addNew != separators.get(separators.size() - 1))
			{
				separators.add(addNew);
			}
		}
		assert separators.size() < subdivision;
	}
	
	
	
	private TwitterAccount twitterAccount;
	
	private final List<DBFollower> allfollowers;
	
	private static final int NOFOLLOWERSINFO = -1;
	
	private static final int FINDFOLLOWERS = 0;
	
	private static final int FINDFOLLOWING = 1;
	
	/** The Twitter accounts' ID. */
	public long id;
	
	/** The Twitter accounts' name. */
	public String name;
	
	/** The Twitter accounts' screen name. */
	public String screenname;
	
	/** The Twitter accounts' profile image. */
	public String image;
	
	/** The Twtitter's account number of followers Database. */
	public IDatabaseNumFollowers numFollowersDB;
	
	/** Serialization version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The heavy hitters database */
	private final IDatabaseHeavyHitters heavyhitterDB;
	
	/** The followers DB */
	private final IDatabaseFollowers followersDB;
	
	private TweetPostService tweetPostService;
	
}
