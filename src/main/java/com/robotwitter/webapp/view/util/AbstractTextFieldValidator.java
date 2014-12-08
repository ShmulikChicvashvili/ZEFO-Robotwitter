
package com.robotwitter.webapp.view.util;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.vaadin.data.validator.AbstractStringValidator;




/**
 * Represents an abstract text-field validator, configured using
 * regular-expression constraints.
 */
public abstract class AbstractTextFieldValidator
	extends
		AbstractStringValidator
{

	/** Instantiates a new abstract text-field validator. */
	public AbstractTextFieldValidator()
	{
		super(null);
		setMinLength(0, null);
		setMinLength(Integer.MAX_VALUE, null);
		constraints = new LinkedHashMap<>();
	}
	
	
	/**
	 * Adds a regular-expression validity constraint on the input.
	 *
	 * @param constraint
	 *            a regular-expression that acts as a constraint.
	 * @param error
	 *            the error message of an input that does not satisfy the
	 *            constraint
	 */
	public final void addConstraint(String constraint, String error)
	{
		constraints.put(Pattern.compile(constraint), error);
	}
	
	
	/**
	 * Sets the maximal valid input length.
	 *
	 * @param length
	 *            the maximal valid length
	 * @param error
	 *            the error message of an input that is longer than the given
	 *            length
	 */
	public final void setMaxLength(int length, String error)
	{
		maxLength = length;
		tooLongError = error;
	}
	
	
	/**
	 * Sets the minimal valid input length.
	 *
	 * @param length
	 *            the minimal valid length
	 * @param error
	 *            the error message of an input that is shorter than the given
	 *            length
	 */
	public final void setMinLength(int length, String error)
	{
		minLength = length;
		tooShortError = error;
	}
	
	
	@Override
	protected final boolean isValidValue(final String input)
	{
		if (input.isEmpty()) { return true; }

		// Validate minimal length
		if (input.length() < minLength)
		{
			setErrorMessage(tooShortError);
			return false;
		}

		// Validate maximal length
		if (input.length() > maxLength)
		{
			setErrorMessage(tooLongError);
			return false;
		}
		
		// Validate regular-expression constraints
		for (final Map.Entry<Pattern, String> entry : constraints.entrySet())
		{
			final Pattern constraint = entry.getKey();
			final String error = entry.getValue();
			if (!constraint.matcher(input).matches())
			{
				setErrorMessage(error);
				return false;
			}
		}

		return true;
	}
	
	
	
	/** The minimal valid input length. */
	int minLength;

	/** The maximal valid input length. */
	int maxLength;

	/** The error message when the input is shorter than {@link #minLength}. */
	String tooShortError;
	
	/** The error message when the input is longer than {@link #maxLength}. */
	String tooLongError;

	/**
	 * A mapping from regular-expressions which act as validity constraints on
	 * the input to their error messages.
	 */
	Map<Pattern, String> constraints;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
