/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.robotwitter.posting.PostingTweetUtils;




/**
 * @author Shmulik
 *
 */
public class PostingTweetUtilsTest
{

	@Test
	public void testBreakToTweets()
	{
		final String stringOfSizeTen = "abcdefghij";
		final ArrayList<String> result =
			PostingTweetUtils.breakToTweets(stringOfSizeTen, 10);
		for (final String s : result)
		{
			System.out.println(s);
		}
		
		assertEquals(1, PostingTweetUtils
			.breakToTweets(stringOfSizeTen, 10)
			.size());
		assertEquals(
			stringOfSizeTen,
			PostingTweetUtils.breakToTweets(stringOfSizeTen, 10).get(0));
		
		final String stringOfSizeTwenty = "abcdefghijabcdefghij";
		assertEquals(1, PostingTweetUtils
			.breakToTweets(stringOfSizeTwenty, 10)
			.size());
		assertEquals(
			stringOfSizeTen,
			PostingTweetUtils.breakToTweets(stringOfSizeTwenty, 10).get(0));
		
		final String stringOfSizeTwentyWithWhiteSpaces =
			"abcdefghij abcdefghij";
		assertEquals(
			2,
			PostingTweetUtils.breakToTweets(
				stringOfSizeTwentyWithWhiteSpaces,
				10).size());
		assertEquals(
			stringOfSizeTen,
			PostingTweetUtils.breakToTweets(
				stringOfSizeTwentyWithWhiteSpaces,
				10).get(0));
		assertEquals(
			stringOfSizeTen,
			PostingTweetUtils.breakToTweets(
				stringOfSizeTwentyWithWhiteSpaces,
				10).get(1));
	}

}
