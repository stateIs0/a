/*
 Navicat Premium Data Transfer

 Source Server         : 120.55.186.105
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 120.55.186.105
 Source Database       : qdyq

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 03/07/2017 16:11:46 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) DEFAULT NULL COMMENT '手机',
  `wx_open_id` varchar(255) NOT NULL COMMENT '微信Open ID',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `balance` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL COMMENT '状态. 0: 正常. 1: 冻结',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `cupboard`
-- ----------------------------
DROP TABLE IF EXISTS `cupboard`;
CREATE TABLE `cupboard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '柜子名称',
  `address` varchar(255) NOT NULL COMMENT '柜子详细地址',
  `location` geometry NOT NULL COMMENT '柜子坐标',
  `merchant_id` bigint(20) NOT NULL COMMENT '供货商ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  SPATIAL KEY `i_location` (`location`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `food`
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `id` bigint(20) NOT NULL,
  `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
  `name` varchar(20) DEFAULT NULL COMMENT '货品名称',
  `picture` varchar(255) DEFAULT NULL COMMENT '货品图片',
  `price` decimal(12,2) DEFAULT NULL COMMENT '售价',
  `original_price` decimal(12,2) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL COMMENT '类型. 1: 早餐. 2: 午餐',
  `food_category_id` bigint(20) DEFAULT NULL COMMENT '类别ID',
  `is_package` tinyint(1) DEFAULT '0' COMMENT '是否为套餐. 0: 否. 1: 是',
  `start_time` datetime DEFAULT NULL COMMENT '开始供货时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束供货时间',
  `sales` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '销售数量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `food_category`
-- ----------------------------
DROP TABLE IF EXISTS `food_category`;
CREATE TABLE `food_category` (
  `id` bigint(20) NOT NULL,
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户ID',
  `name` varchar(20) DEFAULT NULL COMMENT '分类名称',
  `level` tinyint(1) NOT NULL,
  `father_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `food_package`
-- ----------------------------
DROP TABLE IF EXISTS `food_package`;
CREATE TABLE `food_package` (
  `id` bigint(20) NOT NULL,
  `package_id` bigint(20) NOT NULL COMMENT '套餐ID',
  `food_id` bigint(20) NOT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `merchant`
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '商户名称',
  `address` varchar(255) NOT NULL COMMENT '详细地址',
  `location` geometry NOT NULL COMMENT '坐标',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `food_id` bigint(20) NOT NULL COMMENT '货品ID',
  `price` decimal(12,2) NOT NULL COMMENT '价格',
  `amount` int(11) NOT NULL COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `order_primary`
-- ----------------------------
DROP TABLE IF EXISTS `order_primary`;
CREATE TABLE `order_primary` (
  `id` bigint(20) NOT NULL,
  `order_no` varchar(20) NOT NULL COMMENT '订单号',
  `wx_no` varchar(20) DEFAULT NULL COMMENT '微信订单号',
  `account_id` bigint(20) NOT NULL COMMENT '下单人ID',
  `cupboard_id` bigint(20) NOT NULL COMMENT '柜子ID',
  `door` varchar(255) DEFAULT NULL COMMENT '柜门标识',
  `money` decimal(12,2) NOT NULL,
  `order_time` datetime DEFAULT NULL COMMENT '下单时间',
  `arrival_time` datetime DEFAULT NULL COMMENT '送达时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态. 0: 待支付. 1: 已支付, 等待配送. 2: 已配送 3: 已取货 4: 已抛弃',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `schema_version`
-- ----------------------------
DROP TABLE IF EXISTS `schema_version`;
CREATE TABLE `schema_version` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `script` varchar(1000) COLLATE utf8mb4_bin NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
--  Function structure for `slc`
-- ----------------------------
DROP FUNCTION IF EXISTS `slc`;
delimiter ;;
CREATE DEFINER=`root`@`%` FUNCTION `slc`(lat1 double, lon1 double, lat2 double, lon2 double) RETURNS double
RETURN 6371 * acos(cos(radians(lat1)) * cos(radians(lat2)) * cos(radians(lon2) - radians(lon1)) + sin(radians(lat1)) * sin(radians(lat2)))
 ;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
