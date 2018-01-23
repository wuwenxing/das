$(function() {
	dasChartRoomStatistics.init();
});

var dasChartRoomStatistics = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartRoomStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findStatisticsPage';
		var queryParams = {
		};
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['roomNameChecked'] = $("#roomNameChecked").val();
		queryParams['courseNameChecked'] = $("#courseNameChecked").val();
		queryParams['teacherNameChecked'] = $("#teacherNameChecked").val();
		var columns = [ 
			[
				{field : 'dateTime', title : '日期', rowspan: 2, sortable : true, width : 100},
				{field : 'hours', title : '时段', rowspan: 2,sortable : false, width : 50},
				{field : 'weeks', title : '周', rowspan: 2,sortable : false, width : 50},
				{field : 'roomName', title : '房间名称', rowspan: 2,sortable : true, width : 100},
				{field : 'courseName', title : '课程名称', rowspan: 2,sortable : true, width : 100},
				{field : 'teacherName', title : '老师名称', rowspan: 2,sortable : true, width : 100},
				{title : '公聊发言', colspan: 2, sortable : false, width : 100},
				{title : '私聊发言', colspan: 2, sortable : false, width : 100},
				{title : '访问', colspan: 2, sortable : false, width : 100},
				{title : '登录', colspan: 2, sortable : false, width : 100}
			],
		    [
	 			{field : 'publicSpeakCount', title : '次数', sortable : true, width : 100},
	 			{field : 'publicSpeakNumber', title : '人数', sortable : true, width : 100},
	 			{field : 'privateSpeakCount', title : '次数', sortable : true, width : 100},
	 			{field : 'privateSpeakNumber', title : '人数', sortable : true, width : 100},
	 			{field : 'visitCount', title : '次数', sortable : true, width : 100},
	 			{field : 'visitNumber', title : '人数', sortable : true, width : 100},
	 			{field : 'loginCount', title : '次数', sortable : true, width : 100},
	 			{field : 'loginNumber', title : '人数', sortable : true, width : 100}
 			] ];
		$('#' + dasChartRoomStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				if($("#roomNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('showColumn', 'roomName');
				}else{
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('hideColumn', 'roomName');
				}
				if($("#courseNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('showColumn', 'courseName');
				}else{
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('hideColumn', 'courseName');
				}
				if($("#teacherNameChecked").val() == 'true'){
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('showColumn', 'teacherName');
				}else{
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('hideColumn', 'teacherName');
				}
				var reportType = $("#reportTypeSearch").combobox('getValue');
				if(reportType == 'hours'){
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('showColumn', 'hours');
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('hideColumn', 'weeks');
				}else if(reportType == 'days' || reportType == 'months'){
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('hideColumn', 'hours');
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('hideColumn', 'weeks');
				}else if(reportType == 'weeks'){
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('hideColumn', 'hours');
					$("#" + dasChartRoomStatistics.dataGridId).datagrid('showColumn', 'weeks');
				}
				dasChartRoomStatisticsChart.loadChartData(1);
				dasChartRoomStatisticsChart.loadChartData(2);
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dasChartRoomStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		queryParams['roomName'] = $("#roomNameSearch").val();
		queryParams['roomNameChecked'] = $("#roomNameChecked").val();
		queryParams['courseName'] = $("#courseNameSearch").val();
		queryParams['courseNameChecked'] = $("#courseNameChecked").val();
		queryParams['teacherName'] = $("#teacherNameSearch").val();
		queryParams['teacherNameChecked'] = $("#teacherNameChecked").val();
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['startHours'] = $("#startHoursSearch").numberspinner('getValue');
		queryParams['endHours'] = $("#endHoursSearch").numberspinner('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasChartRoomStatistics.getQueryParams();
		common.loadGrid(dasChartRoomStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomStatistics.searchFormId).form('reset');
		
		$("#roomNameChecked").val("false");
		$("#courseNameChecked").val("false");
		$("#teacherNameChecked").val("false");
		
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#startHoursSearch').numberspinner({
			readonly: false
		});
		$('#endHoursSearch').numberspinner({
			readonly: false
		});
		common.setEasyUiCss();
	},
	/**
	 * 统计类型change事件
	 */
	setDateboxAttr: function(newValue,oldValue){
		$("#startHoursSearch").numberspinner('setValue', '');
		$("#endHoursSearch").numberspinner('setValue', '');
		$('#startHoursSearch').numberspinner({
			readonly: true
		});
		$('#endHoursSearch').numberspinner({
			readonly: true
		});
		if(newValue == 'hours'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$('#startHoursSearch').numberspinner({
				readonly: false
			});
			$('#endHoursSearch').numberspinner({
				readonly: false
			});
			$("#startTimeSearch").datebox('setValue', $("#yesterdayStart").val());
			$("#endTimeSearch").datebox('setValue', $("#dayEnd").val());
		}else if(newValue == 'days'){
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
		var queryParams = dasChartRoomStatistics.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/exportStatistics?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

