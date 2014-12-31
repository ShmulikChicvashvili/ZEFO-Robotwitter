/**
 * 
 */

package com.robotwitter.database.interfaces;

import java.util.List;

import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBFollowersNumber;

/**
 * @author Eyal and Shmulik
 *
 */
public interface IDatabaseNumFollowers
{	
	
	public SqlError delete(Long twitterId);
	
	public List<DBFollowersNumber> get(Long twitterId);
	
	public SqlError insert(DBFollowersNumber statistic);
	
	public SqlError update(DBFollowersNumber statistic);
	
}
