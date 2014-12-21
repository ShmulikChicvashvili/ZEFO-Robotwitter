/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mysql.jdbc.Statement;

import com.robotwitter.database.MySQLConEstablisher;
import com.robotwitter.database.MySqlDatabaseNumFollowers;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBFollowersNumber;




/**
 * @author Shmulik
 *
 */
public class DatabaseFollowersTest
{

	/**
	 * Before.
	 */
	@Before
	public void before()
	{
		final Injector injector = Guice.createInjector(new DatabaseTestModule());
		
		try (
			Connection con =
				injector.getInstance(MySQLConEstablisher.class).getConnection();
			Statement statement = (Statement) con.createStatement())
		{
			String dropSchema = "DROP DATABASE `test`";
			statement.executeUpdate(dropSchema);
		} catch (SQLException e)
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
	
	
	
	Calendar c;
	
	IDatabaseNumFollowers db;
	
}
