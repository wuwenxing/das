$(function() {
	dimAccountBlackList.init();
});

var dimAccountBlackList = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dimAccountBlackList.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DimAccountBlackListController/findDimAccountBlackListPage';
		var queryParams = {
		};
		queryParams['startMarkTime'] = $("#startMarkTimeSearch").datebox('getValue');
		queryParams['endMarkTime'] = $("#endMarkTimeSearch").datebox('getValue');
		var columns = [ [
		        {field : 'accountNo', title : '账户', sortable : true, width : 50},
		 	 	{field : 'companyName', title : '事业部', sortable : true, width : 50},
	 			{field : 'platform', title : '交易平台', sortable : true, width : 50},
	 			{field : 'accountNameCn', title : '客户姓名', sortable : true, width : 50},
	 			{field : 'behavior', title : '行为', sortable : true, width : 50},
	 			{field : 'behaviorDate', title : '行为日期', sortable : true, width : 70},
	 			{field : 'markTime', title : '标记日期', sortable : true, width : 50},
	 			{field : 'mobile', title : '手机号码', sortable : true, width : 50},
	 			{field : 'idCardEncrypt', title : '身份证号', sortable : true, width : 100},
	 			{field : 'createIp', title : 'IP', sortable : true, width : 60},
	 			{field : 'riskFactors', title : '风险原因', sortable : true, width : 120}
 			] ];
		$('#' + dimAccountBlackList.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'behaviorDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + dimAccountBlackList.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['accountNo'] = $("#accountNoSearch").val();// 账户
		queryParams['accountName'] = $("#accountNameCnSearch").val();// 客户姓名
		queryParams['mobile'] = $("#mobileSearch").val();// 手机号码
		queryParams['idCard'] = $("#idCardSearch").val();// 身份证号
		queryParams['createIp'] = $("#createIpSearch").val();// IP
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['startMarkTime'] = $("#startMarkTimeSearch").datebox('getValue');
		queryParams['endMarkTime'] = $("#endMarkTimeSearch").datebox('getValue');
		queryParams['companyId'] = $("#companyIdSearch").combobox('getValue');// 业务权限
		queryParams['platform'] = $("#platformSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dimAccountBlackList.getQueryParams();
		common.loadGrid(dimAccountBlackList.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dimAccountBlackList.searchFormId).form('reset');
		$('#startMarkTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endMarkTimeSearch').datebox({
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
		var queryParams = dimAccountBlackList.getQueryParams();
		var url = BASE_PATH + "DimAccountBlackListController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















