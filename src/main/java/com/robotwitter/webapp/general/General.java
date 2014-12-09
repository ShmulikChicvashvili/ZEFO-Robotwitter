
package com.robotwitter.webapp.general;


/**
 * Contains static constant definitions relevant to this package.
 *
 * @author Hagai Akibayov
 */
public final class General
{
	/**
	 * Prohibit instantiation.
	 *
	 * @throws Exception
	 *             always thrown
	 */
	private General() throws Exception
	{
		throw new Exception("instantiation of class " //$NON-NLS-1$
			+ this.getClass().getName()
			+ " is illegal"); //$NON-NLS-1$
	}



	/**
	 * The name of the general messages container.
	 *
	 * @see com.robotwitter.webapp.messages.IMessagesProvider
	 */
	public static final String MESSAGES = "general"; //$NON-NLS-1$
}
