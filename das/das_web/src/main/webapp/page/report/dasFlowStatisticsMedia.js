$(function() {		
	dasFlowStatisticsMedia.init();
});

var dasFlowStatisticsMedia = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	dataTreeGrid:"dataTreeGrid",
	/**
	 * 初始化
	 */
	init:function(){
		dasFlowStatisticsMedia.loadDataGrid();
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
		
		var platformTypeInput = $("#platformTypeInput").val();
		if(null != platformTypeInput && ""!= platformTypeInput){
			if("pc" == platformTypeInput){
				$("#platformTypeSearch").combobox('setValue',"PC端" );
			}else if("mobile" == platformTypeInput){
				$("#platformTypeSearch").combobox('setValue',"移动端" );
			}
		}
		
		common.setEasyUiCss();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasFlowStatisticsController/pageListMedia';
		var queryParams = {
		};
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();// 媒介复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		var platformTypeInput = $("#platformTypeInput").val();
		if(null != platformTypeInput && ""!= platformTypeInput){
			queryParams['platformType'] = platformTypeInput;
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
	 			{field : 'visitCount', title : '访问次数', sortable : true, width : 50},
	 			{field : 'advisoryCountQQ', title : 'QQ咨询', sortable : true, width : 50},
	 			{field : 'advisoryCountLIVE800', title : 'LIVE800咨询', sortable : true, width : 50},
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
	 						html += "<a onclick=\"dasFlowStatisticsMedia.viewDetail3('"
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
	 						html += "<a onclick=\"dasFlowStatisticsMedia.viewDetail3('"
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
	 						html += "<a onclick=\"dasFlowStatisticsMedia.viewDetail3('"
	 							+rowData.dataTime+"', '"+behaviorType+"', '"+utmcsr+"', '"+utmcmd+"', '"
	 							+utmccn+"', '"+utmcct+"', '"+utmctr+"')\">" + value + "</a>";
	 						return html;
	 					}else{
	 						return value;
	 					}
				}},
				{field : 'platformType', title : '访问客户端', sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.platformType == '0'){
							return "PC端";
						}else if(rowData.platformType == '1'){
							return "移动端";
						}else{
							// 统计时，为空代表所有客户端
							if(!common.isBlank(rowData.dataTime)){
								return "(全部)";
							}else{
								// 小计行的该列显示为空
								return "";
							}
						}
	 				}
				}
 			] ];
		$('#' + dasFlowStatisticsMedia.dataGridId).datagrid(easyui.defaultOption).datagrid({
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
				dasFlowStatisticsMediaChart.loadChartData(1);
				dasFlowStatisticsMediaChart.loadChartData(2);
				dasFlowStatisticsMediaChart.loadChartData(3);
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
		var options = $("#" + dasFlowStatisticsMedia.dataGridId).datagrid('options');
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
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['channelIds'] = $("#channelIds").val()+"";		
		queryParams['startTimeCompare'] = $("#startTimeCompareSearch").datebox('getValue');
		queryParams['endTimeCompare'] = $("#endTimeCompareSearch").datebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasFlowStatisticsMedia.getQueryParams();
		var startTime = $("#startTimeSearch").datebox('getValue');
		var endTime = $("#endTimeSearch").datebox('getValue');
		var startTimeCompareSearch = $("#startTimeCompareSearch").datebox('getValue');
		var endTimeCompareSearch = $("#endTimeCompareSearch").datebox('getValue');
		if(!common.isBlank(startTimeCompareSearch) && !common.isBlank(endTimeCompareSearch) && (startTimeCompareSearch != startTime || endTimeCompareSearch != endTime)){
			$("#dataGridDiv").hide();
			$("#dataTreeGridDiv").show();
			dasFlowStatisticsMedia.loadTreeGrid();
		}else{
			$("#dataGridDiv").show();
			$("#dataTreeGridDiv").hide();
			if($("#utmcsrChecked").val() == 'true'){
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('showColumn', 'utmcsr');
			}else{
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('hideColumn', 'utmcsr');
			}
			if($("#utmcmdChecked").val() == 'true'){
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('showColumn', 'utmcmd');
			}else{
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('hideColumn', 'utmcmd');
			}
			if($("#utmccnChecked").val() == 'true'){
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('showColumn', 'utmccn');
			}else{
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('hideColumn', 'utmccn');
			}
			if($("#utmcctChecked").val() == 'true'){
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('showColumn', 'utmcct');
			}else{
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('hideColumn', 'utmcct');
			}
			if($("#utmctrChecked").val() == 'true'){
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('showColumn', 'utmctr');
			}else{
				$("#" + dasFlowStatisticsMedia.dataGridId).datagrid('hideColumn', 'utmctr');
			}
			common.loadGrid(dasFlowStatisticsMedia.dataGridId);
		}
	},
	
	/**
	 * 加载treeGrid
	 */
	loadTreeGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasFlowStatisticsController/pageListMedia';
		var queryParams = {
		};
		queryParams['utmcsr'] = $("#utmcsrSearch").val();// 来源
		queryParams['utmcmd'] = $("#utmcmdSearch").val();// 媒介
		queryParams['utmcsrChecked'] = $("#utmcsrChecked").val();// 来源复选框
		queryParams['utmcmdChecked'] = $("#utmcmdChecked").val();// 媒介复选框
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").datebox('getValue');
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
	 						html += "<a onclick=\"dasFlowStatisticsMedia.viewDetail2('"
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
	 						html += "<a onclick=\"dasFlowStatisticsMedia.viewDetail2('"
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
	 						html += "<a onclick=\"dasFlowStatisticsMedia.viewDetail2('"
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
		$('#' + dasFlowStatisticsMedia.dataTreeGrid).treegrid(easyui.defaultOption).treegrid({
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
		$('#'+dasFlowStatisticsMedia.dataGridId).treegrid('reload', id);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasFlowStatisticsMedia.searchFormId).form('reset');
		
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
		var queryParams = dasFlowStatisticsMedia.getQueryParams();
		var url = BASE_PATH + "DasFlowStatisticsController/exportExcelMedia?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 行为详细汇总
	 */
	viewDetail2 : function(dataTime, behaviorType, utmcsr, utmcmd){
		var platformType = $("#platformTypeSearch").combobox('getValue');
		var url = "DasUserBehaviorController/pageByFlowStatistics?dataTime=" + dataTime
			+ "&behaviorType=" + behaviorType
			+ "&utmcsr=" + utmcsr
			+ "&utmcmd=" + utmcmd
		    + "&platformType=" + platformType;
		parent.index.addOrUpdateTabs("行为详细汇总", url);
	},
	/**
	 * 行为详细汇总
	 */
	viewDetail3 : function(dataTime, behaviorType, utmcsr, utmcmd, utmccn, utmcct, utmctr){
		var platformType = $("#platformTypeSearch").combobox('getValue');
		var url = "DasUserBehaviorController/pageByFlowStatistics?dataTime=" + dataTime
			+ "&behaviorType=" + behaviorType
			+ "&utmcsr=" + utmcsr
			+ "&utmcmd=" + utmcmd
			+ "&utmccn=" + utmccn
			+ "&utmcct=" + utmcct
			+ "&utmctr=" + utmctr
		    + "&platformType=" + platformType;
		parent.index.addOrUpdateTabs("行为详细汇总", url);
	}
	
}




















