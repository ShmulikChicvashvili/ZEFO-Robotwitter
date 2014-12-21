
package com.robotwitter.webapp;


import java.util.HashMap;

import com.robotwitter.webapp.ui.AbstractMenu;
import com.robotwitter.webapp.ui.MainMenu;




/**
 * Contains a mapping of all accessible menus to their names.
 *
 * @author Hagai Akibayov
 */
public class MenuMap extends HashMap<String, Class<? extends AbstractMenu>>
{
	/** Instantiate a new menu map. */
	public MenuMap()
	{
		put(MainMenu.NAME, MainMenu.class);
	}



	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
