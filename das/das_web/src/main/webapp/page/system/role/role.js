$(function() {
	systemRole.init();
});

var systemRole = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	searchFormId: "searchForm",
	addDlogId: "addDlog",
	addDlogFormId: "addDlogForm",
	addDlogToolbarId: "addDlogToolbar",
	addDlogOkId: "addDlogOk",
	addDlogCancelId: "addDlogCancel",
	assignUserOpenFlag: false,
	/**
	 * 初始化
	 */
	init:function(){
		systemRole.loadDataGrid();

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
		var url = BASE_PATH + 'SystemRoleController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'roleId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 200, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.roleId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'roleCode', title : '角色编号', sortable : true, width : 150},
	 			{field : 'roleName', title : '角色名称', sortable : true, width : 150},
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
		$('#' + systemRole.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'roleId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				systemRole.addDlog("2", rowData.roleId);
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + systemRole.dataGridId).datagrid('options').queryParams;
		queryParams['roleCode'] = $("#roleCodeSearch").val();
		queryParams['roleName'] = $("#roleNameSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		systemRole.getQueryParams();
		common.loadGrid(systemRole.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + systemRole.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + systemRole.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + systemRole.addDlogOkId).css("display", "");
		$("#" + systemRole.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#roleId').val("");
			$("#" + systemRole.addDlogId).dialog('open').dialog('setTitle', '新增角色');
		}else{
			var url = BASE_PATH + "SystemRoleController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"roleId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + systemRole.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + systemRole.addDlogOkId).css("display", "");
							$("#" + systemRole.addDlogId).dialog('open').dialog('setTitle', '修改角色');
						}else if(operFlag == 2){
							$("#" + systemRole.addDlogOkId).css("display", "none");
							$("#" + systemRole.addDlogId).dialog('open').dialog('setTitle', '查看角色');
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
	 * 新增角色提交
	 */
	save: function(){
		if(!common.submitFormValidate(systemRole.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SystemRoleController/save",
			data : $("#" + systemRole.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + systemRole.addDlogId).dialog('close');
					common.reloadGrid(systemRole.dataGridId);
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
	deleteById : function(roleIdArray, tip){
		var url = BASE_PATH + "SystemRoleController/deleteById";
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
						"roleIdArray" : roleIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(systemRole.dataGridId);
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
		var checkeds = $("#" + systemRole.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].roleId;
			}else{
				ids += checkeds[i].roleId + ",";
			}
		}
		if (ids.length > 0) {
			systemRole.deleteById(ids, "确认删除选择的记录吗?");
		} else {
			$.messager.alert('提示', "请选择要删除的记录", 'info');
		}
	},
	/**
	 * 分配用户
	 */
	assignUser: function(id) {
		$('#userSearchForm').form('reset');
		$("#assignUserDlog").css("display", "");
		$("#assignUserDlog").dialog('open').dialog('setTitle', '分配用户');
		$("#assignUserRoleId").val(id);
		if(!systemRole.assignUserOpenFlag){
			systemRole.assignUserOpenFlag = true;
			// 初始参数
			var queryParams = {
				roleId : id
			};
			var columns = [ [
				    {field : 'userId', checkbox : true},
		 			{field : 'userNo', title : '账号', sortable : true, width : 150},
		 			{field : 'userName', title : '姓名', sortable : true, width : 150},
		 			{field : 'enableFlag', title : '状态', sortable : true, width : 150, 
		 				formatter : function(value, rowData, rowIndex) {
							if(rowData.enableFlag == 'Y'){
								return "启用";
							}else{
								return "禁用";
							}
					}}
	 			] ];
			$('#userDataGrid').datagrid({
				fitColumns : true, // 列的宽度和自动大小
				singleSelect : true, // select时true单选false多选
				checkOnSelect : false, // select时true则check同样勾选，false则不勾选
				selectOnCheck : false, // check是否可多选选中
				fit : false, // 自动适屏
				height: '526',
				border : false,
				url : BASE_PATH + "SystemRoleController/assignUser",
				queryParams : queryParams,
				columns : columns,
				idField : 'userId', // 唯一字段
				sortName : 'updateDate',
				sortOrder : 'desc',
				rownumbers : true, // 行号显示
				pagination : true, // 分页控件显示
				pageNumber : 1, // 初始页码
				pageSize : 10, // 初始大小
			    checkbox: true, // 显示checkbox
				pageList : [ 20, 50, 100, 200, 500 ],
				onCheck:function(index,row){
					row.checked = true;
				},
				onUncheck:function(index,row){
					row.checked = false;
				},
				onCheckAll:function(rows){
					for(var i=0; i< rows.length; i++){
						rows[i].checked = true;
					}
				},
				onUncheckAll:function(rows){
					for(var i=0; i< rows.length; i++){
						rows[i].checked = false;
					}
				}
			});
		}else{
			systemRole.assignUserFind();
		}
	},
	/**
	 * 分配用户-条件查询
	 */
	assignUserFind: function(){
		var queryParams = $("#userDataGrid").datagrid('options').queryParams;
		queryParams['userNo'] = $("#userNoSearch").val();
		queryParams['userName'] = $("#userNameSearch").val();
		queryParams['roleId'] = $("#assignUserRoleId").val();
		common.loadGrid("userDataGrid");
	},
	/**
	 * 分配用户-刷新
	 */
	assignUserRefresh: function(){
		var queryParams = $("#userDataGrid").datagrid('options').queryParams;
		queryParams['userNo'] = $("#userNoSearch").val();
		queryParams['userName'] = $("#userNameSearch").val();
		queryParams['roleId'] = $("#assignUserRoleId").val();
		common.reloadGrid("userDataGrid");
	},
	/**
	 * 分配用户保存
	 */
	assignUserSave: function() {
		var dataRows = $("#userDataGrid").datagrid('getRows');
		var selectIds = "";
		var unSelectIds = "";
		for ( var i = 0; i < dataRows.length; i++) {
			if(dataRows[i].checked){
				if(i == dataRows.length-1){
					selectIds += dataRows[i].userId;
				}else{
					selectIds += dataRows[i].userId + ",";
				}
			}else{
				if(i == dataRows.length-1){
					unSelectIds += dataRows[i].userId;
				}else{
					unSelectIds += dataRows[i].userId + ",";
				}
			}
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SystemRoleController/assignUserSave",
			data : {
				"roleId" : $("#assignUserRoleId").val(),
				"selectIds" : selectIds + "",
				"unSelectIds" : unSelectIds + ""
			},
			success : function(data) {
				if(common.isSuccess(data)){
					// $("#assignUserDlog").dialog('close');
				}
			},
			error: function(data){
				common.error();
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
		$("#assignMenuRoleId").val(id);
		//初始化树形菜单
		$('#menuTree').tree({
            checkbox: true,
            cascadeCheck : false,
            lines: true,
            url: BASE_PATH + 'SystemRoleController/loadMenuTreeShowCheckbox/' + id,
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
			url: BASE_PATH + "SystemRoleController/assignMenuSave",
			data : {
				"roleId" : $("#assignMenuRoleId").val(),
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
	changeCascadeCheck: function(){
    	$('#menuTree').tree('options').cascadeCheck = $("#menuTreeCheckbox").is(':checked');
	}
	
}


