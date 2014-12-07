/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

import com.robotwitter.database.MySQLConEstablisher;
import com.robotwitter.database.MySqlDatabaseNumFollowers;
import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.returnValues.InsertError;
import com.robotwitter.database.primitives.DBFollowersNumber;




/**
 * @author Shmulik
 *
 */
public class DatabaseFollowersTest
{
	public class MockDatabase extends AbstractModule
	{

		/**
		 *
		 */
		public MockDatabase()
		{}


		/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
		@SuppressWarnings("nls")
		@Override
		protected void configure()
		{
			bind(String.class)
				.annotatedWith(Names.named("DB Server"))
				.toInstance("localhost");
			bind(String.class)
				.annotatedWith(Names.named("DB Schema"))
				.toInstance("test");

			bind(ConnectionEstablisher.class).to(MySQLConEstablisher.class);
		}

	}



	@Before
	public void Before()
	{
		final Injector injector = Guice.createInjector(new MockDatabase());
		db = injector.getInstance(MySqlDatabaseNumFollowers.class);
	}


	@SuppressWarnings("boxing")
	@Test
	public void testGet()
	{
		assertEquals(3, db.get((long) 987654321).get(1));
		assertEquals(2, db.get((long) 123456789).get(1));
		assertEquals(0, db.get((long) 987654321).get(0));
		assertEquals(0, db.get((long) 123456789).get(0));
		assertEquals(null, db.get(null));
	}


	@SuppressWarnings("boxing")
	@Test
	public void testInsert()
	{
		assertEquals(InsertError.INVALID_PARAMS, db.insert(null));
		assertEquals(null, db.get(null));
		final DBFollowersNumber dbTest =
			new DBFollowersNumber((long) 123456789, Timestamp.from(new Date()
				.toInstant()), 0);
		assertEquals(InsertError.SUCCESS, db.insert(dbTest));
		assertEquals(InsertError.ALREADY_EXIST, db.insert(dbTest));
		dbTest.setDate(Timestamp.from(new Date().toInstant()));
		dbTest.setNumFollowers(2);
		assertEquals(InsertError.SUCCESS, db.insert(dbTest));
		dbTest.setTwitterId((long) 987654321);
		dbTest.setNumFollowers(0);
		assertEquals(InsertError.SUCCESS, db.insert(dbTest));
		assertEquals(InsertError.ALREADY_EXIST, db.insert(dbTest));
		dbTest.setDate(Timestamp.from(new Date().toInstant()));
		dbTest.setNumFollowers(3);
		assertEquals(InsertError.SUCCESS, db.insert(dbTest));
		assertEquals(InsertError.ALREADY_EXIST, db.insert(dbTest));
	}



	IDatabaseNumFollowers db;
	
}
