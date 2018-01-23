/*
Navicat MySQL Data Transfer

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-07-25 14:17:56
*/

-- ----------------------------
-- Table structure for t_system_user
-- ----------------------------
DROP TABLE IF EXISTS `t_system_user`;
CREATE TABLE `t_system_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_ip` varchar(20) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `enable_flag` varchar(1) DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_ip` varchar(20) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `version_no` bigint(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_system_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_system_menu`;
CREATE TABLE `t_system_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_ip` varchar(20) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `enable_flag` varchar(1) DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_ip` varchar(20) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `version_no` bigint(20) DEFAULT NULL,
  `menu_code` varchar(25) DEFAULT NULL,
  `menu_name` varchar(50) DEFAULT NULL,
  `menu_type` varchar(25) DEFAULT NULL,
  `menu_url` varchar(250) DEFAULT NULL,
  `order_code` bigint(20) DEFAULT NULL,
  `parent_menu_code` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_system_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_system_dict`;
CREATE TABLE `t_system_dict` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_ip` varchar(20) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `enable_flag` varchar(1) DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_ip` varchar(20) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `version_no` bigint(20) DEFAULT NULL,
  `dict_code` varchar(25) DEFAULT NULL,
  `dict_name` varchar(50) DEFAULT NULL,
  `dict_type` varchar(25) DEFAULT NULL,
  `order_code` bigint(20) DEFAULT NULL,
  `parent_dict_code` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`dict_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
