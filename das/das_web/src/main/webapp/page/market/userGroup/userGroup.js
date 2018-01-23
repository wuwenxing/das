$(function() {
	userGroup.init();
});

var userGroup = {
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
		userGroup.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'UserGroupController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'groupId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 250, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.groupId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'tempFieldName', title : '分组ID', sortable : false, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
						return rowData.groupId;
				}},
	 			{field : 'code', title : '分组编号', sortable : true, width : 100},
	 			{field : 'name', title : '分组名称', sortable : true, width : 100},
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
		$('#' + userGroup.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'groupId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				userGroup.addDlog("2", rowData.groupId);
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + userGroup.dataGridId).datagrid('options');
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
		userGroup.getQueryParams();
		common.loadGrid(userGroup.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + userGroup.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + userGroup.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + userGroup.addDlogOkId).css("display", "");
		$("#" + userGroup.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#groupId').val("");
			$("#" + userGroup.addDlogId).dialog('open').dialog('setTitle', '新增分组');
		}else{
			var url = BASE_PATH + "UserGroupController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"groupId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + userGroup.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + userGroup.addDlogOkId).css("display", "");
							$("#" + userGroup.addDlogId).dialog('open').dialog('setTitle', '修改分组');
						}else if(operFlag == 2){
							$("#" + userGroup.addDlogOkId).css("display", "none");
							$("#" + userGroup.addDlogId).dialog('open').dialog('setTitle', '查看分组');
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
	 * 新增分组提交
	 */
	save: function(){
		if(!common.submitFormValidate(userGroup.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "UserGroupController/save",
			data : $("#" + userGroup.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + userGroup.addDlogId).dialog('close');
					common.reloadGrid(userGroup.dataGridId);
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
	deleteById : function(groupIdArray, tip){
		var url = BASE_PATH + "UserGroupController/deleteById";
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
						"groupIdArray" : groupIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(userGroup.dataGridId);
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
		var checkeds = $("#" + userGroup.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].groupId;
			}else{
				ids += checkeds[i].groupId + ",";
			}
		}
		if (ids.length > 0) {
			userGroup.deleteById(ids, "确认删除选择的记录吗?");
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
		var queryParams = userGroup.getQueryParams();
		var url = BASE_PATH + "UserGroupController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 未知请求日志
	 */
	viewUnknownLog : function(){
		var url = "UserGroupLogController/page?type=unknown";
		parent.index.addOrUpdateTabs("未知分组请求日志", url);
	},
	/**
	 * 请求日志
	 */
	viewLog : function(id){
		var url = "UserGroupLogController/page?groupId=" + id;
		parent.index.addOrUpdateTabs("分组请求日志", url);
	},
	/**
	 * 分组详情
	 */
	viewDetail : function(id){
		var url = "UserGroupDetailController/page?groupId=" + id;
		parent.index.addOrUpdateTabs("分组详情", url);
	},
	test : function(){
		$.ajax({
			type : "post",
			dataType : "json",
//			url: "http://localhost:8080/das_web/UserGroupController/updateUser",
			url: "http://localhost:8080/das_web/customerGroup/updateCustomer",
			data : {
				timestamp : "20161008140800", 
//	 			accountSid: "fa573c78eaa8402cb6c84dabfcce7158", 
//	 			sign : "e9ccb14450d1e7d03014defbf6d401e8", 
				accountSid: "fa573c78eaa8402cb6c84dabfcce7159", 
				sign : "5ed770bc2418fa0081fdb4538deec4a3", 
				groupCode : "week_review", 
				type : "add", 
				phones : "18813982018,15839895686,13553828610,13632622518,13510548359", 
				emails : "1493620862@qq.com,499596110@qq.com,472908590@qq.com,122341696@qq.com,flameszhou@sina.com"
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




















