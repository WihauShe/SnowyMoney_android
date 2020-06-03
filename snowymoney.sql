/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : snowymoney

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2020-05-17 18:01:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` varchar(11) NOT NULL,
  `password` varchar(18) NOT NULL,
  `balance` int(5) NOT NULL DEFAULT '20',
  `credit` int(3) NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jid` int(11) NOT NULL,
  `uid` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `jid` (`jid`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(5) NOT NULL,
  `salary` varchar(10) NOT NULL,
  `duration` varchar(10) NOT NULL,
  `demands` varchar(255) DEFAULT NULL,
  `position` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `flag` int(2) NOT NULL DEFAULT '0',
  `publisherName` varchar(40) NOT NULL DEFAULT '',
  `publisherId` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `publisher` (`publisherId`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ruser` varchar(11) NOT NULL,
  `reason` varchar(50) NOT NULL,
  `evidence` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ruser` (`ruser`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for trade
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade` (
  `tradeNo` varchar(255) NOT NULL,
  `uid` varchar(11) NOT NULL,
  `total` varchar(10) NOT NULL,
  `status` int(2) DEFAULT '0',
  PRIMARY KEY (`tradeNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(11) NOT NULL,
  `img` varchar(100) DEFAULT '',
  `name` varchar(40) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `school` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
