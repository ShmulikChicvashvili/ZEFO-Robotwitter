/**
 * 
 */

package miscellaneous;


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
	
	
	
	public EmailMessage(String from, String to)
	{
		this.emailAddressFrom = from;
		this.emailAddressTo = to;
	}
	
	
	public void setSubject(String subject)
	{
		if (null == subject) { throw new NullPointerException(
			"the subject of the mail cant be null!"); }
		this.msgSubject = subject;
	}
	
	
	public void setText(String text)
	{
		if (null == text) { throw new NullPointerException(
			"the content of the mail cant be null!"); }
		this.msgText = text;
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
}
