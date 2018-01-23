
DROP TABLE IF EXISTS `t_businessTag`;
CREATE TABLE `t_businessTag` (
  `tag_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_ip` varchar(20) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `delete_flag` varchar(1) DEFAULT NULL,
  `enable_flag` varchar(1) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_ip` varchar(20) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `tagContent` int(10) DEFAULT NULL,
  `tagType` int(10) DEFAULT NULL,
  `tagUrl` varchar(500) DEFAULT NULL,
  `eventCategory` varchar(100) DEFAULT NULL,
  `eventAction` varchar(100) DEFAULT NULL,
  `eventLabel` varchar(100) DEFAULT NULL,
  `eventValue` varchar(100) DEFAULT NULL,
  `version_no` bigint(20) DEFAULT NULL,
  `event` varchar(255) DEFAULT NULL,
  `tagEvent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;