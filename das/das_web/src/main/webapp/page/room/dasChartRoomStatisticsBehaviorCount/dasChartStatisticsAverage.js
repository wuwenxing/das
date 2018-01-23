$(function() {
	dasChartStatisticsAverage.init();
});

var dasChartStatisticsAverage = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartStatisticsAverage.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 设置title
		var titleColumn = "开户/入金数";
		if($("#behaviorTypeSearch").val() == 3){
			titleColumn = "模拟开户数";
		}else if($("#behaviorTypeSearch").val() == 4){
			titleColumn = "真实开户数";
		}else if($("#behaviorTypeSearch").val() == 5){
			titleColumn = "首次入金数";
		}
		
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/pageListAverage';
		var queryParams = {
		};
		queryParams['devicetype'] = $("#devicetypeSearch").val();
		queryParams['dataTime'] = $("#dataTimeSearch").val();
		queryParams['behaviorType'] = $("#behaviorTypeSearch").val();
		var columns = [ [
			    {field : 'dataTime', title : '日期', sortable : true, width : 50},
	 			{field : 'utmcmd', title : '媒介', sortable : true, width : 50},
	 			{field : 'accountCount', title : titleColumn, sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.accountCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var html = "";
	 						html += "<a onclick=\"dasChartStatisticsAverage.viewDetail1('"+rowData.dataTime+"', '"+$("#behaviorTypeSearch").val()+"', '"+$("#devicetypeSearch").val()+"', '"+rowData.utmcmd+"')\">" + rowData.accountCount + "</a>";
	 						html += " / ";
	 						html += "<a onclick=\"dasChartStatisticsAverage.viewDetail2('"+rowData.dataTime+"', '"+$("#behaviorTypeSearch").val()+"','"+$("#devicetypeSearch").val()+"', '"+rowData.utmcmd+"')\">详情</a>";
	 						return html;
	 					}else{
	 						return rowData.accountCount;
	 					}
				}},
	 			{field : 'lastMonthAvgCount', title : '上月平均数', sortable : true, width : 50},
	 			{field : 'deviceAvgCount', title : '设备次数', sortable : true, width : 50}
 			] ];
		$('#' + dasChartStatisticsAverage.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'dataTime',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='dataTime']").text("小计：");
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + dasChartStatisticsAverage.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['devicetype'] = $("#devicetypeSearch").val();
		queryParams['dataTime'] = $("#dataTimeSearch").val();
		queryParams['behaviorType'] = $("#behaviorTypeSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasChartStatisticsAverage.getQueryParams();
		common.loadGrid(dasChartStatisticsAverage.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartStatisticsAverage.searchFormId).form('reset');
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = dasChartStatisticsAverage.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/exportExcelAverage?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 来源媒介统计
	 */
	viewDetail1 : function(dataTime, behaviorType,devicetype, utmcmd){
		var url = "DasChartRoomController/roomSourceCountPage?dataTime=" + dataTime
			+ "&behaviorType=" + behaviorType
			+ "&devicetype=" + devicetype
			+ "&utmcmd=" + utmcmd;
		parent.index.addOrUpdateTabs("来源媒介统计", url);
	},
	/**
	 * 行为详细汇总
	 */
	viewDetail2 : function(dataTime, behaviorType,devicetype, utmcmd){
		var url = "DasChartRoomController/pageRoomBehavior?dataTime=" + dataTime
			+ "&behaviorType=" + behaviorType
			+ "&devicetype=" + devicetype
			+ "&utmcmd=" + utmcmd;
		parent.index.addOrUpdateTabs("行为详细汇总", url);
	}
	
}




















