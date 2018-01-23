$(function() {
	tag.init();
});

var tag = {
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
		tag.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'TagController/pageList';
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
	 			{field : 'tagName', title : '标签名称', sortable : true, width : 150},
	 			{field : 'tagUrl', title : '标签链接', sortable : true, width : 500},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 150}
 			] ];
		$('#' + tag.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'tagId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				tag.addDlog("2", rowData.tagId);
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + tag.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['tagName'] = $("#tagNameSearch").val();
		queryParams['tagUrl'] = $("#tagUrlSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		tag.getQueryParams();
		common.loadGrid(tag.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + tag.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + tag.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + tag.addDlogOkId).css("display", "");
		$("#" + tag.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#tagId').val("");
			$("#" + tag.addDlogId).dialog('open').dialog('setTitle', '新增标签');
		}else{
			var url = BASE_PATH + "TagController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"tagId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + tag.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + tag.addDlogOkId).css("display", "");
							$("#" + tag.addDlogId).dialog('open').dialog('setTitle', '修改标签');
						}else if(operFlag == 2){
							$("#" + tag.addDlogOkId).css("display", "none");
							$("#" + tag.addDlogId).dialog('open').dialog('setTitle', '查看标签');
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
	 * 新增标签提交
	 */
	save: function(){
		if(!common.submitFormValidate(tag.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "TagController/save",
			data : $("#" + tag.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + tag.addDlogId).dialog('close');
					common.reloadGrid(tag.dataGridId);
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
		var url = BASE_PATH + "TagController/deleteById";
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
							common.reloadGrid(tag.dataGridId);
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
		var checkeds = $("#" + tag.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].tagId;
			}else{
				ids += checkeds[i].tagId + ",";
			}
		}
		if (ids.length > 0) {
			tag.deleteById(ids, "确认删除选择的记录吗?");
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
		var queryParams = tag.getQueryParams();
		var url = BASE_PATH + "TagController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















