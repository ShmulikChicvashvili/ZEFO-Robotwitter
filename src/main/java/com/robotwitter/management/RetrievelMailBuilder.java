/**
 *
 */

package com.robotwitter.management;


import com.google.inject.Inject;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.TemplateMail;
import com.robotwitter.miscellaneous.TemplateMailReader;




/**
 * @author Itay
 *
 */
public class RetrievelMailBuilder
{
	@Inject
	public RetrievelMailBuilder(
		final String templatePath,
		final TemplateMailReader reader)
	{
		this.templatePath = templatePath;
		templateReader = reader;
	}
	
	
	public EmailMessage buildRetrievalEmail(
		final String systemEmail,
		final String userEmail,
		final String password)
	{
		final TemplateMail mail = templateReader.ReadTemplateMail(templatePath);
		mail.setProperty("password", password);
		final EmailMessage $ = new EmailMessage(systemEmail, userEmail);
		$.setSubject(mail.getSubject());
		$.setText(mail.getText());

		return $;
	}
	
	
	
	String templatePath;

	TemplateMailReader templateReader;
}
