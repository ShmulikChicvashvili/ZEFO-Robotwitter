
package com.robotwitter.webapp;


import com.google.inject.Injector;

import com.robotwitter.webapp.ui.AbstractMenu;




/**
 * A creator of views using a Guice {@link Injector}.
 *
 * @author Hagai Akibayov
 */
public class GuiceMenuFactory implements IMenuFactory
{
	/**
	 * Instantiates a new menus factory.
	 *
	 * @param menus
	 *            a mapping of all available menus
	 * @param injector
	 *            should contain resolved menus as dependencies, i.e., using
	 *            {@link Injector#getInstance(Class)} should return a <b>new</b>
	 *            instance of the requested menu component
	 */
	public GuiceMenuFactory(MenuMap menus, Injector injector)
	{
		this.injector = injector;
		this.menus = menus;
	}


	@Override
	public final AbstractMenu create(String name)
	{
		Class<? extends AbstractMenu> menuClass = menus.get(name);
		return injector.getInstance(menuClass);
	}



	/** A Guice injector used to create the view's instances. */
	private final Injector injector;

	/** A mapping of all available menus. */
	MenuMap menus;
}
