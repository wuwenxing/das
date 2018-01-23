$(function() {
	sms.init();
});

var sms = {
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
	addDlogOkId_1: "addDlogOk_1",
	addDlogCancelId_1: "addDlogCancel_1",
	
	/**
	 * 初始化
	 */
	init:function(){
		sms.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'SmsController/pageList';
		var queryParams = {
		};
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		var columns = [ [
			    {field : 'smsId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.smsId);
							if(rowData.sendType == '定时发送'){
								$(this).attr("type", '1');
							}else{
								$(this).attr("type", '0');
							}
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'content', title : '短信内容', sortable : true, width : 300, 
	 				formatter : function(value, rowData, rowIndex) {
						return rowData.smsSign + rowData.content;
				}},
	 			{field : 'inputNum', title : '手机总数', sortable : true, width : 50},
	 			{field : 'legalNum', title : '合法手机数', sortable : true, width : 50},
	 			{field : 'illegalNum', title : '非法手机数', sortable : true, width : 50},
//	 			{field : 'totalNum', title : '发送总数', sortable : true, width : 50},
//	 			{field : 'successNum', title : '发送成功数', sortable : true, width : 50},
//	 			{field : 'failNum', title : '发送失败数', sortable : true, width : 50},
	 			{field : 'sendType', title : '发送类型', sortable : true, width : 50},
	 			{field : 'timeSwitch', title : '定时开关', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
						if(rowData.timeSwitch == 'Y'){
							return "开启";
						}else{
							return "关闭";
						}
				}},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 100}
 			] ];
		$('#' + sms.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'smsId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				if(rowData.sendType == '定时发送'){
					sms.addDlog_1("2", rowData.smsId, '1');
				}else{
					sms.addDlog("2", rowData.smsId, '0');
				}
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + sms.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['content'] = $("#contentSearch").val();
		queryParams['sendType'] = $("#sendTypeSearch").combobox('getValue');
		queryParams['startDate'] = $("#startDateSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endDateSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		sms.getQueryParams();
		common.loadGrid(sms.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + sms.searchFormId).form('reset');
		$('#startDateSearch').datebox({
			formatter:easyui.formatterYYYYMMDD000000,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endDateSearch').datebox({
			formatter:easyui.formatterYYYYMMDD235959,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + sms.addDlogId).dialog('close');
	},
	/**
	 * close新增窗口
	 */
	cancel_1: function(){
		$('#' + sms.addDlogId_1).dialog('close');
	},
	/**
	 * 复选框click事件
	 */
	checkboxClick: function (obj){
		if(obj.checked){
			obj.setAttribute("checked", "checked");
		}else{
			obj.removeAttribute("checked");
		}
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 * type == 1为定时发送短信
	 */
	addDlog: function(operFlag, id, type) {
		if(type == '1'){
			sms.addDlog_1(operFlag, id, type);
			return;
		}else{
			if(operFlag == 1){
				$.messager.alert('提示', "即时发送短信的记录，不可修改", 'info');
				return;
			}
		}
		
		$("#" + sms.addDlogOkId).css("display", "");
		$("#" + sms.addDlogFormId).form('reset');
		$("#" + sms.addDlogFormId + " input[type='checkbox'][name='userScreenId']").removeAttr("checked");
		$("#" + sms.addDlogFormId + " input[type='checkbox'][name='userGroupId']").removeAttr("checked");
		if(operFlag == 0){
			$('#smsId').val("");
			$("#" + sms.addDlogId).dialog('open').dialog('setTitle', '即时发送短信');
			$('#sourceTypeTabs').tabs('select', '手动输入或上传手机号');
		}else{
			var url = BASE_PATH + "SmsController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"smsId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + sms.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + sms.addDlogOkId).css("display", "");
							$("#" + sms.addDlogId).dialog('open').dialog('setTitle', '修改短信');
						}else if(operFlag == 2){
							$("#" + sms.addDlogOkId).css("display", "none");
							$("#" + sms.addDlogId).dialog('open').dialog('setTitle', '查看短信');
						}
						// 设置手机号页签tabs
						var sourceType = data.sourceType;
						if(sourceType == 'input'){
							$('#sourceTypeTabs').tabs('select', '手动输入或上传手机号');
						}else if(sourceType == 'userScreen'){
							$('#sourceTypeTabs').tabs('select', '用户筛选手机号');
							var screenIds = data.screenIds;
							if(null != screenIds && '' != screenIds){
								var screenIdAry = screenIds.split(",");
								for(var i=0; i<screenIdAry.length; i++){
									var screenId = screenIdAry[i];
									if('' != screenId){
										$("#" + sms.addDlogFormId + " input[type='checkbox'][name='userScreenId'][value='"+screenId+"']").attr("checked", "checked");
									}
								}
							}
						}else if(sourceType == 'userGroup'){
							$('#sourceTypeTabs').tabs('select', '用户分组手机号');
							var groupIds = data.groupIds;
							if(null != groupIds && '' != groupIds){
								var groupIdAry = groupIds.split(",");
								for(var i=0; i<groupIdAry.length; i++){
									var groupId = groupIdAry[i];
									if('' != groupId){
										$("#" + sms.addDlogFormId + " input[type='checkbox'][name='userGroupId'][value='"+groupId+"']").attr("checked", "checked");
									}
								}
							}
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
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog_1: function(operFlag, id, sendType) {
		$("#" + sms.addDlogOkId_1).css("display", "");
		$("#" + sms.addDlogFormId_1).form('reset');
		$("#" + sms.addDlogFormId_1 + " input[type='checkbox'][name='userScreenId']").removeAttr("checked");
		$("#" + sms.addDlogFormId_1 + " input[type='checkbox'][name='userGroupId']").removeAttr("checked");
		sms.removeAllTimeRow();
		if(operFlag == 0){
			sms.addTimeRow();
			$('#smsId_1').val("");
			$("#" + sms.addDlogId_1).dialog('open').dialog('setTitle', '定时发送短信');
			$('#sourceTypeTabs_1').tabs('select', '手动输入或上传手机号');
		}else{
			var url = BASE_PATH + "SmsController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"smsId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + sms.addDlogFormId_1).form('load', data);
						if(operFlag == 1){
							$("#" + sms.addDlogOkId_1).css("display", "");
							$("#" + sms.addDlogId_1).dialog('open').dialog('setTitle', '修改定时短信');
						}else if(operFlag == 2){
							$("#" + sms.addDlogOkId_1).css("display", "none");
							$("#" + sms.addDlogId_1).dialog('open').dialog('setTitle', '查看定时短信');
						}
						// 设置手机号页签tabs
						var sourceType = data.sourceType;
						if(sourceType == 'input'){
							$('#sourceTypeTabs_1').tabs('select', '手动输入或上传手机号');
						}else if(sourceType == 'userScreen'){
							$('#sourceTypeTabs_1').tabs('select', '用户筛选手机号');
							var screenIds = data.screenIds;
							if(null != screenIds && '' != screenIds){
								var screenIdAry = screenIds.split(",");
								for(var i=0; i<screenIdAry.length; i++){
									var screenId = screenIdAry[i];
									if('' != screenId){
										$("#" + sms.addDlogFormId_1 + " input[type='checkbox'][name='userScreenId'][value='"+screenId+"']").attr("checked", "checked");
									}
								}
							}
						}else if(sourceType == 'userGroup'){
							$('#sourceTypeTabs_1').tabs('select', '用户分组手机号');
							var groupIds = data.groupIds;
							if(null != groupIds && '' != groupIds){
								var groupIdAry = groupIds.split(",");
								for(var i=0; i<groupIdAry.length; i++){
									var groupId = groupIdAry[i];
									if('' != groupId){
										$("#" + sms.addDlogFormId_1 + " input[type='checkbox'][name='userGroupId'][value='"+groupId+"']").attr("checked", "checked");
									}
								}
							}
						}
						// 设置定时时间
						var sendType = data.sendType;
						var timingList = data.timingList;
						if(sendType == 'setTime' && null != timingList){
							for(var i=0; i<timingList.length; i++){
								var startDate = timingList[i].startDate;
								var endDate = timingList[i].endDate;
								var hour = timingList[i].hour;
								var minute = timingList[i].minute;
								sms.addTimeRow(startDate, endDate, hour, minute);
							}
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
	 * 新增短信提交
	 */
	save: function(){
		var tab = $('#sourceTypeTabs').tabs('getSelected');
		var index = $('#sourceTypeTabs').tabs('getTabIndex', tab);
		if (index == 0) {
			$("#phones").validatebox({
			    required: true
			});
		}else{
			$("#phones").validatebox({
			    required: false
			});
			$("#phones").val("");
			if(index == 1){
				$("#" + sms.addDlogFormId + " input[type='checkbox'][name='userGroupId']").removeAttr("checked");
				// 验证是否勾选用户刷选复选框
				var checkObj = $("#" + sms.addDlogFormId + " input[type='checkbox'][name='userScreenId'][checked='checked']");
				if(checkObj.size() <= 0){
					$.messager.alert('提示', "请先勾选[用户筛选条件]", 'info');
					return;
				}
			}else if(index == 2){
				$("#" + sms.addDlogFormId + " input[type='checkbox'][name='userScreenId']").removeAttr("checked");
				// 验证是否勾选用户分组复选框
				var checkObj = $("#" + sms.addDlogFormId + " input[type='checkbox'][name='userGroupId'][checked='checked']");
				if(checkObj.size() <= 0){
					$.messager.alert('提示', "请先勾选[用户分组条件]", 'info');
					return;
				}
			}
		}
		if(!common.submitFormValidate(sms.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SmsController/save",
			data : $("#" + sms.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + sms.addDlogId).dialog('close');
					common.reloadGrid(sms.dataGridId);
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 新增短信提交
	 */
	save_1: function(){
		var tab = $('#sourceTypeTabs_1').tabs('getSelected');
		var index = $('#sourceTypeTabs_1').tabs('getTabIndex', tab);
		if (index == 0) {
			$("#phones_1").validatebox({
			    required: true
			});
		}else{
			$("#phones_1").validatebox({
			    required: false
			});
			$("#phones_1").val("");
			if(index == 1){
				$("#" + sms.addDlogFormId_1 + " input[type='checkbox'][name='userGroupId']").removeAttr("checked");
				// 验证是否勾选用户刷选复选框
				var checkObj = $("#" + sms.addDlogFormId_1 + " input[type='checkbox'][name='userScreenId'][checked='checked']");
				if(checkObj.size() <= 0){
					$.messager.alert('提示', "请先勾选[用户筛选条件]", 'info');
					return;
				}
			}else if(index == 2){
				$("#" + sms.addDlogFormId_1 + " input[type='checkbox'][name='userScreenId']").removeAttr("checked");
				// 验证是否勾选用户分组复选框
				var checkObj = $("#" + sms.addDlogFormId_1 + " input[type='checkbox'][name='userGroupId'][checked='checked']");
				if(checkObj.size() <= 0){
					$.messager.alert('提示', "请先勾选[用户分组条件]", 'info');
					return;
				}
			}
		}
		if(!common.submitFormValidate(sms.addDlogFormId_1)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SmsController/save",
			data : $("#" + sms.addDlogFormId_1).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + sms.addDlogId_1).dialog('close');
					common.reloadGrid(sms.dataGridId);
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
	deleteById : function(smsIdArray, tip){
		var url = BASE_PATH + "SmsController/deleteById";
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
						"smsIdArray" : smsIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(sms.dataGridId);
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
		var checkeds = $("#" + sms.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].smsId;
			}else{
				ids += checkeds[i].smsId + ",";
			}
		}
		if (ids.length > 0) {
			sms.deleteById(ids, "确认删除选择的记录吗?");
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
		var queryParams = sms.getQueryParams();
		var url = BASE_PATH + "SmsController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 文件上传成功回调此函数
	 */
	callback: function(phones){
		$.messager.progress('close');
		$("#phones").val(phones);
	},
	/**
	 * 文件上传成功回调此函数
	 */
	callback_1: function(phones){
		$.messager.progress('close');
		$("#phones_1").val(phones);
	},
	addTimeRow : function (startDate, endDate, hour, minute){
		var length = $("#timeDivBox div").size();
		var startDateListInput = $("<input class=\"easyui-datebox\" type=\"text\" name=\"startDateList\" data-options=\"required:true, editable:false\" style=\"width: 100px;\"/>");
		var endDateListInput = $("<input class=\"easyui-datebox\" type=\"text\" name=\"endDateList\" data-options=\"required:true, editable:false\" style=\"width: 100px;\"/>");
		var hourListInput = $("<input class=\"easyui-numberspinner\" type=\"text\" name=\"hourList\" data-options=\"required:true, min:0, max:23\" style=\"width: 60px;\"/>");
		var minuteListInput = $("<input class=\"easyui-numberspinner\" type=\"text\" name=\"minuteList\" data-options=\"required:true, min:0, max:60\" style=\"width: 60px;\"/>");
		
		$("#timeDivBox").append("<div>");
		$("#timeDivBox div:eq("+length+")").append("日期：").append(startDateListInput);
		$("#timeDivBox div:eq("+length+")").append(" 至 ").append(endDateListInput);
		$("#timeDivBox div:eq("+length+")").append(" 小时：").append(hourListInput);
		$("#timeDivBox div:eq("+length+")").append(" 分钟：").append(minuteListInput);

		if(null != startDate && '' != startDate && startDate != 'undefined'){
			startDateListInput.datebox({
			}).datebox('setValue', startDate);
			endDateListInput.datebox({
			}).datebox('setValue', endDate);
			hourListInput.numberspinner({
			}).numberspinner('setValue', hour);
			minuteListInput.numberspinner({
			}).numberspinner('setValue', minute);
		}else{
			startDateListInput.datebox({
			});
			endDateListInput.datebox({
			});
			hourListInput.numberspinner({
			});
			minuteListInput.numberspinner({
			});
		}
		common.setEasyUiCss();
	},
	removeTimeRow : function (){
		var length = $("#timeDivBox div").size();
		if(length > 1){
			$("#timeDivBox div:eq("+(length-1)+")").remove();
		}
	},
	removeAllTimeRow : function (){
		var length = $("#timeDivBox div").size();
		for(var i=0; i<length; i++){
			$("#timeDivBox div:eq(0)").remove();
		}
	},
	/**
	 * 重发
	 */
	reSendSms : function(smsId){
		var url = BASE_PATH + "SmsController/reSendSms";
		$.messager.confirm('确认', "确认再次发送该批短信吗?", function(r) {
			if (r) {
				$.ajax({
					type : "post",
					dataType : "json",
					url : url,
					data : {
						"smsId" : smsId + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(sms.dataGridId);
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
	 * 立即对失败重发
	 */
	reSendSmsByFailPhone : function(smsId){
		var url = BASE_PATH + "SmsController/reSendSmsByFailPhone";
		$.messager.confirm('确认', "确认对发送失败的手机号再次发送短信吗?", function(r) {
			if (r) {
				$.ajax({
					type : "post",
					dataType : "json",
					url : url,
					data : {
						"smsId" : smsId + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(sms.dataGridId);
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
	 * 短信详情
	 */
	viewDetail : function(id){
		var url = "SmsDetailController/page?smsId=" + id;
		parent.index.addOrUpdateTabs("短信发送详情", url);
	}
	
}




















