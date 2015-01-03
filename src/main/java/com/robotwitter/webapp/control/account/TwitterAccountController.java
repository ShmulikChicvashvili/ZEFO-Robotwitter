
package com.robotwitter.webapp.control.account;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;




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
	public final Map<Date, Integer> getAmountOfFollowers(Date from, Date to)
	{
		// TODO Hogery use this example to generate the map
		
		Map<Date, Integer> followers = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		
		// For example, two dates, the first being:
		calendar.set(Calendar.YEAR, 1993);
		calendar.set(Calendar.MONTH, Calendar.JUNE);
		calendar.set(Calendar.DATE, 24);
		calendar.set(Calendar.HOUR_OF_DAY, 13);
		calendar.set(Calendar.MINUTE, 49);
		calendar.set(Calendar.SECOND, 35);
		// Obviously, you don't need to set everything. Just what you can.
		// (If database has only hour precision now, don't set minutes, and
		// seconds)
		Date date1 = calendar.getTime();
		int followers1 = 9000;
		followers.put(date1, Integer.valueOf(followers1));
		
		// For example, the second date can be
		calendar.clear(); // First clear the previous date
		calendar.set(1993, 6, 25);	// Then set 25 June, 1993 (for example)
		calendar.set(Calendar.HOUR_OF_DAY, 13); // I can then set hour too
		// See that I can set year, month, and date in one method
		Date date2 = calendar.getTime();
		int followers2 = 9001;
		followers.put(date2, Integer.valueOf(followers2));
		
		return followers;
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
