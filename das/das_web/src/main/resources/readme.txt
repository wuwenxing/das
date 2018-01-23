一、系统结构
	common\dao\service\web 层层依赖
二、包结构划分
	-system 对应系统管理及系统日志模块
	-market 营销工具模块
	-room 对应直播间模块报表
	-website 对应网站模块报表
	-trade 对应交易模块报表
三、
	-统一常量放在com.gw.das.common.Constants.java类中；
	-枚举类型统一放在包com.gw.das.common.enums包；
	-公共接口或实现类放在base包；
	-sysConfig.properties放系统参数配置，区分UAT与PRO；
	-database.properties放数据库参数配置，区分UAT与PRO；
	-通过类UserContext.java可全局获得当前登录用户；




