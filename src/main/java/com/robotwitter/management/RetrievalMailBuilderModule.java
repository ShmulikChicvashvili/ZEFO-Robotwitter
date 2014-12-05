/**
 * 
 */

package com.robotwitter.management;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;




/**
 * @author Itay
 *
 */
public class RetrievalMailBuilderModule extends AbstractModule
{
	
	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected void configure()
	{
		// FIXME: fix the template mail path! in the junits the working dir is
		// 5775-234311-t09 so a relative path works, but in the server the
		// working dir is the eclipse installation folder. this needs further
		// research!
		bind(IRetrievalMailBuilder.class).to(RetrievalMailBuilder.class);
		bind(String.class)
			.annotatedWith(Names.named("Retrieval Template")).toInstance("C:\\Users\\Itay\\Desktop\\TechnionDocs\\Semester7\\YearlyProject\\ProjectRepo\\5775-234311-2-t09\\src\\main\\java\\com\\robotwitter\\management\\RetrievalMailTemplate.xml"); //$NON-NLS-1$ //$NON-NLS-2$
		
	}
	
}
