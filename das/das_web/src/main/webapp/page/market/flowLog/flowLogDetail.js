$(function() {
	flowLogDetail.init();
});

var flowLogDetail = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		flowLogDetail.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'FlowLogDetailController/pageList';
		var queryParams = {};
		queryParams['flowLogId'] = $("#flowLogIdSearch").val();
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
	 			{field : 'flowLogId', title : '流量充值ID', sortable : true, width : 100},
	 			{field : 'flowSize', title : '充值大小', sortable : true, width : 100},
	 			{field : 'interfaceType', title : '流量通道', sortable : true, width : 100},
	 			{field : 'resBatchNo', title : '通道返回批次号', sortable : true, width : 100},
	 			{field : 'resCode', title : '通道返回状态码', sortable : true, width : 100},
	 			{field : 'phone', title : '充值手机号', sortable : true, width : 100},
	 			{field : 'commitStatus', title : '提交状态', sortable : true, width : 100},
	 			{field : 'sendStatus', title : '充值状态', sortable : true, width : 100},
	 			{field : 'updateDate', title : '充值时间', sortable : true, width : 100}
 			] ];
		$('#' + flowLogDetail.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var queryParams = $("#" + flowLogDetail.dataGridId).datagrid('options').queryParams;
		queryParams['flowLogId'] = $("#flowLogIdSearch").val();
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
		flowLogDetail.getQueryParams();
		common.loadGrid(flowLogDetail.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + flowLogDetail.searchFormId).form('reset');
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




















