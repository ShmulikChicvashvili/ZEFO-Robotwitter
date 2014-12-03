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
	public EmailMessage buildRetrievalEmail(
		final String systemEmail,
		final String userEmail,
		final String password);
}
