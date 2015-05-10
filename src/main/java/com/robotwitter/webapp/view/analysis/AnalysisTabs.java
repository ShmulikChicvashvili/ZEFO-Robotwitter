
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
	
	/**
	 * Instantiates a new AnalysisTabs.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public AnalysisTabs(IMessagesContainer messages)
	{
		super(messages);

		initialiseLayout();
	}
	
	
	/** @return A new the tab-menu component. */
	private Component createTabMenu()
	{

		// tab-menu buttons

		Button influentialFollowers =
			new Button(messages.get("AnalysisTabs.tab.influential-followers"));
		influentialFollowers.setStyleName(ValoTheme.BUTTON_BORDERLESS);

		Button followersOverTime =
			new Button(messages.get("AnalysisTabs.tab.followers-over-time"));
		followersOverTime.setStyleName(ValoTheme.BUTTON_BORDERLESS);

		Button displayedLanguage =
			new Button(messages.get("AnalysisTabs.tab.displayed-language"));
		displayedLanguage.setStyleName(ValoTheme.BUTTON_BORDERLESS);

		Button followersFollowers =
			new Button(messages.get("AnalysisTabs.tab.followers-followers"));
		followersFollowers.setStyleName(ValoTheme.BUTTON_BORDERLESS);

		Button followersFollowing =
			new Button(messages.get("AnalysisTabs.tab.followers-following"));
		followersFollowing.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		
		// tab-menu buttons click-events
		
		influentialFollowers.addClickListener(event -> {
			componentDescription.setValue(messages
				.get("AnalysisTabs.desc.influential-followers"));
			analysisComponentContainer.removeAllComponents();
			analysisComponentContainer.addComponent(mostInfluentialFollowers);
		});

		followersOverTime.addClickListener(event -> {
			componentDescription.setValue(messages
				.get("AnalysisTabs.desc.followers-over-time"));
			analysisComponentContainer.removeAllComponents();
			analysisComponentContainer
				.addComponent(followersAmountOverTimeChart);
		});

		displayedLanguage.addClickListener(event -> {
			componentDescription.setValue(messages
				.get("AnalysisTabs.desc.displayed-language"));
			analysisComponentContainer.removeAllComponents();
			analysisComponentContainer
			.addComponent(followersDisplayedLanguageChart);
		});

		followersFollowers.addClickListener(event -> {
			componentDescription.setValue(messages
				.get("AnalysisTabs.desc.followers-followers"));
			analysisComponentContainer.removeAllComponents();
			analysisComponentContainer
			.addComponent(followersFollowersAmountChart);
		});

		followersFollowing.addClickListener(event -> {
			componentDescription.setValue(messages
				.get("AnalysisTabs.desc.followers-following"));
			analysisComponentContainer.removeAllComponents();
			analysisComponentContainer
				.addComponent(followersFollowingAmountChart);
		});

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
		
		componentDescription =
			new Label(messages.get("AnalysisTabs.desc.influential-followers"));
		
		analysisComponentContainer =
			new VerticalLayout(mostInfluentialFollowers);

		VerticalLayout descriptionAndContainer =
			new VerticalLayout(componentDescription, analysisComponentContainer);
		
		HorizontalLayout layout =
			new HorizontalLayout(tabMenu, descriptionAndContainer);

		setCompositionRoot(layout);
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
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
}
