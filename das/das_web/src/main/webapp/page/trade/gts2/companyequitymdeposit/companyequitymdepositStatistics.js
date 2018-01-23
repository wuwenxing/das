/**
 * 净入金图表
 */
$(function() {
	companyequitymdepositStatistics.init();
});

var companyequitymdepositStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		companyequitymdepositStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'TradeController/cashandaccountdetailStatisticsYearsPage';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		var columns = [ 
			[
				{field : 'exectime', title : '日期',  sortable : true, width : 100},
				{field : 'equitymdeposit', title : '当天净入金', sortable : false, width : 50,
	 				styler:function(value, rowData, rowIndex){
						if(!common.isBlank(value) && value > 0){
							return 'color:red;';					
						}else if(!common.isBlank(value) && value < 0){
							return 'color:green;';
						}
				}},
				{field : 'platformtype', title : '平台类型', sortable : false, width : 50,
					formatter : function(value, rowData, rowIndex) {
						if(common.isBlank(rowData.platformtype)){
							return "(全部)";
						}else{
							return rowData.platformtype;
						}
	 				}}
			]
			];
		$('#' + companyequitymdepositStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				
				companyequitymdepositStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + companyequitymdepositStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		companyequitymdepositStatistics.getQueryParams();
		common.loadGrid(companyequitymdepositStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + companyequitymdepositStatistics.searchFormId).form('reset');
		
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
		var queryParams = companyequitymdepositStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		var url = BASE_PATH + "TradeController/exportExcelCompanyequitymdeposit?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

