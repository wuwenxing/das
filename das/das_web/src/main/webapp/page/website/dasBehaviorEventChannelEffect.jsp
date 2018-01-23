<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/website/dasBehaviorEventChannelEffect.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">
		查询条件
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="common.showSearchCon();"
			data-options="iconCls:'icon-searchCon', plain:'true'">更多条件</a>
	</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<input type="hidden" id="yesterdayStart" name="yesterdayStart" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>">
			<input type="hidden" id="dayEnd" name="dayEnd" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>">
			<input type="hidden" id="halfaWeek" name="halfaWeek" value="<das:date type="halfaWeek" format="yyyy-MM-dd"/>">
			<input type="hidden" id="lessOneMonths" name="lessOneMonths" value="<das:date type="lessOneMonths" format="yyyy-MM"/>">
			<input type="hidden" id="monthEnd" name="monthEnd" value="<das:date type="monthEnd" format="yyyy-MM"/>">
			<table class="commonTable">
				<tr>
					<th>统计周期</th>
					<td>
						<select id="reportTypeSearch" name="reportTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false, onChange: function(newValue,oldValue){dasBehaviorEventChannelEffect.setDateboxAttr(newValue,oldValue)}">
							<das:enumListTag dataList="${reportTypeEnum}"/>
						</select>
					</td>
					<th>日期</th>
					<td colspan="3">
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr class="more">
					<th><input type="checkbox" id="deviceTypeChecked" name="deviceTypeChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>设备类型</th>
				    <td>
					   <select id="deviceTypeSearch" name="deviceTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
						  <option value="">---请选择---</option>
						  <option value="1">ANDROID</option>
						  <option value="2">IOS</option>
						  <option value="3">PCUI</option>
						  <option value="5">APP</option>
					   </select>
				    </td>
				    <th>LandingPage</th>
					<td colspan="3"><input type="text" id="landingPageSearch" name="landingPageSearch" maxlength="200" style="width: 330px;"/></td>
				</tr>
				<tr class="more">
				    <th><input type="checkbox" id="platformVersionChecked" name="platformVersionChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>版本号</th>
					<td><input type="text" id="platformVersionSearch" name="platformVersionSearch" maxlength="50" /></td>
				    <th><input type="checkbox" id="platformNameChecked" name="platformNameChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>马甲包</th>
					<td colspan="3"><input type="text" id="platformNameSearch" name="platformNameSearch" maxlength="50" /></td>
				</tr>
				<tr class="more">
				    <th><input type="checkbox" id="channelGrpChecked" name="channelGrpChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>渠道分组</th>
					<td><input type="text" id="channelGrpSearch" name="channelGrpSearch" maxlength="50" /></td>
				    <th><input type="checkbox" id="channelLevelChecked" name="channelLevelChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>渠道分级</th>
					<td colspan="3"><input type="text" id="channelLevelSearch" name="channelLevelSearch" maxlength="50" /></td>
				</tr>
				<tr class="more">
				    <th><input type="checkbox" id="channelChecked" name="channelChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>渠道</th>
					<td><input type="text" id="channelSearch" name="channelSearch" maxlength="50" /></td>
				    <th><input type="checkbox" id="utmcsrChecked" name="utmcsrChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>来源(utmcsr)</th>
					<td><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50" /></td>
				    <th style="width: 110px;"><input type="checkbox" id="utmcmdChecked" name="utmcmdChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>媒介(utmcmd)</th>
					<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50" /></td>
				</tr>
				<tr class="more">
					<th><input type="checkbox" id="utmccnChecked" name="utmccnChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>系列(utmccn)</th>
					<td>
						<input type="text" id="utmccnSearch" name="utmccnSearch" maxlength="50"/>
					</td>
					<th><input type="checkbox" id="utmcctChecked" name="utmcctChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>组(utmcct)</th>
					<td>
						<input type="text" id="utmcctSearch" name="utmcctSearch" maxlength="50"/>
					</td>
				    <th style="width: 110px;"><input type="checkbox" id="utmctrChecked" name="utmctrChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>关键字(utmctr)</th>
					<td>
						<input type="text" id="utmctrSearch" name="utmctrSearch" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasBehaviorEventChannelEffect.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasBehaviorEventChannelEffect.reset();"
								data-options="iconCls:'icon-empty', plain:'true'">重置</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div class="common-box-style">
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasBehaviorEventChannelEffect.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; height:auto; min-height: 138px;"></table>
	</div>
</div>

</body>
</html>
