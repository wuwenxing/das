$(function() {

});

/**
 * 交易统计-持仓时间
 */
var p_1_7 = {
	dataGridId : "dataGrid_1_7",
	loadDataGridFlag : false,
	/**
	 * 初始化
	 */
	init : function() {
		if (p_1_7.loadDataGridFlag) {
			p_1_7.find();
		} else {
			p_1_7.loadDataGridFlag = true;
			p_1_7.loadDataGrid();
		}
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function() {
		// 初始参数
		var url = BASE_PATH + 'TradeController/top10PositionStatisticsYearsList';
		var queryParams = {};
		queryParams['platformType'] = "";
		queryParams['dateTime'] = $("#dateTimeSearch").val();
		var columns = [ [
		    {field : 'rowKey', hidden : true, checkbox : false},
 			{field : 'datetime', title : '日期', sortable : true, width : 100},
 			{field : 'timeRange', title : '时间', sortable : true, width : 100},
 			{field : 'dealamount', title : '次数', sortable : true, width : 100},
 			{field : 'percent', title : '百分比', sortable : true, width : 100, 
 				formatter : function(value, rowData, rowIndex) {
 					return value + "%";
			}},
 			{field : 'cleanProfit', title : '当天净盈亏(盈亏+回佣+利息)', sortable : true, width : 150, 
 				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
			}}
		] ];
		$('#' + p_1_7.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'timeRange',
			sortOrder : 'asc',
			border : false,
			showFooter : true,
			pagination : false, // 分页控件显示
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='datetime']").text("总计：");
				common.iFrameHeight();
			}
		});
	},
	getQueryParams : function() {
		var options = $("#" + p_1_7.dataGridId).datagrid('options');
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
		p_1_7.getQueryParams();
		common.loadGrid(p_1_7.dataGridId);
	}
}