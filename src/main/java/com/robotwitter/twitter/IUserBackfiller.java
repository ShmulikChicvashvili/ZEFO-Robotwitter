/**
 * 
 */

package com.robotwitter.twitter;


/**
 * @author Itay
 *
 */
public interface IUserBackfiller
{
	
	public abstract void setUser(Long userID);
	
	
	public abstract void start();
	
	
	public abstract void stop();
	
}
