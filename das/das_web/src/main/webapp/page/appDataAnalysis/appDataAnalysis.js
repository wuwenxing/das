$(function() {
	appDataAnalysis.init();
});

var appDataAnalysis = {
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
		
		common.setEasyUiCss();
		
		appDataAnalysis.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'appDataAnalysisController/pageList';
		var queryParams = {
		};
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['channel'] = $("#channelIds").val()+"";
		queryParams['deviceType'] = $("#platformTypeSearch").combobox('getValue');
		
		queryParams['channelChecked'] = $("#channelChecked").is(':checked');
		queryParams['devicetypeChecked'] = $("#devicetypeChecked").is(':checked');
		
		var frozenColumns = appDataAnalysis.getFrozenColumns();	
		var columns = appDataAnalysis.getColumns();
		
		$('#' + appDataAnalysis.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:frozenColumns,
			columns : columns,
			sortName : '',
			sortOrder : 'desc',
            showFooter: true,
    		collapsible: true,
    		fitColumns:false,
			onDblClickRow : function(rowIndex, rowData) {
			},
			onLoadSuccess : function(data){	
				var reportType = $("#reportTypeSearch").combobox('getValue');
				if("days" == reportType){
					$("div.datagrid-footer [class$='dtDay']").text("小计：");
				}else{
					$("div.datagrid-footer [class$='dtMonth']").text("小计：");
				}
				
				common.iFrameHeight();	
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + appDataAnalysis.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['channel'] = $("#channelIds").val()+"";
		queryParams['deviceType'] = $("#platformTypeSearch").combobox('getValue');
		queryParams['channelChecked'] = $("#channelChecked").is(':checked');
		queryParams['devicetypeChecked'] = $("#devicetypeChecked").is(':checked');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		appDataAnalysis.getQueryParams();
		//common.loadGrid(appDataAnalysis.dataGridId);
		$('#' + appDataAnalysis.dataGridId).datagrid({frozenColumns:appDataAnalysis.getFrozenColumns(),columns:appDataAnalysis.getColumns()});
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + appDataAnalysis.searchFormId).form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYY,
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
		var queryParams = appDataAnalysis.getQueryParams();
		queryParams['channelChecked'] = $("#channelChecked").is(':checked');
		queryParams['devicetypeChecked'] = $("#devicetypeChecked").is(':checked');
		var url = BASE_PATH + "appDataAnalysisController/exportExcelAnalysis?";
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
			$("#startTimeSearch").datebox('setValue', $("#lessOneMonths").val());
			$("#endTimeSearch").datebox('setValue', $("#dayEnd").val());
		}else if(newValue == 'weeks'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$("#startTimeSearch").datebox('setValue', $("#lessThreeMonths").val());
			$("#endTimeSearch").datebox('setValue', $("#monthEnd").val());
		}else if(newValue == 'months'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$("#startTimeSearch").datebox('setValue', $("#lessOneYears").val());
			$("#endTimeSearch").datebox('setValue', $("#monthEnd").val());
		}
		common.setEasyUiCss();
	},
	
	getFrozenColumns:function(){
		var reportType = $("#reportTypeSearch").combobox('getValue');
		if("days" == reportType){
			var frozenColumns = [[{field : 'dtDay', title : '日期', sortable : true, align:'center',width : 90}]];				
		}
		
		if("months" == reportType){
			var frozenColumns = [[{field : 'dtMonth', title : '日期', sortable : true, align:'center',width : 90}]];	
		}
        if("weeks" == reportType){
        	var frozenColumns = [[
        	                      {field : 'dtMonth', title : '日期', sortable : true, align:'center',width : 90},
        	                      {field : 'dtWeek', title : '周', sortable : false, align:'center',width : 90}
        	                    ]];	
		}
		
		return frozenColumns;
	},
	
	getColumns:function(){
		var channelChecked = $("#channelChecked").is(':checked');
		var devicetypeChecked = $("#devicetypeChecked").is(':checked');
		var demoRateChecked = $("#demoRateChecked").is(':checked');
		var realRateChecked = $("#realRateChecked").is(':checked');	
		//渠道、系统类型、模拟率、真实率：默认不显示
		if(!channelChecked && !devicetypeChecked && !demoRateChecked && !realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},			   				
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280}			   			
			   			],
			   		    [
			   		        //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},
			   	            
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}}
			   	            
			    			] ];
		         return columns;
		}
		//渠道显示
		if(channelChecked && !devicetypeChecked && !demoRateChecked && !realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'channel', title : '渠道', rowspan: 2, sortable : false, align:'center',width : 60},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},
			   	            
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}}
			   	            
			    			] ];
		         return columns;
		}
		//系统类型显示
		if(!channelChecked && devicetypeChecked && !demoRateChecked && !realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'devicetype', title : '设备类型', rowspan: 2, sortable : false, align:'center',width : 60,
			   					formatter : function(value, rowData, rowIndex) {
									if(rowData.devicetype == '1'){
										return "Android";
									}else if(rowData.devicetype == '2'){
										return "IOS";
									}else{
										return rowData.devicetype;
									}
				 				}},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},
			   	            
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}}
			   	            			   	            
			    			] ];
		         return columns;
		}
		
		//模拟率显示
		if(!channelChecked && !devicetypeChecked && demoRateChecked && !realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '模拟率', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},
							
							 {field : 'demoNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.demoNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
				   	         {field : 'demoNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.demoNewuserTradeRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
						     }},
				   	         {field : 'demoOlduserTradeRate', title : '老客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.demoOlduserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
							}},		     
				             {field : 'demoAllTradeRate', title : '总交易率', sortable : true, width : 70,
											styler:function(value, rowData, rowIndex){
												var values =  rowData.demoAllTradeRate.replace("%","");
												if(!common.isBlank(values) && values > 0){
													return 'color:red;';					
												}else if(!common.isBlank(values) && values < 0){
													return 'color:green;';
												}
							}},
			   	            
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}}
		               	   	            
			   	            
			    			] ];
		         return columns;
		}
		
		//系统类型显示
		if(!channelChecked && !devicetypeChecked && !demoRateChecked && realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实率', colspan: 3, sortable : false, align:'center',width : 210}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},
							
			   	            
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}},
			   	            
			                {field : 'realNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.realNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	            {field : 'realNewuserDepositRate', title : '新客入金率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.realNewuserDepositRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},	   	 			
			                {field : 'realNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.realNewuserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}}
			   	            
			    			] ];
		         return columns;
		}
		
		//系统类型显示
		if(channelChecked && devicetypeChecked && !demoRateChecked && !realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'channel', title : '渠道', rowspan: 2, sortable : false, align:'center',width : 60},
			   				{field : 'devicetype', title : '系统类型', rowspan: 2, sortable : false, align:'center',width : 60,
			   					formatter : function(value, rowData, rowIndex) {
									if(rowData.devicetype == '1'){
										return "Android";
									}else if(rowData.devicetype == '2'){
										return "IOS";
									}else{
										return rowData.devicetype;
									}
				 				}},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280}
			   			],
			   		    [
			                  //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},
							
			   	            
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}}
			   	            
			   	            
			    			] ];
		         return columns;
		}
		//系统类型显示
		if(channelChecked && !devicetypeChecked && demoRateChecked && !realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'channel', title : '渠道', rowspan: 2, sortable : false, align:'center',width : 60},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '模拟率', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280}
			   			],
			   		    [
			   		  //启动客户
		                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
		                	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
			 							return html;
			 						}
			 						return rowData.activeuser;
			 					}else{
			 						return rowData.activeuser;
			 					}
						}},
		   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
			 							return html;
			 						}
			 						return rowData.newuser;
			 					}else{
			 						return rowData.newuser;
			 					}
						}},
		                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
		                	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
			 							return html;
			 						}
			 						return rowData.olduserDemo;
			 					}else{
			 						return rowData.olduserDemo;
			 					}
						}},
		                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
		                	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
			 							return html;
			 						}
			 						return rowData.olduserActivate;
			 					}else{
			 						return rowData.olduserActivate;
			 					}
						}},
						
		   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
			 							return html;
			 						}
			 						return rowData.olduserReal;
			 					}else{
			 						return rowData.olduserReal;
			 					}
						}},
		   	            //模拟
		                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
		                	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
			 							return html;
			 						}
			 						return rowData.demoAccount;
			 					}else{
			 						return rowData.demoAccount;
			 					}
						}},
		   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
			 							return html;
			 						}
			 						return rowData.demoNewuserLogin;
			 					}else{
			 						return rowData.demoNewuserLogin;
			 					}
						}},
		   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
			 							return html;
			 						}
			 						return rowData.demoNewuserTrade;
			 					}else{
			 						return rowData.demoNewuserTrade;
			 					}
						}},		     
		                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
		                	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
			 							return html;
			 						}
			 						return rowData.demoOlduserLogin;
			 					}else{
			 						return rowData.demoOlduserLogin;
			 					}
						}},
		   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
			 							return html;
			 						}
			 						return rowData.demoOlduserTrade;
			 					}else{
			 						return rowData.demoOlduserTrade;
			 					}
						}},
		   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
			 							return html;
			 						}
			 						return rowData.demoAllLogin;
			 					}else{
			 						return rowData.demoAllLogin;
			 					}
						}},
		   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
			 							return html;
			 						}
			 						return rowData.demoAllTrade;
			 					}else{
			 						return rowData.demoAllTrade;
			 					}
						}},
						 {field : 'demoNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
							styler:function(value, rowData, rowIndex){
								var values =  rowData.demoNewuserLoginRate.replace("%","");
								if(!common.isBlank(values) && values > 0){
									return 'color:red;';					
								}else if(!common.isBlank(values) && values < 0){
									return 'color:green;';
								}
							}},
			   	         {field : 'demoNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.demoNewuserTradeRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	         {field : 'demoOlduserTradeRate', title : '老客交易率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.demoOlduserTradeRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},		     
			             {field : 'demoAllTradeRate', title : '总交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.demoAllTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}},
		   	            
		                //真实
		   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
			 							return html;
			 						}
			 						return rowData.realAccount;
			 					}else{
			 						return rowData.realAccount;
			 					}
						}},		   	 			
		                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
			 							return html;
			 						}
			 						return rowData.realNewuserLogin;
			 					}else{
			 						return rowData.realNewuserLogin;
			 					}
						}},
		   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
			 							return html;
			 						}
			 						return rowData.realNewuserDeposit;
			 					}else{
			 						return rowData.realNewuserDeposit;
			 					}
						}},
		   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
		   	            	formatter : function(value, rowData, rowIndex) {
			 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
			 						var html = "";
			 						var reportType = $("#reportTypeSearch").combobox('getValue');
			 						if("days" == reportType && common.isBlank(rowData.footer)){
			 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
			 							return html;
			 						}
			 						return rowData.realNewuserTrade;
			 					}else{
			 						return rowData.realNewuserTrade;
			 					}
						}}
							
							

			    			] ];
		         return columns;
		}
		//系统类型显示
		if(channelChecked && !devicetypeChecked && !demoRateChecked && realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'channel', title : '渠道', rowspan: 2, sortable : false, align:'center',width : 60},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实率', colspan: 3, sortable : false, align:'center',width : 210}
			   			],
			   		    [
							//启动客户
							{field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
								formatter : function(value, rowData, rowIndex) {
										if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
												return html;
											}
											return rowData.activeuser;
										}else{
											return rowData.activeuser;
										}
							}},
							   {field : 'newuser', title : '新用户', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
												return html;
											}
											return rowData.newuser;
										}else{
											return rowData.newuser;
										}
							}},
							{field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
								formatter : function(value, rowData, rowIndex) {
										if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
												return html;
											}
											return rowData.olduserDemo;
										}else{
											return rowData.olduserDemo;
										}
							}},
							{field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
								formatter : function(value, rowData, rowIndex) {
										if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
												return html;
											}
											return rowData.olduserActivate;
										}else{
											return rowData.olduserActivate;
										}
							}},
							
							   {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
												return html;
											}
											return rowData.olduserReal;
										}else{
											return rowData.olduserReal;
										}
							}},
							   //模拟
							{field : 'demoAccount', title : '新开户', sortable : true, width : 70,
								formatter : function(value, rowData, rowIndex) {
										if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
												return html;
											}
											return rowData.demoAccount;
										}else{
											return rowData.demoAccount;
										}
							}},
							   {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
												return html;
											}
											return rowData.demoNewuserLogin;
										}else{
											return rowData.demoNewuserLogin;
										}
							}},
							   {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
												return html;
											}
											return rowData.demoNewuserTrade;
										}else{
											return rowData.demoNewuserTrade;
										}
							}},		     
							{field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
								formatter : function(value, rowData, rowIndex) {
										if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
												return html;
											}
											return rowData.demoOlduserLogin;
										}else{
											return rowData.demoOlduserLogin;
										}
							}},
							   {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
												return html;
											}
											return rowData.demoOlduserTrade;
										}else{
											return rowData.demoOlduserTrade;
										}
							}},
							   {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
												return html;
											}
											return rowData.demoAllLogin;
										}else{
											return rowData.demoAllLogin;
										}
							}},
							   {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
												return html;
											}
											return rowData.demoAllTrade;
										}else{
											return rowData.demoAllTrade;
										}
							}},
							 
							   
							//真实
							   {field : 'realAccount', title : '新开户', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
												return html;
											}
											return rowData.realAccount;
										}else{
											return rowData.realAccount;
										}
							}},		   	 			
							{field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
												return html;
											}
											return rowData.realNewuserLogin;
										}else{
											return rowData.realNewuserLogin;
										}
							}},
							   {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
												return html;
											}
											return rowData.realNewuserDeposit;
										}else{
											return rowData.realNewuserDeposit;
										}
							}},
							   {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
							   	formatter : function(value, rowData, rowIndex) {
										if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
											var html = "";
											var reportType = $("#reportTypeSearch").combobox('getValue');
											if("days" == reportType && common.isBlank(rowData.footer)){
												html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
												return html;
											}
											return rowData.realNewuserTrade;
										}else{
											return rowData.realNewuserTrade;
										}
							}},
			   	            
			                {field : 'realNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.realNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	            {field : 'realNewuserDepositRate', title : '新客入金率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.realNewuserDepositRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},	   	 			
			                {field : 'realNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.realNewuserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}}
			   	            
			    			] ];
		         return columns;
		}
		//系统类型显示
		if(!channelChecked && devicetypeChecked && demoRateChecked && !realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'devicetype', title : '设备类型', rowspan: 2, sortable : false, align:'center',width : 60,
			   					formatter : function(value, rowData, rowIndex) {
									if(rowData.devicetype == '1'){
										return "Android";
									}else if(rowData.devicetype == '2'){
										return "IOS";
									}else{
										return rowData.devicetype;
									}
				 				}},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '模拟率', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},
							 {field : 'demoNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.demoNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
				   	         {field : 'demoNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.demoNewuserTradeRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},
				   	         {field : 'demoOlduserTradeRate', title : '老客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.demoOlduserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}},		     
				             {field : 'demoAllTradeRate', title : '总交易率', sortable : true, width : 70,
											styler:function(value, rowData, rowIndex){
												var values =  rowData.demoAllTradeRate.replace("%","");
												if(!common.isBlank(values) && values > 0){
													return 'color:red;';					
												}else if(!common.isBlank(values) && values < 0){
													return 'color:green;';
												}
											}},
							
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}}

			   	            
			    			] ];
		         return columns;
		}
		//系统类型显示
		if(!channelChecked && devicetypeChecked && !demoRateChecked && realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'devicetype', title : '设备类型', rowspan: 2, sortable : false, align:'center',width : 60,
			   					formatter : function(value, rowData, rowIndex) {
									if(rowData.devicetype == '1'){
										return "Android";
									}else if(rowData.devicetype == '2'){
										return "IOS";
									}else{
										return rowData.devicetype;
									}
				 				}},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实率', colspan: 3, sortable : false, align:'center',width : 210}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},							
							
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}},	
			   	            		               
			   	            
			                {field : 'realNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.realNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	            {field : 'realNewuserDepositRate', title : '新客入金率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.realNewuserDepositRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},	   	 			
			                {field : 'realNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.realNewuserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}}
			   	            
			    			] ];
		         return columns;
		}
		
		//系统类型显示
		if(!channelChecked && !devicetypeChecked && demoRateChecked && realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '模拟率', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实率', colspan: 3, sortable : false, align:'center',width : 210}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},	
							
							{field : 'demoNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.demoNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	            {field : 'demoNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.demoNewuserTradeRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},
			   	            {field : 'demoOlduserTradeRate', title : '老客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.demoOlduserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}},		     
			                {field : 'demoAllTradeRate', title : '总交易率', sortable : true, width : 70,
											styler:function(value, rowData, rowIndex){
												var values =  rowData.demoAllTradeRate.replace("%","");
												if(!common.isBlank(values) && values > 0){
													return 'color:red;';					
												}else if(!common.isBlank(values) && values < 0){
													return 'color:green;';
												}
											}},
							
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}},	
			   	            		               
			   	            
			                {field : 'realNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.realNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	            {field : 'realNewuserDepositRate', title : '新客入金率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.realNewuserDepositRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},	   	 			
			                {field : 'realNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.realNewuserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}}
			   	            
			    			] ];
		         return columns;
		}
		//系统类型显示
		if(!channelChecked && devicetypeChecked && demoRateChecked && realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'devicetype', title : '设备类型', rowspan: 2, sortable : false, align:'center',width : 60,
			   					formatter : function(value, rowData, rowIndex) {
									if(rowData.devicetype == '1'){
										return "Android";
									}else if(rowData.devicetype == '2'){
										return "IOS";
									}else{
										return rowData.devicetype;
									}
				 				}},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '模拟率', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实率', colspan: 3, sortable : false, align:'center',width : 210}
			   			],
			   		    [
			                  //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},	
							
							 {field : 'demoNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.demoNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
				   	         {field : 'demoNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.demoNewuserTradeRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},
				   	         {field : 'demoOlduserTradeRate', title : '老客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.demoOlduserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}},		     
				             {field : 'demoAllTradeRate', title : '总交易率', sortable : true, width : 70,
											styler:function(value, rowData, rowIndex){
												var values =  rowData.demoAllTradeRate.replace("%","");
												if(!common.isBlank(values) && values > 0){
													return 'color:red;';					
												}else if(!common.isBlank(values) && values < 0){
													return 'color:green;';
												}
											}},
							
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}},	
			   	            
			                {field : 'realNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.realNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	            {field : 'realNewuserDepositRate', title : '新客入金率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.realNewuserDepositRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},	   	 			
			                {field : 'realNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.realNewuserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}}
			   	            
			    			] ];
		         return columns;
		}
		
		//系统类型显示
		if(channelChecked && devicetypeChecked && demoRateChecked && !realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'channel', title : '渠道', rowspan: 2, sortable : false, align:'center',width : 60},
			   				{field : 'devicetype', title : '设备类型', rowspan: 2, sortable : false, align:'center',width : 60,
			   					formatter : function(value, rowData, rowIndex) {
									if(rowData.devicetype == '1'){
										return "Android";
									}else if(rowData.devicetype == '2'){
										return "IOS";
									}else{
										return rowData.devicetype;
									}
				 				}},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '模拟率', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},	
							
							 {field : 'demoNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.demoNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
				   	         {field : 'demoNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.demoNewuserTradeRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},
				   	         {field : 'demoOlduserTradeRate', title : '老客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.demoOlduserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}},		     
				             {field : 'demoAllTradeRate', title : '总交易率', sortable : true, width : 70,
											styler:function(value, rowData, rowIndex){
												var values =  rowData.demoAllTradeRate.replace("%","");
												if(!common.isBlank(values) && values > 0){
													return 'color:red;';					
												}else if(!common.isBlank(values) && values < 0){
													return 'color:green;';
												}
											}},
							
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}}
			   	            
			    			] ];
		         return columns;
		}
		
		//系统类型显示
		if(channelChecked && devicetypeChecked && !demoRateChecked && realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'channel', title : '渠道', rowspan: 2, sortable : false, align:'center',width : 60},
			   				{field : 'devicetype', title : '设备类型', rowspan: 2, sortable : false, align:'center',width : 60,
			   					formatter : function(value, rowData, rowIndex) {
									if(rowData.devicetype == '1'){
										return "Android";
									}else if(rowData.devicetype == '2'){
										return "IOS";
									}else{
										return rowData.devicetype;
									}
				 				}},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实率', colspan: 3, sortable : false, align:'center',width : 210}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},	
							
							
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}},	
		   	            
			                {field : 'realNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.realNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	            {field : 'realNewuserDepositRate', title : '新客入金率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.realNewuserDepositRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},	   	 			
			                {field : 'realNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.realNewuserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}}
			   	            
			    			] ];
		         return columns;
		}
		//系统类型显示
		if(channelChecked && devicetypeChecked && demoRateChecked && realRateChecked ){
			var columns = [ 
			      			[
			   				{field : 'channel', title : '渠道', rowspan: 2, sortable : false, align:'center',width : 60},
			   				{field : 'devicetype', title : '设备类型', rowspan: 2, sortable : false, align:'center',width : 60,
			   					formatter : function(value, rowData, rowIndex) {
									if(rowData.devicetype == '1'){
										return "Android";
									}else if(rowData.devicetype == '2'){
										return "IOS";
									}else{
										return rowData.devicetype;
									}
				 				}},
			   				{field : 'allhead', title : '启动客户', colspan: 5, sortable : false, align:'center',width : 350},
			   				{field : 'devicehead', title : '模拟', colspan: 7, sortable : false, align:'center',width : 490},
			   				{field : 'channelhead', title : '模拟率', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实', colspan: 4, sortable : false, align:'center',width : 280},
			   				{field : 'channelhead', title : '真实率', colspan: 3, sortable : false, align:'center',width : 210}
			   			],
			   		    [
			                 //启动客户
			                {field : 'activeuser', title : '活跃用户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.activeuser > 0 && !common.isBlank(rowData.activeuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.activeuser+"','activeuser')\">" + rowData.activeuser + "</a>";
				 							return html;
				 						}
				 						return rowData.activeuser;
				 					}else{
				 						return rowData.activeuser;
				 					}
							}},
			   	            {field : 'newuser', title : '新用户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.newuser > 0 && !common.isBlank(rowData.newuser)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.newuser+"','newuser')\">" + rowData.newuser + "</a>";
				 							return html;
				 						}
				 						return rowData.newuser;
				 					}else{
				 						return rowData.newuser;
				 					}
							}},
			                {field : 'olduserDemo', title : '老客户-D', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserDemo > 0 && !common.isBlank(rowData.olduserDemo)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserDemo+"','olduserDemo')\">" + rowData.olduserDemo + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserDemo;
				 					}else{
				 						return rowData.olduserDemo;
				 					}
							}},
			                {field : 'olduserActivate', title : '老客户-N', sortable : false, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserActivate > 0 && !common.isBlank(rowData.olduserActivate)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserActivate+"','olduserActivate')\">" + rowData.olduserActivate + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserActivate;
				 					}else{
				 						return rowData.olduserActivate;
				 					}
							}},
							
			   	            {field : 'olduserReal', title : '老客户-R', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.olduserReal > 0 && !common.isBlank(rowData.olduserReal)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.olduserReal+"','olduserReal')\">" + rowData.olduserReal + "</a>";
				 							return html;
				 						}
				 						return rowData.olduserReal;
				 					}else{
				 						return rowData.olduserReal;
				 					}
							}},
			   	            //模拟
			                {field : 'demoAccount', title : '新开户', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAccount > 0 && !common.isBlank(rowData.demoAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAccount+"','demoAccount')\">" + rowData.demoAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAccount;
				 					}else{
				 						return rowData.demoAccount;
				 					}
							}},
			   	            {field : 'demoNewuserLogin', title : '新客户-登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserLogin > 0 && !common.isBlank(rowData.demoNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserLogin+"','demoNewuserLogin')\">" + rowData.demoNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserLogin;
				 					}else{
				 						return rowData.demoNewuserLogin;
				 					}
							}},
			   	            {field : 'demoNewuserTrade', title : '新客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoNewuserTrade > 0 && !common.isBlank(rowData.demoNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoNewuserTrade+"','demoNewuserTrade')\">" + rowData.demoNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoNewuserTrade;
				 					}else{
				 						return rowData.demoNewuserTrade;
				 					}
							}},		     
			                {field : 'demoOlduserLogin', title : '老客户-登录', sortable : true, width : 70,
			                	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserLogin > 0 && !common.isBlank(rowData.demoOlduserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserLogin+"','demoOlduserLogin')\">" + rowData.demoOlduserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserLogin;
				 					}else{
				 						return rowData.demoOlduserLogin;
				 					}
							}},
			   	            {field : 'demoOlduserTrade', title : '老客户-交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoOlduserTrade > 0 && !common.isBlank(rowData.demoOlduserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoOlduserTrade+"','demoOlduserTrade')\">" + rowData.demoOlduserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoOlduserTrade;
				 					}else{
				 						return rowData.demoOlduserTrade;
				 					}
							}},
			   	            {field : 'demoAllLogin', title : '登录总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllLogin > 0 && !common.isBlank(rowData.demoAllLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllLogin+"','demoAllLogin')\">" + rowData.demoAllLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllLogin;
				 					}else{
				 						return rowData.demoAllLogin;
				 					}
							}},
			   	            {field : 'demoAllTrade', title : '交易总人数', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.demoAllTrade > 0 && !common.isBlank(rowData.demoAllTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.demoAllTrade+"','demoAllTrade')\">" + rowData.demoAllTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.demoAllTrade;
				 					}else{
				 						return rowData.demoAllTrade;
				 					}
							}},	
							   
			                {field : 'demoNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.demoNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	            {field : 'demoNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.demoNewuserTradeRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},
			   	            {field : 'demoOlduserTradeRate', title : '老客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.demoOlduserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}},		     
			                {field : 'demoAllTradeRate', title : '总交易率', sortable : true, width : 70,
											styler:function(value, rowData, rowIndex){
												var values =  rowData.demoAllTradeRate.replace("%","");
												if(!common.isBlank(values) && values > 0){
													return 'color:red;';					
												}else if(!common.isBlank(values) && values < 0){
													return 'color:green;';
												}
											}},
							
			                //真实
			   	            {field : 'realAccount', title : '新开户', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realAccount > 0 && !common.isBlank(rowData.realAccount)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realAccount+"','realAccount')\">" + rowData.realAccount + "</a>";
				 							return html;
				 						}
				 						return rowData.realAccount;
				 					}else{
				 						return rowData.realAccount;
				 					}
							}},		   	 			
			                {field : 'realNewuserLogin', title : '新客登录', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserLogin > 0 && !common.isBlank(rowData.realNewuserLogin)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserLogin+"','realNewuserLogin')\">" + rowData.realNewuserLogin + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserLogin;
				 					}else{
				 						return rowData.realNewuserLogin;
				 					}
							}},
			   	            {field : 'realNewuserDeposit', title : '新客入金', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserDeposit > 0 && !common.isBlank(rowData.realNewuserDeposit)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserDeposit+"','realNewuserDeposit')\">" + rowData.realNewuserDeposit + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserDeposit;
				 					}else{
				 						return rowData.realNewuserDeposit;
				 					}
							}},
			   	            {field : 'realNewuserTrade', title : '新客户交易', sortable : true, width : 70,
			   	            	formatter : function(value, rowData, rowIndex) {
				 					if(rowData.realNewuserTrade > 0 && !common.isBlank(rowData.realNewuserTrade)){
				 						var html = "";
				 						var reportType = $("#reportTypeSearch").combobox('getValue');
				 						if("days" == reportType && common.isBlank(rowData.footer)){
				 							html += "<a onclick=\"appDataAnalysis.viewDetail('"+rowData.dtMonth+"','"+rowData.channel+"','"+rowData.devicetype+"','"+rowData.realNewuserTrade+"','realNewuserTrade')\">" + rowData.realNewuserTrade + "</a>";
				 							return html;
				 						}
				 						return rowData.realNewuserTrade;
				 					}else{
				 						return rowData.realNewuserTrade;
				 					}
							}},	
		   	            	
  
			                {field : 'realNewuserLoginRate', title : '新客登录率', sortable : true, width : 70,
								styler:function(value, rowData, rowIndex){
									var values =  rowData.realNewuserLoginRate.replace("%","");
									if(!common.isBlank(values) && values > 0){
										return 'color:red;';					
									}else if(!common.isBlank(values) && values < 0){
										return 'color:green;';
									}
								}},
			   	            {field : 'realNewuserDepositRate', title : '新客入金率', sortable : true, width : 70,
									styler:function(value, rowData, rowIndex){
										var values =  rowData.realNewuserDepositRate.replace("%","");
										if(!common.isBlank(values) && values > 0){
											return 'color:red;';					
										}else if(!common.isBlank(values) && values < 0){
											return 'color:green;';
										}
									}},	   	 			
			                {field : 'realNewuserTradeRate', title : '新客交易率', sortable : true, width : 70,
										styler:function(value, rowData, rowIndex){
											var values =  rowData.realNewuserTradeRate.replace("%","");
											if(!common.isBlank(values) && values > 0){
												return 'color:red;';					
											}else if(!common.isBlank(values) && values < 0){
												return 'color:green;';
											}
										}}
			   	            
			    			] ];
		         return columns;
		}
	},
	viewDetail : function(dtMonth, channel, devicetype,paramValue,field){
		var url = "appDataAnalysisController/appDataAnalysisDetails?";
		var queryParams = {};
		var reportType = $("#reportTypeSearch").combobox('getValue');
		queryParams['reportType'] = reportType;
		queryParams['dtMonth'] = dtMonth;
		queryParams['channel'] = channel;
		queryParams['devicetype'] = devicetype;
		queryParams['field'] = field;
		queryParams['paramValue'] = paramValue;
		queryParams['channelChecked'] = $("#channelChecked").is(':checked');
		queryParams['devicetypeChecked'] = $("#devicetypeChecked").is(':checked');
		url = common.formatUrl(url, queryParams);
		parent.index.addOrUpdateTabs("APP数据分析详情", url);
	}
	
}
