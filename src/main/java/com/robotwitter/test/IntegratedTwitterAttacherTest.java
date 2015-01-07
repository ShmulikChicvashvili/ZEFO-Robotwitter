/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySqlConnectionEstablisherModule;
import com.robotwitter.database.MySqlDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBTwitterAccount;
import com.robotwitter.twitter.IllegalPinException;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.twitter.TwitterAppConfiguration;
import com.robotwitter.twitter.TwitterAttacher;




/**
 * @author Itay
 *
 */
public class IntegratedTwitterAttacherTest
{
	@Before
	public void before()
	{
		final TwitterAppConfiguration conf = new TwitterAppConfiguration();
		final TwitterFactory tf =
			new TwitterFactory(conf.getUserConfiguration());
		account = new TwitterAccount(tf);

		final Injector injector = Guice.createInjector(new MySqlConnectionEstablisherModule());
		db = injector.getInstance(MySqlDatabaseTwitterAccounts.class);

		attacher = new TwitterAttacher(db);
	}


	@Test
	public void test()
	{
		System.out
		.println("Enter the url and enter the pin after authrization");
		System.out.println(attacher.getAuthorizationURL(account));

		BufferedReader br =
			new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter PIN: "); //$NON-NLS-1$
		try
		{
			attacher.attachAccount(
				"shmulikjkech@gmail.com",
				account,
				br.readLine());
			System.out.println("Attached account!");

			ArrayList<DBTwitterAccount> twitterAccounts =
				db.get("shmulikjkech@gmail.com");
			assertTrue(twitterAccounts.size() == 1);
			DBTwitterAccount shmulikAccount = twitterAccounts.get(0);
			Twitter shmulikTwitter =
				new TwitterFactory(
					new TwitterAppConfiguration().getUserConfiguration())
			.getInstance();
			AccessToken shmulikAccess =
				new AccessToken(
					shmulikAccount.getToken(),
					shmulikAccount.getPrivateToken());
			shmulikTwitter.setOAuthAccessToken(shmulikAccess);

			try
			{
				shmulikTwitter.updateStatus("yolo swag!");
			} catch (TwitterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IllegalPinException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	TwitterAccount account;

	TwitterAttacher attacher;

	MySqlDatabaseTwitterAccounts db;

}
