/**
 *
 */

package com.robotwitter.webapp.control.scheduling;


import java.util.ArrayList;
import java.util.List;




/**
 * @author Eyal
 *
 */
public class ScheduledTweetsControllerMock
	implements
		IScheduledTweetsController
{

	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.scheduling.IScheduledTweetsController
	 * #previewTweet(java.lang.String) */
	@Override
	public List<String> previewTweet(String text)
	{
		// TODO Auto-generated method stub
		final List<String> l = new ArrayList<String>();
		l.add("This is first");
		l.add("This is second");
		return l;
	}

}
