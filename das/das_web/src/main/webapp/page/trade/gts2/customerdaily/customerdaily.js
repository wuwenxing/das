/**
 * 结算日报表
 */
$(function() {
	customerdaily.init();
});

var customerdaily = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",

	/**
	 * 初始化
	 */
	init:function(){
		customerdaily.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeAccountDiagnosisController/findCustomerdailyPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		var columns = [ [
	 			{field : 'execdaterpt', title : '日期', sortable : false, width : 100},
	 			{field : 'accountno', title : '账户', sortable : false, width : 100},
	 			{field : 'balance', title : '账户余额', sortable : false, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var balance =  rowData.balance;
						if(!common.isBlank(balance) && balance > 0){
							return 'color:red;';					
						}else if(!common.isBlank(balance) && balance < 0){
							return 'color:green;';
						}
				}},
	 			{field : 'equity', title : '账户净值', sortable : false, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var equity =  rowData.equity;
						if(!common.isBlank(equity) && equity > 0){
							return 'color:red;';					
						}else if(!common.isBlank(equity) && equity < 0){
							return 'color:green;';
						}
				}},
	 			{field : 'deposit', title : '存款', sortable : false, width : 100},
	 			{field : 'withdraw', title : '取款', sortable : false, width : 100},
	 			{field : 'balancepreviousbalance', title : '当日平仓盈亏', sortable : false, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var balancePreviousbalance =  rowData.balancePreviousbalance;
						if(!common.isBlank(balancePreviousbalance) && balancePreviousbalance > 0){
							return 'color:red;';					
						}else if(!common.isBlank(balancePreviousbalance) && balancePreviousbalance < 0){
							return 'color:green;';
						}
				}},
	 			{field : 'floatingprofit', title : '浮动盈亏', sortable : false, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var floatingprofit =  rowData.floatingprofit;
						if(!common.isBlank(floatingprofit) && floatingprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(floatingprofit) && floatingprofit < 0){
							return 'color:green;';
						}
				}},
	 			{field : 'margin', title : '占用保证金', sortable : false, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var margin =  rowData.margin;
						if(!common.isBlank(margin) && margin > 0){
							return 'color:red;';					
						}else if(!common.isBlank(margin) && margin < 0){
							return 'color:green;';
						}
				}},
	 			{field : 'freemargin', title : '可用保证金', sortable : false, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var freemargin =  rowData.freemargin;
						if(!common.isBlank(freemargin) && freemargin > 0){
							return 'color:red;';					
						}else if(!common.isBlank(freemargin) && freemargin < 0){
							return 'color:green;';
						}
				}},
	 			{field : 'platform', title : '平台', sortable : false, width : 100}
 			] ];
		$('#' + customerdaily.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'execdate_rpt',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			},
            onLoadSuccess : function(data) {
				
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + customerdaily.dataGridId).datagrid('options').queryParams;
		queryParams['accountno'] = $("#accountnoSearch").val();
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		if(!common.submitFormValidate(customerdaily.searchFormId)){
			return false;
		}
		customerdaily.getQueryParams();
		common.loadGrid(customerdaily.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + customerdaily.searchFormId).form('reset');
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
		var queryParams = customerdaily.getQueryParams();
		var url = BASE_PATH + "tradeAccountDiagnosisController/exportExcelCustomerdaily?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

