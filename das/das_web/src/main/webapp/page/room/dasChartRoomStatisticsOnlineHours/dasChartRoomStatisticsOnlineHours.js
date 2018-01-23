$(function() {
	dasChartRoomStatisticsOnlineHours.init();
});

var dasChartRoomStatisticsOnlineHours = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartRoomStatisticsOnlineHours.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findStatisticsOnlineHoursPage';
		var queryParams = {
		};
		queryParams['courseNameChecked'] = $("#courseNameChecked").val();// 复选框
		queryParams['teacherNameChecked'] = $("#teacherNameChecked").val();// 复选框
		queryParams['roomNameChecked'] = $("#roomNameChecked").val();// 复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
 		 	 	{field : 'dateTime', title : '数据日期', sortable : true, width : 100},
 		 	 	{field : 'courseName', title : '课程名称', sortable : true, width : 100},
 		 	 	{field : 'teacherName', title : '老师名称', sortable : true, width : 100},
 		 	 	{field : 'roomName', title : '房间名称', sortable : true, width : 100},
		 	 	{field : 'seconds1', title : '1分钟以内', sortable : true, width : 100},
		 	 	{field : 'seconds2', title : '1-5分钟', sortable : true, width : 100},
	 			{field : 'seconds3', title : '5-30分钟', sortable : true, width : 100},
	 			{field : 'seconds4', title : '30-60分钟', sortable : true, width : 100},
	 			{field : 'seconds5', title : '1-2小时', sortable : true, width : 100},
	 			{field : 'seconds6', title : '2小时以上', sortable : true, width : 100}
 			] ];
		$('#' + dasChartRoomStatisticsOnlineHours.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'dateTime',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data){
				$("div.datagrid-footer [class$='dateTime']").text("小计：");
				if($("#courseNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsOnlineHours.dataGridId).datagrid('showColumn', 'courseName');
				}else{
					$("#" + dasChartRoomStatisticsOnlineHours.dataGridId).datagrid('hideColumn', 'courseName');
				}
				if($("#teacherNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsOnlineHours.dataGridId).datagrid('showColumn', 'teacherName');
				}else{
					$("#" + dasChartRoomStatisticsOnlineHours.dataGridId).datagrid('hideColumn', 'teacherName');
				}
				if($("#roomNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsOnlineHours.dataGridId).datagrid('showColumn', 'roomName');
				}else{
					$("#" + dasChartRoomStatisticsOnlineHours.dataGridId).datagrid('hideColumn', 'roomName');
				}
				dasChartRoomStatisticsOnlineHoursChart.loadChartData(2);
				dasChartRoomStatisticsOnlineHoursChart.loadChartData(1);
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dasChartRoomStatisticsOnlineHours.dataGridId).datagrid('options').queryParams;
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		queryParams['userSource'] = $("#userSourceSearch").val();
		queryParams['courseName'] = $("#courseNameSearch").val();
		queryParams['courseNameChecked'] = $("#courseNameChecked").val();// 复选框
		queryParams['teacherName'] = $("#teacherNameSearch").val();
		queryParams['teacherNameChecked'] = $("#teacherNameChecked").val();// 复选框
		queryParams['roomName'] = $("#roomNameSearch").val();
		queryParams['roomNameChecked'] = $("#roomNameChecked").val();// 复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasChartRoomStatisticsOnlineHours.getQueryParams();
		common.loadGrid(dasChartRoomStatisticsOnlineHours.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomStatisticsOnlineHours.searchFormId).form('reset');
		
		$("#courseNameChecked").val("false");
		$("#teacherNameChecked").val("false");
		$("#roomNameChecked").val("false");
		
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
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
		var queryParams = dasChartRoomStatisticsOnlineHours.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/exportStatisticsOnlineHours?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

