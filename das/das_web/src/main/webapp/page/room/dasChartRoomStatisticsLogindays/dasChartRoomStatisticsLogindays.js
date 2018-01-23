$(function() {
	dasChartRoomStatisticsLogindays.init();
});

var dasChartRoomStatisticsLogindays = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartRoomStatisticsLogindays.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findStatisticsLogindaysPage';
		var queryParams = {
		};
		queryParams['courseNameChecked'] = $("#courseNameChecked").val();// 复选框
		queryParams['teacherNameChecked'] = $("#teacherNameChecked").val();// 复选框
		queryParams['roomNameChecked'] = $("#roomNameChecked").val();// 复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
 		 	 	{field : 'dateTime', title : '日期', sortable : true, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var dateTime = rowData.dateTime;
	 					if(!common.isBlank(dateTime)){
		 					return dateTime.substring(0, 7);
 						}else{
 							return "";
 						}
				}},
 		 	 	{field : 'courseName', title : '课程名称', sortable : true, width : 100},
 		 	 	{field : 'teacherName', title : '老师名称', sortable : true, width : 100},
 		 	 	{field : 'roomName', title : '房间名称', sortable : true, width : 100},
		 	 	{field : 'u0', title : '0天', sortable : true, width : 50},
		 	 	{field : 'u1', title : '1天', sortable : true, width : 50},
		 	 	{field : 'u2', title : '1-5天', sortable : true, width : 100},
	 			{field : 'u3', title : '5-10天', sortable : true, width : 100},
	 			{field : 'u4', title : '10-20天', sortable : true, width : 100},
	 			{field : 'u5', title : '20-30天', sortable : true, width : 100}
 			] ];
		$('#' + dasChartRoomStatisticsLogindays.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
					$("#" + dasChartRoomStatisticsLogindays.dataGridId).datagrid('showColumn', 'courseName');
				}else{
					$("#" + dasChartRoomStatisticsLogindays.dataGridId).datagrid('hideColumn', 'courseName');
				}
				if($("#teacherNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsLogindays.dataGridId).datagrid('showColumn', 'teacherName');
				}else{
					$("#" + dasChartRoomStatisticsLogindays.dataGridId).datagrid('hideColumn', 'teacherName');
				}
				if($("#roomNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsLogindays.dataGridId).datagrid('showColumn', 'roomName');
				}else{
					$("#" + dasChartRoomStatisticsLogindays.dataGridId).datagrid('hideColumn', 'roomName');
				}
				dasChartRoomStatisticsLogindaysChart.loadChartData(2);
				dasChartRoomStatisticsLogindaysChart.loadChartData(1);
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dasChartRoomStatisticsLogindays.dataGridId).datagrid('options').queryParams;
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
		dasChartRoomStatisticsLogindays.getQueryParams();
		common.loadGrid(dasChartRoomStatisticsLogindays.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomStatisticsLogindays.searchFormId).form('reset');
		
		$("#courseNameChecked").val("false");
		$("#teacherNameChecked").val("false");
		$("#roomNameChecked").val("false");
		
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMM,
			parser:easyui.parserYYYYMM
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMM,
			parser:easyui.parserYYYYMM
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
		var queryParams = dasChartRoomStatisticsLogindays.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/exportExcelStatisticsLogindays?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

