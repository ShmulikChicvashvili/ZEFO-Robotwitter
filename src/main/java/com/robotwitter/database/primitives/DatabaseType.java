/**
 *
 */

package com.robotwitter.database.primitives;


/**
 * @author Shmulik and Eyal
 *
 *         Everything that's going to be kept on the database will be kept for
 *         certain user
 */
public class DatabaseType
{
	/**
	 * @param email
	 *            The email of the user
	 */
	public DatabaseType(final String email)
	{
		eMail = email;
	}


	/* (non-Javadoc) @see java.lang.Object#equals(java.lang.Object) */
	@SuppressWarnings("cast")
	@Override
	public boolean equals(final Object obj)
	{
		if(obj == null || !(obj instanceof DatabaseType)) { return false; }
		DatabaseType other = (DatabaseType) obj;
		return other.eMail.equals(eMail);
	}


	/**
	 * @return the email
	 */
	public String getEMail()
	{
		return eMail;
	}


	/**
	 * @param email
	 *            the Email to set
	 */
	public void setEMail(final String email)
	{
		eMail = email;
	}


	/* (non-Javadoc) @see java.lang.Object#toString() */
	@Override
	public String toString()
	{
		return eMail;
	}



	/**
	 * The user name of the user
	 */
	String eMail;

}
