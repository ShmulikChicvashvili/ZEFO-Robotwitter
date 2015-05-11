
package com.robotwitter.webapp.util.tweeting;


import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * A preview of a single Tweet (or a Tweet with replies).
 *
 * @author Eyal
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
		String converted;
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
	 * Strudel to twitter html links.
	 *
	 * @param string
	 *            the string
	 * @return the string
	 */
	private static String strudelToTwitterHtmlLinks(String string)
	{
		String converted;
		while (true)
		{
			converted =
				string.replaceAll(
					"(^|\\s)@(\\w*[a-zA-Z_]+\\w*)($|\\s)",
					"$1<a class=\"hashtag\" href=\"http://twitter.com/$2\""
						+ " target=\"_blank\" >"
						+ "@<span>$2</span></a>$3");
			if (converted.equals(string))
			{
				break;
			}
			string = converted;
		}
		
		return converted;
	}
	
	
	/**
	 * Instantiates a new tweet preview.
	 *
	 * @param messages
	 *            the messages
	 */
	public TweetPreview()
	{
		super(null);
		
		initialiseLayout();
		
		getUserSession().observeActiveTwitterAccount(this);
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.util.RobotwitterCustomComponent#
	 * activateTwitterAccount(long) */
	@Override
	public final void activateTwitterAccount(long id)
	{
		activateTwitterAccount();
	}
	
	
	/**
	 * Update the preview with the given tweets.
	 *
	 * @param tweets
	 *            the tweets to be shown. Does not validate any restrictions
	 *            that might be.
	 */
	public final void updatePreview(List<String> tweets)
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
	 * Alter the preview to correspond with the newly chosen account.
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
		preview.addStyleName(STYLENAME);
		
		setCompositionRoot(preview);
		
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
		tweetHtml = strudelToTwitterHtmlLinks(tweetHtml);
		
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
		
		pictureImage.addStyleName(PICTURE_STYLENAME);
		nameButton.addStyleName(NAME_STYLENAME);
		nameButton.addStyleName(ValoTheme.BUTTON_LINK);
		screennameLabel.addStyleName(SCREENNAME_STYLENAME);
		text.addStyleName(TEXT_STYLENAME);
		layout.setStyleName(TWEET_STYLENAME);
		
		return layout;
	}
	
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The CSS class name to apply to the preview component. */
	private static final String STYLENAME = "TweetPreview";
	
	/** The CSS class name to apply to a tweet in the preview. */
	private static final String TWEET_STYLENAME = "TweetPreview-tweet";
	
	/** The CSS class name to apply to a tweet's picture in the preview. */
	private static final String PICTURE_STYLENAME = "TweetPreview-picture";
	
	/** The CSS class name to apply to a tweet's name in the preview. */
	private static final String NAME_STYLENAME = "TweetPreview-name";
	
	/** The CSS class name to apply to a tweet's screenname in the preview. */
	private static final String SCREENNAME_STYLENAME =
		"TweetPreview-screenname";
	
	/** The CSS class name to apply to a tweet's text in the preview. */
	private static final String TEXT_STYLENAME = "TweetPreview-text";
	
	/** The Tweet preview component. */
	private VerticalLayout preview;
	
	/** The active Twitter follower's picture image. */
	String picture;
	
	/** The active Twitter follower's name label. */
	String name;
	
	/** The active Twitter follower's screenname label. */
	String screenname;
}
