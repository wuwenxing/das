$(function() {

});

/**
 * 交易指标
 */
var p_8_1 = {
	dataGridId : "dataGrid_8_1",
	loadDataGridFlag : false,
	/**
	 * 初始化
	 */
	init : function() {
		if (p_8_1.loadDataGridFlag) {
			p_8_1.find();
		} else {
			p_8_1.loadDataGridFlag = true;
			p_8_1.loadDataGrid();
		}
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function() {
		// 初始参数
		var url = BASE_PATH + 'TradeIndexController/list';
		var queryParams = {};
		queryParams['dateTime'] = $("#dateTimeSearch").val();
		var columns = [ [
		    {field : 'dateTime', title : '日期', sortable : true, width : 80},
	        {field : 'goldHighPrice', title : '最高金价', sortable : true, width : 70,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
 			{field : 'goldLowPrice', title : '最低金价', sortable : true, width : 70,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
 			{field : 'goldAmp', title : '金价波幅', sortable : true, width : 70,
 				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
 				}},
 			{field : 'goldUpAndDown', title : '金价升跌', sortable : true, width : 70,
 				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
 				}},
 			{field : 'silverHighPrice', title : '最高银价', sortable : true, width : 70,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
 			{field : 'silverLowPrice', title : '最低银价', sortable : true, width : 70,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
 			{field : 'silverAmp', title : '银价波幅', sortable : true, width : 70,
 				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
 				}},
 			{field : 'silverUpAndDown', title : '银价升跌', sortable : true, width : 70,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}}
		] ];
		$('#' + p_8_1.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'tradeIndexId', // 唯一字段
			sortName : 'dateTime',
			sortOrder : 'desc',
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
		var options = $("#" + p_8_1.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['dateTime'] = $("#dateTimeSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find : function() {
		p_8_1.getQueryParams();
		common.loadGrid(p_8_1.dataGridId);
	}
}