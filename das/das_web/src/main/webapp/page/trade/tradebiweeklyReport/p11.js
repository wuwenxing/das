$(function() {
	//p11.init();
});

var p11 = {
	dataGridId: "dataGrid_11",
	loadDataGridFlag : false,
	/**
	 * 初始化
	 */
	init : function() {
		if (p11.loadDataGridFlag) {
			p11.find();
		} else {
			p11.loadDataGridFlag = true;
			p11.loadDataGrid();
		}
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function() {
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDealprofitdetailMT4AndGts2PageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var platformType = "";//$("#platformTypeSearch").combobox('getValue');
		queryParams['platformType'] = platformType;
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
			   							return parseFloat(rowData.mt4volumeaxucnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'mt4volumeaxg', title : 'MT4伦敦银平仓手数', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxg)){
			   							return parseFloat(rowData.mt4volumeaxg) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'mt4volumeaxgcnh', title : 'MT4人民币银平仓手数',  sortable : true, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxgcnh)){
			   							return parseFloat(rowData.mt4volumeaxgcnh) ;
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
			   							return parseFloat(rowData.gts2volumeaxu) ;
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
			   							return parseFloat(rowData.gts2volumeaxgcnh) ;
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
			   							return parseFloat(rowData.mt4volumeaxu) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'mt4volumeaxucnh', title : 'MT4人民币金平仓手数', sortable : true, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxucnh)){
			   							return parseFloat(rowData.mt4volumeaxucnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'mt4volumeaxg', title : 'MT4伦敦银平仓手数', sortable : true, width : 120,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxg)){
			   							return parseFloat(rowData.mt4volumeaxg) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'mt4volumeaxgcnh', title : 'MT4人民币银平仓手数',  sortable : true, width : 130,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.mt4volumeaxgcnh)){
			   							return parseFloat(rowData.mt4volumeaxgcnh) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},
			   				{field : 'gts2volumeaxu', title : 'GTS2伦敦金平仓手数', sortable : true, width : 125,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxu)){
			   							return parseFloat(rowData.gts2volumeaxu) ;
			   						}else{
			   							return "";
			   						}
			   	 			}},				
			   				{field : 'gts2volumeaxucnh', title : 'GTS2人民币金平仓手数', sortable : true, width : 135,
			   					formatter : function(value, rowData, rowIndex) {
			   						if(!common.isBlank(rowData.gts2volumeaxucnh)){
			   							return parseFloat(rowData.gts2volumeaxucnh) ;
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
			   							return parseFloat(rowData.gts2volumeaxgcnh) ;
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
		
		$('#' + p11.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'exectime',
			sortOrder : 'asc',
            showFooter: true,
			border : false,
            rownumbers:false,
            collapsible: true,
    		fitColumns:false,
			rowStyler:function(rowIndex,rowData){  
				if(!common.isBlank(rowData.weekCode)){  
		        	var weekCode = rowData.weekCode;
		        	if(weekCode ==6 || weekCode ==0){
		        		//return 'background-color:#6293BB;color:#fff;font-weight:bold;font-size:12px;'; 
		        		return 'background-color:#F2DCDB;color:#000000;';
		        	}
		        }
		    }
		});
	},
	getQueryParams : function() {
		var options = $("#" + p11.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var platformType = "";//$("#platformTypeSearch").combobox('getValue');
		queryParams['platformType'] = platformType;
		
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find : function() {
		p11.getQueryParams();
		common.loadGrid(p11.dataGridId);
	}
	
}