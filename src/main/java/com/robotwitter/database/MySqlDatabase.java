/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.robotwitter.database.interfaces.ConnectionEstablisher;




/**
 * @author Shmulik
 *
 */
public abstract class MySqlDatabase implements AutoCloseable
{
	/**
	 * C'tor of general settings
	 * 
	 * @param conEstablisher
	 *            A connection establisher for the database
	 */
	public MySqlDatabase(final ConnectionEstablisher conEstablisher)
	{
		this.connectionEstablisher = conEstablisher;
		this.schema = this.connectionEstablisher.getSchema();
	}
	
	
	
	/**
	 * connection handler
	 */
	protected Connection con = null;

	/**
	 * statement handler
	 */
	protected Statement statement = null;

	/**
	 * preparedStatement handler
	 */
	protected PreparedStatement preparedStatement = null;

	/**
	 * resultSet handler
	 */
	protected ResultSet resultSet = null;

	/**
	 * The database name
	 */
	protected final String schema;
	
	/**
	 * The connection establisher with the database
	 */
	protected final ConnectionEstablisher connectionEstablisher;
	
	/**
	 * Added in 3.12.14 By Shmulik
	 * 
	 * Will close the whole connection to the database
	 */
	protected void CloseConnection() {
		if(this.con != null) {
			try
			{
				this.con.close();
			} catch (SQLException e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		if(this.statement != null) {
			try
			{
				this.statement.close();
			} catch (SQLException e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		if(this.preparedStatement != null) {
			try
			{
				this.preparedStatement.close();
			} catch (SQLException e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		if(this.resultSet != null) {
			try
			{
				this.resultSet.close();
			} catch (SQLException e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
	}

	/* (non-Javadoc) @see java.lang.AutoCloseable#close() */
	@Override
	public void close() throws Exception
	{
		if(this.con != null) {
			try
			{
				this.con.close();
			} catch (SQLException e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		if(this.statement != null) {
			try
			{
				this.statement.close();
			} catch (SQLException e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		if(this.preparedStatement != null) {
			try
			{
				this.preparedStatement.close();
			} catch (SQLException e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		if(this.resultSet != null) {
			try
			{
				this.resultSet.close();
			} catch (SQLException e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
	}
	
}
