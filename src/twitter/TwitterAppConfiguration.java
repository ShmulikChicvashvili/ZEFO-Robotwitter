/**
 * 
 */

package twitter;

import twitter4j.Twitter;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Itay
 *
 */
public class TwitterAppConfiguration
{
	//TODO: allow to change and configure these values
	String consumerKey = "0kkK9O83YyDRC3HkOP97HFiIi";
	String consumerSecret = "13wJ8y3gu4epaM9vqJFuWUa0MNE8IfCDYmBdcKE0NfT3RWbM9M";
	
	Configuration conf;
	
	public TwitterAppConfiguration(String consumerKey,String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(this.consumerKey);
		cb.setOAuthConsumerSecret(this.consumerSecret);
		conf = cb.build();
		conf.getOAuthRequestTokenURL();
	}
	
	public Configuration getConfiguration() {
		return conf;
	}
}
