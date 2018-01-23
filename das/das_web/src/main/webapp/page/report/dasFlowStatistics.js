$(function() {
	dasFlowStatistics.init();
});

var dasFlowStatistics = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasFlowStatistics.loadDataGrid();
		// 加载图表数据
		dasFlowStatisticsChart.loadChartData();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasFlowStatisticsController/pageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
			    {field : 'dataTime', title : '日期', sortable : true, width : 50},
	 			{field : 'visitCount', title : '访问次数', sortable : true, width : 50},
	 			{field : 'advisoryCountQQ', title : 'QQ咨询', sortable : true, width : 50},
	 			{field : 'advisoryCountLIVE800', title : 'LIVE800咨询', sortable : true, width : 50},
	 			{field : 'demoCount', title : '模拟开户数', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.demoCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var html = "";	 						
	 						html += "<a onclick=\"dasFlowStatistics.viewDetail1('"+rowData.dataTime+"','"+$("#platformTypeSearch").combobox('getValue')+"', '3')\">" + rowData.demoCount + "</a>";
	 						html += " / ";
	 						html += "<a onclick=\"dasFlowStatistics.viewDetail2('"+rowData.dataTime+"', '"+$("#platformTypeSearch").combobox('getValue')+"', '3')\">详情</a>";
	 						return html;
	 					}else{
	 						return rowData.demoCount;
	 					}
				}},
	 			{field : 'realCount', title : '真实开户数', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.realCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var html = "";
	 						html += "<a onclick=\"dasFlowStatistics.viewDetail1('"+rowData.dataTime+"','"+$("#platformTypeSearch").combobox('getValue')+"', '4')\">" + rowData.realCount + "</a>";
	 						html += " / ";
	 						html += "<a onclick=\"dasFlowStatistics.viewDetail2('"+rowData.dataTime+"','"+$("#platformTypeSearch").combobox('getValue')+"', '4')\">详情</a>";
	 						return html;
	 					}else{
	 						return rowData.realCount;
	 					}
				}},
	 			{field : 'depositCount', title : '首次入金数', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.depositCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var html = "";
	 						html += "<a onclick=\"dasFlowStatistics.viewDetail1('"+rowData.dataTime+"','"+$("#platformTypeSearch").combobox('getValue')+"', '5')\">" + rowData.depositCount + "</a>";
	 						html += " / ";
	 						html += "<a onclick=\"dasFlowStatistics.viewDetail2('"+rowData.dataTime+"','"+$("#platformTypeSearch").combobox('getValue')+"', '5')\">详情</a>";
	 						return html;
	 					}else{
	 						return rowData.depositCount;
	 					}
				}},
				{field : 'platformType', title : '访问客户端', sortable : false, width : 70, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.platformType == '0'){
							return "PC端";
						}else if(rowData.platformType == '1'){
							return "移动端";
						}else{
							// 统计时，为空代表所有客户端
							if(!common.isBlank(rowData.dataTime)){
								return "(全部)";
							}else{
								// 小计行的该列显示为空
								return "";
							}
						}
	 				}
				}
 			] ];
		$('#' + dasFlowStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var options = $("#" + dasFlowStatistics.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasFlowStatistics.getQueryParams();
		common.loadGrid(dasFlowStatistics.dataGridId);
		// 加载图表数据
		dasFlowStatisticsChart.loadChartData();
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasFlowStatistics.searchFormId).form('reset');
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
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = dasFlowStatistics.getQueryParams();
		var url = BASE_PATH + "DasFlowStatisticsController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 模拟开户数
	 */
	viewDetail1 : function(dataTime,platformType, behaviorType){
		var url = "DasFlowStatisticsController/pageAverage?dataTime=" + dataTime + "&platformType="+ platformType + "&behaviorType=" + behaviorType;
		if(behaviorType == '3'){
			parent.index.addOrUpdateTabs("模拟开户数", url);
		}else if(behaviorType == '4'){
			parent.index.addOrUpdateTabs("真实开户数", url);
		}else if(behaviorType == '5'){
			parent.index.addOrUpdateTabs("首次入金数", url);
		}
	},
	/**
	 * 行为详细汇总
	 */
	viewDetail2 : function(dataTime, platformType,behaviorType){
		var url = "DasUserBehaviorController/pageByFlowStatistics?dataTime=" + dataTime + "&platformType="+ platformType + "&behaviorType=" + behaviorType;
		parent.index.addOrUpdateTabs("行为详细汇总", url);
	}
	
}




















