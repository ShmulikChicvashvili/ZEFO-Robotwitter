/**
 *
 */

package com.robotwitter.miscellaneous;


import javax.mail.Session;




/**
 * @author Itay
 *
 */
public interface IEmailSession
{
	public Session getSession();
	
	
	public void initSession();
}
