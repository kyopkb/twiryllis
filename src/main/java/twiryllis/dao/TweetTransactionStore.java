package twiryllis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import twiryllis.api.follow.FollowAndRemoveBase;
import twiryllis.common.Constant;
import twiryllis.model.TweetTransactionModel;

/**
 * DAO class that updates data to tweet table
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class TweetTransactionStore extends DataStoreBase {

	public TweetTransactionStore(String url, String userId, String password) {
		super(url, userId, password);
	}

	public TweetTransactionStore() {
		super();
	}

	/**
	 * Register tweet list in database
	 * 
	 * @param tweetModelList  the tweet list obtained by search
	 * @since 0.1
	 */
	public boolean insertData(List<TweetTransactionModel> tweetModelList) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO twiryllis.tweet (accountId, userId, name, tweet, tweetDate) VALUES ");
		for (TweetTransactionModel tweetModel : tweetModelList) {
			sql.append("('" + tweetModel.getAccountId() + "',");
			sql.append("'" + tweetModel.getUserId() + "',");
			sql.append("'" + tweetModel.getName() + "',");
			sql.append("'" + tweetModel.getTweet().replace("'", "''") + "',");
			sql.append("'" + sdf.format(tweetModel.getTweetDate()) + "'),");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(';');

		try {
			Statement stm = connection.createStatement();
			int result = stm.executeUpdate(sql.toString());
			log.info("Insert results " + result);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * Update the follow flag.
	 * 
	 * @param userId  the user id
	 * @param status  the status(follow or remove)
	 * @return true if update completed, false in case of failure
	 * @since 0.1
	 */
	public boolean updateFollowedStatus(Long userId, FollowAndRemoveBase.UpdateStatus status) {
		Integer statusInt = null;
		switch (status) {
		case FOLLOW:
			statusInt = 1;
			break;
		case REMOVE:
			statusInt = 0;
			break;
		}

		try {
			Statement stm = connection.createStatement();
			stm.executeUpdate("UPDATE twiryllis.tweet SET followed = " + statusInt + " WHERE userId = " + userId
					+ " AND accountId = '" + Constant.User.ACCOUNT_ID + "';");
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;

	}

	/**
	 * A flag (twiryllis.tweet.sentDM) for judging whether DM has been transmitted
	 * or not is updated to transmitt.
	 * 
	 * @param userId  the user id
	 * @return true if update completed, false in case of failure
	 * @since 0.1
	 */
	public boolean updateSentDMStatus(Long userId) {
		try {
			Statement stm = connection.createStatement();
			String query = "UPDATE twiryllis.tweet SET sentDM = 1 WHERE userId = '" + userId + "' AND accountId = '"
					+ Constant.User.ACCOUNT_ID + "';";
			stm.executeUpdate(query);
			log.info("Update sentDM query: " + query);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * It judges whether a direct message is being sent to the user.<br>
	 * If an error occurs, true is returned to avoid duplicate sending.
	 * 
	 * @param userId  the user id
	 * @return true if sent, false if unsent.
	 * @since 0.1
	 */
	public boolean isSentDM(Long userId) {
		try {
			Statement stm = connection.createStatement();
			String query = "SELECT sentDM FROM twiryllis.tweet WHERE userId = '" + userId + "' AND accountId = '"
					+ Constant.User.ACCOUNT_ID + "';";
			ResultSet rs = stm.executeQuery(query);
			log.info("Select sentDM query: " + query);
			while (rs.next()) {
				if (rs.getString("sentDM").equals("0")) {
					log.info("Have not sent DM : " + userId);
					return false;
				} else {
					log.info("Already sent DM : " + userId);
					return true;
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return true;
		}
		return true;
	}

}
