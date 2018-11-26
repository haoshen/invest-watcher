/*
 Navicat MySQL Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost
 Source Database       : invest

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : utf-8

 Date: 11/26/2018 16:26:56 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `invest_hold`
-- ----------------------------
DROP TABLE IF EXISTS `invest_hold`;
CREATE TABLE `invest_hold` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '持仓人编号',
  `invest_id` varchar(11) NOT NULL DEFAULT '0' COMMENT '投资品种',
  `direction` tinyint(4) NOT NULL DEFAULT '0' COMMENT '开仓方向:0做多/1做空',
  `current_price` double NOT NULL DEFAULT '0' COMMENT '剩余持仓的均价',
  `current_num` double NOT NULL DEFAULT '0' COMMENT '剩余持仓的数量',
  `profit` double NOT NULL DEFAULT '0' COMMENT '已平仓数量获得的盈利',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态:0持仓中/1已结束',
  `records` text COMMENT '交易记录',
  `comment` varchar(255) DEFAULT NULL COMMENT '交易备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='持仓表';

-- ----------------------------
--  Table structure for `invest_market_AGRMB`
-- ----------------------------
DROP TABLE IF EXISTS `invest_market_AGRMB`;
CREATE TABLE `invest_market_AGRMB` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` float DEFAULT NULL,
  `end` float DEFAULT '0',
  `top` float DEFAULT NULL,
  `bottom` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `invest_market_AURMB`
-- ----------------------------
DROP TABLE IF EXISTS `invest_market_AURMB`;
CREATE TABLE `invest_market_AURMB` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` float DEFAULT NULL,
  `end` float DEFAULT '0',
  `top` float DEFAULT NULL,
  `bottom` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `invest_market_BCO`
-- ----------------------------
DROP TABLE IF EXISTS `invest_market_BCO`;
CREATE TABLE `invest_market_BCO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` float DEFAULT NULL,
  `end` float DEFAULT '0',
  `top` float DEFAULT NULL,
  `bottom` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `invest_market_ICBCCO`
-- ----------------------------
DROP TABLE IF EXISTS `invest_market_ICBCCO`;
CREATE TABLE `invest_market_ICBCCO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` float DEFAULT NULL,
  `end` float DEFAULT '0',
  `top` float DEFAULT NULL,
  `bottom` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `invest_market_ICBCOI`
-- ----------------------------
DROP TABLE IF EXISTS `invest_market_ICBCOI`;
CREATE TABLE `invest_market_ICBCOI` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` float DEFAULT NULL,
  `end` float DEFAULT '0',
  `top` float DEFAULT NULL,
  `bottom` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `invest_market_NG`
-- ----------------------------
DROP TABLE IF EXISTS `invest_market_NG`;
CREATE TABLE `invest_market_NG` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` float DEFAULT NULL,
  `end` float DEFAULT '0',
  `top` float DEFAULT NULL,
  `bottom` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `invest_market_WTICO`
-- ----------------------------
DROP TABLE IF EXISTS `invest_market_WTICO`;
CREATE TABLE `invest_market_WTICO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` float DEFAULT NULL,
  `end` float DEFAULT '0',
  `top` float DEFAULT NULL,
  `bottom` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `invest_market_XAG`
-- ----------------------------
DROP TABLE IF EXISTS `invest_market_XAG`;
CREATE TABLE `invest_market_XAG` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` float DEFAULT NULL,
  `end` float DEFAULT '0',
  `top` float DEFAULT NULL,
  `bottom` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `invest_market_XAU`
-- ----------------------------
DROP TABLE IF EXISTS `invest_market_XAU`;
CREATE TABLE `invest_market_XAU` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` float DEFAULT NULL,
  `end` float DEFAULT '0',
  `top` float DEFAULT NULL,
  `bottom` float DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `invest_operation_records`
-- ----------------------------
DROP TABLE IF EXISTS `invest_operation_records`;
CREATE TABLE `invest_operation_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '操作人编号',
  `invest_id` varchar(11) NOT NULL DEFAULT '0' COMMENT '投资品种',
  `oper_direction` tinyint(4) NOT NULL DEFAULT '0' COMMENT '操作方向:0做多/1做空',
  `oper_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '操作类型:0开仓/1平仓',
  `oper_num` double NOT NULL DEFAULT '0' COMMENT '操作数量',
  `oper_price` double NOT NULL DEFAULT '0' COMMENT '操作价格',
  `oper_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作的时间',
  `hold_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应持仓记录的编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='操作记录表';

-- ----------------------------
--  Table structure for `invest_user`
-- ----------------------------
DROP TABLE IF EXISTS `invest_user`;
CREATE TABLE `invest_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `status` tinyint(4) NOT NULL COMMENT '用户状态，0存在，1删除',
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;



# insert test user

insert into `invest`.`invest_user` ( `password`, `status`, `updated_at`, `name`, `created_at`) 
  values ( 'test', '0', '2018-11-23', 'test', '2018-11-23');
