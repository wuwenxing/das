$(function() {
	dasChartRoomVisitor.init();
});

var dasChartRoomVisitor = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartRoomVisitor.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findVisitorPage';
		var queryParams = {
		};
		queryParams['upStartTime'] = $("#upStartTimeSearch").datetimebox('getValue');
		queryParams['upEndTime'] = $("#upEndTimeSearch").datetimebox('getValue');
		var columns = [ [
 	 			{field : 'oper', title : '操作', sortable : false, width : 80, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var touristId = rowData.touristId;
	 					var userTel = rowData.userTel;
	 					var rowKey = rowData.rowKey;
	 					if(common.isBlank(touristId)){
	 						touristId = ""
	 					}
	 					if(common.isBlank(userTel)){
	 						userTel = ""
	 					}
	 					if(common.isBlank(rowKey)){
	 						rowKey = ""
	 					}
	 					var temp = touristId + "," + userTel + "," + rowKey + ",";
						$("#rowOperation a").each(function(){
							$(this).attr("id", temp);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'touristId', title : '游客ID', sortable : false, width : 50},
//	 			{field : 'userName', title : '名称', sortable : false, width : 50},
	 			{field : 'nickName', title : '昵称', sortable : false, width : 50},
	 			{field : 'userTel', title : '手机号码', sortable : false, width : 60, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var userTel = rowData.userTel;
	 					if(!common.isBlank(userTel) && userTel.length>7){
	 						userTel = userTel.substring(0, 3) 
	 							+ "****" + userTel.substring(7, userTel.length)
	 					}
	 					return userTel;
	 				}
	 			},
	 			{field : 'userType', title : '用户级别', sortable : false, width : 60},
	 			{field : 'roomName', title : '房间名称', sortable : false, width : 60},
	 			{field : 'visitCount', title : '访问次数', sortable : false, width : 60},
	 			{field : 'publicSpeakDay', title : '公聊天数', sortable : false, width : 60},
	 			{field : 'publicSpeakCount', title : '公聊次数', sortable : false, width : 60},
	 			{field : 'privateSpeakDay', title : '私聊天数', sortable : false, width : 60},
	 			{field : 'privateSpeakCount', title : '私聊次数', sortable : false, width : 60},
	 			{field : 'registerTime', title : '注册时间', sortable : false, width : 60},
//	 			{field : 'registerTime1', title : '开户时间', sortable : false, width : 60, 
//	 				formatter : function(value, rowData, rowIndex) {
//	 					return rowData.registerTime;
//				}},
	 			{field : 'lastStartTime', title : '上次上线时间', sortable : false, width : 80},
	 			{field : 'startTime', title : '上线时间', sortable : false, width : 80},
	 			{field : 'endTime', title : '下线时间', sortable : false, width : 60},
	 			{field : 'timeLength', title : '在线时长', sortable : false, width : 60},
	 			{field : 'userIp', title : '用户IP', sortable : false, width : 70},
	 			{field : 'ipHome', title : '用户所在地', sortable : false, width : 70}
 			] ];
		$('#' + dasChartRoomVisitor.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'startTime',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dasChartRoomVisitor.dataGridId).datagrid('options').queryParams;
		queryParams['nickName'] = $("#nickNameSearch").val();
		queryParams['userTel'] = $("#userTelSearch").val();
		queryParams['roomName'] = $("#roomNameSearch").val();
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		queryParams['timeLengthStart'] = $("#timeLengthStartSearch").numberspinner('getValue');
		queryParams['timeLengthEnd'] = $("#timeLengthEndSearch").numberspinner('getValue');
		queryParams['upStartTime'] = $("#upStartTimeSearch").datetimebox('getValue');
		queryParams['upEndTime'] = $("#upEndTimeSearch").datetimebox('getValue');
		queryParams['downStartTime'] = $("#downStartTimeSearch").datetimebox('getValue');
		queryParams['downEndTime'] = $("#downEndTimeSearch").datetimebox('getValue');
		queryParams['regStartTime'] = $("#regStartTimeSearch").datetimebox('getValue');
		queryParams['regEndTime'] = $("#regEndTimeSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasChartRoomVisitor.getQueryParams();
		common.loadGrid(dasChartRoomVisitor.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomVisitor.searchFormId).form('reset');
		
		$('#upStartTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD000000,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#upEndTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD235959,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	},
	/**
	 * 访客详情
	 */
	viewDetail : function(id){
		var touristId = id.split(",")[0];
		var userTel = id.split(",")[1];
		var rowKey = id.split(",")[2];
		// 因含有特殊字符#，需要对rowKey参数编码
		rowKey = encodeURIComponent(rowKey);
		var url = "DasChartRoomController/visitorDetailPage" +
				"?touristId=" + touristId + "&userTel=" + userTel
				+ "&rowKey=" + rowKey;
		parent.index.addOrUpdateTabs("访客详情", url);
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = dasChartRoomVisitor.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/visitorExportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

