$(function() {
	userGroupDetail.init();
});

var userGroupDetail = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		userGroupDetail.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'UserGroupDetailController/pageList';
		var queryParams = {};
		queryParams['groupId'] = $("#groupIdSearch").val();
		var columns = [ [
	 			{field : 'groupId', title : '分组ID', sortable : true, width : 50},
	 			{field : 'account', title : '账号', sortable : true, width : 100},
	 			{field : 'accountType', title : '账号类型', sortable : true, width : 100},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 100}
 			] ];
		$('#' + userGroupDetail.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'detailId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + userGroupDetail.dataGridId).datagrid('options').queryParams;
		queryParams['groupId'] = $("#groupIdSearch").val();
		queryParams['account'] = $("#accountSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		userGroupDetail.getQueryParams();
		common.loadGrid(userGroupDetail.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + userGroupDetail.searchFormId).form('reset');
	}
	
}




















