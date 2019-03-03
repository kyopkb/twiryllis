package twiryllis.api.search;

import java.util.ArrayList;
import java.util.List;

import twiryllis.api.APIClientBase;
import twiryllis.model.AccountMasterModel;
import twiryllis.model.TweetTransactionModel;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Class that execute API related to searching tweet
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class TweetSearchClient extends APIClientBase {

	public TweetSearchClient(AccountMasterModel accountMasterModel) {
		super(accountMasterModel);
	}

	/**
	 * Search tweets based on keywords
	 * 
	 * @param keyword  the Keywords used in search
	 * @param limit  the maximum number of acquisitions
	 * @return list of searched tweets
	 * @since 0.1
	 */
	public List<TweetTransactionModel> searchTweet(String keyword, int limit) {
		Query query = new Query();
		query.setQuery(keyword);
		query.setCount(limit);
		List<TweetTransactionModel> tweetModelList = new ArrayList<TweetTransactionModel>();

		QueryResult result;
		try {
			result = twitter.search(query);
			log.info("Search results " + result.getTweets().size());

			for (Status tweet : result.getTweets()) {
				TweetTransactionModel tweetModel = new TweetTransactionModel();
				tweetModel.setAccountId(accountMasterModel.getId());
				tweetModel.setUserId(tweet.getUser().getId());
				tweetModel.setName(twitter.showUser(tweet.getUser().getId()).getScreenName());
				tweetModel.setTweet(tweet.getText());
				tweetModel.setTweetDate(tweet.getCreatedAt());
				tweetModelList.add(tweetModel);
			}
		} catch (TwitterException e) {
			log.error(e.getErrorMessage(), e);
			return null;
		}
		return tweetModelList;
	}

}
