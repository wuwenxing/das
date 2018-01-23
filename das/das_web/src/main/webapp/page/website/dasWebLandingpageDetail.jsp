<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/website/dasWebLandingpageDetail.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<table class="commonTable">
				<tr>
				    <th>日期</th>
					<td>
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
					</td>
					<th><input type="checkbox" id="deviceTypeChecked" name="deviceTypeChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>设备类型</th>
				    <td>
					   <select id="deviceTypeSearch" name="deviceTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
						  <option value="">---请选择---</option>
						   <option value="ALL">ALL</option>
						  <das:enumListTag dataList="${deviceTypeEnum}"/>
					   </select>
				    </td>
				    <th>LandingPage</th>
					<td><input type="text" id="landingPageSearch" name="landingPageSearch" maxlength="200" /></td>
				</tr>
				<tr>
				    <th><input type="checkbox" id="channelChecked" name="channelChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>渠道</th>
					<td><input type="text" id="channelSearch" name="channelSearch" maxlength="50" /></td>
				    <th><input type="checkbox" id="utmcsrChecked" name="utmcsrChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>来源(utmcsr)</th>
					<td><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50" /></td>
				    <th style="width: 110px;"><input type="checkbox" id="utmcmdChecked" name="utmcmdChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>媒介(utmcmd)</th>
					<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50" /></td>
				</tr>
				<tr>
					<th><input type="checkbox" id="utmccnChecked" name="utmccnChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>系列(utmccn)</th>
					<td>
						<input type="text" id="utmccnSearch" name="utmccnSearch" maxlength="50"/>
					</td>
					<th><input type="checkbox" id="utmcctChecked" name="utmcctChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>组(utmcct)</th>
					<td>
						<input type="text" id="utmcctSearch" name="utmcctSearch" maxlength="50"/>
					</td>
				    <th style="width: 110px;"><input type="checkbox" id="utmctrChecked" name="utmctrChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>关键字(utmctr)</th>
					<td>
						<input type="text" id="utmctrSearch" name="utmctrSearch" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasWebLandingpageDetail.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasWebLandingpageDetail.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasWebLandingpageDetail.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; height:auto; min-height: 138px;"></table>
	</div>
</div>

</body>
</html>
