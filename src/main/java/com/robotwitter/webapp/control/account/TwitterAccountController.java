
package com.robotwitter.webapp.control.account;


/**
 * A simple implementation of the Twitter account controller interface.
 *
 * @author Hagai Akibayov
 */
public class TwitterAccountController implements ITwitterAccountController
{

	/**
	 * Instantiates a new twitter account controller.
	 *
	 * @param id
	 *            the Twitter account's ID
	 * @param name
	 *            the Twitter account's name
	 * @param screenname
	 *            the Twitter account's screenname
	 * @param image
	 *            the Twitter account's image
	 */
	TwitterAccountController(
		long id,
		String name,
		String screenname,
		String image)
	{
		this.id = id;
		this.name = name;
		this.screenname = screenname;
		this.image = image;
	}
	
	
	@Override
	public final long getID()
	{
		return id;
	}
	
	
	@Override
	public final String getImage()
	{
		return image;
	}
	
	
	@Override
	public final String getName()
	{
		return name;
	}


	@Override
	public final String getScreenname()
	{
		return screenname;
	}



	/** The Twitter accounts' ID. */
	public long id;
	
	/** The Twitter accounts' name. */
	public String name;
	
	/** The Twitter accounts' screenname. */
	public String screenname;
	
	/** The Twitter accounts' profile image. */
	public String image;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
