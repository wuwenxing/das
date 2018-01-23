$(function() {
	smsTemplateDetail.init();
});

var smsTemplateDetail = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		smsTemplateDetail.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'SmsTemplateDetailController/pageList';
		var queryParams = {};
		queryParams['templateId'] = $("#templateIdSearch").val();
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
	 			{field : 'templateId', title : '模板ID', sortable : true, width : 50},
	 			{field : 'content', title : '短信内容', sortable : true, width : 300},
	 			{field : 'interfaceType', title : '短信通道', sortable : true, width : 100},
	 			{field : 'smsid', title : '通道返回smsid', sortable : true, width : 100},
	 			{field : 'resCode', title : '通道返回状态码', sortable : true, width : 100},
	 			{field : 'phone', title : '接收手机号', sortable : true, width : 100},
	 			{field : 'commitStatus', title : '短信提交状态', sortable : true, width : 100},
	 			{field : 'sendStatus', title : '短信发送状态', sortable : true, width : 100},
	 			{field : 'updateDate', title : '发送时间', sortable : true, width : 100}
 			] ];
		$('#' + smsTemplateDetail.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var queryParams = $("#" + smsTemplateDetail.dataGridId).datagrid('options').queryParams;
		queryParams['templateId'] = $("#templateIdSearch").val();
		queryParams['phone'] = $("#phoneSearch").val();
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
		smsTemplateDetail.getQueryParams();
		common.loadGrid(smsTemplateDetail.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + smsTemplateDetail.searchFormId).form('reset');
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




















