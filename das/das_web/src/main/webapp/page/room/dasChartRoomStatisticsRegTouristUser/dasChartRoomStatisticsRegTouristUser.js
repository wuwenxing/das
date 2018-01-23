$(function() {
	dasChartRoomStatisticsRegTouristUser.init();
});

var dasChartRoomStatisticsRegTouristUser = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
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
		dasChartRoomStatisticsRegTouristUser.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findStatisticsRegTouristUserPage';
		var queryParams = {
		};
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
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
		 	 	{field : 'vipNumbers', title : 'VIP用户', sortable : true, width : 100},
	 			{field : 'realANumbers', title : '真实A用户', sortable : true, width : 100},
	 			{field : 'realNNumbers', title : '真实N用户', sortable : true, width : 100},
	 			{field : 'demoNumbers', title : '模拟用户', sortable : true, width : 100},
	 			{field : 'registNumbers', title : '注册用户', sortable : true, width : 100},
	 			{field : 'touristNumbers', title : '游客用户', sortable : true, width : 100}
 			] ];
		$('#' + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
					$("#" + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('showColumn', 'courseName');
				}else{
					$("#" + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('hideColumn', 'courseName');
				}
				if($("#teacherNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('showColumn', 'teacherName');
				}else{
					$("#" + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('hideColumn', 'teacherName');
				}
				if($("#roomNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('showColumn', 'roomName');
				}else{
					$("#" + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('hideColumn', 'roomName');
				}
				var reportType = $("#reportTypeSearch").combobox('getValue');
				if(reportType == 'days' || reportType == 'months'){
					$("#" + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('hideColumn', 'weeks');
				}
				if(reportType == 'weeks'){
					$("#" + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('showColumn', 'weeks');
				}
				dasChartRoomStatisticsRegTouristUserChart.loadChartData(1);
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dasChartRoomStatisticsRegTouristUser.dataGridId).datagrid('options').queryParams;
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
//		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
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
		dasChartRoomStatisticsRegTouristUser.getQueryParams();
		common.loadGrid(dasChartRoomStatisticsRegTouristUser.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomStatisticsRegTouristUser.searchFormId).form('reset');
		
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
		var queryParams = dasChartRoomStatisticsRegTouristUser.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/exportStatisticsRegTouristUser?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

