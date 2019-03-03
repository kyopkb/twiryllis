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
