$(function() {
	dasChartRoomStatisticsSpeakcounts.init();
});

var dasChartRoomStatisticsSpeakcounts = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartRoomStatisticsSpeakcounts.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findStatisticsSpeakcountsPage';
		var queryParams = {
		};
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
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
	 						var reportType = $("#reportTypeSearch").combobox('getValue');
		 					if(reportType == 'days'){
		 						return dateTime;
		 					}else{
		 						return dateTime.substring(0, 7);
		 					}
 						}else{
 							return "";
 						}
				}},
				{field : 'weeks', title : '周',sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var dateTime = rowData.dateTime
	 					return common.getWeekNumber(new Date(dateTime));
				}},
 		 	 	{field : 'courseName', title : '课程名称', sortable : true, width : 100},
 		 	 	{field : 'teacherName', title : '老师名称', sortable : true, width : 100},
 		 	 	{field : 'roomName', title : '房间名称', sortable : true, width : 100},
		 	 	{field : 'u1', title : '1次', sortable : true, width : 50},
		 	 	{field : 'u2', title : '1-5次', sortable : true, width : 100},
	 			{field : 'u3', title : '5-10次', sortable : true, width : 100},
	 			{field : 'u4', title : '10-20次', sortable : true, width : 100},
	 			{field : 'u5', title : '20-30次', sortable : true, width : 100},
	 			{field : 'u6', title : '30次以上', sortable : true, width : 100}
 			] ];
		$('#' + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
					$("#" + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('showColumn', 'courseName');
				}else{
					$("#" + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('hideColumn', 'courseName');
				}
				if($("#teacherNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('showColumn', 'teacherName');
				}else{
					$("#" + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('hideColumn', 'teacherName');
				}
				if($("#roomNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('showColumn', 'roomName');
				}else{
					$("#" + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('hideColumn', 'roomName');
				}
				
				var reportType = $("#reportTypeSearch").combobox('getValue');
				if(reportType == 'days' || reportType == 'months'){
					$("#" + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('hideColumn', 'weeks');
				}
				if(reportType == 'weeks'){
					$("#" + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('showColumn', 'weeks');
				}
				dasChartRoomStatisticsSpeakcountsChart.loadChartData(2);
				dasChartRoomStatisticsSpeakcountsChart.loadChartData(1);
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dasChartRoomStatisticsSpeakcounts.dataGridId).datagrid('options').queryParams;
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
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
		dasChartRoomStatisticsSpeakcounts.getQueryParams();
		common.loadGrid(dasChartRoomStatisticsSpeakcounts.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomStatisticsSpeakcounts.searchFormId).form('reset');
		
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
	 * 统计类型change事件
	 */
	setDateboxAttr: function(newValue,oldValue){
		if(newValue == 'days'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$("#startTimeSearch").datebox('setValue', $("#lessOneMonths").val());
			$("#endTimeSearch").datebox('setValue', $("#dayEnd").val());
		}else if(newValue == 'weeks'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$("#startTimeSearch").datebox('setValue', $("#lessThreeMonths").val());
			$("#endTimeSearch").datebox('setValue', $("#monthEnd").val());
		}else if(newValue == 'months'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$("#startTimeSearch").datebox('setValue', $("#lessOneYears").val());
			$("#endTimeSearch").datebox('setValue', $("#monthEnd").val());
		}
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = dasChartRoomStatisticsSpeakcounts.getQueryParams();
		queryParams['speakTypeStatistics'] = 2;
		var url = BASE_PATH + "DasChartRoomController/exportExcelStatisticsSpeak?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

