$(function() {
	uploadLog.init();
});

var uploadLog = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		uploadLog.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'UploadController/pageList';
		var queryParams = {};
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
			    {field : 'fileType', title : '文件类型', sortable : true, width : 50},
	 			{field : 'fileName', title : '文件名称', sortable : true, width : 100},
	 			{field : 'fileUrl', title : '文件访问地址', sortable : true, width : 200},
	 			{field : 'filePath', title : '文件路径', sortable : true, width : 200},
	 			{field : 'updateUser', title : '上传人', sortable : true, width : 80},
	 			{field : 'updateIp', title : '上传IP', sortable : true, width : 80},
	 			{field : 'updateDate', title : '上传时间', sortable : true, width : 100}
 			] ];
		$('#' + uploadLog.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'uploadLogId', // 唯一字段
			sortName : 'uploadLogId',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + uploadLog.dataGridId).datagrid('options').queryParams;
		queryParams['fileName'] = $("#fileNameSearch").val();
		queryParams['fileType'] = $("#fileTypeSearch").combobox('getValue');
		queryParams['fileUrl'] = $("#fileUrlSearch").val();
		queryParams['filePath'] = $("#filePathSearch").val();
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		uploadLog.getQueryParams();
		common.loadGrid(uploadLog.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + uploadLog.searchFormId).form('reset');
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
	 * 显示上传的图片
	 */
	showImg: function(url){
		if(url != ''){
			window.open(url);
		}else{
			$.messager.alert('提示', "图片Url为空,无法查看", 'info');
		}
	}
	
}




















