/**
 *
 */
package com.robotwitter.database.interfaces;

/**
 * @author Eyal
 *
 */
public interface ReturnValues
{
	@SuppressWarnings("javadoc")
	public enum InsertErrors{
		SUCCESS, ALREADY_EXISTS, INVALID_PARMS
	}
}
