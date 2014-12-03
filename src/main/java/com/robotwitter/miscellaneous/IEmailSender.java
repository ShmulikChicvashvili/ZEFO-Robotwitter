/**
 *
 */

package com.robotwitter.miscellaneous;


import javax.mail.MessagingException;
import javax.mail.internet.AddressException;




/**
 * @author Itay
 *
 */
public interface IEmailSender
{
	public void sendEmail(EmailMessage mail) throws MessagingException, AddressException;
}
