$(function() {
	tradeIndex.init();
});

var tradeIndex = {
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
		tradeIndex.loadDataGrid();
	},
	/**
	 * 加载dataGrid
	 */
	loadDataGrid : function(){
		// 初始参数
		var url = BASE_PATH + 'TradeIndexController/pageList';
		var queryParams = {
		};
		queryParams['startDate'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endTimeSearch").datetimebox('getValue');
		var columns = [ [
	 			{field : 'goldHighPrice', title : '最高金价', sortable : true, width : 70},
	 			{field : 'goldLowPrice', title : '最低金价', sortable : true, width : 70},
	 			{field : 'goldAmp', title : '金价波幅', sortable : true, width : 70},
	 			{field : 'goldUpAndDown', title : '金价升跌', sortable : true, width : 70},
	 			{field : 'silverHighPrice', title : '最高银价', sortable : true, width : 70},
	 			{field : 'silverLowPrice', title : '最低银价', sortable : true, width : 70},
	 			{field : 'silverAmp', title : '银价波幅', sortable : true, width : 70},
	 			{field : 'silverUpAndDown', title : '银价升跌', sortable : true, width : 70},
	 			{field : 'advisoryCustomerCount', title : '当日客户对话量', sortable : true, width : 110},
	 			{field : 'advisoryCount', title : '当日对话总数', sortable : true, width : 100},
	 			{field : 'customerPhoneCount', title : '取得客户电话数量', sortable : true, width : 120},
	 			{field : 'siteVisitsCount', title : '当日网站总访问量', sortable : true, width : 115},
	 			{field : 'channelPromotionCosts', title : '当日渠道推广费用', sortable : true, width : 110},
	 			{field : 'handOperHedgeProfitAndLossGts2', title : '手动对冲盈亏Gts2', sortable : true, width : 120},
	 			{field : 'handOperHedgeProfitAndLossMt4', title : '手动对冲盈亏Mt4', sortable : true, width : 120},
	 			{field : 'handOperHedgeProfitAndLossGts', title : '手动对冲盈亏Gts', sortable : true, width : 120},
	 			{field : 'handOperHedgeProfitAndLossMt5', title : '手动对冲盈亏Mt5', sortable : true, width : 120},
	 			{field : 'goldNetPositionsVolume', title : '黄金净仓手数', sortable : true, width : 100},
	 			{field : 'silverNetPositionsVolume', title : '白银净仓手数', sortable : true, width : 100},
	 			{field : 'bonusOtherGts2', title : '活动推广赠金(其它)GTS2', sortable : true, width : 140},
	 			{field : 'bonusOtherMt4', title : '活动推广赠金(其它)MT4', sortable : true, width : 140},
	 			{field : 'bonusOtherGts', title : '活动推广赠金(其它)GTS', sortable : true, width : 140},
	 			{field : 'bonusOtherMt5', title : '活动推广赠金(其它)MT5', sortable : true, width : 140}
 			] ];
		$('#' + tradeIndex.dataGridId).datagrid(easyui.defaultOption).datagrid({
			url : url,
			queryParams : queryParams,
			frozenColumns:[[
			    {field : 'oper', title : '操作', sortable : false, width : 120, 
	 				formatter : function(value, rowData, rowIndex) {
						$("#rowOperation a").each(function(){
							$(this).attr("id", rowData.tradeIndexId);
					    });
						return $("#rowOperation").html();
				}},
			    {field : 'dateTime', title : '日期', sortable : true, width : 80}
			]],
			columns : columns,
			idField : 'tradeIndexId', // 唯一字段
			sortName : 'dateTime',
			sortOrder : 'desc',
			collapsible: true,
    		fitColumns:false,
			onDblClickRow : function(rowIndex, rowData) {
				tradeIndex.addDlog("2", rowData.tradeIndexId);
			},
			onLoadSuccess : function(data) {
				if(COMPANY_ID == '2'){//贵金属
					$("#" + tradeIndex.dataGridId).datagrid('hideColumn', 'handOperHedgeProfitAndLossGts2');
					$("#" + tradeIndex.dataGridId).datagrid('hideColumn', 'bonusOtherGts2');
					$("#" + tradeIndex.dataGridId).datagrid('showColumn', 'handOperHedgeProfitAndLossGts');
					$("#" + tradeIndex.dataGridId).datagrid('showColumn', 'bonusOtherGts');
					$("#" + tradeIndex.dataGridId).datagrid('showColumn', 'handOperHedgeProfitAndLossMt5');
					$("#" + tradeIndex.dataGridId).datagrid('showColumn', 'bonusOtherMt5');
				}else{
					$("#" + tradeIndex.dataGridId).datagrid('showColumn', 'handOperHedgeProfitAndLossGts2');
					$("#" + tradeIndex.dataGridId).datagrid('showColumn', 'bonusOtherGts2');
					$("#" + tradeIndex.dataGridId).datagrid('hideColumn', 'handOperHedgeProfitAndLossGts');
					$("#" + tradeIndex.dataGridId).datagrid('hideColumn', 'bonusOtherGts');
					$("#" + tradeIndex.dataGridId).datagrid('hideColumn', 'handOperHedgeProfitAndLossMt5');
					$("#" + tradeIndex.dataGridId).datagrid('hideColumn', 'bonusOtherMt5');
				}
				common.iFrameHeight();
			}
		});
	},
	getQueryParams: function() {
		var options = $("#" + tradeIndex.dataGridId).datagrid('options');
		var queryParams = options.queryParams;
		queryParams['sort'] = options.sortName;
		queryParams['order'] = options.sortOrder;
		queryParams['startDate'] = $("#startTimeSearch").datetimebox('getValue');
		queryParams['endDate'] = $("#endTimeSearch").datetimebox('getValue');
		return queryParams;
	},
	/**
	 * 条件查询
	 */
	find: function(){
		tradeIndex.getQueryParams();
		common.loadGrid(tradeIndex.dataGridId);
	},
	/**
	 * reset条件查询
	 */
	reset: function(){
		$('#' + tradeIndex.searchFormId).form('reset');
		$('#startTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		$('#endTimeSearch').datebox({
			formatter:easyui.formatterYYYYMMDD,
			parser:easyui.parserYYYYMMDD
		});
		common.setEasyUiCss();
	},
	/**
	 * close新增窗口
	 */
	cancel: function(){
		$('#' + tradeIndex.addDlogId).dialog('close');
	},
	/**
	 * 新增或修改或查看
	 * operFlag 0新增，1修改，2查看
	 * id 修改或查看实体的id
	 */
	addDlog: function(operFlag, id) {
		$("#" + tradeIndex.addDlogOkId).css("display", "");
		$("#" + tradeIndex.addDlogFormId).form('reset');
		if(operFlag == 0){
			$('#tradeIndexId').val("");
			$("#" + tradeIndex.addDlogId).dialog('open').dialog('setTitle', '新增');
		}else{
			var url = BASE_PATH + "TradeIndexController/findById";
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : {
					"tradeIndexId" : id
				},
				success : function(data) {
					if (null != data) {
						$("#" + tradeIndex.addDlogFormId).form('load', data);
						if(operFlag == 1){
							$("#" + tradeIndex.addDlogOkId).css("display", "");
							$("#" + tradeIndex.addDlogId).dialog('open').dialog('setTitle', '修改');
						}else if(operFlag == 2){
							$("#" + tradeIndex.addDlogOkId).css("display", "none");
							$("#" + tradeIndex.addDlogId).dialog('open').dialog('setTitle', '查看');
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
	 * 新增提交
	 */
	save: function(){
		if(!common.submitFormValidate(tradeIndex.addDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "TradeIndexController/save",
			data : $("#" + tradeIndex.addDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
					$("#" + tradeIndex.addDlogId).dialog('close');
					common.reloadGrid(tradeIndex.dataGridId);
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
	deleteById : function(tradeIndexIdArray, tip){
		var url = BASE_PATH + "TradeIndexController/deleteById";
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
						"tradeIndexIdArray" : tradeIndexIdArray + ""
					},
					success : function(data) {
						if(common.isSuccess(data)){
							common.reloadGrid(tradeIndex.dataGridId);
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
		var checkeds = $("#" + tradeIndex.dataGridId).datagrid('getChecked');
		var ids = "";
		for ( var i = 0; i < checkeds.length; i++) {
			if(i == checkeds.length-1){
				ids += checkeds[i].tradeIndexId;
			}else{
				ids += checkeds[i].tradeIndexId + ",";
			}
		}
		if (ids.length > 0) {
			tradeIndex.deleteById(ids, "确认删除选择的记录吗?");
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
		var queryParams = tradeIndex.getQueryParams();
		var url = BASE_PATH + "TradeIndexController/exportExcel?";
		url = common.formatUrl(url, queryParams);
		window.location.href = url;
	}
	
}




















