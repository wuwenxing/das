$(function() {
	dealcategoryStatistics.init();
});

var dealcategoryStatistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dealcategoryStatistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDealcategoryPageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		
		var columns = dealcategoryStatistics.getColumns();
		
		$('#' + dealcategoryStatistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'exectime',
			sortOrder : 'desc',
            showFooter: true,
            collapsible: true,
    		fitColumns:false,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='exectime']:eq(0)").text("小计：");
				
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dealcategoryStatistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dealcategoryStatistics.getQueryParams();
		//common.loadGrid(dealcategoryStatistics.dataGridId);
		
		$('#' + dealcategoryStatistics.dataGridId).datagrid({columns:dealcategoryStatistics.getColumns()});
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dealcategoryStatistics.searchFormId).form('reset');
		
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
		var queryParams = dealcategoryStatistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDealcategory?type=0";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	
	getColumns :function(){
		var platformType = $("#platformTypeSearch").combobox('getValue');
		var columns;
		if("MT4"==platformType){
			columns = [ 
			   			[
			   				{field : 'exectime', title : '日期',  sortable : true, width : 90},
							{field : 'mt4volumeaxu', title : 'MT4倫敦金平倉手數', sortable : false, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxu)){
			   							return parseFloat(rowData.mt4volumeaxu) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'mt4volumeaxucnh', title : 'MT4人民幣金平倉手數', sortable : false, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxucnh)){
			   							return parseFloat(rowData.mt4volumeaxucnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'mt4volumeaxg', title : 'MT4倫敦銀平倉手數', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxg)){
			   							return parseFloat(rowData.mt4volumeaxg) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'mt4volumeaxgcnh', title : 'MT4人民幣銀平倉手數', sortable : true, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxgcnh)){
			   							return parseFloat(rowData.mt4volumeaxgcnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},						
							{field : 'mt4floatingprofit', title : 'MT4浮盈', sortable : false, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4floatingprofit)){
			   							return parseFloat(rowData.mt4floatingprofit) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'mt4balance', title : 'MT4結餘', sortable : true, width : 120},
							{field : 'mt4useramount', title : 'MT4人數', sortable : true, width : 80},				
							{field : 'mt4dealamount', title : 'MT4次數', sortable : false, width : 80},
							{field : 'currency', title : '货币种类',  sortable : true, width : 80}
			   			]
			   			];
		}else if("GTS2"==platformType){
			columns = [ 
			   			[
			   				{field : 'exectime', title : '日期',  sortable : true, width : 90},
							{field : 'gts2volumeaxu', title : 'GTS2倫敦金平倉手數', sortable : true, width : 125,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxu)){
			   							return parseFloat(rowData.gts2volumeaxu) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'gts2volumeaxucnh', title : 'GTS2人民幣金平倉手數', sortable : true, width : 135,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxucnh)){
			   							return parseFloat(rowData.gts2volumeaxucnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},				
							{field : 'gts2volumeaxg', title : 'GTS2倫敦銀平倉手數',  sortable : true, width : 125,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxg)){
			   							return parseFloat(rowData.gts2volumeaxg) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'gts2volumeaxgcnh', title : 'GTS2人民幣銀平倉手數', sortable : false, width : 135,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxgcnh)){
			   							return parseFloat(rowData.gts2volumeaxgcnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'gts2floatingprofit', title : 'GTS2浮盈', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2floatingprofit)){
			   							return parseFloat(rowData.gts2floatingprofit) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'gts2balance', title : 'GTS2結餘', sortable : true, width : 120},
							{field : 'gts2useramount', title : 'GTS2人數',  sortable : true, width : 80},
							{field : 'gts2dealamount', title : 'GTS2次數', sortable : false, width : 80},				
							{field : 'currency', title : '货币种类',  sortable : true, width : 80}
			   			]
			   			];
		}else{
			columns = [ 
			   			[
			   				{field : 'exectime', title : '日期',  sortable : true, width : 90},
							{field : 'mt4volumeaxu', title : 'MT4倫敦金平倉手數', sortable : false, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxu)){
			   							return parseFloat(rowData.mt4volumeaxu) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'mt4volumeaxucnh', title : 'MT4人民幣金平倉手數', sortable : false, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxucnh)){
			   							return parseFloat(rowData.mt4volumeaxucnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'mt4volumeaxg', title : 'MT4倫敦銀平倉手數', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxg)){
			   							return parseFloat(rowData.mt4volumeaxg) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'mt4volumeaxgcnh', title : 'MT4人民幣銀平倉手數', sortable : true, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxgcnh)){
			   							return parseFloat(rowData.mt4volumeaxgcnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'gts2volumeaxu', title : 'GTS2倫敦金平倉手數', sortable : true, width : 125,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxu)){
			   							return parseFloat(rowData.gts2volumeaxu) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'gts2volumeaxucnh', title : 'GTS2人民幣金平倉手數', sortable : true, width : 135,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxucnh)){
			   							return parseFloat(rowData.gts2volumeaxucnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},				
							{field : 'gts2volumeaxg', title : 'GTS2倫敦銀平倉手數',  sortable : true, width : 125,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxg)){
			   							return parseFloat(rowData.gts2volumeaxg) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'gts2volumeaxgcnh', title : 'GTS2人民幣銀平倉手數', sortable : false, width : 135,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxgcnh)){
			   							return parseFloat(rowData.gts2volumeaxgcnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'mt4floatingprofit', title : 'MT4浮盈', sortable : false, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4floatingprofit)){
			   							return parseFloat(rowData.mt4floatingprofit) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'gts2floatingprofit', title : 'GTS2浮盈', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2floatingprofit)){
			   							return parseFloat(rowData.gts2floatingprofit) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
							{field : 'mt4balance', title : 'MT4結餘', sortable : true, width : 120},
							{field : 'gts2balance', title : 'GTS2結餘', sortable : true, width : 120},
							{field : 'mt4useramount', title : 'MT4人數', sortable : true, width : 80},				
							{field : 'gts2useramount', title : 'GTS2人數',  sortable : true, width : 80},
							{field : 'mt4dealamount', title : 'MT4次數', sortable : false, width : 80},
							{field : 'gts2dealamount', title : 'GTS2次數', sortable : false, width : 80},				
							{field : 'currency', title : '货币种类',  sortable : true, width : 90}
			   			]
			   			];
		}
		return columns;
	}
}

