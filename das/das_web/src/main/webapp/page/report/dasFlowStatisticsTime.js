$(function() {
	dasFlowStatisticsTime.init();
});

var dasFlowStatisticsTime = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasFlowStatisticsTime.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasFlowStatisticsController/pageListTime';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
			    {field : 'formatTime', title : '日期', sortable : true, width : 50},
	 			{field : 'utmcsr', title : '来源', sortable : true, width : 50},
	 			{field : 'utmcmd', title : '媒介', sortable : true, width : 50},
	 			{field : 'visitCount', title : '访问次数', sortable : true, width : 50},
	 			{field : 'advisoryCountQQ', title : 'QQ咨询', sortable : true, width : 50},
	 			{field : 'advisoryCountLIVE800', title : 'LIVE800咨询', sortable : true, width : 50},
	 			{field : 'demoCount', title : '模拟开户数', sortable : true, width : 50},
	 			{field : 'realCount', title : '真实开户数', sortable : true, width : 50},
	 			{field : 'depositCount', title : '首次入金数', sortable : true, width : 50},
	 			{field : 'platformType', title : '访问客户端', sortable : false, width : 70, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.platformType == '0'){
							return "PC端";
						}else if(rowData.platformType == '1'){
							return "移动端";
						}else{
							// 统计时，为空代表所有客户端
							if(!common.isBlank(rowData.formatTime)){
								return "(全部)";
							}else{
								// 小计行的该列显示为空
								return "";
							}
						}
	 				}
				}
 			] ];
		$('#' + dasFlowStatisticsTime.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'formatTime',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='formatTime']").text("小计：");
				dasFlowStatisticsTimeChart.loadChartData(1);
				dasFlowStatisticsTimeChart.loadChartData(2);
				dasFlowStatisticsTimeChart.loadChartData(3);
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + dasFlowStatisticsTime.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasFlowStatisticsTime.getQueryParams();
		common.loadGrid(dasFlowStatisticsTime.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasFlowStatisticsTime.searchFormId).form('reset');
		$('#startTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDDHH
		});
		$('#endTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDDHH
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
		var queryParams = dasFlowStatisticsTime.getQueryParams();
		var url = BASE_PATH + "DasFlowStatisticsController/exportExcelTime?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}














