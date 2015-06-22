
package com.robotwitter.webapp.view.analysis;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.control.tools.OnDemandDownloader;
import com.robotwitter.webapp.control.tools.OnDemandDownloader.OnDemandStreamResource;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/**
 * Analysis view.
 *
 * @author Eyal Tolchinsky
 * @author Hagai Akibayov
 */
public class AnalysisView extends AbstractView
{

	/**
	 * Instantiates a new login view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public AnalysisView(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("AnalysisView.page.title"));
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
	
	
	private OnDemandStreamResource createDownloadResource() {
        return new OnDemandStreamResource()
		{
			@Override
			public String getFilename()
			{
				return getUserSession().getAccountController().getActiveTwitterAccount().getExportedDatabaseName();
			}


			@Override
			public InputStream getStream()
			{
				byte[] file = getUserSession().getAccountController().getActiveTwitterAccount().getExportedDatabase();
                InputStream input = new ByteArrayInputStream(file);
                return input;
			}
			
			
			private static final long serialVersionUID = 1L;
		};
    }
	
	@Override
	protected final void initialise()
	{
		Label header = new Label(messages.get("AnalysisView.label.header"));
		FollowersAmountOverview overview =
			new FollowersAmountOverview(messages);
		AnalysisTabs tabs = new AnalysisTabs(messages);
		
		Button downloadButton = new Button(messages.get("AnalysisView.label.download-csv"));
		downloadButton.setIcon(FontAwesome.DOWNLOAD);

        OnDemandStreamResource myResource = createDownloadResource();
        FileDownloader fileDownloader = new OnDemandDownloader(myResource);
        fileDownloader.extend(downloadButton);

		VerticalLayout layout = new VerticalLayout(header, overview, tabs,downloadButton);

		header.addStyleName(HEADER_STYLENAME);
		tabs.addStyleName(TABS_STYLENAME);
		downloadButton.addStyleName(BUTTON_STYLENAME);
		addStyleName(STYLENAME);

		setCompositionRoot(layout);
	}



	/** The view's name. */
	public static final String NAME = "analysis";

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "AnalysisView";
	
	/** The CSS class name to apply to the header component. */
	private static final String HEADER_STYLENAME = "AnalysisView-header";
	
	/** The CSS class name to apply to the header component. */
	private static final String TABS_STYLENAME = "AnalysisView-tabs";
	
	/** The CSS class name to apply to the header component. */
	private static final String BUTTON_STYLENAME = "AnalysisView-button";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
