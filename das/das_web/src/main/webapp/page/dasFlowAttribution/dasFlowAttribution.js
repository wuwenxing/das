$(function() {
	dasFlowAttribution.init();
});

var dasFlowAttribution = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasFlowAttribution.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasFlowAttributionController/pageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
			    {field : 'dataTime', title : '日期', sortable : false, width : 50},
	 			{field : 'utmcsr', title : '来源', sortable : false, width : 50},
	 			{field : 'utmcmd', title : '媒介', sortable : false, width : 50},
	 			{field : 'demoCount', title : '模拟开户数', sortable : false, width : 50},
	 			{field : 'realCount', title : '真实开户数', sortable : false, width : 50},
	 			{field : 'platformType', title : '访问客户端', sortable : false, width : 70, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.platformType == '0'){
							return "PC端";
						}else if(rowData.platformType == '1'){
							return "移动端";
						}else{
							// 统计时，为空代表所有客户端
							return "(全部)";
						}
	 				}
				}
 			] ];
		$('#' + dasFlowAttribution.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'rowKey',
			sortOrder : 'desc',
			pageList : [ 20, 50, 100, 200, 500 ],
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + dasFlowAttribution.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasFlowAttribution.getQueryParams();
		common.loadGrid(dasFlowAttribution.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasFlowAttribution.searchFormId).form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYY,
			parser:easyui.parserYYYYMMDD
		});
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = dasFlowAttribution.getQueryParams();
		var url = BASE_PATH + "DasFlowAttributionController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















