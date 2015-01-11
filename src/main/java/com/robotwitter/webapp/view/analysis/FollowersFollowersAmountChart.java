/**
 *
 */

package com.robotwitter.webapp.view.analysis;


import java.util.ArrayList;
import java.util.List;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractBarCharComponent;




/**
 * Represents a chart showing followers amount by their number of followers.
 *
 * @author Eyal
 * @author Hagai
 */
public class FollowersFollowersAmountChart extends AbstractBarCharComponent
{
	/**
	 * Instantiates a new followers displayed language chart.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public FollowersFollowersAmountChart(IMessagesContainer messages)
	{
		super(messages, messages
			.get("FollowersFollowersAmountChart.error.no-data"));
		initialiseLayout();
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


	/**
	 * Initialise layout.
	 */
	private void initialiseLayout()
	{
		updateChart();
	}


	/**
	 * Update chart.
	 */
	private void updateChart()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();

		List<Integer> separators = new ArrayList<>();
		List<Integer> amounts = new ArrayList<>();
		controller.getFollowersAmountByTheirFollowersAmount(
			5,
			amounts,
			separators);

		setBySeperators(separators, amounts);
		show();
	}
}
