
package com.robotwitter.webapp.view.analysis;


import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * Represents all analysis components in a tabbed manner.
 *
 * @author Hagai Akibayov
 */
public class AnalysisTabs extends RobotwitterCustomComponent
{
	
	/** Available tabs. */
	enum Tab
	{
		/** The influential followers. */
		INFLUENTIAL_FOLLOWERS,
		
		/** The followers over time. */
		FOLLOWERS_OVER_TIME,
		
		/** The displayed language. */
		DISPLAYED_LANGUAGE,
		
		/** The followers followers. */
		FOLLOWERS_FOLLOWERS,
		
		/** The followers following. */
		FOLLOWERS_FOLLOWING
	}



	/**
	 * Instantiates a new AnalysisTabs.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public AnalysisTabs(IMessagesContainer messages)
	{
		super(messages);
		
		influentialFollowers = new Button();
		followersOverTime = new Button();
		displayedLanguage = new Button();
		followersFollowers = new Button();
		followersFollowing = new Button();

		influentialFollowers.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		followersOverTime.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		displayedLanguage.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		followersFollowers.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		followersFollowing.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		initialiseLayout();
	}


	/** @return A new the tab-menu component. */
	private Component createTabMenu()
	{

		// tab-menu buttons

		influentialFollowers.setCaption(messages
			.get("AnalysisTabs.tab.influential-followers"));
		followersOverTime.setCaption(messages
			.get("AnalysisTabs.tab.followers-over-time"));
		displayedLanguage.setCaption(messages
			.get("AnalysisTabs.tab.displayed-language"));
		followersFollowers.setCaption(messages
			.get("AnalysisTabs.tab.followers-followers"));
		followersFollowing.setCaption(messages
			.get("AnalysisTabs.tab.followers-following"));
		
		// tab-menu buttons click-events
		
		influentialFollowers
		.addClickListener(event -> activateTab(Tab.INFLUENTIAL_FOLLOWERS));
		followersOverTime
		.addClickListener(event -> activateTab(Tab.FOLLOWERS_OVER_TIME));
		displayedLanguage
		.addClickListener(event -> activateTab(Tab.DISPLAYED_LANGUAGE));
		followersFollowers
		.addClickListener(event -> activateTab(Tab.FOLLOWERS_FOLLOWERS));
		followersFollowing
			.addClickListener(event -> activateTab(Tab.FOLLOWERS_FOLLOWING));

		// layout
		
		VerticalLayout tabs =
			new VerticalLayout(
				influentialFollowers,
				followersOverTime,
				displayedLanguage,
				followersFollowers,
				followersFollowing);
		
		return tabs;
	}


