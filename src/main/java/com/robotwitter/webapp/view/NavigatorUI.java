
package com.robotwitter.webapp.view;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import com.robotwitter.webapp.Configuration;




/**
 * The view navigator which managers transitions between views.
 * <p>
 * An instance of this class is created by the
 * {@link com.robotwitter.webapp.Servlet} when a client-connection is made, and
 * then the UI instance is available only to that client for the client's usage
 * session (Closing browser, losing connection for more than a pre-defined
 * timout, etc).
 *
 * @author Hagai Akibayov
 */
@Theme("robotwitter")
public class NavigatorUI extends UI
{
	@Override
	protected final void init(VaadinRequest request)
	{
		navigator = new Navigator(this, this);
		
		viewFactory =
			(ViewProvider) VaadinServlet
				.getCurrent()
				.getServletContext()
				.getAttribute(Configuration.VIEW_FACTORY);

		navigator.addProvider(viewFactory);
	}



	/** The view factory, used for creation of views during this session. */
	ViewProvider viewFactory;
	
	/** The navigator that controls view-transition inside the application. */
	Navigator navigator;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
}
