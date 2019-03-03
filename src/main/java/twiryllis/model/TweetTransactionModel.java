package twiryllis.model;

import java.util.Date;

/**
 * Class that a model of the tweet table
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class TweetTransactionModel {

	private String accountId;
	private Long userId;
	private String name;
	private String tweet;
	private Date tweetDate;
	private Date createDate;
	private boolean followed;
	private boolean deleted;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String userId) {
		this.accountId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public Date getTweetDate() {
		return tweetDate;
	}

	public void setTweetDate(Date tweetDate) {
		this.tweetDate = tweetDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isFollowed() {
		return followed;
	}

	public void setFollowed(boolean followed) {
		this.followed = followed;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
