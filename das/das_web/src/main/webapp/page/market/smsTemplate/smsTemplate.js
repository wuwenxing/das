$(function() {
	smsTemplate.init();
});

var smsTemplate = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	addDlogId: "addDlog",
	addDlogFormId: "addDlogForm",
	addDlogToolbarId: "addDlogToolbar",
	addDlogOkId: "addDlogOk",
	addDlogCancelId: "addDlogCancel",
	/**
	 * 初始化
	 */
	init:function(){
		smsTemplate.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'SmsTemplateController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'templateId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 360, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.templateId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'tempFieldName', title : '模板ID', sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
						return rowData.templateId;
				}},
	 			{field : 'code', title : '模板编号', sortable : true, width : 150},
	 			{field : 'name', title : '模板名称', sortable : true, width : 120},
	 			{field : 'content', title : '短信内容', sortable : true, width : 400, 
	 				formatter : function(value, rowData, rowIndex) {
						return rowData.smsSign + rowData.content;
				}},
	 			{field : 'enableFlag', title : '状态', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.enableFlag == 'Y'){
							return "启用";
						}else{
							return "禁用";
						}
				}},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 130}
 			] ];
		$('#' + smsTemplate.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'templateId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				smsTemplate.addDlog("2", rowData.templateId);
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + smsTemplate.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['code'] = $("#codeSearch").val();
		queryParams['name'] = $("#nameSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		smsTemplate.getQueryParams();
		common.loadGrid(smsTemplate.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + smsTemplate.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + smsTemplate.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + smsTemplate.addDlogOkId).css("display", "");
		$("#" + smsTemplate.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#templateId').val("");
			$("#" + smsTemplate.addDlogId).dialog('open').dialog('setTitle', '新增短信模板');
		}else{
			var url = BASE_PATH + "SmsTemplateController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"templateId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + smsTemplate.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + smsTemplate.addDlogOkId).css("display", "");
							$("#" + smsTemplate.addDlogId).dialog('open').dialog('setTitle', '修改短信模板');
						}else if(operFlag == 2){
							$("#" + smsTemplate.addDlogOkId).css("display", "none");
							$("#" + smsTemplate.addDlogId).dialog('open').dialog('setTitle', '查看短信模板');
						}
					}
				},
				error: function(data){
					common.error();
		        }
			});
		}
	},
	/**
	 * 新增短信模板提交
	 */
	save: function(){
		if(!common.submitFormValidate(smsTemplate.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SmsTemplateController/save",
			data : $("#" + smsTemplate.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + smsTemplate.addDlogId).dialog('close');
					common.reloadGrid(smsTemplate.dataGridId);
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 单个删除
	 */
	deleteById : function(templateIdArray, tip){
		var url = BASE_PATH + "SmsTemplateController/deleteById";
		if(null == tip || tip == ''){
			tip = '确认删除该行记录吗?'
		}
		$.messager.confirm('确认', tip, function(r) {
			if (r) {
				$.ajax({
					type : "post",
					dataType : "json",
					url : url,
					data : {
						"templateIdArray" : templateIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(smsTemplate.dataGridId);
						}
					},
					error: function(data){
						common.error();
			        }
				});
			}
		});
	},
	/**
	 * 批量删除
	 */
	deleteBatch : function(){
		var checkeds = $("#" + smsTemplate.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].templateId;
			}else{
				ids += checkeds[i].templateId + ",";
			}
		}
		if (ids.length > 0) {
			smsTemplate.deleteById(ids, "确认删除选择的记录吗?");
		} else {
			$.messager.alert('提示', "请选择要删除的记录", 'info');
		}
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = smsTemplate.getQueryParams();
		var url = BASE_PATH + "SmsTemplateController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 未知请求日志
	 */
	viewUnknownLog : function(){
		var url = "SmsTemplateLogController/page?type=unknown";
		parent.index.addOrUpdateTabs("未知短信请求日志", url);
	},
	/**
	 * 请求日志
	 */
	viewLog : function(id){
		var url = "SmsTemplateLogController/page?templateId=" + id;
		parent.index.addOrUpdateTabs("短信请求日志", url);
	},
	/**
	 * 短信详情
	 */
	viewDetail : function(id){
		var url = "SmsTemplateDetailController/page?templateId=" + id;
		parent.index.addOrUpdateTabs("短信详情", url);
	},
	test : function(){
		$.ajax({
			type : "post",
			dataType : "json",
//			url: "http://localhost:8080/das_web/SmsTemplateController/send",
			url: "http://localhost:8080/das_web/smsTemplate/send",
			data : {
				timestamp : "20161008140800", 
	 			accountSid: "fa573c78eaa8402cb6c84dabfcce7159", 
	 			sign : "5ed770bc2418fa0081fdb4538deec4a3", 
//				accountSid: "fa573c78eaa8402cb6c84dabfcce7160", 
//				sign : "e4974582c371b9d58c5e369923c276d8", 
//	 			templateCode : "TradingStrategy", 
//	 			templateParam : "{\"teacherName\":\"小娜\", \"varietyList\":[{\"support\":\"1290.50\",\"variety\":\"黄金\"},{\"support\":\"17.90\",\"variety\":\"白银\"}]}", 
//	 			templateCode : "LiveReminder", 
//	 			templateParam : "{\"userName\":\"梅长苏\", \"courseTime\":\"今晚8点\", \"teacherName\":\"小娜\"}",
//				templateCode : "ShoutSingleStrategy", 
//				templateParam : "{\"teacherName\":\"小娜\", \"shoutSingleList\":[{\"name\":\"白银\",\"trend\":\"做多\",\"entryPoint\":\"17.60\",\"stopPoint\":\"17.30\",\"profitPoint\":\"17.90\"}]}", 
	 			templateCode : "SimulateCustomerByCommon", 
	 			templateParam : "{}",
				phones : "13760291376"
			},
			success : function(data) {
				var resultCode = data.respCode;
				var resultMsg = data.respMsg;
				alert(resultCode);
				alert(resultMsg);
			}
		});
	}
}




















