package twiryllis.api.follow;

import java.util.ArrayList;
import java.util.List;

import twiryllis.api.APIClientBase;
import twiryllis.model.AccountMasterModel;
import twitter4j.IDs;
import twitter4j.TwitterException;

/**
 * It is the base class of the API client that will follow new users or remove existing users.<br>
 * Includes generic processing such as getting follow lists and follower lists.
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class FollowAndRemoveBase extends APIClientBase {
	// followers list
	protected List<Long> followersList = new ArrayList<Long>();
	// follows list
	protected List<Long> friendsList = new ArrayList<Long>();

	public FollowAndRemoveBase(AccountMasterModel accountMasterModel) {
		super(accountMasterModel);
	}

	public enum UpdateStatus {
		FOLLOW, REMOVE
	}

	/**
	 * Load followers list
	 * 
	 * @since 0.1
	 */
	public void loadfollowersList() {
		try {
			long cursor = -1L;
			while (true) {
				IDs followers = twitter.getFollowersIDs(cursor);
				long[] ids = followers.getIDs();
				if (0 == ids.length)
					break;
				for (int i = 0; i < ids.length; i++) {
					followersList.add(ids[i]);
				}
				cursor = followers.getNextCursor();
			}
		} catch (TwitterException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Load follows list
	 * 
	 * @since 0.1
	 */
	public void loadFriendsList() {
		try {
			long cursor = -1L;
			cursor = -1L;
			while (true) {
				IDs friends = twitter.getFriendsIDs(cursor);
				long[] ids = friends.getIDs();
				if (0 == ids.length)
					break;
				for (int i = 0; i < ids.length; i++) {
					friendsList.add(ids[i]);
				}
				cursor = friends.getNextCursor();
			}
		} catch (TwitterException e) {
			log.error(e.getMessage(), e);
		}
	}

}
