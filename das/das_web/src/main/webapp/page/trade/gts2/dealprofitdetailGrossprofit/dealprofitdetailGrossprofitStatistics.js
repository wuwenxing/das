/**
 * 毛利图表
 */
$(function() {
	dealprofitdetailGrossprofitStatistics.init();
});

var dealprofitdetailGrossprofitStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dealprofitdetailGrossprofitStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDealprofitdetailPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ 
			[
				{field : 'exectime', title : '日期',  sortable : true, width : 100},
				{field : 'grossprofit', title : '毛利', sortable : false, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var grossprofit =  rowData.grossprofit;
						if(!common.isBlank(grossprofit) && grossprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(grossprofit) && grossprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'platform', title : '平台类型', sortable : false, width : 100,
					formatter : function(value, rowData, rowIndex) {
						if(common.isBlank(rowData.platform)){
							return "(全部)";
						}else{
							return rowData.platform;
						}
	 				}}
			]
			];
		$('#' + dealprofitdetailGrossprofitStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				
				dealprofitdetailGrossprofitStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dealprofitdetailGrossprofitStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dealprofitdetailGrossprofitStatistics.getQueryParams();
		common.loadGrid(dealprofitdetailGrossprofitStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dealprofitdetailGrossprofitStatistics.searchFormId).form('reset');
		
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
		var queryParams = dealprofitdetailGrossprofitStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDealprofitdetail?type=1";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

