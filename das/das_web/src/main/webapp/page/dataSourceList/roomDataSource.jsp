<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/dataSourceList/roomDataSource.js?version=20170306"></script>
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
					    <th style="width: 135px;">系列(站内utmccn2)</th>
						<td><input type="text" id="utmccn2Search" name="utmccn2Search" maxlength="50" /></td>
						<th style="width: 135px;">组(站内utmcct2)</th>
						<td><input type="text" id="utmcct2Search" name="utmcct2Search" maxlength="50" /></td>
						<th style="width: 135px;">关键字(站内utmctr2)</th>
						<td><input type="text" id="utmctr2Search" name="utmctr2Search" maxlength="50" /></td>
					</tr>
					<tr class="more">
					    <th>电话号码</th>
						<td><input type="text" id="userTelSearch" name="userTelSearch" maxlength="50" /></td>
						<th class="more">用户类型</th>
						<td class="more">
							<select id="userTypeSearch" name="userTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${userTypeDataEnum}" defaultVal="${userType}"/>
							</select>
						</td>
						<th>用户名称</th>
						<td><input type="text" id="userNameSearch" name="userNameSearch" maxlength="50" /></td>
					</tr>
					<tr class="more">
					    <th>房间名称</th>
						<td><input type="text" id="roomNameSearch" name="roomNameSearch" maxlength="50" /></td>
						<th>来源(utmcsr)</th>
						<td><input type="text" id="userSourceSearch" name="userSourceSearch" maxlength="50" /></td>
						<th>使用设备</th>
						<td><input type="text" id="useEquipmentSearch" name="useEquipmentSearch" maxlength="50" /></td>
					</tr>
					<tr class="more">
					    <th>交易帐号</th>
						<td><input type="text" id="tradingAccountSearch" name="tradingAccountSearch" maxlength="50" /></td>
						<th>交易平台</th>
						<td><input type="text" id="tradingPlatformSearch" name="tradingPlatformSearch" maxlength="50" /></td>
						<th>用户入口</th>
						<td><input type="text" id="operateEntranceSearch" name="operateEntranceSearch" maxlength="50" /></td>
					</tr>
					<tr class="more">
					    <th>操作类型</th>
						<td class="more">
							<select id="operationTypeSearch" name="operationTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${operationDataTypeEnum}" defaultVal="${operationType}"/>
							</select>
						</td>
						<th>访客ID</th>
						<td><input type="text" id="touristIdSearch" name="touristIdSearch" maxlength="50" /></td>
						<th>用户昵称</th>
						<td><input type="text" id="nickNameSearch" name="nickNameSearch" maxlength="50" /></td>
					</tr>
					<tr class="more">
						<th>用户邮箱</th>
						<td><input type="text" id="emailSearch" name="emailSearch" maxlength="50" /></td>
						<th>教学视频名称</th>
						<td><input type="text" id="videoNameSearch" name="videoNameSearch" maxlength="50" /></td>
						<th>课程名称</th>
						<td><input type="text" id="courseNameSearch" name="courseNameSearch" maxlength="50" /></td>
					</tr>
					<tr class="more">
						<th>老师名称</th>
						<td><input type="text" id="teacherNameSearch" name="teacherNameSearch" maxlength="50" /></td>
						<th>事件类别</th>
						<td><input type="text" id="eventCategorySearch" name="eventCategorySearch" maxlength="50" /></td>
						 <th>事件操作</th>
						<td><input type="text" id="eventActionSearch" name="eventActionSearch" maxlength="50" /></td>
					</tr>
					<tr class="more">
						<th>访问IP</th>
						<td colspan="5"><input type="text" id="userIpSearch" name="userIpSearch" maxlength="50" /></td>						
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="roomDataSource.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="roomDataSource.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="roomDataSource.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
