package com.robotwitter.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.robotwitter.database.interfaces.ConnectionEstablisher;

public class MySqlDatabaseBulkPOC extends AbstractMySqlDatabase {

	public MySqlDatabaseBulkPOC(ConnectionEstablisher conEstablisher)
			throws SQLException {
		super(conEstablisher);
		try (Connection con = connectionEstablisher.getConnection();
				Statement statement = con.createStatement()) {
			final String statementCreateBulk = String.format(
					"CREATE TABLE IF NOT EXISTS %s (" //$NON-NLS-1$
							+ "`%s` VARCHAR(255) NOT NULL," //$NON-NLS-1$
							+ "`%s` VARCHAR(255) NOT NULL," //$NON-NLS-1$
							+ "`%s` VARCHAR(255) NOT NULL," //$NON-NLS-1$
							+ "PRIMARY KEY (`%s`));", //$NON-NLS-1$
					bulkTable, "colKey", "col1", "col2", "colKey");
			statement.execute(statementCreateBulk);
		}
	}

	/**
	 * The table name.
	 */
	private final String bulkTable = schema + "." + "`bulk`"; //$NON-NLS-1$ //$NON-NLS-2$

}
