/**
 * 
 */

package com.Robotwitter.miscellaneous;


/**
 * @author Itay
 *
 */
public class EmailMessage
{
	String emailAddressFrom;
	
	String emailAddressTo;
	
	String msgSubject = new String();
	
	String msgText = new String();
	
	
	
	/**
	 * @param emailAddressFrom the emailAddressFrom to set
	 */
	public void setFrom(String from)
	{
		this.emailAddressFrom = from;
	}


	/**
	 * @param emailAddressTo the emailAddressTo to set
	 */
	public void setTo(String to)
	{
		this.emailAddressTo = to;
	}


	/**
	 * @param from
	 * @param to
	 */
	public EmailMessage(String from, String to)
	{
		this.emailAddressFrom = from;
		this.emailAddressTo = to;
	}
	
	
	/**
	 * @param subject
	 */
	public void setSubject(String subject)
	{
		if (null == subject) { throw new NullPointerException(
			"the subject of the mail cant be null!"); } //$NON-NLS-1$
		this.msgSubject = subject;
	}
	
	
	/**
	 * @param text
	 */
	public void setText(String text)
	{
		if (null == text) { throw new NullPointerException(
			"the content of the mail cant be null!"); } //$NON-NLS-1$
		this.msgText = text;
	}
	
	
	/**
	 * @return the emailAddressFrom
	 */
	public String getEmailAddressFrom()
	{
		return this.emailAddressFrom;
	}
	
	
	/**
	 * @return the emailAddressTo
	 */
	public String getEmailAddressTo()
	{
		return this.emailAddressTo;
	}
	
	
	/**
	 * @return the msgSubject
	 */
	public String getMsgSubject()
	{
		return this.msgSubject;
	}
	
	
	/**
	 * @return the msgText
	 */
	public String getMsgText()
	{
		return this.msgText;
	}
}
