package twiryllis.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twiryllis.model.AccountMasterModel;
import twitter4j.Twitter;

/**
 * Base class for various API clients that keep account information necessary
 * for Twitter API.
 * 
 * @author suka-kiyo
 */
public abstract class APIClientBase {

	protected static final Logger log = LogManager.getLogger(APIClientBase.class);
	protected Twitter twitter;
	protected AccountMasterModel accountMasterModel;

	public APIClientBase(AccountMasterModel accountMasterModel) {
		this.accountMasterModel = accountMasterModel;
		this.twitter = accountMasterModel.getTwitter();
	}
}
