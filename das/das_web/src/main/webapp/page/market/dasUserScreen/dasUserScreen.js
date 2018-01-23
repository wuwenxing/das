$(function() {
	dasUserScreen.init();
});

var dasUserScreen = {
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
		dasUserScreen.loadDataGrid();

		$("select[multiple='multiple']").multiselect({
			noneSelectedText: "---请选择---", 
			selectedList: 10 // 0-based index
		}).multiselectfilter();
		$(".ui-multiselect-menu").css("width", "217px");
		
		$("#startSilenceDays").numberspinner({
			width:70
		});
		$("#silenceDays").numberspinner({
			width:70
		});
		$("#visitCountGt").numberspinner({
			width:70
		});
		$("#visitCountLt").numberspinner({
			width:70
		});
		$("#advisoryCountGt").numberspinner({
			width:70
		});
		$("#advisoryCountLt").numberspinner({
			width:70
		});
		$("#smsSendCountGt").numberspinner({
			width:70
		});
		$("#smsSendCountLt").numberspinner({
			width:70
		});
		common.setEasyUiCss();
		
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'DasUserScreenController/pageList';
		var queryParams = {
			
		};
		var columns = [ [
			    {field : 'screeenId', checkbox : true},
	 			{field : 'oper', title : '操作', sortable : false, width : 150, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.screenId);
					    });
						return $("#rowOperation").html();
				}},
	 			{field : 'screenName', title : '筛选名称', sortable : true, width : 100},
	 			{field : 'behaviorType', title : '行为类型', sortable : true, width : 100},
	 			{field : 'planTimeType', title : '计划时间', sortable : true, width : 100},
	 			{field : 'startTime', title : '筛选开始时间', sortable : true, width : 100},
	 			{field : 'endTime', title : '筛选结束时间', sortable : true, width : 100},
	 			{field : 'resultSmsCount', title : '筛选手机号数量', sortable : true, width : 100},
	 			{field : 'resultEmailCount', title : '筛选邮箱数量', sortable : true, width : 100},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 100}
 			] ];
		$('#' + dasUserScreen.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'screenId', // 唯一字段
			sortName : 'updateDate',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
				dasUserScreen.addDlog("2", rowData.screenId);
			}
		});
	},
	getQueryParams: function() {
		var queryParams = $("#" + dasUserScreen.dataGridId).datagrid('options').queryParams;
		queryParams['screenName'] = $("#screenNameSearch").val();
		queryParams['behaviorType'] = $("#behaviorTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		dasUserScreen.getQueryParams();
		common.loadGrid(dasUserScreen.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + dasUserScreen.searchFormId).form('reset');
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + dasUserScreen.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + dasUserScreen.addDlogOkId).css("display", "");
		$("#" + dasUserScreen.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#screenId').val("");
			$("#isDemo").val("N");
			$("#isDemo").attr("checked", false);
			$("#isReal").val("N");
			$("#isReal").attr("checked", false);
			$("#isDepesit").val("N");
			$("#isDepesit").attr("checked", false);
			$("#isBacklist").val("N");
			$("#isBacklist").attr("checked", false);
			$("#isFreeAngry").val("N");
			$("#isFreeAngry").attr("checked", false);
			$("#" + dasUserScreen.addDlogId).dialog('open').dialog('setTitle', '新增筛选条件');
		}else{
			var url = BASE_PATH + "DasUserScreenController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"screenId" : id
				},
				success : function(data) {
					if (null != data) {
						// 调用change事件，改变下拉框，重新赋值
						var behaviorType = data.behaviorType;
						dasUserScreen.getOrSetSilenceList(behaviorType, '', data.silence);
						
						$("#" + dasUserScreen.addDlogFormId).form('load', data);
						
						// multiselect复选框勾选
						var channelIds = ',' + data.channelIds + ',';// 添加分隔符号，好indexOf进行比较
						$('#channelIds option').each(function(){
							if(channelIds.indexOf(',' + this.value + ',')!=-1)this.selected=true;
						});
						var tagIds = ',' + data.tagIds + ',';// 添加分隔符号，好indexOf进行比较
						$('#tagIds option').each(function(){
							if(tagIds.indexOf(',' + this.value + ',')!=-1)this.selected=true;
						});
						var areas = ',' + data.areas + ',';// 添加分隔符号，好indexOf进行比较
						$('#areas option').each(function(){
							if(areas.indexOf(',' + this.value + ',')!=-1)this.selected=true;
						});
						var browsers = ',' + data.browsers + ',';// 添加分隔符号，好indexOf进行比较
						$('#browsers option').each(function(){
							if(browsers.indexOf(',' + this.value + ',')!=-1)this.selected=true;
						});
						$("select[multiple='multiple']").multiselect("refresh");
						$(".ui-multiselect-menu").css("width", "217px");
						
						// 排重复选框勾选
						if(data.isDemo == 'Y'){
							$("#isDemo").val("Y");
							$("#isDemo").attr("checked", true);
						}
						if(data.isReal == 'Y'){
							$("#isReal").val("Y");
							$("#isReal").attr("checked", true);
						}
						if(data.isDepesit == 'Y'){
							$("#isDepesit").val("Y");
							$("#isDepesit").attr("checked", true);
						}
						if(data.isBacklist == 'Y'){
							$("#isBacklist").val("Y");
							$("#isBacklist").attr("checked", true);
						}
						if(data.isFreeAngry == 'Y'){
							$("#isFreeAngry").val("Y");
							$("#isFreeAngry").attr("checked", true);
						}
						// 设置统计个数
						if(null != data.resultSmsCount){
							$("#resultSmsCountText").text('满足条件的手机号个数：' + data.resultSmsCount);
						}else{
							$("#resultSmsCountText").text('满足条件的手机号个数：0');
						}
						if(null != data.resultEmailCount){
							$("#resultEmailCountText").text('邮箱个数：' + data.resultEmailCount);
						}else{
							$("#resultEmailCountText").text('邮箱个数：0');
						}
						
						if(operFlag == 1){
							$("#" + dasUserScreen.addDlogOkId).css("display", "");
							$("#" + dasUserScreen.addDlogId).dialog('open').dialog('setTitle', '修改筛选条件');
						}else if(operFlag == 2){
							$("#" + dasUserScreen.addDlogOkId).css("display", "none");
							$("#" + dasUserScreen.addDlogId).dialog('open').dialog('setTitle', '查看筛选条件');
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
	 * 新增筛选条件提交
	 */
	save: function(){
		if(!common.submitFormValidate(dasUserScreen.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "DasUserScreenController/save",
			data : $("#" + dasUserScreen.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + dasUserScreen.addDlogId).dialog('close');
					common.reloadGrid(dasUserScreen.dataGridId);
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
	deleteById : function(screenIdArray, tip){
		var url = BASE_PATH + "DasUserScreenController/deleteById";
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
						"screenIdArray" : screenIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(dasUserScreen.dataGridId);
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
		var checkeds = $("#" + dasUserScreen.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].screenId;
			}else{
				ids += checkeds[i].screenId + ",";
			}
		}
		if (ids.length > 0) {
			dasUserScreen.deleteById(ids, "确认删除选择的记录吗?");
		} else {
			$.messager.alert('提示', "请选择要删除的记录", 'info');
		}
	},
	/**
	 * 统计数量
	 */
	showResultCount: function(){
		var url = BASE_PATH + "DasUserScreenController/resultCount";
		$.ajax({
			type : "post",
			dataType : "json",
			url: url,
			data : $("#" + dasUserScreen.addDlogFormId).serialize(),
			success : function(data) {
				if (null != data) {
					$("#resultSmsCountText").text('满足条件的手机号个数：' + data.resultSmsCount);
					$("#resultEmailCountText").text('邮箱个数：' + data.resultEmailCount);
				}
			},
			error: function(data){
				
	        }
		});
	},
	/**
	 * combobox onChange事件，获取下拉列表
	 */
	getSilenceList: function(newValue, oldValue){
		var url = BASE_PATH + "DasUserScreenController/getSilenceList";
		$.ajax({
			type : "post",
			dataType : "json",
			url: url,
			data : {"type": newValue},
			success : function(data) {
				$('#silence').combobox('clear');
				if (null != data) {
					$('#silence').combobox('loadData', data);
				}
			},
			error: function(data){
				
	        }
		});
	},
	/**
	 * combobox onChange事件，获取下拉列表
	 */
	getOrSetSilenceList: function(newValue, oldValue, selectValue){
		var url = BASE_PATH + "DasUserScreenController/getSilenceList";
		$.ajax({
			type : "post",
			dataType : "json",
			url: url,
			data : {"type": newValue},
			success : function(data) {
				$('#silence').combobox('clear');
				if (null != data) {
					$('#silence').combobox('loadData', data);
					setTimeout("dasUserScreen.selectValue('"+selectValue+"')", 200);
				}
			},
			error: function(data){
				
	        }
		});
	},
	/**
	 * combobox选择指定列表项
	 */
	selectValue: function(selectValue){
		$('#silence').combobox('select', selectValue);
	}
	
}

