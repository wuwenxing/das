$(function() {
	emailTemplateDetail.init();
});

var emailTemplateDetail = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		emailTemplateDetail.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'EmailTemplateDetailController/pageList';
		var queryParams = {};
		queryParams['templateId'] = $("#templateIdSearch").val();
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
 	 			{field : 'oper', title : '操作', sortable : false, width : 50, 
 	 				formatter : function(value, rowData, rowIndex) {
 						$("#rowOperation a").each(function(){
 							$(this).attr("id", rowData.detailId);
 					    });
 						return $("#rowOperation").html();
 				}},
	 			{field : 'templateId', title : '模板ID', sortable : true, width : 50},
	 			{field : 'sendEmail', title : '发送邮箱', sortable : true, width : 100},
	 			{field : 'recEmail', title : '接收邮箱', sortable : true, width : 100},
	 			{field : 'title', title : '邮件标题', sortable : true, width : 100},
	 			{field : 'commitStatus', title : '邮件提交状态', sortable : true, width : 100},
	 			{field : 'sendStatus', title : '邮件发送状态', sortable : true, width : 100},
	 			{field : 'updateDate', title : '发送时间', sortable : true, width : 100}
 			] ];
		$('#' + emailTemplateDetail.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var queryParams = $("#" + emailTemplateDetail.dataGridId).datagrid('options').queryParams;
		queryParams['templateId'] = $("#templateIdSearch").val();
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
		emailTemplateDetail.getQueryParams();
		common.loadGrid(emailTemplateDetail.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + emailTemplateDetail.searchFormId).form('reset');
		$('#startDateSearch').datebox({
			formatter:easyui.formatterYYYYMMDD000000,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endDateSearch').datebox({
			formatter:easyui.formatterYYYYMMDD235959,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	},
	/**
	 * 邮件预览
	 */
	preview : function(id){
		var url = BASE_PATH + "EmailTemplateDetailController/preview/" + id;
		window.open(url);
	}
	
}




















