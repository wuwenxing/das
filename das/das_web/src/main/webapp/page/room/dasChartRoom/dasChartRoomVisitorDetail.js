$(function() {
	dasChartRoomVisitorDetail.init();
});

var dasChartRoomVisitorDetail = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartRoomVisitorDetail.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findVisitorDetailPage';
		var queryParams = {
		};
		queryParams['rowKey'] = $("#rowKey").val();
		queryParams['touristId'] = $("#touristId").val();
		queryParams['userTel'] = $("#userTelSearch").val();
		queryParams['upStartTime'] = $("#upStartTimeSearch").datetimebox('getValue');
		queryParams['upEndTime'] = $("#upEndTimeSearch").datetimebox('getValue');
		var columns = [ [
		 	 	{field : 'touristId', title : '游客ID', sortable : false, width : 100},
//	 			{field : 'userName', title : '名称', sortable : false, width : 50},
	 			{field : 'nickName', title : '昵称', sortable : false, width : 50},
	 			{field : 'userTel', title : '手机号码', sortable : false, width : 100},
	 			{field : 'userType', title : '用户级别', sortable : false, width : 60},
	 			{field : 'roomName', title : '房间名称', sortable : false, width : 100},
	 			{field : 'startTime', title : '上线时间', sortable : false, width : 100},
	 			{field : 'endTime', title : '下线时间', sortable : false, width : 100},
	 			{field : 'timeLength', title : '在线时长', sortable : false, width : 100},
	 			{field : 'publicSpeakCount', title : '公聊次数', sortable : false, width : 100},
	 			{field : 'privateSpeakCount', title : '私聊次数', sortable : false, width : 100},
	 			{field : 'loginCount', title : '登录次数', sortable : false, width : 100},
	 			{field : 'userIp', title : '用户IP', sortable : false, width : 70},
	 			{field : 'userSource', title : '用户来源', sortable : false, width : 60},
	 			{field : 'ipHome', title : '用户所在地', sortable : false, width : 70},
	 			{field : 'platformType', title : '访问客户端', sortable : false, width : 70, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.platformType == '0'){
							return "PC端";
						}else if(rowData.platformType == '1'){
							return "移动端";
						}else{
							return "";
						}
	 				}
				}
 			] ];
		$('#' + dasChartRoomVisitorDetail.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var queryParams = $("#" + dasChartRoomVisitorDetail.dataGridId).datagrid('options').queryParams;
		queryParams['rowKey'] = $("#rowKey").val();
		queryParams['touristId'] = $("#touristId").val();
		queryParams['nickName'] = $("#nickNameSearch").val();
		queryParams['userTel'] = $("#userTelSearch").val();
		queryParams['roomName'] = $("#roomNameSearch").val();
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['userSource'] = $("#userSourceSearch").val();
		queryParams['timeLengthStart'] = $("#timeLengthStartSearch").numberspinner('getValue');
		queryParams['timeLengthEnd'] = $("#timeLengthEndSearch").numberspinner('getValue');
		queryParams['upStartTime'] = $("#upStartTimeSearch").datetimebox('getValue');
		queryParams['upEndTime'] = $("#upEndTimeSearch").datetimebox('getValue');
		queryParams['downStartTime'] = $("#downStartTimeSearch").datetimebox('getValue');
		queryParams['downEndTime'] = $("#downEndTimeSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasChartRoomVisitorDetail.getQueryParams();
		common.loadGrid(dasChartRoomVisitorDetail.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomVisitorDetail.searchFormId).form('reset');
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = {};
		queryParams['touristId'] = $("#touristId").val();
		queryParams['nickName'] = $("#nickNameSearch").val();
		queryParams['userTel'] = $("#userTelSearch").val();
		queryParams['roomName'] = $("#roomNameSearch").val();
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['userSource'] = $("#userSourceSearch").val();
		queryParams['timeLengthStart'] = $("#timeLengthStartSearch").numberspinner('getValue');
		queryParams['timeLengthEnd'] = $("#timeLengthEndSearch").numberspinner('getValue');
		queryParams['upStartTime'] = $("#upStartTimeSearch").datetimebox('getValue');
		queryParams['upEndTime'] = $("#upEndTimeSearch").datetimebox('getValue');
		queryParams['downStartTime'] = $("#downStartTimeSearch").datetimebox('getValue');
		queryParams['downEndTime'] = $("#downEndTimeSearch").datetimebox('getValue');
		var url = BASE_PATH + "DasChartRoomController/visitorDetailExportExcel?";
		url = common.formatUrl(url, queryParams);
		// 因含有特殊字符#，需要对rowKey参数编码
		var rowKey = encodeURIComponent($("#rowKey").val());
		url += "&rowKey=" + rowKey;
		window.location.href = url;
	}
}

