$(function() {
	statisticsLogin.init();
});

var statisticsLogin = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		statisticsLogin.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findStatisticsByUserTypePage/1';
		var queryParams = {
		};
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['courseNameChecked'] = $("#courseNameChecked").val();// 复选框
		queryParams['teacherNameChecked'] = $("#teacherNameChecked").val();// 复选框
		queryParams['roomNameChecked'] = $("#roomNameChecked").val();// 复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
 		 	 	{field : 'dateTime', title : '日期',rowspan: 2, sortable : true, width : 100, 
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
				{field : 'weeks', title : '周',rowspan: 2,sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var dateTime = rowData.dateTime
	 					return common.getWeekNumber(new Date(dateTime));
				}},
 		 	 	{field : 'courseName', title : '课程名称',rowspan: 2, sortable : true, width : 100},
 		 	 	{field : 'teacherName', title : '老师名称',rowspan: 2, sortable : true, width : 100},
 		 	 	{field : 'roomName', title : '房间名称',rowspan: 2, sortable : true, width : 100},
		 	 	{title : 'VIP用户', colspan: 2, sortable : false, width : 100},
		 	 	{title : '真实A用户', colspan: 2, sortable : false, width : 100},
		 	 	{title : '真实N用户', colspan: 2, sortable : false, width : 100},
		 	 	{title : '模拟用户', colspan: 2, sortable : false, width : 100},
		 	 	{title : '注册用户', colspan: 2, sortable : false, width : 100},
		 	 	{title : '游客', colspan: 2, sortable : false, width : 100},
		 	 	{title : '分析师', colspan: 2, sortable : false, width : 100},
		 	 	{title : '管理员', colspan: 2, sortable : false, width : 100},
		 	 	{title : '客服', colspan: 2, sortable : false, width : 100}
 			],[
				{field : 'vipNumbers', title : '人数', sortable : true, width : 100},
				{field : 'vipCounts', title : '次数', sortable : true, width : 100},
		 	 	{field : 'realANumbers', title : '人数', sortable : true, width : 100},
	 			{field : 'realACounts', title : '次数', sortable : true, width : 100},
	 			{field : 'realNNumbers', title : '人数', sortable : true, width : 100},
	 			{field : 'realNCounts', title : '次数', sortable : true, width : 100},
	 			{field : 'demoNumbers', title : '人数', sortable : true, width : 100},
	 			{field : 'demoCounts', title : '次数', sortable : true, width : 100},
	 			{field : 'registNumbers', title : '人数', sortable : true, width : 100},
	 			{field : 'registCounts', title : '次数', sortable : true, width : 100},
	 			{field : 'touristNumbers', title : '人数', sortable : true, width : 100},
	 			{field : 'touristCounts', title : '次数', sortable : true, width : 100},
	 			{field : 'analystNumbers', title : '人数', sortable : true, width : 100},
	 			{field : 'analystCounts', title : '次数', sortable : true, width : 100},
	 			{field : 'adminNumbers', title : '人数', sortable : true, width : 100},
	 			{field : 'adminCounts', title : '次数', sortable : true, width : 100},
	 			{field : 'cServiceNumbers', title : '人数', sortable : true, width : 100},
	 			{field : 'cServiceCounts', title : '次数', sortable : true, width : 100}
 			   ] ];
		$('#' + statisticsLogin.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
					$("#" + statisticsLogin.dataGridId).datagrid('showColumn', 'courseName');
				}else{
					$("#" + statisticsLogin.dataGridId).datagrid('hideColumn', 'courseName');
				}
				if($("#teacherNameChecked").val() == 'true'){
					$("#" + statisticsLogin.dataGridId).datagrid('showColumn', 'teacherName');
				}else{
					$("#" + statisticsLogin.dataGridId).datagrid('hideColumn', 'teacherName');
				}
				if($("#roomNameChecked").val() == 'true'){
					$("#" + statisticsLogin.dataGridId).datagrid('showColumn', 'roomName');
				}else{
					$("#" + statisticsLogin.dataGridId).datagrid('hideColumn', 'roomName');
				}
				var reportType = $("#reportTypeSearch").combobox('getValue');
				if(reportType == 'days' || reportType == 'months'){
					$("#" + statisticsLogin.dataGridId).datagrid('hideColumn', 'weeks');
				}
				if(reportType == 'weeks'){
					$("#" + statisticsLogin.dataGridId).datagrid('showColumn', 'weeks');
				}
				statisticsLoginChart.loadChartData(1);
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + statisticsLogin.dataGridId).datagrid('options').queryParams;
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
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
		statisticsLogin.getQueryParams();
		common.loadGrid(statisticsLogin.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + statisticsLogin.searchFormId).form('reset');
		
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
		var queryParams = statisticsLogin.getQueryParams();
		queryParams['userTypeStatistics'] = 1;
		var url = BASE_PATH + "DasChartRoomController/exportExcelStatisticsByUserType?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

