
package com.robotwitter.webapp;


import java.util.HashMap;

import com.vaadin.navigator.View;

import com.robotwitter.webapp.view.analysis.AnalysisView;
import com.robotwitter.webapp.view.connect_twitter.ConnectTwitterView;
import com.robotwitter.webapp.view.dashboard.DashboardView;
import com.robotwitter.webapp.view.login.LoginView;
import com.robotwitter.webapp.view.registration.RegistrationView;
import com.robotwitter.webapp.view.tools.ToolsView;




/**
 * Contains a mapping of all accessible views to their names (which also act as
 * their shebang (#!) URI) in the web-application.
 * <p>
 * It is encouraged to subclass {@link com.robotwitter.webapp.view.AbstractView}
 * which provide a basic view functionality instead of implementing
 * {@link com.vaadin.navigator.View} directly. It is also encouraged to name the
 * view using a <code>public static final String NAME</code> field.
 * <p>
 * In order to add a new view to Robotwitter, the following conditions must be
 * satisfied:
 * <ol>
 * <li>The view is put into this map using the {@link #put} method. This should
 * be done in the constructor {@link #ViewMap()}.
 * <li>A <code>name</code>.properties file should exist in
 * {@link com.robotwitter.webapp.messages}, where <code>name</code> is the
 * view's name.
 * </ol>
 * <p>
 * Optional customisations:
 * <ol>
 * <li>In addition to the default .properties file, a localised one can be
 * supplied. See {@link java.util.Locale} for more information.
 * <li>SCSS styling sheets can be supplied in
 * <i>webapp/META-INF/VAADIN/themes/robotwitter/internal/<code>name</code>
 * /_*.scss</i> where <code>name</code> is the view's name and the .scss files
 * are SCSS mixins. The mixins should then be imported and included into
 * <i>webapp/META-INF/VAADIN/themes/robotwitter/_robotwitter.scss</i>
 * </ol>
 *
 * @author Hagai Akibayov
 */
public class ViewMap extends HashMap<String, Class<? extends View>>
{
	/** Instantiate a new view map. */
	public ViewMap()
	{
		// default view
		put("", LoginView.class);

		// other views. Add additional views here
		put(LoginView.NAME, LoginView.class);
		put(RegistrationView.NAME, RegistrationView.class);
		put(ConnectTwitterView.NAME, ConnectTwitterView.class);
		put(DashboardView.NAME, DashboardView.class);
		put(AnalysisView.NAME, AnalysisView.class);
		put(ToolsView.NAME, ToolsView.class);
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
