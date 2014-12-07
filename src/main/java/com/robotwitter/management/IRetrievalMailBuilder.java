/**
 * 
 */

package com.robotwitter.management;


import com.robotwitter.miscellaneous.EmailMessage;




/**
 * @author Itay
 *
 */
public interface IRetrievalMailBuilder
{
	/**
	 * @param systemEmail
	 *            the "from" field of the email
	 * @param userEmail
	 *            the "to" field of the email, aka the email the user is
	 *            registered with
	 * @param password
	 *            the password of the user whose email is userEmail
	 * @return an EmailMessage whose fields corresponds to the parameters
	 *         (meaning, the from field is systemEmail, the to field is
	 *         userEmail, the subject and text fields are text helping the user
	 *         to retrieve his password)
	 */
	public EmailMessage buildRetrievalEmail(
		final String systemEmail,
		final String userEmail,
		final String password);
}
