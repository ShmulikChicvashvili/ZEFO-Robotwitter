/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.IDatabase;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.primitives.DBUser;




/**
 * @author Shmulik
 *
 */
public class TestDatabaseUser
{
	/**
	 * Testing the user database table features
	 */
	@SuppressWarnings("nls")
	@Test
	public void test()
	{
		try
		{
			final Injector injector =
				Guice.createInjector(new MySQLDBUserModule());
			final IDatabase db = injector.getInstance(MySqlDatabaseUser.class);
			final DBUser shmulikTheMan =
				new DBUser("shmulikjkech@gmail.com", "sh");
			if (!db.isExists(shmulikTheMan))
			{
				db.insert(shmulikTheMan);
			}
			assertTrue(db.isExists(shmulikTheMan));
			// assertFalse(db.isExists("notanexistingemail@gmail.com"));
			assertFalse(db.isExists(null));
			assertEquals(db.get("shmulikjkech@gmail.com").size(), 1);
			assertEquals(shmulikTheMan, db.get("shmulikjkech@gmail.com").get(0));
			assertNotEquals(shmulikTheMan, db.get(""));
			assertEquals(null, db.get(""));
			assertNotEquals(null, db.get("Shmulikjkech@gmail.com"));
		} catch (final Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
