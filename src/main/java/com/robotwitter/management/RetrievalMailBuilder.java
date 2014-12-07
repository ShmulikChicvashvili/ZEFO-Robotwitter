/**
 *
 */

package com.robotwitter.management;


import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.robotwitter.miscellaneous.EmailMessage;




/**
 * @author Itay
 *
 */
public class RetrievalMailBuilder implements IRetrievalMailBuilder
{
	private static final String SUBJECT_TAG = "subject"; //$NON-NLS-1$
	
	private static final String MAIL_TAG = "mail"; //$NON-NLS-1$
	
	private static final String PASSWORD_TAG = "password"; //$NON-NLS-1$
	
	
	
	/**
	 * @param templatePath
	 */
	@Inject
	public RetrievalMailBuilder(
		@Named("Retrieval Template") final String templatePath)
	{
		this.templatePath = templatePath;
	}
	
	
	@Override
	public EmailMessage buildRetrievalEmail(
		final String systemEmail,
		final String userEmail,
		final String password)
	{
		File mailXML = new File(this.templatePath);
		try
		{
			Document parsedMail = Jsoup.parse(mailXML, "UTF-8"); //$NON-NLS-1$
			parsedMail.select("br").append("\\n"); //$NON-NLS-1$ //$NON-NLS-2$
		    
			Element passwordField = parsedMail.select(PASSWORD_TAG).first();
			passwordField.text(password);
			Element subjectField = parsedMail.select(SUBJECT_TAG).first();
			Element bodyField = parsedMail.select(MAIL_TAG).first();
			
			EmailMessage $ = new EmailMessage(systemEmail, userEmail);
			$.setSubject(subjectField.text());
			$.setText(bodyField.text());
			
			return $;
			
		} catch (IOException e)
		{
			return null;
		}
	}
	
	
	
	private String templatePath;
}
