$(function() {
	dasUserInfo.init();
});

var dasUserInfo = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	addDlogId: "addDlog",
	addDlogFormId: "addDlogForm",
	addDlogToolbarId: "addDlogToolbar",
	addDlogOkId: "addDlogOk",
	addDlogCancelId: "addDlogCancel",
	/**
	 * 初始化
	 */
	init:function(){
		dasUserInfo.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasUserInfoController/pageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		var columns = [ [
	 			{field : 'behaviorDetail', title : '行为账号', sortable : false, width : 100},
	 			{field : 'behaviorType', title : '行为类型', sortable : false, width : 100},
	 			{field : 'createDate', title : '行为时间', sortable : false, width : 100},
	 			{field : 'utmcsr', title : '来源', sortable : false, width : 100},
	 			{field : 'utmcmd', title : '媒介', sortable : false, width : 100},
	 			{field : 'guestName', title : '客户姓名', sortable : false, width : 100},
	 			{field : 'tel', title : '电话', sortable : false, width : 100},
	 			{field : 'email', title : '邮箱', sortable : false, width : 100},
	 			{field : 'platformType', title : '开户客户端', sortable : false, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.platformType == '0'){
							return "PC端";
						}else if(rowData.platformType == '1'){
							return "移动端";
						}else{
							return "";
						}
				}},
	 			{field : 'ipHomeJson', title : '归属地', sortable : false, width : 100},
	 			{field : 'ip', title : '操作IP', sortable : false, width : 100}
 			] ];
		$('#' + dasUserInfo.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'userId', // 唯一字段
			sortName : 'createDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				dasUserInfo.addDlog("2", rowData.userId);
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dasUserInfo.dataGridId).datagrid('options').queryParams;
		queryParams['behaviorDetail'] = $("#behaviorDetailSearch").val();
		queryParams['behaviorType'] = $("#behaviorTypeSearch").combobox('getValue');
		queryParams['utmcsr'] = $("#utmcsrSearch").val();
		queryParams['utmcmd'] = $("#utmcmdSearch").val();
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['isDemo'] = $("#isDemoSearch").val();
		queryParams['isReal'] = $("#isRealSearch").val();
		queryParams['isDepesit'] = $("#isDepesitSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasUserInfo.getQueryParams();
		common.loadGrid(dasUserInfo.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasUserInfo.searchFormId).form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD000000,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD235959,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	}
}

