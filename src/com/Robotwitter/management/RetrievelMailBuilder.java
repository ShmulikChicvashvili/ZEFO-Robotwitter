/**
 * 
 */
package com.Robotwitter.management;

import com.Robotwitter.miscellaneous.EmailMessage;
import com.Robotwitter.miscellaneous.TemplateMail;
import com.Robotwitter.miscellaneous.TemplateMailReader;

/**
 * @author Itay
 *
 */
public class RetrievelMailBuilder
{	
	String templatePath;
	TemplateMailReader templateReader;
	
	public RetrievelMailBuilder(String templatePath, TemplateMailReader reader) {
		this.templatePath = templatePath;
		this.templateReader = reader;
	}
	
	public EmailMessage buildRetrievalEmail(String systemEmail,String userEmail,String password) {
		TemplateMailReader reader = new TemplateMailReader(); //TODO: fix this new with factory
		TemplateMail mail = reader.ReadTemplateMail(this.templatePath);
		mail.setProperty("password",password);
		EmailMessage $ = new EmailMessage(systemEmail,userEmail);
		$.setSubject(mail.getSubject());
		$.setText(mail.getText());
		
		return $;
	}
}
