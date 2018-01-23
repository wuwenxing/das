$(function() {
	flowLog.init();
});

var flowLog = {
	dataGridId: "dataGrid",
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
		flowLog.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'FlowLogController/pageList';
		var queryParams = {};
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
 	 			{field : 'oper', title : '操作', sortable : false, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.flowLogId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'flowLogId', title : '流量充值ID', sortable : true, width : 50},
	 			{field : 'param', title : '请求参数', sortable : true, width : 300},
	 			{field : 'statusCode', title : '请求状态', sortable : true, width : 50},
	 			{field : 'inputNum', title : '手机号总数', sortable : true, width : 50},
	 			{field : 'legalNum', title : '合法手机号数', sortable : true, width : 50},
	 			{field : 'illegalNum', title : '非法手机号数', sortable : true, width : 50},
	 			{field : 'updateDate', title : '请求时间', sortable : true, width : 60}
 			] ];
		$('#' + flowLog.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var queryParams = $("#" + flowLog.dataGridId).datagrid('options').queryParams;
		queryParams['statusCode'] = $("#statusCodeSearch").combobox('getValue');
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		flowLog.getQueryParams();
		common.loadGrid(flowLog.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + flowLog.searchFormId).form('reset');
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
	 * 流量充值详情
	 */
	viewDetail : function(id){
		var url = "FlowLogDetailController/page?flowLogId=" + id;
		parent.index.addOrUpdateTabs("流量充值详情", url);
	},
	/**
	 * 流量充值提交窗口
	 */
	addDlog: function() {
		$("#" + flowLog.addDlogOkId).css("display", "");
		$("#" + flowLog.addDlogFormId).form('reset');
		$("#" + flowLog.addDlogId).dialog('open').dialog('setTitle', '流量充值');
	},
	/**
	 * 流量充值提交
	 */
	save: function(){
		if(!common.submitFormValidate(flowLog.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "FlowLogController/save",
			data : {
				phones : $("#phones").val(), 
				flowSize : $("#flowPackage").combobox('getValue')
			},
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + flowLog.addDlogId).dialog('close');
					common.reloadGrid(flowLog.dataGridId);
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + flowLog.addDlogId).dialog('close');
	},
	/**
	 * 文件上传成功回调此函数
	 */
	callback: function(phones){
		$.messager.progress('close');
		$("#phones").val(phones);
	}
	
}




















