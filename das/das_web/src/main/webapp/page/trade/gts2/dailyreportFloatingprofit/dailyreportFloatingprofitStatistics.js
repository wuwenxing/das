$(function() {
	dailyreportFloatingprofitStatistics.init();
});

var dailyreportFloatingprofitStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dailyreportFloatingprofitStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDailyreportPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		
		var columns = [ 
			[
				{field : 'exectime', title : '日期',  sortable : true, width : 100},
				{field : 'mt4floatingprofit', title : 'MT4浮动盈亏', sortable : false, width : 50},
				{field : 'gts2floatingprofit', title : 'GTS2浮动盈亏', sortable : false, width : 50},
				{field : 'floatingprofit', title : '汇总', sortable : false, width : 50}
			]
			];
		$('#' + dailyreportFloatingprofitStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				$("div.datagrid-footer [class$='exectime']").text("小计：");
				
				dailyreportFloatingprofitStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dailyreportFloatingprofitStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dailyreportFloatingprofitStatistics.getQueryParams();
		common.loadGrid(dailyreportFloatingprofitStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dailyreportFloatingprofitStatistics.searchFormId).form('reset');
		
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
		var queryParams = dailyreportFloatingprofitStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['reportType'] = 2;
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDailyreport?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

