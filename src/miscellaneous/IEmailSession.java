/**
 * 
 */
package miscellaneous;

import javax.mail.Session;

/**
 * @author Itay
 *
 */
public interface IEmailSession
{	
	public void initSession();
	public Session getSession();
}
