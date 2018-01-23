$(function() {
	threadLog.init();
});

var threadLog = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		threadLog.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'SystemThreadLogController/pageList';
		var queryParams = {};
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
			    {field : 'code', title : '线程名称', sortable : true, width : 50},
	 			{field : 'status', title : '执行状态', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.status == 'Y'){
							return "成功";
						}else{
							return "失败";
						}
				}},
	 			{field : 'startExecuteTime', title : '执行开始时间', sortable : true, width : 100},
	 			{field : 'endExecuteTime', title : '执行完毕时间', sortable : true, width : 100},
	 			{field : 'remark', title : '备注', sortable : true, width : 100}
 			] ];
		$('#' + threadLog.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'logId', // 唯一字段
			sortName : 'logId',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + threadLog.dataGridId).datagrid('options').queryParams;
		queryParams['code'] = $("#codeSearch").combobox('getValue');
		queryParams['status'] = $("#statusSearch").combobox('getValue');
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		threadLog.getQueryParams();
		common.loadGrid(threadLog.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + threadLog.searchFormId).form('reset');
		$('#startDateSearch').datebox({
			formatter:easyui.formatterYYYYMMDD000000,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endDateSearch').datebox({
			formatter:easyui.formatterYYYYMMDD235959,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	}
	
}




















