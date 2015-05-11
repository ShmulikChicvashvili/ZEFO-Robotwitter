/**
 * 
 */

package com.robotwitter.classification;

import java.util.ArrayList;


/**
 * @author Itay
 *
 */
public class SimpleTweetClassifier implements ITweetClassifier
{
	
	/**
	 * 
	 */
	public SimpleTweetClassifier()
	{
		badList.add("hate");
		badList.add("fuck");
		badList.add("bad");
		badList.add("die");
		badList.add("crap");
		badList.add("shit");
		badList.add("improve");
		
		goodList.add("love");
		goodList.add("good");
		goodList.add("great");
		goodList.add("excellent");
		goodList.add("awesome");
		goodList.add("amazing");
		goodList.add("recommend");
	}
	
	/* (non-Javadoc) @see
	 * com.robotwitter.classification.ITweetClassifier#classify
	 * (java.lang.String) */
	@Override
	public String classify(String text)
	{
		int badCounter = 0;
		for (String badWord : badList)
		{
			if (text.toLowerCase().contains(badWord))
			{
				badCounter++;
			}
		}
		
		for (String goodWord : goodList)
		{
			if (text.toLowerCase().contains(goodWord))
			{
				badCounter--;
			}
		}
		
		if(badCounter > 0) {
			return "neg";
		}
		return "pos";
	}

	private ArrayList<String> badList = new ArrayList<String>();
	
	private ArrayList<String> goodList = new ArrayList<String>();
}
