/**
 * 
 */
package com.Robotwitter.miscellaneous;

import java.util.Properties;

/**
 * @author Itay
 *
 */
public class GmailProperties
{	
	public GmailProperties() {}
	
	public Properties getGmailProperties() {
		 Properties $ = new Properties();
	     $.put("mail.smtp.auth", "true"); //$NON-NLS-1$ //$NON-NLS-2$
	     $.put("mail.smtp.starttls.enable", "true"); //$NON-NLS-1$ //$NON-NLS-2$
	     $.put("mail.smtp.host", "smtp.gmail.com"); //$NON-NLS-1$ //$NON-NLS-2$
	     $.put("mail.smtp.port", "587"); //$NON-NLS-1$ //$NON-NLS-2$
	     return $;
	}
}
