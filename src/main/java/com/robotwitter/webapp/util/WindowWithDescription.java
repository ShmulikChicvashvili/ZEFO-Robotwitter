
package com.robotwitter.webapp.util;


import com.vaadin.server.FontIcon;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;




/**
 * Represents a window with a description section.
 *
 * @author Hagai Akibayov
 */
public class WindowWithDescription extends Window
{
	
	/** Instantiates a new window with description. */
	public WindowWithDescription()
	{
		initialiseIconAndDesc();
		
		content = new Label();
		content.setVisible(false);
		wrapper = new VerticalLayout(iconAndDescWrapper, content);
		wrapper.setSpacing(true);
		super.setContent(wrapper);
		
		wrapper.setStyleName(WRAPPER_STYLENAME);
		setStyleName(WINDOW_STYLENAME);
	}


	@Override
	public final void setContent(Component newContent)
	{
		// When called from superclass' constructor
		if (wrapper == null)
		{
			super.setContent(newContent);
			return;
		}

		wrapper.replaceComponent(content, newContent);
		content = newContent;
	}
	
	
	@Override
	public final void setDescription(String newDescription)
	{
		description.setValue(newDescription);
	}
	
	
	/**
	 * Sets the description's icon.
	 *
	 * @param newIcon
	 *            the new icon
	 */
	public final void setIcon(FontIcon newIcon)
	{
		icon.setValue(newIcon.getHtml());
	}


	/** Initialises the description and its icon. */
	private void initialiseIconAndDesc()
	{
		description = new Label();
		icon = new Label();
		icon.setContentMode(ContentMode.HTML);
		iconAndDescWrapper = new HorizontalLayout(icon, description);

		description.setStyleName(DESCRIPTION_STYLENAME);
		icon.setStyleName(ICON_STYLENAME);
		iconAndDescWrapper.setStyleName(ICON_DESC_WRAPPER_STYLENAME);
	}



	/** The CSS class name to apply to the description label. */
	private static final String DESCRIPTION_STYLENAME =
		"WindowWithDescription-description"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the icon label. */
	private static final String ICON_STYLENAME = "WindowWithDescription-icon"; //$NON-NLS-1$

	/** The CSS class name to apply to the content component. */
	private static final String ICON_DESC_WRAPPER_STYLENAME =
		"WindowWithDescription-icon-desc-wrapper"; //$NON-NLS-1$

	/** The CSS class name to apply to the content component. */
	private static final String WRAPPER_STYLENAME =
		"WindowWithDescription-wrapper"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the window component. */
	private static final String WINDOW_STYLENAME = "WindowWithDescription"; //$NON-NLS-1$

	/** The description. */
	Label description;

	/** The icon floating next to the description. */
	Label icon;
	
	/** The description's and icon's wrapper. */
	HorizontalLayout iconAndDescWrapper;
	
	/** The iconAndDescWrapper's and content's wrapper. */
	VerticalLayout wrapper;
	
	/** The content. */
	Component content;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
