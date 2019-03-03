package twiryllis.api.follow;

import twiryllis.dao.TweetTransactionStore;
import twiryllis.model.AccountMasterModel;
import twitter4j.TwitterException;

/**
 * Class that executes API related to user's remove
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class AutoRemoveClient extends FollowAndRemoveBase {

	public AutoRemoveClient(AccountMasterModel accountMasterModel) {
		super(accountMasterModel);
	}

	/**
	 * Remove users that are included in the follow list, but are not included in
	 * the follower list.
	 * 
	 * @since 0.1
	 */
	public boolean executeAutoRemove() {
		loadfollowersList();
		loadFriendsList();

		// Remove users not following from followerlist
		TweetTransactionStore tts = new TweetTransactionStore();
		int newRemoveCount = 0;
		for (Long userId : friendsList) {
			if (!followersList.contains(userId)) {
				try {
					if (tts.updateFollowedStatus(userId, UpdateStatus.REMOVE)) {
						twitter.destroyFriendship(userId);
						log.info("Removed the user: " + twitter.showUser(userId).getScreenName());
					} else
						break;
				} catch (TwitterException e) {
					log.error(e.getMessage(), e);
					tts.updateFollowedStatus(userId, UpdateStatus.FOLLOW);
					return false;
				}
				newRemoveCount++;
			}
		}
		tts.close();
		log.info("Removed " + newRemoveCount + " new users.");
		return true;
	}

}
