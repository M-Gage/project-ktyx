/*
Navicat MySQL Data Transfer

Source Server         : 118.89.26.70 _3306
Source Server Version : 50722
Source Host           : 118.89.26.70 :3306
Source Database       : ktyx

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-07-20 13:38:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
  `announcement_id` int(10) NOT NULL AUTO_INCREMENT,
  `content` varchar(10000) NOT NULL,
  `create_time` datetime NOT NULL,
  `company_id` varchar(32) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`announcement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of announcement
-- ----------------------------

-- ----------------------------
-- Table structure for attribute
-- ----------------------------
DROP TABLE IF EXISTS `attribute`;
CREATE TABLE `attribute` (
  `attribute_id` int(10) NOT NULL AUTO_INCREMENT,
  `attribute_name` varchar(20) DEFAULT NULL,
  `is_nonstandard` tinyint(1) DEFAULT NULL,
  `company_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`attribute_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of attribute
-- ----------------------------

-- ----------------------------
-- Table structure for attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `attribute_value`;
CREATE TABLE `attribute_value` (
  `attribute_id` int(10) NOT NULL,
  `attribute_value_id` int(10) NOT NULL AUTO_INCREMENT,
  `attribute_value` varchar(20) DEFAULT NULL,
  `company_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`attribute_value_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of attribute_value
-- ----------------------------

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `staff_id` varchar(32) NOT NULL,
  `customer_id` varchar(32) NOT NULL,
  `goods_id` varchar(32) NOT NULL,
  `quantity` int(10) NOT NULL,
  PRIMARY KEY (`staff_id`,`customer_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of cart
-- ----------------------------

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `company_id` varchar(32) NOT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `site_number` int(10) DEFAULT NULL,
  `map_keyword` varchar(50) DEFAULT NULL,
  `poi_number` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('90e9b7128a6f11e89a94a6cf71072f73', '系统预设公司', null, null, null, null, '0');

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `contact_id` varchar(32) NOT NULL,
  `customer_id` varchar(32) NOT NULL,
  `contact_name` varchar(50) NOT NULL,
  `contact_phone` varchar(20) NOT NULL,
  `contact_remark` varchar(255) DEFAULT NULL,
  `is_main` tinyint(1) NOT NULL COMMENT '0',
  `company_Id` varchar(32) NOT NULL,
  PRIMARY KEY (`contact_id`),
  UNIQUE KEY `customer_id` (`customer_id`,`contact_name`,`contact_phone`),
  KEY `company_Id` (`company_Id`),
  CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `contact_ibfk_2` FOREIGN KEY (`company_Id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of contact
-- ----------------------------

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` varchar(32) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `customer_status` tinyint(1) NOT NULL DEFAULT '0',
  `customer_no` varchar(20) DEFAULT NULL,
  `staff_id` varchar(32) DEFAULT NULL,
  `staff_name` varchar(20) DEFAULT NULL,
  `longitude` double(10,6) NOT NULL,
  `latitude` double(10,6) NOT NULL,
  `city` varchar(10) NOT NULL,
  `province` varchar(10) NOT NULL,
  `district` varchar(10) NOT NULL,
  `detail_address` varchar(255) DEFAULT NULL,
  `level` varchar(2) NOT NULL,
  `help_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `sync_customer_id` varchar(30) DEFAULT NULL,
  `company_id` varchar(32) DEFAULT NULL,
  `order_count` int(10) NOT NULL DEFAULT '0',
  `follow_count` int(10) NOT NULL DEFAULT '0',
  `last_order_time` datetime DEFAULT NULL,
  `last_follow_time` datetime DEFAULT NULL,
  `dept_id` varchar(50) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_name` (`customer_name`,`company_id`),
  KEY `staff_id` (`staff_id`),
  KEY `company_id` (`company_id`),
  KEY `dept_id` (`dept_id`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `customer_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `customer_ibfk_3` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for customer_label
-- ----------------------------
DROP TABLE IF EXISTS `customer_label`;
CREATE TABLE `customer_label` (
  `customer_label_id` int(11) NOT NULL AUTO_INCREMENT,
  `label_id` int(10) NOT NULL,
  `customer_id` varchar(32) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  `label_name` varchar(255) NOT NULL,
  PRIMARY KEY (`customer_label_id`),
  KEY `label_id` (`label_id`),
  KEY `customer_id` (`customer_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `customer_label_ibfk_1` FOREIGN KEY (`label_id`) REFERENCES `label` (`label_id`),
  CONSTRAINT `customer_label_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `customer_label_ibfk_3` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of customer_label
-- ----------------------------

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `dept_id` varchar(50) NOT NULL,
  `dept_name` varchar(20) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `dept_id` (`dept_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `dept_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of dept
-- ----------------------------

-- ----------------------------
-- Table structure for error_customer
-- ----------------------------
DROP TABLE IF EXISTS `error_customer`;
CREATE TABLE `error_customer` (
  `error_customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `staff_id` varchar(32) NOT NULL,
  `longitude` double(10,6) NOT NULL,
  `latitude` double(10,6) NOT NULL,
  `province` varchar(10) NOT NULL,
  `city` varchar(10) NOT NULL,
  `district` varchar(10) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`error_customer_id`),
  KEY `staff_id` (`staff_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `error_customer_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `error_customer_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of error_customer
-- ----------------------------

-- ----------------------------
-- Table structure for event
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `company_id` varchar(32) NOT NULL,
  `staff_id` varchar(32) NOT NULL,
  `event_name` varchar(66) NOT NULL,
  PRIMARY KEY (`company_id`,`staff_id`,`event_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of event
-- ----------------------------

-- ----------------------------
-- Table structure for experience
-- ----------------------------
DROP TABLE IF EXISTS `experience`;
CREATE TABLE `experience` (
  `experience_id` int(11) NOT NULL AUTO_INCREMENT,
  `staff_id` varchar(32) NOT NULL,
  `behavior` varchar(255) NOT NULL,
  `experience` varchar(255) NOT NULL,
  `before` int(11) NOT NULL,
  `after` int(11) NOT NULL,
  `operating_time` datetime DEFAULT NULL,
  `company_id` varchar(32) NOT NULL,
  PRIMARY KEY (`experience_id`),
  KEY `company_id` (`company_id`),
  KEY `staff_id` (`staff_id`),
  CONSTRAINT `experience_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `experience_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of experience
-- ----------------------------

-- ----------------------------
-- Table structure for experience_config
-- ----------------------------
DROP TABLE IF EXISTS `experience_config`;
CREATE TABLE `experience_config` (
  `experience_config_id` int(11) NOT NULL AUTO_INCREMENT,
  `insert_customer` int(11) NOT NULL,
  `insert_order` int(11) NOT NULL,
  `insert_follow` int(11) NOT NULL,
  `follow_like` int(11) NOT NULL,
  `follow_dislike` int(11) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  PRIMARY KEY (`experience_config_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `experience_config_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of experience_config
-- ----------------------------

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `follow_id` varchar(32) NOT NULL,
  `customer_id` varchar(32) NOT NULL,
  `customer_name` varchar(20) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  `staff_id` varchar(32) NOT NULL,
  `staff_name` varchar(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `voice_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`follow_id`),
  KEY `customer_id` (`customer_id`),
  KEY `staff_id` (`staff_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `follow_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `follow_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `follow_ibfk_3` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of follow
-- ----------------------------

-- ----------------------------
-- Table structure for follow_comment
-- ----------------------------
DROP TABLE IF EXISTS `follow_comment`;
CREATE TABLE `follow_comment` (
  `follow_comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `follow_id` varchar(32) NOT NULL,
  `staff_id` varchar(32) NOT NULL,
  `staff_name` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `reply_comment_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `company_id` varchar(32) NOT NULL,
  PRIMARY KEY (`follow_comment_id`),
  KEY `follow_id` (`follow_id`),
  KEY `staff_id` (`staff_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `follow_comment_ibfk_1` FOREIGN KEY (`follow_id`) REFERENCES `follow` (`follow_id`),
  CONSTRAINT `follow_comment_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `follow_comment_ibfk_3` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of follow_comment
-- ----------------------------

-- ----------------------------
-- Table structure for follow_opinion
-- ----------------------------
DROP TABLE IF EXISTS `follow_opinion`;
CREATE TABLE `follow_opinion` (
  `follow_opinion_id` int(11) NOT NULL AUTO_INCREMENT,
  `follow_id` varchar(32) NOT NULL,
  `opinion` tinyint(4) NOT NULL,
  `staff_id` varchar(32) NOT NULL,
  `staff_name` varchar(50) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  PRIMARY KEY (`follow_opinion_id`),
  KEY `follow_id` (`follow_id`),
  KEY `staff_id` (`staff_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `follow_opinion_ibfk_1` FOREIGN KEY (`follow_id`) REFERENCES `follow` (`follow_id`),
  CONSTRAINT `follow_opinion_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`),
  CONSTRAINT `follow_opinion_ibfk_3` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of follow_opinion
-- ----------------------------

-- ----------------------------
-- Table structure for follow_pic
-- ----------------------------
DROP TABLE IF EXISTS `follow_pic`;
CREATE TABLE `follow_pic` (
  `follow_pic_id` int(11) NOT NULL AUTO_INCREMENT,
  `follow_id` varchar(32) NOT NULL,
  `pic_path` varchar(200) NOT NULL,
  `preview_path` varchar(200) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  PRIMARY KEY (`follow_pic_id`),
  KEY `follow_id` (`follow_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `follow_pic_ibfk_1` FOREIGN KEY (`follow_id`) REFERENCES `follow` (`follow_id`),
  CONSTRAINT `follow_pic_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of follow_pic
-- ----------------------------

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goods_id` varchar(32) NOT NULL,
  `goods_no` varchar(50) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `group_id` varchar(32) DEFAULT NULL,
  `company_id` varchar(32) DEFAULT NULL,
  `sync_goods_id` varchar(50) DEFAULT NULL,
  `retail_price` decimal(11,2) DEFAULT NULL,
  `purchase_price` decimal(11,2) DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `type_id` varchar(20) DEFAULT NULL,
  `stock` bigint(20) DEFAULT NULL,
  `stock_warning` int(10) DEFAULT NULL,
  `help_code` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `expiry_date` varchar(20) DEFAULT NULL,
  `product_date` datetime DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `bar_code` varchar(30) DEFAULT NULL,
  `type_name` varchar(10) DEFAULT NULL,
  `standard_attribute` varchar(255) DEFAULT NULL,
  `nonstandard_attribute` varchar(255) DEFAULT NULL,
  `assist_attribute` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of goods
-- ----------------------------

-- ----------------------------
-- Table structure for goods_image
-- ----------------------------
DROP TABLE IF EXISTS `goods_image`;
CREATE TABLE `goods_image` (
  `goods_id` varchar(32) DEFAULT NULL,
  `goods_image_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of goods_image
-- ----------------------------

-- ----------------------------
-- Table structure for goods_type
-- ----------------------------
DROP TABLE IF EXISTS `goods_type`;
CREATE TABLE `goods_type` (
  `type_id` varchar(20) DEFAULT NULL,
  `type_name` varchar(10) NOT NULL,
  `is_parent` tinyint(4) DEFAULT NULL,
  `rank` int(10) DEFAULT NULL,
  `pre_type_id` varchar(20) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  PRIMARY KEY (`company_id`,`type_name`,`pre_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of goods_type
-- ----------------------------

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `grade_id` int(11) NOT NULL AUTO_INCREMENT,
  `grade_name` varchar(255) NOT NULL,
  `grade_exp` int(11) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`grade_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `grade_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of grade
-- ----------------------------

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label` (
  `label_id` int(10) NOT NULL AUTO_INCREMENT,
  `company_id` varchar(32) NOT NULL,
  `staff_id` varchar(32) NOT NULL,
  `label_name` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `is_private` tinyint(4) NOT NULL DEFAULT '0',
  `staff_name` varchar(50) NOT NULL,
  PRIMARY KEY (`label_id`),
  KEY `company_id` (`company_id`),
  KEY `staff_id` (`staff_id`),
  CONSTRAINT `label_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `label_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of label
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `staff_id` varchar(32) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `parameters` varchar(5000) DEFAULT NULL,
  `method` varchar(6) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `ip_address` varchar(20) DEFAULT NULL,
  `operate_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for map_chart_city
-- ----------------------------
DROP TABLE IF EXISTS `map_chart_city`;
CREATE TABLE `map_chart_city` (
  `city_id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(10) NOT NULL,
  `province` varchar(32) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  `customer_count` int(10) NOT NULL DEFAULT '0',
  `amount_count` decimal(11,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`city_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `map_chart_city_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of map_chart_city
-- ----------------------------

-- ----------------------------
-- Table structure for map_chart_province
-- ----------------------------
DROP TABLE IF EXISTS `map_chart_province`;
CREATE TABLE `map_chart_province` (
  `province_id` int(11) NOT NULL AUTO_INCREMENT,
  `province` varchar(10) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  `customer_count` int(10) NOT NULL DEFAULT '0',
  `amount_count` decimal(11,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`province_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `map_chart_province_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of map_chart_province
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` varchar(20) NOT NULL,
  `menu_name` varchar(20) DEFAULT NULL,
  `menu_url` varchar(50) DEFAULT NULL,
  `is_parent` enum('1','0') DEFAULT '0',
  `parent_id` varchar(20) DEFAULT NULL,
  `menu_icon` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1001', '公司管理', '/', '1', '1001', 'leaf');
INSERT INTO `menu` VALUES ('100101', '职员管理', '/staffManager', '0', '1001', null);
INSERT INTO `menu` VALUES ('100102', '部门管理', '/deptManager', '0', '1001', null);
INSERT INTO `menu` VALUES ('100103', '权限管理', '/authorityManager', '0', '1001', null);
INSERT INTO `menu` VALUES ('1002', '客户管理', '/', '1', '1002', 'group');
INSERT INTO `menu` VALUES ('100201', '查看所有客户', '/customerManager', '0', '1002', null);
INSERT INTO `menu` VALUES ('100202', '添加客户', '/customerAdd', '0', '1002', null);
INSERT INTO `menu` VALUES ('100203', '标签管理', '/labelManager', '0', '1002', null);
INSERT INTO `menu` VALUES ('1003', '客户跟进', '/', '1', '1003', 'tint');
INSERT INTO `menu` VALUES ('100301', '管理跟进', '/followupManager', '0', '1003', null);
INSERT INTO `menu` VALUES ('1004', '业务员日程', '/', '1', '1004', 'bolt');
INSERT INTO `menu` VALUES ('100401', '管理日程', '/scheduleManager', '0', '1004', null);
INSERT INTO `menu` VALUES ('100402', '路程规划', '/routePlan', '0', '1004', null);
INSERT INTO `menu` VALUES ('1005', '商品管理', '/', '1', '1005', 'crop');
INSERT INTO `menu` VALUES ('100501', '添加商品', '/goodsAdd', '0', '1005', null);
INSERT INTO `menu` VALUES ('100502', '分类管理', '/goodsType', '0', '1005', null);
INSERT INTO `menu` VALUES ('100503', '商品清单', '/goodsList', '0', '1005', null);
INSERT INTO `menu` VALUES ('1006', '计划单管理', '/', '1', '1006', 'asterisk');
INSERT INTO `menu` VALUES ('100601', '计划单查询', '/orderList', '0', '1006', null);
INSERT INTO `menu` VALUES ('100602', '计划单报表分析', '/orderAnalyse', '0', '1006', null);
INSERT INTO `menu` VALUES ('1007', '报表分析', '/', '1', '1007', 'fire');
INSERT INTO `menu` VALUES ('100701', '地图区域报表', '/mapChart', '0', '1007', null);
INSERT INTO `menu` VALUES ('100702', '员工业务对比', '/staffContrast', '0', '1007', null);
INSERT INTO `menu` VALUES ('100703', '商品销售对比', '/goodsSaleContrast', '0', '1007', null);
INSERT INTO `menu` VALUES ('1008', '系统配置', '/', '1', '1008', 'cogs');
INSERT INTO `menu` VALUES ('100801', '等级配置', '/levelManager', '0', '1008', null);
INSERT INTO `menu` VALUES ('1009', '公告管理', '/', '1', '1009', 'magnet');
INSERT INTO `menu` VALUES ('100901', '历史公告', '/announcementManager', '0', '1009', null);
INSERT INTO `menu` VALUES ('100902', '添加公告', '/announcementAdd', '0', '1009', null);
INSERT INTO `menu` VALUES ('1010', '报表群', '/', '1', '1010', 'signal');
INSERT INTO `menu` VALUES ('101001', '客户分析', '/customerAnalyse', '0', '1010', null);
INSERT INTO `menu` VALUES ('101002', '员工分析', '/staffAnalyse', '0', '1010', null);
INSERT INTO `menu` VALUES ('101003', '地区分析', '/areaAnalyse', '0', '1001', null);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` varchar(32) NOT NULL,
  `staff_id` varchar(32) DEFAULT NULL,
  `customer_id` varchar(32) DEFAULT NULL,
  `company_id` varchar(32) DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL,
  `amount` decimal(11,2) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `detail_address` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `staff_name` varchar(255) DEFAULT NULL,
  `sync_order_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `order_id` varchar(32) NOT NULL,
  `goods_id` varchar(32) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `sum` int(10) DEFAULT NULL,
  `goods_price` decimal(11,2) DEFAULT NULL,
  `type_id` varchar(20) DEFAULT NULL,
  `type_name` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(10) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  PRIMARY KEY (`role_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `role_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role` VALUES ('2', '导入人员', '90e9b7128a6f11e89a94a6cf71072f73');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` int(10) NOT NULL,
  `menu_id` varchar(20) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`),
  CONSTRAINT `role_menu_ibfk_3` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1001', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100101', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100102', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100103', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '1002', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100201', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100202', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100203', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '1003', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100301', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '1004', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100401', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100402', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '1005', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100501', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100502', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100503', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '1006', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100601', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100602', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '1007', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100701', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100702', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100703', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '1008', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100801', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '1009', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100901', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '100902', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '1010', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '101001', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '101002', '90e9b7128a6f11e89a94a6cf71072f73');
INSERT INTO `role_menu` VALUES ('1', '101003', '90e9b7128a6f11e89a94a6cf71072f73');

-- ----------------------------
-- Table structure for route_plan
-- ----------------------------
DROP TABLE IF EXISTS `route_plan`;
CREATE TABLE `route_plan` (
  `staff_id` varchar(32) DEFAULT NULL,
  `company_id` varchar(32) DEFAULT NULL,
  `route_plan_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(32) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `latitude` double(10,6) DEFAULT NULL,
  `longitude` double(10,6) DEFAULT NULL,
  `route_plan_date` date DEFAULT NULL,
  `route_plan_sort_order` int(10) DEFAULT NULL,
  `staff_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`route_plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of route_plan
-- ----------------------------

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
  `schedule_id` int(10) NOT NULL AUTO_INCREMENT,
  `staff_id` varchar(32) DEFAULT NULL,
  `company_id` varchar(32) DEFAULT NULL,
  `customer_id` varchar(32) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `remind_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `staff_name` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`schedule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of schedule
-- ----------------------------

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `staff_id` varchar(32) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `staff_no` varchar(20) NOT NULL,
  `staff_status` tinyint(1) NOT NULL DEFAULT '0',
  `dept_id` varchar(50) NOT NULL,
  `dept_name` varchar(20) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  `staff_name` varchar(255) NOT NULL,
  `help_code` varchar(10) DEFAULT NULL,
  `sex` varchar(1) NOT NULL DEFAULT '男',
  `birthday` date DEFAULT NULL,
  `is_dept_manager` int(1) NOT NULL DEFAULT '0',
  `role_id` int(10) NOT NULL,
  `role_name` varchar(20) NOT NULL,
  `experience` int(11) NOT NULL DEFAULT '0',
  `sync_staff_id` varchar(30) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`staff_id`),
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  KEY `dept_id` (`dept_id`),
  KEY `company_id` (`company_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`dept_id`),
  CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `staff_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of staff
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
