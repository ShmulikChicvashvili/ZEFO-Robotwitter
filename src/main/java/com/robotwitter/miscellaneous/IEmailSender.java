/**
 *
 */

package com.robotwitter.miscellaneous;


import javax.mail.MessagingException;
import javax.mail.internet.AddressException;




/**
 * @author Itay
 * 
 *         An interface designed for sending email using the JavaMail library
 *         (as seen from the exceptions).
 *
 */
public interface IEmailSender
{
	/**
	 * @param mail
	 *            a valid email message to send, containing all the email
	 *            information (from, to, subject and text)
	 * @throws MessagingException
	 *             if the network can't send the mail or the email isn't
	 *             auhtorized (for example if the mail session doesn't agree
	 *             with the "from field of the email)
	 * @throws AddressException
	 *             if the "to" can't be parsed correctly to an email address
	 */
	public void sendEmail(EmailMessage mail)
		throws MessagingException,
		AddressException;
}
