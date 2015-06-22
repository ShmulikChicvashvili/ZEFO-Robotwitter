
package com.robotwitter.webapp.util;


import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontIcon;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;




/**
 * Represents a window with a description section.
 * <p>
 * Apart from displaying a chosen description, the window supports displaying an
 * icon ({@link FontIcon}) using {@link #setIcon}.
 *
 * @author Hagai Akibayov
 */
public class WindowWithDescription extends Window
{
	
	/** Instantiates a new window with description. */
	public WindowWithDescription()
	{
		initialiseIconAndDesc();
		initialiseContentAndWrapper();
		setDefaults();
		
		setStyleName(STYLENAME);
	}
	
	
	/** Removes the window's content (the description is left unaffected). */
	public final void removeContent()
	{
		wrapper.removeComponent(content);
	}
	
	
	@Override
	public final void setContent(Component newContent)
	{
		// When called from superclass' (com.vaadin.ui.Window) constructor.
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
	
	
	/** Initialises the content and the window's wrapper. */
	private void initialiseContentAndWrapper()
	{
		content = new Label();
		content.setVisible(false);
		wrapper = new VerticalLayout(iconAndDescWrapper, content);
		wrapper.setSpacing(true);
		super.setContent(wrapper);

		wrapper.setStyleName(WRAPPER_STYLENAME);
	}
	
	
	/** Initialises the description and its icon. */
	private void initialiseIconAndDesc()
	{
		description = new Label();
		icon = new Label();
		description.setContentMode(ContentMode.HTML);
		icon.setContentMode(ContentMode.HTML);

		// in mobile, use a vertical layout, in desktop, use horizontal
		if (((AbstractUI) UI.getCurrent()).isMobile())
		{
			iconAndDescWrapper = new VerticalLayout(icon, description);
		} else
		{
			iconAndDescWrapper = new HorizontalLayout(icon, description);
		}

		description.setStyleName(DESCRIPTION_STYLENAME);
		icon.setStyleName(ICON_STYLENAME);
		iconAndDescWrapper.setStyleName(ICON_DESC_WRAPPER_STYLENAME);
	}


	/** Set default properties. */
	private void setDefaults()
	{
		setModal(true);
		setCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		center();
	}



	/** The CSS class name to apply to the description label. */
	private static final String DESCRIPTION_STYLENAME =
		"WindowWithDescription-description";
	
	/** The CSS class name to apply to the content component. */
	private static final String ICON_DESC_WRAPPER_STYLENAME =
		"WindowWithDescription-icon-desc-wrapper";
	
	/** The CSS class name to apply to the icon label. */
	private static final String ICON_STYLENAME = "WindowWithDescription-icon";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "WindowWithDescription";
	
	/** The CSS class name to apply to the content component. */
	private static final String WRAPPER_STYLENAME =
		"WindowWithDescription-wrapper";
	
	/** The content. */
	Component content;
	
	/** The description. */
	Label description;
	
	/** The icon floating next to the description. */
	Label icon;
	
	/** The description's and icon's wrapper. */
	Layout iconAndDescWrapper;

	/** The iconAndDescWrapper's and content's wrapper. */
	VerticalLayout wrapper;

}
