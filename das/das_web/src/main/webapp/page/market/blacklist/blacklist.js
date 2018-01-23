$(function() {
	blacklist.init();
});

var blacklist = {
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
		blacklist.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'BlacklistController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'blacklistId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.blacklistId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'account', title : '账号', sortable : true, width : 150},
	 			{field : 'accountType', title : '账号类型', sortable : true, width : 150},
	 			{field : 'blacklistType', title : '黑名单类型', sortable : true, width : 150},
	 			{field : 'enableFlag', title : '状态', sortable : true, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.enableFlag == 'Y'){
							return "启用";
						}else{
							return "禁用";
						}
				}},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 100}
 			] ];
		$('#' + blacklist.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'blacklistId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				blacklist.addDlog("2", rowData.blacklistId);
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + blacklist.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['account'] = $("#accountSearch").val();
		queryParams['accountType'] = $("#accountTypeSearch").combobox('getValue');
		queryParams['blacklistType'] = $("#blacklistTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		blacklist.getQueryParams();
		common.loadGrid(blacklist.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + blacklist.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + blacklist.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + blacklist.addDlogOkId).css("display", "");
		$("#" + blacklist.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#blacklistId').val("");
			$("#" + blacklist.addDlogId).dialog('open').dialog('setTitle', '新增黑名单');
		}else{
			var url = BASE_PATH + "BlacklistController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"blacklistId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + blacklist.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + blacklist.addDlogOkId).css("display", "");
							$("#" + blacklist.addDlogId).dialog('open').dialog('setTitle', '修改黑名单');
						}else if(operFlag == 2){
							$("#" + blacklist.addDlogOkId).css("display", "none");
							$("#" + blacklist.addDlogId).dialog('open').dialog('setTitle', '查看黑名单');
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
	 * 新增黑名单提交
	 */
	save: function(){
		if(!common.submitFormValidate(blacklist.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "BlacklistController/save",
			data : $("#" + blacklist.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + blacklist.addDlogId).dialog('close');
					common.reloadGrid(blacklist.dataGridId);
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
	deleteById : function(blacklistIdArray, tip){
		var url = BASE_PATH + "BlacklistController/deleteById";
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
						"blacklistIdArray" : blacklistIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(blacklist.dataGridId);
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
		var checkeds = $("#" + blacklist.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].blacklistId;
			}else{
				ids += checkeds[i].blacklistId + ",";
			}
		}
		if (ids.length > 0) {
			blacklist.deleteById(ids, "确认删除选择的记录吗?");
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
		var queryParams = blacklist.getQueryParams();
		var url = BASE_PATH + "BlacklistController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















