$(function() {
	accountchannelStatistics.init();
});

var accountchannelStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		accountchannelStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findAccountchannelPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var accountchannelType = $("#accountchannelType").val();
		queryParams['detailed'] = "1";
		if(accountchannelType == 1){
			queryParams['type'] = "open";
		}else{
			queryParams['type'] = "active";
		}
		var columns = [];
		if(accountchannelType == 1){
			 columns = [ 
			   			[
			   				{field : 'exectime', title : '日期',  sortable : true, width : 100},
			   				{field : 'webothers', title : '移动其它', sortable : false, width : 100},
			   				{field : 'webpc', title : 'PC', sortable : false, width : 100},
			   				{field : 'loseinfo', title : '资料缺失', sortable : true, width : 100},		   				
			   				{field : 'others', title : '其它', sortable : true, width : 100},			   				
			   				{field : 'platform', title : '平台类型', sortable : true, width : 100,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(common.isBlank(rowData.platform)){
			   							return "(全部)";
			   						}else{
			   							return rowData.platform;
			   						}
			   	 				}}
			   			]
			   			];
		}
		else if(accountchannelType == 2){
			 columns = [ 
			   			[
			   				{field : 'exectime', title : '日期',  sortable : true, width : 100},			   				
			   				{field : 'pc', title : 'PC', sortable : true, width : 100},
			   				{field : 'mobile', title : 'Mobile', sortable : true, width : 100},
			   				{field : 'platform', title : '平台类型', sortable : true, width : 100,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(common.isBlank(rowData.platform)){
			   							return "(全部)";
			   						}else{
			   							return rowData.platform;
			   						}
			   	 				}}
			   			]
			   			];
		}else{
			 columns = [ 
			   			[
			   				{field : 'exectime', title : '日期',  sortable : true, width : 100},
			   				{field : 'webpc', title : 'webPC数量', sortable : false, width : 100},
			   				{field : 'android', title : 'Android数量', sortable : false, width : 100},
			   				{field : 'webios', title : 'IOS数量', sortable : true, width : 100},
			   				{field : 'webother', title : '其他数量', sortable : true, width : 100},
			   				{field : 'backoffice', title : '后台数量', sortable : true, width : 100},
			   				{field : 'trans', title : '转移数量', sortable : true, width : 100},
			   				{field : 'kinweb', title : '总笔金管家网站数量', sortable : true, width : 100},
			   				{field : 'loseinfo', title : '径资料丢失数量', sortable : true, width : 100},
			   				{field : 'pc', title : 'PC途径数量', sortable : true, width : 100},
			   				{field : 'mobile', title : 'MOBILE途径数量', sortable : true, width : 100},
			   				{field : 'other', title : '其他途径数量', sortable : true, width : 100},
			   				{field : 'platform', title : '平台类型', sortable : true, width : 100,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(common.isBlank(rowData.platform)){
			   							return "(全部)";
			   						}else{
			   							return rowData.platform;
			   						}
			   	 				}}
			   			]
			   			];
		}
		
		$('#' + accountchannelStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'exectime',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='exectime']:eq(0)").text("总数：");
				$("div.datagrid-footer [class$='exectime']:eq(1)").text("平均：");
				
				accountchannelStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + accountchannelStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		var accountchannelType = $("#accountchannelType").val();
		queryParams['detailed'] = "1";
		if(accountchannelType == 1){
			queryParams['type'] = "open";
		}else{
			queryParams['type'] = "active";
		}
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		accountchannelStatistics.getQueryParams();
		common.loadGrid(accountchannelStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + accountchannelStatistics.searchFormId).form('reset');
		
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
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
		var accountchannelType = $("#accountchannelType").val();
		var queryParams = accountchannelStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		var url = BASE_PATH + "tradeBordereauxController/exportExcelAccountchannel?type2="+accountchannelType;
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

