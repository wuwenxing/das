-- ----------------------------
-- Table structure for t_flow_config
-- ----------------------------
CREATE TABLE `t_flow_config` (
  `flow_config_id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `flow_channel` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`flow_config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- insert init data
-- ----------------------------
INSERT INTO `t_flow_config` (`company_id`, `create_date`, `create_ip`, `create_user`, `delete_flag`, `enable_flag`, `update_date`, `update_ip`, `update_user`, `version_no`, `flow_channel`) VALUES ('1', '2017-02-15 15:15:23', '127.0.0.1', '超级管理员', 'N', 'Y', '2017-02-15 15:15:40', '127.0.0.1', '超级管理员', '0', 'ym');
INSERT INTO `t_flow_config` (`company_id`, `create_date`, `create_ip`, `create_user`, `delete_flag`, `enable_flag`, `update_date`, `update_ip`, `update_user`, `version_no`, `flow_channel`) VALUES ('2', '2017-02-15 15:15:23', '127.0.0.1', '超级管理员', 'N', 'Y', '2017-02-15 15:15:40', '127.0.0.1', '超级管理员', '0', 'ym');


alter TABLE t_channel add column isPay BIGINT(20) DEFAULT NULL;




commit;