/**
 * 
 */

package com.robotwitter.miscellaneous;


/**
 * @author Itay
 * 
 *         The class is used as the return value of void type functions, to
 *         avoid exception based flow.
 */
public class ReturnStatus
{
	/**
	 * @param b the success value of the function
	 * @param string the message to understand the status
	 */
	public ReturnStatus(boolean b, String string)
	{
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the success value of the status
	 */
	public Boolean isSuccess()
	{
		return this.success;
	}
	
	
	/**
	 * @param success
	 *            the success value to set
	 */
	public void setSuccess(Boolean success)
	{
		this.success = success;
	}
	
	
	/**
	 * @return the message of the status
	 */
	public String getMessage()
	{
		return this.message;
	}
	
	
	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	
	
	Boolean success;
	
	String message;
}
