/**
 *
 */

package com.robotwitter.miscellaneous;


/**
 * @author Itay
 *
 */
public class EmailMessage
{
	/**
	 * @param from
	 * @param to
	 */
	public EmailMessage(final String from, final String to)
	{
		emailAddressFrom = from;
		emailAddressTo = to;
	}


	/**
	 * @return the emailAddressFrom
	 */
	public String getEmailAddressFrom()
	{
		return emailAddressFrom;
	}


	/**
	 * @return the emailAddressTo
	 */
	public String getEmailAddressTo()
	{
		return emailAddressTo;
	}


	/**
	 * @return the msgSubject
	 */
	public String getMsgSubject()
	{
		return msgSubject;
	}


	/**
	 * @return the msgText
	 */
	public String getMsgText()
	{
		return msgText;
	}


	/**
	 * @param emailAddressFrom
	 *            the emailAddressFrom to set
	 */
	public void setFrom(final String from)
	{
		emailAddressFrom = from;
	}


	/**
	 * @param subject
	 */
	public void setSubject(final String subject)
	{
		if (null == subject) { throw new NullPointerException(
			"the subject of the mail cant be null!"); } //$NON-NLS-1$
		msgSubject = subject;
	}
	
	
	/**
	 * @param text
	 */
	public void setText(final String text)
	{
		if (null == text) { throw new NullPointerException(
			"the content of the mail cant be null!"); } //$NON-NLS-1$
		msgText = text;
	}
	
	
	/**
	 * @param emailAddressTo
	 *            the emailAddressTo to set
	 */
	public void setTo(final String to)
	{
		emailAddressTo = to;
	}



	String emailAddressFrom;

	String emailAddressTo;

	String msgSubject = new String();

	String msgText = new String();
}
