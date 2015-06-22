
package com.robotwitter.webapp;


import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import com.robotwitter.webapp.view.DesktopUI;




/**
 * The initial UI provided to the desktop-user is the {@link DesktopUI}.
 */
@WebServlet(
	value = { "/desktop/*", "/VAADIN/*" },
	asyncSupported = true,
	initParams = { @WebInitParam(
		name = "widgetset",
		value = "com.robotwitter.webapp.RobotwitterWidgetset") })
@VaadinServletConfiguration(productionMode = false, ui = DesktopUI.class)
public class DesktopServlet extends VaadinServlet
{
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
