package twiryllis.api.follow;

import java.util.ArrayList;
import java.util.List;

import twiryllis.dao.TweetTransactionStore;
import twiryllis.model.AccountMasterModel;
import twiryllis.model.TweetTransactionModel;
import twitter4j.TwitterException;

/**
 * Class that executes API related to user's follow
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class AutoFollowClient extends FollowAndRemoveBase {

	public AutoFollowClient(AccountMasterModel accountMasterModel) {
		super(accountMasterModel);
	}

	/**
	 * Follow the users included in the tweet list obtained by retrieval
	 * 
	 * @param tweetModelList  the tweet list obtained by search
	 * @since 0.1
	 */
	public void executeFollowFromSearchResult(List<TweetTransactionModel> tweetModelList) {
		List<Long> followList = new ArrayList<Long>();
		for (TweetTransactionModel tweetModel : tweetModelList) {
			followList.add(tweetModel.getUserId());
		}
		executeFollow(followList);
	}

	/**
	 * Follow the users included in the tweet list obtained by retrieval
	 * 
	 * @param followList  the List of userId to be followed
	 * @since 0.1
	 */
	public void executeFollow(List<Long> followList) {
		loadFriendsList();

		TweetTransactionStore tts = new TweetTransactionStore();
		int newFollowCount = 0;
		for (Long userId : followList) {
			if (!friendsList.contains(userId)) {
				try {
					if (tts.updateFollowedStatus(userId, UpdateStatus.FOLLOW)) {
						twitter.createFriendship(userId);
						friendsList.add(userId);
						log.info("Followed the user: " + twitter.showUser(userId).getScreenName());
					} else
						continue;
				} catch (TwitterException e) {
					log.error(e.getMessage(), e);
					tts.updateFollowedStatus(userId, UpdateStatus.REMOVE);
					continue;
				}
				newFollowCount++;
			} else {
				// Update the status of the user you have already followed
				tts.updateFollowedStatus(userId, UpdateStatus.FOLLOW);
				tts.updateSentDMStatus(userId);
			}
		}
		tts.close();
		log.info("Followed " + newFollowCount + " new users.");
	}

	/**
	 * Follow the users that are included in the follower list, but have not
	 * followed up yet.
	 * 
	 * @since 0.1
	 */
	public void executeFollowBack() {
		loadfollowersList();
		loadFriendsList();

		// Follow the user who is not following from the follower list.
		int newFollowCount = 0;
		for (Long userId : followersList) {
			if (!friendsList.contains(userId)) {
				try {
					twitter.createFriendship(userId);
					log.info("Followed the user: " + twitter.showUser(userId).getScreenName());
				} catch (TwitterException e) {
					log.error(e.getMessage(), e);
				}
				newFollowCount++;
			}
		}
		log.info("Followed " + newFollowCount + " new users.");
	}

}
