$(function() {
	emailTemplate.init();
});

var emailTemplate = {
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
		emailTemplate.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'EmailTemplateController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'templateId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 330, 
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
	 			{field : 'code', title : '模板编号', sortable : true, width : 100},
	 			{field : 'name', title : '模板名称', sortable : true, width : 100},
	 			{field : 'title', title : '邮件标题', sortable : true, width : 300},
	 			{field : 'enableFlag', title : '状态', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.enableFlag == 'Y'){
							return "启用";
						}else{
							return "禁用";
						}
				}},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 100}
 			] ];
		$('#' + emailTemplate.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'templateId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				emailTemplate.addDlog("2", rowData.templateId);
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + emailTemplate.dataGridId).datagrid('options');
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
		emailTemplate.getQueryParams();
		common.loadGrid(emailTemplate.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + emailTemplate.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + emailTemplate.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + emailTemplate.addDlogOkId).css("display", "");
		$("#" + emailTemplate.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#templateId').val("");
			$("#" + emailTemplate.addDlogId).dialog('open').dialog('setTitle', '新增邮件模板');
		}else{
			var url = BASE_PATH + "EmailTemplateController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"templateId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + emailTemplate.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + emailTemplate.addDlogOkId).css("display", "");
							$("#" + emailTemplate.addDlogId).dialog('open').dialog('setTitle', '修改邮件模板');
						}else if(operFlag == 2){
							$("#" + emailTemplate.addDlogOkId).css("display", "none");
							$("#" + emailTemplate.addDlogId).dialog('open').dialog('setTitle', '查看邮件模板');
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
	 * 新增邮件模板提交
	 */
	save: function(){
		if(!common.submitFormValidate(emailTemplate.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "EmailTemplateController/save",
			data : $("#" + emailTemplate.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + emailTemplate.addDlogId).dialog('close');
					common.reloadGrid(emailTemplate.dataGridId);
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
		var url = BASE_PATH + "EmailTemplateController/deleteById";
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
							common.reloadGrid(emailTemplate.dataGridId);
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
		var checkeds = $("#" + emailTemplate.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].templateId;
			}else{
				ids += checkeds[i].templateId + ",";
			}
		}
		if (ids.length > 0) {
			emailTemplate.deleteById(ids, "确认删除选择的记录吗?");
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
		var queryParams = emailTemplate.getQueryParams();
		var url = BASE_PATH + "EmailTemplateController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 文件上传成功回调此函数
	 */
	callback: function(url){
		$.messager.progress('close');
		$("#uploadFileUrl").append(url + "<br/>");
	},
	/**
	 * 未知请求日志
	 */
	viewUnknownLog : function(){
		var url = "EmailTemplateLogController/page?type=unknown";
		parent.index.addOrUpdateTabs("未知邮件请求日志", url);
	},
	/**
	 * 请求日志
	 */
	viewLog : function(id){
		var url = "EmailTemplateLogController/page?templateId=" + id;
		parent.index.addOrUpdateTabs("邮件请求日志", url);
	},
	/**
	 * 邮件详情
	 */
	viewDetail : function(id){
		var url = "EmailTemplateDetailController/page?templateId=" + id;
		parent.index.addOrUpdateTabs("邮件详情", url);
	},
	/**
	 * 邮件预览
	 */
	preview : function(id){
		var url = BASE_PATH + "EmailTemplateController/preview/" + id;
		window.open(url);
	},
	test : function(){
		$.ajax({
			type : "post",
			dataType : "json",
//			url: "http://localhost:8080/das_web/EmailTemplateController/send",
			url: "http://localhost:8080/das_web/emailTemplate/send",
			data : {
				timestamp : "20161008140800", 
				
//	 			accountSid: "fa573c78eaa8402cb6c84dabfcce7159", 
//	 			sign : "5ed770bc2418fa0081fdb4538deec4a3", 
//	 			accountSid: "fa573c78eaa8402cb6c84dabfcce7158", 
//	 			sign : "e9ccb14450d1e7d03014defbf6d401e8",
				accountSid: "fa573c78eaa8402cb6c84dabfcce7160", 
				sign : "e4974582c371b9d58c5e369923c276d8",  

//				templateCode : "TradingStrategy", 
//				templateParam : "{\"content\":\"交易策略内容\",\"teacherName\":\"小娜\",\"time\":\"2016-10-19 15:50\", \"varietyList\":[{\"support\":\"1290.50\",\"variety\":\"黄金\"},{\"support\":\"17.90\",\"variety\":\"白银\"}]}", 
//	 			templateCode : "VerityEmail", 
//	 			templateParam : "{\"userName\":\"梅长苏\",\"email\":\"xxxx@33.com\",\"time\":\"2016-10-19 15:50\",\"url\":\"http:\/\/www.baidu.com\"}",
//	 			templateCode : "LiveReminder", 
//	 			templateParam : "{\"userName\":\"梅长苏\",\"teacherName\":\"小娜\",\"liveTime\":\"2016-10-21 15:50\",\"time\":\"2016-10-19 15:50\"}",
	 			templateCode : "ShoutSingleStrategy", 
	 			templateParam : "{\"userName\":\"梅长苏\",\"typeLabel\":\"喊单\",\"teacherName\":\"小娜\",\"time\":\"2016-10-19 15:50\",\"content\":\"喊单内容参数\", \"shoutSingleList\":[{\"name\":\"黄金\",\"trend\":\"进取多单\",\"entryPoint\":\"1290.60\",\"stopPoint\":\"1285.60\",\"profitPoint\":\"1295.60\"},{\"name\":\"白银\",\"trend\":\"保守空单\",\"entryPoint\":\"17.60\",\"stopPoint\":\"18.60\",\"profitPoint\":\"16.60\"}]}", 
				
//	 			templateCode : "test001", 
//	 			templateParam : "", 
				
				emails : "wuwenxing-nc@qq.com"
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




















