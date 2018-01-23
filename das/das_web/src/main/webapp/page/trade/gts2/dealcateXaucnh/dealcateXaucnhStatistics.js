$(function() {
	dealcateXaucnhStatistics.init();
});

var dealcateXaucnhStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dealcateXaucnhStatistics.loadDataGrid();
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
		
		var columns = [ 
			   			[
			   				{field : 'exectime', title : '日期',  sortable : true, width : 100},
			   				{field : 'mt4volumeaxg', title : 'MT4伦敦银平仓手数', sortable : true, width : 100},	
			   				{field : 'gts2volumeaxg', title : 'GTS2伦敦银平仓手数', sortable : true, width : 100},	
			   				{field : 'volumeaxg', title : '汇总', sortable : true, width : 100}	
			   			]
			   			];
		
		$('#' + dealcateXaucnhStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				
				dealcateXaucnhStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dealcateXaucnhStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dealcateXaucnhStatistics.getQueryParams();
		common.loadGrid(dealcateXaucnhStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dealcateXaucnhStatistics.searchFormId).form('reset');
		
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
		var queryParams = dealcateXaucnhStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['reportType'] = 4;
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDealcategoryAxuAxgcnh?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

