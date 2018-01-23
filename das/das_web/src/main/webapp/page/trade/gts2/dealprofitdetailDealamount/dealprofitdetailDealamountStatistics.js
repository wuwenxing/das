$(function() {
	dealprofitdetailDealamountStatistics.init();
});

var dealprofitdetailDealamountStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dealprofitdetailDealamountStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDealprofitdetailUseraAndDealamountPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		var columns = [ 
			[
				{field : 'exectime', title : '日期',  sortable : true, width : 100},
				{field : 'mt4dealamount', title : 'MT4交易次数', sortable : true, width : 100},
				{field : 'gts2dealamount', title : 'GTS2交易次数', sortable : true, width : 100},
				{field : 'dealamount', title : '汇总', sortable : true, width : 100}
			]
			];
		$('#' + dealprofitdetailDealamountStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				
				dealprofitdetailDealamountStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dealprofitdetailDealamountStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dealprofitdetailDealamountStatistics.getQueryParams();
		common.loadGrid(dealprofitdetailDealamountStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dealprofitdetailDealamountStatistics.searchFormId).form('reset');
		
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
		var queryParams = dealprofitdetailDealamountStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['reportType'] = 2;
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDealprofitdetailUseraAndDealamount?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

