/**
 * 净入金图表
 */
$(function() {
	accountbalance.init();
});

var accountbalance = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		accountbalance.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeAccountDiagnosisController/findAccountbalancePageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
		                {field : 'datetime', title : '时间', sortable : false, width : 100},
		                {field : 'accountno', title : '账户', sortable : false, width : 100},
		 	 			{field : 'orderid', title : '订单号', sortable : false, width : 100},
		 	 			{field : 'reason', title : '交易类型', sortable : false, width : 100,
		   					formatter : function(value, rowData, rowIndex) {
		   						if(rowData.reason == 1){
		   							return "市价建仓";
		   						}else if(rowData.reason == 2){
		   							return "限价建仓";
		   						}else if(rowData.reason == 4){
		   							return "停损建仓";
		   						}else if(rowData.reason == 8){
		   							return "市场平仓";
		   						}else if(rowData.reason == 16){
		   							return "止损";
		   						}else if(rowData.reason == 32){
		   							return "止盈";
		   						}else if(rowData.reason == 64){
		   							return "强平";
		   						}else if(rowData.reason == 81){
		   							return "结余";
		   						}else if(rowData.reason == 128){
		   							return "部分平仓";
		   						}else if(rowData.reason == 129){
		   							return "额度";
		   						}else if(rowData.reason == 130){
		   							return "信用额度";
		   						}else if(rowData.reason == 131){
		   							return "charge";
		   						}else if(rowData.reason == 132){
		   							return "系统清零";
		   						}else if(rowData.reason == 134){
		   							return "赠金";
		   						}else if(rowData.reason == 135){
		   							return "手续费";
		   						}else if(rowData.reason == 136){
		   							return "调整清零";
		   						}else if(rowData.reason == 160){
		   							return "到期";
		   						}else if(rowData.reason == 161){
		   							return "Admin平仓";
		   						}else if(rowData.reason == 162){
		   							return "结算平仓";
		   						}else if(rowData.reason == 0){
		   							return null;
		   						}else{
		   							return rowData.reason;
		   						}
		   	 			}},
		 	 			{field : 'amount', title : '金额', sortable : false, width : 100,
		 	 				styler:function(value, rowData, rowIndex){
		 						var amount =  rowData.amount;
		 						if(!common.isBlank(amount) && amount > 0){
		 							return 'color:red;';					
		 						}else if(!common.isBlank(amount) && amount < 0){
		 							return 'color:green;';
		 						}
		 				}},
		 	 			{field : 'platform', title : '平台', sortable : false, width : 100}
		  			] ];
		$('#' + accountbalance.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'datetime',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + accountbalance.dataGridId).datagrid('options').queryParams;
		queryParams['accountno'] = $("#accountnoSearch").val();
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		if(!common.submitFormValidate(accountbalance.searchFormId)){
			return false;
		}
		accountbalance.getQueryParams();
		common.loadGrid(accountbalance.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + accountbalance.searchFormId).form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		var queryParams = accountbalance.getQueryParams();
		queryParams['sort'] = "datetime";
		queryParams['order'] = "desc";
		var url = BASE_PATH + "tradeAccountDiagnosisController/exportExcelAccountbalance?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

