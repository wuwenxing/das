/**
 * 公司盈亏图表
 */
$(function() {
	dealprofithourStatistics.init();
});

var dealprofithourStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dealprofithourStatistics.loadDataGrid();
		
		var platformTypeInput = $("#platformTypeInput").val();
		if(null != platformTypeInput && ""!= platformTypeInput){
			if("MT4" == platformTypeInput){
				$("#platformTypeSearch").combobox('setValue',"MT4" );
			}else if("GTS2" == platformTypeInput){
				$("#platformTypeSearch").combobox('setValue',"GTS2" );
			}
		}
		
		common.setEasyUiCss();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDealprofithourPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeInput").val();
		queryParams['detailed'] = "1";
		
		var columns = [ 
			[
				{field : 'exectime', title : '日期',  sortable : true, width : 100},
				{field : 'hour', title : '小时',  sortable : true, width : 100},
				{field : 'companyprofit', title : '公司盈亏', sortable : false, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var companyprofit =  rowData.companyprofit;
						if(!common.isBlank(companyprofit) && companyprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(companyprofit) && companyprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'platform', title : '平台类型', sortable : true, width : 100,
					formatter : function(value, rowData, rowIndex) {
						if(common.isBlank(rowData.platform)){
							return "(全部)";
						}else{
							return rowData.platform;
						}
	 				}}
			]
			];
		$('#' + dealprofithourStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'exectime,hour',
			sortOrder : 'desc,desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='exectime']").text("当日总盈亏：");
				
				dealprofithourStatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dealprofithourStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['detailed'] = "1";
		
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dealprofithourStatistics.getQueryParams();
		common.loadGrid(dealprofithourStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dealprofithourStatistics.searchFormId).form('reset');
		
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
		var queryParams = dealprofithourStatistics.getQueryParams();
		queryParams['sort'] = "exectime,hour";
		queryParams['order'] = "desc,desc";
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDealprofithour?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

