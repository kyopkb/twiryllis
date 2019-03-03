package twiryllis.dao;

import java.sql.ResultSet;
import java.sql.Statement;

import twiryllis.cipher.CipherManager;
import twiryllis.common.Constant;
import twiryllis.common.Constant.Cipher;
import twiryllis.common.Constant.User;
import twiryllis.model.AccountMasterModel;

/**
 * DAO class that updates data to account table
 * 
 * @author suka-kiyo
 * @since 0.1
 */
public class AccountMasterStore extends DataStoreBase {

	public AccountMasterStore(String url, String userId, String password) {
		super(url, userId, password);
	}

	public AccountMasterStore() {
		super();
	}

	/**
	 * Register Twitter authentication information in the database
	 * 
	 * @param id  the user id of twitter account
	 * @param accountName  the account name of twitter account
	 * @param consumerKey  the Consumer Key of twitter api
	 * @param consumerSecret  the Consumer Secret of twitter api
	 * @param accessToken  the Access Token of twitter api
	 * @param accessTokenSecret  the Access Token Secret of twitter api
	 * @return true if registration is complete, false in case of failure
	 * @since 0.1
	 */
	public boolean registerAccountConf(String id, String accountName, String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {

		try {
			StringBuilder sql = new StringBuilder();
			sql.append(
					"INSERT INTO account (id, accountName, consumerKey, consumerSecret, accessToken, accessTokenSecret) VALUES ");

			String encryptConsumerKey = CipherManager.encrypt(consumerKey, Cipher.COMPOSITE_KEY, Cipher.ALGORITHM);
			String encryptConsumerSecret = CipherManager.encrypt(consumerSecret, Cipher.COMPOSITE_KEY,
					Cipher.ALGORITHM);
			String encryptAccessToken = CipherManager.encrypt(accessToken, Cipher.COMPOSITE_KEY, Cipher.ALGORITHM);
			String encryptAccessTokenSecret = CipherManager.encrypt(accessTokenSecret, Cipher.COMPOSITE_KEY,
					Cipher.ALGORITHM);

			sql.append("('" + id + "',");
			sql.append("'" + accountName + "',");
			sql.append("'" + encryptConsumerKey + "',");
			sql.append("'" + encryptConsumerSecret + "',");
			sql.append("'" + encryptAccessToken + "',");
			sql.append("'" + encryptAccessTokenSecret + "');");

			Statement stm = connection.createStatement();
			stm.executeUpdate(sql.toString());
            log.info("Insert complete: " + id);

			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Acquire authentication information to use Twitter API
	 * 
	 * @param id  the unique ID to identify the user
	 * @return model of account table
	 * @since 0.1
	 */
	public AccountMasterModel getAccountConf(String id) {

		try {
			Statement stm = connection.createStatement();
			String sql = "SELECT * FROM account WHERE id = '" + id + "'";
			ResultSet rs = stm.executeQuery(sql);

			String encryptConsumerKey = null;
			String encryptConsumerSecret = null;
			String encryptAccessToken = null;
			String encryptAccessTokenSecret = null;

			while (rs.next()) {
				encryptConsumerKey = rs.getString(User.CONSUMER_KEY);
				encryptConsumerSecret = rs.getString(User.CONSUMER_SECRET);
				encryptAccessToken = rs.getString(User.ACCESS_TOKEN);
				encryptAccessTokenSecret = rs.getString(User.ACCESS_TOKEN_SECRET);
			}

			String consumerKey = CipherManager.decrypt(encryptConsumerKey, Cipher.COMPOSITE_KEY, Cipher.ALGORITHM);
			String consumerSecret = CipherManager.decrypt(encryptConsumerSecret, Cipher.COMPOSITE_KEY,
					Cipher.ALGORITHM);
			String accessToken = CipherManager.decrypt(encryptAccessToken, Cipher.COMPOSITE_KEY, Cipher.ALGORITHM);
			String accessTokenSecret = CipherManager.decrypt(encryptAccessTokenSecret, Cipher.COMPOSITE_KEY,
					Cipher.ALGORITHM);

			AccountMasterModel accountConfiguration = new AccountMasterModel("");
			accountConfiguration.setConsumerKey(consumerKey);
			accountConfiguration.setConsumerSecret(consumerSecret);
			accountConfiguration.setAccessToken(accessToken);
			accountConfiguration.setAccessTokenSecret(accessTokenSecret);
			accountConfiguration.setId(id);

			Constant.User.ACCOUNT_ID = id;

			return accountConfiguration;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
