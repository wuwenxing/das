$(function() {
	behavior.init();
});

var behavior = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		behavior.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/findBehaviorPageList';
		var queryParams = {
		};
		queryParams['behaviorType'] = $("#behaviorTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['devicetype'] = $("#devicetypeSearch").datebox('getValue');
		var columns = [ [
			    {field : 'userId', title : '用户', sortable : false, width : 150},
	 			{field : 'startTime', title : '行为时间', sortable : false, width : 100},
	 			{field : 'timeLength', title : '行为时长', sortable : false, width : 50},
	 			{field : 'behaviorType', title : '行为类型', sortable : false, width : 60, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var behaviorType = rowData.behaviorType;
	 					if(behaviorType == '模拟' || behaviorType == '真实' || behaviorType == '入金'){
	 						return rowData.behaviorDetail;
	 					}else{
	 						return behaviorType;
	 					}
				}},
	 			{field : 'utmcsr', title : '来源', sortable : false, width : 50},
	 			{field : 'utmcmd', title : '媒介', sortable : false, width : 50},
	 			{field : 'utmccn', title : '系列', sortable : false, width : 50},
	 			{field : 'utmcct', title : '组', sortable : false, width : 50},
	 			{field : 'utmctr', title : '关键字', sortable : false, width : 70},
	 			{field : 'ip', title : 'IP', sortable : false, width : 70},
	 			//{field : 'ipHomeJson', title : '归属地', sortable : false, width : 100},	
	 			{field : 'ipHome', title : '归属地', sortable : false, width : 100},	
	 			/*{field : 'visitCount', title : '页面浏览数', sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					//if(rowData.visitCount > 0){
	 					//	return "<a onclick=\"behavior.viewUrl('"+rowData.rowKey+"', '" + rowData.formatTime + "', '" + $("#devicetypeSearch").datebox('getValue') + "', '" + $("#utmcmdSearch").val() + "', '" + $("#utmcsrSearch").val() + "')\">" + rowData.visitCount + "</a>";	
	 					//}else{
	 					//	return rowData.visitCount;
	 					//}
	 					return rowData.visitCount;
				}}*/
 			] ];
		$('#' + behavior.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var options = $("#" + behavior.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['behaviorType'] = $("#behaviorTypeSearch").combobox('getValue');
		queryParams['userId'] = $("#userIdSearch").val();
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['utmccn'] = $("#utmccnSearch").val();// 系列
		queryParams['utmcct'] = $("#utmcctSearch").val();// 组
		queryParams['ip'] = $("#ipSearch").val();
		queryParams['utmctr'] = $("#utmctrSearch").val();// 关键字
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['devicetype'] = $("#devicetypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		behavior.getQueryParams();
		common.loadGrid(behavior.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + behavior.searchFormId).form('reset');
		$('#startTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
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
		var queryParams = behavior.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/exportExcelBehavior?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 浏览详细
	 */
	viewUrl : function(flowDetailId, formatTime,devicetype,utmcmdh,utmcsr){
		var url = "DasChartRoomController/flowDetailUrlPage?flowDetailId=" + flowDetailId + "&formatTime=" + formatTime
		          +"&devicetype="+devicetype
		          +"&utmcmd="+utmcmdh
		          +"&utmcsr="+utmcsr
		          +"&startTime="+$("#startTimeSearch").datetimebox('getValue')
		          +"&endTime="+$("#endTimeSearch").datetimebox('getValue');
		parent.index.addOrUpdateTabs("浏览详细", url);
	}
	
}




















