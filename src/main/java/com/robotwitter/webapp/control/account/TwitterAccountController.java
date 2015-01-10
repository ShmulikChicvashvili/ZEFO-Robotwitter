package com.robotwitter.webapp.control.account;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.primitives.DBFollower;
import com.robotwitter.database.primitives.DBFollowersNumber;
import com.robotwitter.database.interfaces.IDatabaseHeavyHitters;
import com.robotwitter.database.interfaces.IDatabaseFollowers;

/**
 * A simple implementation of the Twitter account controller interface.
 *
 * @author Doron Hogery
 */
public class TwitterAccountController implements ITwitterAccountController {
	private List<DBFollower> allfollowers;
	private static final int NOFOLLOWERSINFO = -1;

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
	public TwitterAccountController(long id, String name, String screenname,
			String image, IDatabaseNumFollowers numFollowersDB,
			IDatabaseHeavyHitters heavyhitterDB, IDatabaseFollowers followersDB) {
		this.id = id;
		this.name = name;
		this.screenname = screenname;
		this.image = image;
		this.numFollowersDB = numFollowersDB;
		this.heavyhitterDB = heavyhitterDB;
		this.followersDB = followersDB;
	}

	@Override
	public final Map<Date, Integer> getAmountOfFollowers(Date from, Date to) {

		final Map<Date, Integer> followersBetween = new HashMap<>();
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		if (dbfollowers == null) {
			return followersBetween;
		}
		for (final DBFollowersNumber follower : dbfollowers) {
			final Date d = new Date(follower.getDate().getTime());
			if (from == null && to == null) {
				followersBetween.put(d,
						Integer.valueOf(follower.getNumFollowers()));
			} else if (from == null) {
				if (follower.getDate().toInstant().compareTo(to.toInstant()) <= 0) {
					followersBetween.put(d,
							Integer.valueOf(follower.getNumFollowers()));
				}
			} else if (to == null) {
				if (follower.getDate().toInstant().compareTo(from.toInstant()) >= 0) {
					followersBetween.put(d,
							Integer.valueOf(follower.getNumFollowers()));
				}
			} else {
				if (follower.getDate().toInstant().compareTo(from.toInstant()) >= 0
						&& follower.getDate().toInstant()
								.compareTo(to.toInstant()) <= 0) {
					followersBetween.put(d,
							Integer.valueOf(follower.getNumFollowers()));
				}
			}
		}
		return followersBetween;

	}

	/*
	 * (non-Javadoc) @see
	 * com.robotwitter.webapp.control.account.ITwitterAccountController
	 * #getFollowersAmountByDisplayedLanguage()
	 */
	@Override
	public Map<String, Integer> getFollowersAmountByDisplayedLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc) @see
	 * com.robotwitter.webapp.control.account.ITwitterAccountController
	 * #getFollowersAmountByTheirFollowersAmount(java.util.List)
	 */
	@Override
	public List<Integer> getFollowersAmountByTheirFollowersAmount(
			List<Integer> separators) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc) @see
	 * com.robotwitter.webapp.control.account.ITwitterAccountController
	 * #getFollowersAmountByTheirFollowingAmount(java.util.List)
	 */
	@Override
	public List<Integer> getFollowersAmountByTheirFollowingAmount(
			List<Integer> separators) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final long getID() {
		return id;
	}

	@Override
	public final String getImage() {
		return image;
	}

	// DORON
	@Override
	public final int getLastKnownAmountOfFollowers() {
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		if (dbfollowers.isEmpty()) {
			return NOFOLLOWERSINFO;
		}
		return lastUpdateInDatabase().getNumFollowers();
	}

	// DORON
	@Override
	public final int getLastKnownAmountOfGainedFollowers() {
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		if (dbfollowers.isEmpty()) {
			return NOFOLLOWERSINFO;
		}
		return lastUpdateInDatabase().getNumJoined();
	}

	// DORON
	@Override
	public final int getLastKnownAmountOfLostFollowers() {
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		if (dbfollowers.isEmpty()) {
			return NOFOLLOWERSINFO;
		}
		return lastUpdateInDatabase().getNumLeft();
	}

	// DORON
	@Override
	public final List<TwitterFollower> getMostInfluentialFollowers() {
		List<TwitterFollower> list = new LinkedList<>();
		ArrayList<Long> list2 = heavyhitterDB.get(this.id);
		for (final Long iterator : list2) {
			DBFollower follower = followersDB.get(iterator);
			if ((follower) != null) {
				TwitterFollower tempfollower = new TwitterFollower(iterator,
						follower.getName(), follower.getScreenName(),
						follower.getDescription(), follower.getFollowers(),
						follower.getFollowing(), follower.getLocation(),
						follower.getFavorites(), follower.getLanguage(),
						follower.getIsCelebrity(), follower.getJoined(),
						follower.getPicture());
				list.add(tempfollower);
			}
		}

		return list;
	}

	private final DBFollowersNumber lastUpdateInDatabase() {
		final List<DBFollowersNumber> dbfollowers = numFollowersDB.get(id);
		if (dbfollowers.isEmpty()) {// Should be checked before calling but
									// returns null
			return null;
		}
		DBFollowersNumber latest = null;
		boolean firstTime = true;

		for (final DBFollowersNumber follower : dbfollowers) {
			if (firstTime) {
				firstTime = false;
				latest = follower;
			} else {
				if (follower.getDate().after(latest.getDate())) {
					latest = follower;
				}
			}
		}
		return latest;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final String getScreenname() {
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

	/** The heavyhitters database */
	private IDatabaseHeavyHitters heavyhitterDB;

	/** The followers DB */
	private IDatabaseFollowers followersDB;

}
