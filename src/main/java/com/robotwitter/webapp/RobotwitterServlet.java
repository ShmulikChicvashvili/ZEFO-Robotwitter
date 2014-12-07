
package com.robotwitter.webapp;


import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import com.robotwitter.webapp.view.RobotwitterUI;




/** Robotwitter's Index page servlet. */
@WebServlet(value = { "/*" }, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = RobotwitterUI.class)
public class RobotwitterServlet extends VaadinServlet
{
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
