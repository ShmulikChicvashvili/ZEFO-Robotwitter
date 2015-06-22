/**
 * 
 */

package com.robotwitter.webapp.control.tools;


import java.io.IOException;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;




/**
 * @author Itay
 *
 */
public class OnDemandDownloader extends FileDownloader
{
	
	/**
	 * Provide both the {@link StreamSource} ima sheli zona and the filename in an on-demand
	 * way.
	 */
	public interface OnDemandStreamResource extends StreamSource
	{
		String getFilename();
	}
	
	
	
	public OnDemandDownloader(OnDemandStreamResource onDemandStreamResource)
	{
		super(new StreamResource(onDemandStreamResource, ""));
		this.onDemandStreamResource = onDemandStreamResource;
	}
	
	
	@Override
	public boolean handleConnectorRequest(
		VaadinRequest request,
		VaadinResponse response,
		String path) throws IOException
	{
		getResource().setFilename(onDemandStreamResource.getFilename());
		return super.handleConnectorRequest(request, response, path);
	}
	
	
	private StreamResource getResource()
	{
		return (StreamResource) this.getResource("dl");
	}
	
	
	
	private static final long serialVersionUID = 1L;
	
	private final OnDemandStreamResource onDemandStreamResource;
	
}
