update t_system_menu set parent_menu_code = 'system' where menu_code = 'systemManager';
update t_system_menu set parent_menu_code = 'statistics' where menu_code = 'systemLog';
update t_system_menu set parent_menu_code = 'statistics' where menu_code = 'market';
update t_system_menu set parent_menu_code = 'statistics' where menu_code = 'custConsultationReport';
update t_system_menu set parent_menu_code = 'statistics' where menu_code = 'APP';
update t_system_menu set parent_menu_code = 'statistics' where menu_code = 'tradeReport';
update t_system_menu set parent_menu_code = 'statistics' where menu_code = 'room';
update t_system_menu set parent_menu_code = 'statistics' where menu_code = 'report';

update t_system_menu set menu_name = '首页' where menu_code = 'home';

INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('1', '2017-04-21 09:07:34', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-04-21 11:02:56', '127.0.0.1', '超级管理员', '2', 'statistics', '统计分析', '0', 'LoginController/index', '1', '');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('2', '2017-04-21 09:07:34', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-04-21 11:02:56', '127.0.0.1', '超级管理员', '2', 'statistics', '统计分析', '0', 'LoginController/index', '1', '');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-21 09:07:34', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-04-21 11:02:56', '127.0.0.1', '超级管理员', '2', 'statistics', '统计分析', '0', 'LoginController/index', '1', '');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('4', '2017-04-21 09:07:34', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-04-21 11:02:56', '127.0.0.1', '超级管理员', '2', 'statistics', '统计分析', '0', 'LoginController/index', '1', '');

INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('1', '2017-04-21 09:08:23', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-04-21 10:40:58', '127.0.0.1', '超级管理员', '4', 'system', '系统管理', '0', 'LoginController/system', '2', '');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('2', '2017-04-21 09:08:23', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-04-21 10:40:58', '127.0.0.1', '超级管理员', '4', 'system', '系统管理', '0', 'LoginController/system', '2', '');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-21 09:08:23', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-04-21 10:40:58', '127.0.0.1', '超级管理员', '4', 'system', '系统管理', '0', 'LoginController/system', '2', '');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('4', '2017-04-21 09:08:23', '172.30.110.1', '超级管理员', 'Y', 'N', '2017-04-21 10:40:58', '127.0.0.1', '超级管理员', '4', 'system', '系统管理', '0', 'LoginController/system', '2', '');

commit;


drop PROCEDURE if exists updateUser_2;
CREATE PROCEDURE updateUser_2 ()
BEGIN
	-- 声明变量
 DECLARE _userId int;-- 用户ID
 DECLARE _menuId int;-- 菜单ID
 DECLARE _companyId int;-- 业务权限ID
 DECLARE _recordCount int;-- 总数

 -- 声明游标1(有系统管理权限)
 DECLARE _userIdCur cursor for 
		select user_id, menu_id, company_id 
		from t_system_menu_user 
		where menu_id in (select menu_id from t_system_menu where menu_code = 'systemManager');

 -- 声明游标2(有系统管理权限总数)
 DECLARE _recordCountCur cursor for 
		select count(user_id)
		from t_system_menu_user 
		where menu_id in (select menu_id from t_system_menu where menu_code = 'systemManager');

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
 fetch _userIdCur into _userId, _menuId, _companyId;

 -- 业务操作-start

 -- 先删除原有用户菜单权限
 delete from t_system_menu_user where menu_id = (select menu_id from t_system_menu where menu_code = 'system' and company_id = _companyId) and user_id = _userId and company_id = _companyId;
 -- 再新增用户菜单权限
 INSERT INTO t_system_menu_user (company_id, create_date, create_ip, create_user, delete_flag, enable_flag, 
	      update_date, update_ip, update_user, version_no, menu_id, user_id) 
    VALUES (_companyId, '2017-04-14 00:00:00', '127.0.0.1', '', 'N', 'Y', '2017-04-14 00:00:00', '127.0.0.1', '', '0', (select menu_id from t_system_menu where menu_code = 'system' and company_id = _companyId), _userId);
 -- 业务操作-end

 -- 循环结束
 end while;

 -- 关闭游标1
 close _userIdCur;

 -- 关闭游标2
 close _recordCountCur;
END;

call updateUser_2();


commit;





