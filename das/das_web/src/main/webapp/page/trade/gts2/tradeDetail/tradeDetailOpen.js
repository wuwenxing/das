/**
 * 持仓交易
 */
$(function() {
	tradeDetailOpen.init();
});

var tradeDetailOpen = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",

	/**
	 * 初始化
	 */
	init:function(){
		tradeDetailOpen.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeAccountDiagnosisController/findTradeDetailOpenPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		var columns = [ [
	            {field : 'accountno', title : '账户', sortable : false, width : 50},
	            {field : 'positionid', title : '持仓单号', sortable : false, width : 50},
	 			{field : 'opentime', title : '开仓时间', sortable : false, width : 70},
	 			{field : 'direction', title : '开仓方向', sortable : false, width : 50,
                	formatter : function(value, rowData, rowIndex) {
                		var direction = rowData.direction;
	 					if(!common.isBlank(direction) && '2' == direction){
	 						return '卖';
	 					}else if(!common.isBlank(direction) && '1' == direction){
	 						return '买';
	 					}else{
	 						return rowData.direction;
	 					}
				}},
	 			{field : 'openprice', title : '开仓价格', sortable : false, width : 50},	 			
				{field : 'volume', title : '开仓手数', sortable : false, width : 50},
				{field : 'surplusvolume', title : '剩余手数', sortable : false, width : 50},					
				//{field : 'reason', title : '交易类型', sortable : false, width : 50},	 			 			
	 			{field : 'symbol', title : '交易货币', sortable : false, width : 100},	 
	 			{field : 'stoploss', title : '止损设置', sortable : false, width : 50},
	 			{field : 'takeprofit', title : '止盈设置', sortable : false, width : 50},
	 			{field : 'commission', title : '佣金', sortable : false, width : 50},
	 			{field : 'swap', title : '过夜利息', sortable : false, width : 50,
	 				styler:function(value, rowData, rowIndex){
						var swap =  rowData.swap;
						if(!common.isBlank(swap) && swap > 0){
							return 'color:red;';					
						}else if(!common.isBlank(swap) && swap < 0){
							return 'color:green;';
						}
				}},					
	 			{field : 'platform', title : '平台', sortable : false, width : 100}
 			] ];
		$('#' + tradeDetailOpen.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'opentime',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			},
            onLoadSuccess : function(data) {
				
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + tradeDetailOpen.dataGridId).datagrid('options').queryParams;
		queryParams['accountno'] = $("#accountnoSearch").val();
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		if(!common.submitFormValidate(tradeDetailOpen.searchFormId)){
			return false;
		}
		tradeDetailOpen.getQueryParams();
		common.loadGrid(tradeDetailOpen.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + tradeDetailOpen.searchFormId).form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD000000,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD235959,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		var queryParams = tradeDetailOpen.getQueryParams();
		var url = BASE_PATH + "tradeAccountDiagnosisController/exportExcelTradeDetailOpen?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

