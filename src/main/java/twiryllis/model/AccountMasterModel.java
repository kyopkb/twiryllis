package twiryllis.model;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Class that a model of the account table
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class AccountMasterModel {

	private String id;

	private String accountName;

	private String consumerKey;

	private String consumerSecret;

	private String accessToken;

	private String accessTokenSecret;

	private Twitter twitter;

	public AccountMasterModel(String accountName) {
		this.accountName = accountName;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Twitter getTwitter() {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true).setOAuthConsumerKey(this.consumerKey)
				.setOAuthConsumerSecret(this.consumerSecret).setOAuthAccessToken(this.accessToken)
				.setOAuthAccessTokenSecret(this.accessTokenSecret);
		TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
		twitter = twitterFactory.getInstance();

		return twitter;
	}

	public User getUser() {

		if (null == twitter)
			getTwitter();

		User user = null;
		try {
			user = twitter.verifyCredentials();
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return user;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
