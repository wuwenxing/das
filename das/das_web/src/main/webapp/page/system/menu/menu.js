$(function() {
	menu.init();
});

var menu = {
	dataGridId: "dataGrid",
	rowOperationId: "rowOperation",
	saveDlogFormId: "saveDlogForm",
	searchFormId: "searchForm",
	addDlogId: "addDlog",
	addDlogFormId: "addDlogForm",
	addDlogToolbarId: "addDlogToolbar",
	addDlogOkId: "addDlogOk",
	addDlogCancelId: "addDlogCancel",
	loadDataGridFlag:false,
	/**
	 * 初始化
	 */
	init:function(){
		menu.loadMenuTree();
	},
	/**
	 * 功能：菜单tree初始化
	 */
	loadMenuTree : function(){
		var url = BASE_PATH + 'SystemMenuController/loadMenuTree';
		//默认隐藏右边菜单form div
		$("#menuDiv").hide();
		
		//初始化左边的树形菜单
		menu.menuId = $('#menuTree');
		menu.menuId.tree({
            checkbox: false,
            lines: true,
            url: url,
            onSelect : function(node){
            	//选中菜单节点时调用
            	var parentNode = menu.menuId.tree('getParent', node.target) ;
            	parentNodeName = parentNode ? (parentNode.text + "/" + parentNode.id) : '';
            	menu.getMenu(node.id, parentNodeName);
            	$("#menuDiv").show();
            	$("#menuLeftDiv").css("margin-top", "0px");
            },
            //右键菜单节点时调用
            onContextMenu : function(e, node){
            	
            },
            onLoadSuccess : function(){
            	// 设置外层div高度
            	var height = $("#menuTree").height() + 140;
            	$("#menuDivBox_1").height(height);
            	$("#menuDivBox_2").height(height);
            	// 设置外层iFrame高度
            	common.iFrameHeight();
            }
		});
	},
	/**
	 * 功能：根据菜单Id、父节点名称-->获取菜单对象
	 * @param menuCode          菜单code
	 * @param parentNodeName  父节点名称 
	 */
	getMenu : function(menuCode, parentNodeName){
		var url = BASE_PATH + 'SystemMenuController/findByCode';
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data : {
				"menuCode" : menuCode
			},
			success : function(data) {
				if(null != data) {
					$("#parentMenuName").text(parentNodeName);
					$("#" + menu.saveDlogFormId).form('load', data);
	            	//加载拥有此权限的用户列表
	            	menu.find(data.menuId);
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 功能：展开菜单
	 */
	expandAll : function(){
		menu.menuId.tree('expandAll');
//		var node = menu.menuId.tree('getSelected');  
//        if(node) {
//        	menu.menuId.tree('expandAll', node.target);  
//        }else {
//        	menu.menuId.tree('expandAll');
//        }
	},
	/**
	 * 功能：收起菜单
	 */
	collapseAll : function(){
    	menu.menuId.tree('collapseAll');  
//		var node = menu.menuId.tree('getSelected');  
//        if(node) {
//        	menu.menuId.tree('collapseAll', node.target);  
//        }else {
//        	menu.menuId.tree('collapseAll');  
//        }
	},
	/**
	 * 功能：刷新菜单节点
	 */
	reloadTree : function(){
		menu.menuId.tree('reload');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + menu.addDlogId).dialog('close');
	},
	/**
	 * 功能：添加菜单节点
	 */
	appendTree : function(){
		var node = menu.menuId.tree('getSelected');
		if(node != null){
			if(node.attributes  != null && node.attributes  != undefined && node.attributes.type == 2){
				$.messager.alert('提示','对不起,功能菜单下面不能再新增菜单!','warning');
				return;
			}
			$("#parentMenuCode").val(node.id);
			$("#parentMenuName_Add").text(node.text + "/" + node.id);
		}else{
			$("#parentMenuCode").val("");
			$("#parentMenuName_Add").text("");
		}
		$("#" + menu.addDlogOkId).css("display", "");
		$("#" + menu.addDlogFormId).form('reset');
		$("#" + menu.addDlogId).dialog('open').dialog('setTitle', '新增菜单');
	},
	/**
	 * 新增菜单提交
	 */
	add: function(){
		if(!common.submitFormValidate(menu.addDlogFormId)){
			return false;
		}
		if($("#parentMenuId").val() == '' && $("#menuType option:selected").val() == '2'){
			$.messager.alert('提示','对不起,一级菜单下面不能新增功能菜单!','warning');
			return false;
		}

		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SystemMenuController/save",
			data : $("#" + menu.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + menu.addDlogId).dialog('close');
					//菜单添加成功后,刷新菜单节点
					menu.reloadTree();
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 保存菜单提交
	 */
	save: function(){
		if(!common.submitFormValidate(menu.saveDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SystemMenuController/save",
			data : $("#" + menu.saveDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					menu.updateTree($("#menuName").val());
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 功能：删除菜单节点
	 */
	removeTree : function(){
		var node = menu.menuId.tree('getSelected');
		$.messager.confirm('确认', '确定要删除该节点及对应的子节点吗?', function(r) {
			if(r) {
				var url = BASE_PATH + "SystemMenuController/deleteByMenuCode";
				$.ajax({
					type : "post",
					dataType : "json",
					url : url,
					data : {
						"menuCode" : node.id + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							menu.menuId.tree('remove', node.target);
							$("#menuDiv").hide();
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
	 * 功能：更新菜单节点
	 */
	updateTree : function(menuName){
		var node = menu.menuId.tree('getSelected');
		if (node){
			menu.menuId.tree('update', {
				target: node.target,
				text: menuName
			});
		}
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(menuId){
		// 初始参数
		var url = BASE_PATH + 'SystemUserController/pageList';
		var queryParams = {
			"menuId":menuId
		};
		var columns = [ [
			    {field : 'userId', checkbox : false, hidden:true},
	 			{field : 'userNo', title : '账号', sortable : true, width : 150},
	 			{field : 'userName', title : '姓名', sortable : true, width : 150},
	 			{field : 'companyIds', title : '业务权限', sortable : true, width : 200, 
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
	 			{field : 'enableFlag', title : '状态', sortable : true, width : 100, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.enableFlag == 'Y'){
							return "启用";
						}else{
							return "禁用";
						}
				}},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 100}
 			] ];
		$('#' + menu.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'userId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function(menuId) {
		var options = $("#" + menu.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['menuId'] = menuId;
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(menuId){
		if(menu.loadDataGridFlag){
			menu.getQueryParams(menuId);
			common.loadGrid(menu.dataGridId);
		}else{
			menu.loadDataGridFlag = true;
        	menu.loadDataGrid(menuId);
		}
	}
}


