<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/room/behavior/behavior.js?version=20170306"></script>
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
			<div>
				<table class="commonTable">
					<tr>
					    <th>行为时间</th>
						 <td >
							<input class="easyui-datetimebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="${startTime}"/>
							至
							<input class="easyui-datetimebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="${endTime}"/>
						 </td>
						<th class="more">行为类型</th>
						<td class="more">
							<select id="behaviorTypeSearch" name="behaviorTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${behaviorTypeEnum}" defaultVal="${behaviorType}"/>
							</select>
						</td>
						<th class="more">访问客户端</th>
					    <td class="more">
						   <select id="devicetypeSearch" name="devicetypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							  <option value="">---请选择---</option>
							  <das:enumListTag defaultVal="${devicetype}" dataList="${devicetypeEnumEnum}"/>
						   </select>
					    </td>
					</tr>
					<tr class="more">
						<th>用户</th>
						<td><input type="text" id="userIdSearch" name="userIdSearch" maxlength="50"/></td>
						<th>组(utmcct)</th>
						<td><input type="text" id="utmcctSearch" name="utmcctSearch" maxlength="50" value="${utmcct}"/></td>
						<th>系列(utmccn)</th>
						<td><input type="text" id="utmccnSearch" name="utmccnSearch" maxlength="50" value="${utmccn}"/></td>
					</tr>
					<tr class="more">
					    <th style="width: 110px;">媒介(utmcmd)</th>
						<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50" value="${utmcmd}"/></td>
						<th style="width: 110px;">关键字(utmctr)</th>
						<td><input type="text" id="utmctrSearch" name="utmctrSearch" maxlength="50" value="${utmctr}"/></td>
						<th>IP</th>
						<td><input type="text" id="ipSearch" name="ipSearch" maxlength="50"/></td>
					</tr>
					<tr class="more">
					    <th>来源(utmcsr)</th>
						<td colspan="7"><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50" value="${utmcsr}"/></td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="behavior.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="behavior.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="behavior.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
