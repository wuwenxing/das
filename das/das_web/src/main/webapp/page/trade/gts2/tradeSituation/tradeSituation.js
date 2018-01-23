$(function() {
	tradeSituation.init();
});

var tradeSituation = {
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
		
		tradeSituation.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'tradeBordereauxController/findTradeSituationPageList';
		var queryParams = {
		};
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');//平台
		queryParams['channelgroup'] = $("#channelgroupSearch").val()+"";//渠道分组
		queryParams['channellevel'] = $("#channellevelSearch").val()+"";//渠道分级
		queryParams['channel'] = $("#channelSearch").val()+"";//渠道
		queryParams['utmsource'] = $("#utmsourceSearch").val()+"";	//来源	
		queryParams['utmmedium'] = $("#utmmediumSearch").val()+"";//媒件
		queryParams['utmcampaign'] = $("#utmcampaignSearch").val()+"";//系列
		queryParams['utmcontent'] = $("#utmcontentSearch").val()+"";//组
		queryParams['utmterm'] = $("#utmtermSearch").val()+"";//关键字
		
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();//平台复选框
		queryParams['channelgroupChecked'] = $("#channelgroupChecked").val();// 渠道分组复选框
		queryParams['channellevelChecked'] = $("#channellevelChecked").val();// 渠道分级复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框
		queryParams['utmsourceChecked'] = $("#utmsourceChecked").val();// 来源复选框
		queryParams['utmmediumChecked'] = $("#utmmediumChecked").val();//媒件复选框	
		queryParams['utmcampaignChecked'] = $("#utmcampaignChecked").val();// 系列复选框
		queryParams['utmcontentChecked'] = $("#utmcontentChecked").val();// 组复选框
		queryParams['utmtermChecked'] = $("#utmtermChecked").val();// 关键字复选框
		
		var columns = [ [
                            /*{field : 'execdate', title : '日期', sortable : false, width : 100, 
            	 				formatter : function(value, rowData, rowIndex) {
            	 					var dateTime = rowData.execdate;
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
            	 					var dateTime = rowData.execdate
            	 					return common.getWeekNumber(new Date(dateTime));
            				}},
			 	 			{field : 'devicetype', title : '设备类型', hidden : true,sortable : false, width : 100,
                            	formatter : function(value, rowData, rowIndex) {
                            		var deviceType = rowData.devicetype;
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
			 	 			{field : 'channelgroup', title : '渠道分组', hidden : true,sortable : false, width : 150},	 			
			 	 			{field : 'channellevel', title : '渠道分级', hidden : true,sortable : false, width : 100},
			 	 			{field : 'channel', title : '渠道', hidden : true,sortable : false, width : 100},			 	 						 	 			
			 	 			{field : 'utmsource', title : '来源',hidden : true, sortable : false, width : 150},
			 	 			{field : 'utmmedium', title : '媒件', hidden : true,sortable : false, width : 100},
			 	 			{field : 'utmcampaign', title : '系列', hidden : true,sortable : false, width : 100},
			 	 			{field : 'utmcontent', title : '组',hidden : true, sortable : false, width : 150},
			 	 			{field : 'utmterm', title : '关键字', hidden : true,sortable : false, width : 100},
			 	 			{field : 'accountactivecnt', title : '激活账户数', sortable : false, width : 100},
			 	 			{field : 'depositamt', title : '总存款', sortable : false, width : 100},
			 	 			{field : 'withdrawamt', title : '总取款', sortable : false, width : 100},
			 	 			{field : 'cleandeposit', title : '净入金', sortable : false, width : 100},
			 	 			{field : 'openvolume', title : '开仓手数', sortable : false, width : 100},
			 	 			{field : 'closevolume', title : '平仓手数', sortable : false, width : 100},
			 	 			{field : 'avgdepositamt', title : '人均存款', sortable : false, width : 100},
			 	 			{field : 'avgwithdrawamt', title : '人均取款', sortable : false, width : 100},
			 	 			{field : 'avgcleandeposit', title : '人均净入金', sortable : false, width : 100},
			 	 			{field : 'avgopenvolume', title : '人均开仓手数', sortable : false, width : 100},
			 	 			{field : 'avgclosevolume', title : '人均平仓手数', sortable : false, width : 100}
			  			] ];
		
		$('#' + tradeSituation.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[
						    {field : 'execdate', title : '日期', sortable : true, align:'center', width : 100}
						]],
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'execdate',
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
		    	tradeSituation.showColumn();
		    	common.iFrameHeight();
		    }
		});
	},
	getQueryParams: function() {
		var options = $("#" + tradeSituation.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
			
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');//平台
		queryParams['channelgroup'] = $("#channelgroupSearch").val()+"";//渠道分组
		queryParams['channellevel'] = $("#channellevelSearch").val()+"";//渠道分级
		queryParams['channel'] = $("#channelSearch").val()+"";//渠道
		queryParams['utmsource'] = $("#utmsourceSearch").val()+"";	//来源	
		queryParams['utmmedium'] = $("#utmmediumSearch").val()+"";//媒件
		queryParams['utmcampaign'] = $("#utmcampaignSearch").val()+"";//系列
		queryParams['utmcontent'] = $("#utmcontentSearch").val()+"";//组
		queryParams['utmterm'] = $("#utmtermSearch").val()+"";//关键字
		
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();//平台复选框
		queryParams['channelgroupChecked'] = $("#channelgroupChecked").val();// 渠道分组复选框
		queryParams['channellevelChecked'] = $("#channellevelChecked").val();// 渠道分级复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道复选框
		queryParams['utmsourceChecked'] = $("#utmsourceChecked").val();// 来源复选框
		queryParams['utmmediumChecked'] = $("#utmmediumChecked").val();//媒件复选框	
		queryParams['utmcampaignChecked'] = $("#utmcampaignChecked").val();// 系列复选框
		queryParams['utmcontentChecked'] = $("#utmcontentChecked").val();// 组复选框
		queryParams['utmtermChecked'] = $("#utmtermChecked").val();// 关键字复选框
				
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		tradeSituation.getQueryParams();
		
		tradeSituation.showColumn();
		
		common.loadGrid(tradeSituation.dataGridId);
	},
	
	showColumn:function(){
		var reportType = $("#reportTypeSearch").combobox('getValue');
		if(reportType == 'days' || reportType == 'months'){
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'weeks');
		}
		if(reportType == 'weeks'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'weeks');
		}
		
		if($("#deviceTypeChecked").val() == 'true'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'devicetype');
		}else{
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'devicetype');
		}
		
		if($("#channelgroupChecked").val() == 'true'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'channelgroup');
		}else{
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'channelgroup');
		}
		
		if($("#channellevelChecked").val() == 'true'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'channellevel');
		}else{
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'channellevel');
		}
		
		if($("#channelChecked").val() == 'true'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'channel');
		}else{
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'channel');
		}
		
		if($("#utmsourceChecked").val() == 'true'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'utmsource');
		}else{
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'utmsource');
		}
		
		if($("#utmmediumChecked").val() == 'true'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'utmmedium');
		}else{
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'utmmedium');
		}
		
		if($("#utmcampaignChecked").val() == 'true'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'utmcampaign');
		}else{
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'utmcampaign');
		}
		
		if($("#utmcontentChecked").val() == 'true'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'utmcontent');
		}else{
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'utmcontent');
		}
		
		if($("#utmtermChecked").val() == 'true'){
			$("#" + tradeSituation.dataGridId).datagrid('showColumn', 'utmterm');
		}else{
			$("#" + tradeSituation.dataGridId).datagrid('hideColumn', 'utmterm');
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
		
		$('#' + tradeSituation.searchFormId).form('reset');
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
		var queryParams = tradeSituation.getQueryParams();
		var url = BASE_PATH + "tradeBordereauxController/exportExcelTradeSituation?";
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
