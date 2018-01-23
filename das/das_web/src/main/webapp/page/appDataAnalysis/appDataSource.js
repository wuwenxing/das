$(function() {
	appDataSource.init();
});

var appDataSource = {
	dataGridId: "dataGrid",
	searchFormId: "searchForm",
	/**
	 * 初始化
	 */
	init:function(){
		$("#dateTimeTd").hide();
		
		$("select[multiple='multiple']").multiselect({
			noneSelectedText: "---请选择---", 
			selectedList: 10 // 0-based index
		}).multiselectfilter();
		$(".ui-multiselect").css("width", "160px");
		common.setEasyUiCss();
		
		appDataSource.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'appDataSourceController/appDataSourcePageList';
		var queryParams = {
		};
		queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['platformVersion'] = $("#platformVersionSearch").val()+"";
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['platformName'] = $("#platformNameSearch").val()+"";
		queryParams['channel'] = $("#channelSearch").val()+"";
		
		queryParams['userType'] = $("#userTypeSearch").val()+"";
		queryParams['eventCategory'] = $("#eventCategorySearch").val()+"";
		queryParams['eventAction'] = $("#eventActionSearch").val()+"";
		queryParams['eventLabel'] = $("#eventLabelSearch").val()+"";
		queryParams['eventValue'] = $("#eventValueSearch").val()+"";
		
		queryParams['platformVersionChecked'] = $("#platformVersionChecked").val();// 版本复选框
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();// 设备平台复选框
		queryParams['platformTypeChecked'] = $("#platformTypeChecked").val();// 交易平台复选框
		queryParams['platformNameChecked'] = $("#platformNameChecked").val();// 马甲包复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道字复选框
		queryParams['userTypeChecked'] = $("#userTypeChecked").val();// 客户类型字复选框
		
		var columns = [ [
                            //{field:'ck',checkbox:true },
                            {field : 'datetime', title : '日期', sortable : false, width : 100},
                            {field : 'weeks', title : '周',sortable : false, width : 50},
            				
			 	 			{field : 'eventCategory', title : '事件类别', sortable : false, width : 100},
			 	 			{field : 'eventAction', title : '事件操作', sortable : false, width : 150},	 			
			 	 			{field : 'eventLabel', title : '事件标签', sortable : false, width : 100},
			 	 			{field : 'eventValue', title : '事件参数', sortable : false, width : 100},
			 	 			
			 	 			{field : 'deviceType', title : '设备类型',hidden : true, sortable : false, width : 100,
			 	 				formatter : function(value, rowData, rowIndex) {
									if(rowData.deviceType == 1){
										return "ANDROID";
									}else if(rowData.deviceType == 2){
										return "IOS";
									}else if(rowData.deviceType == 3){
										return "PCUI";
									}else if(rowData.deviceType == 4){
										return "WEBUI";
									}else{
										return rowData.deviceType;
									}
			 	 			}},
			 	 			{field : 'platformType', title : '交易平台',hidden : true, sortable : false, width : 100},	
			 	 			{field : 'platformName', title : '马甲包',hidden : true, sortable : false, width : 250},	
			 	 			{field : 'platformVersion', title : '版本',hidden : true, sortable : false, width : 100},
			 	 			{field : 'userType', title : '客户类型',hidden : true, sortable : false, width : 100,
			 	 				formatter : function(value, rowData, rowIndex) {
									if(rowData.userType == '1'){
										return "游客";
									}else if(rowData.userType == '2'){
										return "模拟";
									}else if(rowData.userType == '3'){
										return "真实";
									}else{
										return rowData.userType;
									}
			 	 			}},
			 	 			{field : 'channel', title : '渠道',hidden : true, sortable : false, width : 150},
			 	 			
			 	 			//{field : 'deviceid', title : '设备唯一标识', sortable : false, width : 100},
			 	 			{field : 'deviceidNum', title : '触发次数', sortable : false, width : 100},
			 	 			{field : 'deviceidCount', title : '触发设备数', sortable : false, width : 100}
			  			] ];
		
		$('#' + appDataSource.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : '',
			sortOrder : '',
    		collapsible: true,
    		fitColumns:false,
    		singleSelect: false,		
    		selectOnCheck: true,	
    		checkOnSelect: true,
    		showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},
		    onLoadSuccess : function(data){
		    	$(".datagrid-footer .datagrid-row [class$='datetime']").text("小计：");
		    	
		    	var reportType = $("#reportTypeSearch").combobox('getValue');
				if(reportType == 'weeks'){
					$("#" + appDataSource.dataGridId).datagrid('showColumn', 'weeks');
				}else{
					$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'weeks');
				}
				
				if(reportType == 'hours' || reportType == 'days' || reportType == 'weeks' || reportType == 'months'){
					$("#" + appDataSource.dataGridId).datagrid('showColumn', 'datetime');
				}else{
					$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'datetime');
				}
				
				
		    	var checkboxs = $(".datagrid-header-row input[type='checkbox'][name=customcheckbox]");
		    	if(checkboxs.length == 0){
		    		var tds = $(".datagrid-header-row td");
			    	for(var i = 1;i< tds.length;i++){
			    		/*if($(tds[i]).attr('field') == 'eventCategory'){
			    			$(tds[i]).children(".datagrid-cell").append("<input type='checkbox' checked='checked'  field='"+$(tds[i]).attr('field')+"' name='customcheckbox'/>");
			    		}else if($(tds[i]).attr('field') != 'deviceidNum' && $(tds[i]).attr('field') != 'deviceidCount' && $(tds[i]).attr('field') != 'datetime' && $(tds[i]).attr('field') != 'weeks'){
			    			$(tds[i]).children(".datagrid-cell").append("<input type='checkbox'  field='"+$(tds[i]).attr('field')+"' name='customcheckbox'/>");
			    		}*/
			    		
			    		if(common.isBlank(reportType) && $(tds[i]).attr('field') == 'eventCategory'){
			    			$(tds[i]).children(".datagrid-cell").append("<input type='checkbox' checked='checked'  field='"+$(tds[i]).attr('field')+"' name='customcheckbox'/>");
			    		}else if(common.isBlank(reportType) && $(tds[i]).attr('field') != 'deviceidNum' && $(tds[i]).attr('field') != 'deviceidCount' && $(tds[i]).attr('field') != 'weeks'){
			    			$(tds[i]).children(".datagrid-cell").append("<input type='checkbox'  field='"+$(tds[i]).attr('field')+"' name='customcheckbox'/>");
			    		}else{
			    			$(tds[i]).children(".datagrid-cell").append("<input type='checkbox'  field='"+$(tds[i]).attr('field')+"' name='customcheckbox'/>");
			    		}
			    		
			    	}
			    	
			    	appDataSourceChart.loadChartData(1,'eventCategory');
			    	
			    	$(".datagrid-header-row input[type='checkbox'][name=customcheckbox]").click(function(e) {
			    		var field = $(this).attr("field");
			    		var isChecked =  $(this).is(":checked"); 
			    		var checkboxs = $(".datagrid-header-row input[type='checkbox'][name=customcheckbox]");
			    		if(isChecked){
				    		for(var i = 0;i<checkboxs.length;i++){
				    			if($(checkboxs[i]).attr("field")!=field){
				    				$(checkboxs[i]).prop('checked',false);
				    			}
				    		}
				    		appDataSourceChart.loadChartData(1,field);
			    		}else{
			    			var flag = false;
				    		for(var i = 0;i<checkboxs.length;i++){
				    			if($(checkboxs[i]).is(":checked")){
				    				flag = true;
				    				break;
				    			}
				    		}
				    		if(flag == false){
				    			$(this).prop('checked',true);
				    			$.messager.alert('提示','请至少选择一列!');
				    		}
			    		}
			        });
		    	}else{		    		
		    		var flag = false;
		    		var checkboxs = $(".datagrid-header-row input[type='checkbox'][name=customcheckbox]");
		    		for(var i = 0;i<checkboxs.length;i++){
		    			console.log($(checkboxs[i]).attr("field")+" : "+$(checkboxs[i]).is(":checked"));
		    			if($(checkboxs[i]).is(":checked")){
		    				flag = true;
		    				break;
		    			}
		    		}
		    		
		    		if(!flag){
		    			if(common.isBlank(reportType)){
		    				$(".datagrid-header-row input[type='checkbox'][field=eventCategory]").prop('checked',true);
		    				appDataSourceChart.loadChartData(1,'eventCategory');
		    			}else{
		    				$(".datagrid-header-row input[type='checkbox'][field=datetime]").prop('checked',true);
		    				appDataSourceChart.loadChartData(1,'datetime');
		    			}
		    		}
		    		
		    	}
		    	
		    	appDataSource.showColumn();
		    	common.iFrameHeight();
		    }
		});
	},
	getQueryParams: function() {
		var options = $("#" + appDataSource.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
			
		var reportType = $("#reportTypeSearch").combobox('getValue');
		queryParams['reportType'] = reportType;
		if(reportType == 'hours'){
			queryParams['startTime'] = $("#startTimeSearchHour").datetimebox('getValue');
			queryParams['endTime'] = $("#endTimeSearchHour").datetimebox('getValue');
		}else{
			queryParams['startTime'] = $("#startTimeSearch").datetimebox('getValue');
			queryParams['endTime'] = $("#endTimeSearch").datetimebox('getValue');
		}
		
		queryParams['platformVersion'] = $("#platformVersionSearch").val()+"";
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['platformName'] = $("#platformNameSearch").val()+"";
		queryParams['channel'] = $("#channelSearch").val()+"";
		
		queryParams['userType'] = $("#userTypeSearch").combobox('getValue');
		queryParams['eventCategory'] = $("#eventCategorySearch").val()+"";
		queryParams['eventAction'] = $("#eventActionSearch").val()+"";
		queryParams['eventLabel'] = $("#eventLabelSearch").val()+"";
		queryParams['eventValue'] = $("#eventValueSearch").val()+"";
		
		
		queryParams['eventCategoryChecked'] = $("#eventCategoryChecked").val();// 事件类别复选框
		queryParams['eventActionChecked'] = $("#eventActionChecked").val();// 事件操作复选框
		queryParams['eventLabelChecked'] = $("#eventLabelChecked").val();// 事件标签复选框
		queryParams['eventValueChecked'] = $("#eventValueChecked").val();// 事件参数复选框
		
		queryParams['platformVersionChecked'] = $("#platformVersionChecked").val();// 版本复选框
		queryParams['deviceTypeChecked'] = $("#deviceTypeChecked").val();// 设备平台复选框
		queryParams['platformTypeChecked'] = $("#platformTypeChecked").val();// 交易平台复选框
		queryParams['platformNameChecked'] = $("#platformNameChecked").val();// 马甲包复选框
		queryParams['channelChecked'] = $("#channelChecked").val();// 渠道字复选框
		queryParams['userTypeChecked'] = $("#userTypeChecked").val();// 客户类型字复选框
		
				
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		appDataSource.getQueryParams();
		
		appDataSource.showColumn();
		
		common.loadGrid(appDataSource.dataGridId);
	},
	
	showColumn:function(){
		if($("#eventCategoryChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'eventCategory');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'eventCategory');
		}
		
		if($("#eventActionChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'eventAction');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'eventAction');
		}
		
		if($("#eventLabelChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'eventLabel');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'eventLabel');
		}
		
		if($("#eventValueChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'eventValue');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'eventValue');
		}
		
		if($("#platformVersionChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'platformVersion');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'platformVersion');
		}
		
		if($("#deviceTypeChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'deviceType');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'deviceType');
		}
		
		if($("#platformTypeChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'platformType');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'platformType');
		}
		
		if($("#platformNameChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'platformName');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'platformName');
		}
		
		if($("#channelChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'channel');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'channel');
		}
		
		if($("#userTypeChecked").val() == 'true'){
			$("#" + appDataSource.dataGridId).datagrid('showColumn', 'userType');
		}else{
			$("#" + appDataSource.dataGridId).datagrid('hideColumn', 'userType');
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
		
		$('#' + appDataSource.searchFormId).form('reset');
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
		var queryParams = appDataSource.getQueryParams();
		var url = BASE_PATH + "appDataSourceController/exportExcelAppDataSource?";
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
			appDataSource.setCheckboxDisabled(false);
			//appDataSource.setCheckboxChecked(false);
			
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
			appDataSource.setCheckboxDisabled(false);
			//appDataSource.setCheckboxChecked(false);
			
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
			appDataSource.setCheckboxDisabled(false);
			//appDataSource.setCheckboxChecked(false);
			
			
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
			appDataSource.setCheckboxDisabled(false);
			//appDataSource.setCheckboxChecked(false);
			
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
			appDataSource.setCheckboxDisabled(true);
			appDataSource.setCheckboxChecked(true);
			
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
	},
	setCheckboxDisabled: function(obj){	
		$("#eventCategoryChecked").attr("disabled",obj);
		$("#eventActionChecked").attr("disabled",obj);
		$("#eventLabelChecked").attr("disabled",obj);
		$("#eventValueChecked").attr("disabled",obj);
	},
	setCheckboxChecked: function(obj){	
		$("#eventCategoryChecked").prop('checked',obj);
		$("#eventActionChecked").prop('checked',obj);
		$("#eventLabelChecked").prop('checked',obj);
		$("#eventValueChecked").prop('checked',obj);
		$("#eventCategoryChecked").attr('value',obj);
		$("#eventActionChecked").attr("value",obj);
		$("#eventLabelChecked").attr("value",obj);
		$("#eventValueChecked").attr("value",obj);
	}
	
	
}
