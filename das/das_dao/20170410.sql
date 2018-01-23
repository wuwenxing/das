alter table t_system_user add COLUMN company_ids VARCHAR(100);

CREATE TABLE `t_system_menu_user` (
  `menu_user_id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `menu_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`menu_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop PROCEDURE if exists updateUserByRole;
CREATE PROCEDURE updateUserByRole ()
BEGIN
	-- 声明变量
 DECLARE _roleId int;-- 角色ID
 DECLARE _userId int;-- 用户ID
 DECLARE _menuId int;-- 菜单ID
 DECLARE _companyId int;-- 业务权限ID
 DECLARE _recordCount int;-- 总数

 -- 声明游标1
 DECLARE _roleIdCur cursor for 
		select a.role_id, b.user_id, c.menu_id, c.company_id 
		from t_system_role a, t_system_user b, t_system_menu_role c
		where a.role_id = b.role_id
		and b.role_id = c.role_id
		order by user_id, c.company_id;

 -- 声明游标2
 DECLARE _recordCountCur cursor for 
		select count(a.role_id)
		from t_system_role a, t_system_user b, t_system_menu_role c
		where a.role_id = b.role_id
		and b.role_id = c.role_id
		order by user_id, c.company_id;

 -- 打开游标2
 open _recordCountCur;
 -- 读取总数到变量
 fetch _recordCountCur into _recordCount;

 -- 打开游标1
 open _roleIdCur;

 -- 循环开始
 while _recordCount>0 do
 set _recordCount = _recordCount - 1;
 -- 读取一行的数据到变量
 fetch _roleIdCur into _roleId, _userId, _menuId, _companyId;

 -- 业务操作-start
 -- 更新用户业务权限
 update t_system_user set company_ids = (select company_ids from t_system_role where role_id = _roleId) 
        where user_id = _userId;
 -- 先删除原有用户菜单权限
 delete from t_system_menu_user where menu_id = _menuId and user_id = _userId and company_id = _companyId;
 -- 再新增用户菜单权限
 INSERT INTO t_system_menu_user (company_id, create_date, create_ip, create_user, delete_flag, enable_flag, 
	      update_date, update_ip, update_user, version_no, menu_id, user_id) 
    VALUES (_companyId, '2017-04-14 00:00:00', '127.0.0.1', '', 'N', 'Y', '2017-04-14 00:00:00', '127.0.0.1', '', '0', _menuId, _userId);
 -- 业务操作-end

 -- 循环结束
 end while;

 -- 关闭游标1
 close _roleIdCur;

 -- 关闭游标2
 close _recordCountCur;
END;

call updateUserByRole();


commit;