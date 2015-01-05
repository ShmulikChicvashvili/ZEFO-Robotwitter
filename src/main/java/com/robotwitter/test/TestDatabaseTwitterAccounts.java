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
import com.robotwitter.database.MySqlDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBTwitterAccount;




/**
 * @author Shmulik and Eyal
 *
 */
@SuppressWarnings("nls")
public class TestDatabaseTwitterAccounts
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
		db = injector.getInstance(MySqlDatabaseTwitterAccounts.class);
	}


	/**
	 * Test delete single email.
	 */
	@Test
	public final void testDeleteSingleEmail()
	{
		final int num = 50;
		final String email1 = "email1@gmail.com";
		final String email2 = "email1@gmail.com";
		final String tokenPrefix = "token";
		final String pTokenPrefix = "pToken";
		final List<DBTwitterAccount> accounts =
			generateSingleEmailAccounts(num, email1, tokenPrefix, pTokenPrefix);
	}


	/**
	 * Test insert get.
	 */
	@SuppressWarnings("boxing")
	@Test
	public final void testInsertGet()
	{
		assertEquals(SqlError.INVALID_PARAMS, db.insert(null));
		DBTwitterAccount badAccount =
			new DBTwitterAccount(null, null, null, null);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badAccount));
		badAccount =
			new DBTwitterAccount(null, "token", "pToken", (long) 123456);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badAccount));
		badAccount =
			new DBTwitterAccount(
				"email@gmail.com",
				null,
				"pToken",
				(long) 123456);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badAccount));
		badAccount =
			new DBTwitterAccount(
				"email@gmail.com",
				"token",
				null,
				(long) 123456);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badAccount));
		badAccount =
			new DBTwitterAccount("email@gmail.com", "token", "pToken", null);
		assertEquals(SqlError.INVALID_PARAMS, db.insert(badAccount));

		assertEquals(null, db.get(null));
		assertEquals(null, db.get(""));
		assertEquals(null, db.get("email@gmail.com"));

		final String okMail = "OK@gmail.com";
		final String token = "token";
		final String pToken = "privateToken";
		final Long uid = (long) 123;
		final DBTwitterAccount okAccount =
			new DBTwitterAccount(okMail, token, pToken, uid);

		validateAccountNotExists(uid, okMail);

		assertEquals(SqlError.SUCCESS, db.insert(okAccount));
		validateSingleAccount(uid, okMail, token, pToken);
		validateSingleAccount(uid, okMail.toUpperCase(), token, pToken);

		assertEquals(SqlError.ALREADY_EXIST, db.insert(okAccount));
		validateSingleAccount(uid, okMail, token, pToken);

		okAccount.setEMail("NotOkMail@gmail.com");
		assertEquals(SqlError.ALREADY_EXIST, db.insert(okAccount));
		validateSingleAccount(uid, okMail, token, pToken);

		okAccount.setToken("DifferentToken");
		assertEquals(SqlError.ALREADY_EXIST, db.insert(okAccount));
		validateSingleAccount(uid, okMail, token, pToken);

		okAccount.setPrivateToken("DifferentPrivateToken");
		assertEquals(SqlError.ALREADY_EXIST, db.insert(okAccount));
		validateSingleAccount(uid, okMail, token, pToken);

		final DBTwitterAccount bigOkAccount =
			new DBTwitterAccount(
				okMail.toUpperCase(),
				"DifferentToken",
				"DifferentPrivateToken",
				uid);
		assertEquals(SqlError.ALREADY_EXIST, db.insert(bigOkAccount));
	}


	/**
	 * Test insert get single email.
	 */
	@Test
	public final void testInsertGetSingleEmail()
	{
		final String email = "eMail@gmail.com";
		final List<DBTwitterAccount> accounts =
			generateSingleEmailAccounts(50, email, "token", "pToken");

		for (final DBTwitterAccount account : accounts)
		{
			assertFalse(db.isExists(account.getUserId()));
			assertEquals(SqlError.SUCCESS, db.insert(account));
			assertEquals(SqlError.ALREADY_EXIST, db.insert(account));
		}
		validateAccountListsSame(email, accounts);
		validateAccountListsSame(email.toUpperCase(), accounts);

		final List<DBTwitterAccount> badEmailAccounts =
			db.get("badEmail@Gmail.COm");
		assertEquals(null, badEmailAccounts);
	}


	/**
	 * Test is exist.
	 */
	@SuppressWarnings("boxing")
	@Test
	public final void testIsExist()
	{
		assertFalse(db.isExists(null));
		assertFalse(db.isExists((long) 123));
		assertFalse(db.isExists((long) -123));
	}


	/**
	 * Generate single email accounts.
	 *
	 * @param num
	 *            the num
	 * @param email
	 *            the email
	 * @param tokenPrefix
	 *            the token prefix
	 * @param pTokenPrefix
	 *            the token prefix
	 * @return the list
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	private List<DBTwitterAccount> generateSingleEmailAccounts(
		int num,
		String email,
		String tokenPrefix,
		String pTokenPrefix)
	{
		final List<DBTwitterAccount> accounts = new ArrayList<>();
		for (int i = 0; i < num; ++i)
		{
			final DBTwitterAccount account =
				new DBTwitterAccount(
					email,
					tokenPrefix + i,
					pTokenPrefix + i,
					(long) i);
			accounts.add(account);
		}
		return accounts;
	}


	/**
	 * Validate the list we get from Database for the given email is the
	 * expected one.
	 *
	 * @param email
	 *            the email
	 * @param expected
	 *            the expected
	 */
	private void validateAccountListsSame(
		String email,
		List<DBTwitterAccount> expected)
	{
		final List<DBTwitterAccount> accountsOnDB = db.get(email);

		if (expected == null)
		{
			assertEquals(null, accountsOnDB);
			return;
		}
		assertNotEquals(null, accountsOnDB);
		assertEquals(expected.size(), accountsOnDB.size());

		for (final DBTwitterAccount acc : expected)
		{
			assertTrue(accountsOnDB.contains(acc));
		}
		for (final DBTwitterAccount acc : accountsOnDB)
		{
			assertTrue(expected.contains(acc));
		}
	}


	/**
	 * Validate user not exists.
	 *
	 * @param uid
	 *            the uid
	 * @param email
	 *            the email
	 */
	private void validateAccountNotExists(Long uid, String email)
	{
		assertFalse(db.isExists(uid));
		assertEquals(null, db.get(email));
	}


	/**
	 * Validate db user.
	 *
	 * @param uid
	 *            the uid
	 * @param email
	 *            the email
	 * @param token
	 *            the token
	 * @param pToken
	 *            the token
	 */
	private void validateSingleAccount(
		Long uid,
		String email,
		String token,
		String pToken)
	{
		assertTrue(db.isExists(uid));
		final ArrayList<DBTwitterAccount> inDBList = db.get(email);
		assertNotEquals(null, inDBList);
		assertEquals(1, inDBList.size());

		final DBTwitterAccount inDB = inDBList.get(0);
		assertEquals(uid, inDB.getUserId());
		assertEquals(email.toLowerCase(), inDB.getEMail().toLowerCase());
		assertEquals(token, inDB.getToken());
		assertEquals(pToken, inDB.getPrivateToken());
	}



	IDatabaseTwitterAccounts db;
}
