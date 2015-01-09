/**
 * 
 */
package com.robotwitter.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySQLConEstablisher;
import com.robotwitter.database.MySqlDatabaseHeavyHitters;
import com.robotwitter.database.interfaces.IDatabaseHeavyHitters;
import com.robotwitter.database.interfaces.returnValues.SqlError;

/**
 * @author Itay, Shmulik
 *
 */
public class HeavyHittersDatabaseTest
{
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		final Injector injector =
			Guice.createInjector(new DatabaseTestModule());

		db = injector.getInstance(MySqlDatabaseHeavyHitters.class);
	}


	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		final Injector injector =
			Guice.createInjector(new DatabaseTestModule());
		
		try (
			Connection con =
				injector.getInstance(MySQLConEstablisher.class).getConnection();
			Statement statement = con.createStatement())
		{
			final String dropSchema = "DROP DATABASE `test`";
			statement.executeUpdate(dropSchema);
		} catch (final SQLException e)
		{
			System.out.println(e.getErrorCode());
		}
	}
	
	@Test
	public void testGet() {
		assertEquals(null, db.get(null));
		
		Long userNotExist = new Long(1);
		assertArrayEquals(new ArrayList<Long>().toArray(), db.get(userNotExist).toArray());
		
		Long followedUserID = new Long(2);
		ArrayList<Long> goodInputFollowersIDs = new ArrayList<Long>();
		for(int i = 0; i < 10; i++) {
			goodInputFollowersIDs.add(new Long(100-i));
		}
		assertEquals(SqlError.SUCCESS, db.insert(followedUserID, goodInputFollowersIDs));
		assertArrayEquals(goodInputFollowersIDs.toArray(), db.get(followedUserID).toArray());
		
	}
	
	@SuppressWarnings("boxing")
	@Test
	public void testInsert()
	{
		assertEquals(SqlError.INVALID_PARAMS, db.insert(null, null));
		assertEquals(SqlError.INVALID_PARAMS, db.insert((long) 1, null));
		
		ArrayList<Long> badInput = new ArrayList<>();
		badInput.add((long) 1);
		badInput.add(null);
		badInput.add((long) 2);
		assertEquals(SqlError.INVALID_PARAMS, db.insert((long) 1, badInput));
		
		Long followedUserID = new Long(1);
		ArrayList<Long> goodInputFollowersIDs = new ArrayList<Long>();
		for(int i = 0; i < 10; i++) {
			goodInputFollowersIDs.add(new Long(i+100));
		}
		
		assertEquals(SqlError.SUCCESS, db.insert(followedUserID, goodInputFollowersIDs));
		assertArrayEquals(goodInputFollowersIDs.toArray(), db.get(followedUserID).toArray());
		
		ArrayList<Long> newInputForUser = new ArrayList<Long>();
		for(int i = 0; i < 5; i++) {
			newInputForUser.add(new Long(i+155));
		}
		
		assertEquals(SqlError.SUCCESS, db.insert(followedUserID, newInputForUser));
		assertArrayEquals(newInputForUser.toArray(), db.get(followedUserID).toArray());
	}
	
	private IDatabaseHeavyHitters db;
	
}
