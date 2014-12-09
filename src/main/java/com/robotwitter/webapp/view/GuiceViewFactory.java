
package com.robotwitter.webapp.view;


import com.google.inject.Injector;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;

import com.robotwitter.webapp.ViewsMap;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * A creator of views using a Guice {@link Injector}.
 * <p>
 * This factory acts as a view provider for a
 * {@link com.vaadin.navigator.Navigator} that dynamically creates a new view on
 * request ({@link #getView}).
 *
 * @author Itay Khazon
 */
public class GuiceViewFactory implements ViewProvider
{
	
	/**
	 * Instantiates a new view factory.
	 *
	 * @param views
	 *            a mapping of all accessible views
	 * @param injector
	 *            should contain resolved views as dependencies, i.e., using
	 *            {@link Injector#getInstance(Class)} should return a <b>new</b>
	 *            instance of the requested view class
	 */
	public GuiceViewFactory(ViewsMap views, Injector injector)
	{
		viewInjector = injector;
		this.views = views;
	}
	
	
	@Override
	public final View getView(String viewName)
	{
		Class<? extends View> viewClass = views.get(viewName);
		return viewInjector.getInstance(viewClass);
	}
	
	
	@Override
	public final String getViewName(String viewAndParameters)
	{
		// If the view exists (when there are no parameters)
		if (views.containsKey(viewAndParameters)) { return viewAndParameters; }
		
		// If the view doesn't have parameters, it doesn't exist
		int indexOfParameters = viewAndParameters.indexOf('/');
		if (indexOfParameters == -1) { return null; }

		// If the view does have parameters, and its base name exists
		String base = viewAndParameters.substring(0, indexOfParameters - 1);
		if (views.containsKey(base)) { return base; }
		
		// The view -- with or without parameters -- doesn't exist
		return null;
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** A mapping of all accessible views. */
	ViewsMap views;

	/** A Guice injector used to create the view's instances. */
	@SuppressFBWarnings("SE_BAD_FIELD")
	private final Injector viewInjector;
}
