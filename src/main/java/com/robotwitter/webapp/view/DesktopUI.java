
package com.robotwitter.webapp.view;


import com.robotwitter.webapp.util.AbstractUI;




/**
 * Represents the single container of all UI elements.
 * <p>
 * An instance of this class is created by the
 * {@link com.robotwitter.webapp.DesktopServlet} when a user-connection is made,
 * and then the UI instance is available only to that user for the session
 * (Closing browser, losing connection for more than a predefined timeout, etc).
 * Basically, every user communicates with a single UI instance.
 *
 * @author Hagai Akibayov
 */
public class DesktopUI extends AbstractUI
{
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
