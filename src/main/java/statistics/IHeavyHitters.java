/**
 * 
 */

package statistics;


import java.util.ArrayList;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.User;




/**
 * @author Itay, Shmulik
 *
 */
public interface IHeavyHitters
{
	public ArrayList<Long> getCurrentHeavyHitters();
	
	
	public void onDirectMessage(DirectMessage directMessage);
	
	
	public void onFavorite(User source, User target, Status favoritedStatus);
	
	
	public void onFollow(User source, User followedUser);
	
	
	public void onMentioned(Status status);
	
	
	public void onRetweetedStatus(Status status);
	
}
