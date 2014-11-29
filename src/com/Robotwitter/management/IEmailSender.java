/**
 * 
 */
package com.Robotwitter.management;

/**
 * @author Itay
 *
 */
public interface IEmailSender
{	
	public void startSession(IEmailSession session);
	
	public void sendEmail(Email mail);
}
