/**
 *
 */

package com.robotwitter.management;


/**
 * @author Itay
 *
 */
public class UserDoesntExistException extends Exception
{
	
	/**
	 * @param message
	 */
	public UserDoesntExistException(final String message)
	{
		super(message);
	}
	
}
