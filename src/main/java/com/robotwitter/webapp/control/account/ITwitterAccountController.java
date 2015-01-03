
package com.robotwitter.webapp.control.account;


import java.io.Serializable;




/**
 * Controller of a single Twitter account of a user.
 *
 * @author Hagai Akibayov
 */
public interface ITwitterAccountController extends Serializable
{
	
	/** Status codes returned by this instance. */
	enum Status
	{
		/** Operation succeeded. */
		SUCCESS,

		/** An unknown failure occurred . */
		FAILURE
	}
	
	
	
	/** @return The Twitter account's ID. */
	long getID();
	
	
	/** @return The Twitter account's image. */
	String getImage();
	
	
	/** @return The Twitter account's name. */
	String getName();
	
	
	/** @return The Twitter account's screenname. */
	String getScreenname();

}
