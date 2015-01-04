
package com.robotwitter.webapp;


import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import com.robotwitter.webapp.view.RobotwitterUI;




/**
 * This is the handler of all client-requests made to robotwitter's server.
 * <p>
 * This class is instantiated once on the application's startup and the instance
 * then becomes available widely throughout the entire application as the
 * application's HTTP request-handler.
 * <p>
 * The initial UI provided to the user is the {@link RobotwitterUI}, which
 * manages the view navigation inside the application.
 */
@WebServlet(
	value = { "/*" },
	asyncSupported = true,
	initParams = { @WebInitParam(
		name = "widgetset",
		value = "com.robotwitter.webapp.RobotwitterWidgetset") })
@VaadinServletConfiguration(productionMode = false, ui = RobotwitterUI.class)
public class Servlet extends VaadinServlet
{
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
}
