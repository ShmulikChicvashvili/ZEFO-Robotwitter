/**
 * 
 */
package com.robotwitter.test;

import com.robotwitter.management.IRetrievalMailBuilder;
import com.robotwitter.miscellaneous.EmailMessage;

/**
 * @author Itay
 *
 */
public class RetrievelMailBuilderMock implements IRetrievalMailBuilder
{
	
	/**
	 * @param templatePath
	 * @param reader
	 */
	public RetrievelMailBuilderMock()
	{
	}
	
	
	@Override
	public EmailMessage buildRetrievalEmail(
		final String systemEmail,
		final String userEmail,
		final String password)
	{
		EmailMessage $ = new EmailMessage(systemEmail, userEmail);
		$.setSubject("Your Robotwitter account password");
		$
			.setText("Oops, forgot your password?\nDon't worry, we got you covered!\n\nYour password is: "
				+ password
				+ "\n\nHave a pleasant day,\n\tRobotwitter.");
		return $;
	}
	
}
