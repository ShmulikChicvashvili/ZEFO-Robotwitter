package com.robotwitter.webapp.view.tools;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/**
 * Tools view.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 */
public class ToolsView extends AbstractView
{
	
	/**
	 * Wrap the given component in a panel.
	 *
	 * @param component
	 *            the component to wrap
	 * @param title
	 *            the panel's title
	 *
	 * @return a newly created panel wrapper, wrapping the given component
	 */
	private static Component wrapInPanel(Component component, String title)
	{
		Label titleLabel = new Label(title);
		VerticalLayout panel = new VerticalLayout(titleLabel, component);
		panel.setWidthUndefined();
		titleLabel.addStyleName(PANEL_TITLE_STYLENAME);
		panel.addStyleName(PANEL_STYLENAME);
		return panel;
	}
	
	
	/**
	 * Instantiates a new tools view.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param tweetingController
	 *            the tweeting controller
	 */
	@Inject
	public ToolsView(
		@Named(NAME) IMessagesContainer messages,
		ITweetingController tweetingController)
	{
		super(messages, messages.get("ToolsView.page.title"));
		this.tweetingController = tweetingController;
	}
	
	
	@Override
	public final boolean isSignedInProhibited()
	{
		return false;
	}
	
	
	@Override
	public final boolean isSignedInRequired()
	{
		return true;
	}
	
	
	@Override
	protected final void initialise()
	{
		Label header = new Label(messages.get("ToolsView.label.header"));
		TweetComposer composer =
			new TweetComposer(messages, tweetingController);
		
		ComposerSettings settings = new ComposerSettings(messages, tweetingController);
		
		Component composerPanel =
			wrapInPanel(
				composer,
				messages.get("ToolsView.caption.tweet-composer"));
		Component settingsPanel = wrapInPanel(settings, messages.get("ToolsView.caption.composer-settings"));
		
		VerticalLayout composerPanelWrapper = new VerticalLayout(composerPanel, settingsPanel);
		composerPanelWrapper.setSizeFull();
		composerPanelWrapper.setComponentAlignment(
			composerPanel,
			Alignment.MIDDLE_CENTER);
		composerPanelWrapper.setComponentAlignment(settingsPanel, Alignment.BOTTOM_CENTER);

		VerticalLayout layout =
			new VerticalLayout(header, composerPanelWrapper);
		layout.setSizeFull();
		layout.setExpandRatio(composerPanelWrapper, 1);
		
		header.addStyleName(HEADER_STYLENAME);
		addStyleName(STYLENAME);
		
		setCompositionRoot(layout);
	}
	
	
	
	/** The tweeting controller. */
	ITweetingController tweetingController;
	
	/** The view's name. */
	public static final String NAME = "tools";

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "ToolsView";
	
	/** The CSS class name to apply to the header component. */
	private static final String HEADER_STYLENAME = "ToolsView-header";
	
	/** The CSS class name to apply to each panel component. */
	private static final String PANEL_STYLENAME = "ToolsView-panel";
	
	/** The CSS class name to apply to each panel title label. */
	private static final String PANEL_TITLE_STYLENAME = "ToolsView-panel-title";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}