$(function() {
	dealcateSilverStatistics.init();
});

var dealcateSilverStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dealcateSilverStatistics.loadDataGrid();
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
			   				{field : 'mt4volumeaxucnh', title : 'MT4人民币金平仓手数', sortable : true, width : 100},	
			   				{field : 'gts2volumeaxucnh', title : 'GTS2人民币金平仓手数', sortable : true, width : 100},	
			   				{field : 'volumeaxucnh', title : '汇总', sortable : true, width : 100}
			   			]
			   			];
		
		$('#' + dealcateSilverStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				
				dealcateSilverStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dealcateSilverStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['detailed'] = "1";
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dealcateSilverStatistics.getQueryParams();
		common.loadGrid(dealcateSilverStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dealcateSilverStatistics.searchFormId).form('reset');
		
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
		var queryParams = dealcateSilverStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['reportType'] = 2;
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDealcategoryAxuAxgcnh?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

