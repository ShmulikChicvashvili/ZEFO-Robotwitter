/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySQLConEstablisher;
import com.robotwitter.database.MySqlDatabaseNumFollowers;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBFollowersNumber;




/**
 * @author Shmulik & Eyal
 *
 */
public class DatabaseFollowersTest
{

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
			final String dropSchema = "DROP DATABASE `test`";
			statement.executeUpdate(dropSchema);
		} catch (final SQLException e)
		{
			System.out.println(e.getErrorCode());
		}
		db = injector.getInstance(MySqlDatabaseNumFollowers.class);
		
		c = Calendar.getInstance();
	}
	
	
	@SuppressWarnings("boxing")
	@Test
	public void test()
	{
		c.set(2014, 7, 12, 20, 0, 0);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(null));
		assertEquals(null, db.get(null));
		final DBFollowersNumber dbTest =
			new DBFollowersNumber((long) 123456789, Timestamp.from(c
				.toInstant()), 0);
		assertEquals(SqlError.SUCCESS, db.insert(dbTest));
		assertEquals(SqlError.ALREADY_EXIST, db.insert(dbTest));
		
		c.set(2014, 7, 12, 20, 01, 0);
		dbTest.setDate(Timestamp.from(c.toInstant()));
		dbTest.setNumFollowers(2);
		assertEquals(SqlError.SUCCESS, db.insert(dbTest));
		
		dbTest.setTwitterId((long) 987654321);
		dbTest.setNumFollowers(0);
		assertEquals(SqlError.SUCCESS, db.insert(dbTest));
		assertEquals(SqlError.ALREADY_EXIST, db.insert(dbTest));
		
		dbTest.setDate(Timestamp.from(new Date().toInstant()));
		dbTest.setNumFollowers(3);
		assertEquals(SqlError.SUCCESS, db.insert(dbTest));
		assertEquals(SqlError.ALREADY_EXIST, db.insert(dbTest));
		
		assertEquals(3, db.get((long) 987654321).get(1).getNumFollowers());
		assertEquals(2, db.get((long) 123456789).get(1).getNumFollowers());
		assertEquals(0, db.get((long) 987654321).get(0).getNumFollowers());
		assertEquals(0, db.get((long) 123456789).get(0).getNumFollowers());
		assertEquals(null, db.get(null));
	}


	@SuppressWarnings("boxing")
	@Test
	public void testInsertGet()
	{
		assertEquals(SqlError.INVALID_PARAMS, db.insert(null));
		assertEquals(null, db.get(null));
		
		c.setTime(new Date());
		c.set(Calendar.MILLISECOND, 0);
		
		DBFollowersNumber badParamaters = new DBFollowersNumber(null, null, -1);
		
		assertEquals(SqlError.INVALID_PARAMS, db.insert(null));
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badParamaters));
		
		badParamaters = new DBFollowersNumber((long) 123456, null, -1);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badParamaters));
		
		badParamaters =
			new DBFollowersNumber(null, Timestamp.from(c.toInstant()), -1);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badParamaters));
		
		badParamaters = new DBFollowersNumber(null, null, 1);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badParamaters));
		
		final Long userID = (long) 123456;
		final Timestamp date = Timestamp.from(c.toInstant());
		final DBFollowersNumber goodParamaters =
			new DBFollowersNumber(userID, date, 0);
		
		assertEquals(SqlError.SUCCESS, db.insert(goodParamaters));
		
		assertEquals(null, db.get(null));
		
		List<DBFollowersNumber> getResult = db.get(userID);
		assertEquals(1, getResult.size());
		assertEquals(true, getResult.contains(goodParamaters));
		
		c.set(2014, 8, 12, 20, 01, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		final Timestamp newDate = Timestamp.from(c.toInstant());
		goodParamaters.setDate(newDate);
		goodParamaters.setNumFollowers(2);
		assertEquals(SqlError.SUCCESS, db.insert(goodParamaters));
		
		assertEquals(null, db.get(null));
		
		getResult = db.get(userID);
		assertEquals(2, getResult.size());
		assertEquals(true, getResult.contains(goodParamaters));
		
		final Long userNotExist = (long) 666;
		assertEquals(null, db.get(userNotExist));
		assertEquals(null, db.get(null));
		
		int numOfFollowers = 0;
		for (int i = 0; i < 100; i++)
		{
			c.set(2014, 9 + i, 12, 20, 01, 0);
			c.set(Calendar.MILLISECOND, 0);
			
			numOfFollowers = i;
			goodParamaters.setTwitterId(userID);
			goodParamaters.setDate(Timestamp.from(c.toInstant()));
			
			db.insert(goodParamaters);
			
			assertEquals(null, db.get(null));
			
			getResult = db.get(userID);

			assertEquals(null, db.get(userNotExist));

			assertEquals(true, getResult.contains(goodParamaters));
			
			assertEquals(null, db.get(userNotExist));
		}
	}



	Calendar c;
	
	IDatabaseNumFollowers db;
	
}