	/** Initialises the layout. */
	private void initialiseLayout()
	{
		Component tabMenu = createTabMenu();
		
		mostInfluentialFollowers = new MostInfluentialFollowers(messages);
		followersAmountOverTimeChart =
			new FollowersAmountOverTimeChart(messages);
		followersDisplayedLanguageChart =
			new FollowersDisplayedLanguageChart(messages);
		followersFollowersAmountChart =
			new FollowersFollowersAmountChart(messages);
		followersFollowingAmountChart =
			new FollowersFollowingAmountChart(messages);
		
		componentDescription = new Label();
		
		analysisComponentContainer = new VerticalLayout();
		analysisComponentContainer.setSizeFull();

		activateTab(Tab.INFLUENTIAL_FOLLOWERS);

		VerticalLayout descriptionAndContainer =
			new VerticalLayout(componentDescription, analysisComponentContainer);
		descriptionAndContainer.setSizeFull();
		descriptionAndContainer.setSpacing(true);
		
		HorizontalLayout layout =
			new HorizontalLayout(tabMenu, descriptionAndContainer);
		layout.setSizeFull();
		
		tabMenu.addStyleName(MENU_STYLENAME);
		componentDescription.addStyleName(DESC_STYLENAME);
		descriptionAndContainer.addStyleName(CONTENT_STYLENAME);
		addStyleName(STYLENAME);

		setSizeFull();

		setCompositionRoot(layout);
	}
	
	
	/**
	 * Activate a tab.
	 *
	 * @param tab
	 *            the tab
	 */
	final void activateTab(Tab tab)
	{
		analysisComponentContainer.removeAllComponents();

		/* Remove active CSS style */
		influentialFollowers.removeStyleName(ACTIVE_TAB_STYLENAME);
		followersOverTime.removeStyleName(ACTIVE_TAB_STYLENAME);
		displayedLanguage.removeStyleName(ACTIVE_TAB_STYLENAME);
		followersFollowers.removeStyleName(ACTIVE_TAB_STYLENAME);
		followersFollowing.removeStyleName(ACTIVE_TAB_STYLENAME);
		
		switch (tab)
		{
			case INFLUENTIAL_FOLLOWERS:
				/* Set description */
				componentDescription.setValue(messages
					.get("AnalysisTabs.desc.influential-followers"));
				/* Set content */
				analysisComponentContainer
					.addComponent(mostInfluentialFollowers);
				/* Set active CSS */
				influentialFollowers.addStyleName(ACTIVE_TAB_STYLENAME);
				break;
			case FOLLOWERS_OVER_TIME:
				componentDescription.setValue(messages
					.get("AnalysisTabs.desc.followers-over-time"));
				analysisComponentContainer
					.addComponent(followersAmountOverTimeChart);
				followersOverTime.addStyleName(ACTIVE_TAB_STYLENAME);
				break;
			case DISPLAYED_LANGUAGE:
				componentDescription.setValue(messages
					.get("AnalysisTabs.desc.displayed-language"));
				analysisComponentContainer
					.addComponent(followersDisplayedLanguageChart);
				displayedLanguage.addStyleName(ACTIVE_TAB_STYLENAME);
				break;
			case FOLLOWERS_FOLLOWERS:
				componentDescription.setValue(messages
					.get("AnalysisTabs.desc.followers-followers"));
				analysisComponentContainer
					.addComponent(followersFollowersAmountChart);
				followersFollowers.addStyleName(ACTIVE_TAB_STYLENAME);
				break;
			case FOLLOWERS_FOLLOWING:
				componentDescription.setValue(messages
					.get("AnalysisTabs.desc.followers-following"));
				analysisComponentContainer
					.addComponent(followersFollowingAmountChart);
				followersFollowing.addStyleName(ACTIVE_TAB_STYLENAME);
				break;
			default:
				break;
		
		}
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "AnalysisTabs";

	/** The CSS class name to apply to the menu. */
	private static final String MENU_STYLENAME = "AnalysisTabs-menu";
	
	/** The CSS class name to apply to the content wrapper. */
	private static final String CONTENT_STYLENAME = "AnalysisTabs-content";

	/** The CSS class name to apply to the content's description. */
	private static final String DESC_STYLENAME = "AnalysisTabs-desc";

	/** The CSS class name to apply to the active tab. */
	private static final String ACTIVE_TAB_STYLENAME =
		"AnalysisTabs-active-tab";
	
	/** The description of a single analysis component. */
	Label componentDescription;
	
	/** Contains a single analysis component. */
	ComponentContainer analysisComponentContainer;
	
	/** The most influential followers component. */
	MostInfluentialFollowers mostInfluentialFollowers;
	
	/** The followers amount over time chart component. */
	FollowersAmountOverTimeChart followersAmountOverTimeChart;
	
	/** The followers displayed language chart component. */
	FollowersDisplayedLanguageChart followersDisplayedLanguageChart;
	
	/** The followers followers amount chart component. */
	FollowersFollowersAmountChart followersFollowersAmountChart;
	
	/** The followers following amount chart component. */
	FollowersFollowingAmountChart followersFollowingAmountChart;
	
	/** The influential followers button. */
	private Button influentialFollowers;

	/** The displayed language button. */
	private Button displayedLanguage;

	/** The followers over time button. */
	private Button followersOverTime;

	/** The followers followers button. */
	private Button followersFollowers;

	/** The followers following button. */
	private Button followersFollowing;
}
