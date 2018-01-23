$(function() {
	custConsultation.init();
});

var custConsultation = {
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
		custConsultation.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'CustConsultation/pageList';
		var queryParams = {
			reportType : $("#reportTypeSearch").combobox('getValue'),
			consulttationTime : $("#startTimeSearch").val()
		};
		var columns = [ [
			    {field : 'consulttationId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.consulttationId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'consulttationTime', title : '时间', sortable : true, width : 150},
	 			{field : 'newConsulttationNum', title : '新客咨询数', sortable : true, width : 150},
	 			{field : 'oldConsulttationNum', title : '老客咨询数', sortable : true, width : 150},
	 			{field : 'reportType', title : '周期类型', sortable : true, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.reportType == 'days'){
							return "日统计";
						}else{
							return "月统计";
						}
				}}
 			] ];
		$('#' + custConsultation.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'consulttationId', // 唯一字段
			sortName : 'consulttationTime',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				custConsultation.addDlog("2", rowData.consulttationId);
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + custConsultation.dataGridId).datagrid('options').queryParams;
		queryParams['sort'] = "updateDate";
		queryParams['order'] = "desc";
		queryParams['reportType'] = $("#reportTypeSearch").combobox('getValue');
		queryParams['consulttationTime'] = $("#startTimeSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		custConsultation.getQueryParams();
		common.loadGrid(custConsultation.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + custConsultation.searchFormId).form('reset');		
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$("#startTimeSearch").datebox('setValue', $("#yearStart").val());
		common.setEasyUiCss();
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + custConsultation.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + custConsultation.addDlogOkId).css("display", "");
		$("#" + custConsultation.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#consulttationId').val("");
			$("#" + custConsultation.addDlogId).dialog('open').dialog('setTitle', '新增有效咨询');
		}else{
			var url = BASE_PATH + "CustConsultation/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"consulttationId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + custConsultation.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + custConsultation.addDlogOkId).css("display", "");
							$("#" + custConsultation.addDlogId).dialog('open').dialog('setTitle', '修改有效咨询');
						}else if(operFlag == 2){
							$("#" + custConsultation.addDlogOkId).css("display", "none");
							$("#" + custConsultation.addDlogId).dialog('open').dialog('setTitle', '查看有效咨询');
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
	 * 新增新客有效咨询提交
	 */
	save: function(){
		if(!common.submitFormValidate(custConsultation.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "CustConsultation/save",
			data : $("#" + custConsultation.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + custConsultation.addDlogId).dialog('close');
					common.reloadGrid(custConsultation.dataGridId);
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
	deleteById : function(consulttationIdArray, tip){
		var url = BASE_PATH + "CustConsultation/deleteById";
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
						"consulttationIdArray" : consulttationIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(custConsultation.dataGridId);
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
		var checkeds = $("#" + custConsultation.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].consulttationId;
			}else{
				ids += checkeds[i].consulttationId + ",";
			}
		}
		if (ids.length > 0) {
			custConsultation.deleteById(ids, "确认删除选择的记录吗?");
		} else {
			$.messager.alert('提示', "请选择要删除的记录", 'info');
		}
	},
	/**
	 * 统计类型change事件
	 */
	setDateboxAttr: function(newValue,oldValue,type){
		if(type == 1){
			if(newValue == 'days'){
				$('#startTimeSearch').datebox({
					formatter:easyui.formatterYYYYMMDD,
					parser:easyui.parserYYYYMMDD
				});
				$("#startTimeSearch").datebox('setValue', $("#yearStart").val());		
			}else if(newValue == 'months'){
				$('#startTimeSearch').datebox({
					formatter:easyui.formatterYYYYMM,
					parser:easyui.parserYYYYMM
				});
				$("#startTimeSearch").datebox('setValue', $("#yearStart").val());
			}
		}else{
			if(newValue == 'days'){
				$('#consulttationTime').datebox({
					formatter:easyui.formatterYYYYMMDD,
					parser:easyui.parserYYYYMMDD
				});
				$("#consulttationTime").datebox('setValue', $("#yearStart").val());		
			}else if(newValue == 'months'){
				$('#consulttationTime').datebox({
					formatter:easyui.formatterYYYYMM,
					parser:easyui.parserYYYYMM
				});
				$("#consulttationTime").datebox('setValue', $("#yearStart").val());
			}
		}
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = custConsultation.getQueryParams();
		var url = BASE_PATH + "CustConsultation/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	
	/**
	 * 导入新客咨询数
	 */
	addDlog_1: function() {
		$("#" + custConsultation.addDlogOkId_1).css("display", "");
		$("#" + custConsultation.addDlogFormId_1).form('reset');
		$("#" + custConsultation.addDlogId_1).dialog('open').dialog('setTitle', '导入新客咨询数');
	},
	/**
	 * close新增窗口
	 */
	cancel_1: function(){
		$('#' + custConsultation.addDlogId_1).dialog('close');
	},
	/**
	 * 导入新客咨询数-提交
	 */
	submitChannel: function() {
		var reportType = $("#reportTypeSearchImport").combobox('getValue');
		if(common.isBlank(reportType)){
			$.messager.alert('提示', "请选择对应的时间类型!", 'error');
			return false;
		}
		
		upload.uploadExcelFile(BASE_PATH + 'UploadController/consulttationNumUploadExcel/'+reportType+'/custConsultation/callback', 'uploadDiv', '1', '上传Excel文件');
	},
	/**
	 * 导入新客咨询数回调此函数
	 */
	callback: function(msg){
		$.messager.progress('close');
		$("#msg").val(msg);
	},
	
	
	
}




















