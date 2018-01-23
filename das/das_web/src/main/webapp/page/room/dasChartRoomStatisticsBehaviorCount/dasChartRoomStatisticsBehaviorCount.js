$(function() {
	dasChartRoomStatisticsBehaviorCount.init();
});

var dasChartRoomStatisticsBehaviorCount = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartRoomStatisticsBehaviorCount.loadDataGrid();
		// 加载图表数据
		dasChartRoomStatisticsBehaviorCountChart.loadChartData();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/behaviorCountPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
			    {field : 'dataTime', title : '日期', sortable : true, width : 50},
	 			/*{field : 'visitCount', title : '访问次数', sortable : true, width : 50},
	 			{field : 'advisoryCountQQ', title : 'QQ咨询', sortable : true, width : 50},
	 			{field : 'advisoryCountLIVE800', title : 'LIVE800咨询', sortable : true, width : 50},*/
			    {field : 'devicecount', title : '设备次数', sortable : true, width : 50}, 
	 			{field : 'demoCount', title : '模拟开户数', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.demoCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var html = "";	 						
	 						html += "<a onclick=\"dasChartRoomStatisticsBehaviorCount.viewDetail1('"+rowData.dataTime+"','"+$("#devicetypeSearch").combobox('getValue')+"', '3')\">" + rowData.demoCount + "</a>";
	 						html += " / ";
	 						html += "<a onclick=\"dasChartRoomStatisticsBehaviorCount.viewDetail2('"+rowData.dataTime+"', '"+$("#devicetypeSearch").combobox('getValue')+"', '3')\">详情</a>";
	 						return html;
	 					}else{
	 						return rowData.demoCount;
	 					}
				}},
	 			{field : 'realCount', title : '真实开户数', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.realCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var html = "";
	 						html += "<a onclick=\"dasChartRoomStatisticsBehaviorCount.viewDetail1('"+rowData.dataTime+"','"+$("#devicetypeSearch").combobox('getValue')+"', '4')\">" + rowData.realCount + "</a>";
	 						html += " / ";
	 						html += "<a onclick=\"dasChartRoomStatisticsBehaviorCount.viewDetail2('"+rowData.dataTime+"','"+$("#devicetypeSearch").combobox('getValue')+"', '4')\">详情</a>";
	 						return html;
	 					}else{
	 						return rowData.realCount;
	 					}
				}},
	 			{field : 'depositCount', title : '首次入金数', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.depositCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var html = "";
	 						html += "<a onclick=\"dasChartRoomStatisticsBehaviorCount.viewDetail1('"+rowData.dataTime+"','"+$("#devicetypeSearch").combobox('getValue')+"', '5')\">" + rowData.depositCount + "</a>";
	 						html += " / ";
	 						html += "<a onclick=\"dasChartRoomStatisticsBehaviorCount.viewDetail2('"+rowData.dataTime+"','"+$("#devicetypeSearch").combobox('getValue')+"', '5')\">详情</a>";
	 						return html;
	 					}else{
	 						return rowData.depositCount;
	 					}
				}},
				{field : 'devicetype', title : '访问客户端', sortable : false, width : 70, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.devicetype == '0'){
							return "PC";
						}else if(rowData.devicetype == '1'){
							return "Android";
						}else if(rowData.devicetype == '2'){
							return "IOS";
						}else{
							return "(全部)";
						}
	 				}
				}
 			] ];
		$('#' + dasChartRoomStatisticsBehaviorCount.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		var options = $("#" + dasChartRoomStatisticsBehaviorCount.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['devicetype'] = $("#devicetypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasChartRoomStatisticsBehaviorCount.getQueryParams();
		common.loadGrid(dasChartRoomStatisticsBehaviorCount.dataGridId);
		// 加载图表数据
		dasChartRoomStatisticsBehaviorCountChart.loadChartData();
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomStatisticsBehaviorCount.searchFormId).form('reset');
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
		var queryParams = dasChartRoomStatisticsBehaviorCount.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/exportExcelBehaviorCount?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 模拟开户数
	 */
	viewDetail1 : function(dataTime,devicetype, behaviorType){
		var url = "DasChartRoomController/pageAverage?dataTime=" + dataTime + "&devicetype="+ devicetype + "&behaviorType=" + behaviorType;
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
	viewDetail2 : function(dataTime, devicetype,behaviorType){
		var url = "DasChartRoomController/pageRoomBehavior?dataTime=" + dataTime + "&devicetype="+ devicetype + "&behaviorType=" + behaviorType;
		parent.index.addOrUpdateTabs("行为详细汇总", url);
	}
	
}


