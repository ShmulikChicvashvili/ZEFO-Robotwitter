
package com.robotwitter.webapp.view.analysis;


import java.util.List;

import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.control.account.TwitterFollower;
import com.robotwitter.webapp.menu.TwitterCard;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * Represents a list of the most influential followers of a Twitter account.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 */
public class MostInfluentialFollowers extends RobotwitterCustomComponent
{

	/**
	 * Instantiates a new most influential followers list.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public MostInfluentialFollowers(IMessagesContainer messages)
	{
		super(messages);
		activeCardWrapper = null;
		initialiseLayout();
		getUserSession().observeActiveTwitterAccount(this);
	}
	
	
	/**
	 * Activates the given follower.
	 *
	 * @param id
	 *            the follower's Twitter account ID
	 */
	public void activateFollower(long id)
	{

	}


	@Override
	public final void activateTwitterAccount(long id)
	{
		updateList();
	}


	/** Initialises the layout. */
	private void initialiseLayout()
	{
		followers = new VerticalLayout();
		updateList();

		addStyleName(STYLENAME);
		
		setCompositionRoot(followers);
	}
	
	
	/** Update the followers over time chart. */
	private void updateList()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();

		influentialFollowers = controller.getMostInfluentialFollowers();

		followers.removeAllComponents();
		for (TwitterFollower follower : influentialFollowers)
		{
			Label card =
				new Label(TwitterCard.createAsHtml(
					follower.getName(),
					follower.getScreenName(),
					follower.getPicture(),
					false));
			card.setContentMode(ContentMode.HTML);
			VerticalLayout cardWrapper = new VerticalLayout(card);
			cardWrapper.addStyleName(CARD_WRAPPER_STYLENAME);
			cardWrapper
				.addLayoutClickListener(event -> {
					if (event.getButton().compareTo(MouseButton.LEFT) != 0) { return; }
					if (activeCardWrapper != null)
				{
					activeCardWrapper
					.removeStyleName(ACTIVE_CARD_WRAPPER_STYLENAME);
				}
					event.getClickedComponent().addStyleName(
					ACTIVE_CARD_WRAPPER_STYLENAME);
					activeCardWrapper = event.getClickedComponent();
				});

			followers.addComponent(cardWrapper);
		}
	}



	/** The list of influential followers. */
	VerticalLayout followers;
	
	/** A list of the most influential followers. */
	List<TwitterFollower> influentialFollowers;
	
	/** The current active Twitter account card wrapper component. */
	Component activeCardWrapper;

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "MostInfluentialFollowers";

	/** The CSS class name to apply to the active card wrapper component. */
	private static final String ACTIVE_CARD_WRAPPER_STYLENAME =
		"MostInfluentialFollowers-active-card-wrapper";
	
	/** The CSS class name to apply to a card wrapper. */
	private static final String CARD_WRAPPER_STYLENAME =
		"AccountInformationPopup-card-wrapper";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
