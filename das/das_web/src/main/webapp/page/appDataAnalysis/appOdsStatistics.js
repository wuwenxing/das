
$(function() {
	appOdsStatistics.init();
});

var appOdsStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		$("select[multiple='multiple']").multiselect({
			noneSelectedText: "---请选择---", 
			selectedList: 10 // 0-based index
		}).multiselectfilter();
		$(".ui-multiselect").css("width", "160px");
		common.setEasyUiCss();
		
		appOdsStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'dasAppOdsController/findDasAppOdsPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['account'] = $("#accountSearch").val();
		//queryParams['operationType'] = $("#operationTypeSearch").combobox('getValue');
		queryParams['platformType'] = $("#platformSearch").combobox('getValue');
		queryParams['deviceType'] = $("#deviceTypeTypeSearch").combobox('getValue');
		queryParams['accountType'] = $("#accountTypeSearch").combobox('getValue');
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		var columns = [ [
                        {field : 'operationTime', title : '操作时间', sortable : false, width : 140},
                        {field : 'deviceid', title : '设备唯一标识', sortable : false, width : 100},
                        {field : 'deviceType', title : '设备类型', sortable : false, width : 80},
                        {field : 'idfa', title : 'IDFA', sortable : false, width : 100},
                        /*{field : 'operationType', title : '操作类型', sortable : false, width : 70,
                        	formatter : function(value, rowData, rowIndex) {
                        		if(!common.isBlank(rowData.operationType)){
                        			if(rowData.operationType == 1){
                        				return "启动";
                        			}
                        			if(rowData.operationType == 2){
                        				return "登陆";
                        			}
                        			if(rowData.operationType == 3){
                        				return "交易";
                        			}
                        			if(rowData.operationType == 4){
                        				return "注销";
                        			}
                        			if(rowData.operationType == 5){
                        				return "退出";
                        			}
                        		}else{
                        			return "";
                        		}
                        	}}, */ 
                        {field : 'platformType', title : '操作平台', sortable : false, width : 70},
                        {field : 'channel', title : '渠道', sortable : false, width : 90},          
                        {field : 'account', title : '用户帐号', sortable : false, width : 100},   
                        {field : 'userType', title : '用户类型', sortable : false, width : 80,
		 	 				formatter : function(value, rowData, rowIndex) {
			 					if(!common.isBlank(rowData.userType)){
			 					    if(rowData.userType == 1){
			 					    	return "游客";
			 					    }
			 					   if(rowData.userType == 2){
			 					    	return "模拟";
			 					    }
			 					   if(rowData.userType == 3){
			 					    	return "真实";
			 					   }
			 					}else{
			 						return "";
			 					}
						}},
						{field : 'accountType', title : '账号等级', sortable : false, width : 80,
		 	 				formatter : function(value, rowData, rowIndex) {
			 					if(!common.isBlank(rowData.accountType)){
			 					    if(rowData.accountType == 0){
			 					    	return "迷你";
			 					    }
			 					   if(rowData.accountType == 1){
			 					    	return "标准";
			 					    }
			 					   if(rowData.accountType == 2){
			 					    	return "VIP";
			 					   }
			 					}else{
			 						return "";
			 					}
						}},
                        
                                        
						
		 	 			{field : 'carrier', title : '设备厂商', sortable : false, width : 100},
		 	 			{field : 'model', title : '设备型号', sortable : false, width : 170},
		 	 			{field : 'platformName', title : '应用名称', sortable : false, width : 80},
		 	 			{field : 'platformVersion', title : '版本号', sortable : false, width : 80},
		 	 			{field : 'eventCategory', title : '事件类别', sortable : false, width : 100},
		 	 			{field : 'eventAction', title : '事件操作', sortable : false, width : 120},
		 	 			{field : 'eventLabel', title : '事件标签', sortable : false, width : 100},
		 	 			{field : 'eventValue', title : '事件参数', sortable : false, width : 120},
		 	 			{field : 'userIp', title : '访问IP', sortable : false, width : 100}
		  			] ];
		$('#' + appOdsStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'operationTime',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + appOdsStatistics.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');		
		queryParams['account'] = $("#accountSearch").val();
		
		//queryParams['operationType'] = $("#operationTypeSearch").combobox('getValue');
		queryParams['platformType'] = $("#platformSearch").combobox('getValue');
		queryParams['deviceType'] = $("#deviceTypeTypeSearch").combobox('getValue');
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		queryParams['accountType'] = $("#accountTypeSearch").combobox('getValue');
		
		queryParams['idfa'] = $("#idfa").val();
		queryParams['channel'] = $("#channelSearch").val()+"";	
		queryParams['deviceid'] = $("#deviceid").val();
		queryParams['carrier'] = $("#carrier").val();
		queryParams['model'] = $("#model").val();
		queryParams['platformName'] = $("#platformName").val();
		queryParams['platformVersion'] = $("#platformVersion").val();
		queryParams['eventCategory'] = $("#eventCategory").val();
		queryParams['eventAction'] = $("#eventAction").val();
		queryParams['eventLabel'] = $("#eventLabel").val();
		queryParams['eventValue'] = $("#eventValue").val();
		
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		if(!common.submitFormValidate(appOdsStatistics.searchFormId)){
			return false;
		}
		appOdsStatistics.getQueryParams();
		common.loadGrid(appOdsStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + appOdsStatistics.searchFormId).form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		var queryParams = appOdsStatistics.getQueryParams();
		var url = BASE_PATH + "dasAppOdsController/exportExcelDasAppOds?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

