
package com.robotwitter.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySQLConEstablisher;
import com.robotwitter.database.MySqlDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBFollower;




/**
 *
 * @author Amir and Shmulik
 *
 */

public class TestDatabaseActualFollower
{

	public static final ArrayList<DBFollower> generateFollowersWithName(
		int amount,
		String name)
	{
		final ArrayList<DBFollower> list = new ArrayList<DBFollower>();
		for (int i = 0; i < amount; i++)
		{
			final DBFollower follower = new DBFollower(i + 100, name, "s" + i, //$NON-NLS-1$
				"a", //$NON-NLS-1$
				10,
				10,
				"l", //$NON-NLS-1$
				10,
				"l", //$NON-NLS-1$
				false,
				Timestamp.valueOf("2005-09-23 10:10:10.0"), //$NON-NLS-1$
				"p"); //$NON-NLS-1$
			list.add(follower);
		}
		return list;
	}


	public static final ArrayList<DBFollower> generateFollowersWithScreenName(
		int amount,
		String screenName)
	{
		final ArrayList<DBFollower> list = new ArrayList<DBFollower>();
		for (int i = 0; i < amount; i++)
		{
			final DBFollower follower = new DBFollower(i + 1000, "n" + i, //$NON-NLS-1$
				screenName,
				"a", //$NON-NLS-1$
				10,
				10,
				"l", //$NON-NLS-1$
				10,
				"l", //$NON-NLS-1$
				false,
				Timestamp.valueOf("2005-09-23 10:10:10.0"), //$NON-NLS-1$
				"p"); //$NON-NLS-1$
			list.add(follower);
		}
		return list;
	}


	/**
	 * Before.
	 */
	@Before
	public final void before()
	{
		final Injector injector =
			Guice.createInjector(new DatabaseTestModule());

		try (
			Connection con =
				injector.getInstance(MySQLConEstablisher.class).getConnection();
			Statement statement = con.createStatement())
		{
			final String dropSchema = "DROP DATABASE `test`"; //$NON-NLS-1$
			statement.executeUpdate(dropSchema);
		} catch (final SQLException e)
		{
			System.out.println(e.getErrorCode());
		}
		db = injector.getInstance(MySqlDatabaseFollowers.class);
	}


	@Test
	public final void Test()
	{
		final Timestamp joined1 = Timestamp.valueOf("2007-09-23 10:10:10.0"); //$NON-NLS-1$
		final Timestamp joined2 = Timestamp.valueOf("2008-06-11 12:10:10.0"); //$NON-NLS-1$
		final DBFollower first = new DBFollower(1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			999,
			666,
			"Everywhere", //$NON-NLS-1$
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			joined1,
			"twitter.com/random/profile"); //$NON-NLS-1$
		if (!db.isExists(123456))
		{
			db.insert(first);
		}
		assertTrue(db.isExists(1));
		assertTrue(db.getByName("random").contains(first)); //$NON-NLS-1$
		assertTrue(db.getByScreenName("randomMan").contains(first)); //$NON-NLS-1$
		assertFalse(db.isExists(9999999));
		assertFalse(db.isExists(-1));
		final DBFollower second = new DBFollower(2, "random", //$NON-NLS-1$
			"2ndary", //$NON-NLS-1$
			"not as handsome and smart as random man", //$NON-NLS-1$
			888,
			555,
			"Nowhere", //$NON-NLS-1$
			2222,
			"Hebrew", //$NON-NLS-1$
			false,
			joined2,
			"twitter.com/2ndary/profile"); //$NON-NLS-1$
		if (!db.isExists(222222))
		{
			db.insert(second);
		}
		assertTrue(db.isExists(2));
		if (!db.isExists(1, 2))
		{
			db.insert(1, 2);
		}
		assertTrue(db.isExists(1, 2));
	}


