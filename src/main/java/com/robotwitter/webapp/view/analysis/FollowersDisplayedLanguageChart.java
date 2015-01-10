
package com.robotwitter.webapp.view.analysis;


import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractPieChartComponent;




/**
 * Represents a chart of a Twitter account's followers language distribution.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 */
public class FollowersDisplayedLanguageChart extends AbstractPieChartComponent
{

	/**
	 * Instantiates a new followers displayed language chart.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public FollowersDisplayedLanguageChart(IMessagesContainer messages)
	{
		super(messages);

		getUserSession().observeActiveTwitterAccount(this);
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.util.RobotwitterCustomComponent#
	 * activateTwitterAccount(long) */
	@Override
	public final void activateTwitterAccount(long id)
	{
		updateChart();
	}


	private final void updateChart()
	{

	}

}
