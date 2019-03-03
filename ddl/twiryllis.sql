CREATE DATABASE  IF NOT EXISTS `twiryllis` ;
USE `twiryllis`;

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` varchar(10) CHARACTER SET utf8 NOT NULL,
  `accountName` varchar(100) CHARACTER SET utf8 NOT NULL,
  `consumerKey` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `consumerSecret` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `accessToken` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `accessTokenSecret` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `follower`;
CREATE TABLE `follower` (
  `accountId` varchar(10) COLLATE utf8mb4_bin NOT NULL,
  `userId` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `followed` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `sentDM` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


DROP TABLE IF EXISTS `tweet`;
CREATE TABLE `tweet` (
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` varchar(45) CHARACTER SET utf8mb4 NOT NULL,
  `userId` varchar(45) CHARACTER SET utf8mb4 NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 NOT NULL,
  `tweet` varchar(3000) COLLATE utf8mb4_bin NOT NULL,
  `tweetDate` datetime NOT NULL,
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `followed` tinyint(4) NOT NULL DEFAULT '0',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `sentDM` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=882 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
