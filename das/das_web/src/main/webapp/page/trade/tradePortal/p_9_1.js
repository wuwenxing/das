$(function() {

});

/**
 * 开户及存款总结
 */
var p_9_1 = {
	dataGridId : "dataGrid_9_1",
	loadDataGridFlag : false,
	/**
	 * 初始化
	 */
	init : function() {
		if (p_9_1.loadDataGridFlag) {
			p_9_1.find();
		} else {
			p_9_1.loadDataGridFlag = true;
			p_9_1.loadDataGrid();
		}
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function() {
		// 初始参数
		var url = BASE_PATH + 'TradeController/openAccountAndDepositSummary';
		var queryParams = {};
		queryParams['startTime'] = $("#startTimeSearch").val();
		queryParams['endTime'] = $("#endTimeSearch").val();
		var columns = [ [
	        {field : 'sumactdayall', title : '新開激活總數(A)', rowspan: 2, sortable : false, width : 110,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
 			{field : 'sumactday', title : '即日激活賬戶(當天A)', rowspan: 2, sortable : false, width : 135,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
 			{field : 'sumactall', title : '累計即日激活帳戶量(當天A)', rowspan: 2, sortable : false, width : 180,
 				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
 				}},
 			{field : 'sumopenallreal', title : '累計所有真實開戶量(當天A)', rowspan: 2, sortable : false, width : 180,
 				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
 				}},
 			{field : 'sumopenalldemo', title : '新開模擬賬戶', rowspan: 2, sortable : false, width : 100,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
 			{field : 'sumnewaccountday', title : '當天新客戶存款', rowspan: 2, sortable : false, width : 110,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
 			{field : 'equitymdeposit', title : '平台淨存款', rowspan: 2, sortable : false, width : 100,
 				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
 				}},
 				
 			{field : '', title : '平台存款', colspan: 3, sortable : false, width : 100},
 			{field : 'sumwithdraw', title : '平台取款', rowspan: 2, sortable : false, width : 100,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
 			{field : 'balance', title : '結餘', rowspan: 2, sortable : false, width : 160,
				formatter : function(value, rowData, rowIndex) {
 					return common.isAppendColor2(value);
				}},
				
	 		{field : '', title : '客户服务', colspan: 3, sortable : false, width : 340}
		],
		[
			{field : 'summdeposit', title : '總計', sortable : false, width : 100,
				formatter : function(value, rowData, rowIndex) {
					return common.isAppendColor2(value);
				}},
			{field : 'onlinepayment', title : '在線支付', sortable : false, width : 100,
				formatter : function(value, rowData, rowIndex) {
					return common.isAppendColor2(value);
				}},
			{field : 'reimbursement', title : '收還款項', sortable : false, width : 100,
				formatter : function(value, rowData, rowIndex) {
					return common.isAppendColor2(value);
				}},
			{field : 'advisoryCount', title : '客戶咨詢量', sortable : false, width : 100,
				formatter : function(value, rowData, rowIndex) {
					return common.isAppendColor2(value);
				}},
			{field : 'advisoryCustomerCount', title : '客服電話回訪量', sortable : false, width : 120,
				formatter : function(value, rowData, rowIndex) {
					return common.isAppendColor2(value);
				}},
			{field : 'customerPhoneCount', title : '取得客戶電話數目', sortable : false, width : 120,
				formatter : function(value, rowData, rowIndex) {
					return common.isAppendColor2(value);
				}}
		]];
		$('#' + p_9_1.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[
			    {field : 'datetime', title : '日期', align:'center', sortable : false, width : 100}
			]],
			columns : columns,
			idField : 'datetime', // 唯一字段
			sortName : 'datetime',
			sortOrder : 'asc',
			showFooter : true,
			collapsible: true,
    		fitColumns:false,
			border : false,
			pagination : false, // 分页控件
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				common.iFrameHeight();
			}
		});
	},
	getQueryParams : function() {
		var options = $("#" + p_9_1.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startTime'] = $("#startTimeSearch").val();
		queryParams['endTime'] = $("#endTimeSearch").val();
		queryParams['platformType'] = $("#platformTypeSearch_1").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find : function() {
		p_9_1.getQueryParams();
		common.loadGrid(p_9_1.dataGridId);
	}
}