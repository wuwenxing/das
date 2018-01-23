$(function() {
	dasWebLandingpageDetail.init();
});

var dasWebLandingpageDetail = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasWebLandingpageDetail.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasWebSiteController/dasWebLandingpageDetailPageList';
		var queryParams = {
		};
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();// 媒介复选框
		queryParams['utmccnChecked'] = $("#utmccnChecked").val();// 系列复选框
		queryParams['utmcctChecked'] = $("#utmcctChecked").val();// 组复选框
		queryParams['utmctrChecked'] = $("#utmctrChecked").val();// 关键字复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();// 设备类型复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
	 			{field : 'deviceType', title : '设备类型', sortable : true, width : 80},
	 			{field : 'channel', title : '渠道', sortable : true, width : 120},
	 			{field : 'utmcsr', title : '来源', sortable : true, width : 100},
	 			{field : 'utmcmd', title : '媒介', sortable : true, width : 100},
	 			{field : 'utmccn', title : '系列', sortable : true, width : 100},
	 			{field : 'utmcct', title : '组', sortable : true, width : 100},
	 			{field : 'utmctr', title : '关键字', sortable : true, width : 120},
	 			{field : 'landingPage', title : 'LandingPage', sortable : true, width : 300},
	 			{field : 'avgTimelen', title : 'LP访问平均时长', sortable : true, width : 110, 
	 				formatter : function(value, rowData, rowIndex) {
						if(!common.isBlank(value)){
							return value + 's';
						}
						return value;
				}},
	 			{field : 'visitCount', title : 'LP访问次数', sortable : true, width : 100},
	 			{field : 'lpAdvisoryCnt', title : 'LP咨询次数', sortable : true, width : 100},
	 			{field : 'advisoryCount', title : '会话内咨询次数', sortable : true, width : 110},
	 			{field : 'realbtnCount', title : 'LP真实按钮次数', sortable : true, width : 110},
	 			{field : 'demobtnCount', title : 'LP模拟按钮次数', sortable : true, width : 110},
	 			{field : 'realCount', title : 'LP真实开户数', sortable : true, width : 100},
	 			{field : 'demoCount', title : 'LP模拟开户数', sortable : true, width : 100},
	 			{field : 'depositCount', title : 'LP激活数', sortable : true, width : 80}
 			] ];
		$('#' + dasWebLandingpageDetail.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[{field : 'dataTime', title : '日期', sortable : true, align:'center', width : 100}]],
			columns : columns,
			idField : 'rowkey', // 唯一字段
			sortName : 'dataTime',
			sortOrder : 'desc',
			collapsible: true,
    		fitColumns:false,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				if($("#deviceTypeChecked").val() == 'true'){
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('showColumn', 'deviceType');
				}else{
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('hideColumn', 'deviceType');
				}
				if($("#channelChecked").val() == 'true'){
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('showColumn', 'channel');
				}else{
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('hideColumn', 'channel');
				}
				if($("#utmcsrChecked").val() == 'true'){
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('showColumn', 'utmcsr');
				}else{
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('hideColumn', 'utmcsr');
				}
				if($("#utmcmdChecked").val() == 'true'){
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('showColumn', 'utmcmd');
				}else{
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('hideColumn', 'utmcmd');
				}
				if($("#utmccnChecked").val() == 'true'){
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('showColumn', 'utmccn');
				}else{
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('hideColumn', 'utmccn');
				}
				if($("#utmcctChecked").val() == 'true'){
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('showColumn', 'utmcct');
				}else{
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('hideColumn', 'utmcct');
				}
				if($("#utmctrChecked").val() == 'true'){
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('showColumn', 'utmctr');
				}else{
					$("#" + dasWebLandingpageDetail.dataGridId).datagrid('hideColumn', 'utmctr');
				}
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + dasWebLandingpageDetail.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['landingPage'] = $("#landingPageSearch").val();// landingPage
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['utmccn'] = $("#utmccnSearch").val();// 系列
		queryParams['utmcct'] = $("#utmcctSearch").val();// 组
		queryParams['utmctr'] = $("#utmctrSearch").val();// 关键字
		queryParams['channel'] = $("#channelSearch").val();// 渠道
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();// 设备类型复选框
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();// 媒介复选框
		queryParams['utmccnChecked'] = $("#utmccnChecked").val();// 系列复选框
		queryParams['utmcctChecked'] = $("#utmcctChecked").val();// 组复选框
		queryParams['utmctrChecked'] = $("#utmctrChecked").val();// 关键字复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框
		
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasWebLandingpageDetail.getQueryParams();
		common.loadGrid(dasWebLandingpageDetail.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasWebLandingpageDetail.searchFormId).form('reset');
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
		var queryParams = dasWebLandingpageDetail.getQueryParams();
		var url = BASE_PATH + "DasWebSiteController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















