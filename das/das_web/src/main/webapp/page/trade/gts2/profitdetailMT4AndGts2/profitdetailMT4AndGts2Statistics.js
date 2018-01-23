$(function() {
	profitdetailMT4AndGts2Statistics.init();
});

var profitdetailMT4AndGts2Statistics = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		profitdetailMT4AndGts2Statistics.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDealprofitdetailMT4AndGts2PageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		
		var columns = profitdetailMT4AndGts2Statistics.getColumns();
		
		$('#' + profitdetailMT4AndGts2Statistics.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'exectime',
			sortOrder : 'desc',
            showFooter: true,
            rownumbers:false,
            collapsible: true,
    		fitColumns:false,
			onDblClickRow : function(rowIndex, rowData) {
			},
			rowStyler:function(rowIndex,rowData){  
				if(!common.isBlank(rowData.weekCode)){  
		        	var weekCode = rowData.weekCode;
		        	if(weekCode ==6 || weekCode ==0){
		        		//return 'background-color:#6293BB;color:#fff;font-weight:bold;font-size:12px;'; 
		        		return 'background-color:#F2DCDB;color:#000000;';
		        	}
		        }
	        },
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='exectime']:eq(0)").text("小计：");
				
				//profitdetailMT4AndGts2StatisticsChart.loadChartData();
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + profitdetailMT4AndGts2Statistics.dataGridId).datagrid('options').queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		profitdetailMT4AndGts2Statistics.getQueryParams();
		//common.loadGrid(profitdetailMT4AndGts2Statistics.dataGridId);
		
		$('#' + profitdetailMT4AndGts2Statistics.dataGridId).datagrid({columns:profitdetailMT4AndGts2Statistics.getColumns()});
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + profitdetailMT4AndGts2Statistics.searchFormId).form('reset');
		
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
		var queryParams = profitdetailMT4AndGts2Statistics.getQueryParams();
		queryParams['sort'] = "exectime";
		queryParams['order'] = "desc";
		var url = BASE_PATH + "tradeBordereauxController/exportExcelDealprofitdetailMT4AndGts2?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	
	getColumns:function(){
		var platformType = $("#platformTypeSearch").combobox('getValue');
		var columns;
		if("MT4"==platformType){
			 columns = [ 
			   			[
                           {field : 'rowkey', title : '',  sortable : false, width : 40},
			   				{field : 'exectime', title : '日期',  sortable : true, width : 90},
			   				{field : 'week', title : '星期',  sortable : true, width : 80},
			   				{field : 'sysclearzero', title : '系统清零', sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.sysclearzero)){
			   							return parseFloat(rowData.sysclearzero) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'companyprofit', title : '公司实现盈亏', sortable : false, width : 100},
			   				{field : 'mt4volumeaxu', title : 'MT4伦敦金平仓手数', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxu)){
			   							return parseFloat(rowData.mt4volumeaxu);
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'mt4volumeaxucnh', title : 'MT4人民币金平仓手数', sortable : true, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxucnh)){
			   							return parseFloat(rowData.mt4volumeaxucnh);
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'mt4volumeaxg', title : 'MT4伦敦银平仓手数', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxg)){
			   							return parseFloat(rowData.mt4volumeaxg);
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'mt4volumeaxgcnh', title : 'MT4人民币银平仓手数',  sortable : true, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxgcnh)){
			   							return parseFloat(rowData.mt4volumeaxgcnh);
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'commission', title : '返佣', sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.commission)){
			   							return parseFloat(rowData.commission) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},	
			   				{field : 'adjustfixedamount', title : '调整',  sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.adjustfixedamount)){
			   							return parseFloat(rowData.adjustfixedamount) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},		
			   				{field : 'cgse', title : '编码费', sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.cgse)){
			   							return parseFloat(rowData.cgse) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},		
			   				{field : 'swap', title : '实现利息', sortable : true, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.swap)){
			   							return parseFloat(rowData.swap) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},	
			   				{field : 'closedvolume', title : '平倉笔数', sortable : true, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.closedvolume)){
			   							return parseFloat(rowData.closedvolume) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'margin', title : '建仓保证金', sortable : false, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.margin)){
			   							return parseFloat(rowData.margin) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'net', title : 'NET', sortable : false, width : 80},
			   				{field : 'sum', title : 'SUM', sortable : false, width : 80},
			   				{field : 'platform', title : '平台类型', sortable : false, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(common.isBlank(rowData.platform)){
			   							return "(全部)";
			   						}else{
			   							return rowData.platform;
			   						}
			   	 			}}
			   			]
			   			];
		}else if("GTS2"==platformType){
			 columns = [ 
			   			[
                           {field : 'rowkey', title : '',  sortable : false, width : 40},
			   				{field : 'exectime', title : '日期',  sortable : true, width : 90},
			   				{field : 'week', title : '星期',  sortable : true, width : 80},
			   				{field : 'sysclearzero', title : '系统清零', sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.sysclearzero)){
			   							return parseFloat(rowData.sysclearzero) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'companyprofit', title : '公司实现盈亏', sortable : false, width : 100},			   				
			   				{field : 'gts2volumeaxu', title : 'GTS2伦敦金平仓手数', sortable : true, width : 125,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxu)){
			   							return parseFloat(rowData.gts2volumeaxu);
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'gts2volumeaxucnh', title : 'GTS2人民币金平仓手数', sortable : true, width : 135,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxucnh)){
			   							return parseFloat(rowData.gts2volumeaxucnh);
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'gts2volumeaxg', title : 'GTS2伦敦银平仓手数', sortable : true, width : 125,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxg)){
			   							return parseFloat(rowData.gts2volumeaxg);
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'gts2volumeaxgcnh', title : 'GTS2人民币银平仓手数',  sortable : true, width : 135,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxgcnh)){
			   							return parseFloat(rowData.gts2volumeaxgcnh);
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'commission', title : '返佣', sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.commission)){
			   							return parseFloat(rowData.commission) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},	
			   				{field : 'adjustfixedamount', title : '调整',  sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.adjustfixedamount)){
			   							return parseFloat(rowData.adjustfixedamount) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},		
			   				{field : 'cgse', title : '编码费', sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.cgse)){
			   							return parseFloat(rowData.cgse) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},		
			   				{field : 'swap', title : '实现利息', sortable : true, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.swap)){
			   							return parseFloat(rowData.swap) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},	
			   				{field : 'closedvolume', title : '平倉笔数', sortable : true, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.closedvolume)){
			   							return parseFloat(rowData.closedvolume) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'margin', title : '建仓保证金', sortable : false, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.margin)){
			   							return parseFloat(rowData.margin) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'net', title : 'NET', sortable : false, width : 90},
			   				{field : 'sum', title : 'SUM', sortable : false, width : 80},
			   				{field : 'platform', title : '平台类型', sortable : false, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(common.isBlank(rowData.platform)){
			   							return "(全部)";
			   						}else{
			   							return rowData.platform;
			   						}
			   	 			}}
			   			]
			   			];
		}else{
			 columns = [ 
			   			[
                           {field : 'rowkey', title : '',  sortable : false, width : 40},
			   				{field : 'exectime', title : '日期',  sortable : true, width : 90},
			   				{field : 'week', title : '星期',  sortable : true, width : 80},
			   				{field : 'sysclearzero', title : '系统清零', sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.sysclearzero)){
			   							return parseFloat(rowData.sysclearzero) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'companyprofit', title : '公司实现盈亏', sortable : false, width : 100},
			   				{field : 'mt4volumeaxu', title : 'MT4伦敦金平仓手数', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxu)){
			   							return parseFloat(rowData.mt4volumeaxu);
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'mt4volumeaxucnh', title : 'MT4人民币金平仓手数', sortable : true, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxucnh)){
			   							return parseFloat(rowData.mt4volumeaxucnh);
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'mt4volumeaxg', title : 'MT4伦敦银平仓手数', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxg)){
			   							return parseFloat(rowData.mt4volumeaxg);
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'mt4volumeaxgcnh', title : 'MT4人民币银平仓手数',  sortable : true, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxgcnh)){
			   							return parseFloat(rowData.mt4volumeaxgcnh);
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'gts2volumeaxu', title : 'GTS2伦敦金平仓手数', sortable : true, width : 125,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxu)){
			   							return parseFloat(rowData.gts2volumeaxu);
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'gts2volumeaxucnh', title : 'GTS2人民币金平仓手数', sortable : true, width : 135,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxucnh)){
			   							return parseFloat(rowData.gts2volumeaxucnh);
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'gts2volumeaxg', title : 'GTS2伦敦银平仓手数', sortable : true, width : 125,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxg)){
			   							return parseFloat(rowData.gts2volumeaxg) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'gts2volumeaxgcnh', title : 'GTS2人民币银平仓手数',  sortable : true, width : 135,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxgcnh)){
			   							return parseFloat(rowData.gts2volumeaxgcnh);
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'commission', title : '返佣', sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.commission)){
			   							return parseFloat(rowData.commission) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},	
			   				{field : 'adjustfixedamount', title : '调整',  sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.adjustfixedamount)){
			   							return parseFloat(rowData.adjustfixedamount) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},		
			   				{field : 'cgse', title : '编码费', sortable : true, width : 80,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.cgse)){
			   							return parseFloat(rowData.cgse) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},		
			   				{field : 'swap', title : '实现利息', sortable : true, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.swap)){
			   							return parseFloat(rowData.swap) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},	
			   				{field : 'closedvolume', title : '平倉笔数', sortable : true, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.closedvolume)){
			   							return parseFloat(rowData.closedvolume) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'margin', title : '建仓保证金', sortable : false, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.margin)){
			   							return parseFloat(rowData.margin) * -1;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'net', title : 'NET', sortable : false, width : 80},
			   				{field : 'sum', title : 'SUM', sortable : false, width : 80},
			   				{field : 'platform', title : '平台类型', sortable : false, width : 90,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(common.isBlank(rowData.platform)){
			   							return "(全部)";
			   						}else{
			   							return rowData.platform;
			   						}
			   	 			}}
			   			]
			   			];
		}
		return columns;
	}
}

