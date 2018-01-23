$(function() {
	loginStatistics.init();
});

var loginStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		loginStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findLoginStatisticsPage';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
	 			{field : 'dateTime', title : '日期', sortable : true, width : 80},
	 			{field : 'account', title : '交易帐号', sortable : true, width : 80},
	 			{field : 'userType', title : '用户类型', sortable : true, width : 80},
	 			{field : 'openAccountTime', title : '开户时间', sortable : true, width : 100},
	 			{field : 'activeTime', title : '激活时间', sortable : true, width : 100},
	 			{field : 'liveRoomCount', title : '直播大厅-pv数', sortable : true, width : 100},
	 			{field : 'liveRoomCountMin', title : '直播大厅-累计次数', sortable : true, width : 100},
	 			{field : 'vipRoomCount', title : 'VIP房间-pv数', sortable : true, width : 100},
	 			{field : 'vipRoomCountMin', title : 'VIP房间-累计次数', sortable : true, width : 100},
	 			{field : 'latestTradeTime', title : '最后交易时间', sortable : true, width : 100}
 			] ];
		$('#' + loginStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowkey', // 唯一字段
			sortName : 'dateTime',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + loginStatistics.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');// 日期
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['openAccountTimeStart'] = $("#openAccountTimeStartSearch").datebox('getValue');// 开户时间
		queryParams['openAccountTimeEnd'] = $("#openAccountTimeEndSearch").datebox('getValue');
		queryParams['activeTimeStart'] = $("#activeTimeStartSearch").datebox('getValue');// 激活时间
		queryParams['activeTimeEnd'] = $("#activeTimeEndSearch").datebox('getValue');
		queryParams['account'] = $("#accountSearch").val();
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		loginStatistics.getQueryParams();
		common.loadGrid(loginStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + loginStatistics.searchFormId).form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#openAccountTimeStartSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#openAccountTimeEndSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#activeTimeStartSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#activeTimeEndSearch').datebox({
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
		var queryParams = loginStatistics.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/loginStatisticsExportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















