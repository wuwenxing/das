$(function() {
	
	var reportFormType = $("#reportFormType").datebox('getValue');
	if(0 == reportFormType){
		//$("#statisticsReportDiv").show();
		//$("#statisticsDailyChannelDiv").hide();
		document.getElementById("statisticsReportDiv").style.display="";
		document.getElementById("statisticsDailyChannelDiv").style.display="none";
		$("#dailyChannelTr").attr("class","more");
		$("#dailyChanneldowntr").attr("class","more");
			
	}else{
		//$("#statisticsReportDiv").hide();
		//$("#statisticsDailyChannelDiv").show();
		document.getElementById("statisticsDailyChannelDiv").style.display="";
		document.getElementById("statisticsReportDiv").style.display="none";
		$("#dailyChannelTr").attr("class","");
		$("#dailyChanneldowntr").attr("class","");
	}
	
	dasStatisticsReport.init();
});

var dasStatisticsReport = {
	dataGridMonth: "dataGridMonth",
	dataGridDays: "dataGridDays",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	
	dataGridDailyChannel: "dataGridDailyChannel",
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
		
		
		var pageSize = 6;// 初始大小
		var pageList = [ 6,10,15,20, 50, 100, 200, 500 ];		
		var url = BASE_PATH + 'DasStatisticsReport/findStatisticsPage';
		var queryParamMonth = {};
		var reportType = "months";
		queryParamMonth['startTime'] = $("#startmonthSearch").datebox('getValue');
		queryParamMonth['endTime'] = $("#endmonthSearch").datebox('getValue');
		dasStatisticsReport.loadDataGrid(url,queryParamMonth,reportType,dasStatisticsReport.dataGridMonth,pageSize,pageList);
		
		// 初始参数
		var pageSize = 10;// 初始大小
		var pageList = [ 5,10,15,20, 50, 100, 200, 500 ];	
		var url = BASE_PATH + 'DasStatisticsReport/findStatisticsPage';
		var queryParamDays = {};
		var reportType = "days";
		queryParamDays['startTime'] = $("#startDaysSearch").datebox('getValue');
		queryParamDays['endTime'] = $("#endDaysSearch").datebox('getValue');
		dasStatisticsReport.loadDataGrid(url,queryParamDays,reportType,dasStatisticsReport.dataGridDays,pageSize,pageList);
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(url,queryParams,reportType,dataGridId,pageSize,pageList){
		var fieldName ="dateMonth";
		if(reportType == "days"){
			var fieldName = "dateDay";
		}
		queryParams['reportType'] = reportType;
		queryParams['order'] = "desc";					
		
		var columns = dasStatisticsReport.getColumns(fieldName);
		
		$('#' + dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : '',
			sortOrder : 'desc',
            showFooter: true,
    		pageSize : pageSize, // 初始大小
    		pageList : pageList,
    		collapsible: true,
    		fitColumns:false,
			onDblClickRow : function(rowIndex, rowData) {
			},
			rowStyler:function(rowIndex,rowData){  
				if(reportType == "days"){
					if(!common.isBlank(rowData.dateDay)){  
			        	var val = Date.parse(rowData.dateDay);
			        	var newDate = new Date(val);
			        	if(newDate.getDay()==6 || newDate.getDay()==0){
			        		//return 'background-color:#6293BB;color:#fff;font-weight:bold;font-size:12px;'; 
			        		return 'background-color:#F2DCDB;color:#000000;';
			        	}
			        }
				}
		    },
			onLoadSuccess : function(data){	
				if(reportType == "days"){
					$("div.datagrid-footer [class$='dateDay']").text("当月合计：");
				}else{
					$("div.datagrid-footer [class$='dateMonth']").text("当月均值：");
				}
				
				common.iFrameHeight();	
			}
		});
	},
	
	getQueryParamsMonth: function() {
		var queryParams = $("#" + dasStatisticsReport.dataGridMonth).datagrid('options').queryParams;	
		queryParams['reportType'] = "months";
		queryParams['startTime'] = $("#startmonthSearch").datebox('getValue');
		queryParams['endTime'] = $("#endmonthSearch").datebox('getValue');
		queryParams['order'] = "desc";
		return queryParams;
	},
	getQueryParamDays: function() {
		var queryParams = $("#" + dasStatisticsReport.dataGridDays).datagrid('options').queryParams;	
		queryParams['reportType'] = "days";
		queryParams['startTime'] = $("#startDaysSearch").datebox('getValue');
		queryParams['endTime'] = $("#endDaysSearch").datebox('getValue');
		queryParams['order'] = "desc";
		return queryParams;
	},	
		
	/**
	 * 条件查询
	 */
	find: function(){				
		var reportFormType = $("#reportFormType").datebox('getValue');
		if(0 == reportFormType){
			var monthAllChecked = $("#monthAllChecked").is(':checked');
			var monthDeviceChecked = $("#monthDeviceChecked").is(':checked');
			var monthChannelChecked = $("#monthChannelChecked").is(':checked');	
			if(!monthAllChecked && !monthDeviceChecked && !monthChannelChecked){
				$.messager.confirm('报表隐藏列', '请至少选中一个报表隐藏列?', function(r){
					return ;
				});
			}else{
				$('#' + dasStatisticsReport.dataGridMonth).datagrid({columns:dasStatisticsReport.getColumns("dateMonth")});
				dasStatisticsReport.getQueryParamsMonth();
				common.loadGrid(dasStatisticsReport.dataGridMonth);	
				
				$('#' + dasStatisticsReport.dataGridDays).datagrid({columns:dasStatisticsReport.getColumns("dateDay")});
				dasStatisticsReport.getQueryParamDays();
				common.loadGrid(dasStatisticsReport.dataGridDays);
			}
		}else{
			dasStatisticsReport.loadDataDailyChannel();
		}
	},
	/**
	 * reset条件查询
	 */
	reset: function(){	
		var reportFormType = $("#reportFormType").datebox('getValue');
		$('#' + dasStatisticsReport.searchFormId).form('reset');
		
/*		$('#startmonthSearch').datebox({
			formatter:easyui.formatterYYYYMM,
			parser:easyui.parserYYYYMM
		});
		$('#endmonthSearch').datebox({
			formatter:easyui.formatterYYYYMM,
			parser:easyui.parserYYYYMM
		});*/
		
		$('#startDaysSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endDaysSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		
/*		$("#startmonthSearch").datebox('setValue', $("#addSixMonths").val());
		$("#endmonthSearch").datebox('setValue', $("#dayStart").val());*/
		$("#startDaysSearch").datebox('setValue', $("#halfaMonth").val());
		$("#endDaysSearch").datebox('setValue', $("#dayEnd").val());						
		
		$('#reportFormType').combobox('setValue', reportFormType);
		$("#startTimeSearch").datebox('setValue', $("#monthStart").val());
		$("#endTimeSearch").datebox('setValue', $("#dayEnd").val());
		
		common.setEasyUiCss();
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
		}else if(newValue == 'months'){
			$('#startTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$('#endTimeSearch').datebox({
				formatter:easyui.formatterYYYYMM,
				parser:easyui.parserYYYYMM
			});
			$("#startTimeSearch").datebox('setValue', $("#monthStart").val());
			$("#endTimeSearch").datebox('setValue', $("#monthEnd").val());
		}
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(reportType){
		var queryParamMonth = {};
		if(reportType == "months"){
			if(!common.exportExcelValidate(this.dataGridMonth)){
				return;
			}
			queryParams = dasStatisticsReport.getQueryParamsMonth();
		}else{
			if(!common.exportExcelValidate(this.dataGridDays)){
				return;
			}
			queryParams = dasStatisticsReport.getQueryParamDays();
		}
		var url = BASE_PATH + "DasStatisticsReport/exportStatistics?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	
	reportFormTypeChange : function(newValue,oldValue){
		if(0 == newValue){
			$("#statisticsReportDiv").show();
			$("#statisticsDailyChannelDiv").hide();
			$("#dailyChannelTr").attr("class","more");
			$("#dailyChanneldowntr").attr("class","more");
			$("#monthSearchTr").attr("class","");
			$("#daysSearchTr").attr("class","");
			$("#dayshideTr").attr("class","");
		}else{			
			$("#statisticsReportDiv").hide();
			$("#statisticsDailyChannelDiv").show();
			$("#dailyChannelTr").attr("class","");
			$("#dailyChanneldowntr").attr("class","");
			$("#monthSearchTr").attr("class","more");
			$("#daysSearchTr").attr("class","more");
			$("#dayshideTr").attr("class","more");
			dasStatisticsReport.loadDataDailyChannel();
		}
	},
	
	channeltypeChange : function(newValue,oldValue){
		var url = BASE_PATH + 'DasStatisticsReport/findChannelByisPay';
		var channeltype = $("#channeltype").combobox('getValue');
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data : {
				"channeltype" : channeltype
			},
			success : function(data) {
				if(null != data) {		
					$("#channelIds").empty();
					for(var i = 0; i< data.length; i++){
	        			$("<option value='"+data[i]+"'>"+data[i]+"</option>").appendTo("#channelIds");
	        		}
					$("select[multiple='multiple']").multiselect({
            			noneSelectedText: "---请选择---", 
            			selectedList: 10 // 0-based index
            		}).multiselectfilter();
					$(".ui-multiselect-menu").css("width", "217px");
					$("#channelIds").multiselect("refresh"); 
				}
			},
			beforeSend: function(XMLHttpRequest){
		    	
		    },
			error: function(data){
				common.error();
	        }
		});
	},
	
	/**
	 * 加载dataGrid
	 */
	loadDataDailyChannel : function(){
		var url = BASE_PATH + 'DasStatisticsReport/findDailyChannelPage';
		var queryParams = {};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');	
		queryParams['channeltype'] =$("#channeltype").combobox('getValue');	
		queryParams['channel'] = $("#channelIds").val()+"";		
		var columns = [ 
			[
				{field : "dateDay", title : '日期', rowspan: 2, sortable : true, width : 100},
				{field : "channel", title : '来源渠道', rowspan: 2, sortable : true, width : 120},
				{title : '当期值', colspan: 5, sortable : false, width : 450},
				{title : '环比上周均值', colspan: 5, sortable : false, width : 450},
				{title : '环比上月均值', colspan: 5, sortable : false, width : 450},
				{field : "platformtype", title : '访问客户端', rowspan: 2, sortable : false, width : 100,
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.platformtype == '0'){
							return "PC端";
						}else if(rowData.platformtype == '1'){
							return "移动端";
						}else{
							// 统计时，为空代表所有客户端
							return "(全部)";
						}
	 				}
				}
			],			
		    [
                {field : 'visit', title : '访问', sortable : true, width : 90,
                	formatter : function(value, rowData, rowIndex) {
	 					if(rowData.visit > 0 && !common.isBlank(rowData.channel)){
	 						var html = "";
	 						html += "<a onclick=\"dasStatisticsReport.viewDetail('"+rowData.dateDay+"', '"+rowData.channel+"', '"+rowData.platformtype+"')\">" + rowData.visit + "</a>";
	 						return html;
	 					}else{
	 						return rowData.visit;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var visit =  rowData.visit+"";
					if(!common.isBlank(visit) && visit > 0){
						//return 'color:green;';						
					}else{
						//return 'color:red;';
					}
				}},
	            {field : 'advisory', title : '咨询', sortable : true, width : 90,
					formatter : function(value, rowData, rowIndex) {
	 					if(rowData.advisory > 0 && !common.isBlank(rowData.channel)){
	 						var html = "";
	 						html += "<a onclick=\"dasStatisticsReport.viewDetail('"+rowData.dateDay+"', '"+rowData.channel+"', '"+rowData.platformtype+"')\">" + rowData.advisory + "</a>";
	 						return html;
	 					}else{
	 						return rowData.advisory;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var advisory =  rowData.advisory+"";
					if(!common.isBlank(advisory) && advisory > 0){
						//return 'color:green;';						
					}else{
						//return 'color:red;';
					}
				}},
	            {field : 'real', title : '真实', sortable : true, width : 90,
					formatter : function(value, rowData, rowIndex) {
	 					if(rowData.real > 0 && !common.isBlank(rowData.channel)){
	 						var html = "";
	 						html += "<a onclick=\"dasStatisticsReport.viewDetail('"+rowData.dateDay+"', '"+rowData.channel+"', '"+rowData.platformtype+"')\">" + rowData.real + "</a>";
	 						return html;
	 					}else{
	 						return rowData.real;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var real =  rowData.real+"";
					if(!common.isBlank(real) && real > 0){
						//return 'color:green;';						
					}else{
						//return 'color:red;';
					}
				}},		     
                {field : 'demo', title : '模拟', sortable : true, width : 90,
					formatter : function(value, rowData, rowIndex) {
	 					if(rowData.demo > 0 && !common.isBlank(rowData.channel)){
	 						var html = "";
	 						html += "<a onclick=\"dasStatisticsReport.viewDetail('"+rowData.dateDay+"', '"+rowData.channel+"', '"+rowData.platformtype+"')\">" + rowData.demo + "</a>";
	 						return html;
	 					}else{
	 						return rowData.demo;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var demo =  rowData.demo+"";
					if(!common.isBlank(demo) && demo > 0){
						//return 'color:green;';						
					}else{
						//return 'color:red;';
					}
				}},
	            {field : 'deposit', title : '入金', sortable : true, width : 90,
					formatter : function(value, rowData, rowIndex) {
	 					if(rowData.deposit > 0 && !common.isBlank(rowData.channel)){
	 						var html = "";
	 						html += "<a onclick=\"dasStatisticsReport.viewDetail('"+rowData.dateDay+"', '"+rowData.channel+"', '"+rowData.platformtype+"')\">" + rowData.deposit + "</a>";
	 						return html;
	 					}else{
	 						return rowData.deposit;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var deposit =  rowData.deposit+"";
					if(!common.isBlank(deposit) && deposit > 0){
						//return 'color:green;';						
					}else{
						//return 'color:red;';
					}
				}},
	            
                {field : 'onwvisit', title : '访问', sortable : true, width : 90,
                	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onwvisit)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onwvisit;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onwvisit =  rowData.onwvisit.replace("%","");
					if(!common.isBlank(onwvisit) && onwvisit > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onwvisit) && onwvisit < 0){
						return 'color:green;';
					}
				}},
	            {field : 'onwadvisory', title : '咨询', sortable : true, width : 90,
	            	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onwadvisory)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onwadvisory;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onwadvisory =  rowData.onwadvisory.replace("%","");
					if(!common.isBlank(onwadvisory) && onwadvisory > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onwadvisory) && onwadvisory < 0){
						return 'color:green;';
					}
				}},
	            {field : 'onwreal', title : '真实', sortable : true, width : 90,
	            	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onwreal)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onwreal;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onwreal =  rowData.onwreal.replace("%","");
					if(!common.isBlank(onwreal) && onwreal > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onwreal) && onwreal < 0){
						return 'color:green;';
					}
				}},		     
                {field : 'onwdemo', title : '模拟', sortable : true, width : 90,
                	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onwdemo)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onwdemo;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onwdemo =  rowData.onwdemo.replace("%","");
					if(!common.isBlank(onwdemo) && onwdemo > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onwdemo) && onwdemo < 0){
						return 'color:green;';
					}
				}},
	            {field : 'onwdeposit', title : '入金', sortable : true, width : 90,
	            	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onwdeposit)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onwdeposit;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onwdeposit =  rowData.onwdeposit.replace("%","");
					if(!common.isBlank(onwdeposit) && onwdeposit > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onwdeposit) && onwdeposit < 0){
						return 'color:green;';
					}
				}},
	            
                {field : 'onmvisit', title : '访问', sortable : true, width : 90,
                	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onmvisit)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onmvisit;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onmvisit =  rowData.onmvisit.replace("%","");
					if(!common.isBlank(onmvisit) && onmvisit > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onmvisit) && onmvisit < 0){
						return 'color:green;';
					}
				}},
	            {field : 'onmadvisory', title : '咨询', sortable : true, width : 90,
	            	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onmadvisory)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onmadvisory;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onmadvisory =  rowData.onmadvisory.replace("%","");
					if(!common.isBlank(onmadvisory) && onmadvisory > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onmadvisory) && onmadvisory < 0){
						return 'color:green;';
					}
				}},
	            {field : 'onmreal', title : '真实', sortable : true, width : 90,
	            	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onmreal)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onmreal;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onmreal =  rowData.onmreal.replace("%","");
					if(!common.isBlank(onmreal) && onmreal > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onmreal) && onmreal < 0){
						return 'color:green;';
					}
				}},		     
                {field : 'onmdemo', title : '模拟', sortable : true, width : 90,
                	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onmdemo)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onmdemo;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onmdemo =  rowData.onmdemo.replace("%","");
					if(!common.isBlank(onmdemo) && onmdemo > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onmdemo) && onmdemo < 0){
						return 'color:green;';
					}
				}},
	            {field : 'onmdeposit', title : '入金', sortable : true, width : 90,
	            	formatter : function(value, rowData, rowIndex) {
	 					if(common.isBlank(rowData.onmdeposit)){	 						 						
	 						return "-";
	 					}else{
	 						return rowData.onmdeposit;
	 					}
				},
				styler:function(value, rowData, rowIndex){
					var onmdeposit =  rowData.onmdeposit.replace("%","");
					if(!common.isBlank(onmdeposit) && onmdeposit > 0){
						return 'color:red;';					
					}else if(!common.isBlank(onmdeposit) && onmdeposit < 0){
						return 'color:green;';
					}
				}}	 			               
 			] ];
		$('#' + dasStatisticsReport.dataGridDailyChannel).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : '',
			sortOrder : 'desc',
            showFooter: true,
			onDblClickRow : function(rowIndex, rowData) {
			},			
			onLoadSuccess : function(data){	
				
				common.iFrameHeight();	
			}
		});
	},
	
	/**
	 * 导出excel
	 */
	exportDailyChannel : function(reportType){
		var queryParams = {};
		queryParams['startTime'] = $("#startTimeSearch").datebox('getValue');
		queryParams['endTime'] = $("#endTimeSearch").datebox('getValue');
		queryParams['platformType'] = $("#platformTypeSearch").combobox('getValue');	
		queryParams['channeltype'] =$("#channeltype").combobox('getValue');	
		queryParams['channel'] = $("#channelIds").val()+"";		
		var url = BASE_PATH + "DasStatisticsReport/exportDailyChannel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},	
	viewDetail : function(dataTime, channel, platformType){
		var url = "DasFlowStatisticsController/pageMedia?";
		var queryParams = {};
		queryParams['dataTime'] = dataTime;
		queryParams['channel'] = channel;
		queryParams['platformType'] = platformType;
		url = common.formatUrl(url, queryParams);
		parent.index.addOrUpdateTabs("来源媒介统计", url);
	},	
	
	getColumns:function(fieldName){
		var monthAllChecked = $("#monthAllChecked").is(':checked');
		var monthDeviceChecked = $("#monthDeviceChecked").is(':checked');
		var monthChannelChecked = $("#monthChannelChecked").is(':checked');	
		if(monthAllChecked && monthDeviceChecked && monthChannelChecked){
			var columns = [ 
						    [
							 {field : fieldName, title : '', sortable : false, align:'center',width : 92},
							 {field : 'allhead', title : '全部', colspan: 7, sortable : false, align:'center',width : 360,
								 styler: function(value,row,index){
										return 'background-color:#DC143C;color:red;';
							 }},
							 {field : 'devicehead', title : '设备类型', colspan: 25, sortable : false, align:'center',width : 1250},
							 {field : 'channelhead', title : '渠道',colspan: 40, sortable : false, align:'center',width : 2000}
						    ],
   
			      			[
			   				{field : fieldName, title : '日期', rowspan: 3, sortable : true, align:'center',width : 92},
			   				{field : 'allhead', title : '整站', rowspan: 2,colspan: 7, sortable : false, align:'center',width : 360,
			   					styler: function(value,row,index){
								        return 'background-color:#DC143C;color:red;';
							 }},
			   				{field : 'devicehead', title : 'PC端', colspan: 10, sortable : false, align:'center',width : 500},
			   				{field : 'channelhead', title : '移动端', colspan: 15, sortable : false, align:'center',width : 750},
			   				
			 				{field : 'pay', title : 'SEM', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : 'App',rowspan: 2, colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'other', title : '媒体', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'pay', title : 'SEO', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : '免费', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'other', title : '微信', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'pay', title : '好友邀请', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : '其他', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250}
			
			   			   ],
			   			   [			   				
			   				{field : 'pc', title : 'WEB页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : '客户端', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : 'WAP页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'android', title : 'GTS2_Android', colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'ios', title : 'GTS2_IOS', colspan: 5, sortable : false, align:'center',width : 250}
			   			   ],
			   		       [
			                {field : 'allVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'allAdvisory', title : '咨询', sortable : true, width : 50},
			                {field : 'newCustConsultation', title : '新客咨询', sortable : false, width : 55},
			                {field : 'oldCustConsultation', title : '老客咨询', sortable : false, width : 55},
			   	            {field : 'allReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'allDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'allDeposit', title : '入金', sortable : true, width : 50},
			   	            
			   	            
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devAndroidVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devAndroidAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devAndroidReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devAndroidDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devAndroidDeposit', title : '入金', sortable : true, width : 50},
			   	 			
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			   	            			   	            
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50}			                			                
			    		   ] ];
		  return columns;
		}
		
		if(monthAllChecked && monthDeviceChecked && !monthChannelChecked){
			var columns = [ 
						    [
							 {field : fieldName, title : '', sortable : false, align:'center',width : 92},
							 {field : 'allhead', title : '', colspan: 7, sortable : false, align:'center',width : 360},
							 {field : 'devicehead', title : '设备类型', colspan: 25, sortable : false, align:'center',width : 1250}
						    ],
  
			      			[
			   				{field : fieldName, title : '日期', rowspan: 3, sortable : true, align:'center',width : 92},
			   				{field : 'allhead', title : '整站', rowspan: 2,colspan: 7, sortable : false, align:'center',width : 360},
			   				{field : 'devicehead', title : 'PC端', colspan: 10, sortable : false, align:'center',width : 500},
			   				{field : 'channelhead', title : '移动端', colspan: 15, sortable : false, align:'center',width : 750}
			
			   			   ],
			   			   [			   				
			   				{field : 'pc', title : 'WEB页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : '客户端', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : 'WAP页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'android', title : 'GTS2_Android', colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'ios', title : 'GTS2_IOS', colspan: 5, sortable : false, align:'center',width : 250}
			   			   ],
			   		       [
			                {field : 'allVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'allAdvisory', title : '咨询', sortable : true, width : 50},
			                {field : 'newCustConsultation', title : '新客咨询', sortable : false, width : 55},
			                {field : 'oldCustConsultation', title : '老客咨询', sortable : false, width : 55},
			   	            {field : 'allReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'allDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'allDeposit', title : '入金', sortable : true, width : 50},
			   	            
			   	            
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devAndroidVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devAndroidAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devAndroidReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devAndroidDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devAndroidDeposit', title : '入金', sortable : true, width : 50},
			   	 			
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50}
	                			                
			    		   ] ];
			 return columns;
		}
		
		if(monthAllChecked && !monthDeviceChecked && monthChannelChecked){
			var columns = [ 
						    [
							 {field : fieldName, title : '', sortable : false, align:'center',width : 92},
							 {field : 'allhead', title : '', colspan: 7, sortable : false, align:'center',width : 360},
							 {field : 'channelhead', title : '渠道',colspan: 40, sortable : false, align:'center',width : 2000}
						    ],
  
			      			[
			   				{field : fieldName, title : '日期', rowspan: 3, sortable : true, align:'center',width : 92},
			   				{field : 'allhead', title : '整站', rowspan: 2,colspan: 7, sortable : false, align:'center',width : 360},
			   				
			 				{field : 'pay', title : 'SEM', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : 'App',rowspan: 2, colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'other', title : '媒体', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'pay', title : 'SEO', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : '免费', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'other', title : '微信', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'pay', title : '好友邀请', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : '其他', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250}
			
			   			   ],
			   			   [			   				
			   				{field : 'pc', title : 'WEB页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : '客户端', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : 'WAP页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'android', title : 'GTS2_Android', colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'ios', title : 'GTS2_IOS', colspan: 5, sortable : false, align:'center',width : 250}
			   			   ],
			   		       [
			                {field : 'allVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'allAdvisory', title : '咨询', sortable : true, width : 50},
			                {field : 'newCustConsultation', title : '新客咨询', sortable : false, width : 55},
			                {field : 'oldCustConsultation', title : '老客咨询', sortable : false, width : 55},
			   	            {field : 'allReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'allDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'allDeposit', title : '入金', sortable : true, width : 50},
			   	            
		   	            
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50}			                			                
			    		   ] ];
			 return columns;
		}
		if(!monthAllChecked && monthDeviceChecked && monthChannelChecked){
			var columns = [ 
						    [
							 {field : fieldName, title : '', sortable : false, align:'center',width : 92},
							 {field : 'devicehead', title : '设备类型', colspan: 25, sortable : false, align:'center',width : 1250},
							 {field : 'channelhead', title : '渠道',colspan: 40, sortable : false, align:'center',width : 2000}
						    ],
  
			      			[
			   				{field : fieldName, title : '日期', rowspan: 3, sortable : true, align:'center',width : 92},
			   				{field : 'devicehead', title : 'PC端', colspan: 10, sortable : false, align:'center',width : 500},
			   				{field : 'channelhead', title : '移动端', colspan: 15, sortable : false, align:'center',width : 750},
			   				
			 				{field : 'pay', title : 'SEM', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : 'App',rowspan: 2, colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'other', title : '媒体', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'pay', title : 'SEO', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : '免费', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'other', title : '微信', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'pay', title : '好友邀请', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : '其他', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250}
			
			   			   ],
			   			   [			   				
			   				{field : 'pc', title : 'WEB页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : '客户端', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : 'WAP页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'android', title : 'GTS2_Android', colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'ios', title : 'GTS2_IOS', colspan: 5, sortable : false, align:'center',width : 250}
			   			   ],
			   		       [ 	            
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devAndroidVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devAndroidAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devAndroidReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devAndroidDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devAndroidDeposit', title : '入金', sortable : true, width : 50},
			   	 			
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			   	            			   	            
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50}			                			                
			    		   ] ];
			 return columns;
		}
		
		if(!monthAllChecked && !monthDeviceChecked && monthChannelChecked){
			var columns = [ 
						    [
							 {field : fieldName, title : '', sortable : false, align:'center',width : 92},
							 {field : 'channelhead', title : '渠道',colspan: 40, sortable : false, align:'center',width : 2000}
						    ],
  
			      			[			   				
			   				
			 				{field : 'pay', title : 'SEM', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : 'App',rowspan: 2, colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'other', title : '媒体', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'pay', title : 'SEO', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : '免费', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'other', title : '微信', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'pay', title : '好友邀请', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'free', title : '其他', rowspan: 2,colspan: 5, sortable : false, align:'center',width : 250}
			
			   			   ],
			   			  
			   		       [		   	            
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50}			                			                
			    		   ] ];
			 return columns;
		}
		
		if(!monthAllChecked && monthDeviceChecked && !monthChannelChecked){
			var columns = [ 
						    [
							 {field : fieldName, title : '', sortable : false, align:'center',width : 92},
							 {field : 'devicehead', title : '设备类型', colspan: 25, sortable : false, align:'center',width : 1250}
						    ],
  
			      			[
			   				{field : fieldName, title : '日期', rowspan: 3, sortable : true, align:'center',width : 92},
			   				{field : 'devicehead', title : 'PC端', colspan: 10, sortable : false, align:'center',width : 500},
			   				{field : 'channelhead', title : '移动端', colspan: 15, sortable : false, align:'center',width : 750}
			
			   			   ],
			   			   [			   				
			   				{field : 'pc', title : 'WEB页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : '客户端', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'pc', title : 'WAP页面', colspan: 5, sortable : false,align:'center', width : 250},
			   				{field : 'android', title : 'GTS2_Android', colspan: 5, sortable : false, align:'center',width : 250},
			   				{field : 'ios', title : 'GTS2_IOS', colspan: 5, sortable : false, align:'center',width : 250}
			   			   ],
			   		       [			                
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devpcVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devpcAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devpcReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devpcDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devpcDeposit', title : '入金', sortable : true, width : 50},
			   	            
			                {field : 'devAndroidVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devAndroidAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devAndroidReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devAndroidDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devAndroidDeposit', title : '入金', sortable : true, width : 50},
			   	 			
			                {field : 'devIosVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'devIosAdvisory', title : '咨询', sortable : true, width : 50},
			   	            {field : 'devIosReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'devIosDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'devIosDeposit', title : '入金', sortable : true, width : 50}
			   	            
			   	            			   	            	                			                
			    		   ] ];
			 return columns;
		}
		if(monthAllChecked && !monthDeviceChecked && !monthChannelChecked){
			var columns = [ 
						    [
							 {field : fieldName, title : '', sortable : false, align:'center',width : 92},
							 {field : 'allhead', title : '', colspan: 7, sortable : false, align:'center',width : 360}
						    ],
  
			      			[
			   				{field : fieldName, title : '日期', rowspan: 3, sortable : true, align:'center',width : 92},
			   				{field : 'allhead', title : '整站', rowspan: 2,colspan: 7, sortable : false, align:'center',width : 360}
			
			   			   ],
			   		       [
			                {field : 'allVisit', title : '访问', sortable : true, width : 50},
			   	            {field : 'allAdvisory', title : '咨询', sortable : true, width : 50},
			                {field : 'newCustConsultation', title : '新客咨询', sortable : false, width : 55},
			                {field : 'oldCustConsultation', title : '老客咨询', sortable : false, width : 55},
			   	            {field : 'allReal', title : '真实', sortable : true, width : 50},		     
			                {field : 'allDemo', title : '模拟', sortable : true, width : 50},
			   	            {field : 'allDeposit', title : '入金', sortable : true, width : 50}
			   	            
			   	            			   	         			                			                
			    		   ] ];
			 return columns;
		}
		
		if(!monthAllChecked && !monthDeviceChecked && !monthChannelChecked){
			 var columns = [ 
                             {field : fieldName, title : '日期', rowspan: 3, sortable : true, align:'center',width : 92}
				  		  ];
			 return columns;
		}
		
	}
	
}

