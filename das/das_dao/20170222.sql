-- ----------------------------
-- Table structure for t_custconsultation
-- ----------------------------
CREATE TABLE `t_custconsultation` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `consulttationTime` varchar(20) DEFAULT NULL,
  `consulttationNum` bigint(20) DEFAULT NULL,
  `reportType` varchar(20) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_ip` varchar(20) DEFAULT NULL,
  `create_user` varchar(100) DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `enable_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_ip` varchar(20) DEFAULT NULL,
  `update_user` varchar(100) DEFAULT NULL,
  `version_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table t_flow_log
-- ----------------------------
CREATE TABLE `t_flow_log` (
  `flow_log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_ip` varchar(20) DEFAULT NULL,
  `create_user` varchar(100) DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `enable_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_ip` varchar(20) DEFAULT NULL,
  `update_user` varchar(100) DEFAULT NULL,
  `version_no` bigint(20) DEFAULT NULL,
  `flow_size` varchar(10) DEFAULT NULL,
  `illegal_num` bigint(20) DEFAULT NULL,
  `illegal_phones` mediumtext,
  `input_num` bigint(20) DEFAULT NULL,
  `legal_num` bigint(20) DEFAULT NULL,
  `legal_phones` mediumtext,
  `param` mediumtext,
  `phones` mediumtext,
  `status_code` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`flow_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table t_flow_log_detail
-- ----------------------------
CREATE TABLE `t_flow_log_detail` (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_ip` varchar(20) DEFAULT NULL,
  `create_user` varchar(100) DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `enable_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_ip` varchar(20) DEFAULT NULL,
  `update_user` varchar(100) DEFAULT NULL,
  `version_no` bigint(20) DEFAULT NULL,
  `commit_status` varchar(100) DEFAULT NULL,
  `flow_log_id` bigint(20) DEFAULT NULL,
  `flow_size` varchar(10) DEFAULT NULL,
  `interface_type` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `res_batch_no` varchar(100) DEFAULT NULL,
  `res_code` varchar(100) DEFAULT NULL,
  `send_status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;


INSERT INTO `t_system_menu` (`company_id`, `create_date`, `create_ip`, `create_user`, `enable_flag`, `delete_flag`, `update_date`, `update_ip`, `update_user`, `version_no`, `menu_code`, `menu_name`, `menu_type`, `menu_url`, `order_code`, `parent_menu_code`) VALUES ('1', '2017-02-24 09:23:36', '0:0:0:0:0:0:0:1', '超级管理员', 'Y', 'N', '2017-02-24 11:01:25', '120.237.129.78', '超级管理员', '0', 'home', '首页（顶部页签）', '0', 'LoginController/home', '0', '');
INSERT INTO `t_system_menu` (`company_id`, `create_date`, `create_ip`, `create_user`, `enable_flag`, `delete_flag`, `update_date`, `update_ip`, `update_user`, `version_no`, `menu_code`, `menu_name`, `menu_type`, `menu_url`, `order_code`, `parent_menu_code`) VALUES ('2', '2017-02-24 09:23:36', '0:0:0:0:0:0:0:1', '超级管理员', 'Y', 'N', '2017-02-24 11:01:25', '120.237.129.78', '超级管理员', '0', 'home', '首页（顶部页签）', '0', 'LoginController/home', '0', '');
INSERT INTO `t_system_menu` (`company_id`, `create_date`, `create_ip`, `create_user`, `enable_flag`, `delete_flag`, `update_date`, `update_ip`, `update_user`, `version_no`, `menu_code`, `menu_name`, `menu_type`, `menu_url`, `order_code`, `parent_menu_code`) VALUES ('3', '2017-02-24 09:23:36', '0:0:0:0:0:0:0:1', '超级管理员', 'Y', 'N', '2017-02-24 11:01:25', '120.237.129.78', '超级管理员', '0', 'home', '首页（顶部页签）', '0', 'LoginController/home', '0', '');
INSERT INTO `t_system_menu` (`company_id`, `create_date`, `create_ip`, `create_user`, `enable_flag`, `delete_flag`, `update_date`, `update_ip`, `update_user`, `version_no`, `menu_code`, `menu_name`, `menu_type`, `menu_url`, `order_code`, `parent_menu_code`) VALUES ('4', '2017-02-24 09:23:36', '0:0:0:0:0:0:0:1', '超级管理员', 'Y', 'N', '2017-02-24 11:01:25', '120.237.129.78', '超级管理员', '0', 'home', '首页（顶部页签）', '0', 'LoginController/home', '0', '');

commit;