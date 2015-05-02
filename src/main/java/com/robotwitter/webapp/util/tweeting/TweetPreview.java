/**
 *
 */

package com.robotwitter.webapp.util.tweeting;


import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
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
 * @author Eyal
 *
 */
public class TweetPreview extends RobotwitterCustomComponent
{

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
	 * @param messages
	 */
	public TweetPreview(IMessagesContainer messages)
	{
		super(messages);

		initialiseLayout();

		getUserSession().observeActiveTwitterAccount(this);
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.util.RobotwitterCustomComponent#
	 * activateTwitterAccount(long) */
	@Override
	public void activateTwitterAccount(long id)
	{
		activateTwitterAccount();
	}


	public void updatePreview(List<String> tweets)
	{
		preview.removeAllComponents();
		for (final String tweetText : tweets)
		{
			preview.addComponent(createTweetPreview(tweetText));
		}

		if (tweets.isEmpty())
		{
			preview.addComponent(createTweetPreview(""));
		}
	}


	/**
	 *
	 */
	private void activateTwitterAccount()
	{
		final ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();

		picture = controller.getImage();
		name = controller.getName();
		screenname = controller.getScreenname();
	}


	/** Initialises the Tweet preview component. */
	private void initialiseLayout()
	{
		preview = new VerticalLayout();
		preview.setCaption(messages.get("TweetComposer.caption.preview"));
		preview.setIcon(FontAwesome.PAPER_PLANE_O);
		preview.addStyleName(PREVIEW_STYLENAME);

		activateTwitterAccount();
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
		final Image pictureImage = new Image();
		pictureImage.setSource(new ExternalResource(picture));
		pictureImage.setAlternateText(name);
		final Button nameButton = new Button(name);
		final Label screennameLabel = new Label('@' + screenname);

		String tweetHtml = StringEscapeUtils.escapeHtml4(tweetText);

		tweetHtml = hashtagsToTwitterHtmlLinks(tweetHtml);

		final Label text = new Label(tweetHtml, ContentMode.HTML);

		final BrowserWindowOpener opener =
			new BrowserWindowOpener("https://twitter.com/" + screenname);
		opener.extend(nameButton);

		final HorizontalLayout nameAndScreenname =
			new HorizontalLayout(nameButton, screennameLabel);
		final VerticalLayout right =
			new VerticalLayout(nameAndScreenname, text);
		final HorizontalLayout layout =
			new HorizontalLayout(pictureImage, right);

		nameAndScreenname.setSizeFull();
		right.setSizeFull();
		layout.setSizeFull();
		layout.setExpandRatio(right, 1);

		layout.setSpacing(true);

		pictureImage.addStyleName(PREVIEW_PICTURE_STYLENAME);
		nameButton.addStyleName(PREVIEW_NAME_STYLENAME);
		nameButton.addStyleName(ValoTheme.BUTTON_LINK);
		screennameLabel.addStyleName(PREVIEW_SCREENNAME_STYLENAME);
		text.addStyleName(PREVIEW_TEXT_STYLENAME);
		layout.setStyleName(PREVIEW_TWEET_STYLENAME);

		return layout;
	}


	/**
	 *
	 */
	private static final long serialVersionUID = -381333812459248416L;



	/** The CSS class name to apply to the preview component. */
	private static final String PREVIEW_STYLENAME = "TweetComposer-preview";

	/** The CSS class name to apply to a tweet in the preview. */
	private static final String PREVIEW_TWEET_STYLENAME =
		"TweetComposer-preview-tweet";

	/** The CSS class name to apply to a tweet's picture in the preview. */
	private static final String PREVIEW_PICTURE_STYLENAME =
		"TweetComposer-preview-picture";

	/** The CSS class name to apply to a tweet's name in the preview. */
	private static final String PREVIEW_NAME_STYLENAME =
		"TweetComposer-preview-name";

	/** The CSS class name to apply to a tweet's screenname in the preview. */
	private static final String PREVIEW_SCREENNAME_STYLENAME =
		"TweetComposer-preview-screenname";

	/** The CSS class name to apply to a tweet's text in the preview. */
	private static final String PREVIEW_TEXT_STYLENAME =
		"TweetComposer-preview-text";

	/** The Tweet preview component. */
	private VerticalLayout preview;

	/** The active Twitter follower's picture image. */
	String picture;

	/** The active Twitter follower's name label. */
	String name;

	/** The active Twitter follower's screenname label. */
	String screenname;
}
