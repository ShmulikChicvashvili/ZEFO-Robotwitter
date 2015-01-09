
package com.robotwitter.webapp;


import com.google.inject.AbstractModule;

import com.robotwitter.webapp.control.account.AccountController;
import com.robotwitter.webapp.control.account.IAccountController;




/**
 * A module for resolving all configuration dependencies using Guice.
 *
 * @author Itay Khazon
 */
public class ConfigurationModule extends AbstractModule
{

	@Override
	protected final void configure()
	{
		bind(IAccountController.class).to(AccountController.class);
	}

}
