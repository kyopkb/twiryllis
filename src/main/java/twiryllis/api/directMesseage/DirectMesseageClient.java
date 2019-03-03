package twiryllis.api.directMesseage;

import java.util.ArrayList;
import java.util.List;

import twiryllis.api.APIClientBase;
import twiryllis.dao.TweetTransactionStore;
import twiryllis.model.AccountMasterModel;
import twiryllis.model.TweetTransactionModel;
import twitter4j.TwitterException;

/**
 * Class that executes API related to direct message
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class DirectMesseageClient extends APIClientBase {

	public DirectMesseageClient(AccountMasterModel accountMasterModel) {
		super(accountMasterModel);
	}

	/**
	 * Send direct messeage included in the tweet list obtained by retrieval
	 * 
	 * @param tweetModelList  the tweet list obtained by search
	 * @param messeage  the message to send
	 * @since 0.1
	 */
	public void sendDMFromSearchResult(List<TweetTransactionModel> tweetModelList, String message) {
		List<Long> userIdList = new ArrayList<Long>();
		for (TweetTransactionModel tweetModel : tweetModelList) {
			userIdList.add(tweetModel.getUserId());
		}
		sendDM(userIdList, message);
	}

	/**
	 * Send a direct message to the user
	 * 
	 * @param userIdList  the list of user IDs to send messages
	 * @param messeage  the message to send
	 * @since 0.1
	 */
	public void sendDM(List<Long> userIdList, String message) {
		TweetTransactionStore tts = new TweetTransactionStore();
		int count = 0;

		for (Long userId : userIdList) {
			try {
				if (!tts.isSentDM(userId) && tts.updateSentDMStatus(userId)) {
					twitter.sendDirectMessage(userId, message);
					log.info("Sent DM to: " + twitter.showUser(userId).getScreenName());
					count++;
				} else
					continue;
			} catch (TwitterException e) {
				log.error(e.getMessage(), e);
				return;
			}
		}

		log.info("Sent DM results " + count);
		tts.close();
	}
}
