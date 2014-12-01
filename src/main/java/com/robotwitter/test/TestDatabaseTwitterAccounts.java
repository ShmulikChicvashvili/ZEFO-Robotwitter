/**
 *
 */

package com.robotwitter.test;


import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBTwitterAccount;




/**
 * @author Shmulik
 *
 */
public class TestDatabaseTwitterAccounts
{

	@SuppressWarnings({ "nls", "boxing" })
	@Test
	public void test()
	{
		try
		{
			final Injector injector =
				Guice.createInjector(new MySQLDBUserModule());
			final IDatabaseTwitterAccounts db =
				injector.getInstance(MySqlDatabaseTwitterAccounts.class);
			final DBTwitterAccount shmulikAccount =
				new DBTwitterAccount(
					"Shmulik@gmail.com",
					"tokenblalblala",
					"tokenSecret",
					(long) 123456789);
			if (!db.isExists((long) 123456789))
			{
				db.insert(shmulikAccount);
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

}
