/**
 *
 */

package com.robotwitter.miscellaneous;


import javax.mail.MessagingException;




/**
 * @author Itay
 *
 */
public interface IEmailSender
{
	public void sendEmail(EmailMessage mail) throws MessagingException;
}
