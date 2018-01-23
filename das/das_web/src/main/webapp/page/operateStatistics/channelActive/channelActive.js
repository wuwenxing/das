$(function() {
	channelActive.init();
});

var channelActive = {
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
		
		channelActive.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'OperateStatisticsController/findChannelActivePageList';
		var queryParams = {
		};
		queryParams['srcType'] = "gts2_client";
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');//平台
		queryParams['channelGroup'] = $("#channelGroupSearch").val()+"";//渠道分组
		queryParams['channelLevel'] = $("#channelLevelSearch").val()+"";//渠道分级
		queryParams['channel'] = $("#channelSearch").val()+"";//渠道		
		queryParams['platformVersion'] = $("#platformVersionSearch").val()+"";	//版本	
		queryParams['platformName'] = $("#platformNameSearch").val()+"";//马甲包
		
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();//平台复选框
		queryParams['channelGroupChecked'] = $("#channelGroupChecked").val();// 渠道分组复选框
		queryParams['channelLevelChecked'] = $("#channelLevelChecked").val();// 渠道分级复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框	
		queryParams['platformVersionChecked'] = $("#platformVersionChecked").val();// 版本复选框
		queryParams['platformNameChecked'] = $("#platformNameChecked").val();//马甲包复选框	
		
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
			 	 			{field : 'platformVersion', title : '版本',hidden : true, sortable : false, width : 150},
			 	 			{field : 'platformName', title : '马甲包', hidden : true,sortable : false, width : 100},

			 	 			{field : 'newDeviceCount', title : '新增设备数', sortable : false, width : 100},
			 	 			{field : 'activeDeviceCount', title : '活跃设备数', sortable : false, width : 100},
			 	 			{field : 'deviceLoginCount', title : '登录设备数', sortable : false, width : 100},
			 	 			{field : 'accountLoginCount', title : '登录账号数', sortable : false, width : 100},
			 	 			{field : 'deviceLoginRate', title : '设备登录率', sortable : false, width : 100},
			 	 			{field : 'timeLen', title : '总停留时间', sortable : false, width : 100}
			  			] ];
		
		$('#' + channelActive.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
		    	channelActive.showColumn();		    	    		
		    	common.iFrameHeight();
		    }
		});
	},
	getQueryParams: function() {
		var options = $("#" + channelActive.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		
		queryParams['srcType'] = "gts2_client";
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');//平台
		queryParams['channelGroup'] = $("#channelGroupSearch").val()+"";//渠道分组
		queryParams['channelLevel'] = $("#channelLevelSearch").val()+"";//渠道分级
		queryParams['channel'] = $("#channelSearch").val()+"";//渠道		
		queryParams['platformVersion'] = $("#platformVersionSearch").val()+"";	//版本	
		queryParams['platformName'] = $("#platformNameSearch").val()+"";//马甲包
		
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();//平台复选框
		queryParams['channelGroupChecked'] = $("#channelGroupChecked").val();// 渠道分组复选框
		queryParams['channelLevelChecked'] = $("#channelLevelChecked").val();// 渠道分级复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框	
		queryParams['platformVersionChecked'] = $("#platformVersionChecked").val();// 版本复选框
		queryParams['platformNameChecked'] = $("#platformNameChecked").val();//马甲包复选框	
				
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		channelActive.getQueryParams();
		
		channelActive.showColumn();
		
		common.loadGrid(channelActive.dataGridId);
	},
	
	showColumn:function(){
		var reportType = $("#reportTypeSearch").combobox('getValue');
		if(reportType == 'days' || reportType == 'months'){
			$("#" + channelActive.dataGridId).datagrid('hideColumn', 'weeks');
		}
		if(reportType == 'weeks'){
			$("#" + channelActive.dataGridId).datagrid('showColumn', 'weeks');
		}
		
		if($("#deviceTypeChecked").val() == 'true'){
			$("#" + channelActive.dataGridId).datagrid('showColumn', 'deviceType');
		}else{
			$("#" + channelActive.dataGridId).datagrid('hideColumn', 'deviceType');
		}
		
		if($("#channelGroupChecked").val() == 'true'){
			$("#" + channelActive.dataGridId).datagrid('showColumn', 'channelGrp');
		}else{
			$("#" + channelActive.dataGridId).datagrid('hideColumn', 'channelGrp');
		}
		
		if($("#channelLevelChecked").val() == 'true'){
			$("#" + channelActive.dataGridId).datagrid('showColumn', 'channelLevel');
		}else{
			$("#" + channelActive.dataGridId).datagrid('hideColumn', 'channelLevel');
		}
		
		if($("#channelChecked").val() == 'true'){
			$("#" + channelActive.dataGridId).datagrid('showColumn', 'channel');
		}else{
			$("#" + channelActive.dataGridId).datagrid('hideColumn', 'channel');
		}
		
		if($("#platformVersionChecked").val() == 'true'){
			$("#" + channelActive.dataGridId).datagrid('showColumn', 'platformVersion');
		}else{
			$("#" + channelActive.dataGridId).datagrid('hideColumn', 'platformVersion');
		}
		
		if($("#platformNameChecked").val() == 'true'){
			$("#" + channelActive.dataGridId).datagrid('showColumn', 'platformName');
		}else{
			$("#" + channelActive.dataGridId).datagrid('hideColumn', 'platformName');
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
		
		$('#' + channelActive.searchFormId).form('reset');
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
		var queryParams = channelActive.getQueryParams();
		var url = BASE_PATH + "OperateStatisticsController/exportExcelChannelActive?";
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
