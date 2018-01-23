
$(function() {
	appDataAnalysisDetails.init();
});

var appDataAnalysisDetails = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
				
		$("select[multiple='multiple']").multiselect({
			noneSelectedText: "---请选择---", 
			selectedList: 10 // 0-based index
		}).multiselectfilter();
		$(".ui-multiselect-menu").css("width", "217px");
		
		
		var channel = $("#channelSearch").val();
		if(null != channel && "" != channel){
			$('#channelIds option').each(function(){
				if(channel.indexOf(this.value)!=-1)this.selected=true;
			});
			$(".ui-multiselect-menu").css("width", "217px");
			$("select[multiple='multiple']").multiselect("refresh");
		}
		
		var devicetype = $("#devicetypeSearch").val();
		if(null != devicetype && ""!= devicetype){
			if("1" == devicetype){
				$("#platformTypeSearch").combobox('setValue',"Android" );
			}else if("2" == devicetype){
				$("#platformTypeSearch").combobox('setValue',"IOS" );
			}
		}
		
		common.setEasyUiCss();
		
		appDataAnalysisDetails.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'appDataAnalysisController/appDataAnalysisDetailsPage';
		var queryParams = {
		};
		queryParams['devicetype'] = $("#devicetypeSearch").val();
		queryParams['dtMonth'] = $("#dtMonthSearch").val();
		queryParams['behaviorType'] = $("#behaviorTypeSearch").val();
		
		queryParams['reportType'] = $("#reportTypeSearch").val();
		queryParams['channel'] = $("#channelSearch").val();
		queryParams['field'] = $("#fieldSearch").val();
		queryParams['paramValue'] = $("#paramValueSearch").val();
		
		queryParams['channelChecked'] = $("#channelCheckedSearch").val();
		queryParams['devicetypeChecked'] = $("#devicetypeCheckedSearch").val();
		
		var columns = [ [
		 			    {field : 'dtMonth', title : '日期', sortable : true, width : 50},
		 	 			{field : 'channel', title : '渠道', sortable : true, width : 50},
		 	 			{field : 'devicetype', title : '系统类型', sortable : true, width : 50},
		 	 			{field : 'paramValue', title : '活跃用户\新用户', sortable : true, width : 50},
		 	 			{field : 'mavgValue', title : '上月均值', sortable : true, width : 50}
		  			] ];
		$('#' + appDataAnalysisDetails.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'dt_month',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data) {
				$("div.datagrid-footer [class$='dataTime']").text("小计：");
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + appDataAnalysisDetails.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
	
		queryParams['field'] = $("#fieldSearch").val();
		queryParams['paramValue'] = $("#paramValueSearch").val();
		queryParams['dtMonth'] = $("#startTimeSearch").datebox('getValue');
		queryParams['devicetype'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['channel'] = $("#channelIds").val()+"";
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		appDataAnalysisDetails.getQueryParams();
		common.loadGrid(appDataAnalysisDetails.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + appDataAnalysisDetails.searchFormId).form('reset');
		
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMM,
			parser:easyui.parserYYYYMM
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
		var queryParams = appDataAnalysisDetails.getQueryParams();
		var url = BASE_PATH + "appDataAnalysisController/exportExcelAnalysisDetails?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}

