$(function() {
	dasFlowStatisticsAverage.init();
});

var dasFlowStatisticsAverage = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasFlowStatisticsAverage.loadDataGrid();
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
		var url = BASE_PATH + 'DasFlowStatisticsController/pageListAverage';
		var queryParams = {
		};
		queryParams['platformType'] = $("#platformTypeSearch").val();
		queryParams['dataTime'] = $("#dataTimeSearch").val();
		queryParams['behaviorType'] = $("#behaviorTypeSearch").val();
		var columns = [ [
			    {field : 'dataTime', title : '日期', sortable : true, width : 50},
	 			{field : 'utmcmd', title : '媒介', sortable : true, width : 50},
	 			{field : 'accountCount', title : titleColumn, sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.accountCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var html = "";
	 						html += "<a onclick=\"dasFlowStatisticsAverage.viewDetail1('"+rowData.dataTime+"', '"+$("#behaviorTypeSearch").val()+"', '"+$("#platformTypeSearch").val()+"', '"+rowData.utmcmd+"')\">" + rowData.accountCount + "</a>";
	 						html += " / ";
	 						html += "<a onclick=\"dasFlowStatisticsAverage.viewDetail2('"+rowData.dataTime+"', '"+$("#behaviorTypeSearch").val()+"','"+$("#platformTypeSearch").val()+"', '"+rowData.utmcmd+"')\">详情</a>";
	 						return html;
	 					}else{
	 						return rowData.accountCount;
	 					}
				}},
	 			{field : 'lastMonthAvgCount', title : '上月平均数', sortable : true, width : 50}
 			] ];
		$('#' + dasFlowStatisticsAverage.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var options = $("#" + dasFlowStatisticsAverage.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['platformType'] = $("#platformTypeSearch").val();
		queryParams['dataTime'] = $("#dataTimeSearch").val();
		queryParams['behaviorType'] = $("#behaviorTypeSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasFlowStatisticsAverage.getQueryParams();
		common.loadGrid(dasFlowStatisticsAverage.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasFlowStatisticsAverage.searchFormId).form('reset');
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = dasFlowStatisticsAverage.getQueryParams();
		var url = BASE_PATH + "DasFlowStatisticsController/exportExcelAverage?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 来源媒介统计
	 */
	viewDetail1 : function(dataTime, behaviorType,platformType, utmcmd){
		var url = "DasFlowStatisticsController/pageMedia?dataTime=" + dataTime
			+ "&behaviorType=" + behaviorType
			+ "&platformType=" + platformType
			+ "&utmcmd=" + utmcmd;
		parent.index.addOrUpdateTabs("来源媒介统计", url);
	},
	/**
	 * 行为详细汇总
	 */
	viewDetail2 : function(dataTime, behaviorType,platformType, utmcmd){
		var url = "DasUserBehaviorController/pageByFlowStatistics?dataTime=" + dataTime
			+ "&behaviorType=" + behaviorType
			+ "&platformType=" + platformType
			+ "&utmcmd=" + utmcmd;
		parent.index.addOrUpdateTabs("行为详细汇总", url);
	}
	
}




















