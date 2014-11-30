
package com.robotwitter.webapp.view.login;


import com.vaadin.data.validator.AbstractStringValidator;




/** Validator of a user's password. */
public class PasswordValidator extends AbstractStringValidator
{

	/** Construct a password validator. */
	public PasswordValidator()
	{
		super(null);
	}
	
	
	@Override
	protected boolean isValidValue(final String password)
	{
		if (password.isEmpty()) { return true; }

		if (password.length() < 8)
		{
			setErrorMessage(Messages.get("LoginView.error.password-must-be-longer-than-8")); //$NON-NLS-1$
			return false;
		} else if (password.length() > 20)
		{
			setErrorMessage(Messages.get("LoginView.error.password-must-be-shorter-than-20")); //$NON-NLS-1$
			return false;
		} else if (!password.matches(".*[0-9].*")) //$NON-NLS-1$
		{
			setErrorMessage(Messages.get("LoginView.error.password-must-contain-digit")); //$NON-NLS-1$
			return false;
		} else if (!password.matches(".*[a-z].*")) //$NON-NLS-1$
		{
			setErrorMessage(Messages.get("LoginView.error.password-must-contain-lowercase-letter")); //$NON-NLS-1$
			return false;
		} else if (!password.matches(".*[A-Z].*")) //$NON-NLS-1$
		{
			setErrorMessage(Messages.get("LoginView.error.password-must-contain-uppercase-letter")); //$NON-NLS-1$
			return false;
		}
		return true;
	}



	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
