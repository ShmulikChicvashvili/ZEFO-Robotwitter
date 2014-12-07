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
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
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
		
		final Injector injector = Guice.createInjector(new MySQLDBUserModule());
		final IDatabaseUsers db = injector.getInstance(MySqlDatabaseUser.class);
		final DBUser shmulikTheMan = new DBUser("shmulikjkech@gmail.com", "sh");
		if (!db.isExists("shmulikjkech@gmail.com"))
		{
			db.insert(shmulikTheMan);
		}
		assertTrue(db.isExists("shmulikjkech@gmail.com"));
		assertFalse(db.isExists("notanexistingemail@gmail.com"));
		assertFalse(db.isExists(null));
		assertEquals(shmulikTheMan, db.get("shmulikjkech@gmail.com"));
		assertNotEquals(shmulikTheMan, db.get(""));
		assertEquals(null, db.get(""));
		assertNotEquals(null, db.get("Shmulikjkech@gmail.com"));
		
	}
	
}
