/**
 *
 */

package com.robotwitter.database.primitives;



/**
 * @author Shmulik
 *
 */
public class DBTwitterAccount extends DatabaseType
{

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
		final String email,
		final String token,
		final String privateToken,
		final Long userId)
	{
		super(email);
		this.token = token;
		this.privateToken = privateToken;
		this.userId = userId;
	}


	/* (non-Javadoc) @see
	 * com.Robotwitter.DatabasePrimitives.DatabaseType#equals(java.lang.Object) */
	@Override
	public boolean equals(final Object obj)
	{
		if (obj != null && obj instanceof DBTwitterAccount)
		{
			final DBTwitterAccount twitterAccount = (DBTwitterAccount) obj;
			return getEMail() == twitterAccount.getEMail()
				&& getToken() == twitterAccount.getToken()
				&& getPrivateToken() == twitterAccount.getPrivateToken()
				&& getUserId() == twitterAccount.getUserId();
		}
		return false;
	}


	/**
	 * @return the privateToken
	 */
	public String getPrivateToken()
	{
		return privateToken;
	}


	/**
	 * @return the token
	 */
	public String getToken()
	{
		return token;
	}


	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}


	/**
	 * @param privateToken
	 *            The privateToken to set
	 */
	public void setPrivateToken(final String privateToken)
	{
		this.privateToken = privateToken;
	}


	/**
	 * @param token
	 *            The token to set
	 */
	public void setToken(final String token)
	{
		this.token = token;
	}


	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(final Long userId)
	{
		this.userId = userId;
	}


	/* (non-Javadoc) @see
	 * com.Robotwitter.DatabasePrimitives.DatabaseType#toString() */
	@SuppressWarnings("nls")
	@Override
	public String toString()
	{
		return "Email: "
			+ getEMail()
			+ " Token: "
			+ getToken()
			+ " Private Token: "
			+ getPrivateToken();
	}



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

}
