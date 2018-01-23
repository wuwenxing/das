$(function() {
	channel.init();
});

var channel = {
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
		channel.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'ChannelController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'channelId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.channelId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'channelType', title : '渠道类型', sortable : true, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(!common.isBlank(value)){
		 					if(value == 'webSite'){
		 						return "网站渠道";
		 					}else if(value == 'app'){
		 						return "app渠道";
		 					}else if(value == 'room'){
		 						return "直播间渠道";
		 					}
	 					}
	 					return value;
				}},
	 			{field : 'channelName', title : '渠道名称', sortable : true, width : 150},
	 			{field : 'utmcsr', title : '来源', sortable : true, width : 150},
	 			{field : 'utmcmd', title : '媒介', sortable : true, width : 150},
	 			{field : 'isPay', title : '是否付费', sortable : true, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
	 					if(!common.isBlank(value)){
		 					if(value == 1){
		 						return "免费";
		 					}else if(value == 2){
		 						return "付费";
		 					}else if(value == 3){
		 						return "其他";
		 					}
	 					}
	 					return value;
				}},
	 			{field : 'channelGroup', title : '渠道分组', sortable : true, width : 150},
	 			{field : 'channelLevel', title : '渠道分级', sortable : true, width : 150},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 100}
 			] ];
		$('#' + channel.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'channelId', // 唯一字段
			sortName : 'channelName',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				channel.addDlog("2", rowData.channelId);
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + channel.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['channelName'] = $("#channelNameSearch").val();
		queryParams['channelType'] = $("#channelTypeSearch").combobox('getValue');
		queryParams['utmcsr'] = $("#utmcsrSearch").val();
		queryParams['utmcmd'] = $("#utmcmdSearch").val();
		queryParams['isPay'] = $("#isPaySearch").combobox('getValue');
		queryParams['channelGroup'] = $("#channelGroupSearch").val();
		queryParams['channelLevel'] = $("#channelLevelSearch").val();
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		channel.getQueryParams();
		common.loadGrid(channel.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + channel.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + channel.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + channel.addDlogOkId).css("display", "");
		$("#" + channel.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#channelId').val("");
			$("#" + channel.addDlogId).dialog('open').dialog('setTitle', '新增渠道');
		}else{
			var url = BASE_PATH + "ChannelController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"channelId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + channel.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + channel.addDlogOkId).css("display", "");
							$("#" + channel.addDlogId).dialog('open').dialog('setTitle', '修改渠道');
						}else if(operFlag == 2){
							$("#" + channel.addDlogOkId).css("display", "none");
							$("#" + channel.addDlogId).dialog('open').dialog('setTitle', '查看渠道');
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
	 * 新增渠道提交
	 */
	save: function(){
		if(!common.submitFormValidate(channel.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "ChannelController/save",
			data : $("#" + channel.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + channel.addDlogId).dialog('close');
					common.reloadGrid(channel.dataGridId);
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 导入渠道
	 */
	addDlog_1: function() {
		$("#" + channel.addDlogOkId_1).css("display", "");
		$("#" + channel.addDlogFormId_1).form('reset');
		$("#" + channel.addDlogId_1).dialog('open').dialog('setTitle', '导入渠道');
	},
	/**
	 * close新增窗口
	 */
	cancel_1: function(){
		$('#' + channel.addDlogId_1).dialog('close');
	},
	/**
	 * 导入渠道-提交
	 */
	submitChannel: function() {
		var channelType = $("#channelType").combobox('getValue');
		if(common.isBlank(channelType)){
			$.messager.alert('提示', "请选择对应的渠道类型!", 'error');
			return false;
		}
		
		upload.uploadExcelFile(BASE_PATH + 'UploadController/channelUploadExcel/'+channelType+'/channel/callback', 'uploadDiv', '1', '上传Excel文件');
	},
	/**
	 * 导入渠道回调此函数
	 */
	callback: function(msg){
		$.messager.progress('close');
		$("#msg").val(msg);
	},
	/**
	 * 单个删除
	 */
	deleteById : function(channelIdArray, tip){
		var url = BASE_PATH + "ChannelController/deleteById";
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
						"channelIdArray" : channelIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(channel.dataGridId);
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
		var checkeds = $("#" + channel.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].channelId;
			}else{
				ids += checkeds[i].channelId + ",";
			}
		}
		if (ids.length > 0) {
			channel.deleteById(ids, "确认删除选择的记录吗?");
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
		var queryParams = channel.getQueryParams();
		var url = BASE_PATH + "ChannelController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















