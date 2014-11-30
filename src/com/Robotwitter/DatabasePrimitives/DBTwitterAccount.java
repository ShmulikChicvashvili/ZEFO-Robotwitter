/**
 * 
 */

package com.Robotwitter.DatabasePrimitives;


/**
 * @author Shmulik
 *
 */
public class DBTwitterAccount extends DatabaseType
{
	
	/**
	 * Argument given by twitter system
	 */
	String token;
	
	/**
	 * Argument given by twitter system
	 */
	String privateToken;
	
	/**
	 * The user id
	 */
	Long userId;
	
	
	
	/**
	 * @param email
	 *            The email of the user which connected to his twitter account
	 * @param token
	 *            One of the arguments given by twitter on login of user
	 * @param privateToken
	 *            One of the arguments given by twitter on login of user
	 * @param userId
	 *            the user id
	 */
	public DBTwitterAccount(
		String email,
		String token,
		String privateToken,
		Long userId)
	{
		super(email);
		this.token = token;
		this.privateToken = privateToken;
		this.userId = userId;
	}
	
	
	/**
	 * @return the token
	 */
	public String getToken()
	{
		return this.token;
	}
	
	
	/**
	 * @param token
	 *            The token to set
	 */
	public void setToken(String token)
	{
		this.token = token;
	}
	
	
	/**
	 * @return the privateToken
	 */
	public String getPrivateToken()
	{
		return this.privateToken;
	}
	
	
	/**
	 * @param privateToken
	 *            The privateToken to set
	 */
	public void setPrivateToken(String privateToken)
	{
		this.privateToken = privateToken;
	}
	
	
	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return this.userId;
	}
	
	
	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.DatabasePrimitives.DatabaseType#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			DBTwitterAccount twitterAccount = (DBTwitterAccount) obj;
			return (this.getEMail() == twitterAccount.getEMail()
				&& this.getToken() == twitterAccount.getToken()
				&& this.getPrivateToken() == twitterAccount.getPrivateToken() && this
					.getUserId() == twitterAccount.getUserId());
		}
		return false;
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.DatabasePrimitives.DatabaseType#toString() */
	@SuppressWarnings("nls")
	@Override
	public String toString()
	{
		return "Email: "
			+ this.getEMail()
			+ " Token: "
			+ this.getToken()
			+ " Private Token: "
			+ this.getPrivateToken();
	}
	
}
