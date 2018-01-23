$(function() {
	dasFlowDetailUrl.init();
});

var dasFlowDetailUrl = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasFlowDetailUrl.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/flowDetailUrlPageList';
		var queryParams = {
		};
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['flowDetailId'] = $("#flowDetailIdSearch").val();// 访问ID
		queryParams['flowDetailUrl'] = $("#flowDetailUrlSearch").val();// 访问URL
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['devicetype'] = $("#devicetypeSearch").datebox('getValue');
		var columns = [ [
			    {field : 'visitTime', title : '访问时间', sortable : false, width : 100},
			    {field : 'flowDetailUrl', title : '访问URL', sortable : false, width : 100}
 			] ];
		$('#' + dasFlowDetailUrl.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'visitTime',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + dasFlowDetailUrl.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['flowDetailId'] = $("#flowDetailIdSearch").val();// 访问ID
		queryParams['flowDetailUrl'] = $("#flowDetailUrlSearch").val();// 访问URL
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['devicetype'] = $("#devicetypeSearch").datebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasFlowDetailUrl.getQueryParams();
		common.loadGrid(dasFlowDetailUrl.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasFlowDetailUrl.searchFormId).form('reset');
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = dasFlowDetailUrl.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/exportExcelFlowDetailUrl?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















