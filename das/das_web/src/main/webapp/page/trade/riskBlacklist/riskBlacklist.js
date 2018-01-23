$(function() {
	riskBlacklist.init();
});

var riskBlacklist = {
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
	
	dataGridId_1: "dataGrid_1",
	searchFormId_1: "searchForm_1",
	
	/**
	 * 初始化
	 */
	init:function(){
		riskBlacklist.loadDataGrid_1();
		riskBlacklist.loadDataGrid();
		$("#content").xheditor({
			upImgUrl : BASE_PATH + "UploadController/uploadImg/email/callback_2",
			upImgExt : "jpg,jpeg,gif,png"
		});
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'RiskBlacklistController/pageList';
		var queryParams = {
		};
		queryParams['startDate'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endTimeSearch").datetimebox('getValue');
		var columns = [ [
	 			{field : 'oper', title : '操作', sortable : false, width : 70, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.riskBlacklistId);
					    });
						return $("#rowOperation").html();
				}},
		 	 	{field : 'companyId', title : '事业部', sortable : true, width : 50, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var returnString = "";
						if(!common.isBlank(value)){
							if(value == '0'){
								returnString += "集团";
							}else if(value == '1'){
								returnString += "外汇";
							}else if(value == '2'){
								returnString += "贵金属";
							}else if(value == '3'){
								returnString += "恒信";
							}else if(value == '4'){
								returnString += "创富";
							}
						}
						return returnString;
				}},
	 			{field : 'riskType', title : '风险类型', sortable : true, width : 70},
	 			{field : 'riskReason', title : '风险原因', sortable : true, width : 100},
	 			{field : 'riskRemark', title : '风险备注', sortable : true, width : 100},
	 			{field : 'riskTime', title : '风险时间', sortable : true, width : 75},
	 			{field : 'platform', title : '平台', sortable : true, width : 50},
	 			{field : 'accountNo', title : '账号', sortable : true, width : 70},
	 			{field : 'mobile', title : '电话', sortable : true, width : 100},
	 			{field : 'email', title : '邮箱', sortable : true, width : 100},
	 			{field : 'idCard', title : '证件号码', sortable : true, width : 120},
	 			{field : 'ip', title : 'IP', sortable : true, width : 100},
	 			{field : 'deviceType', title : '设备类型', sortable : true, width : 100},
	 			{field : 'deviceInfo', title : '设备信息', sortable : true, width : 100},
	 			{field : 'updateDate', title : '更新时间', sortable : true, width : 70}
 			] ];
		$('#' + riskBlacklist.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'riskBlacklistId', // 唯一字段
			sortName : 'riskTime',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + riskBlacklist.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startDate'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endTimeSearch").datetimebox('getValue');
		queryParams['riskType'] = $("#riskTypeSearch").combobox('getValue');// 风险类型
		queryParams['riskReason'] = $("#riskReasonSearch").val();// 风险原因
		queryParams['riskRemark'] = $("#riskRemarkSearch").val();// 风险备注
		queryParams['idCard'] = $("#idCardSearch").val();// 证件号码
		queryParams['accountNo'] = $("#accountNoSearch").val();// 账户
		queryParams['mobile'] = $("#mobileSearch").val();// 手机号码
		queryParams['email'] = $("#emailSearch").val();// 邮箱
		queryParams['ip'] = $("#ipSearch").val();// IP
		queryParams['deviceInfo'] = $("#deviceInfoSearch").val();// 设备信息
		queryParams['companyId'] = $("#companyIdSearch").combobox('getValue');// 业务权限
		queryParams['platform'] = $("#platformSearch").combobox('getValue');
		queryParams['deviceType'] = $("#deviceTypeSearch").combobox('getValue');
		return queryParams;
	},
	/**
	 * 加载loadDataGrid_1
	 */
	loadDataGrid_1 : function(){
		// 初始参数
		var url = BASE_PATH + 'RiskBlacklistController/findDimBlackList';
		var queryParams = {
		};
		queryParams['startMarkTime'] = $("#startTimeSearch_1").datebox('getValue');
		queryParams['endMarkTime'] = $("#endTimeSearch_1").datebox('getValue');
		var columns = [ [
	 			{field : 'type', title : '类型', sortable : true, width : 70},
	 			{field : 'value', title : '类型值', sortable : true, width : 70},
	 			{field : 'riskType', title : '风险类型', sortable : true, width : 70},
	 			{field : 'createTime', title : '创建时间', sortable : true, width : 100},
	 			{field : 'updateTime', title : '更新时间', sortable : true, width : 100},
	 			{field : 'markTime', title : '标记时间', sortable : true, width : 100},
	 			{field : 'source', title : '来源标记', sortable : true, width : 70, 
	 				formatter : function(value, rowData, rowIndex) {
	 					var returnString = "";
						if(!common.isBlank(value)){
							if(value == '1'){
								returnString = "自动";
							}else if(value == '2'){
								returnString = "手工";
							}else if(value == '3'){
								returnString = "自动&手工";
							}
						}
						return returnString;
				}},
	 			{field : 'remark', title : '备注', sortable : true, width : 120},
	 			{field : 'remarkEn', title : '备注英文', sortable : true, width : 120},
 			] ];
		$('#' + riskBlacklist.dataGridId_1).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			columns : columns,
			idField : 'rowKey', // 唯一字段
			sortName : 'markTime',
			sortOrder : 'desc',
			onDblClickRow : function(rowIndex, rowData) {
			}
		});
	},
	getQueryParams_1: function() {
		var options = $("#" + riskBlacklist.dataGridId_1).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startMarkTime'] = $("#startTimeSearch_1").datebox('getValue');
		queryParams['endMarkTime'] = $("#endTimeSearch_1").datebox('getValue');
		queryParams['type'] = $("#typeSearch").combobox('getValue');// 类型
		queryParams['value'] = $("#valueSearch").val();// 类型值
		queryParams['riskType'] = $("#riskTypeSearch_1").combobox('getValue');// 风险类型
		queryParams['source'] = $("#sourceSearch").combobox('getValue');// 来源标记
		queryParams['remark'] = $("#remarkSearch").val();// 备注
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		riskBlacklist.getQueryParams();
		common.loadGrid(riskBlacklist.dataGridId);
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + riskBlacklist.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + riskBlacklist.addDlogOkId).css("display", "");
		$("#" + riskBlacklist.addDlogFormId).form('reset');
		var url = BASE_PATH + "RiskBlacklistController/findById";
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data : {
				"riskBlacklistId" : id
			},
			success : function(data) {
				if (null != data) {
					$("#" + riskBlacklist.addDlogFormId).form('load', data);
					if(operFlag == 1){
						$("#" + riskBlacklist.addDlogOkId).css("display", "");
						$("#" + riskBlacklist.addDlogId).dialog('open').dialog('setTitle', '修改风险要素');
					}else if(operFlag == 2){
						$("#" + riskBlacklist.addDlogOkId).css("display", "none");
						$("#" + riskBlacklist.addDlogId).dialog('open').dialog('setTitle', '修改风险要素');
					}
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 新增黑名单提交
	 */
	save: function(){
		if(!common.submitFormValidate(riskBlacklist.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "RiskBlacklistController/save",
			data : $("#" + riskBlacklist.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + riskBlacklist.addDlogId).dialog('close');
					common.reloadGrid(riskBlacklist.dataGridId);
				}
			},
			error: function(data){
				common.error();
	        }
		});
	},
	/**
	 * 条件查询
	 */
	find_1: function(){
		riskBlacklist.getQueryParams_1();
		common.loadGrid(riskBlacklist.dataGridId_1);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + riskBlacklist.searchFormId).form('reset');
		$('#startTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		$('#endTimeSearch').datetimebox({
			formatter:easyui.formatterYYYYMMDDHHMMSS,
			parser:easyui.parserYYYYMMDDHHMMSS
		});
		common.setEasyUiCss();
	},
	/**
	 * reset条件查询
	 */
	reset_1: function(){
		$('#' + riskBlacklist.searchFormId_1).form('reset');
		$('#startTimeSearch_1').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch_1').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		common.setEasyUiCss();
	},
	/**
	 * 导出excel
	 */
	exportExcel : function(){
		if(!common.exportExcelValidate(this.dataGridId)){
			return;
		}
		var queryParams = riskBlacklist.getQueryParams();
		var url = BASE_PATH + "RiskBlacklistController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 导出excel
	 */
	exportExcel_1 : function(){
		if(!common.exportExcelValidate(this.dataGridId_1)){
			return;
		}
		var queryParams = riskBlacklist.getQueryParams_1();
		var url = BASE_PATH + "RiskBlacklistController/dimBlackListExportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	},
	/**
	 * 导入Excel数据
	 */
	addDlog_1: function() {
		$("#" + riskBlacklist.addDlogOkId_1).css("display", "");
		$("#" + riskBlacklist.addDlogFormId_1).form('reset');
		$("#" + riskBlacklist.addDlogId_1).dialog('open').dialog('setTitle', '导入数据');
	},
	/**
	 * close新增窗口
	 */
	cancel_1: function(){
		$('#' + riskBlacklist.addDlogId_1).dialog('close');
	},
	/**
	 * 导入Excel数据-提交
	 */
	submitExcel: function() {
		upload.uploadExcelFile(BASE_PATH + 'UploadController/riskBlacklistUploadExcel/riskBlacklist/callback', 'uploadDiv', '1', '上传Excel文件');
	},
	/**
	 * 导入Excel数据回调此函数
	 */
	callback: function(msg){
		$.messager.progress('close');
		$("#msg").val(msg);
		common.reloadGrid(riskBlacklist.dataGridId);
	}
	
}




















