/**
 * 
 */

package com.Robotwitter.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

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
		this.attacher = new TwitterAttacher();
	}
	
	
	@Test
	public void test() {
		System.out.println("Go to this URL and enter the PIN given after you authorize"); //$NON-NLS-1$
		System.out.println(this.attacher.getAuthorizationURL(this.account));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter PIN: "); //$NON-NLS-1$
		
		try
		{
			this.attacher.attachAccount(this.account, br.readLine());
			System.out.println("Successfully attached this account!"); //$NON-NLS-1$
					
			System.out.print("As proof, choose a tweet to post: "); //$NON-NLS-1$
			Twitter attachedTwitter = this.account.getTwitter();
			attachedTwitter.updateStatus(br.readLine());
			
			AccessToken token = attachedTwitter.getOAuthAccessToken();
			String tokenString = token.getToken();
			String tokenSecret = token.getTokenSecret();
			
			System.out.println("Trying to build the twitter from the secrets."); //$NON-NLS-1$
			
			AccessToken test_token = new AccessToken(tokenString, tokenSecret);
			TwitterAppConfiguration conf = new TwitterAppConfiguration();
			TwitterFactory tf = new TwitterFactory(conf.getConfiguration());
			Twitter t = tf.getInstance();
			t.setOAuthAccessToken(test_token);
			System.out.print("As another proof, choose a tweet to post: "); //$NON-NLS-1$
			t.updateStatus(br.readLine());
			System.out.println(t.getId()); //$NON-NLS-1$
			System.out.println(t.showUser(t.getId()).getName()); //$NON-NLS-1$
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
