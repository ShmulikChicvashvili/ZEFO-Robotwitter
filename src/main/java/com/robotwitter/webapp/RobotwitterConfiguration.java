
package com.robotwitter.webapp;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.robotwitter.webapp.messages.MessagesProvider;




/**
 * Represents the web-application configuration. This object is initialised once
 * on the application's startup and is available widely throughout the entire
 * application as a singleton.
 *
 * @author Hagai Akibayov
 */
@WebListener
public class RobotwitterConfiguration implements ServletContextListener
{
	
	/**
	 * Initialises the messages provider of this application.
	 *
	 * @param context
	 *            the servlet's context
	 */
	private static void initialiseMessagesProvider(ServletContext context)
	{
		context.setAttribute(MESSAGES_PROVIDER, new MessagesProvider());
	}
	
	
	@Override
	public final void contextDestroyed(ServletContextEvent event)
	{
		// Do nothing
	}


	@Override
	public final void contextInitialized(ServletContextEvent event)
	{
		final ServletContext context = event.getServletContext();
		initialiseMessagesProvider(context);
	}



	/** The messages provider attribute. */
	static final String MESSAGES_PROVIDER = "MessagesProvider"; //$NON-NLS-1$
}
