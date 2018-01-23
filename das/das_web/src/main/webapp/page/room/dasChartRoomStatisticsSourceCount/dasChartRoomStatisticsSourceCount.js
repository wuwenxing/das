$(function() {		
	dasChartRoomStatisticsSourceCount.init();
});

var dasChartRoomStatisticsSourceCount = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	dataTreeGrid:"dataTreeGrid",
	/**
	 * 初始化
	 */
	init:function(){
		dasChartRoomStatisticsSourceCount.loadDataGrid();
		$("#dataTreeGridDiv").hide();
		$("select[multiple='multiple']").multiselect({
			noneSelectedText: "---请选择---", 
			selectedList: 10 // 0-based index
		}).multiselectfilter();
		$(".ui-multiselect-menu").css("width", "217px");
		
		var channel = $("#channelInput").val();
		if(null != channel && "" != channel){
			$('#channelIds option').each(function(){
				if(channel.indexOf(this.value)!=-1)this.selected=true;
			});
			$(".ui-multiselect-menu").css("width", "217px");
			$("select[multiple='multiple']").multiselect("refresh");
		}		
		
		var devicetypeInput = $("#devicetypeInput").val();
		if(null != devicetypeInput && ""!= devicetypeInput){
			if("pc" == devicetypeInput){
				$("#devicetypeSearch").combobox('setValue',"PC端" );
			}else if("mobile" == devicetypeInput){
				$("#devicetypeSearch").combobox('setValue',"移动端" );
			}
		}
		
		common.setEasyUiCss();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/pageListRoomSourceCount';
		var queryParams = {
		};
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();// 媒介复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['devicetype'] = $("#devicetypeSearch").combobox('getValue');
		var devicetypeInput = $("#devicetypeInput").val();
		if(null != devicetypeInput && ""!= devicetypeInput){
			queryParams['devicetype'] = devicetypeInput;
		}
		queryParams['channelIds'] = $("#channelIds").val()+"";
		var channel = $("#channelInput").val();
		if(null != channel && "" != channel){
			queryParams['channelIds']  = channel;
		}				
		queryParams['startTimeCompare'] = $("#startTimeCompareSearch").datebox('getValue');
		queryParams['endTimeCompare'] = $("#endTimeCompareSearch").datebox('getValue');
		
		var columns = [ [
			    {field : 'dataTime', title : '日期', sortable : true, width : 50},
	 			{field : 'utmcsr', title : '来源', sortable : true, width : 50},
	 			{field : 'utmcmd', title : '媒介', sortable : true, width : 50},
	 			{field : 'utmccn', title : '系列', hidden : true, sortable : true, width : 80},
	 			{field : 'utmcct', title : '组', hidden : true, sortable : true, width : 80},
	 			{field : 'utmctr', title : '关键字', hidden : true, sortable : true, width : 80},
	 			/*{field : 'visitCount', title : '访问次数', sortable : true, width : 50},
	 			{field : 'advisoryCountQQ', title : 'QQ咨询', sortable : true, width : 50},
	 			{field : 'advisoryCountLIVE800', title : 'LIVE800咨询', sortable : true, width : 50},*/
	 			{field : 'devicecount', title : '设备次数', sortable : true, width : 50}, 
	 			{field : 'demoCount', title : '模拟开户数', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.demoCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var behaviorType = '3';
	 						var utmcsr = common.isBlank(rowData.utmcsr)?"":rowData.utmcsr;
	 						var utmcmd = common.isBlank(rowData.utmcmd)?"":rowData.utmcmd;
	 						var utmccn = common.isBlank(rowData.utmccn)?"":rowData.utmccn;
	 						var utmcct = common.isBlank(rowData.utmcct)?"":rowData.utmcct;
	 						var utmctr = common.isBlank(rowData.utmctr)?"":rowData.utmctr;
	 						var html = "";
	 						html += "<a onclick=\"dasChartRoomStatisticsSourceCount.viewDetail3('"
	 							+rowData.dataTime+"', '"+behaviorType+"', '"+utmcsr+"', '"+utmcmd+"', '"
	 							+utmccn+"', '"+utmcct+"', '"+utmctr+"')\">" + value + "</a>";
	 						return html;
	 					}else{
	 						return value;
	 					}
				}},
	 			{field : 'realCount', title : '真实开户数', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.realCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var behaviorType = '4';
	 						var utmcsr = common.isBlank(rowData.utmcsr)?"":rowData.utmcsr;
	 						var utmcmd = common.isBlank(rowData.utmcmd)?"":rowData.utmcmd;
	 						var utmccn = common.isBlank(rowData.utmccn)?"":rowData.utmccn;
	 						var utmcct = common.isBlank(rowData.utmcct)?"":rowData.utmcct;
	 						var utmctr = common.isBlank(rowData.utmctr)?"":rowData.utmctr;
	 						var html = "";
	 						html += "<a onclick=\"dasChartRoomStatisticsSourceCount.viewDetail3('"
	 							+rowData.dataTime+"', '"+behaviorType+"', '"+utmcsr+"', '"+utmcmd+"', '"
	 							+utmccn+"', '"+utmcct+"', '"+utmctr+"')\">" + value + "</a>";
	 						return html;
	 					}else{
	 						return value;
	 					}
				}},
	 			{field : 'depositCount', title : '首次入金数', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(value > 0 && !common.isBlank(rowData.dataTime)){
	 						var behaviorType = '5';
	 						var utmcsr = common.isBlank(rowData.utmcsr)?"":rowData.utmcsr;
	 						var utmcmd = common.isBlank(rowData.utmcmd)?"":rowData.utmcmd;
	 						var utmccn = common.isBlank(rowData.utmccn)?"":rowData.utmccn;
	 						var utmcct = common.isBlank(rowData.utmcct)?"":rowData.utmcct;
	 						var utmctr = common.isBlank(rowData.utmctr)?"":rowData.utmctr;
	 						var html = "";
	 						html += "<a onclick=\"dasChartRoomStatisticsSourceCount.viewDetail3('"
	 							+rowData.dataTime+"', '"+behaviorType+"', '"+utmcsr+"', '"+utmcmd+"', '"
	 							+utmccn+"', '"+utmcct+"', '"+utmctr+"')\">" + value + "</a>";
	 						return html;
	 					}else{
	 						return value;
	 					}
				}},
				{field : 'devicetype', title : '访问客户端', sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.devicetype == '0'){
							return "PC";
						}else if(rowData.platformType == '1'){
							return "Android";
						}else if(rowData.devicetype == '2'){
							return "IOS";
						}else{
							return "(全部)";
						}
	 				}
				}
 			] ];
		$('#' + dasChartRoomStatisticsSourceCount.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'dataTime',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='dataTime']").text("小计：");
				dasChartRoomStatisticsSourceCountChart.loadChartData(1);
				dasChartRoomStatisticsSourceCountChart.loadChartData(2);
				dasChartRoomStatisticsSourceCountChart.loadChartData(3);
				common.iFrameHeight();
				
				// multiselect复选框勾选
				var channelIds = ',' + data.channelIds + ',';// 添加分隔符号，好indexOf进行比较
				$('#channelIds option').each(function(){
					if(channelIds.indexOf(',' + this.value + ',')!=-1)this.selected=true;
				});
				$("select[multiple='multiple']").multiselect("refresh");
				$(".ui-multiselect-menu").css("width", "217px");
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['utmccn'] = $("#utmccnSearch").val();// 系列
		queryParams['utmcct'] = $("#utmcctSearch").val();// 组
		queryParams['utmctr'] = $("#utmctrSearch").val();// 关键字
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();// 媒介复选框
		queryParams['utmccnChecked'] = $("#utmccnChecked").val();// 系列复选框
		queryParams['utmcctChecked'] = $("#utmcctChecked").val();// 组复选框
		queryParams['utmctrChecked'] = $("#utmctrChecked").val();// 关键字复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['devicetype'] = $("#devicetypeSearch").combobox('getValue');
		queryParams['channelIds'] = $("#channelIds").val()+"";		
		queryParams['startTimeCompare'] = $("#startTimeCompareSearch").datebox('getValue');
		queryParams['endTimeCompare'] = $("#endTimeCompareSearch").datebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasChartRoomStatisticsSourceCount.getQueryParams();
		var startTime = $("#startTimeSearch").datebox('getValue');
		var endTime = $("#endTimeSearch").datebox('getValue');
		var startTimeCompareSearch = $("#startTimeCompareSearch").datebox('getValue');
		var endTimeCompareSearch = $("#endTimeCompareSearch").datebox('getValue');
		if(!common.isBlank(startTimeCompareSearch) && !common.isBlank(endTimeCompareSearch) && (startTimeCompareSearch != startTime || endTimeCompareSearch != endTime)){
			$("#dataGridDiv").hide();
			$("#dataTreeGridDiv").show();
			dasChartRoomStatisticsSourceCount.loadTreeGrid();
		}else{
			$("#dataGridDiv").show();
			$("#dataTreeGridDiv").hide();
			if($("#utmcsrChecked").val() == 'true'){
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('showColumn', 'utmcsr');
			}else{
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('hideColumn', 'utmcsr');
			}
			if($("#utmcmdChecked").val() == 'true'){
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('showColumn', 'utmcmd');
			}else{
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('hideColumn', 'utmcmd');
			}
			if($("#utmccnChecked").val() == 'true'){
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('showColumn', 'utmccn');
			}else{
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('hideColumn', 'utmccn');
			}
			if($("#utmcctChecked").val() == 'true'){
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('showColumn', 'utmcct');
			}else{
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('hideColumn', 'utmcct');
			}
			if($("#utmctrChecked").val() == 'true'){
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('showColumn', 'utmctr');
			}else{
				$("#" + dasChartRoomStatisticsSourceCount.dataGridId).datagrid('hideColumn', 'utmctr');
			}
			common.loadGrid(dasChartRoomStatisticsSourceCount.dataGridId);
		}
	},
	
	/**
	 * 加载treeGrid
	 */
	loadTreeGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasChartRoomController/pageListRoomSourceCount';
		var queryParams = {
		};
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();// 媒介复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['devicetype'] = $("#devicetypeSearch").datebox('getValue');
		queryParams['channelIds'] = $("#channelIds").val()+"";		
		queryParams['startTimeCompare'] = $("#startTimeCompareSearch").datebox('getValue');
		queryParams['endTimeCompare'] = $("#endTimeCompareSearch").datebox('getValue');
		var columns = [ [
				{field : 'utmcsrAndUtmcmd', title : '来源/媒介', sortable : false, width : 150},
/*	 			{field : 'utmcsr', title : '来源', sortable : true, width : 50},
	 			{field : 'utmcmd', title : '媒介', sortable : true, width : 50},*/
	 			{field : 'visitCount', title : '访问次数', sortable : false, width : 50,
	 				styler:function(value, rowData, rowIndex){
						var visitCount =  rowData.visitCount+"";
						if(null != visitCount && visitCount.indexOf("%")>0){
							var num = value.replace("%","");
							if(num <= 0){
								return 'color:red;';
							}else{
								return 'color:green;';
							}
						}
					}},
		 		{field : 'devicecount', title : '设备次数', sortable : false, width : 50,
		 				styler:function(value, rowData, rowIndex){
							var devicecount =  rowData.devicecount+"";
							if(null != devicecount && devicecount.indexOf("%")>0){
								var num = value.replace("%","");
								if(num <= 0){
									return 'color:red;';
								}else{
									return 'color:green;';
								}
							}
				}},
	 			{field : 'advisoryCountQQ', title : 'QQ咨询', sortable : false, width : 50,
						styler:function(value, rowData, rowIndex){
							var advisoryCountQQ =  rowData.advisoryCountQQ+"";
							if(null != advisoryCountQQ && advisoryCountQQ.indexOf("%")>0){
								var num = value.replace("%","");
								if(num <= 0){
									return 'color:red;';
								}else{
									return 'color:green;';
								}
							}
						}},
	 			{field : 'advisoryCountLIVE800', title : 'LIVE800咨询', sortable : false, width : 50,
							styler:function(value, rowData, rowIndex){
								var advisoryCountLIVE800 =  rowData.advisoryCountLIVE800+"";
								if(null != advisoryCountLIVE800 && advisoryCountLIVE800.indexOf("%")>0){
									var num = value.replace("%","");
									if(num <= 0){
										return 'color:red;';
									}else{
										return 'color:green;';
									}
								}
							}},
	 			{field : 'demoCount', title : '模拟开户数', sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.demoCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var behaviorType = '3';
	 						var utmcsr = common.isBlank(rowData.utmcsr)?"":rowData.utmcsr;
	 						var utmcmd = common.isBlank(rowData.utmcmd)?"":rowData.utmcmd;
	 						var html = "";
	 						html += "<a onclick=\"dasChartRoomStatisticsSourceCount.viewDetail2('"
	 							+rowData.dataTime+"', '"+behaviorType+"', '"+utmcsr+"', '"+utmcmd+"')\">" + value + "</a>";
	 						return html;
	 					}else{
	 						return value;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var demoCount =  rowData.demoCount+"";
					if(null != demoCount && demoCount.indexOf("%")>0){
						var num = value.replace("%","");
						if(num <= 0){
							return 'color:red;';
						}else{
							return 'color:green;';
						}
					}
				}},
	 			{field : 'realCount', title : '真实开户数', sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.realCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var behaviorType = '4';
	 						var utmcsr = common.isBlank(rowData.utmcsr)?"":rowData.utmcsr;
	 						var utmcmd = common.isBlank(rowData.utmcmd)?"":rowData.utmcmd;
	 						var html = "";
	 						html += "<a onclick=\"dasChartRoomStatisticsSourceCount.viewDetail2('"
	 							+rowData.dataTime+"', '"+behaviorType+"', '"+utmcsr+"', '"+utmcmd+"')\">" + value + "</a>";
	 						return html;
	 					}else{
	 						return value;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var realCount =  rowData.realCount+"";
					if(null != realCount && realCount.indexOf("%")>0){
						var num = value.replace("%","");
						if(num <= 0){
							return 'color:red;';
						}else{
							return 'color:green;';
						}
					}
				}},
	 			{field : 'depositCount', title : '首次入金数', sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(rowData.depositCount > 0 && !common.isBlank(rowData.dataTime)){
	 						var behaviorType = '5';
	 						var utmcsr = common.isBlank(rowData.utmcsr)?"":rowData.utmcsr;
	 						var utmcmd = common.isBlank(rowData.utmcmd)?"":rowData.utmcmd;
	 						var html = "";
	 						html += "<a onclick=\"dasChartRoomStatisticsSourceCount.viewDetail2('"
	 							+rowData.dataTime+"', '"+behaviorType+"', '"+utmcsr+"', '"+utmcmd+"')\">" + value + "</a>";
	 						return html;
	 					}else{
	 						return value;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var depositCount =  rowData.depositCount+"";
					if(null != depositCount && depositCount.indexOf("%")>0){
						var num = value.replace("%","");
						if(num <= 0){
							return 'color:red;';
						}else{
							return 'color:green;';
						}
					}
				}}
 			] ];
		$('#' + dasChartRoomStatisticsSourceCount.dataTreeGrid).treegrid(easyui.defaultOption).treegrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'id', // 唯一字段
 			treeField : 'utmcsrAndUtmcmd',
			method : 'post',
			animate:true,
			collapsible:true,
			sortName : 'dataTime',
			sortOrder : 'desc',
			rowStyler:function(rowIndex,rowData){  
		        if(!common.isBlank(rowIndex.utmcsrAndUtmcmd) && rowIndex.utmcsrAndUtmcmd.indexOf("/") > 0){  
		        	 return 'background:#E5E5E5'; 
		        	//return 'background-color:#6293BB;color:#fff;font-weight:bold;font-size:12px;';
		        }
		    },
			onExpand : function(row){
				common.iFrameHeight();
			},onLoadSuccess : function(data) {
				common.iFrameHeight();
			}
		});
	},
	
	/**
	 * 刷新
	 */
	refresh: function(id){
		$('#'+dasChartRoomStatisticsSourceCount.dataGridId).treegrid('reload', id);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasChartRoomStatisticsSourceCount.searchFormId).form('reset');
		
		$("#utmcsrChecked").val("true");
		$("#utmcmdChecked").val("true");
		$("#utmccnChecked").val("false");
		$("#utmcctChecked").val("false");
		$("#utmctrChecked").val("false");
		
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
		var queryParams = dasChartRoomStatisticsSourceCount.getQueryParams();
		var url = BASE_PATH + "DasChartRoomController/exportExcelRoomSourceCount?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 行为详细汇总
	 */
	viewDetail2 : function(dataTime, behaviorType, utmcsr, utmcmd){
		var devicetype = $("#devicetypeSearch").combobox('getValue');
		var url = "DasChartRoomController/pageRoomBehavior?dataTime=" + dataTime
			+ "&behaviorType=" + behaviorType
			+ "&utmcsr=" + utmcsr
			+ "&utmcmd=" + utmcmd
		    + "&devicetype=" + devicetype;
		parent.index.addOrUpdateTabs("行为详细汇总", url);
	},
	/**
	 * 行为详细汇总
	 */
	viewDetail3 : function(dataTime, behaviorType, utmcsr, utmcmd, utmccn, utmcct, utmctr){
		var devicetype = $("#devicetypeSearch").combobox('getValue');
		var url = "DasChartRoomController/pageRoomBehavior?dataTime=" + dataTime
			+ "&behaviorType=" + behaviorType
			+ "&utmcsr=" + utmcsr
			+ "&utmcmd=" + utmcmd
			+ "&utmccn=" + utmccn
			+ "&utmcct=" + utmcct
			+ "&utmctr=" + utmctr
		    + "&devicetype=" + devicetype;
		parent.index.addOrUpdateTabs("行为详细汇总", url);
	}
	
}




















