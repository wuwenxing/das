/**
 * 交易记录总表
 */
$(function() {
	dealprofitdetailStatistics.init();
});

var dealprofitdetailStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dealprofitdetailStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDealprofitdetailPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ 
			[
				/*{field : 'exectime', title : '日期',  sortable : true, width : 100},*/
				{field : 'grossprofit', title : '毛利', sortable : false, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var grossprofit =  rowData.grossprofit;
						if(!common.isBlank(grossprofit) && grossprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(grossprofit) && grossprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'companyprofit', title : '公司实现盈亏', sortable : false, width : 120,
	 				styler:function(value, rowData, rowIndex){
						var companyprofit =  rowData.companyprofit;
						if(!common.isBlank(companyprofit) && companyprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(companyprofit) && companyprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'swap', title : '实现利息', sortable : true, width : 100,
	 				styler:function(value, rowData, rowIndex){
						var swap =  rowData.swap;
						if(!common.isBlank(swap) && swap > 0){
							return 'color:red;';					
						}else if(!common.isBlank(swap) && swap < 0){
							return 'color:green;';
						}
				}},
				{field : 'sysclearzero', title : '系统清零', sortable : true, width : 100},
				{field : 'bonus', title : '推广贈金', sortable : true, width : 100},
				{field : 'commission', title : '返佣', sortable : true, width : 100},				
				{field : 'adjustfixedamount', title : '调整',  sortable : true, width : 100},
				{field : 'floatingprofit', title : '公司浮动盈亏', sortable : false, width : 120,
	 				styler:function(value, rowData, rowIndex){
						var floatingprofit =  rowData.floatingprofit;
						if(!common.isBlank(floatingprofit) && floatingprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(floatingprofit) && floatingprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'goldprofit', title : '伦敦金盈亏', sortable : false, width : 120,
	 				styler:function(value, rowData, rowIndex){
						var goldprofit =  rowData.goldprofit;
						if(!common.isBlank(goldprofit) && goldprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(goldprofit) && goldprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'silverprofit', title : '伦敦银盈亏', sortable : true, width : 120,
	 				styler:function(value, rowData, rowIndex){
						var silverprofit =  rowData.silverprofit;
						if(!common.isBlank(silverprofit) && silverprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(silverprofit) && silverprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'xaucnhprofit', title : '人民币金盈亏', sortable : true, width : 120,
	 				styler:function(value, rowData, rowIndex){
						var xaucnhprofit =  rowData.xaucnhprofit;
						if(!common.isBlank(xaucnhprofit) && xaucnhprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(xaucnhprofit) && xaucnhprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'xagcnhprofit', title : '人民币银盈亏', sortable : true, width : 120,
	 				styler:function(value, rowData, rowIndex){
						var xagcnhprofit =  rowData.xagcnhprofit;
						if(!common.isBlank(xagcnhprofit) && xagcnhprofit > 0){
							return 'color:red;';					
						}else if(!common.isBlank(xagcnhprofit) && xagcnhprofit < 0){
							return 'color:green;';
						}
				}},
				{field : 'goldvolume', title : '交易手数(金)', sortable : true, width : 110},				
				{field : 'silvervolume', title : '交易手数(銀)',  sortable : true, width : 110},
				{field : 'xaucnhvolume', title : '交易手数(人民幣金)', sortable : false, width : 120},
				{field : 'xagcnhvolume', title : '交易手数(人民幣銀)', sortable : false, width : 120},
				{field : 'volume', title : '交易手数', sortable : true, width : 100},
				{field : 'closedvolume', title : '交易手数（平倉手）', sortable : true, width : 120},
				{field : 'useramount', title : '交易人次', sortable : true, width : 100},
				{field : 'dealamount', title : '交易次数', sortable : true, width : 100},	
				{field : 'handOperHedgeProfitAndLoss', title : '手动对冲盈亏', sortable : false, width : 100},
				{field : 'currency', title : '货币种类',  sortable : true, width : 100},
				{field : 'platform', title : '平台类型', sortable : false, width : 100,
					formatter : function(value, rowData, rowIndex) {
						if(common.isBlank(rowData.platform)){
							return "(全部)";
						}else{
							return rowData.platform;
						}
	 				}}
			]
			];
		$('#' + dealprofitdetailStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[{field : 'exectime', title : '日期', sortable : true, align:'center',width : 92}]],
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'exectime',
			sortOrder : 'desc',
            showFooter: true,
            collapsible: true,
    		fitColumns:false,
			border : false,
			pageSize : 50,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='exectime']:eq(0)").text("总数：");
				$("div.datagrid-footer [class$='exectime']:eq(1)").text("平均：");
				
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dealprofitdetailStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dealprofitdetailStatistics.getQueryParams();
		common.loadGrid(dealprofitdetailStatistics.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dealprofitdetailStatistics.searchFormId).form('reset');
		
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
		var queryParams = dealprofitdetailStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDealprofitdetail?type=0";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

