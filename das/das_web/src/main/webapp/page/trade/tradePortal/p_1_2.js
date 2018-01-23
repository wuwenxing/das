$(function() {

});

/**
 * 十大亏损
 */
var p_1_2 = {
	dataGridId : "dataGrid_1_2",
	loadDataGridFlag : false,
	/**
	 * 初始化
	 */
	init : function() {
		if (p_1_2.loadDataGridFlag) {
			p_1_2.find();
		} else {
			p_1_2.loadDataGridFlag = true;
			p_1_2.loadDataGrid();
		}
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function() {
		// 初始参数
		var url = BASE_PATH + 'TradeController/top10WinnerLoserYearsList';
		var queryParams = {};
		queryParams['type'] = "1";
		queryParams['dateTime'] = $("#dateTimeSearch").val();
		var columns = [ [
		    {field : 'rowKey', hidden : true, checkbox : false},
 			{field : 'datetime', title : '日期', sortable : true, width : 100},
 			{field : 'accountNo', title : '账号', sortable : true, width : 100},
 			{field : 'chineseName', title : '姓名', sortable : true, width : 100},
 			{field : 'cleanProfit', title : '当天净盈亏(盈亏+回佣+利息)', sortable : true, width : 150, 
 				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
			}},
 			{field : 'accountCleanProfit', title : '开户总净盈亏', sortable : true, width : 100, 
 				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
			}}
		] ];
		$('#' + p_1_2.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'cleanProfit',
			sortOrder : 'asc',
			border : false,
			pagination : false, // 分页控件显示
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				common.iFrameHeight();
			}
		});
	},
	getQueryParams : function() {
		var options = $("#" + p_1_2.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['dateTime'] = $("#dateTimeSearch").val();
		queryParams['platformType'] = $("#platformTypeSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find : function() {
		p_1_2.getQueryParams();
		common.loadGrid(p_1_2.dataGridId);
	}
}