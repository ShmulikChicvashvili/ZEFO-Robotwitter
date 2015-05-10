/**
 * 
 */

package com.robotwitter.database.primitives;


import com.robotwitter.posting.TweetPostingPreferenceType;




/**
 * @author Shmulik
 *
 */
public class DBTweetPostingPreferences extends DatabaseType
{
	
	/**
	 * @param email
	 */
	public DBTweetPostingPreferences(
		String email,
		TweetPostingPreferenceType postingPreference,
		String prefix,
		String postfix)
	{
		super(email);
		this.postingPreference = postingPreference;
		this.prefix = prefix;
		this.postfix = postfix;
	}
	
	
	/**
	 * @return the postingPreference
	 */
	public TweetPostingPreferenceType getPostingPreference()
	{
		return postingPreference;
	}
	
	
	/**
	 * @param postingPreference
	 *            the postingPreference to set
	 */
	public void setPostingPreference(
		TweetPostingPreferenceType postingPreference)
	{
		this.postingPreference = postingPreference;
	}
	
	
	/**
	 * @return the prefix
	 */
	public String getPrefix()
	{
		return prefix;
	}
	
	
	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}
	
	
	/**
	 * @return the postfix
	 */
	public String getPostfix()
	{
		return postfix;
	}
	
	
	/**
	 * @param postfix
	 *            the postfix to set
	 */
	public void setPostfix(String postfix)
	{
		this.postfix = postfix;
	}
	
	
	
	TweetPostingPreferenceType postingPreference;
	
	String prefix;
	
	String postfix;
}
