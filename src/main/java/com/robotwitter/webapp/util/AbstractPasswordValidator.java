
package com.robotwitter.webapp.util;


/**
 * Represents an abstract password field validator.
 * <p>
 * Used to differentiate between a simple text-field validator (
 * {@link AbstractTextFieldValidator}) and the more specialised password field
 * validator.
 *
 * @author Hagai Akibayov
 */
public abstract class AbstractPasswordValidator
extends
AbstractTextFieldValidator
{
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
