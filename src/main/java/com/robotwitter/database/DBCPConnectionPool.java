/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.robotwitter.database.interfaces.ConnectionPool;




/**
 * @author Shmulik and Amir
 *
 */
public class DBCPConnectionPool implements ConnectionPool
{
	@Inject
	public DBCPConnectionPool(
		@Named("DB Driver") final String driverName,
		@Named("DB Username") final String userName,
		@Named("DB Password") final String password,
		@Named("DB URL") final String url) throws SQLException
	{
		this.driverName = driverName;
		this.userName = userName;
		this.password = password;
		this.url = url;

		buildDataSource();
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.ConnectionPool#getConnection() */
	@Override
	public Connection getConnection() throws SQLException
	{
		return dataSource.getConnection();
	}
	
	
	private void buildDataSource()
	{
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverName);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
		
		dataSource.setMinIdle(5);
		dataSource.setMaxIdle(20);
		dataSource.setMaxOpenPreparedStatements(180);
	}



	BasicDataSource dataSource;

	private final String driverName;

	private final String userName;
	
	private final String password;

	private final String url;

}
