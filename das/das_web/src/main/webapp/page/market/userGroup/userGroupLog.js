$(function() {
	userGroupLog.init();
});

var userGroupLog = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		userGroupLog.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'UserGroupLogController/pageList';
		var queryParams = {};
		queryParams['groupId'] = $("#groupIdSearch").val();
		queryParams['type'] = $("#type").val();
		var columns = [ [
	 			{field : 'groupId', title : '分组ID', sortable : true, width : 50},
	 			{field : 'param', title : '请求参数', sortable : true, width : 300},
	 			{field : 'statusCode', title : '请求状态', sortable : true, width : 50},
	 			{field : 'inputPhoneNum', title : '手机总数', sortable : true, width : 50},
	 			{field : 'legalPhoneNum', title : '合法手机数', sortable : true, width : 50},
	 			{field : 'illegalPhoneNum', title : '非法手机数', sortable : true, width : 50},
	 			{field : 'inputEmailNum', title : '邮箱总数', sortable : true, width : 50},
	 			{field : 'legalEmailNum', title : '合法邮箱数', sortable : true, width : 50},
	 			{field : 'illegalEmailNum', title : '非法邮箱数', sortable : true, width : 50},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 60}
 			] ];
		$('#' + userGroupLog.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var queryParams = $("#" + userGroupLog.dataGridId).datagrid('options').queryParams;
		queryParams['groupId'] = $("#groupIdSearch").val();
		queryParams['statusCode'] = $("#statusCodeSearch").combobox('getValue');
		queryParams['type'] = $("#type").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		userGroupLog.getQueryParams();
		common.loadGrid(userGroupLog.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + userGroupLog.searchFormId).form('reset');
	}
	
}




















