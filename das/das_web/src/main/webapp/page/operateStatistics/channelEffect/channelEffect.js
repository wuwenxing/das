$(function() {
	channelEffect.init();
});

var channelEffect = {
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
		
		channelEffect.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'OperateStatisticsController/findChannelEffectPageList';
		var queryParams = {
		};
		queryParams['srcType'] = "web";
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
		queryParams['landingPage'] = $("#landingPageSearch").val()+"";//landingPageSearch
				
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();//平台复选框
		queryParams['channelGroupChecked'] = $("#channelGroupChecked").val();// 渠道分组复选框
		queryParams['channelLevelChecked'] = $("#channelLevelChecked").val();// 渠道分级复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框	
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();//媒件复选框	
		queryParams['utmccnChecked'] = $("#utmccnChecked").val();// 系列复选框
		queryParams['utmcctChecked'] = $("#utmcctChecked").val();// 组复选框
		queryParams['utmctrChecked'] = $("#utmctrChecked").val();// 关键字复选框
		queryParams['landingPageChecked'] = $("#landingPageChecked").val();// landingPageSearch复选框
		
		var columns = [ [
                            /*{field : 'dateTime', title : '日期', sortable : false, width : 100, 
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
			 	 			{field : 'landingPage', title : 'LandingPage', hidden : true,sortable : false, width : 300},
			 	 			
			 	 			{field : 'newDeviceCount', title : '新增设备数', sortable : false, width : 100},
			 	 			{field : 'activeDeviceCount', title : '活跃设备数', sortable : false, width : 100},
			 	 			{field : 'deviceLoginCount', title : '登录设备数', sortable : false, width : 100},
			 	 			{field : 'accountLoginCount', title : '登录账号数', sortable : false, width : 100},
			 	 			{field : 'deviceLoginRate', title : '设备登录率', sortable : false, width : 100},
			 	 			{field : 'timeLen', title : '总停留时间', sortable : false, width : 100},
			 	 			{field : 'visitCount', title : '会话数', sortable : false, width : 100},
			 	 			{field : 'avgVisitTimeLen', title : '会话平均使用时间', sortable : false, width : 120},
			 	 			{field : 'eventAdvisoryCount', title : '进入咨询会话数', sortable : false, width : 120},
			 	 			{field : 'eventDemoCount', title : '进入模拟开户会话数', sortable : false, width : 120},
			 	 			{field : 'eventRealCount', title : '进入真实开户会话数', sortable : false, width : 120},			 	 			
			 	 			{field : 'eventActiveCount', title : '进入入金流程会话数', sortable : false, width : 120},
			 	 			{field : 'eventDeviceAdvisoryCount', title : '进入咨询设备数', sortable : false, width : 100},
			 	 			{field : 'eventDeviceDemoCount', title : '进入模拟开户设备数', sortable : false, width : 120},
			 	 			{field : 'eventDeviceRealCount', title : '进入真实开户设备数', sortable : false, width : 120},
			 	 			{field : 'eventDeviceActiveCount', title : '进入入金流程设备数', sortable : false, width : 120},
			 	 			{field : 'demoCount', title : '模拟开户数', sortable : false, width : 100},
			 	 			{field : 'realCount', title : '真实开户数', sortable : false, width : 100},		 	 			
			 	 			{field : 'activeCount', title : '入金账户数', sortable : false, width : 100},
			 	 			
			 	 			{field : 'demoCountRate', title : '模拟开户率', sortable : false, width : 100},
			 	 			{field : 'realCountRate', title : '真实开户率', sortable : false, width : 100},
			 	 			{field : 'demoVisitRate', title : '模拟开户率GW', sortable : false, width : 100},
			 	 			{field : 'realVisitRate', title : '真实开户率GW', sortable : false, width : 100},
			 	 			{field : 'activeRate', title : '入金率', sortable : false, width : 100}
			  			] ];
		
		$('#' + channelEffect.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		    	channelEffect.showColumn();		    	    		
		    	common.iFrameHeight();
		    }
		});
	},
	getQueryParams: function() {
		var options = $("#" + channelEffect.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		
		queryParams['srcType'] = "web";
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
		queryParams['landingPage'] = $("#landingPageSearch").val()+"";//landingPageSearch
				
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();//平台复选框
		queryParams['channelGroupChecked'] = $("#channelGroupChecked").val();// 渠道分组复选框
		queryParams['channelLevelChecked'] = $("#channelLevelChecked").val();// 渠道分级复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框	
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();//媒件复选框	
		queryParams['utmccnChecked'] = $("#utmccnChecked").val();// 系列复选框
		queryParams['utmcctChecked'] = $("#utmcctChecked").val();// 组复选框
		queryParams['utmctrChecked'] = $("#utmctrChecked").val();// 关键字复选框
		queryParams['landingPageChecked'] = $("#landingPageChecked").val();// landingPageSearch复选框
				
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		channelEffect.getQueryParams();
		
		channelEffect.showColumn();
		
		common.loadGrid(channelEffect.dataGridId);
	},
	
	showColumn:function(){
		var reportType = $("#reportTypeSearch").combobox('getValue');
		if(reportType == 'days' || reportType == 'months'){
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'weeks');
		}
		if(reportType == 'weeks'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'weeks');
		}
		
		if($("#deviceTypeChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'deviceType');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'deviceType');
		}
		
		if($("#channelGroupChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'channelGrp');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'channelGrp');
		}
		
		if($("#channelLevelChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'channelLevel');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'channelLevel');
		}
		
		if($("#channelChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'channel');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'channel');
		}
		
		if($("#utmcsrChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'utmcsr');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'utmcsr');
		}
		
		if($("#utmcmdChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'utmcmd');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'utmcmd');
		}
		
		if($("#utmccnChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'utmccn');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'utmccn');
		}
		
		if($("#utmcctChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'utmcct');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'utmcct');
		}
		
		if($("#utmctrChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'utmctr');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'utmctr');
		}
		
		if($("#landingPageChecked").val() == 'true'){
			$("#" + channelEffect.dataGridId).datagrid('showColumn', 'landingPage');
		}else{
			$("#" + channelEffect.dataGridId).datagrid('hideColumn', 'landingPage');
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
		
		$('#' + channelEffect.searchFormId).form('reset');
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
		var queryParams = channelEffect.getQueryParams();
		var url = BASE_PATH + "OperateStatisticsController/exportExcelChannelEffect?";
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
