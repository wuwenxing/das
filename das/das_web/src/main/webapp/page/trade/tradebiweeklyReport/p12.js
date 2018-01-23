$(function() {
	//p12.init();
});

var p12 = {
	dataGridId: "dataGrid_12",
	loadDataGridFlag : false,
	/**
	 * 初始化
	 */
	init : function() {
		if (p12.loadDataGridFlag) {
			p12.find();
		} else {
			p12.loadDataGridFlag = true;
			p12.loadDataGrid();
		}
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function() {
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findDealcategoryPageList';
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
			   							return parseFloat(rowData.mt4volumeaxgcnh);
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
		
		$('#' + p12.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'exectime',
			sortOrder : 'asc',
            showFooter: true,
			border : false,
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
		var options = $("#" + p12.dataGridId).datagrid('options');
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
		p12.getQueryParams();
		common.loadGrid(p12.dataGridId);
	}
	
}