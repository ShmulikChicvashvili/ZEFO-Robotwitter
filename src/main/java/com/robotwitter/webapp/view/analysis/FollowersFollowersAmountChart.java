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
 * The Class FollowersFollowersAmountChart.
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
		super(messages);
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
		set("!FollowersAmountChart!");
		updateChart();
	}


	/**
	 * Update chart.
	 */
	private final void updateChart()
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
