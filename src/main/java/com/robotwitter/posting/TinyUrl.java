/**
 *
 */

package com.robotwitter.posting;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;




/**
 * @author Shmulik
 *
 */
public class TinyUrl
{
	
	/**
	 * @param longUrl
	 *            The url which want to compress
	 * @return The compressed url
	 */
	@SuppressWarnings("nls")
	public static String getTinyUrl(String longUrl)
	{
		if (longUrl == null) { return null; }
		
		String $ = "";
		
		final String tinyUrlServiceUri =
			"http://tinyurl.com/api-create.php?url=";
		try
		{
			final URL url = new URL(tinyUrlServiceUri + longUrl);
			final BufferedReader in =
				new BufferedReader(new InputStreamReader(url.openStream()));
			
			String str;
			while ((str = in.readLine()) != null)
			{
				$ += str;
			}
			
			in.close();
			
		} catch (final MalformedURLException e)
		{
			return null;
		} catch (final IOException e)
		{
			return null;
		}
		
		return $;
	}
	
}
