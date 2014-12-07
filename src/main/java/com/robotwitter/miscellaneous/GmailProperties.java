/**
 *
 */

package com.robotwitter.miscellaneous;


import java.util.Properties;




/**
 * @author Itay
 *
 */
public class GmailProperties
{
	public GmailProperties()
	{}
	
	
	public Properties getGmailProperties()
	{
		final Properties $ = new Properties();
		$.put("mail.smtp.auth", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		$.put("mail.smtp.starttls.enable", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		$.put("mail.smtp.host", "smtp.gmail.com"); //$NON-NLS-1$ //$NON-NLS-2$
		$.put("mail.smtp.port", "25"); //FIXME: possibly need to change back to 587 //$NON-NLS-1$ //$NON-NLS-2$
		return $;
	}
}
