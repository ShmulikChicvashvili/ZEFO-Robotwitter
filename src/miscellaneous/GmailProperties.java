/**
 * 
 */
package miscellaneous;

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
	     $.put("mail.smtp.auth", "true");
	     $.put("mail.smtp.starttls.enable", "true");
	     $.put("mail.smtp.host", "smtp.gmail.com");
	     $.put("mail.smtp.port", "587");
	     return $;
	}
}
