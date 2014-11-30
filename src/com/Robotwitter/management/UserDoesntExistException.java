/**
 * 
 */
package com.Robotwitter.management;

/**
 * @author Itay
 *
 */
public class UserDoesntExistException extends Exception
{

	/**
	 * @param message
	 */
	public UserDoesntExistException(String message)
	{
		super(message);
	}	
	
}
