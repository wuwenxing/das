<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/behavior/dasFlowDetailUrl.js?version=20170306"></script>
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
				<input type="hidden" id="flowDetailIdSearch" name="flowDetailIdSearch" value="${flowDetailId}"/>
				<table class="commonTable">
					<tr>
					    <th>行为时间</th>
						<td>
							<input class="easyui-datetimebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="${formatTime}" />
							至
							<input class="easyui-datetimebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="${endTime}"/>
						</td>
						<th class="more">访问客户端</th>
					    <td class="more">
						   <select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							  <option value="">---请选择---</option>
							  <das:enumListTag defaultVal="${platformType}" dataList="${clientEnum}"/>
						   </select>
					    </td>
						<th class="more">来源(utmcsr)</th>
						<td class="more"><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50" value="${utmcsr}"/></td>
						
					</tr>
					<tr class="more">
					    <th style="width: 110px;">媒介(utmcmd)</th>
						<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50" value="${utmcmd}"/></td>
						<th>访问URL</th>
						<td colspan="3">
							<input type="text" id="flowDetailUrlSearch" name="flowDetailUrlSearch" maxlength="500" style="width: 400px;"/>
						</td>
						
					</tr>
					<tr>
						<td colspan="6">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasFlowDetailUrl.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasFlowDetailUrl.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasFlowDetailUrl.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
