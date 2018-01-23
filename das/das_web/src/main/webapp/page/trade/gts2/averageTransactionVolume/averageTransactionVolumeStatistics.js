$(function() {
	averageTransactionVolumeStatistics.init();
});

var averageTransactionVolumeStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		averageTransactionVolumeStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findAverageTransactionVolumePageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		
		var columns = [ 
			   			[
			   				{field : 'exectime', title : '日期',  sortable : true, width : 100},
							{field : 'mt4avgtransvolume', title : 'MT4人均交易手数', sortable : true, width : 100},
							{field : 'gts2avgtransvolume', title : 'GTS2人均交易手数', sortable : true, width : 100},	
							{field : 'avgtransvolume', title : '汇总', sortable : true, width : 100},
			   			]
			   			];
		$('#' + averageTransactionVolumeStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				//$("div.datagrid-footer [class$='exectime']").text("小计：");
				
				averageTransactionVolumeStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + averageTransactionVolumeStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		averageTransactionVolumeStatistics.getQueryParams();
		common.loadGrid(averageTransactionVolumeStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + averageTransactionVolumeStatistics.searchFormId).form('reset');
		
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
		var queryParams = averageTransactionVolumeStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		var url = BASE_PATH + "tradeBordereauxController/exportExcelAverageTransactionVolume?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

