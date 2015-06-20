/**
 *
 */

package com.robotwitter.webapp.control.scheduling;


import java.util.ArrayList;
import java.util.List;

import com.robotwitter.posting.AutomateTweetPostingPeriod;




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
	 * #addScheduledTweet(java.lang.String, java.lang.String, long,
	 * java.util.Calendar, com.robotwitter.posting.AutomateTweetPostingPeriod) */
	@Override
	public void addScheduledTweet(
		String name,
		String text,
		long userId,
		java.util.Calendar c,
		AutomateTweetPostingPeriod period)
	{
		// TODO Auto-generated method stub

	}

	
	@Override
	public List<String> breakTweet(String tweet){
		return null;
	};

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
