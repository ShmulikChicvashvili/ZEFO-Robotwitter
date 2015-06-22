package com.robotwitter.webapp.view.scheduling;

import java.io.Serializable;

import org.apache.commons.lang3.StringEscapeUtils;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractUI;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;
import com.robotwitter.webapp.view.IUserSession;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class SchedulePreview extends RobotwitterCustomComponent {

	/** Represents a Tweet's character count. */
	static class CharacterCount implements Serializable
	{
		/** The count. */
		public int count;

		/** Serialisation version unique ID. */
		private static final long serialVersionUID = 1L;
	}
		
	public SchedulePreview(IMessagesContainer messages) {
		super(messages);
		
		count = new CharacterCount();
		max = new CharacterCount();
		
		initialiseLayout();
		getUserSession().observeActiveTwitterAccount(this);
	}

	/** Updates state based on the active Twitter account. */
	private void updateBasedOnActiveTwitterAccount()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();

		picture = controller.getImage();
		name = controller.getName();
		screenname = controller.getScreenname();

		max.count = controller.getCurrentMaximumTweetLength();
	}
	
	/**
	 * Converts all the hashtags in the given string to HTML anchor links.
	 * <p>
	 * For example, if the hashtag #dussan exists in the given string, it will
	 * be converted to an HTML anchor linking to:
	 * https://twitter.com/hashtag/dussan
	 *
	 * @param string
	 *            the input string to convert
	 *
	 * @return the converted string
	 */
	private static String hashtagsToTwitterHtmlLinks(String string)
	{
		String converted = string;
		while (true)
		{
			converted =
				string.replaceAll(
					"(^|\\s)#(\\w*[a-zA-Z_]+\\w*)($|\\s)",
					"$1<a class=\"hashtag\" href=\"http://twitter.com/hashtag/$2\""
						+ " target=\"_blank\" >"
						+ "#<span>$2</span></a>$3");
			if (converted.equals(string))
			{
				break;
			}
			string = converted;
		}

		return converted;
	}
	
	/**
	 * Creates a tweet preview component.
	 *
	 * @param tweetText
	 *            the tweet's text
	 *
	 * @return the tweet preview component
	 */
	final Component createTweetPreview(String tweetText)
	{
		Image pictureImage = new Image();
		pictureImage.setSource(new ExternalResource(picture));
		pictureImage.setAlternateText(name);
		Button nameButton = new Button(name);
		Label screennameLabel = new Label('@' + screenname);
		
		String tweetHtml = StringEscapeUtils.escapeHtml4(tweetText);

		tweetHtml = hashtagsToTwitterHtmlLinks(tweetHtml);
		
		Label text = new Label(tweetHtml, ContentMode.HTML);
		
		BrowserWindowOpener opener =
			new BrowserWindowOpener("https://twitter.com/" + screenname);
		opener.extend(nameButton);
		
		HorizontalLayout nameAndScreenname =
			new HorizontalLayout(nameButton, screennameLabel);
		VerticalLayout right = new VerticalLayout(nameAndScreenname, text);
		HorizontalLayout layout = new HorizontalLayout(pictureImage, right);

		nameAndScreenname.setSizeFull();
		right.setSizeFull();
		layout.setSizeFull();
		layout.setExpandRatio(right, 1);

		layout.setSpacing(true);
		
		return layout;
	}
	
	/** Initialises the Tweet preview component. */
	private void initialisePreview()
	{
		preview = new VerticalLayout();
		preview.setCaption(messages.get("ScheduleView.caption.preview"));
		preview.setIcon(FontAwesome.PAPER_PLANE_O);
	}
	
	/** Initialises the layout. */
	private void initialiseLayout()
	{
		initialisePreview();
		updateBasedOnActiveTwitterAccount();
		VerticalLayout layout = new VerticalLayout(preview);
		layout.setSizeFull();
		setCompositionRoot(layout);
	}
	
	/** The Tweet preview component. */
	private VerticalLayout preview;
	
	/** The current character count of the Tweet. */
	CharacterCount count;
	
	/** The maximum allowed character count of the Tweet. */
	CharacterCount max;
	
	/** The active Twitter follower's picture image. */
	String picture;
	
	/** The active Twitter follower's name label. */
	String name;
	
	/** The active Twitter follower's screenname label. */
	String screenname;

}
