$(function() {
	dealcateGoldStatistics.init();
});

var dealcateGoldStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dealcateGoldStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDealcategoryAxuAxgcnhPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		
		var columns = columns = [ 
		 			   			[
					   				{field : 'exectime', title : '日期',  sortable : true, width : 100},
					   				{field : 'mt4volumeaxu', title : 'MT4伦敦金平仓手数', sortable : true, width : 100},		
					   				{field : 'gts2volumeaxu', title : 'GTS2伦敦金平仓手数', sortable : true, width : 100},	
					   				{field : 'volumeaxu', title : '汇总', sortable : true, width : 100}					   				
					   			]
					   			];
		
		$('#' + dealcateGoldStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				$("div.datagrid-footer [class$='exectime']:eq(0)").text("小计：");
				
				dealcateGoldStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dealcateGoldStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dealcateGoldStatistics.getQueryParams();
		common.loadGrid(dealcateGoldStatistics.dataGridId);		
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dealcateGoldStatistics.searchFormId).form('reset');
		
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
		var queryParams = dealcateGoldStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['reportType'] = 1;
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDealcategoryAxuAxgcnh?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

