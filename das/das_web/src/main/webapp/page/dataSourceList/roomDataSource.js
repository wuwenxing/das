$(function() {
	roomDataSource.init();
});

var roomDataSource = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		roomDataSource.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DataSourceListController/findRoomDataSourcePageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['userId'] = $("#userIdSearch").val();// UUID
		queryParams['sessionId'] = $("#sessionIdSearch").val();// 会话标识
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');//终端类型
		queryParams['deviceId'] = $("#deviceIdSearch").val();// 终端标识
		
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源(站外)
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介(站外)
		queryParams['utmccn'] = $("#utmccnSearch").val();// 系列(站外)
		queryParams['utmcct'] = $("#utmcctSearch").val();// 组(站外)
		queryParams['utmctr'] = $("#utmctrSearch").val();// 关键字(站外)
		
		queryParams['utmcsr2'] = $("#utmcsr2Search").val();// 来源(站内)
		queryParams['utmcmd2'] = $("#utmcmd2Search").val();// 媒介(站内)
		queryParams['utmccn2'] = $("#utmccn2Search").val();// 系列(站内)
		queryParams['utmcct2'] = $("#utmcct2Search").val();// 组(站内)
		queryParams['utmctr2'] = $("#utmctr2Search").val();// 关键字(站内)
		
		queryParams['userTel'] = $("#userTelSearch").val();// 电话号码
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue'); //用户类型		
		queryParams['userName'] = $("#userNameSearch").val();// 用户名称
		queryParams['roomName'] = $("#roomNameSearch").val();//房间名称
		queryParams['userSource'] = $("#userSourceSearch").val();//来源
		queryParams['useEquipment'] = $("#useEquipmentSearch").val();//使用设备
		queryParams['tradingAccount'] = $("#tradingAccountSearch").val();//交易帐号
		queryParams['tradingPlatform'] = $("#tradingPlatformSearch").val();//交易平台
		
		queryParams['operateEntrance'] = $("#operateEntranceSearch").val();//用户入口
		queryParams['operationType'] = $("#operationTypeSearch").combobox('getValue');//操作类型
		queryParams['touristId'] = $("#touristIdSearch").val();//访客ID
		queryParams['nickName'] = $("#nickNameSearch").val();//用户昵称
		queryParams['email'] = $("#emailSearch").val();//用户邮箱
		queryParams['videoName'] = $("#videoNameSearch").val();//教学视频名称
		queryParams['courseName'] = $("#courseNameSearch").val();//课程名称
		
		queryParams['teacherName'] = $("#teacherNameSearch").val();//老师名称
		queryParams['eventCategory'] = $("#eventCategorySearch").val();//事件对象
		queryParams['eventAction'] = $("#eventActionSearch").val();//互动类型
		queryParams['userIp'] = $("#userIpSearch").val();//访问IP
		
		var columns = [ [

	 			{field : 'sessionId', title : '会话标识', sortable : false, width : 230},
	 			{field : 'operationTime', title : '访问时间', sortable : false, width : 130},
	 			/*{field : 'businessPlatform', title : '业务平台', sortable : false, width : 70,
 	 				formatter : function(value, rowData, rowIndex) {
	 					if(!common.isBlank(rowData.businessPlatform)){
	 					    if(rowData.businessPlatform == 1){
	 					    	return "外汇";
	 					    }
	 					    else if(rowData.businessPlatform == 2){
	 					    	return "贵金属";
	 					    }
	 					    else if(rowData.businessPlatform == 3){
	 					    	return "恒信";
	 					    }
	 					    else if(rowData.businessPlatform == 4){
	 					    	return "创富";
	 					    }
	 					    else if(rowData.businessPlatform == 5){
	 					    	return "烧麦";
	 					   }else{
	 						  return "";
	 					   }
	 					}else{
	 						return "";
	 					}
				}},*/
	 			//{field : 'platformName', title : '平台名称', sortable : false, width : 100},
	 			{field : 'platformType', title : '终端类型', sortable : false, width : 70,
 	 				formatter : function(value, rowData, rowIndex) {
	 					if(!common.isBlank(rowData.platformType)){
	 					    if(rowData.platformType == 1){
	 					    	return "移动端";
	 					    }
	 					    else{
	 						  return "PC";
	 					   }
	 					}else{
	 						return "PC";
	 					}
				}},
	 			//{field : 'platformVersion', title : '平台版本', sortable : false, width : 70},
	 			{field : 'deviceId', title : '交易客户端标识', sortable : false, width : 100},
	 			//{field : 'markWords', title : '标记关键字', sortable : false, width : 100},
	 			
	 			{field : 'utmcsr', title : '来源(站外)', sortable : false, width : 100},
	 			{field : 'utmcmd', title : '媒介(站外)', sortable : false, width : 100},
	 			{field : 'utmccn', title : '系列(站外)', sortable : false, width : 100},	
	 			{field : 'utmcct', title : '组(站外)', sortable : false, width : 100},
	 			{field : 'utmctr', title : '关键字(站外)', sortable : false, width : 100},
	 			
	 			{field : 'utmcsr2', title : '来源(站内)', sortable : false, width : 100},
	 			{field : 'utmcmd2', title : '媒介(站内)', sortable : false, width : 100},
	 			{field : 'utmccn2', title : '系列(站内)', sortable : false, width : 100},
	 			{field : 'utmcct2', title : '组(站内)', sortable : false, width : 100},
	 			{field : 'utmctr2', title : '关键字(站内)', sortable : false, width : 100},
	 			
	 			{field : 'userTel', title : '电话号码', sortable : false, width : 100},
	 			{field : 'userType', title : '用户类型', sortable : false, width : 100,
 	 				formatter : function(value, rowData, rowIndex) {
	 					if(!common.isBlank(rowData.userType)){
	 					    if(rowData.userType == 1){
	 					    	return "游客";
	 					    }else if(rowData.userType == 2){
	 					    	return "注册";
	 					    }else if(rowData.userType == 3){
	 					    	return "模拟用户";
	 					    }else if(rowData.userType == 4){
	 					    	return "真实A用户";
	 					    }else if(rowData.userType == 5){
	 					    	return "真实N用户";
	 					    }else if(rowData.userType == 6){
	 					    	return "VIP用户";
	 					    }else if(rowData.userType == 7){
	 					    	return "分析师";
	 					    }else if(rowData.userType == 8){
	 					    	return "管理员";
	 					    }else if(rowData.userType == 9){
	 					    	return "客服";
	 					    }else{
	 						  return "";
	 					   }
	 					}else{
	 						return "";
	 					}
				}},
	 			{field : 'userName', title : '用户名称', sortable : false, width : 100},
	 			{field : 'roomName', title : '房间名称', sortable : false, width : 200},
	 			{field : 'userSource', title : '来源', sortable : false, width : 100},
	 			{field : 'useEquipment', title : '使用设备', sortable : false, width : 100,
 	 				formatter : function(value, rowData, rowIndex) {
	 					  return common.browserType(rowData.useEquipment);
				}},
	 			{field : 'tradingAccount', title : '交易帐号', sortable : false, width : 200},
	 			{field : 'tradingPlatform', title : '交易平台', sortable : false, width : 100},
			    {field : 'operateEntrance', title : '用户入口', sortable : false, width : 100},
	 			{field : 'operationType', title : '操作类型', sortable : false, width : 100,
 	 				formatter : function(value, rowData, rowIndex) {
	 					if(!common.isBlank(rowData.operationType)){
	 					    if(rowData.operationType == 1){
	 					    	return "上线";
	 					    }else if(rowData.operationType == 2){
	 					    	return "公聊";
	 					    }else if(rowData.operationType == 3){
	 					    	return "注册";
	 					    }else if(rowData.operationType == 4){
	 					    	return "登录";
	 					    }else if(rowData.operationType == 5){
	 					    	return "退出";
	 					    }else if(rowData.operationType == 6){
	 					    	return "下线";
	 					    }else if(rowData.operationType == 7){
	 					    	return "视频";
	 					    }else if(rowData.operationType == 8){
	 					    	return "私聊";
	 					    }else{
	 						  return "";
	 					   }
	 					}else{
	 						return "";
	 					}
				}},
	 			{field : 'touristId', title : '访客ID', sortable : false, width : 120},
	 			{field : 'nickName', title : '用户昵称', sortable : false, width : 100},
	 			{field : 'email', title : '用户邮箱', sortable : false, width : 100},
	 			{field : 'videoName', title : '教学视频名称', sortable : false, width : 100},
	 			{field : 'courseName', title : '课程名称', sortable : false, width : 200},
	 			{field : 'teacherName', title : '老师名称', sortable : false, width : 100},
	 			{field : 'eventCategory', title : '事件类别', sortable : false, width : 100},
	 			{field : 'eventAction', title : '事件操作', sortable : false, width : 100},
	 			{field : 'userIp', title : '访问IP', sortable : false, width : 100}
	 			
	 			
 			] ];
		$('#' + roomDataSource.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[{field : "userId", title : 'UUID', sortable : false, align:'center',width : 220}]],
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'operationTime',
			sortOrder : '',
    		collapsible: true,
    		fitColumns:false,
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + roomDataSource.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['userId'] = $("#userIdSearch").val();// UUID
		queryParams['sessionId'] = $("#sessionIdSearch").val();// 会话标识
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');//终端类型
		queryParams['deviceId'] = $("#deviceIdSearch").val();// 终端标识
		
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源(站外)
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介(站外)
		queryParams['utmccn'] = $("#utmccnSearch").val();// 系列(站外)
		queryParams['utmcct'] = $("#utmcctSearch").val();// 组(站外)
		queryParams['utmctr'] = $("#utmctrSearch").val();// 关键字(站外)
		
		queryParams['utmcsr2'] = $("#utmcsr2Search").val();// 来源(站内)
		queryParams['utmcmd2'] = $("#utmcmd2Search").val();// 媒介(站内)
		queryParams['utmccn2'] = $("#utmccn2Search").val();// 系列(站内)
		queryParams['utmcct2'] = $("#utmcct2Search").val();// 组(站内)
		queryParams['utmctr2'] = $("#utmctr2Search").val();// 关键字(站内)
		
		queryParams['userTel'] = $("#userTelSearch").val();// 电话号码
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue'); //用户类型		
		queryParams['userName'] = $("#userNameSearch").val();// 用户名称
		queryParams['roomName'] = $("#roomNameSearch").val();//房间名称
		queryParams['userSource'] = $("#userSourceSearch").val();//来源
		queryParams['useEquipment'] = $("#useEquipmentSearch").val();//使用设备
		queryParams['tradingAccount'] = $("#tradingAccountSearch").val();//交易帐号
		queryParams['tradingPlatform'] = $("#tradingPlatformSearch").val();//交易平台
		
		queryParams['operateEntrance'] = $("#operateEntranceSearch").val();//用户入口
		queryParams['operationType'] = $("#operationTypeSearch").combobox('getValue');//操作类型
		queryParams['touristId'] = $("#touristIdSearch").val();//访客ID
		queryParams['nickName'] = $("#nickNameSearch").val();//用户昵称
		queryParams['email'] = $("#emailSearch").val();//用户邮箱
		queryParams['videoName'] = $("#videoNameSearch").val();//教学视频名称
		queryParams['courseName'] = $("#courseNameSearch").val();//课程名称
		
		queryParams['teacherName'] = $("#teacherNameSearch").val();//老师名称
		queryParams['eventCategory'] = $("#eventCategorySearch").val();//事件对象
		queryParams['eventAction'] = $("#eventActionSearch").val();//互动类型
		queryParams['userIp'] = $("#userIpSearch").val();//访问IP
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		roomDataSource.getQueryParams();
		common.loadGrid(roomDataSource.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + roomDataSource.searchFormId).form('reset');
		$('#startTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = roomDataSource.getQueryParams();
		var url = BASE_PATH + "DataSourceListController/exportExcelRoomDataSource?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

