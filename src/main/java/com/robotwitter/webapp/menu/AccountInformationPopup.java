
package com.robotwitter.webapp.menu;


import com.vaadin.server.FontAwesome;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.account.IAccountController;
import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.control.account.ITwitterConnectorController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractUI;
import com.robotwitter.webapp.view.IUserSession;




/**
 * Represents a user account information component which can be expanded on
 * click from a minimised presentation to a popped-up descriptive
 * representation.
 */
class AccountInformationPopup implements PopupView.Content
{
	/**
	 * Instantiates a new account information pop-up.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param twitterConnectorController
	 *            the Twitter account connector controller
	 */
	public AccountInformationPopup(
		IMessagesContainer messages,
		ITwitterConnectorController twitterConnectorController)
	{
		this.messages = messages;
		this.twitterConnectorController = twitterConnectorController;
		
		initialiseTwitterConnectorController();
		
		userSession = ((AbstractUI) UI.getCurrent()).getUserSession();
		owner = null;
	}


	@Override
	public String getMinimizedValueAsHTML()
	{
		IAccountController controller = userSession.getAccountController();
		
		// Root element
		String minimisedOpen;
		if (((AbstractUI) UI.getCurrent()).isMobile())
		{
			minimisedOpen =
				"<div class=\""
					+ MINIMISED_STYLENAME
					+ " "
					+ MOBILE_MINIMISED_STYLENAME
					+ "\">";
		} else
		{
			minimisedOpen = "<div class=\"" + MINIMISED_STYLENAME + "\">";
		}
		String minimiseClose = "</div>";
		
		ITwitterAccountController account =
			controller.getActiveTwitterAccount();

		String minimiseContent = null;
		if (account == null)
		{
			minimiseContent =
				TwitterCard
				.createAsHtml(
					messages
							.get("AccountInformationPopup.label.no-twitter-account"),
					messages
							.get("AccountInformationPopup.label.click-to-connect"),
					"http://www.austadpro.com/blog/wp-content/uploads/2011/07/anonymous-user-gravatar.png",
					true,
					((AbstractUI) UI.getCurrent()).isMobile());
		} else
		{
			minimiseContent =
				TwitterCard.createAsHtml(
					account.getName(),
					account.getScreenname(),
					account.getImage(),
					false,
					((AbstractUI) UI.getCurrent()).isMobile());
		}

		String minimiseElem = minimisedOpen + minimiseContent + minimiseClose;
		
		return minimiseElem;
	}
	
	
	@Override
	public Component getPopupComponent()
	{
		VerticalLayout popup =
			new VerticalLayout(
				createTwitterAccountCards(),
				createAccountInformation());
		
		popup.addStyleName(POPUP_STYLENAME);
		if (((AbstractUI) UI.getCurrent()).isMobile())
		{
			popup.addStyleName(MOBILE_POPUP_STYLENAME);
		}
		
		return popup;
	}


	/**
	 * Sets the owner component of this pop-up view content.
	 * <p>
	 * Must be called immediately after binding this content to a pop-up view
	 * component.
	 * <p>
	 * Note: Vaadin does not provide a way to acquire the owning component of
	 * this pop-up view component, and so we must manually set it using this
	 * method.
	 * <p>
	 * Note 2: this is required in order to close the pop-up programmatically.
	 *
	 * @param owner
	 *            the pop-up view owning this content
	 */
	public void setOwner(PopupView owner)
	{
		this.owner = owner;
	}


