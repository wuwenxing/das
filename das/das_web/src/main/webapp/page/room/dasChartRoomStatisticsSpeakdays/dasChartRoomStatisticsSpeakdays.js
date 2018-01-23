$(function() {
	dasChartRoomStatisticsSpeakdays.init();
});

var dasChartRoomStatisticsSpeakdays = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartRoomStatisticsSpeakdays.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findStatisticsSpeakdaysPage';
		var queryParams = {
		};
		queryParams['courseNameChecked'] = $("#courseNameChecked").val();// 复选框
		queryParams['teacherNameChecked'] = $("#teacherNameChecked").val();// 复选框
		queryParams['roomNameChecked'] = $("#roomNameChecked").val();// 复选框
		queryParams['speakType'] = $("#speakTypeSearch").combobox('getValue');// 发言类型（公聊或私聊）
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
		$('#' + dasChartRoomStatisticsSpeakdays.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
					$("#" + dasChartRoomStatisticsSpeakdays.dataGridId).datagrid('showColumn', 'courseName');
				}else{
					$("#" + dasChartRoomStatisticsSpeakdays.dataGridId).datagrid('hideColumn', 'courseName');
				}
				if($("#teacherNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsSpeakdays.dataGridId).datagrid('showColumn', 'teacherName');
				}else{
					$("#" + dasChartRoomStatisticsSpeakdays.dataGridId).datagrid('hideColumn', 'teacherName');
				}
				if($("#roomNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsSpeakdays.dataGridId).datagrid('showColumn', 'roomName');
				}else{
					$("#" + dasChartRoomStatisticsSpeakdays.dataGridId).datagrid('hideColumn', 'roomName');
				}
				dasChartRoomStatisticsSpeakdaysChart.loadChartData(2);
				dasChartRoomStatisticsSpeakdaysChart.loadChartData(1);
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dasChartRoomStatisticsSpeakdays.dataGridId).datagrid('options').queryParams;
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		queryParams['speakType'] = $("#speakTypeSearch").combobox('getValue');// 发言类型（公聊或私聊）
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
		dasChartRoomStatisticsSpeakdays.getQueryParams();
		common.loadGrid(dasChartRoomStatisticsSpeakdays.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomStatisticsSpeakdays.searchFormId).form('reset');

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
		var queryParams = dasChartRoomStatisticsSpeakdays.getQueryParams();
		queryParams['speakTypeStatistics'] = 1;
		var url = BASE_PATH + "DasChartRoomController/exportExcelStatisticsSpeak?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

