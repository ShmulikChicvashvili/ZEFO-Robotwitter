/**
 *
 */

package com.robotwitter.miscellaneous;


import javax.mail.Session;




/**
 * @author Itay
 * 
 *         An interface used to handle JavaMail email session (for example for
 *         gmail, hotmail ect.)
 */
public interface IEmailSession
{
	
	/**
	 * @return the JavaMail email session associated with the EmailSession instance. A Good
	 *         practice is to call initSession first (although most
	 *         implementations will do it for you if you forget)
	 */
	public Session getSession();
	
	
	/**
	 * Initialises the JavaMail email session of the instance.
	 */
	public void initSession();
}
