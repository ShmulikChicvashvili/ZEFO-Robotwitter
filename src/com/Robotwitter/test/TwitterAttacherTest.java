/**
 * 
 */

package com.Robotwitter.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.Robotwitter.Database.MySqlDatabaseTwitterAccounts;
import com.Robotwitter.twitter.IllegalPinException;
import com.Robotwitter.twitter.TwitterAccount;
import com.Robotwitter.twitter.TwitterAppConfiguration;
import com.Robotwitter.twitter.TwitterAttacher;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;




/**
 * @author Itay
 *
 */
public class TwitterAttacherTest // TODO: think how to test this...
{
	
	TwitterAttacher attacher;
	
	TwitterAccount account;
	
	
	
	@Before
	public void before()
	{
		TwitterAppConfiguration conf = new TwitterAppConfiguration();
		TwitterFactory tf = new TwitterFactory(conf.getConfiguration());
		this.account = new TwitterAccount(tf);
		
		MySqlDatabaseTwitterAccounts db = Mockito.mock(MySqlDatabaseTwitterAccounts.class);
		
		this.attacher = new TwitterAttacher(db);
	}
	
	
	@Test
	public void test()
	{
		System.out
			.println("Go to this URL and enter the PIN given after you authorize"); //$NON-NLS-1$
		System.out.println(this.attacher.getAuthorizationURL(this.account));
		BufferedReader br =
			new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter PIN: "); //$NON-NLS-1$
		
		try
		{
			this.attacher.attachAccount("itaykhazon@gmail.com",this.account, br.readLine()); //$NON-NLS-1$
			System.out.println("Successfully attached this account!"); //$NON-NLS-1$
			
			System.out.print("As proof, choose a tweet to post: "); //$NON-NLS-1$
			Twitter attachedTwitter = this.account.getTwitter();
			attachedTwitter.updateStatus(br.readLine());
			
			AccessToken token = attachedTwitter.getOAuthAccessToken();
			String tokenString = token.getToken();
			String tokenSecret = token.getTokenSecret();
			
		} catch (IllegalPinException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
