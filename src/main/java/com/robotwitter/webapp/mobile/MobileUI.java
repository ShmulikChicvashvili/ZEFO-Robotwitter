
package com.robotwitter.webapp.mobile;


import com.vaadin.annotations.Viewport;

import com.robotwitter.webapp.menu.MobileMainMenu;
import com.robotwitter.webapp.util.AbstractUI;




/**
 * Represents the single container of all UI elements.
 * <p>
 * An instance of this class is created by the
 * {@link com.robotwitter.webapp.MobileServlet} when a user-connection is made,
 * and then the UI instance is available only to that user for the session
 * (Closing browser, losing connection for more than a predefined timeout, etc).
 * Basically, every user communicates with a single UI instance.
 *
 * @author Hagai Akibayov
 */
@Viewport("width=device-width; initial-scale=1.0; maximum-scale=1.0;")
public class MobileUI extends AbstractUI
{

	/** Instantiates a new mobile UI. */
	public MobileUI()
	{
		super(MobileMainMenu.NAME);
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
