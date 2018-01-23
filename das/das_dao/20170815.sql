INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('0', '2017-08-15 09:25:09', '127.0.0.1', '超级管理员', 'Y', 'N', '2017-08-15 09:25:09', '127.0.0.1', '超级管理员', '0', 'riskBlacklist', '风控要素', '1', 'RiskBlacklistController/page', '2', 'windControl');

drop TABLE if exists t_risk_blacklist;
CREATE TABLE `t_risk_blacklist` (
  `risk_blacklist_id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `device_info` varchar(100) DEFAULT NULL,
  `device_type` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `id_card` varchar(100) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `mobile` varchar(100) DEFAULT NULL,
  `platform` varchar(100) DEFAULT NULL,
  `risk_time` varchar(100) DEFAULT NULL,
  `risk_type` varchar(100) DEFAULT NULL,
  `risk_reason` varchar(100) DEFAULT NULL,
  `md5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`risk_blacklist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('0', '2016-08-01 09:30:54', '172.30.5.132', '超级管理员', 'Y', 'N', '2017-05-04 15:38:03', '172.20.10.32', '超级管理员', '3', 'systemLog', '系统日志', '1', '', '98', 'statistics');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('0', '2016-07-25 15:17:21', '127.0.0.1', '超级管理员', 'Y', 'N', '2016-11-01 15:57:28', '127.0.0.1', '超级管理员', '6', 'fileUploadLog', '文件上传日志', '1', 'UploadController/page', '1', 'systemLog');


COMMIT;