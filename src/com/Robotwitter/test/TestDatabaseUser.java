/**
 * 
 */

package com.Robotwitter.test;


import static org.junit.Assert.*;

import org.junit.Test;

import com.Robotwitter.Database.IDatabase;
import com.Robotwitter.Database.MySQLDBUserModule;
import com.Robotwitter.Database.MySqlDatabaseUser;
import com.Robotwitter.DatabasePrimitives.DBUser;
import com.google.inject.Guice;
import com.google.inject.Injector;




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
			Injector injector = Guice.createInjector(new MySQLDBUserModule());
			IDatabase db = injector.getInstance(MySqlDatabaseUser.class);
			DBUser shmulikTheMan = new DBUser("shmulikjkech@gmail.com", "sh");
			if (!db.isExists(shmulikTheMan))
			{
				db.insert(shmulikTheMan);
			}
			assertTrue(db.isExists(shmulikTheMan));
//			assertFalse(db.isExists("notanexistingemail@gmail.com"));
			assertFalse(db.isExists(null));
			assertEquals(db.get("shmulikjkech@gmail.com").size(), 1);
			assertEquals(shmulikTheMan, (db.get("shmulikjkech@gmail.com")).get(0));
			assertNotEquals(shmulikTheMan, db.get(""));
			assertEquals(null, db.get(""));
			assertNotEquals(null, db.get("Shmulikjkech@gmail.com"));
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
