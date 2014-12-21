
package com.robotwitter.webapp.control.account;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;




/**
 * A stub implementation for basic testing.
 *
 * @author Hagai Akibayov
 */
public class AccountControllerStub implements IAccountController
{

	/** Instantiates a new account controller stub. */
	public AccountControllerStub()
	{
		isConnnected = false;
		activeTwitterAccount = null;
		twitterAccounts = new HashMap<>();
		initialiseStubTwitterAccount();
	}


	@Override
	public final Status activateTwitterAccount(String screenname)
	{
		TwitterAccount account = twitterAccounts.get(screenname);
		if (account == null) { return Status.TWITTER_ACCOUNT_DOESNT_EXIST; }
		activeTwitterAccount = account;
		return Status.SUCCESS;
	}
	
	
	/**
	 * The only existing user in the stub is {@link #EMAIL}.
	 * <p>
	 * If <code>email</code> equals <code>"FAILURE"</code>,
	 * {@link IAccountController.Status#FAILURE} will be returned.
	 */
	@Override
	public final Status connect(String email)
	{
		if (EMAIL.equals(Status.FAILURE.name())) { return Status.FAILURE; }
		if (!EMAIL.equals(email)) { return Status.USER_DOESNT_EXIST; }
		isConnnected = true;
		return Status.SUCCESS;
	}


	@Override
	public final void disconnect()
	{
		isConnnected = false;
	}
	
	
	@Override
	public final TwitterAccount getActiveTwitterAccount()
	{
		return activeTwitterAccount;
	}
	
	
	@Override
	public final String getEmail()
	{
		if (!isConnnected) { return null; }
		return EMAIL;
	}


	@Override
	public final String getName()
	{
		if (!isConnnected) { return null; }
		return NAME;
	}


	@Override
	public final Collection<TwitterAccount> getTwitterAccounts()
	{
		return twitterAccounts.values();
	}


	/** Initialises a couple of stub twitter accounts. */
	private void initialiseStubTwitterAccount()
	{
		TwitterAccount acc = new TwitterAccount();
		acc.name = "Don Akibayov"; //$NON-NLS-1$
		acc.screenname = "DonAkibayov"; //$NON-NLS-1$
		acc.image =
			"https://lh6.googleusercontent.com/-JSKRzgV58v8/AAAAAAAAAAI/AAAAAAAAAAA/p38p2EqFFYA/s128-c-k/photo.jpg"; //$NON-NLS-1$
		twitterAccounts.put(acc.screenname, acc);
		
		acc = new TwitterAccount();
		acc.name = "Michael Jackson"; //$NON-NLS-1$
		acc.screenname = "michaeljackson"; //$NON-NLS-1$
		acc.image =
			"https://pbs.twimg.com/profile_images/450619412718944256/H7EZ4ME1.jpeg"; //$NON-NLS-1$
		twitterAccounts.put(acc.screenname, acc);
		
		acc = new TwitterAccount();
		acc.name = "Albus Dumbledore"; //$NON-NLS-1$
		acc.screenname = "Dumbledore77"; //$NON-NLS-1$
		acc.image =
			"https://pbs.twimg.com/profile_images/1113126171/ALBUSSSS.png"; //$NON-NLS-1$
		twitterAccounts.put(acc.screenname, acc);
		
		acc = new TwitterAccount();
		acc.name = "Fuck My Life"; //$NON-NLS-1$
		acc.screenname = "FuckUrLife"; //$NON-NLS-1$
		acc.image =
			"https://abs.twimg.com/sticky/default_profile_images/default_profile_6_200x200.png"; //$NON-NLS-1$
		twitterAccounts.put(acc.screenname, acc);
	}



	/** The user's email. */
	private static final String EMAIL = "hesosz@gmail.com"; //$NON-NLS-1$

	/** The user's name. */
	private static final String NAME = "Hagai Akibayov"; //$NON-NLS-1$
	
	/** The Twitter accounts connected to the user, mapped by their screennames. */
	private Map<String, TwitterAccount> twitterAccounts;

	/** The currently active Twitter account. */
	private TwitterAccount activeTwitterAccount;

	/** <code>true</code> if a user is connected. */
	boolean isConnnected;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
