$(function() {
	dailyreportFloatingprofitRatioStatistics.init();
});

var dailyreportFloatingprofitRatioStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dailyreportFloatingprofitRatioStatistics.loadDataGrid();
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
				{field : 'mt4floatingprofitRatio', title : 'MT4浮动盈亏比例', sortable : true, width : 100},
				{field : 'gts2floatingprofitRatio', title : 'GTS2浮动盈亏比例', sortable : true, width : 100},
				{field : 'floatingprofitRatio', title : '汇总', sortable : true, width : 100}
			]
			];
		$('#' + dailyreportFloatingprofitRatioStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'exectime',
			sortOrder : 'desc',
            showFooter: false,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				//$("div.datagrid-footer [class$='exectime']").text("小计：");
				
				dailyreportFloatingprofitRatioStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dailyreportFloatingprofitRatioStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dailyreportFloatingprofitRatioStatistics.getQueryParams();
		common.loadGrid(dailyreportFloatingprofitRatioStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dailyreportFloatingprofitRatioStatistics.searchFormId).form('reset');
		
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
		var queryParams = dailyreportFloatingprofitRatioStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['reportType'] = 4;
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDailyreport?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