	/** Closes the owning pop-up. */
	private void close()
	{
		owner.setPopupVisible(false);
	}
	
	
	/** @return an account information section component. */
	private Component createAccountInformation()
	{
		IAccountController controller = userSession.getAccountController();

		// Create components
		Label name = new Label(controller.getName());
		Label email = new Label(controller.getEmail());
		Button signout =
			new Button(
				messages.get("AccountInformationPopup.button.signout"),
				event -> {
					close();
					userSession.unsign();
				});

		// Create layouts
		VerticalLayout information = new VerticalLayout(name, email);
		HorizontalLayout wrapper = new HorizontalLayout(information, signout);
		wrapper.setSizeFull();
		
		// Set styles
		name.addStyleName(ACCOUNT_NAME_STYLENAME);
		email.addStyleName(ACCOUNT_EMAIL_STYLENAME);
		signout.addStyleName(SIGNOUT_STYLENAME);
		signout.addStyleName(ValoTheme.BUTTON_SMALL);
		information.addStyleName(ACCOUNT_INFO_STYLENAME);
		wrapper.addStyleName(ACCOUNT_WRAPPER_STYLENAME);
		
		return wrapper;
	}
	
	
	/**
	 * @return a list of twitter account cards as a component. Does not include
	 *         the currently active twitter account.
	 */
	private Component createTwitterAccountCards()
	{
		IAccountController controller = userSession.getAccountController();
		
		VerticalLayout twitterAccounts = new VerticalLayout();

		ITwitterAccountController activeAccount =
			controller.getActiveTwitterAccount();
		for (ITwitterAccountController account : controller
			.getTwitterAccounts())
		{
			// Skip this account if its the currently active one
			if (activeAccount != null
				&& activeAccount.getID() == account.getID())
			{
				continue;
			}

			Label card =
				new Label(TwitterCard.createAsHtml(
					account.getName(),
					account.getScreenname(),
					account.getImage(),
					false,
					false));
			card.setContentMode(ContentMode.HTML);
			VerticalLayout cardWrapper = new VerticalLayout(card);
			cardWrapper.addStyleName(CARD_WRAPPER_STYLENAME);
			cardWrapper
			.addLayoutClickListener(event -> {
				if (event.getButton().compareTo(MouseButton.LEFT) != 0) { return; }
				((AbstractUI) UI.getCurrent())
				.activateTwitterAccount(account.getID());
				close();
			});
			twitterAccounts.addComponent(cardWrapper);
		}
		
		// Create manage button
		Button manage =
			new Button(
				messages.get("AccountInformationPopup.button.manage"),
				event -> {
					UI.getCurrent().addWindow(twitterConnectorWindow);
					close();
				});
		manage.addStyleName(MANAGE_STYLENAME);
		manage.addStyleName(ValoTheme.BUTTON_LINK);
		manage.setSizeFull();
		manage.setIcon(FontAwesome.TWITTER);
		twitterAccounts.addComponent(manage);
		
		return twitterAccounts;
	}
	
	
	/** Initialises the Twitter account connector window. */
	private void initialiseTwitterConnectorController()
	{
		twitterConnectorWindow =
			new TwitterConnectorWindow(messages, twitterConnectorController);
	}
	
	
	
	/** The CSS class name to apply to the minimised presentation. */
	private static final String MINIMISED_STYLENAME =
		"AccountInformationPopup-minimised";

	/**
	 * The CSS class name to apply to the minimised presentation in mobile
	 * browsers.
	 */
	private static final String MOBILE_MINIMISED_STYLENAME =
		"AccountInformationPopup-minimised-mobile";
	
	/** The CSS class name to apply to the popup (maximised) presentation. */
	private static final String POPUP_STYLENAME =
		"AccountInformationPopup-popup";
	
	/**
	 * The CSS class name to apply to the popup (maximised) presentation in
	 * mobile browsers..
	 */
	private static final String MOBILE_POPUP_STYLENAME =
		"AccountInformationPopup-popup-mobile";
	
	/** The CSS class name to apply to a card wrapper. */
	private static final String CARD_WRAPPER_STYLENAME =
		"AccountInformationPopup-card-wrapper";
	
	/** The CSS class name to apply the "Manage Twitter accounts" button. */
	private static final String MANAGE_STYLENAME =
		"AccountInformationPopup-manage";
	
	/** The CSS class name to apply to the account information section. */
	private static final String ACCOUNT_INFO_STYLENAME =
		"AccountInformationPopup-account-info";
	
	/** The CSS class name to apply to the account information wrapper. */
	private static final String ACCOUNT_WRAPPER_STYLENAME =
		"AccountInformationPopup-account-wrapper";

	/** The CSS class name to apply to the account name label. */
	private static final String ACCOUNT_NAME_STYLENAME =
		"AccountInformationPopup-account-name";
	
	/** The CSS class name to apply to the account email label. */
	private static final String ACCOUNT_EMAIL_STYLENAME =
		"AccountInformationPopup-account-email";
	
	/** The CSS class name to apply the "Sign out" button. */
	private static final String SIGNOUT_STYLENAME =
		"AccountInformationPopup-signout";
	
	/** The messages displayed by this component. */
	protected IMessagesContainer messages;

	/** The current user's browsing session. */
	private final IUserSession userSession;

	/** The owning component. */
	private PopupView owner;
	
	/** The Twitter account connector controller. */
	private final ITwitterConnectorController twitterConnectorController;

	/** The Twitter account connector window. */
	private TwitterConnectorWindow twitterConnectorWindow;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
