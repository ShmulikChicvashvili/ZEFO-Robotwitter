/**
 * 
 */

package com.robotwitter.classification;


import java.io.File;
import java.io.IOException;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;
import com.google.inject.Inject;
import com.google.inject.name.Named;




/**
 * @author Itay
 *
 */
public class SentimentClassifier implements ITweetClassifier
{
	@Inject
	public SentimentClassifier(@Named("Classifier Filepath") String classifierFilepath)
	{
		try
		{
			classifier =
				(LMClassifier) AbstractExternalizable.readObject(new File(
					classifierFilepath));
			categories = classifier.categories();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String classify(String text)
	{
		ConditionalClassification classification = classifier.classify(text);
		return classification.bestCategory();
	}
	
	
	
	String[] categories;
	
	LMClassifier classifier;
}
