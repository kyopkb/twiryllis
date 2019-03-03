# twiryllis
This application automatically performs various processing related to Twitter.  
For example, the following is possible:  

+ Follow the user who tweeted a specific keyword.
+ Send a direct message to the user who tweeted a specific keyword.
+ Return follow to the user being followed.

## Notes
+ This application is developed for learning purposes.
+ In order to use this application, MySQL must be installed.

## How to use

First, you need to register the Twitter API authentication information in the database.  
The authentication information is encrypted and stored.  

```
// Connect MySQL
AccountMasterStore accountMasterStore = new AccountMasterStore("jdbc:mysql://host:xxxx/twiryllis", "user", "password");

// Register necessary information for Twitter API
userMasterStore.registerAccountConf(userId, userName, consumerKey, consumerSecret, accessToken,
				accessTokenSecret);
```


To return follow to followers who are not following, write as follows.

```
AccountMasterModel accountMasterModel = accountMasterStore.getAccountConf(userId);

AutoFollowClient autoFollowClient = new AutoFollowClient(accountMasterModel);
autoFollowClient.executeFollowBack();
```

To follow a user tweeted that contains the keyword "Hello world!", Write as follows.

```
AccountMasterModel accountMasterModel = accountMasterStore.getAccountConf(userId);
		
TweetSearchClient tweetSearchClient = new TweetSearchClient(accountMasterModel);
List<TweetTransactionModel> tweetModelList = tweetSearchClient.searchTweet("Hellow world!", 100);
		
AutoFollowClient autoFollowClient = new AutoFollowClient(accountMasterModel);
autoFollowClient.executeFollowFromSearchResult(tweetModelList);
```