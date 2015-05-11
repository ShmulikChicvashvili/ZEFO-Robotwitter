
package com.robotwitter.webapp.view.analysis;


import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

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
		updateTwitterAccountInformation();
		updateAmounts();
	}
	
	
	/** Initialises the layout. */
	private void initialiseLayout()
	{
		updateTwitterAccountInformation();

		Image pictureImage = new Image();
		pictureImage.setSource(new ExternalResource(picture));
		pictureImage.setAlternateText(name);
		Button nameButton = new Button(name);
		nameButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		Label screennameLabel = new Label('@' + screenname);
		
		totalFollowersAmountLabel = new Label();
		gainedFollowersAmountLabel = new Label();
		lostFollowersAmountLabel = new Label();

		updateAmounts();
		
		totalFollowersAmountLabel.setCaption(messages
			.get("FollowersAmountOverview.caption.total-amount-of-followers"));
		gainedFollowersAmountLabel.setCaption(messages
			.get("FollowersAmountOverview.caption.gained-amount-of-followers"));
		lostFollowersAmountLabel.setCaption(messages
			.get("FollowersAmountOverview.caption.lost-amount-of-followers"));

		totalFollowersAmountLabel.setIcon(FontAwesome.USERS);
		gainedFollowersAmountLabel.setIcon(FontAwesome.ARROW_CIRCLE_UP);
		lostFollowersAmountLabel.setIcon(FontAwesome.ARROW_CIRCLE_DOWN);
		
		VerticalLayout nameAndScreenname =
			new VerticalLayout(nameButton, screennameLabel);

		VerticalLayout stats =
			new VerticalLayout(
				totalFollowersAmountLabel,
				gainedFollowersAmountLabel,
				lostFollowersAmountLabel);
		
		HorizontalLayout layout =
			new HorizontalLayout(nameAndScreenname, pictureImage, stats);
		
		totalFollowersAmountLabel.addStyleName(AMOUNT_COMPONENT_STYLENAME);
		gainedFollowersAmountLabel.addStyleName(AMOUNT_COMPONENT_STYLENAME);
		lostFollowersAmountLabel.addStyleName(AMOUNT_COMPONENT_STYLENAME);
		totalFollowersAmountLabel.addStyleName(TOTAL_STYLENAME);
		gainedFollowersAmountLabel.addStyleName(GAINED_STYLENAME);
		lostFollowersAmountLabel.addStyleName(LOST_STYLENAME);
		pictureImage.addStyleName(PICTURE_STYLENAME);
		nameButton.addStyleName(NAME_STYLENAME);
		screennameLabel.addStyleName(SCREENNAME_STYLENAME);
		addStyleName(STYLENAME);
		
		setCompositionRoot(layout);
	}
	
	
	/** Update amounts. */
	private void updateAmounts()
	{
		ITwitterAccountController account =
			getUserSession().getAccountController().getActiveTwitterAccount();

		int total = account.getLastKnownAmountOfFollowers();
		int gained = account.getLastKnownAmountOfGainedFollowers();
		int lost = account.getLastKnownAmountOfLostFollowers();

		totalFollowersAmountLabel.setValue(String.valueOf(total));
		gainedFollowersAmountLabel.setValue(String.valueOf(gained));
		lostFollowersAmountLabel.setValue(String.valueOf(lost));
	}
	
	
	/** Updates active Twitter account information. */
	private void updateTwitterAccountInformation()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();
		
		picture = controller.getImage();
		name = controller.getName();
		screenname = controller.getScreenname();
	}
	
	
	
	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "FollowersAmountOverview";
	
	/** The CSS class name to apply to an amount component. */
	private static final String AMOUNT_COMPONENT_STYLENAME =
		"FollowersAmountOverview-amount-component";
	
	/** The CSS class name to apply to the total amount component. */
	private static final String TOTAL_STYLENAME =
		"FollowersAmountOverview-total";
	
	/** The CSS class name to apply to the gained amount component. */
	private static final String GAINED_STYLENAME =
		"FollowersAmountOverview-gained";
	
	/** The CSS class name to apply to the lost amount component. */
	private static final String LOST_STYLENAME = "FollowersAmountOverview-lost";
	
	/** The CSS class name to apply to the Twitter account's picture. */
	private static final String PICTURE_STYLENAME =
		"FollowersAmountOverview-picture";
	
	/** The CSS class name to apply to the Twitter account's name. */
	private static final String NAME_STYLENAME = "FollowersAmountOverview-name";
	
	/** The CSS class name to apply to the Twitter account's screenname. */
	private static final String SCREENNAME_STYLENAME =
		"FollowersAmountOverview-screenname";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The active Twitter follower's picture image. */
	String picture;
	
	/** The active Twitter follower's name label. */
	String name;
	
	/** The active Twitter follower's screenname label. */
	String screenname;
	
	/** The total followers amount label. */
	private Label totalFollowersAmountLabel;
	
	/** The gained followers label. */
	private Label gainedFollowersAmountLabel;
	
	/** The lost followers label. */
	private Label lostFollowersAmountLabel;
}
