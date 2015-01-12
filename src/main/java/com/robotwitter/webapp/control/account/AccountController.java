
package com.robotwitter.webapp.control.account;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseHeavyHitters;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBTwitterAccount;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.twitter.TwitterAppConfiguration;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * A stub implementation for basic testing.
 *
 * @author Hagai Akibayov
 */
public class AccountController implements IAccountController
{

	/**
	 * Instantiates a new account controller stub.
	 *
	 * @param usersDB
	 *            the users database
	 * @param twitterAccountsDB
	 *            the twitter accounts database
	 * @param conf
	 *            the configuration of the application, to communicate with
	 *            twitter
	 */
	@Inject
	public AccountController(
		IDatabaseUsers usersDB,
		IDatabaseTwitterAccounts twitterAccountsDB,
		IDatabaseNumFollowers numFollowersDB,
		IDatabaseHeavyHitters heavyhitterDB,
		IDatabaseFollowers followersDB,
		TwitterAppConfiguration conf)
	{
		this.usersDB = usersDB;
		this.twitterAccountsDB = twitterAccountsDB;
		this.numFollowersDB = numFollowersDB;
		this.heavyhitterDB = heavyhitterDB;
		this.followersDB = followersDB;
		appConnector =
			new TwitterFactory(conf.getAppConfiguration()).getInstance();
		email = null;
		activeTwitterAccount = null;
		twitterAccounts = new HashMap<>();
	}


	@SuppressWarnings("boxing")
	@Override
	public final Status activateTwitterAccount(long id)
	{
		if (email == null) { return null; }
		final ITwitterAccountController account = twitterAccounts.get(id);
		if (account == null) { return Status.TWITTER_ACCOUNT_DOESNT_EXIST; }
		activeTwitterAccount = account;
		return Status.SUCCESS;
	}


	@Override
	public final Status connect(@SuppressWarnings("hiding") String email)
	{
		if (!usersDB.isExists(email)) { return Status.USER_DOESNT_EXIST; }

		this.email = email;
		if (!updateTwitterAccounts())
		{
			this.email = null;
			return Status.FAILURE;
		}
		return Status.SUCCESS;
	}


	@Override
	public final void disconnect()
	{
		email = null;
		activeTwitterAccount = null;
		twitterAccounts = new HashMap<>();
	}


	@Override
	public final ITwitterAccountController getActiveTwitterAccount()
	{
		if (email == null) { return null; }
		return activeTwitterAccount;
	}


	@Override
	public final String getEmail()
	{
		return email;
	}


	@Override
	public final String getName()
	{
		if (email == null) { return null; }
		// FIXME: once we support names in sign up, change this!
		return "Robotwitter account:";
	}


	@Override
	public final Collection<ITwitterAccountController> getTwitterAccounts()
	{
		if (email == null) { return null; }
		if (!updateTwitterAccounts()) { return null; } // FIXME: this askes
														// twitter for the users
														// every time... change
														// this!
		return twitterAccounts.values();
	}


	/**
	 * Update twitter accounts.
	 *
	 * @return true, if successful, false otherwise
	 */
	@SuppressWarnings("boxing")
	private boolean updateTwitterAccounts()
	{
		final ArrayList<DBTwitterAccount> attachedAccounts =
			twitterAccountsDB.get(email);

		if (attachedAccounts == null) { return true; }

		final long[] ids = new long[attachedAccounts.size()];
		for (int i = 0; i < attachedAccounts.size(); i++)
		{
			ids[i] = attachedAccounts.get(i).getUserId();
		}

		ResponseList<User> userList = null;
		try
		{
			userList = appConnector.lookupUsers(ids);
		} catch (final TwitterException e)
		{
			e.printStackTrace();
			return false;
		}
		for (final User user : userList)
		{
			final TwitterAccountController currAccount =
				new TwitterAccountController(
					user.getId(),
					user.getName(),
					user.getScreenName(),
					user.getProfileImageURL(),
					numFollowersDB,
					heavyhitterDB,
					followersDB);
			final TwitterFactory tf =
				new TwitterFactory(
					new TwitterAppConfiguration().getUserConfiguration());
			final TwitterAccount userAccount = new TwitterAccount(tf);
			final Twitter connector = tf.getInstance();
			final DBTwitterAccount account =
				twitterAccountsDB.get(user.getId());
			connector.setOAuthAccessToken(new AccessToken(
				account.getToken(),
				account.getPrivateToken()));
			userAccount.setTwitter(connector);
			userAccount.setAttached(true);
			currAccount.setTwitterAccount(userAccount);
			twitterAccounts.put(currAccount.id, currAccount);
		}

		return true;
	}



	/** The app connector. */
	private final Twitter appConnector;

	/** <code>null</code> if a user isn't connected, else his email. */
	private String email;

	/** The users database. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	private final IDatabaseUsers usersDB;

	/** The twitter accounts database. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	private final IDatabaseTwitterAccounts twitterAccountsDB;

	/** The twitter accounts num of followers database. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	private final IDatabaseNumFollowers numFollowersDB;

	/** The Twitter accounts connected to the user, mapped by their IDs. */
	private Map<Long, ITwitterAccountController> twitterAccounts;

	/** The currently active Twitter account. */
	private ITwitterAccountController activeTwitterAccount;

	/** The database of heavy hitters */
	@SuppressFBWarnings("SE_BAD_FIELD")
	private final IDatabaseHeavyHitters heavyhitterDB;

	/** The database of followers */
	@SuppressFBWarnings("SE_BAD_FIELD")
	private final IDatabaseFollowers followersDB;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
