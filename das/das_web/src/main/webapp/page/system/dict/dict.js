$(function() {
	dict.init();
});

var dict = {
	dataGridId: "dataGrid",
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
		dict.loadTreeGrid();
	},
	/**
	 * 加载treeGrid
	 */
	loadTreeGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'SystemDictController/loadTreeGrid';
		var queryParams = {
			
		};
		var columns = [ [
	 			{field : 'oper', title : '操作', sortable : false, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.type == 1){
							$("#rowOperation_1 a").each(function(){
								$(this).attr("id", rowData.id);
						    });
							return $("#rowOperation_1").html();
	 					}else if(rowData.type == 2){
							$("#rowOperation_2 a").each(function(){
								$(this).attr("id", rowData.id);
						    });
							return $("#rowOperation_2").html();
	 					}
				}},
				{field : 'dictCode', title : '编号', sortable : true, width : 150},
	 			{field : 'dictName', title : '名称', sortable : true, width : 150},
	 			{field : 'enableFlag', title : '状态', sortable : true, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.enableFlag == 'Y'){
							return "启用";
						}else{
							return "禁用";
						}
				}}
 			] ];
		$('#' + dict.dataGridId).treegrid(easyui.defaultOption).treegrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'id', // 唯一字段
 			treeField : 'dictCode',
			method : 'post',
			animate:true,
			collapsible:true,
			sortName : 'orderCode',
			sortOrder : 'asc',
			// 此方法主要用于异步处理
			onBeforeLoad:function(row, param){
				if(row){
					$(this).treegrid('options').url = BASE_PATH +'SystemDictController/loadChildTreeGrid/'+row.dictCode;
				}else{
					$(this).treegrid('options').url = BASE_PATH+'SystemDictController/loadTreeGrid';
				}
			},
			onDblClickRow : function(row, param) {
				if(row.type == 1){
					dict.refresh(row.id);
				}
			},
			onExpand : function(row){
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dict.dataGridId).treegrid('options').queryParams;
		queryParams['dictCode'] = $("#dictCodeSearch").val();
		queryParams['dictName'] = $("#dictNameSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dict.getQueryParams();
		$("#" + dict.dataGridId).treegrid('unselectAll');
		$("#" + dict.dataGridId).treegrid('uncheckAll');
		$("#" + dict.dataGridId).treegrid('load');
	},
	/**
	 * reload
	 */
	reload: function(){
		dict.getQueryParams();
		$("#" + dict.dataGridId).treegrid('unselectAll');
		$("#" + dict.dataGridId).treegrid('uncheckAll');
		$("#" + dict.dataGridId).treegrid('reload');
	},
	/**
	 * 刷新
	 */
	refresh: function(id){
		$('#'+dict.dataGridId).treegrid('reload', id);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dict.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + dict.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增字典分组，1修改，2查看,3新增字典
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + dict.addDlogOkId).css("display", "");
		$("#" + dict.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#dictId').val("");
			$('#dictType').val("1");// type=1为数据字典分组
			$('#parentDictCode').val("");
			$('#parentDictCodeSpan').text("");
			$("#" + dict.addDlogId).dialog('open').dialog('setTitle', '新增数据字典分组');
		}else if(operFlag == 3){
			$('#dictId').val("");
			$('#dictType').val("2");// type=2为数据字典
			var node = $('#'+dict.dataGridId).treegrid('getSelected');
			if(node == null || node.type == 2){
				$.messager.alert('提示', '请选择上级数据字典分组','warning');
				return;
			}
			var parentCode = node.dictCode;
			$('#parentDictCode').val(parentCode);
			$('#parentDictCodeSpan').text(parentCode);
			$("#" + dict.addDlogId).dialog('open').dialog('setTitle', '新增数据字典');
		}else{
			var url = BASE_PATH + "SystemDictController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"dictId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + dict.addDlogFormId).form('load', data);
						if(null != data.parentDictCode){
							$("#parentDictCodeSpan").text(data.parentDictCode);
						}else{
							$('#parentDictCodeSpan').text("");
						}
						if(operFlag == 1){
							$("#" + dict.addDlogOkId).css("display", "");
							$("#" + dict.addDlogId).dialog('open').dialog('setTitle', '修改数据字典');
						}else if(operFlag == 2){
							$("#" + dict.addDlogOkId).css("display", "none");
							$("#" + dict.addDlogId).dialog('open').dialog('setTitle', '查看数据字典');
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
	 * 新增数据字典提交
	 */
	save: function(){
		if(!common.submitFormValidate(dict.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SystemDictController/save",
			data : $("#" + dict.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + dict.addDlogId).dialog('close');
					dict.reload();
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
	deleteById : function(dictId, tip){
		var url = BASE_PATH + "SystemDictController/deleteById";
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
						"dictId" : dictId + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							dict.reload();
						}
					},
					error: function(data){
						common.error();
			        }
				});
			}
		});
	}
	
}




















