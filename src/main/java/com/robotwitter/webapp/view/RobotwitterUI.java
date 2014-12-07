
package com.robotwitter.webapp.view;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;




/**
 * The view navigator which managers transitions between views. An instance of
 * this class is also created by the
 * {@link com.robotwitter.webapp.RobotwitterServlet} when a request for a UI is
 * made.
 *
 * @author Hagai Akibayov
 */
@Theme("robotwitter")
public class RobotwitterUI extends UI
{

	/**
	 * Adds a view to the navigator.
	 *
	 * @param view
	 *            the view to add to the navigator
	 */
	public final void add(AbstractView view)
	{
		navigator.addView(view.getName(), view);
	}
	
	
	@Override
	protected final void init(VaadinRequest request)
	{
		navigator = new Navigator(this, this);
	}
	
	
	
	/** The navigator that controls view-transition inside the application. */
	Navigator navigator;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
