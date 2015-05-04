/**
 *
 */

package com.robotwitter.webapp.view.analysis;


/**
 * @author Doron Hogery
 *
 */

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




public class PosNegView extends AbstractView
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
	 * Creates a tweet preview component.
	 *
	 * @param tweetText
	 *            the tweet's text
	 * @param name
	 *            the tweet's text
	 * @param picture
	 *            the tweet's picture url
	 * @param screenname
	 *            the tweeter's screen name
	 *
	 * @return the tweet preview component
	 */
	final static Component createTweetPreview(
		String tweetText,
		String name,
		String picture,
		String screenname)
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
	 * Instantiates a new login view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public PosNegView(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("posNegView.page.title"));
	}


	@Override
	public final boolean isSignedInProhibited()
	{
		return false;
	}


	@Override
	public final boolean isSignedInRequired()
	{
		return false;
	}


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
	private Component wrapInPanel(Component component, String title)
	{
		final Label titleLabel = new Label(title);
		final VerticalLayout panel = new VerticalLayout(titleLabel, component);
		panel.setSizeFull();
		panel.setExpandRatio(component, 1);
		titleLabel.addStyleName(PANEL_TITLE_STYLENAME);
		panel.addStyleName(PANEL_STYLENAME);
		return panel;
	}


	@Override
	protected final void initialise()
	{
		final Table pos = new Table(messages.get("posNegView.title.pos"));
		final Table spam = new Table(messages.get("posNegView.title.spam"));
		final Table neg = new Table(messages.get("posNegView.title.neg"));
		final Label header = new Label(messages.get("posNegView.LabelHeader"));
		
		pos.addContainerProperty(
			messages.get("posNegView.Positive"),
			Component.class,
			null);
		spam.addContainerProperty(
			messages.get("posNegView.Spam"),
			Component.class,
			null);
		neg.addContainerProperty(
			messages.get("posNegView.Negative"),
			Component.class,
			null);
		pos.setSizeFull();
		pos.setDragMode(TableDragMode.ROW);
		spam.setSizeFull();
		spam.setDragMode(TableDragMode.ROW);
		neg.setSizeFull();
		neg.setDragMode(TableDragMode.ROW);
		final Component twitter1 =
			createTweetPreview(
				"Check if works",
				"Doron",
				"http://flightdiary.net/img/facebook-profile.png",
				"Hogery");
		pos.addItem(new Object[] { twitter1 }, 1);
		// pos.addItem(new Object[] { new Button("Fuck") }, 2);
		// pos.addItem(new Object[] { new Button("Shit") }, 3);
		final Component TablePanelPos =
			wrapInPanel(pos, messages.get("posNegView.empty"));
		final Component TablePanelSpam =
			wrapInPanel(spam, messages.get("posNegView.tooltip"));
		final Component TablePanelNeg =
			wrapInPanel(neg, messages.get("posNegView.empty"));
		pos.setDropHandler(new DropHandler()
		{
			@Override
			public void drop(DragAndDropEvent dropEvent)
			{
				final AbstractSelectTargetDetails dropData =
					(AbstractSelectTargetDetails) dropEvent.getTargetDetails();
				final Object targetItemId = dropData.getItemIdOver();
				
				final DataBoundTransferable t =
					(DataBoundTransferable) dropEvent.getTransferable();
				final Container sourceContainer = t.getSourceContainer();
				final Object sourceId = t.getItemId();
				final Item sourceItem = sourceContainer.getItem(sourceId);
				sourceContainer.removeItem(sourceId);
				if (sourceItem != null)
				{
					sourceContainer.removeItem(sourceId);
					pos.addItem(sourceItem);
				}
			}
			
			
			@Override
			public AcceptCriterion getAcceptCriterion()
			{
				return AcceptAll.get();
			}
		});
		
		spam.setDropHandler(new DropHandler()
		{
			@Override
			public void drop(DragAndDropEvent dropEvent)
			{
				final AbstractSelectTargetDetails dropData =
					(AbstractSelectTargetDetails) dropEvent.getTargetDetails();
				final Object targetItemId = dropData.getItemIdOver();
				
				final DataBoundTransferable t =
					(DataBoundTransferable) dropEvent.getTransferable();
				final Container sourceContainer = t.getSourceContainer();
				final Object sourceId = t.getItemId();
				final Item sourceItem = sourceContainer.getItem(sourceId);
				sourceContainer.removeItem(sourceId);
				if (sourceItem != null)
				{
					sourceContainer.removeItem(sourceId);
					if (dropData.getDropLocation() == VerticalDropLocation.BOTTOM)
					{
						spam.addItem(new Object[] { sourceItem }, 1);
					} else
					{
						final Object prevItemId = pos.prevItemId(targetItemId);
						spam.addItemAfter(prevItemId, sourceItem);
					}
				}
			}
			
			
			@Override
			public AcceptCriterion getAcceptCriterion()
			{
				return AcceptAll.get();
			}
		});
		
		neg.setDropHandler(new DropHandler()
		{
			@Override
			public void drop(DragAndDropEvent dropEvent)
			{
				final AbstractSelectTargetDetails dropData =
					(AbstractSelectTargetDetails) dropEvent.getTargetDetails();
				final Object targetItemId = dropData.getItemIdOver();
				
				final DataBoundTransferable t =
					(DataBoundTransferable) dropEvent.getTransferable();
				final Container sourceContainer = t.getSourceContainer();
				final Object sourceId = t.getItemId();
				final Item sourceItem = sourceContainer.getItem(sourceId);
				sourceContainer.removeItem(sourceId);
				if (sourceItem != null)
				{
					sourceContainer.removeItem(sourceId);
					if (dropData.getDropLocation() == VerticalDropLocation.BOTTOM)
					{
						neg.addItem(new Object[] { sourceItem }, 1);
					} else
					{
						final Object prevItemId = pos.prevItemId(targetItemId);
						neg.addItemAfter(prevItemId, sourceItem);
					}
				}
			}
			
			
			@Override
			public AcceptCriterion getAcceptCriterion()
			{
				return AcceptAll.get();
			}
		});
		// TODO this should be in CSS
		// TablePanel.setWidth("700px");
		
		final HorizontalLayout layout =
			new HorizontalLayout(TablePanelPos, TablePanelSpam, TablePanelNeg);
		layout.setSizeFull();
		layout.setSpacing(true);
		
		header.addStyleName(HEADER_STYLENAME);
		addStyleName(STYLENAME);
		setCompositionRoot(layout);
	}



	private static final int FIRSTCOLUMN = 0;

	private static final int SECONDCOLUMN = 1;

	private static final int THIRDCOLUMN = 2;

	/** The view's name. */
	public static final String NAME = "pos-neg";

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "PosNegView";

	/** The CSS class name to apply to each panel component. */
	private static final String PANEL_STYLENAME = "PosNegView-panel";

	/** The CSS class name to apply to a tweet in the preview. */
	private static final String PREVIEW_TWEET_STYLENAME =
		"PosNeg-preview-tweet";

	/** The CSS class name to apply to a tweet's picture in the preview. */
	private static final String PREVIEW_PICTURE_STYLENAME =
		"PosNeg-preview-picture";

	/** The CSS class name to apply to a tweet's name in the preview. */
	private static final String PREVIEW_NAME_STYLENAME = "PosNeg-preview-name";

	/** The CSS class name to apply to a tweet's screenname in the preview. */
	private static final String PREVIEW_SCREENNAME_STYLENAME =
		"PosNeg-preview-screenname";

	/** The CSS class name to apply to a tweet's text in the preview. */
	private static final String PREVIEW_TEXT_STYLENAME = "PosNeg-preview-text";

	/** The CSS class name to apply to the compose another button. */
	private static final String COMPOSE_ANOTHER_STYLENAME =
		"PosNeg-compose-another";

	/** The CSS class name to apply to each panel title label. */
	private static final String PANEL_TITLE_STYLENAME =
		"posNegView-panel-title";

	/** The CSS class name to apply to the header component. */
	private static final String HEADER_STYLENAME = "posNegView-header";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
