
package com.robotwitter.webapp.view;


import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import com.robotwitter.webapp.view.login.LoginView;




/**
 * Servlet implementation class RobotwitterServlet
 */
@WebServlet(value = { "/login/*", "/VAADIN/*" }, asyncSupported = true)
@WebListener()
@VaadinServletConfiguration(productionMode = false, ui = LoginView.class)
public class RobotwitterServlet extends VaadinServlet
{
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
