/**
 * 
 */
package com.robotwitter.management;

import com.robotwitter.management.EmailPasswordRetriever.ReturnStatus;

/**
 * @author Itay
 *
 */
public interface IEmailPasswordRetriever
{	
	public ReturnStatus retrievePasswordByMail(final String userEmail);
}
