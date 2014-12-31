package com.robotwitter.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBFollower;
import com.robotwitter.database.primitives.DBUser;

/**
 * @author Amir and Shmulik
 */

public class MySqlDatabaseFollowers extends AbstractMySqlDatabase implements
		IDatabaseFollowers {

	private enum Columns {
		/**
		 * Email column.
		 */
		FOLLOWER_ID, FOLLOWED_ID,
		/**
		 * Password column.
		 */
		NAME, SCREEN_NAME, DESCRIPTION, FOLLOWERS, FOLLOWING, LOCATION, FAVORITES, LANGUAGE, IS_CELEBRITY, JOINED
	}

	public MySqlDatabaseFollowers(final ConnectionEstablisher conEstablisher)
			throws SQLException {
		super(conEstablisher);
		try (Connection con = connectionEstablisher.getConnection();
				Statement statement = (Statement) con.createStatement()) {
			final String statementCreateFollowers = String.format(
					"CREATE TABLE IF NOT EXISTS %s (" //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "PRIMARY KEY (`%s`));", //$NON-NLS-1$
					followersTable, Columns.FOLLOWER_ID.toString()
							.toLowerCase(), Columns.NAME.toString()
							.toLowerCase(), Columns.SCREEN_NAME.toString()
							.toLowerCase(), Columns.DESCRIPTION.toString()
							.toLowerCase(), Columns.FOLLOWERS.toString()
							.toLowerCase(), Columns.FOLLOWING.toString()
							.toLowerCase(), Columns.LOCATION.toString()
							.toLowerCase(), Columns.FAVORITES.toString()
							.toLowerCase(), Columns.LANGUAGE.toString()
							.toLowerCase(), Columns.IS_CELEBRITY.toString()
							.toLowerCase(), Columns.JOINED.toString()
							.toLowerCase(), Columns.FOLLOWER_ID.toString()
							.toLowerCase());
			final String statementCreateFollowing = String.format(
					"CREATE TABLE IF NOT EXISTS %s (" //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "`%s` TINYTEXT NOT NULL," //$NON-NLS-1$
							+ "PRIMARY KEY (`%s` , `%s`));", //$NON-NLS-1$
					followingTable, Columns.FOLLOWER_ID.toString()
							.toLowerCase(), Columns.FOLLOWED_ID.toString()
							.toLowerCase(), Columns.FOLLOWER_ID.toString()
							.toLowerCase(), Columns.FOLLOWED_ID.toString()
							.toLowerCase());
			statement.execute(statementCreateFollowers);
			statement.execute(statementCreateFollowing);
		}
	}

	/*
	 * (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseFollowers
	 * #get(com.robotwitter.database.primitives.DBFollower)
	 */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public DBFollower get(long twitterId) {
		DBFollower $ = null;
		try (Connection con = connectionEstablisher.getConnection();
				PreparedStatement preparedStatement = con
						.prepareStatement("SELECT * FROM " //$NON-NLS-1$
								+ followersTable
								+ " WHERE " //$NON-NLS-1$
								+ Columns.FOLLOWER_ID.toString().toLowerCase()
								+ "=?;")) //$NON-NLS-1$)
		{
			preparedStatement.setLong(1, twitterId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				$ = new DBFollower(resultSet.getLong(Columns.FOLLOWER_ID
						.toString().toLowerCase()),
						resultSet.getString(Columns.NAME.toString()
								.toLowerCase()),
						resultSet.getString(Columns.SCREEN_NAME.toString()
								.toLowerCase()),
						resultSet.getString(Columns.DESCRIPTION.toString()
								.toLowerCase()),
						resultSet.getInt(Columns.FOLLOWERS.toString()
								.toLowerCase()),
						resultSet.getInt(Columns.FOLLOWING.toString()
								.toLowerCase()),
						resultSet.getString(Columns.LOCATION.toString()
								.toLowerCase()),
						resultSet.getInt(Columns.FAVORITES.toString()
								.toLowerCase()),
						resultSet.getString(Columns.LANGUAGE.toString()
								.toLowerCase()),
						resultSet.getBoolean(Columns.IS_CELEBRITY.toString()
								.toLowerCase()),
						resultSet.getTimestamp(Columns.JOINED.toString()
								.toLowerCase()));
			}
			resultSet.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return $;
	}
	
	/*
	 * (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseFollowers
	 * #getFollowersIds(com.robotwitter.database.primitives.DBFollower)
	 */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public ArrayList<Long> getFollowersId(String userId) {
		ArrayList<Long> $ = new ArrayList<Long>();
		try (Connection con = connectionEstablisher.getConnection();
				PreparedStatement preparedStatement = con
						.prepareStatement("SELECT * FROM " //$NON-NLS-1$
								+ followingTable + " WHERE " //$NON-NLS-1$
								+ Columns.FOLLOWED_ID.toString().toLowerCase() + "=?;")) //$NON-NLS-1$)
		{
			preparedStatement.setString(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				$.add(resultSet.getLong(Columns.FOLLOWER_ID.toString().toLowerCase()));
			}
			resultSet.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return $;
	}

	/*
	 * (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseFollowers
	 * #getByName(com.robotwitter.database.primitives.DBFollower)
	 */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public ArrayList<DBFollower> getByName(String name) {
		ArrayList<DBFollower> $ = new ArrayList<DBFollower>();
		try (Connection con = connectionEstablisher.getConnection();
				PreparedStatement preparedStatement = con
						.prepareStatement("SELECT * FROM " //$NON-NLS-1$
								+ followersTable + " WHERE " //$NON-NLS-1$
								+ Columns.NAME.toString().toLowerCase() + "=?;")) //$NON-NLS-1$)
		{
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				$.add(new DBFollower(resultSet.getLong(Columns.FOLLOWER_ID
						.toString().toLowerCase()), resultSet
						.getString(Columns.NAME.toString().toLowerCase()),
						resultSet.getString(Columns.SCREEN_NAME.toString()
								.toLowerCase()), resultSet
								.getString(Columns.DESCRIPTION.toString()
										.toLowerCase()), resultSet
								.getInt(Columns.FOLLOWERS.toString()
										.toLowerCase()), resultSet
								.getInt(Columns.FOLLOWING.toString()
										.toLowerCase()), resultSet
								.getString(Columns.LOCATION.toString()
										.toLowerCase()), resultSet
								.getInt(Columns.FAVORITES.toString()
										.toLowerCase()), resultSet
								.getString(Columns.LANGUAGE.toString()
										.toLowerCase()), resultSet
								.getBoolean(Columns.IS_CELEBRITY.toString()
										.toLowerCase()), resultSet
								.getTimestamp(Columns.JOINED.toString()
										.toLowerCase())));
			}
			resultSet.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return $;
	}

	/*
	 * (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseFollowers
	 * #getByScreenName(com.robotwitter.database.primitives.DBFollower)
	 */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public ArrayList<DBFollower> getByScreenName(String screenName) {
		ArrayList<DBFollower> $ = new ArrayList<DBFollower>();
		try (Connection con = connectionEstablisher.getConnection();
				PreparedStatement preparedStatement = con
						.prepareStatement("SELECT * FROM " //$NON-NLS-1$
								+ followersTable
								+ " WHERE " //$NON-NLS-1$
								+ Columns.SCREEN_NAME.toString().toLowerCase()
								+ "=?;")) //$NON-NLS-1$)
		{
			preparedStatement.setString(1, screenName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				$.add(new DBFollower(resultSet.getLong(Columns.FOLLOWER_ID
						.toString().toLowerCase()), resultSet
						.getString(Columns.NAME.toString().toLowerCase()),
						resultSet.getString(Columns.SCREEN_NAME.toString()
								.toLowerCase()), resultSet
								.getString(Columns.DESCRIPTION.toString()
										.toLowerCase()), resultSet
								.getInt(Columns.FOLLOWERS.toString()
										.toLowerCase()), resultSet
								.getInt(Columns.FOLLOWING.toString()
										.toLowerCase()), resultSet
								.getString(Columns.LOCATION.toString()
										.toLowerCase()), resultSet
								.getInt(Columns.FAVORITES.toString()
										.toLowerCase()), resultSet
								.getString(Columns.LANGUAGE.toString()
										.toLowerCase()), resultSet
								.getBoolean(Columns.IS_CELEBRITY.toString()
										.toLowerCase()), resultSet
								.getTimestamp(Columns.JOINED.toString()
										.toLowerCase())));
			}
			resultSet.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return $;
	}

	/*
	 * (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseFollowers
	 * #insert(com.robotwitter.database.primitives.DBFollower)
	 */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public SqlError insert(DBFollower follower) {
		if (follower == null || follower.getName() == null
				|| follower.getScreenName() == null
				|| follower.getDescription() == null
				|| follower.getLocation() == null
				|| follower.getLanguage() == null
				|| follower.getJoined() == null) {
			return SqlError.INVALID_PARAMS;
		}
		try (Connection con = connectionEstablisher.getConnection();
				@SuppressWarnings("nls")
				PreparedStatement preparedStatement = (PreparedStatement) con
						.prepareStatement("INSERT INTO " + followersTable
								+ " ("
								+ Columns.FOLLOWER_ID.toString().toLowerCase()
								+ "," + Columns.NAME.toString().toLowerCase()
								+ ","
								+ Columns.SCREEN_NAME.toString().toLowerCase()
								+ ","
								+ Columns.FOLLOWERS.toString().toLowerCase()
								+ ","
								+ Columns.FOLLOWING.toString().toLowerCase()
								+ ","
								+ Columns.LOCATION.toString().toLowerCase()
								+ ","
								+ Columns.FAVORITES.toString().toLowerCase()
								+ ","
								+ Columns.LANGUAGE.toString().toLowerCase()
								+ ","
								+ Columns.IS_CELEBRITY.toString().toLowerCase()
								+ "," + Columns.JOINED.toString().toLowerCase()
								+ ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? );")) {
			preparedStatement.setLong(1, follower.getFollowerId());
			preparedStatement.setString(2, follower.getName());
			preparedStatement.setString(3, follower.getScreenName());
			preparedStatement.setInt(4, follower.getFollowers());
			preparedStatement.setInt(5, follower.getFollowing());
			preparedStatement.setString(6, follower.getLocation());
			preparedStatement.setInt(7, follower.getFavorites());
			preparedStatement.setBoolean(8, follower.getIsCelebrity());
			preparedStatement.setTimestamp(9, follower.getJoined());
			preparedStatement.executeUpdate();

		} catch (final SQLException e) {
			if (e.getErrorCode() == insertAlreadyExists) {
				return SqlError.ALREADY_EXIST;
			}
			e.printStackTrace();
		}
		return SqlError.SUCCESS;
	}
	
	/*
	 * (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseFollowers
	 * #insert(com.robotwitter.database.primitives.DBFollower)
	 */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public SqlError insert(long userId, long followerId) {
		if (userId < 1 || followerId < 1) {
			return SqlError.INVALID_PARAMS;
		}
		try (Connection con = connectionEstablisher.getConnection();
				@SuppressWarnings("nls")
				PreparedStatement preparedStatement = (PreparedStatement) con
						.prepareStatement("INSERT INTO " + followingTable
								+ " ("
								+ Columns.FOLLOWER_ID.toString().toLowerCase()
								+ "," 
								+ Columns.FOLLOWED_ID.toString().toLowerCase()
								+ ") VALUES ( ?, ?);")) {
			preparedStatement.setLong(1, followerId);
			preparedStatement.setLong(2, userId);
			preparedStatement.executeUpdate();

		} catch (final SQLException e) {
			if (e.getErrorCode() == insertAlreadyExists) {
				return SqlError.ALREADY_EXIST;
			}
			e.printStackTrace();
		}
		return SqlError.SUCCESS;
	}

	/*
	 * (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseFollowers
	 * #insert(com.robotwitter.database.primitives.DBFollower)
	 */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public boolean isExistsByName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseFollowers
	 * #insert(com.robotwitter.database.primitives.DBFollower)
	 */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public boolean isExistsByScreenName(String ScreenName) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseFollowers
	 * #insert(com.robotwitter.database.primitives.DBFollower)
	 */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public SqlError update(DBFollower follower) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * The table name.
	 */
	private final String followingTable = schema + "." + "`following`"; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The table name.
	 */
	private final String followersTable = schema + "." + "`followers`"; //$NON-NLS-1$ //$NON-NLS-2$
}
