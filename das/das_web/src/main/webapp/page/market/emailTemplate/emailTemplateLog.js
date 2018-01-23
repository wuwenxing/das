$(function() {
	emailTemplateLog.init();
});

var emailTemplateLog = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		emailTemplateLog.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'EmailTemplateLogController/pageList';
		var queryParams = {};
		queryParams['templateId'] = $("#templateIdSearch").val();
		queryParams['type'] = $("#type").val();
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
	 			{field : 'templateId', title : '模板ID', sortable : true, width : 50},
	 			{field : 'param', title : '请求参数', sortable : true, width : 300},
	 			{field : 'statusCode', title : '请求状态', sortable : true, width : 50},
	 			{field : 'inputNum', title : '邮箱总数', sortable : true, width : 50},
	 			{field : 'legalNum', title : '合法邮箱数', sortable : true, width : 50},
	 			{field : 'illegalNum', title : '非法邮箱数', sortable : true, width : 50},
	 			{field : 'updateDate', title : '请求时间', sortable : true, width : 60}
 			] ];
		$('#' + emailTemplateLog.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'logId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + emailTemplateLog.dataGridId).datagrid('options').queryParams;
		queryParams['templateId'] = $("#templateIdSearch").val();
		queryParams['statusCode'] = $("#statusCodeSearch").combobox('getValue');
		queryParams['type'] = $("#type").val();
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		emailTemplateLog.getQueryParams();
		common.loadGrid(emailTemplateLog.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + emailTemplateLog.searchFormId).form('reset');
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




















