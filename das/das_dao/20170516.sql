
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('1', '2017-05-17 17:49:32', '127.0.0.1', '超级管理员', 'Y', 'N', '2017-05-17 18:17:36', '172.30.110.1', '超级管理员', '4', 'selfHelpReport', '自助报表', '0', 'cboard/login.html', '3', '');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('2', '2017-05-17 17:49:32', '127.0.0.1', '超级管理员', 'Y', 'N', '2017-05-17 18:17:36', '172.30.110.1', '超级管理员', '4', 'selfHelpReport', '自助报表', '0', 'cboard/login.html', '3', '');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-05-17 17:49:32', '127.0.0.1', '超级管理员', 'Y', 'N', '2017-05-17 18:17:36', '172.30.110.1', '超级管理员', '4', 'selfHelpReport', '自助报表', '0', 'cboard/login.html', '3', '');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('4', '2017-05-17 17:49:32', '127.0.0.1', '超级管理员', 'Y', 'N', '2017-05-17 18:17:36', '172.30.110.1', '超级管理员', '4', 'selfHelpReport', '自助报表', '0', 'cboard/login.html', '3', '');


update t_system_user set company_ids = '1,2,3,4' where user_id = 1;

commit;


drop TABLE if exists dashboard_board;
CREATE TABLE dashboard_board (
  board_id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id varchar(50) NOT NULL,
  category_id bigint(20) DEFAULT NULL,
  board_name varchar(100) NOT NULL,
  layout_json text,
  PRIMARY KEY (board_id)
);

drop TABLE if exists dashboard_category;
CREATE TABLE dashboard_category (
  category_id bigint(20) NOT NULL AUTO_INCREMENT,
  category_name varchar(100) NOT NULL,
  user_id varchar(100) NOT NULL,
  PRIMARY KEY (category_id)
);

drop TABLE if exists dashboard_datasource;
CREATE TABLE dashboard_datasource (
  datasource_id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id varchar(50) NOT NULL,
  source_name varchar(100) NOT NULL,
  source_type varchar(100) NOT NULL,
  config text,
  PRIMARY KEY (datasource_id)
);

drop TABLE if exists dashboard_widget;
CREATE TABLE dashboard_widget (
  widget_id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id varchar(100) NOT NULL,
  category_name varchar(100) DEFAULT NULL,
  widget_name varchar(100) DEFAULT NULL,
  data_json text,
  PRIMARY KEY (widget_id)
);

drop TABLE if exists dashboard_dataset;
CREATE TABLE dashboard_dataset (
  dataset_id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id varchar(100) NOT NULL,
  category_name varchar(100) DEFAULT NULL,
  dataset_name varchar(100) DEFAULT NULL,
  data_json text,
  PRIMARY KEY (dataset_id)
);

drop TABLE if exists dashboard_user;
CREATE TABLE dashboard_user (
  user_id varchar(50) NOT NULL,
  login_name varchar(100) DEFAULT NULL,
  user_name varchar(100) DEFAULT NULL,
  user_password varchar(100) DEFAULT NULL,
  user_status varchar(100) DEFAULT NULL,
  PRIMARY KEY (user_id)
);

drop TABLE if exists dashboard_user_role;
CREATE TABLE dashboard_user_role (
  user_role_id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id varchar(100) DEFAULT NULL,
  role_id varchar(100) DEFAULT NULL,
  PRIMARY KEY (user_role_id)
);

drop TABLE if exists dashboard_role;
CREATE TABLE dashboard_role (
  role_id varchar(100) NOT NULL,
  role_name varchar(100) DEFAULT NULL,
  user_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (role_id)
);

drop TABLE if exists dashboard_role_res;
CREATE TABLE dashboard_role_res (
  role_res_id bigint(20) NOT NULL AUTO_INCREMENT,
  role_id varchar(100) DEFAULT NULL,
  res_type varchar(100) DEFAULT NULL,
  res_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (role_res_id)
);

alter table dashboard_user add column company_ids varchar(20) DEFAULT NULL;
alter table dashboard_role add column role_type varchar(20) DEFAULT NULL;
alter table dashboard_role add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_user_role add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_board add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_dataset add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_widget add column company_id varchar(20) DEFAULT NULL;
alter table dashboard_category add column company_id varchar(20) DEFAULT NULL;
commit;


drop PROCEDURE if exists updateUser_CB;
CREATE PROCEDURE updateUser_CB ()
BEGIN
	-- 声明变量
 DECLARE _userId int;-- 用户
 DECLARE _login_name VARCHAR(100);-- 用户
 DECLARE _user_name VARCHAR(100);-- 用户
 DECLARE _user_password VARCHAR(100);-- 用户
 DECLARE _user_status VARCHAR(100);-- 用户
 DECLARE _company_ids VARCHAR(100);-- 用户
 DECLARE _recordCount int;-- 用户ID总数

 -- 声明游标1
 DECLARE _userIdCur cursor for 
		SELECT
			user_id,
			user_no as login_name,
			user_name,
			PASSWORD as user_password,
			CASE
		WHEN enable_flag = 'Y' THEN
			1
		ELSE
			0
		END AS user_status,
		 company_ids
		FROM
			t_system_user where user_id not in(select user_id from dashboard_user);

 -- 声明游标2
 DECLARE _recordCountCur cursor for 
		select count(user_id)
		FROM
			t_system_user where user_id not in(select user_id from dashboard_user);

 -- 打开游标2
 open _recordCountCur;
 -- 读取总数到变量
 fetch _recordCountCur into _recordCount;

 -- 打开游标1
 open _userIdCur;

 -- 循环开始
 while _recordCount>0 do
 set _recordCount = _recordCount - 1;
 -- 读取一行的数据到变量
 fetch _userIdCur into _userId, _login_name, _user_name, _user_password, _user_status, _company_ids;
 -- 业务操作-start
INSERT INTO dashboard_user (user_id, login_name, user_name, user_password, user_status, company_ids)
VALUES(_userId, _login_name, _user_name, _user_password, _user_status, _company_ids);
 -- 业务操作-end

 -- 循环结束
 end while;

 -- 关闭游标1
 close _userIdCur;

 -- 关闭游标2
 close _recordCountCur;
END;

call updateUser_CB();


commit;

