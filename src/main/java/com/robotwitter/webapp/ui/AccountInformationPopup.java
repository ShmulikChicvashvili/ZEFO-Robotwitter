
package com.robotwitter.webapp.ui;


import com.vaadin.server.FontAwesome;
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
import com.robotwitter.webapp.control.account.IAccountController.TwitterAccount;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.IUserSession;
import com.robotwitter.webapp.view.RobotwitterUI;




/**
 * Represents a user account information component which can be expanded on
 * click from a minimised presentation to a popped-up descriptive
 * representation.
 */
class AccountInformationPopup implements PopupView.Content
{
	/**
	 * Instantiates a new account information popup.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public AccountInformationPopup(IMessagesContainer messages)
	{
		this.messages = messages;
		userSession = ((RobotwitterUI) UI.getCurrent()).getUserSession();
		owner = null;
	}
	
	
	@Override
	public String getMinimizedValueAsHTML()
	{
		IAccountController controller = userSession.getAccountController();

		// Root element
		String minimisedOpen = "<div class=\"" + MINIMISED_STYLENAME + "\">"; //$NON-NLS-1$ //$NON-NLS-2$
		String minimiseClose = "</div>"; //$NON-NLS-1$
		
		TwitterAccount account = controller.getActiveTwitterAccount();
		String minimiseContent =
			TwitterCard.createAsHtml(
				account.name,
				account.screenname,
				account.image);
		
		String minimiseElem = minimisedOpen + minimiseContent + minimiseClose;

		return minimiseElem;
	}


	@Override
	public Component getPopupComponent()
	{
		return new VerticalLayout(
			createTwitterAccountCards(),
			createAccountInformation());
	}
	
	
	/**
	 * Sets the owner component of this popup view content.
	 * <p>
	 * Must be called immediately after binding this content to a popup view
	 * component.
	 * <p>
	 * Note: Vaadin does not provide a way to acquire the owning component of
	 * this popup view component, and so we must manually set it using this
	 * method.
	 * <p>
	 * Note 2: this is required in order to close the popup programmatically.
	 *
	 * @param owner
	 *            the popup view owning this content
	 */
	public void setOwner(PopupView owner)
	{
		this.owner = owner;
	}
	
	
	/** Closes the owning popup. */
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
			new Button(messages.get("AccountInformationPopup.button.signout"), //$NON-NLS-1$
				event -> {
					close();
					userSession.unsign();
				});
		
		// Create layouts
		VerticalLayout information = new VerticalLayout(name, email);
		HorizontalLayout wrapper = new HorizontalLayout(information, signout);

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
		
		TwitterAccount activeAccount = controller.getActiveTwitterAccount();
		for (TwitterAccount account : controller.getTwitterAccounts())
		{
			// Skip this account if its the currently active one
			if (activeAccount != null
				&& activeAccount.screenname.equals(account.screenname))
			{
				continue;
			}
			
			Label card =
				new Label(TwitterCard.createAsHtml(
					account.name,
					account.screenname,
					account.image));
			card.setContentMode(ContentMode.HTML);
			VerticalLayout cardWrapper = new VerticalLayout(card);
			cardWrapper.addStyleName(CARD_WRAPPER_STYLENAME);
			cardWrapper.addLayoutClickListener(event -> {
				userSession.activateTwitterAccount(account.screenname);
				close();
			});
			twitterAccounts.addComponent(cardWrapper);
		}

		// Create manage button
		Button manage =
			new Button(messages.get("AccountInformationPopup.button.manage")); //$NON-NLS-1$
		manage.addStyleName(MANAGE_STYLENAME);
		manage.addStyleName(ValoTheme.BUTTON_LINK);
		manage.setSizeFull();
		manage.setIcon(FontAwesome.TWITTER);
		twitterAccounts.addComponent(manage);

		return twitterAccounts;
	}
	
	
	
	/** The CSS class name to apply to the minimised presentation. */
	private static final String MINIMISED_STYLENAME =
		"AccountInformationPopup-minimised"; //$NON-NLS-1$

	/** The CSS class name to apply to a card wrapper. */
	private static final String CARD_WRAPPER_STYLENAME =
		"AccountInformationPopup-card-wrapper"; //$NON-NLS-1$

	/** The CSS class name to apply the "Manage Twitter accounts" button. */
	private static final String MANAGE_STYLENAME =
		"AccountInformationPopup-manage"; //$NON-NLS-1$

	/** The CSS class name to apply to the account information section. */
	private static final String ACCOUNT_INFO_STYLENAME =
		"AccountInformationPopup-account-info"; //$NON-NLS-1$

	/** The CSS class name to apply to the account information wrapper. */
	private static final String ACCOUNT_WRAPPER_STYLENAME =
		"AccountInformationPopup-account-wrapper"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the account name label. */
	private static final String ACCOUNT_NAME_STYLENAME =
		"AccountInformationPopup-account-name"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the account email label. */
	private static final String ACCOUNT_EMAIL_STYLENAME =
		"AccountInformationPopup-account-email"; //$NON-NLS-1$

	/** The CSS class name to apply the "Sign out" button. */
	private static final String SIGNOUT_STYLENAME =
		"AccountInformationPopup-signout"; //$NON-NLS-1$

	/** The messages displayed by this component. */
	protected IMessagesContainer messages;
	
	/** The current user's browsing session. */
	private final IUserSession userSession;
	
	/** The owning component. */
	private PopupView owner;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
