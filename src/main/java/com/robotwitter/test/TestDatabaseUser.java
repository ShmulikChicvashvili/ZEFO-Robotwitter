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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

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
	 * Generate users.
	 *
	 * @param num
	 *            the number of users to generate
	 * @param emailPrefix
	 *            the email prefix
	 * @param passPrefix
	 *            the pass prefix
	 * @return the list
	 */
	private static List<DBUser> generateUsers(
		int num,
		String emailPrefix,
		String passPrefix)
	{
		final List<DBUser> users = new ArrayList<>();
		for (int i = 0; i < num; ++i)
		{
			final String email = emailPrefix + i + "@gmail.com";
			final String pass = passPrefix + i;
			final DBUser user = new DBUser(email, pass);
			users.add(user);
		}
		return users;
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
			final String dropSchema = "DROP DATABASE `test`";
			statement.executeUpdate(dropSchema);
		} catch (final SQLException e)
		{
			System.out.println(e.getErrorCode());
		}
		db = injector.getInstance(MySqlDatabaseUser.class);
	}


	/**
	 * Testing the user database table features
	 */
	@Test
	public final void test()
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
	public final void testInsertGet()
	{
		assertEquals(SqlError.INVALID_PARAMS, db.insert(null));
		DBUser badUser = new DBUser(null, null);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badUser));
		badUser = new DBUser("email@gmail.com", null);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badUser));
		badUser = new DBUser(null, "pass");
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badUser));

		assertEquals(null, db.get(null));
		assertEquals(null, db.get(""));
		assertEquals(null, db.get("email@gmail.com"));

		final String okMail = "ok@gmail.com";
		final String pass = "pass";
		final DBUser okUser = new DBUser(okMail, pass);

		validateUserNotExists(okMail);

		assertEquals(SqlError.SUCCESS, db.insert(okUser));
		validateUser(okMail, pass);

		assertEquals(SqlError.ALREADY_EXIST, db.insert(okUser));
		validateUser(okMail, pass);

		okUser.setPassword("different");
		assertEquals(SqlError.ALREADY_EXIST, db.insert(okUser));
		validateUser(okMail, pass);

		final DBUser bigOkUser = new DBUser(okMail.toUpperCase(), "bla");
		assertEquals(SqlError.ALREADY_EXIST, db.insert(bigOkUser));
	}


	/**
	 * Test insert get big data.
	 */
	@Test
	public final void testInsertGetBigData()
	{
		final List<DBUser> users = generateUsers(100, "email", "pass");

		for (final DBUser user : users)
		{
			validateUserNotExists(user.getEMail());
			assertEquals(SqlError.SUCCESS, db.insert(user));
			validateUser(user.getEMail(), user.getPassword());
		}
		validateUser("email50@gmail.com", "pass50");

		final DBUser existingUser =
			new DBUser("EmaIl66@gmail.Com", "notAPassword");
		assertEquals(SqlError.ALREADY_EXIST, db.insert(existingUser));
		validateUser("Email66@gmail.com", "pass66");
	}


	/**
	 * Test is exist.
	 */
	@Test
	public final void testIsExist()
	{
		assertFalse(db.isExists(null));
		assertFalse(db.isExists(""));
		assertFalse(db.isExists("asd"));
		assertFalse(db.isExists("ASD"));
		assertFalse(db.isExists("email@gmail.com"));
	}


	/**
	 * Test update.
	 */
	@Test
	public final void testUpdate()
	{
		assertEquals(SqlError.INVALID_PARAMS, db.update(null));
		assertEquals(SqlError.INVALID_PARAMS, db.update(new DBUser(null, null)));
		assertEquals(
			SqlError.INVALID_PARAMS,
			db.update(new DBUser("email", null)));
		assertEquals(
			SqlError.INVALID_PARAMS,
			db.update(new DBUser(null, "pass")));

		final List<DBUser> users = generateUsers(100, "email", "pass");
		final List<DBUser> updatedUsers = generateUsers(50, "email", "newPass");

		for (final DBUser updatedUser : updatedUsers)
		{
			validateUserNotExists(updatedUser.getEMail());
			assertEquals(SqlError.DOES_NOT_EXIST, db.update(updatedUser));
		}

		for (final DBUser user : users)
		{
			validateUserNotExists(user.getEMail());
			assertEquals(SqlError.SUCCESS, db.insert(user));
		}

		for (final DBUser updatedUser : updatedUsers)
		{
			assertEquals(SqlError.SUCCESS, db.update(updatedUser));
			validateUser(updatedUser.getEMail(), updatedUser.getPassword());
		}
		validateUser("email10@gmail.com", "newPass10");
		validateUser("email60@gmail.com", "pass60");
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
		final DBUser inDB = db.get(email);
		assertNotEquals(null, inDB);
		assertEquals(email.toLowerCase(), inDB.getEMail().toLowerCase());
		assertEquals(pass, inDB.getPassword());
	}


	/**
	 * Validate user not exists.
	 *
	 * @param email
	 *            the email
	 */
	private void validateUserNotExists(String email)
	{
		assertFalse(db.isExists(email));
		assertEquals(null, db.get(email));
	}



	/** The db. */
	IDatabaseUsers db;
}
