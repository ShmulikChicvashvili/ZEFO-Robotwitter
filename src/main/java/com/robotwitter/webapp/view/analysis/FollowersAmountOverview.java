
package com.robotwitter.webapp.view.analysis;


import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
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
	 * @param showAccountPicture
	 *            whether to show or not to show the account's profile picture
	 */
	public FollowersAmountOverview(
		IMessagesContainer messages,
		boolean showAccountPicture)
	{
		super(messages);
		
		this.showAccountPicture = showAccountPicture;

		picture = new Image();
		name = new Button();
		screenname = new Label();
		nameOpener = null;

		initialiseLayout();
		
		getUserSession().observeActiveTwitterAccount(this);
	}


	@Override
	public final void activateTwitterAccount(long id)
	{
		updateTwitterAccountInformation();
		updateAmounts();
	}
	
	
	/** @return The create stats component. */
	private Component createStatsComponent()
	{
		totalFollowersAmountLabel = new Label();
		gainedFollowersAmountLabel = new Label();
		lostFollowersAmountLabel = new Label();

		updateAmounts();
		
		if (!isMobile())
		{
			totalFollowersAmountLabel
				.setCaption(messages
					.get("FollowersAmountOverview.caption.total-amount-of-followers"));
			gainedFollowersAmountLabel
				.setCaption(messages
					.get("FollowersAmountOverview.caption.gained-amount-of-followers"));
			lostFollowersAmountLabel
				.setCaption(messages
					.get("FollowersAmountOverview.caption.lost-amount-of-followers"));
		}

		totalFollowersAmountLabel.setIcon(FontAwesome.USERS);
		gainedFollowersAmountLabel.setIcon(FontAwesome.ARROW_CIRCLE_UP);
		lostFollowersAmountLabel.setIcon(FontAwesome.ARROW_CIRCLE_DOWN);

		VerticalLayout stats;

		if (!isMobile())
		{
			stats =
				new VerticalLayout(
					totalFollowersAmountLabel,
					gainedFollowersAmountLabel,
					lostFollowersAmountLabel);
		} else
		{
			HorizontalLayout total =
				new HorizontalLayout(
					totalFollowersAmountLabel,
					new Label(
						messages
							.get("FollowersAmountOverview.caption.total-amount-of-followers")));
			
			HorizontalLayout gained =
				new HorizontalLayout(
					gainedFollowersAmountLabel,
					new Label(
						messages
							.get("FollowersAmountOverview.caption.gained-amount-of-followers")));
			
			HorizontalLayout lost =
				new HorizontalLayout(
					lostFollowersAmountLabel,
					new Label(
						messages
							.get("FollowersAmountOverview.caption.lost-amount-of-followers")));
			
			stats = new VerticalLayout(total, gained, lost);
		}
		
		stats.addStyleName(STATS_STYLENAME);

		totalFollowersAmountLabel.addStyleName(AMOUNT_COMPONENT_STYLENAME);
		gainedFollowersAmountLabel.addStyleName(AMOUNT_COMPONENT_STYLENAME);
		lostFollowersAmountLabel.addStyleName(AMOUNT_COMPONENT_STYLENAME);
		totalFollowersAmountLabel.addStyleName(TOTAL_STYLENAME);
		gainedFollowersAmountLabel.addStyleName(GAINED_STYLENAME);
		lostFollowersAmountLabel.addStyleName(LOST_STYLENAME);
		
		return stats;
	}
	
	
	/** Initialises the layout. */
	private void initialiseLayout()
	{
		updateTwitterAccountInformation();
		
		Component stats = createStatsComponent();
		
		VerticalLayout nameAndScreenname = new VerticalLayout(name, screenname);
		
		AbstractOrderedLayout layout;
		if (isMobile())
		{
			layout = new VerticalLayout(picture, nameAndScreenname, stats);
		} else
		{
			layout = new HorizontalLayout(nameAndScreenname, picture, stats);
		}
		layout.setSpacing(true);

		if (!showAccountPicture)
		{
			layout.removeComponent(picture);
		}
		
		picture.addStyleName(PICTURE_STYLENAME);
		nameAndScreenname.addStyleName(NAME_AND_SCREENNAME_STYLENAME);
		name.addStyleName(NAME_STYLENAME);
		name.addStyleName(ValoTheme.BUTTON_LINK);
		screenname.addStyleName(SCREENNAME_STYLENAME);
		addStyleName(STYLENAME);

		if (isMobile())
		{
			addStyleName(MOBILE_STYLENAME);
		}
		
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

		picture.setSource(new ExternalResource(controller.getImage()));
		picture.setAlternateText(controller.getName());
		name.setCaption(controller.getName());
		screenname.setValue('@' + controller.getScreenname());
		
		if (nameOpener != null)
		{
			nameOpener.remove();
		}

		nameOpener =
			new BrowserWindowOpener("https://twitter.com/"
				+ controller.getScreenname());
		nameOpener.extend(name);
	}



	/** Whether to show or not to show the account's profile picture. */
	private boolean showAccountPicture;

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "FollowersAmountOverview";
	
	/** The CSS class name to apply to this component in mobile browsers. */
	private static final String MOBILE_STYLENAME =
		"FollowersAmountOverview-mobile";
	
	/** The CSS class name to apply to an amount component. */
	private static final String AMOUNT_COMPONENT_STYLENAME =
		"FollowersAmountOverview-amount-component";
	
	/** The CSS class name to apply to the stats component. */
	private static final String STATS_STYLENAME =
		"FollowersAmountOverview-stats";
	
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
	
	/** The CSS class name to apply to the name and screenname wrapper. */
	private static final String NAME_AND_SCREENNAME_STYLENAME =
		"FollowersAmountOverview-name-and-screenname";
	
	/** The CSS class name to apply to the Twitter account's name. */
	private static final String NAME_STYLENAME = "FollowersAmountOverview-name";
	
	/** The CSS class name to apply to the Twitter account's screenname. */
	private static final String SCREENNAME_STYLENAME =
		"FollowersAmountOverview-screenname";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The active Twitter follower's picture image. */
	Image picture;
	
	/** The active Twitter follower's name button. */
	Button name;
	
	/** The active Twitter follower's screenname label. */
	Label screenname;
	
	/** The browser window opener extension for the name button. */
	BrowserWindowOpener nameOpener;
	
	/** The total followers amount label. */
	private Label totalFollowersAmountLabel;
	
	/** The gained followers label. */
	private Label gainedFollowersAmountLabel;
	
	/** The lost followers label. */
	private Label lostFollowersAmountLabel;
}
