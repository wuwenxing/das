$(function() {
	
});

var p_5_1 = {
	dataGridId : "dataGrid_5_1",
	loadDataGridFlag : false,
	/**
	 * 初始化
	 */
	init : function() {
		if (p_5_1.loadDataGridFlag) {
			p_5_1.find();
		} else {
			p_5_1.loadDataGridFlag = true;
			p_5_1.loadDataGrid();
		}
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function() {
		// 初始参数
		var url = BASE_PATH
				+ 'tradeBordereauxController/findDealprofitdetailPageList';
		var queryParams = {};
		queryParams['startTime'] = $("#startTimeSearch").val();
		queryParams['endTime'] = $("#endTimeSearch").val();

		var columns = [ [
				{
					field : 'grossprofit',
					title : '毛利',
					sortable : false,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'companyprofit',
					title : '公司实现盈亏',
					sortable : false,
					width : 120,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'swap',
					title : '实现利息',
					sortable : true,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'sysclearzero',
					title : '系统清零',
					sortable : true,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'bonus',
					title : '推广贈金',
					sortable : true,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'commission',
					title : '返佣',
					sortable : true,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'adjustfixedamount',
					title : '调整',
					sortable : true,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'floatingprofit',
					title : '公司浮动盈亏',
					sortable : false,
					width : 120,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'goldprofit',
					title : '伦敦金盈亏',
					sortable : false,
					width : 120,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'silverprofit',
					title : '伦敦银盈亏',
					sortable : true,
					width : 120,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'xaucnhprofit',
					title : '人民币金盈亏',
					sortable : true,
					width : 120,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},
				{
					field : 'xagcnhprofit',
					title : '人民币银盈亏',
					sortable : true,
					width : 120,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				}, {
					field : 'goldvolume',
					title : '交易手数(金)',
					sortable : true,
					width : 110,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				}, {
					field : 'silvervolume',
					title : '交易手数(銀)',
					sortable : true,
					width : 110,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				}, {
					field : 'xaucnhvolume',
					title : '交易手数(人民幣金)',
					sortable : false,
					width : 120,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				}, {
					field : 'xagcnhvolume',
					title : '交易手数(人民幣銀)',
					sortable : false,
					width : 120,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				}, {
					field : 'volume',
					title : '交易手数',
					sortable : true,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				}, {
					field : 'closedvolume',
					title : '交易手数（平倉手）',
					sortable : true,
					width : 120,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				}, {
					field : 'useramount',
					title : '交易人次',
					sortable : true,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				}, {
					field : 'dealamount',
					title : '交易次数',
					sortable : true,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				},{
					field : 'handOperHedgeProfitAndLoss',
					title : '手动对冲盈亏',
					sortable : false,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
	 					return common.isAppendColor2(value);
					}
				}, {
					field : 'currency',
					title : '货币种类',
					sortable : true,
					width : 100
				}, {
					field : 'platform',
					title : '平台类型',
					sortable : false,
					width : 100,
					formatter : function(value, rowData, rowIndex) {
						if (common.isBlank(rowData.platform)) {
							return "(全部)";
						} else {
							return rowData.platform;
						}
					}
				} ] ];

		$('#' + p_5_1.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[{field : 'exectime', title : '日期', sortable : true, align:'center',width : 92}]],
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'exectime',
			sortOrder : 'desc',
			showFooter : true,
			collapsible: true,
    		fitColumns:false,
			border : false,
			pageSize : 50,
			onLoadSuccess : function(data){
				common.iFrameHeight();	
			}
		});
	},
	getQueryParams : function() {
		var options = $("#" + p_5_1.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startTime'] = $("#startTimeSearch").val();
		queryParams['endTime'] = $("#endTimeSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find : function() {
		p_5_1.getQueryParams();
		common.loadGrid(p_5_1.dataGridId);
	}

}