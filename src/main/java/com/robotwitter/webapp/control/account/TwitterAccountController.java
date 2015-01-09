
package com.robotwitter.webapp.control.account;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.primitives.DBFollowersNumber;




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
	 */
	public TwitterAccountController(
		long id,
		String name,
		String screenname,
		String image,
		IDatabaseNumFollowers numFollowersDB)
	{
		this.id = id;
		this.name = name;
		this.screenname = screenname;
		this.image = image;
		this.numFollowersDB = numFollowersDB;
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
		return 9000;
	}
	
	
	@Override
	public final int getLastKnownAmountOfGainedFollowers()
	{
		return 42;
	}
	
	
	@Override
	public final int getLastKnownAmountOfLostFollowers()
	{
		return 69;
	}
	
	
	@Override
	public final List<TwitterFollower> getMostInfluentialFollowers()
	{
		List<TwitterFollower> list = new LinkedList<>();
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
	
	
	
	/** The Twitter accounts' ID. */
	public long id;
	
	/** The Twitter accounts' name. */
	public String name;
	
	/** The Twitter accounts' screenname. */
	public String screenname;
	
	/** The Twitter accounts' profile image. */
	public String image;
	
	/** The Twtitter's account number of followers Database. */
	public IDatabaseNumFollowers numFollowersDB;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