	@Test
	public final void testGetsUpdateDelete()
	{
		final DBFollower mainUser = new DBFollower(123456789, "man", //$NON-NLS-1$
			"manG", //$NON-NLS-1$
			"some man", //$NON-NLS-1$
			9999,
			9999,
			"somewhere", //$NON-NLS-1$
			9999,
			"English", //$NON-NLS-1$
			false,
			Timestamp.valueOf("2010-10-10 10:10:10.0"), //$NON-NLS-1$
			"twitter.com/man/profile"); //$NON-NLS-1$
		assertEquals(SqlError.SUCCESS, db.insert(mainUser));

		final ArrayList<DBFollower> nameFollowers =
			generateFollowersWithName(100, "Yossi"); //$NON-NLS-1$
		final ArrayList<DBFollower> screenNameFollowers =
			generateFollowersWithScreenName(100, "theYossi"); //$NON-NLS-1$
		final ArrayList<DBFollower> updatedFollowers =
			generateFollowersWithName(100, "Menahem"); //$NON-NLS-1$
		ArrayList<Long> followersById = db.getFollowersId(123456789);
		assertTrue(followersById.size() == 0);

		assertEquals(SqlError.INVALID_PARAMS, db.deleteFollower(-1));
		assertEquals(SqlError.INVALID_PARAMS, db.deleteFollow(-1, -1));
		assertEquals(SqlError.INVALID_PARAMS, db.deleteFollow(-1, 99));
		assertEquals(SqlError.INVALID_PARAMS, db.deleteFollow(99, -1));

		for (final DBFollower follower : nameFollowers)
		{
			assertEquals(SqlError.DOES_NOT_EXIST, db.update(follower));
			assertEquals(
				SqlError.DOES_NOT_EXIST,
				db.deleteFollower(follower.getFollowerId()));
		}

		for (final DBFollower follower : nameFollowers)
		{
			assertFalse(db.isExists(follower.getFollowerId()));
			assertEquals(SqlError.SUCCESS, db.insert(follower));
			assertEquals(
				SqlError.SUCCESS,
				db.insert(123456789, follower.getFollowerId()));
		}

		final ArrayList<DBFollower> followerNames = db.getByName("Yossi"); //$NON-NLS-1$
		assertTrue(followerNames.size() == 100);
		followersById = db.getFollowersId(123456789);
		assertTrue(followersById.size() == 100);

		for (final DBFollower follower : screenNameFollowers)
		{
			assertFalse(db.isExists(follower.getFollowerId()));
			assertEquals(SqlError.SUCCESS, db.insert(follower));
			assertEquals(
				SqlError.SUCCESS,
				db.insert(123456789, follower.getFollowerId()));
		}

		final ArrayList<DBFollower> followerScreenNames =
			db.getByScreenName("theYossi"); //$NON-NLS-1$
		assertTrue(followerScreenNames.size() == 100);
		followersById = db.getFollowersId(123456789);
		assertTrue(followersById.size() == 200);

		for (final DBFollower follower : updatedFollowers)
		{
			assertEquals(SqlError.SUCCESS, db.update(follower));
		}
		final ArrayList<DBFollower> previousNameFollowers =
			db.getByName("Yossi"); //$NON-NLS-1$
		assertTrue(previousNameFollowers.size() == 0);
		ArrayList<DBFollower> updatedNameFollowers = db.getByName("Menahem"); //$NON-NLS-1$
		assertTrue(updatedNameFollowers.size() == 100);

		for (final DBFollower follower : updatedFollowers)
		{
			assertEquals(
				SqlError.SUCCESS,
				db.deleteFollow(123456789, follower.getFollowerId()));
			assertFalse(db.isExists(123456789, follower.getFollowerId()));
			assertEquals(
				SqlError.SUCCESS,
				db.deleteFollower(follower.getFollowerId()));
			assertFalse(db.isExists(follower.getFollowerId()));
		}

		updatedNameFollowers = db.getByName("Menahem"); //$NON-NLS-1$
		assertTrue(updatedNameFollowers.size() == 0);
	}


	@Test
	public final void testInsertGet()
	{
		final Timestamp joined = Timestamp.valueOf("2005-09-23 10:10:10.0"); //$NON-NLS-1$
		final Timestamp joined2 = Timestamp.valueOf("2007-11-11 11:10:10.0"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(null));
		DBFollower badFollower =
			new DBFollower(
				-1,
				null,
				null,
				null,
				-1,
				-1,
				null,
				-1,
				null,
				false,
				null,
				null);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(-1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			999,
			666,
			"Everywhere", //$NON-NLS-1$
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			joined,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, null, "randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			999,
			666,
			"Everywhere", //$NON-NLS-1$
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			joined,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, "random", //$NON-NLS-1$
			null,
			"handsome and smart random man", //$NON-NLS-1$
			999,
			666,
			"Everywhere", //$NON-NLS-1$
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			joined,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			null,
			999,
			666,
			"Everywhere", //$NON-NLS-1$
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			joined,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			-1,
			666,
			"Everywhere", //$NON-NLS-1$
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			joined,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			999,
			-1,
			"Everywhere", //$NON-NLS-1$
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			joined,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			999,
			666,
			null,
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			joined,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			999,
			666,
			"Everywhere", //$NON-NLS-1$
			-1,
			"Hebrew", //$NON-NLS-1$
			false,
			joined,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			999,
			666,
			"Everywhere", //$NON-NLS-1$
			6969,
			null,
			false,
			joined,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			999,
			666,
			"Everywhere", //$NON-NLS-1$
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			null,
			"twitter.com/random/profile"); //$NON-NLS-1$
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));
		badFollower = new DBFollower(1, "random", //$NON-NLS-1$
			"randomMan", //$NON-NLS-1$
			"handsome and smart random man", //$NON-NLS-1$
			999,
			666,
			"Everywhere", //$NON-NLS-1$
			6969,
			"Hebrew", //$NON-NLS-1$
			false,
			joined,
			null);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badFollower));

		assertEquals(SqlError.INVALID_PARAMS, db.insert(-1, -1));
		assertEquals(SqlError.INVALID_PARAMS, db.insert(567, -1));
		assertEquals(SqlError.INVALID_PARAMS, db.insert(-1, 222));

		assertEquals(null, db.get(-1));
		assertEquals(null, db.get(666));
		final DBFollower okFollower = new DBFollower(666, "man", //$NON-NLS-1$
			"manG", //$NON-NLS-1$
			"some man", //$NON-NLS-1$
			12,
			23,
			"somewhere", //$NON-NLS-1$
			2,
			"English", //$NON-NLS-1$
			false,
			joined,
			"twitter.com/man/profile"); //$NON-NLS-1$
		final DBFollower secondFollower = new DBFollower(999, "2ndman", //$NON-NLS-1$
			"2manG", //$NON-NLS-1$
			"2nd man", //$NON-NLS-1$
			55,
			66,
			"2nd place", //$NON-NLS-1$
			1,
			"Chinese", //$NON-NLS-1$
			false,
			joined2,
			"twitter.com/man/profile"); //$NON-NLS-1$
		assertFalse(db.getFollowersId(666).contains(secondFollower));
		assertTrue(db.getFollowersId(666).isEmpty());

		assertEquals(SqlError.SUCCESS, db.insert(okFollower));
		assertEquals(SqlError.ALREADY_EXIST, db.insert(okFollower));
		assertEquals(SqlError.SUCCESS, db.insert(secondFollower));

		assertEquals(SqlError.SUCCESS, db.insert(666, 999));
		assertEquals(SqlError.ALREADY_EXIST, db.insert(666, 999));
	}



	/** The Database */
	IDatabaseFollowers db;
}
