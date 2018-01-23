$(function() {
	customerQualityClient.init();
});

var customerQualityClient = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		$("#dateTimeTd").hide();
		
		$("select[multiple='multiple']").multiselect({
			noneSelectedText: "---请选择---", 
			selectedList: 10 
		}).multiselectfilter();
		$(".ui-multiselect").css("width", "160px");
		common.setEasyUiCss();
		
		customerQualityClient.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'OperateStatisticsController/findCustomerQualityPageList';
		var queryParams = {
		};
		queryParams['srcType'] = "client";
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');//平台
		queryParams['channelGroup'] = $("#channelGroupSearch").val()+"";//渠道分组
		queryParams['channelLevel'] = $("#channelLevelSearch").val()+"";//渠道分级
		queryParams['channel'] = $("#channelSearch").val()+"";//渠道
		queryParams['utmcsr'] = $("#utmcsrSearch").val()+"";	//来源	
		queryParams['utmcmd'] = $("#utmcmdSearch").val()+"";//媒件
		queryParams['utmccn'] = $("#utmccnSearch").val()+"";//系列
		queryParams['utmcct'] = $("#utmcctSearch").val()+"";//组
		queryParams['utmctr'] = $("#utmctrSearch").val()+"";//关键字
		queryParams['landingPage'] = $("#landingPageSearch").val()+"";//landingPage
		
		queryParams['platformVersion'] = $("#platformVersionSearch").val()+"";	//版本	
		queryParams['platformName'] = $("#platformNameSearch").val()+"";//马甲包
		
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();//平台复选框
		queryParams['channelGroupChecked'] = $("#channelGroupChecked").val();// 渠道分组复选框
		queryParams['channelLevelChecked'] = $("#channelLevelChecked").val();// 渠道分级复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();//媒件复选框	
		queryParams['utmccnChecked'] = $("#utmccnChecked").val();// 系列复选框
		queryParams['utmcctChecked'] = $("#utmcctChecked").val();// 组复选框
		queryParams['utmctrChecked'] = $("#utmctrChecked").val();// 关键字复选框
		queryParams['landingPageChecked'] = $("#landingPageChecked").val();// landingPage复选框
		
		queryParams['platformVersionChecked'] = $("#platformVersionChecked").val();// 版本复选框
		queryParams['platformNameChecked'] = $("#platformNameChecked").val();//马甲包复选框	
		
		var columns = [ [
                           /* {field : 'dateTime', title : '日期', sortable : false, width : 100, 
            	 				formatter : function(value, rowData, rowIndex) {
            	 					var dateTime = rowData.dateTime;
            	 					if(!common.isBlank(dateTime)){
            	 						var reportType = $("#reportTypeSearch").combobox('getValue');
            		 					if(reportType == 'days'){
            		 						return dateTime;
            		 					}else{
            		 						return dateTime.substring(0, 7);
            		 					}
            		 					return dateTime;
             						}else{
             							return "";
             						}
            				}}, */
            				{field : 'weeks', title : '周',sortable : false, width : 50, 
            	 				formatter : function(value, rowData, rowIndex) {
            	 					var dateTime = rowData.dateTime
            	 					return common.getWeekNumber(new Date(dateTime));
            				}},
			 	 			{field : 'deviceType', title : '设备类型', hidden : true,sortable : false, width : 100,
                            	formatter : function(value, rowData, rowIndex) {
                            		var deviceType = rowData.deviceType;
            	 					if(!common.isBlank(deviceType) && '0' == deviceType){
            	 						return 'PC';
            	 					}else if(!common.isBlank(deviceType) && '1' == deviceType){
            	 						return 'ANDROID';
            	 					}else if(!common.isBlank(deviceType) && '2' == deviceType){
            	 						return 'IOS';
            	 					}else if(!common.isBlank(deviceType) && '3' == deviceType){
            	 						return 'PCUI';
            	 					}else if(!common.isBlank(deviceType) && '5' == deviceType){
            	 						return 'APP';
            	 					}else if(!common.isBlank(deviceType) && '6' == deviceType){
            	 						return 'MOBILE';
            	 					}else{
            	 						return rowData.deviceType;
            	 					}
            				}},	
			 	 			{field : 'channelGrp', title : '渠道分组', hidden : true,sortable : false, width : 150},	 			
			 	 			{field : 'channelLevel', title : '渠道分级', hidden : true,sortable : false, width : 100},
			 	 			{field : 'channel', title : '渠道', hidden : true,sortable : false, width : 100},			 	 						 	 			
			 	 			{field : 'utmcsr', title : '来源',hidden : true, sortable : false, width : 150},
			 	 			{field : 'utmcmd', title : '媒件', hidden : true,sortable : false, width : 100},
			 	 			{field : 'utmccn', title : '系列', hidden : true,sortable : false, width : 100},
			 	 			{field : 'utmcct', title : '组',hidden : true, sortable : false, width : 150},
			 	 			{field : 'utmctr', title : '关键字', hidden : true,sortable : false, width : 100},
			 	 			{field : 'landingPage', title : 'landingPage', hidden : true,sortable : false, width : 300},
			 	 			{field : 'platformVersion', title : '版本',hidden : true, sortable : false, width : 150},
			 	 			{field : 'platformName', title : '马甲包', hidden : true,sortable : false, width : 100},
			 	 			
			 	 			{field : 'demoOpenCnt', title : '模拟开户数', sortable : false, width : 100},
			 	 			{field : 'accountOpenCnt', title : '真实开户数', sortable : false, width : 100},
			 	 			{field : 'accountActiveCnt', title : '激活账户数', sortable : false, width : 100},
			 	 			{field : 'demoOpenLoginCnt', title : '模拟开户登录数', sortable : false, width : 100},
			 	 			{field : 'demoOpenLoginRatio', title : '模拟开户登录率', sortable : false, width : 100},
			 	 			{field : 'demoToRealCnt', title : '模拟开户转真实数', sortable : false, width : 120},
			 	 			{field : 'demoToRealRatio', title : '模拟开户转真实率', sortable : false, width : 120},
			 	 			{field : 'demoToActiveCnt', title : '模拟开户转激活数', sortable : false, width : 120},
			 	 			{field : 'demoToActiveRatio', title : '模拟开户转激活率', sortable : false, width : 120},
			 	 			{field : 'accountOpenLoginCnt', title : '真实开户登录数', sortable : false, width : 100},
			 	 			{field : 'accountOpenLoginRatio', title : '真实开户登录率', sortable : false, width : 100},			 	 			
			 	 			{field : 'accountOpenActiveCnt', title : '真实开户激活数', sortable : false, width : 100},
			 	 			{field : 'accountOpenActiveRatio', title : '真实开户激活率', sortable : false, width : 100},
			 	 			{field : 'accountOpenOrderCnt', title : '真实开户交易数', sortable : false, width : 100},
			 	 			{field : 'accountOpenOrderRatio', title : '真实开户交易率', sortable : false, width : 100}
			  			] ];
		
		$('#' + customerQualityClient.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[
						    {field : 'dateTime', title : '日期', sortable : true, align:'center', width : 100}
						]],
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'dateTime',
			sortOrder : 'desc',
    		collapsible: true,
    		fitColumns:false,
    		singleSelect: false,		
    		selectOnCheck: true,	
    		checkOnSelect: true,
    		showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
		    onLoadSuccess : function(data){		    	
		    	customerQualityClient.showColumn();		    	    		
		    	common.iFrameHeight();
		    }
		});
	},
	getQueryParams: function() {
		var options = $("#" + customerQualityClient.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		
		queryParams['srcType'] = "client";
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');	
		
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');//平台
		queryParams['channelGroup'] = $("#channelGroupSearch").val()+"";//渠道分组
		queryParams['channelLevel'] = $("#channelLevelSearch").val()+"";//渠道分级
		queryParams['channel'] = $("#channelSearch").val()+"";//渠道
		queryParams['utmcsr'] = $("#utmcsrSearch").val()+"";	//来源	
		queryParams['utmcmd'] = $("#utmcmdSearch").val()+"";//媒件
		queryParams['utmccn'] = $("#utmccnSearch").val()+"";//系列
		queryParams['utmcct'] = $("#utmcctSearch").val()+"";//组
		queryParams['utmctr'] = $("#utmctrSearch").val()+"";//关键字
		queryParams['landingPage'] = $("#landingPageSearch").val()+"";//landingPage
		
		queryParams['platformVersion'] = $("#platformVersionSearch").val()+"";	//版本	
		queryParams['platformName'] = $("#platformNameSearch").val()+"";//马甲包
		
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();//平台复选框
		queryParams['channelGroupChecked'] = $("#channelGroupChecked").val();// 渠道分组复选框
		queryParams['channelLevelChecked'] = $("#channelLevelChecked").val();// 渠道分级复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();//媒件复选框	
		queryParams['utmccnChecked'] = $("#utmccnChecked").val();// 系列复选框
		queryParams['utmcctChecked'] = $("#utmcctChecked").val();// 组复选框
		queryParams['utmctrChecked'] = $("#utmctrChecked").val();// 关键字复选框
		queryParams['landingPageChecked'] = $("#landingPageChecked").val();// landingPage复选框
		
		queryParams['platformVersionChecked'] = $("#platformVersionChecked").val();// 版本复选框
		queryParams['platformNameChecked'] = $("#platformNameChecked").val();//马甲包复选框	
				
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		customerQualityClient.getQueryParams();
		
		customerQualityClient.showColumn();
		
		common.loadGrid(customerQualityClient.dataGridId);
	},
	
	showColumn:function(){
		var reportType = $("#reportTypeSearch").combobox('getValue');
		if(reportType == 'days' || reportType == 'months'){
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'weeks');
		}
		if(reportType == 'weeks'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'weeks');
		}
		
		if($("#deviceTypeChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'deviceType');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'deviceType');
		}
		
		if($("#channelGroupChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'channelGrp');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'channelGrp');
		}
		
		if($("#channelLevelChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'channelLevel');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'channelLevel');
		}
		
		if($("#channelChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'channel');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'channel');
		}
		
		if($("#utmcsrChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'utmcsr');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'utmcsr');
		}
		
		if($("#utmcmdChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'utmcmd');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'utmcmd');
		}
		
		if($("#utmccnChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'utmccn');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'utmccn');
		}
		
		if($("#utmcctChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'utmcct');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'utmcct');
		}
		
		if($("#utmctrChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'utmctr');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'utmctr');
		}	
		
		if($("#landingPageChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'landingPage');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'landingPage');
		}	
		
		if($("#platformVersionChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'platformVersion');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'platformVersion');
		}
		
		if($("#platformNameChecked").val() == 'true'){
			$("#" + customerQualityClient.dataGridId).datagrid('showColumn', 'platformName');
		}else{
			$("#" + customerQualityClient.dataGridId).datagrid('hideColumn', 'platformName');
		}
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$("#startTimeSearchHour").datebox('setValue', $("#beforeYesterdayStartHour").val());
		$("#endTimeSearchHour").datebox('setValue', $("#dayEndHour").val());
		$("#dateTd").show();
		$("#dateTimeTd").hide();
		
		$('#' + customerQualityClient.searchFormId).form('reset');
		$('#startTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		
		$("#startTimeSearch").datebox('setValue', $("#yesterdayStart").val());
		$("#endTimeSearch").datebox('setValue', $("#dayEnd").val());
		
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = customerQualityClient.getQueryParams();
		var url = BASE_PATH + "OperateStatisticsController/exportExcelCustomerQuality?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	
	/**
	 * 统计类型change事件
	 */
	setDateboxAttr: function(newValue,oldValue){		
		if(newValue == 'hours'){
			$("#dateTd").hide();
			$("#dateTimeTd").show();
			
			$('#startTimeSearchHour').datebox({
				formatter:easyui.formatterYYYYMMDDHH,
				parser:easyui.parserYYYYMMDDHH
			});
			$('#endTimeSearchHour').datebox({
				formatter:easyui.formatterYYYYMMDDHH,
				parser:easyui.parserYYYYMMDDHH
			});
			$("#startTimeSearchHour").datebox('setValue', $("#yesterdayStartHour").val());
			$("#endTimeSearchHour").datebox('setValue', $("#dayEndHour").val());
		}else if(newValue == 'days'){
			$("#dateTd").show();
			$("#dateTimeTd").hide();
			
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMMDD,
				parser:easyui.parserYYYYMMDD
			});
			$("#startTimeSearch").datebox('setValue', $("#yesterdayStartHour").val());
			$("#endTimeSearch").datebox('setValue', $("#dayEnd").val());
		}else if(newValue == 'weeks'){
			$("#dateTd").show();
			$("#dateTimeTd").hide();
			
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
			$("#dateTd").show();
			$("#dateTimeTd").hide();
			
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
		}else{
			$("#dateTd").show();
			$("#dateTimeTd").hide();
			
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
		}
		common.setEasyUiCss();
	}

}
