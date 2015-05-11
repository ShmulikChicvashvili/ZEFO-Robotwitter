/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseResponses;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBResponse;




/**
 * @author Itay
 *
 */
public class MySqlDatabaseResponses extends AbstractMySqlDatabase
implements
IDatabaseResponses
{

	private enum Columns
	{
		USER_ID,
		
		RESPONDER_ID,

		RESPONSE_ID,

		DATE,

		TEXT,

		CLASSIFICATION,

		ANSWERED
	}



	/**
	 * @param conEstablisher
	 * @throws SQLException
	 */
	@Inject
	public MySqlDatabaseResponses(ConnectionEstablisher conEstablisher)
		throws SQLException
	{
		super(conEstablisher);
		try (
			Connection con = connectionEstablisher.getConnection();
			Statement statement = con.createStatement())
			{
			final String statementCreate =
				String.format("CREATE TABLE IF NOT EXISTS %s (" //$NON-NLS-1$
					+ "`%s` BIGINT NOT NULL," //$NON-NLS-1$
					+ "`%s` BIGINT NOT NULL," //$NON-NLS-1$
					+ "`%s` BIGINT NOT NULL," //$NON-NLS-1$
					+ "`%s` TIMESTAMP NOT NULL," //$NON-NLS-1$
					+ "`%s` VARCHAR(255) NOT NULL," //$NON-NLS-1$
					+ "`%s` VARCHAR(10) NOT NULL," //$NON-NLS-1$
					+ "`%s` VARCHAR(10) NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`%s`));", //$NON-NLS-1$
					table,
					Columns.USER_ID.toString().toLowerCase(),
					Columns.RESPONDER_ID.toString().toLowerCase(),
					Columns.RESPONSE_ID.toString().toLowerCase(),
					Columns.DATE.toString().toLowerCase(),
					Columns.TEXT.toString().toLowerCase(),
					Columns.CLASSIFICATION.toString().toLowerCase(),
					Columns.ANSWERED.toString().toLowerCase(),
					Columns.RESPONSE_ID.toString().toLowerCase());
			statement.execute(statementCreate);
			}
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseResponses#answer(long) */
	@Override
	public SqlError answer(long responseId)
	{
		if (responseId < 0) { return SqlError.INVALID_PARAMS; }
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement(String.format(
					"UPDATE %s SET %s = ? WHERE %s = ? ",
					table,
					Columns.ANSWERED.toString().toLowerCase(),
					Columns.RESPONSE_ID.toString().toLowerCase())))
					{
			preparedStatement.setString(1, "true");
			preparedStatement.setLong(2, responseId);
			preparedStatement.executeUpdate();
					} catch (final SQLException e)
		{
						e.printStackTrace();
						return SqlError.FAILURE;
		}
		return SqlError.SUCCESS;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseResponses#
	 * deleteResponse(long) */
	@Override
	public SqlError deleteResponse(long responseId)
	{
		if (responseId < 0) { return SqlError.INVALID_PARAMS; }

		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement(""
					+ "DELETE FROM "
					+ table
					+ " WHERE "
					+ Columns.RESPONSE_ID.toString().toLowerCase()
					+ "= ?;"))
					{
			preparedStatement.setLong(1, responseId);
			preparedStatement.executeUpdate();
					} catch (final SQLException e)
		{
						e.printStackTrace();
						return SqlError.FAILURE;
		}

		return SqlError.SUCCESS;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseResponses#get(long) */
	@Override
	public DBResponse get(long responseId)
	{
		DBResponse $ = null;
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement("SELECT * FROM " //$NON-NLS-1$
					+ table
					+ " WHERE " //$NON-NLS-1$
					+ Columns.RESPONSE_ID.toString().toLowerCase()
					+ "=?;")) //$NON-NLS-1$)
					{
			preparedStatement.setLong(1, responseId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				$ = buildResponseFromResultSet();
			}
			resultSet.close();
					} catch (final SQLException e)
		{
						e.printStackTrace();
		}
		return $;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseResponses#
	 * getBadResponsesOfUser(long) */
	@Override
	public ArrayList<DBResponse> getBadResponsesOfUser(long userId)
	{
		final ArrayList<DBResponse> $ = new ArrayList<DBResponse>();
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement("SELECT * FROM " //$NON-NLS-1$
					+ table
					+ " WHERE " //$NON-NLS-1$
					+ Columns.USER_ID.toString().toLowerCase()
					+ "=?"
					+ " AND "
					+ Columns.CLASSIFICATION.toString().toLowerCase()
					+ " =?"
					+ ";")) //$NON-NLS-1$)
					{
			preparedStatement.setLong(1, userId);
			preparedStatement.setString(2, "neg");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				$.add(buildResponseFromResultSet());
			}
			resultSet.close();
					} catch (final SQLException e)
		{
						e.printStackTrace();
		}
		return $;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseResponses#
	 * getResponsesOfUser(long) */
	@Override
	public ArrayList<DBResponse> getResponsesOfUser(long userId)
	{
		final ArrayList<DBResponse> $ = new ArrayList<DBResponse>();
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement("SELECT * FROM " //$NON-NLS-1$
					+ table
					+ " WHERE " //$NON-NLS-1$
					+ Columns.USER_ID.toString().toLowerCase()
					+ "=?"
					+ ";")) //$NON-NLS-1$)
					{
			preparedStatement.setLong(1, userId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				$.add(buildResponseFromResultSet());
			}
			resultSet.close();
					} catch (final SQLException e)
		{
						e.printStackTrace();
		}
		return $;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseResponses#
	 * getUnansweredResponsesOfUser(long) */
	@Override
	public ArrayList<DBResponse> getUnansweredResponsesOfUser(long userId)
	{
		final ArrayList<DBResponse> $ = new ArrayList<DBResponse>();
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement("SELECT * FROM " //$NON-NLS-1$
					+ table
					+ " WHERE " //$NON-NLS-1$
					+ Columns.USER_ID.toString().toLowerCase()
					+ "=?"
					+ " AND "
					+ Columns.ANSWERED.toString().toLowerCase()
					+ " =?"
					+ ";")) //$NON-NLS-1$)
					{
			preparedStatement.setLong(1, userId);
			preparedStatement.setString(2, "false");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				$.add(buildResponseFromResultSet());
			}
			resultSet.close();
					} catch (final SQLException e)
		{
						e.printStackTrace();
		}
		return $;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseResponses#
	 * insert(com.robotwitter.database.primitives.DBResponse) */
	@Override
	public SqlError insert(DBResponse response)
	{
		if (response == null
			|| response.getAnswered() == null
			|| response.getClassify() == null
			|| response.getId() < 0
			|| response.getResponderID() < 0
			|| response.getText() == null
			|| response.getUserID() < 0
			|| response.getTimestamp() == null) { return SqlError.INVALID_PARAMS; }
		try (
			Connection con = connectionEstablisher.getConnection();
			@SuppressWarnings("nls")
			PreparedStatement preparedStatement =
			con.prepareStatement("INSERT INTO "
				+ table
				+ " ("
				+ Columns.USER_ID.toString().toLowerCase()
				+ ","
				+ Columns.RESPONSE_ID.toString().toLowerCase()
				+ ","
				+ Columns.RESPONDER_ID.toString().toLowerCase()
				+ ","
				+ Columns.DATE.toString().toLowerCase()
				+ ","
				+ Columns.TEXT.toString().toLowerCase()
				+ ","
				+ Columns.CLASSIFICATION.toString().toLowerCase()
				+ ","
				+ Columns.ANSWERED.toString().toLowerCase()
				+ ") VALUES ( ?, ?, ?, ?, ?, ?, ? );"))
				{
			preparedStatement.setLong(1, response.getUserID());
			preparedStatement.setLong(2, response.getResponderID());
			preparedStatement.setLong(3, response.getId());
			preparedStatement.setTimestamp(4, response.getTimestamp());
			preparedStatement.setString(5, response.getText());
			preparedStatement.setString(6, response.getClassify());
			preparedStatement.setString(7, response.getAnswered().toString());
			preparedStatement.executeUpdate();

				} catch (final SQLException e)
		{
					if (e.getErrorCode() == insertAlreadyExists) { return SqlError.ALREADY_EXIST; }
					e.printStackTrace();
					return SqlError.FAILURE;
		}
		return SqlError.SUCCESS;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	private DBResponse buildResponseFromResultSet() throws SQLException
	{
		return new DBResponse(resultSet.getLong(Columns.USER_ID
			.toString()
			.toLowerCase()), resultSet.getLong(Columns.RESPONDER_ID
			.toString()
			.toLowerCase()), resultSet.getLong(Columns.RESPONSE_ID
			.toString()
			.toLowerCase()), resultSet.getTimestamp(Columns.DATE
			.toString()
			.toLowerCase()), resultSet.getString(Columns.TEXT
			.toString()
			.toLowerCase()), resultSet.getString(Columns.CLASSIFICATION
			.toString()
			.toLowerCase()), resultSet.getString(
			Columns.ANSWERED.toString().toLowerCase()).equals("true"));
	}



	private final String table = schema + "." + "`responses`";

}