drop PROCEDURE if exists updateUser_1;
CREATE PROCEDURE updateUser_1 ()
BEGIN
	-- 声明变量
 DECLARE _userId int;-- 用户ID
 DECLARE _menuId int;-- 菜单ID
 DECLARE _companyId int;-- 业务权限ID
 DECLARE _recordCount int;-- 总数

 -- 声明游标1(有系统管理权限)
 DECLARE _userIdCur cursor for 
		select user_id, menu_id, company_id 
		from t_system_menu_user;

 -- 声明游标2(有系统管理权限总数)
 DECLARE _recordCountCur cursor for 
		select count(user_id)
		from t_system_menu_user;

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
 fetch _userIdCur into _userId, _menuId, _companyId;

 -- 业务操作-start

 -- 先删除原有用户菜单权限
 delete from t_system_menu_user where menu_id = (select menu_id from t_system_menu where menu_code = 'statistics' and company_id = _companyId) and user_id = _userId and company_id = _companyId;
 -- 再新增用户菜单权限
 INSERT INTO t_system_menu_user (company_id, create_date, create_ip, create_user, delete_flag, enable_flag, 
	      update_date, update_ip, update_user, version_no, menu_id, user_id) 
    VALUES (_companyId, '2017-04-15 00:00:00', '127.0.0.1', '', 'N', 'Y', '2017-04-15 00:00:00', '127.0.0.1', '', '0', (select menu_id from t_system_menu where menu_code = 'statistics' and company_id = _companyId), _userId);
 -- 业务操作-end

 -- 循环结束
 end while;

 -- 关闭游标1
 close _userIdCur;

 -- 关闭游标2
 close _recordCountCur;
END;

call updateUser_1();


commit;





-- 只新增恒信业务下的菜单(业务报表、双周报表及其子菜单)
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-13 09:26:06', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-25 11:07:00', '172.30.110.1', 'hxuser', '5', 'businessReport', '业务报表', '1', 'TradeDetailController/portal', '5', 'tradeReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-13 09:28:40', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-24 16:41:40', '172.30.110.1', 'hxuser', '9', 'profitdetailPanel', '交易记录面板', '2', 'TradeController/findDaysOrMonthsSumByProfitdetail', '1', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-13 09:27:57', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-24 16:41:55', '172.30.110.1', 'hxuser', '7', 'cashandaccountdetailPanel', '存取款及账户面板', '2', 'TradeController/findDaysOrMonthsSumByCashandaccountdetail', '2', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-24 16:38:41', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-24 16:40:54', '172.30.110.1', 'hxuser', '2', 'top10WinnerLoser', '十大赢家|十大亏损', '2', 'TradeController/top10WinnerLoserYearsList', '3', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-24 16:39:37', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-24 16:40:57', '172.30.110.1', 'hxuser', '1', 'top10CashinCashout', '十大存款|十大取款', '2', 'TradeController/top10CashinCashoutYearsList', '4', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-24 16:39:58', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-24 16:41:00', '172.30.110.1', 'hxuser', '1', 'top10Trader', '十大交易量客户', '2', 'TradeController/top10TraderYearsList', '5', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-24 16:40:39', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-24 16:40:45', '172.30.110.1', 'hxuser', '1', 'top10VolumeStatistics', '交易统计-手数', '2', 'TradeController/top10VolumeStatisticsYearsList', '6', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-24 16:43:43', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-25 11:25:11', '172.30.110.1', 'hxuser', '1', 'top10PositionStatistics', '交易统计-持仓时间', '2', 'TradeController/top10PositionStatisticsYearsList', '7', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 10:45:44', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-25 16:30:17', '172.30.110.1', 'hxuser', '2', 'GTS2SingleWay', 'GTS2下单途径', '2', 'tradeBordereauxController/findDealchannelList', '8', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:23:56', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-25 16:30:28', '172.30.110.1', 'hxuser', '5', 'GTS2SingleWayPage', 'GTS2下单途径(详情页)', '2', 'tradeBordereauxController/dealchannelPage', '9', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 10:46:42', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 16:43:19', '172.30.110.1', 'hxuser', '7', 'monthlyGrossProfit', '毛利|交易手数', '2', 'tradeBordereauxController/findBusinessDealprofitdetailList', '10', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:26:43', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:47:36', '172.30.110.1', 'hxuser', '8', 'monthlyGrossProfitPage', '毛利(详情页)', '2', 'tradeBordereauxController/dealprofitdetailGrossprofitPage', '11', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:27:27', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 14:58:45', '172.30.110.1', 'hxuser', '10', 'tradeVolumePage', '交易手数(详情页)', '2', 'tradeBordereauxController/dealprofitdetailVolumePage', '12', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 10:47:45', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 16:42:35', '172.30.110.1', 'hxuser', '6', 'balanceOfTheMonth', '结余', '2', 'tradeBordereauxController/findBusinessDailyreportList', '13', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:17:31', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:47:45', '172.30.110.1', 'hxuser', '11', 'balanceOfTheMonthPage', '结余(详情页)', '2', 'tradeBordereauxController/dailyreportPage', '14', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 10:49:06', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:53:16', '172.30.110.1', 'hxuser', '6', 'netDeposit', '净入金', '2', 'TradeController/cashandaccountdetailStatisticsYearsList', '15', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:00:47', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:53:20', '172.30.110.1', 'hxuser', '8', 'netDepositPage', '净入金(详情页)', '2', 'TradeController/cashandaccountdetailStatisticsYearsPage', '16', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 10:49:45', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:53:24', '172.30.110.1', 'hxuser', '11', 'MT4NetProfitAndLoss', 'MT4/GTS2净盈亏小时图', '2', 'tradeBordereauxController/findDealprofithourList', '17', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 16:25:08', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:53:27', '172.30.110.1', 'hxuser', '8', 'MT4GTS2NetProfitAndLoss', 'MT4/GTS2净盈亏小时图(详情页)', '2', 'tradeBordereauxController/dealprofithourPage', '18', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 17:00:00', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 16:06:52', '172.30.110.1', 'hxuser', '6', 'tradeRecodeCount', '交易记录总结', '2', 'tradeBordereauxController/findDealprofitdetailPageList', '19', 'businessReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-26 15:12:15', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:53:33', '172.30.110.1', 'hxuser', '3', 'tradeRecodeCountPage', '交易记录总结(详情页)', '2', 'tradeBordereauxController/dealprofitdetailPage', '20', 'businessReport');

INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-19 15:48:16', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-19 15:48:16', '172.30.110.1', 'hxuser', '0', 'biweeklyReport', '双周报表', '1', 'tradeBordereauxController/biweeklyReport', '6', 'tradeReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 09:55:20', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 14:50:24', '172.30.110.1', 'hxuser', '5', 'traderUserNumber', '交易人数|次数', '2', 'tradeBordereauxController/findDealprofitdetailList', '1', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:28:04', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-25 16:45:55', '172.30.110.1', 'hxuser', '7', 'traderUserNumberPage', '交易人数(详情页)', '2', 'tradeBordereauxController/dealprofitdetailUseramountPage', '2', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:53:34', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:53:46', '172.30.110.1', 'hxuser', '8', 'traderNumberPage', '交易次数(详情页)', '2', 'tradeBordereauxController/dealprofitdetailDealamountPage', '3', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 10:14:08', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:56:08', '172.30.110.1', 'hxuser', '6', 'dealcateGold', '伦敦|人民币金银平仓手数分布', '2', 'tradeBordereauxController/findDealcategoryList', '4', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:56:50', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:56:13', '172.30.110.1', 'hxuser', '11', 'dealcateGoldPage', '伦敦|人民币金银平仓手数分布(详情页)', '2', 'tradeBordereauxController/dealcateGoldPage', '5', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 10:16:16', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:53:54', '172.30.110.1', 'hxuser', '6', 'floatingprofit', '浮盈|结余|占用保证金比例|浮盈比例', '2', 'tradeBordereauxController/findDailyreportList', '6', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:19:34', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:53:57', '172.30.110.1', 'hxuser', '5', 'floatingprofitPage', '浮盈(详情页)', '2', 'tradeBordereauxController/dailyreportFloatingprofitPage', '7', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:22:42', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:54:00', '172.30.110.1', 'hxuser', '8', 'dailyreportbalancePage', '结余(详情页)', '2', 'tradeBordereauxController/dailyreportPreviousbalancePage', '8', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:21:52', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:54:03', '172.30.110.1', 'hxuser', '5', 'dailyreportMarginPage', '占用保证金比例(详情页)', '2', 'tradeBordereauxController/dailyreportMarginRatioPage', '9', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:20:58', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:54:07', '172.30.110.1', 'hxuser', '5', 'floatingprofitRatioPage', '浮盈比例(详情页)', '2', 'tradeBordereauxController/dailyreportFloatingprofitRatioPage', '10', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 10:18:09', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:54:10', '172.30.110.1', 'hxuser', '4', 'MT4AndGts2Report', 'MT4/GTS2报表', '2', 'tradeBordereauxController/findDealprofitdetailMT4AndGts2PageList', '11', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:55:11', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:54:13', '172.30.110.1', 'hxuser', '5', 'MT4AndGts2ReportPage', 'MT4/GTS2报表(详情页)', '2', 'tradeBordereauxController/findProfitdetailMT4AndGts2Page', '12', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 10:18:33', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:54:15', '172.30.110.1', 'hxuser', '5', 'tradeTypeData', '交易类别数据', '2', 'tradeBordereauxController/findDealcategoryPageList', '13', 'biweeklyReport');
INSERT INTO t_system_menu (company_id, create_date, create_ip, create_user, enable_flag, delete_flag, update_date, update_ip, update_user, version_no, menu_code, menu_name, menu_type, menu_url, order_code, parent_menu_code) VALUES ('3', '2017-04-25 14:59:13', '172.30.110.1', 'hxuser', 'Y', 'N', '2017-04-26 15:54:19', '172.30.110.1', 'hxuser', '6', 'tradeTypeDataPage', '交易类别数据(详情页)', '2', 'tradeBordereauxController/dealcategoryPage', '14', 'biweeklyReport');



commit;







