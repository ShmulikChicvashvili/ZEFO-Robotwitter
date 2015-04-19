/**
 * 
 */

package com.robotwitter.classification;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;




/**
 * A listener to the status stream that upon recieving a tweet, saves it in a
 * new txt file
 * 
 * @author Itay
 *
 */
public class TweetStorer implements StatusListener
{
	
	/**
	 * @param string
	 */
	public TweetStorer(String dirPath, int count)
	{
		this.count = count;
		dir = dirPath;
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.StatusListener#onDeletionNotice(twitter4j.StatusDeletionNotice) */
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.StreamListener#onException(java.lang.Exception) */
	@Override
	public void onException(Exception ex)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.StatusListener#onScrubGeo(long, long) */
	@Override
	public void onScrubGeo(long userId, long upToStatusId)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see
	 * twitter4j.StatusListener#onStallWarning(twitter4j.StallWarning) */
	@Override
	public void onStallWarning(StallWarning warning)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.StatusListener#onStatus(twitter4j.Status) */
	@Override
	public void onStatus(Status status)
	{
		if (status.getLang().equals("en") || count < 0 || !status.isRetweet()) { return; }
		
		count--;
		String fileName = ((Long) status.getId()).toString();
		System.out.println("Storing tweet "
			+ fileName
			+ ", "
			+ count
			+ " left.");
		String fullPath = dir + "\\" + fileName + ".txt";
		try
		{
			FileUtils.writeStringToFile(new File(fullPath), status.getText());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/* (non-Javadoc) @see twitter4j.StatusListener#onTrackLimitationNotice(int) */
	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	
	private int count;
	
	private String dir;
	
}
