
package com.robotwitter.webapp.view.analysis;


import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import com.robotwitter.twitter.GainedLostNumFollowers;
import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * Represents a simple overview of the amount of followers of the active Twitter
 * account and the amount gained and lost since yesterday.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 */
public class FollowersAmountOverview extends RobotwitterCustomComponent
{

	/**
	 * Instantiates a new followers amount overview.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public FollowersAmountOverview(IMessagesContainer messages)
	{
		super(messages);
		initialiseLayout();
		getUserSession().observeActiveTwitterAccount(this);
	}


	@Override
	public final void activateTwitterAccount(long id)
	{
		updateAmounts();
	}


	/** Initialises the layout. */
	private void initialiseLayout()
	{
		totalFollowersAmountLabel = new Label();
		gainedFollowersAmountLabel = new Label();
		lostFollowersAmountLabel = new Label();

		totalFollowersAmountLabel.setCaption(messages
			.get("FollowersAmountOverview.caption.total-amount-of-followers"));
		gainedFollowersAmountLabel.setCaption(messages
			.get("FollowersAmountOverview.caption.gained-amount-of-followers"));
		lostFollowersAmountLabel.setCaption(messages
			.get("FollowersAmountOverview.caption.lost-amount-of-followers"));

		updateAmounts();

		HorizontalLayout layout =
			new HorizontalLayout(
				totalFollowersAmountLabel,
				gainedFollowersAmountLabel,
				lostFollowersAmountLabel);

		totalFollowersAmountLabel.addStyleName(AMOUNT_COMPONENT_STYLENAME);
		gainedFollowersAmountLabel.addStyleName(AMOUNT_COMPONENT_STYLENAME);
		lostFollowersAmountLabel.addStyleName(AMOUNT_COMPONENT_STYLENAME);
		addStyleName(STYLENAME);

		setCompositionRoot(layout);
	}


	/** Update amounts. */
	@SuppressWarnings("boxing")
	private void updateAmounts()
	{
		ITwitterAccountController account =
			getUserSession().getAccountController().getActiveTwitterAccount();
		GainedLostNumFollowers gainedLost =
			account.getLastGainedAndLostNumFollowers();
		totalFollowersAmountLabel.setValue(((Integer) gainedLost.getTotal())
			.toString());
		gainedFollowersAmountLabel.setValue(((Integer) gainedLost.getGained())
			.toString());
		lostFollowersAmountLabel.setValue(((Integer) gainedLost.getLost())
			.toString());
	}



	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "FollowersAmountOverview";

	/** The CSS class name to apply to an amount component. */
	private static final String AMOUNT_COMPONENT_STYLENAME =
		"FollowersAmountOverview-amount-component";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The total followers amount label. */
	private Label totalFollowersAmountLabel;

	/** The gained followers label. */
	private Label gainedFollowersAmountLabel;

	/** The lost followers label. */
	private Label lostFollowersAmountLabel;
}
