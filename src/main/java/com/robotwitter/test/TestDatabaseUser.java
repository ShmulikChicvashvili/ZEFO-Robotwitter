/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mysql.jdbc.Statement;

import com.robotwitter.database.MySQLConEstablisher;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBUser;




/**
 * The Class TestDatabaseUser.
 *
 * @author Shmulik and Eyal
 */
@SuppressWarnings("nls")
public class TestDatabaseUser
{
	
	/**
	 * Before.
	 */
	@Before
	public void before()
	{
		final Injector injector =
			Guice.createInjector(new DatabaseTestModule());
		
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
		db = injector.getInstance(MySqlDatabaseUser.class);
	}
	
	
	/**
	 * Testing the user database table features
	 */
	@Test
	public void test()
	{
		
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
	
	
	/**
	 * Test insert.
	 */
	@Test
	public void testInsertGet()
	{
		assertEquals(SqlError.INVALID_PARAMS, db.insert(null));
		DBUser badUser = new DBUser(null, null);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badUser));
		badUser = new DBUser("email@gmail.com", null);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badUser));
		badUser = new DBUser(null, "pass");
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badUser));
		
		assertEquals(null, db.get(null));
		
		String okMail = "ok@gmail.com";
		String pass = "pass";
		DBUser okUser = new DBUser(okMail, pass);
		
		validateUserNotExists(okMail);
		
		assertEquals(SqlError.SUCCESS, db.insert(okUser));
		validateUser(okMail, pass);
		
		assertEquals(SqlError.ALREADY_EXIST, db.insert(okUser));
		validateUser(okMail, pass);
		
		okUser.setPassword("different");
		assertEquals(SqlError.ALREADY_EXIST, db.insert(okUser));
		validateUser(okMail, pass);

		DBUser bigOkUser = new DBUser(okMail.toUpperCase(), "bla");
		assertEquals(SqlError.ALREADY_EXIST, db.insert(bigOkUser));
	}
	
	
	/**
	 * Test is exist.
	 */
	@Test
	public void testIsExist()
	{
		assertFalse(db.isExists(null));
		assertFalse(db.isExists(""));
		assertFalse(db.isExists("asd"));
		assertFalse(db.isExists("ASD"));
		assertFalse(db.isExists("email@gmail.com"));
	}
	
	
	/**
	 * Validate db user.
	 *
	 * @param email
	 *            the email
	 * @param pass
	 *            the pass
	 */
	private void validateUser(String email, String pass)
	{
		assertTrue(db.isExists(email));
		DBUser inDB = db.get(email);
		assertNotEquals(null, inDB);
		assertEquals(email.toLowerCase(), inDB.getEMail().toLowerCase());
		assertEquals(pass, inDB.getPassword());
	}
	
	
	/**
	 * Validate user not exists.
	 *
	 * @param email the email
	 */
	private void validateUserNotExists(String email)
	{
		assertFalse(db.isExists(email));
		assertEquals(null, db.get(email));
	}
	
	
	
	/** The db. */
	IDatabaseUsers db;
}
