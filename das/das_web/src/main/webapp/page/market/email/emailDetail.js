$(function() {
	emailDetail.init();
});

var emailDetail = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		emailDetail.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'EmailDetailController/pageList';
		var queryParams = {};
		queryParams['emailId'] = $("#emailIdSearch").val();
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
	 			{field : 'emailIdFiled', title : '邮件ID', sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					return rowData.emailId;
				}},
	 			{field : 'sendEmail', title : '发送邮箱', sortable : true, width : 100},
	 			{field : 'recEmail', title : '接收邮箱', sortable : true, width : 100},
	 			{field : 'commitStatus', title : '邮件提交状态', sortable : true, width : 100},
	 			{field : 'sendStatus', title : '邮件发送状态', sortable : true, width : 100},
	 			{field : 'updateDate', title : '发送时间', sortable : true, width : 100}
 			] ];
		$('#' + emailDetail.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var queryParams = $("#" + emailDetail.dataGridId).datagrid('options').queryParams;
		queryParams['emailId'] = $("#emailIdSearch").val();
		queryParams['recEmail'] = $("#recEmailSearch").val();
		queryParams['commitStatus'] = $("#commitStatusSearch").combobox('getValue');
		queryParams['sendStatus'] = $("#sendStatusSearch").combobox('getValue');
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		emailDetail.getQueryParams();
		common.loadGrid(emailDetail.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + emailDetail.searchFormId).form('reset');
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




















