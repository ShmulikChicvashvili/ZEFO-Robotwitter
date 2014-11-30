/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.SQLException;




/**
 * @author Eyal
 *
 */
public interface ConnectionEstablisher
{
	/**
	 * @return a valid SQL connection
	 * @throws SQLException
	 *             Indicating a connection could not be established
	 * @throws ClassNotFoundException
	 *             Indicating the JDBC driver for the connection could not be
	 *             found
	 */
	Connection getConnection() throws SQLException, ClassNotFoundException;


	/**
	 * @return the schema the connection is connected to
	 */
	String getSchema();
}
