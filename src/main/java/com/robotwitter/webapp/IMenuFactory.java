
package com.robotwitter.webapp;


import com.robotwitter.webapp.ui.AbstractMenu;




/**
 * A creator of menu components.
 *
 * @author Hagai Akibayov
 */
public interface IMenuFactory
{
	/**
	 * Creates the requested menu.
	 *
	 * @param name
	 *            the menu's name
	 * @return the newly created menu component
	 */
	AbstractMenu create(String name);
}
