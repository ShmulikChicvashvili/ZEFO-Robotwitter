package com.robotwitter.database;

import java.util.ArrayList;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBFollower;

public class MySqlDatabaseFollowers implements IDatabaseFollowers {

	@Override
	public ArrayList<DBFollower> getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBFollower getByScreenName(String screenName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SqlError insert(DBFollower follower) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExistsByName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExistsByScreenName(String ScreenName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SqlError update(DBFollower follower) {
		// TODO Auto-generated method stub
		return null;
	}

}
