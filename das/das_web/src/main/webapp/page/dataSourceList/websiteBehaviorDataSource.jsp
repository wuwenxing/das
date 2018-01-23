<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/dataSourceList/websiteBehaviorDataSource.js?version=20170306"></script>
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
					    <th>访问时间</th>
						 <td >
							<input class="easyui-datetimebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="${startTime}"/>
							至
							<input class="easyui-datetimebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="${endTime}"/>
						 </td>
						<th class="more">UUID</th>
						<td class="more"><input type="text" id="userIdSearch" name="userIdSearch" maxlength="50"/></td>
						<th class="more">会话标识</th>
						<td class="more"><input type="text" id="sessionIdSearch" name="sessionIdSearch" maxlength="50"/></td>
					</tr>
					<tr class="more">
						<th>终端类型</th>
						<td class="more">
						   <select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							  <option value="">---请选择---</option>
							  <das:enumListTag defaultVal="${platformType}" dataList="${clientEnum}"/>
						   </select>
					    </td>
						<th>终端标识</th>
						<td><input type="text" id="deviceIdSearch" name="deviceIdSearch" maxlength="50" /></td>
						<th>来源(站外utmcsr)</th>
						<td><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50" /></td>
					</tr>
					<tr class="more">
					    <th style="width: 135px;">媒介(站外utmcmd)</th>
						<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50" /></td>
						<th style="width: 135px;">系列(站外utmccn)</th>
						<td><input type="text" id="utmccnSearch" name="utmccnSearch" maxlength="50" /></td>
						<th style="width: 135px;">组(站外utmcct)</th>
						<td><input type="text" id="utmcctSearch" name="utmcctSearch" maxlength="50"/></td>
					</tr>
					<tr class="more">
					    <th style="width: 135px;">关键字(站外utmctr)</th>
						<td><input type="text" id="utmctrSearch" name="utmctrSearch" maxlength="50" /></td>
						<th style="width: 135px;">来源(站内utmcsr2)</th>
						<td><input type="text" id="utmcsr2Search" name="utmcsr2Search" maxlength="50" /></td>
						<th style="width: 135px;">媒介(站内utmcmd2)</th>
						<td><input type="text" id="utmcmd2Search" name="utmcmd2Search" maxlength="50" /></td>
					</tr>
					<tr class="more">
					    <th>系列(站内utmccn2)</th>
						<td><input type="text" id="utmccn2Search" name="utmccn2Search" maxlength="50" /></td>
						<th>组(站内utmcct2)</th>
						<td><input type="text" id="utmcct2Search" name="utmcct2Search" maxlength="50" /></td>
						<th>关键字(站内utmctr2)</th>
						<td><input type="text" id="utmctr2Search" name="utmctr2Search" maxlength="50" /></td>
					</tr>
					<tr class="more">
					    <th>行为明细</th>
						<td><input type="text" id="behaviorDetailSearch" name="behaviorDetailSearch" maxlength="50" /></td>
						<th class="more">行为类型</th>
						<td class="more">
							<select id="behaviorTypeSearch" name="behaviorTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${behaviorTypeEnum}" defaultVal="${behaviorType}"/>
							</select>
						</td>
						<th>事件类别</th>
						<td><input type="text" id="eventCategorySearch" name="eventCategorySearch" maxlength="50" /></td>
					</tr>
					<tr class="more">
					    <th>事件操作</th>
						<td><input type="text" id="eventActionSearch" name="eventActionSearch" maxlength="50" /></td>
						<th>浏览器信息</th>
						<td><input type="text" id="browserSearch" name="browserSearch" maxlength="50" /></td>
						<th>上一次URL地址</th>
						<td><input type="text" id="prevUrlSearch" name="prevUrlSearch" maxlength="250" /></td>
					</tr>
					<tr class="more">
					    <th>访问URL</th>
						<td><input type="text" id="urlSearch" name="urlSearch" maxlength="250" /></td>
						<th>终端IP</th>
						<td colspan="3"><input type="text" id="ipSearch" name="ipSearch" maxlength="50" /></td>						
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="websiteBehaviorDataSource.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="websiteBehaviorDataSource.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="websiteBehaviorDataSource.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
