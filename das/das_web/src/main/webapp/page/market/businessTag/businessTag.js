$(function() {
	businessTag.init();
});

var businessTag = {
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
		businessTag.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'businessTagController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'tagId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 200, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.tagId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'tagContent', title : '标签内容', sortable : true, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(!common.isBlank(value)){
		 					if(value == '1'){
		 						return "开始模拟开户流程";
		 					}else if(value == '2'){
		 						return "开始真实开户流程";
		 					}else if(value == '3'){
		 						return "开始入金流程";
		 					}else if(value == '4'){
		 						return "开始资讯流程";
		 					}
	 					}
	 					return value;
				}},
	 			{field : 'eventCategory', title : 'EventCategory', sortable : true, width : 150},
	 			{field : 'eventAction', title : 'EventAction', sortable : true, width : 150},
	 			{field : 'eventLabel', title : 'EventLabel', sortable : true, width : 150},
	 			{field : 'eventValue', title : 'EventValue', sortable : true, width : 150},
	 			{field : 'tagUrl', title : 'URL', sortable : true, width : 500}
 			] ];
		$('#' + businessTag.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'tagId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				businessTag.addDlog("2", rowData.tagId);
			},onLoadSuccess : function(data){
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + businessTag.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['tagContent'] = $("#tagContentSearch").val();
		queryParams['tagUrl'] = $("#tagUrlSearch").val();
		queryParams['tagEvent'] = $("#tagEventSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		businessTag.getQueryParams();
		common.loadGrid(businessTag.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + businessTag.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$(".tabTypeCombobox").combobox('enable'); 
		$('#' + businessTag.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		if(operFlag == 1){
			$(".tabTypeCombobox").combobox('disable'); 
		}
		
		$("#" + businessTag.addDlogOkId).css("display", "");
		$("#" + businessTag.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#tagId').val("");
			$("#" + businessTag.addDlogId).dialog('open').dialog('setTitle', '新增标签');
		}else{
			var url = BASE_PATH + "businessTagController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"tagId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + businessTag.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + businessTag.addDlogOkId).css("display", "");
							$("#" + businessTag.addDlogId).dialog('open').dialog('setTitle', '修改标签');
						}else if(operFlag == 2){
							$("#" + businessTag.addDlogOkId).css("display", "none");
							$("#" + businessTag.addDlogId).dialog('open').dialog('setTitle', '查看标签');
						}
						var tagType = $(".tabTypeCombobox").combobox('getValue');
						businessTag.showTag(tagType);
					}
				},
				error: function(data){
					common.error();
		        }
			});
		}
	},
	
	/**
	 * 导入标签
	 */
	addDlog_1: function() {
		$("#" + businessTag.addDlogOkId_1).css("display", "");
		$("#" + businessTag.addDlogFormId_1).form('reset');
		$("#" + businessTag.addDlogId_1).dialog('open').dialog('setTitle', '导入业务标签');
		
		var tagType = $(".uploadTabTypeCombobox").combobox('getValue');
		if(tagType == 1){
			$("#exportHref").attr("href",BASE_PATH+"template/market/businessPageTagTemplate.xlsx");
		}else{
			$("#exportHref").attr("href",BASE_PATH+"template/market/businessEventTagTemplate.xlsx");
		}
	},
	/**
	 * close新增窗口
	 */
	cancel_1: function(){
		$('#' + businessTag.addDlogId_1).dialog('close');
	},
	/**
	 * 导入渠道-提交
	 */
	submitChannel: function() {
		var tagContent = $("#tagContent").combobox('getValue');
		var tagType = $("#tagType").combobox('getValue');
		if(common.isBlank(tagType)){
			$.messager.alert('提示', "请选择对应的标签类型!", 'error');
			return false;
		}
		
		upload.uploadExcelFile(BASE_PATH + 'UploadController/businessTagUploadExcel/'+tagContent+'/'+tagType+'/businessTag/callback', 'uploadDiv', '1', '上传Excel文件');
	},
	/**
	 * 导入渠道回调此函数
	 */
	callback: function(msg){
		$.messager.progress('close');
		$("#msg").val(msg);
	},
	
	/**
	 * 新增标签提交
	 */
	save: function(){
		if(!common.submitFormValidate(businessTag.addDlogFormId)){
			return false;
		}
		$(".tabTypeCombobox").combobox('enable'); 
		var tagType = $(".tabTypeCombobox").combobox('getValue');
		var tagUrl = $("#tagUrl").val();
		if(tagType == 1 && (tagUrl.length == 0 || tagUrl.length >500)){
			$.messager.progress('close');
			$.messager.alert("提示","标签链接输入不合法，请重新输入",'info');
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "businessTagController/save",
			data : $("#" + businessTag.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + businessTag.addDlogId).dialog('close');
					common.reloadGrid(businessTag.dataGridId);
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
	deleteById : function(tagIdArray, tip){
		var url = BASE_PATH + "businessTagController/deleteById";
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
						"tagIdArray" : tagIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(businessTag.dataGridId);
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
		var checkeds = $("#" + businessTag.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].tagId;
			}else{
				ids += checkeds[i].tagId + ",";
			}
		}
		if (ids.length > 0) {
			businessTag.deleteById(ids, "确认删除选择的记录吗?");
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
		var queryParams = businessTag.getQueryParams();
		var url = BASE_PATH + "businessTagController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 统计类型change事件
	 */
	onchange: function(newValue,oldValue){	
		businessTag.showTag(newValue);
	},
	
	/**
	 * 统计类型change事件
	 */
	onChangeExport: function(newValue,oldValue){	
		if(newValue == 1){
			$("#exportHref").attr("href",BASE_PATH+"template/market/businessPageTagTemplate.xlsx");
		}else{
			$("#exportHref").attr("href",BASE_PATH+"template/market/businessEventTagTemplate.xlsx");
		}
	},
	
	showTag:function(value){	
		if(value == 1){
			$("#add1").show();
			$("#add2").hide();
			$("#add3").hide();
		}else{
			$("#add1").hide();
			$("#add2").show();
			$("#add3").show();
		}
	}
	
	
}
