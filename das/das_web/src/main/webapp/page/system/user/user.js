$(function() {
	user.init();
});

var user = {
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
		user.loadDataGrid();

		$("select[multiple='multiple']").multiselect({
			
			noneSelectedText: "---请选择---", 
			selectedList: 10 // 0-based index
		}).multiselectfilter();
		$(".ui-multiselect-menu").css("width", "217px");
		
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'SystemUserController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'userId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 330, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.userId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'userNo', title : '账号', sortable : true, width : 130},
	 			{field : 'userName', title : '姓名', sortable : true, width : 130},
	 			{field : 'companyIds', title : '业务权限', sortable : true, width : 160, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var returnString = "";
						if(!common.isBlank(value)){
							var companyIdAry = value.split(",");
							for(var i=0; i<companyIdAry.length; i++){
								if(companyIdAry[i] == '0'){
									returnString += "集团，";
								}else if(companyIdAry[i] == '1'){
									returnString += "外汇，";
								}else if(companyIdAry[i] == '2'){
									returnString += "贵金属，";
								}else if(companyIdAry[i] == '3'){
									returnString += "恒信，";
								}else if(companyIdAry[i] == '4'){
									returnString += "创富，";
								}
							}
							returnString = returnString.substring(0, returnString.length - 1);
						}
						return returnString;
				}},
	 			{field : 'enableFlag', title : '状态', sortable : true, width : 60, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.enableFlag == 'Y'){
							return "启用";
						}else{
							return "禁用";
						}
				}},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 130}
 			] ];
		$('#' + user.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'userId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				user.addDlog("2", rowData.userId);
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + user.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['userNo'] = $("#userNoSearch").val();
		queryParams['userName'] = $("#userNameSearch").val();
		queryParams['companyId'] = $("#companyIdSearch").val();
		queryParams['enableFlag'] = $("#enableFlagSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		user.getQueryParams();
		common.loadGrid(user.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + user.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + user.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + user.addDlogOkId).css("display", "");
		$("#" + user.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#userId').val("");
			$("#" + user.addDlogId).dialog('open').dialog('setTitle', '新增用户');
		}else{
			var url = BASE_PATH + "SystemUserController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"userId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + user.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + user.addDlogOkId).css("display", "");
							$("#" + user.addDlogId).dialog('open').dialog('setTitle', '修改用户');
						}else if(operFlag == 2){
							$("#" + user.addDlogOkId).css("display", "none");
							$("#" + user.addDlogId).dialog('open').dialog('setTitle', '查看用户');
						}
						
						// multiselect复选框勾选
						var companyIds = ',' + data.companyIds + ',';// 添加分隔符号，好indexOf进行比较
						$('#companyIds option').each(function(){
							if(companyIds.indexOf(',' + this.value + ',')!=-1)this.selected=true;
						});
						$("select[multiple='multiple']").multiselect("refresh");
						$(".ui-multiselect-menu").css("width", "217px");
						
					}
				},
				error: function(data){
					common.error();
		        }
			});
		}
	},
	/**
	 * 新增用户提交
	 */
	save: function(){
		if(!common.submitFormValidate(user.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SystemUserController/save",
			data : $("#" + user.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + user.addDlogId).dialog('close');
					common.reloadGrid(user.dataGridId);
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
	deleteById : function(userIdArray, tip){
		var url = BASE_PATH + "SystemUserController/deleteById";
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
						"userIdArray" : userIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(user.dataGridId);
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
		var checkeds = $("#" + user.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].userId;
			}else{
				ids += checkeds[i].userId + ",";
			}
		}
		if (ids.length > 0) {
			user.deleteById(ids, "确认删除选择的记录吗?");
		} else {
			$.messager.alert('提示', "请选择要删除的记录", 'info');
		}
	},
	/**
	 * 重置密码
	 */
	resetPassword: function(userId){
		$.messager.confirm('确认', "重置为初始密码：<span style=\"color:red\">111</span>，确认要重置密码吗?", function(r) {
			if (r) {
				$.ajax({
					type : "post",
					dataType : "json",
					url: BASE_PATH + "SystemUserController/resetPassword",
					data : {
						userId : userId
					},
					success : function(data) {
						if(common.isSuccess(data)){
							
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
	 * 菜单权限
	 */
	assignMenu: function(id) {
		$("#assignMenuDlog").css("display", "");
		$("#assignMenuDlogForm").form('reset');
		$("#assignMenuDlog").dialog('open').dialog('setTitle', '菜单权限');
		$("#assignMenuUserId").val(id);
		//初始化树形菜单
		$('#menuTree').tree({
            checkbox: true,
            cascadeCheck : false,
            lines: true,
            url: BASE_PATH + 'SystemUserController/loadMenuTreeShowCheckbox/' + id,
            onLoadSuccess : function(node, data){
            	$('#menuTree').tree('options').cascadeCheck = true;
            }
		});
	},
	/**
	 * 菜单权限保存
	 */
	assignMenuSave: function() {
		var ids=[],types=[],nodes = $('#menuTree').tree('getChecked');
		// 获取勾选中的
		if(nodes != null && nodes.length > 0){
			for(var i=0;i<nodes.length;i++){
				ids.push(nodes[i].id);
				types.push(nodes[i].attributes.type);
			}
		}
		// 获取级联选中的
		var otherNodes = $('#menuTree').tree('getChecked','indeterminate');
		if(otherNodes != null &&  otherNodes.length > 0){
			for(var i=0;i<otherNodes.length;i++){
				ids.push(otherNodes[i].id);
				types.push(otherNodes[i].attributes.type);
			}
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SystemUserController/assignMenuSave",
			data : {
				"userId" : $("#assignMenuUserId").val(),
				"menuCodes" : ids.join(","),
				"menuTypes": types.join(",")
			},
			success : function(data) {
				if(common.isSuccess(data)){
					$("#assignMenuDlog").dialog('close');
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 复选框级联
	 */
	changeCascadeCheck: function(){
    	$('#menuTree').tree('options').cascadeCheck = $("#menuTreeCheckbox").is(':checked');
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = user.getQueryParams();
		var url = BASE_PATH + "SystemUserController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 导出excel
	 */
	exportExcelAuth : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = user.getQueryParams();
		var url = BASE_PATH + "SystemUserController/exportExcelAuth?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
}




















