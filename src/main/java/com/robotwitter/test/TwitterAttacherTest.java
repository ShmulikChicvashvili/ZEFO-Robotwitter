/**
 *
 */

package com.robotwitter.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.robotwitter.database.MySqlDatabaseTwitterAccounts;
import com.robotwitter.twitter.IllegalPinException;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.twitter.TwitterAppConfiguration;
import com.robotwitter.twitter.TwitterAttacher;




/**
 * @author Itay
 *
 */
public class TwitterAttacherTest // TODO: think how to test this...
{

	@Before
	public void before()
	{
		final TwitterAppConfiguration conf = new TwitterAppConfiguration();
		final TwitterFactory tf = new TwitterFactory(conf.getConfiguration());
		account = new TwitterAccount(tf);

		final MySqlDatabaseTwitterAccounts db =
			Mockito.mock(MySqlDatabaseTwitterAccounts.class);

		attacher = new TwitterAttacher(db);
	}
	
	
	@Test
	public void test()
	{
		System.out
		.println("Go to this URL and enter the PIN given after you authorize"); //$NON-NLS-1$
		System.out.println(attacher.getAuthorizationURL(account));
		final BufferedReader br =
			new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter PIN: "); //$NON-NLS-1$

		try
		{
			attacher.attachAccount(
				"itaykhazon@gmail.com", account, br.readLine()); //$NON-NLS-1$
			System.out.println("Successfully attached this account!"); //$NON-NLS-1$

			System.out.print("As proof, choose a tweet to post: "); //$NON-NLS-1$
			final Twitter attachedTwitter = account.getTwitter();
			attachedTwitter.updateStatus(br.readLine());

			final AccessToken token = attachedTwitter.getOAuthAccessToken();
			final String tokenString = token.getToken();
			final String tokenSecret = token.getTokenSecret();

		} catch (final IllegalPinException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final TwitterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	TwitterAttacher attacher;
	
	TwitterAccount account;
}
