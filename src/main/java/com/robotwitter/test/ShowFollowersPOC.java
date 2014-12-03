/**
 * 
 */

package com.robotwitter.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.mockito.Mockito;

import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import com.robotwitter.database.MySqlDatabaseTwitterAccounts;
import com.robotwitter.twitter.IllegalPinException;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.twitter.TwitterAppConfiguration;
import com.robotwitter.twitter.TwitterAttacher;




/**
 * @author Itay
 *
 */
public class ShowFollowersPOC
{
	@Test
	public void poc()
	{
		final TwitterAppConfiguration conf = new TwitterAppConfiguration();
		final TwitterFactory tf = new TwitterFactory(conf.getConfiguration());
		TwitterAccount account = new TwitterAccount(tf);
		
		final MySqlDatabaseTwitterAccounts db =
			Mockito.mock(MySqlDatabaseTwitterAccounts.class);
		
		TwitterAttacher attacher = new TwitterAttacher(db);
		
		try
		{
			System.out
				.println("Go to this URL and enter the PIN given after you authorize"); //$NON-NLS-1$
			System.out.println(attacher.getAuthorizationURL(account));
			final BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter PIN: "); //$NON-NLS-1$
			
			attacher.attachAccount("", account, br.readLine()); //$NON-NLS-1$
			System.out.println("Successfully attached this account!"); //$NON-NLS-1$
			System.out.println("User "
				+ account.getTwitter().getScreenName()
				+ "followers:");
			PagableResponseList<User> followers =
				account.getTwitter().getFollowersList(
					account.getTwitter().getId(),
					-1);
			for (User follower : followers)
			{
				System.out.println(follower.getScreenName());
			}
		} catch (TwitterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
