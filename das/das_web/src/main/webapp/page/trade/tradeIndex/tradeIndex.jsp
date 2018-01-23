<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/trade/tradeIndex/tradeIndex.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>日期</th>
						<td>
							<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd"/>"/>
							至
							<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tradeIndex.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tradeIndex.reset();"
									data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>

<div class="common-box-style">
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tradeIndex.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
<!-- 		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tradeIndex.exportExcel();" -->
<!-- 			data-options="iconCls:'icon-export', plain:'true'">导出</a> -->
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 135px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="tradeIndex.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="tradeIndex.addDlog(1, this.id)">修改</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="tradeIndex.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="tradeIndex.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="tradeIndexId" id="tradeIndexId">
		<table class="commonTable">
			<tr>
				<th>日期<span class="spanRed">*</span></th>
				<td colspan="3"><input class="easyui-datebox" type="text" name="dateTime" id="dateTime" data-options="required:true, editable:false" value=""/></td>
			</tr>
			<tr>
				<td colspan="4" style="font-weight: bold;">》交易指标</td>
			</tr>
			<tr>
				<th>最高金价</th>
				<td><input class="easyui-numberspinner" type="text" name="goldHighPrice" id="goldHighPrice" data-options="required:false, precision:2" value="0"/></td>
				<th>最高银价</th>
				<td><input class="easyui-numberspinner" type="text" name="silverHighPrice" id="silverHighPrice" data-options="required:false, precision:2" value="0"/></td>
			</tr>
			<tr>
				<th>最低金价</th>
				<td><input class="easyui-numberspinner" type="text" name="goldLowPrice" id="goldLowPrice" data-options="required:false, precision:2" value="0"/></td>
				<th>最低银价</th>
				<td><input class="easyui-numberspinner" type="text" name="silverLowPrice" id="silverLowPrice" data-options="required:false, precision:2" value="0"/></td>
			</tr>
			<tr>
				<th>金价波幅</th>
				<td><input class="easyui-numberspinner" type="text" name="goldAmp" id="goldAmp" data-options="required:false, precision:2" value="0"/></td>
				<th>银价波幅</th>
				<td><input class="easyui-numberspinner" type="text" name="silverAmp" id="silverAmp" data-options="required:false, precision:2" value="0"/></td>
			</tr>
			<tr>
				<th>金价升跌</th>
				<td>
					<select id="goldUpAndDown" name="goldUpAndDown" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
						<option value="升">升</option>
						<option value="跌">跌</option>
					</select>
				</td>
				<th>银价升跌</th>
				<td>
					<select id="silverUpAndDown" name="silverUpAndDown" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
						<option value="升">升</option>
						<option value="跌">跌</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td colspan="4" style="font-weight: bold;">》客户状况</td>
			</tr>
			<tr>
				<th>当日客户对话量</th>
				<td><input class="easyui-numberspinner" type="text" name="advisoryCustomerCount" id="advisoryCustomerCount" data-options="required:false" value="0"/></td>
				<th>当日对话总数</th>
				<td><input class="easyui-numberspinner" type="text" name="advisoryCount" id="advisoryCount" data-options="required:false" value="0"/></td>
			</tr>
			<tr>
				<th>取得客户电话数量</th>
				<td colspan="3"><input class="easyui-numberspinner" type="text" name="customerPhoneCount" id="customerPhoneCount" data-options="required:false" value="0"/></td>
			</tr>
			
			<tr>
				<td colspan="4" style="font-weight: bold;">》市场状况</td>
			</tr>
			<tr>
				<th>当日网站总访问量</th>
				<td><input class="easyui-numberspinner" type="text" name="siteVisitsCount" id="siteVisitsCount" data-options="required:false" value="0"/></td>
				<th>当日渠道推广费用</th>
				<td><input class="easyui-numberspinner" type="text" name="channelPromotionCosts" id="channelPromotionCosts" data-options="required:false, precision:2" value="0"/></td>
			</tr>
			<tr>
				<td colspan="4" style="font-weight: bold;">》交易记录总结</td>
			</tr>
			<c:if test="${companyId == 2}">
				<tr>
					<th>手动对冲盈亏GTS</th>
					<td><input class="easyui-numberspinner" type="text" name="handOperHedgeProfitAndLossGts" id="handOperHedgeProfitAndLossGts" data-options="required:false, precision:2" value="0"/></td>
					<th>手动对冲盈亏MT4</th>
					<td><input class="easyui-numberspinner" type="text" name="handOperHedgeProfitAndLossMt4" id="handOperHedgeProfitAndLossMt4" data-options="required:false, precision:2" value="0"/></td>
				</tr>
				<tr>
					<th>手动对冲盈亏MT5</th>
					<td colspan="3"><input class="easyui-numberspinner" type="text" name="handOperHedgeProfitAndLossMt5" id="handOperHedgeProfitAndLossMt5" data-options="required:false, precision:2" value="0"/></td>
				</tr>
			</c:if>
			<c:if test="${companyId != 2}">
				<tr>
					<th>手动对冲盈亏GTS2</th>
					<td><input class="easyui-numberspinner" type="text" name="handOperHedgeProfitAndLossGts2" id="handOperHedgeProfitAndLossGts2" data-options="required:false, precision:2" value="0"/></td>
					<th>手动对冲盈亏MT4</th>
					<td><input class="easyui-numberspinner" type="text" name="handOperHedgeProfitAndLossMt4" id="handOperHedgeProfitAndLossMt4" data-options="required:false, precision:2" value="0"/></td>
				</tr>
			</c:if>
			<tr>
				<td colspan="4" style="font-weight: bold;">》净仓</td>
			</tr>
			<tr>
				<th>黄金净仓手数</th>
				<td><input class="easyui-numberspinner" type="text" name="goldNetPositionsVolume" id="goldNetPositionsVolume" data-options="required:false, precision:2" value="0"/></td>
				<th>白银净仓手数</th>
				<td><input class="easyui-numberspinner" type="text" name="silverNetPositionsVolume" id="silverNetPositionsVolume" data-options="required:false, precision:2" value="0"/></td>
			</tr>
			<tr>
				<td colspan="4" style="font-weight: bold;">》其它</td>
			</tr>
			<c:if test="${companyId == 2}">
				<tr>
					<th style="width: 140px;">活动推广赠金(其它)GTS</th>
					<td><input class="easyui-numberspinner" type="text" name="bonusOtherGts" id="bonusOtherGts" data-options="required:false, precision:2" value="0"/></td>
					<th style="width: 140px;">活动推广赠金(其它)MT4</th>
					<td><input class="easyui-numberspinner" type="text" name="bonusOtherMt4" id="bonusOtherMt4" data-options="required:false, precision:2" value="0"/></td>
				</tr>
				<tr>
					<th style="width: 140px;">活动推广赠金(其它)MT5</th>
					<td colspan="3"><input class="easyui-numberspinner" type="text" name="bonusOtherMt5" id="bonusOtherMt5" data-options="required:false, precision:2" value="0"/></td>
				</tr>
			</c:if>
			<c:if test="${companyId != 2}">
				<tr>
					<th style="width: 140px;">活动推广赠金(其它)GTS2</th>
					<td><input class="easyui-numberspinner" type="text" name="bonusOtherGts2" id="bonusOtherGts2" data-options="required:false, precision:2" value="0"/></td>
					<th style="width: 140px;">活动推广赠金(其它)MT4</th>
					<td><input class="easyui-numberspinner" type="text" name="bonusOtherMt4" id="bonusOtherMt4" data-options="required:false, precision:2" value="0"/></td>
				</tr>
			</c:if>
		</table>
	</form>
</div>

</body>
</html>
