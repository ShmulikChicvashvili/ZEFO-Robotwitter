/**
 *
 */

package com.robotwitter.twitter;


/**
 * @author Itay
 *
 */
public interface ITwitterAttacher
{
	/**
	 * @param account
	 * @param pin
	 * @throws IllegalPinException
	 */
	void attachAccount(String userEmail, TwitterAccount account, String pin)
		throws IllegalPinException;
	
	
	/**
	 * @param account
	 * @return
	 */
	String getAuthorizationURL(TwitterAccount account);
}
