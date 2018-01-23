/**
 * 结余图表
 */
$(function() {
	dailyreportStatistics.init();
});

var dailyreportStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dailyreportStatistics.loadDataGrid();
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
		queryParams['type'] = "business";
		
		var columns = [ 
			[
				{field : 'exectime', title : '日期',  sortable : true, width : 100},
				{field : 'currency', title : '货币种类', sortable : true, width : 100},
				{field : 'floatingprofit', title : '浮动盈亏', sortable : false, width : 50,
	 				styler:function(value, rowData, rowIndex){
						var floatingprofit =  rowData.floatingprofit;
						if(!common.isBlank(floatingprofit) && floatingprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(floatingprofit) && floatingprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'balance', title : '结余', sortable : false, width : 50,
	 				styler:function(value, rowData, rowIndex){
						var balance =  rowData.balance;
						if(!common.isBlank(balance) && balance > 0){
							return 'color:red;';					
						}else if(!common.isBlank(balance) && balance < 0){
							return 'color:green;';
						}
				}},
				{field : 'margin', title : '保证金', sortable : true, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var margin =  rowData.margin;
						if(!common.isBlank(margin) && margin > 0){
							return 'color:red;';					
						}else if(!common.isBlank(margin) && margin < 0){
							return 'color:green;';
						}
				}},
				/*{field : 'source', title : '新旧平台标识', sortable : true, width : 100},*/
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
		$('#' + dailyreportStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				
				dailyreportStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dailyreportStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['detailed'] = "1";
		queryParams['type'] = "business";
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dailyreportStatistics.getQueryParams();
		common.loadGrid(dailyreportStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dailyreportStatistics.searchFormId).form('reset');
		
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
		var queryParams = dailyreportStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		queryParams['reportType'] = 1;
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDailyreport?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

