$(function() {
	dasBehaviorEventChannelEffect.init();
});

var dasBehaviorEventChannelEffect = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		dasBehaviorEventChannelEffect.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasWebSiteController/dasBehaviorEventChannelEffectPageList';
		var queryParams = {
		};
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();// 媒介复选框
		queryParams['utmccnChecked'] = $("#utmccnChecked").val();// 系列复选框
		queryParams['utmcctChecked'] = $("#utmcctChecked").val();// 组复选框
		queryParams['utmctrChecked'] = $("#utmctrChecked").val();// 关键字复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();// 设备类型复选框
		queryParams['channelGrpChecked'] = $("#channelGrpChecked").val();// 渠道分组复选框
		queryParams['channelLevelChecked'] = $("#channelLevelChecked").val();// 渠道分级复选框
		queryParams['platformNameChecked'] = $("#platformNameChecked").val();// 马甲包复选框
		queryParams['platformVersionChecked'] = $("#platformVersionChecked").val();// 版本号复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		var columns = [ [
		        {field : 'weeks', title : '周数', sortable : true, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var dateTime = rowData.dateTime
	 					return common.getWeekNumber(new Date(dateTime));
				}},
	 			{field : 'deviceType', title : '设备类型', sortable : true, width : 80},
	 			{field : 'channelGrp', title : '渠道分组', sortable : true, width : 80},
	 			{field : 'channelLevel', title : '渠道分级', sortable : true, width : 80},
	 			{field : 'channel', title : '渠道', sortable : true, width : 120},
	 			{field : 'utmcsr', title : '来源', sortable : true, width : 100},
	 			{field : 'utmcmd', title : '媒介', sortable : true, width : 100},
	 			{field : 'utmccn', title : '系列', sortable : true, width : 100},
	 			{field : 'utmcct', title : '组', sortable : true, width : 100},
	 			{field : 'utmctr', title : '关键字', sortable : true, width : 100},
	 			{field : 'landingPage', title : 'LandingPage', sortable : true, width : 300},
	 			{field : 'platformVersion', title : '版本号', sortable : false, width : 70},
	 			{field : 'platformName', title : '马甲包', sortable : false, width : 70},
	 			{field : 'eventAdvisoryCount', title : '进入咨询设备数', sortable : true, width : 110},
	 			{field : 'eventDemoCount', title : '进入模拟开户设备数', sortable : true, width : 130},
	 			{field : 'eventRealCount', title : '进入真实开户设备数', sortable : true, width : 130},
	 			{field : 'eventActiveCount', title : '进入入金流程设备数', sortable : true, width : 130},
	 			{field : 'demoCount', title : '模拟开户数', sortable : true, width : 100},
	 			{field : 'realCount', title : '真实开户数', sortable : true, width : 100},
	 			{field : 'activeCount', title : '入金账户数', sortable : true, width : 100},
	 			{field : 'demoRate', title : '模拟开户率', sortable : true, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
						if(!common.isBlank(value)){
							return common.toDecimal(value*100) + '%';
						}
						return value;
				}},
	 			{field : 'realRate', title : '真实开户率', sortable : true, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
						if(!common.isBlank(value)){
							return common.toDecimal(value*100) + '%';
						}
						return value;
				}},
	 			{field : 'activeRate', title : '入金率', sortable : true, width : 80, 
	 				formatter : function(value, rowData, rowIndex) {
						if(!common.isBlank(value)){
							return common.toDecimal(value*100) + '%';
						}
						return value;
				}}
 			] ];
		$('#' + dasBehaviorEventChannelEffect.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[
			    {field : 'dateTime', title : '日期', sortable : true, align:'center', width : 100}
			]],
			columns : columns,
			idField : 'rowkey', // 唯一字段
			sortName : 'dateTime',
			sortOrder : 'desc',
			collapsible: true,
    		fitColumns:false,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				var reportType = $("#reportTypeSearch").combobox('getValue');
				if(reportType == 'days' || reportType == 'months'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'weeks');
				}else if(reportType == 'weeks'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'weeks');
				}
				if($("#deviceTypeChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'deviceType');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'deviceType');
				}
				if($("#platformNameChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'platformName');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'platformName');
				}
				if($("#platformVersionChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'platformVersion');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'platformVersion');
				}
				if($("#channelChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'channel');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'channel');
				}
				if($("#channelGrpChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'channelGrp');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'channelGrp');
				}
				if($("#channelLevelChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'channelLevel');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'channelLevel');
				}
				if($("#utmcsrChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'utmcsr');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'utmcsr');
				}
				if($("#utmcmdChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'utmcmd');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'utmcmd');
				}
				if($("#utmccnChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'utmccn');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'utmccn');
				}
				if($("#utmcctChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'utmcct');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'utmcct');
				}
				if($("#utmctrChecked").val() == 'true'){
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('showColumn', 'utmctr');
				}else{
					$("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('hideColumn', 'utmctr');
				}
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + dasBehaviorEventChannelEffect.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['landingPage'] = $("#landingPageSearch").val();// landingPage
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');// 设备类型
		queryParams['platformName'] = $("#platformNameSearch").val();// 马甲包
		queryParams['platformVersion'] = $("#platformVersionSearch").val();// 版本号
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['utmccn'] = $("#utmccnSearch").val();// 系列
		queryParams['utmcct'] = $("#utmcctSearch").val();// 组
		queryParams['utmctr'] = $("#utmctrSearch").val();// 关键字
		queryParams['channel'] = $("#channelSearch").val();// 渠道
		queryParams['channelGrp'] = $("#channelGrpSearch").val();// 渠道分组
		queryParams['channelLevel'] = $("#channelLevelSearch").val();// 渠道分级
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();// 设备类型复选框
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();// 媒介复选框
		queryParams['utmccnChecked'] = $("#utmccnChecked").val();// 系列复选框
		queryParams['utmcctChecked'] = $("#utmcctChecked").val();// 组复选框
		queryParams['utmctrChecked'] = $("#utmctrChecked").val();// 关键字复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框
		queryParams['channelGrpChecked'] = $("#channelGrpChecked").val();// 渠道分组复选框
		queryParams['channelLevelChecked'] = $("#channelLevelChecked").val();// 渠道分级复选框
		queryParams['platformNameChecked'] = $("#platformNameChecked").val();// 马甲包复选框
		queryParams['platformVersionChecked'] = $("#platformVersionChecked").val();// 版本号复选框

		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasBehaviorEventChannelEffect.getQueryParams();
		common.loadGrid(dasBehaviorEventChannelEffect.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasBehaviorEventChannelEffect.searchFormId).form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$("#deviceTypeChecked").val("false");
		$("#utmcsrChecked").val("false");
		$("#utmcmdChecked").val("false");
		$("#utmccnChecked").val("false");
		$("#utmcctChecked").val("false");
		$("#utmctrChecked").val("false");
		$("#channelChecked").val("false");
		$("#channelGrpChecked").val("false");
		$("#channelLevelChecked").val("false");
		$("#platformNameChecked").val("false");
		$("#platformVersionChecked").val("false");
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = dasBehaviorEventChannelEffect.getQueryParams();
		var url = BASE_PATH + "DasWebSiteController/eventChannelEffectExportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 统计类型change事件
	 */
	setDateboxAttr: function(newValue,oldValue){
		if(newValue == 'days'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$("#startTimeSearch").datebox('setValue', $("#yesterdayStart").val());
			$("#endTimeSearch").datebox('setValue', $("#dayEnd").val());
		}else if(newValue == 'weeks'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$("#startTimeSearch").datebox('setValue', $("#halfaWeek").val());
			$("#endTimeSearch").datebox('setValue', $("#dayEnd").val());
		}else if(newValue == 'months'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$("#startTimeSearch").datebox('setValue', $("#lessOneMonths").val());
			$("#endTimeSearch").datebox('setValue', $("#monthEnd").val());
		}
		common.setEasyUiCss();
	}
	
}




















