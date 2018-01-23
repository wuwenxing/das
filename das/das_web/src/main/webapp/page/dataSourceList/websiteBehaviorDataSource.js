$(function() {
	websiteBehaviorDataSource.init();
});

var websiteBehaviorDataSource = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		websiteBehaviorDataSource.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DataSourceListController/findWebsiteBehaviorDataSourcePageList';
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
		
		queryParams['behaviorDetail'] = $("#behaviorDetailSearch").val();// 行为明细
		queryParams['behaviorType'] = $("#behaviorTypeSearch").combobox('getValue'); //行为类型		
		queryParams['eventCategory'] = $("#eventCategorySearch").val();// 事件对象
		queryParams['eventAction'] = $("#eventActionSearch").val();// 互动类型
		queryParams['browser'] = $("#browserSearch").val();// 浏览器信息
		queryParams['prevUrl'] = $("#prevUrlSearch").val();//上一次URL地址
		queryParams['url'] = $("#urlSearch").val();//访问URL
		queryParams['ip'] = $("#ipSearch").val();//终端IP

		var columns = [ [
	 			{field : 'sessionId', title : '会话标识', sortable : false, width : 230},
	 			{field : 'visitTime', title : '访问时间', sortable : false, width : 130},
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
	 			{field : 'deviceId', title : '交易客户端标识', sortable : false, width : 200},
	 			//{field : 'markWords', title : '标记关键字', sortable : false, width : 100},
	 			
	 			{field : 'utmcsr', title : '来源(站外)', sortable : false, width : 100},
	 			{field : 'utmcmd', title : '媒介(站外)', sortable : false, width : 100},
	 			{field : 'utmccn', title : '系列(站外)', sortable : false, width : 200},	
	 			{field : 'utmcct', title : '组(站外)', sortable : false, width : 200},
	 			{field : 'utmctr', title : '关键字(站外)', sortable : false, width : 100},
	 			
	 			{field : 'utmcsr2', title : '来源(站内)', sortable : false, width : 100},
	 			{field : 'utmcmd2', title : '媒介(站内)', sortable : false, width : 100},
	 			{field : 'utmccn2', title : '系列(站内)', sortable : false, width : 100},
	 			{field : 'utmcct2', title : '组(站内)', sortable : false, width : 100},
	 			{field : 'utmctr2', title : '关键字(站内)', sortable : false, width : 100},
	 			
	 			{field : 'behaviorDetail', title : '行为明细', sortable : false, width : 200},
	 			{field : 'behaviorType', title : '行为类型', sortable : false, width : 100,
 	 				formatter : function(value, rowData, rowIndex) {
	 					if(!common.isBlank(rowData.behaviorType)){
	 					    if(rowData.behaviorType == 1){
	 					    	return "访问";
	 					    }else if(rowData.behaviorType == 2){
	 					    	return "咨询";
	 					    }else if(rowData.behaviorType == 3){
	 					    	return "模拟";
	 					    }else if(rowData.behaviorType == 4){
	 					    	return "真实";
	 					    }else if(rowData.behaviorType == 6){
	 					    	return "事件";
	 					    }else{
	 						  return "";
	 					   }
	 					}else{
	 						return "";
	 					}
				}},
	 			
			    /*{field : 'advisoryType', title : '咨询类型', sortable : false, width : 100,
 	 				formatter : function(value, rowData, rowIndex) {
	 					if(!common.isBlank(rowData.advisoryType)){
	 					    if(rowData.advisoryType == 1){
	 					    	return "qq";
	 					    }else if(rowData.advisoryType == 2){
	 					    	return "live800";
	 					    }else{
	 						  return "";
	 					   }
	 					}else{
	 						return "";
	 					}
				}},	*/ 			
	 			{field : 'eventCategory', title : '事件类别', sortable : false, width : 120},
	 			{field : 'eventAction', title : '事件操作', sortable : false, width : 100},
	 			{field : 'browser', title : '浏览器信息', sortable : false, width : 100,
 	 				formatter : function(value, rowData, rowIndex) {
	 					  return common.browserType(rowData.browser);
				}},
				{field : 'prevUrl', title : '上一次URL地址', sortable : false, width : 300,
 	 				formatter : function(value, rowData, rowIndex) {
 	 					if(!common.isBlank(rowData.prevUrl) && rowData.prevUrl.length > 200){
 	 						return rowData.prevUrl.substr(0,200)+"...";
 	 					}
 	 					return rowData.prevUrl;
				}},
	 			{field : 'url', title : '访问URL', sortable : false, width : 500,
 	 				formatter : function(value, rowData, rowIndex) {
 	 					if(!common.isBlank(rowData.url) && rowData.url.length > 200){
 	 						return rowData.url.substr(0,200)+"...";
 	 					}
 	 					return rowData.url;
				}},
	 			{field : 'ip', title : '终端IP', sortable : false, width : 100}
	 			
 			] ];
		$('#' + websiteBehaviorDataSource.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[{field : "userId", title : 'UUID', sortable : false, align:'center',width : 220}]],
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'visitTime',
			sortOrder : '',
    		collapsible: true,
    		fitColumns:false,
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + websiteBehaviorDataSource.dataGridId).datagrid('options');
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
		
		queryParams['behaviorDetail'] = $("#behaviorDetailSearch").val();// 行为明细
		queryParams['behaviorType'] = $("#behaviorTypeSearch").combobox('getValue'); //行为类型		
		queryParams['eventCategory'] = $("#eventCategorySearch").val();// 事件对象
		queryParams['eventAction'] = $("#eventActionSearch").val();// 互动类型
		queryParams['browser'] = $("#browserSearch").val();// 浏览器信息
		queryParams['prevUrl'] = $("#prevUrlSearch").val();//上一次URL地址
		queryParams['url'] = $("#urlSearch").val();//访问URL
		queryParams['ip'] = $("#ipSearch").val();//终端IP

		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		websiteBehaviorDataSource.getQueryParams();
		common.loadGrid(websiteBehaviorDataSource.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + websiteBehaviorDataSource.searchFormId).form('reset');
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
		var queryParams = websiteBehaviorDataSource.getQueryParams();
		var url = BASE_PATH + "DataSourceListController/exportExcelWebsiteBehaviorDataSource?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}




