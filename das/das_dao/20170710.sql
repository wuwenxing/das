INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('1', '2017-07-06 11:10:32', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-07-06 11:10:32', '172.30.110.1', '超级管理员', '0', 'accountAnalyze', '账户诊断', '1', 'AccountAnalyzeController/page', '13', 'market');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('2', '2017-07-06 11:10:32', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-07-06 11:10:32', '172.30.110.1', '超级管理员', '0', 'accountAnalyze', '账户诊断', '1', 'AccountAnalyzeController/page', '13', 'market');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-07-06 11:10:32', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-07-06 11:10:32', '172.30.110.1', '超级管理员', '0', 'accountAnalyze', '账户诊断', '1', 'AccountAnalyzeController/page', '13', 'market');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('4', '2017-07-06 11:10:32', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-07-06 11:10:32', '172.30.110.1', '超级管理员', '0', 'accountAnalyze', '账户诊断', '1', 'AccountAnalyzeController/page', '13', 'market');


CREATE TABLE `t_account_analyze` (
  `batch_id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `account_no` varchar(100) DEFAULT NULL,
  `batch_no` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `end_date` varchar(255) DEFAULT NULL,
  `generate_status` varchar(20) DEFAULT NULL,
  `generate_time` datetime DEFAULT NULL,
  `path` varchar(200) DEFAULT NULL,
  `send_status` varchar(20) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `start_date` varchar(255) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `platform` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8;



COMMIT;