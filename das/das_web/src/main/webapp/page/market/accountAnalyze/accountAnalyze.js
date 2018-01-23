$(function() {
	accountAnalyze.init();
});

var accountAnalyze = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	addDlogId: "addDlog",
	addDlogFormId: "addDlogForm",
	addDlogToolbarId: "addDlogToolbar",
	addDlogOkId: "addDlogOk",
	addDlogCancelId: "addDlogCancel",
	
	addDlogId_1: "addDlog_1",
	addDlogFormId_1: "addDlogForm_1",
	addDlogToolbarId_1: "addDlogToolbar_1",
	addDlogCancelId_1: "addDlogCancel_1",
	
	/**
	 * 初始化
	 */
	init:function(){
		accountAnalyze.loadDataGrid();
		$("#content").xheditor({
			upImgUrl : BASE_PATH + "UploadController/uploadImg/email/callback_2",
			upImgExt : "jpg,jpeg,gif,png"
		});
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'AccountAnalyzeController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'batchId', checkbox : true},
	 			{field : 'url', title : '', sortable : true, width : 100, hidden: true},
	 			{field : 'oper', title : '操作', sortable : false, width : 225, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.batchId);
							$(this).attr("attr1", rowData.url);
							$(this).attr("attr2", rowData.generateStatus);
							$(this).attr("attr3", rowData.sendStatus);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'batchNo', title : '批次号', sortable : true, width : 175},
	 			{field : 'accountNo', title : '账号', sortable : true, width : 75},
	 			{field : 'platform', title : '平台', sortable : true, width : 50},
	 			{field : 'email', title : '邮箱', sortable : true, width : 140},
	 			{field : 'startDate', title : '诊断开始时间', sortable : true, width : 95},
	 			{field : 'endDate', title : '诊断结束时间', sortable : true, width : 95},
	 			{field : 'generateStatus', title : '生成报告状态', sortable : true, width : 95},
	 			{field : 'generateTime', title : '生成报告时间', sortable : true, width : 100},
	 			{field : 'sendStatus', title : '邮件发送状态', sortable : true, width : 95},
	 			{field : 'sendTime', title : '邮件发送时间', sortable : true, width : 100},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 90}
 			] ];
		$('#' + accountAnalyze.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'batchId', // 唯一字段
			sortName : 'batchNo',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + accountAnalyze.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['batchNo'] = $("#batchNoSearch").val();
		queryParams['accountNo'] = $("#accountNoSearch").val();
		queryParams['generateStatus'] = $("#accountAnalyzeStatusSearch").combobox('getValue');
		queryParams['sendStatus'] = $("#sendStatusSearch").combobox('getValue');

		queryParams['generateTimeStart'] = $("#generateTimeStartSearch").datetimebox('getValue');
		queryParams['generateTimeEnd'] = $("#generateTimeEndSearch").datetimebox('getValue');
		queryParams['sendTimeStart'] = $("#sendTimeStartSearch").datetimebox('getValue');
		queryParams['sendTimeEnd'] = $("#sendTimeEndSearch").datetimebox('getValue');
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		accountAnalyze.getQueryParams();
		common.loadGrid(accountAnalyze.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + accountAnalyze.searchFormId).form('reset');
	},
	/**
	 * 导入账号诊断数据
	 */
	addDlog_1: function() {
		$("#" + accountAnalyze.addDlogOkId_1).css("display", "");
		$("#" + accountAnalyze.addDlogFormId_1).form('reset');
		$("#" + accountAnalyze.addDlogId_1).dialog('open').dialog('setTitle', '导入账号数据');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + accountAnalyze.addDlogId).dialog('close');
	},
	/**
	 * close新增窗口
	 */
	cancel_1: function(){
		$('#' + accountAnalyze.addDlogId_1).dialog('close');
	},
	/**
	 * 导入账号诊断数据-提交
	 */
	submitAccountAnalyze: function() {
		upload.uploadExcelFile(BASE_PATH + 'UploadController/accountAnalyzeUploadExcel/accountAnalyze/callback', 'uploadDiv', '1', '上传Excel文件');
	},
	/**
	 * 导入账号诊断数据回调此函数
	 */
	callback: function(msg){
		$.messager.progress('close');
		$("#msg").val(msg);
		common.reloadGrid(accountAnalyze.dataGridId);
	},
	/**
	 * 单个删除
	 */
	deleteById : function(batchIdArray, tip){
		var url = BASE_PATH + "AccountAnalyzeController/deleteById";
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
						"batchIdArray" : batchIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(accountAnalyze.dataGridId);
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
		var checkeds = $("#" + accountAnalyze.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].batchId;
			}else{
				ids += checkeds[i].batchId + ",";
			}
		}
		if (ids.length > 0) {
			accountAnalyze.deleteById(ids, "确认删除选择的记录吗?");
		} else {
			$.messager.alert('提示', "请选择要删除的记录", 'info');
		}
	},
	/**
	 * 生成报告
	 */
	generateReport : function(operFlag, batchId, obj){
		if(operFlag == 0){
			// 批量生成报告
			var checkeds = $("#" + accountAnalyze.dataGridId).datagrid('getChecked');
			var ids = "";
			for ( var i = 0; i < checkeds.length; i++) {
				var generateStatus = checkeds[i].generateStatus;
				if(generateStatus == '已生成'){
					$.messager.alert('提示', "不能选择已生成报告的记录", 'info');
					return;
				}
				if(i == checkeds.length-1){
					ids += checkeds[i].batchId;
				}else{
					ids += checkeds[i].batchId + ",";
				}
			}
			if (ids.length <= 0) {
				$.messager.alert('提示', "请选择未生成报告的记录", 'info');
				return;
			}else{
				batchId = ids;
			}
		}else{
			var generateStatus = obj.getAttribute("attr2");
			// 单个生成报告
			if(generateStatus == '已生成'){
				$.messager.alert('提示', "该记录已生成报告", 'info');
				return;
			}
		}
		
		$.messager.confirm('确认', "确认生成报告吗?", function(r) {
			if (r) {
				var url = BASE_PATH + "AccountAnalyzeController/generateReport";
				$.ajax({
					type : "post",
					dataType : "json",
					url : url,
					data : {
						"batchIds" : batchId + ""
					},
					success : function(data) {
						common.isSuccess(data);
					},
					error: function(data){
						common.error();
			        }
				});
			}
		});
	},
	/**
	 * 预览报告
	 */
	previewReport : function(obj){
		var url = obj.getAttribute("attr1");
		var generateStatus = obj.getAttribute("attr2");
		if(generateStatus != '已生成'){
			$.messager.alert('提示', "请选择已生成报告的记录进行预览", 'info');
			return;
		}
		window.open($("#accountAnalyzeDomainName").val() + url);
	},
	/**
	 * 编辑报告
	 */
	editReport : function(batchId, obj){
		var generateStatus = obj.getAttribute("attr2");
		var sendStatus = obj.getAttribute("attr3");
		if(generateStatus != '已生成'){
			$.messager.alert('提示', "请选择已生成报告的记录进行编辑", 'info');
			return;
		}
		if(sendStatus == '发送成功'){
			$.messager.alert('提示', "该记录已经发送邮件成功，不能进行编辑", 'info');
			return;
		}
		var url = BASE_PATH + "AccountAnalyzeController/findById";
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data : {
				"batchId" : batchId + ""
			},
			success : function(data) {
				if (null != data) {
					var content = data.content;
					$("#batchId").val(data.batchId);
					$("#batchNoTd").text(data.batchNo);
					$("#accountNoTd").text(data.accountNo);
					$("#content").val(content);
					$("#" + accountAnalyze.addDlogOkId).css("display", "");
					$("#" + accountAnalyze.addDlogId).dialog('open').dialog('setTitle', '编辑报告');
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 保存-编辑报告
	 */
	save: function(){
		// 诊断报告内容不能为空
		if($("#addDlog textarea[name='content']").val() == ''  || $.trim($("#addDlog textarea[name='content']").val()) == ''){
			$.messager.alert('提示', "诊断报告内容不能为空", 'info');
			return;
		}
		if(!common.submitFormValidate(accountAnalyze.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "AccountAnalyzeController/save",
			data : $("#" + accountAnalyze.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + accountAnalyze.addDlogId).dialog('close');
					common.reloadGrid(accountAnalyze.dataGridId);
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 发送邮件
	 */
	sendEmail : function(operFlag, batchId, obj){
		if(operFlag == 0){
			// 批量发送邮件
			var checkeds = $("#" + accountAnalyze.dataGridId).datagrid('getChecked');
			var ids = "";
			for ( var i = 0; i < checkeds.length; i++) {
				var generateStatus = checkeds[i].generateStatus;
				var sendStatus = checkeds[i].sendStatus;
				if(generateStatus != '已生成'){
					$.messager.alert('提示', "不能选择包含未生成报告的记录", 'info');
					return;
				}
				if(sendStatus == '发送成功'){
					$.messager.alert('提示', "不能选择包含已经发送邮件成功的记录", 'info');
					return;
				}
				if(i == checkeds.length-1){
					ids += checkeds[i].batchId;
				}else{
					ids += checkeds[i].batchId + ",";
				}
			}
			if (ids.length <= 0) {
				$.messager.alert('提示', "请选择已生成报告的记录进行发送邮件", 'info');
				return;
			}else{
				batchId = ids;
			}
		}else{
			// 单个发送邮件
			var generateStatus = obj.getAttribute("attr2");
			var sendStatus = obj.getAttribute("attr3");
			if(generateStatus != '已生成'){
				$.messager.alert('提示', "请选择已生成报告的记录进行发送邮件", 'info');
				return;
			}
			if(sendStatus == '发送成功'){
				$.messager.alert('提示', "该记录已经发送邮件成功，不能重复发送", 'info');
				return;
			}
		}

		$.messager.confirm('确认', "确认发送邮件吗?", function(r) {
			if (r) {
				var url = BASE_PATH + "AccountAnalyzeController/sendEmail";
				$.ajax({
					type : "post",
					dataType : "json",
					url : url,
					data : {
						"batchIds" : batchId + ""
					},
					success : function(data) {
						common.isSuccess(data);
					},
					error: function(data){
						common.error();
			        }
				});
			}
		});
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = accountAnalyze.getQueryParams();
		var url = BASE_PATH + "AccountAnalyzeController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}




















