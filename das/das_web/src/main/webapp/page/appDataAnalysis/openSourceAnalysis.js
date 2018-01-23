$(function() {
	openSourceAnalysis.init();
});

var openSourceAnalysis = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		openSourceAnalysis.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
	    // 初始参数
		var url = BASE_PATH + 'appDataAnalysisController/openSourceAnalysisPageList';
		var queryParams = {
		};
		queryParams['platformType'] = $("#platformTypeSearch").val();
		queryParams['dataTime'] = $("#dataTimeSearch").val();
		queryParams['behaviorType'] = $("#behaviorTypeSearch").val();
		var columns = [ 
		      			[
		   				{field : 'dtMonth', title : '日期', rowspan: 2, sortable : true, align:'center',width : 92},
		   				{field : '', title : 'GTS模拟开户来源', colspan: 3, sortable : false, align:'center',width : 60},
		   				{field : '', title : 'GTS真实开户来源', colspan: 3, sortable : false, align:'center',width : 60}
		   			],
		   		    [		                	   	            
		                {field : 'dtDay', title : '登录的广告位', sortable : true,align:'center', width : 70},
		   	            {field : 'partitionfield', title : '内嵌开户页面', sortable : true, align:'center',width : 70},
		   	            {field : 'channel', title : '直播间', sortable : true, align:'center',width : 70},
		                
		   	            {field : 'mobiletype', title : '登录的广告位', sortable : true, align:'center',width : 70},		   	 			
		                {field : 'activeuser', title : '内嵌开户页面', sortable : true, align:'center',width : 70},
		   	            {field : 'newuser', title : '直播间', sortable : true, align:'center',width : 70}
		   	            
		    			] ];
		
		var columns2 = [ 
		      			[
		   				{field : 'dtMonth', title : '日期', rowspan: 2, sortable : true, align:'center',width : 92},
		   				{field : '', title : 'GTS2模拟开户来源', colspan: 3, sortable : false, align:'center',width : 60},
		   				{field : '', title : 'GTS2模拟开户来源', colspan: 3, sortable : false, align:'center',width : 60}
		   			],
		   		    [		                	   	            
		                {field : 'dtDay', title : '内嵌开户页面', sortable : true,align:'center', width : 70},
		   	            {field : 'partitionfield', title : '首页广告位', sortable : true, align:'center',width : 70},
		   	            {field : 'channel', title : '直播间', sortable : true, align:'center',width : 70},
		                
		   	            {field : 'mobiletype', title : '内嵌开户页面', sortable : true, align:'center',width : 70},		   	 			
		                {field : 'activeuser', title : '首页广告位', sortable : true, align:'center',width : 70},
		   	            {field : 'newuser', title : '直播间', sortable : true, align:'center',width : 70}
		   	            
		    			] ];
		
		$('#' + openSourceAnalysis.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns2,
			idField : 'rowKey', // 唯一字段
			sortName : 'dataTime',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='dtMonth']").text("小计：");
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + openSourceAnalysis.dataGridId).datagrid('options');
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
		openSourceAnalysis.getQueryParams();
		common.loadGrid(openSourceAnalysis.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + openSourceAnalysis.searchFormId).form('reset');
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = openSourceAnalysis.getQueryParams();
		var url = BASE_PATH + "DasFlowStatisticsController/exportExcelAverage?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















