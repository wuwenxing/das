<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/room/loginStatistics/loginStatistics.js?version=20170306"></script>
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
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
					</td>
				    <th>交易帐号</th>
					<td><input type="text" id="accountSearch" name="accountSearch" maxlength="50" /></td>
					<th>用户类型</th>
				    <td>
					   <select id="userTypeSearch" name="userTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
						  <option value="">---请选择---</option>
						  <das:enumListTag dataList="${roomUserTypeEnum}"/>
					   </select>
				    </td>
				</tr>
				<tr>
				    <th>开户时间</th>
					<td>
						<input class="easyui-datebox" type="text" name="openAccountTimeStartSearch" id="openAccountTimeStartSearch" data-options="editable:false" value=""/>
						至
						<input class="easyui-datebox" type="text" name="openAccountTimeEndSearch" id="openAccountTimeEndSearch" data-options="editable:false" value=""/>
					</td>
				    <th>激活时间</th>
					<td colspan="3">
						<input class="easyui-datebox" type="text" name="activeTimeStartSearch" id="activeTimeStartSearch" data-options="editable:false" value=""/>
						至
						<input class="easyui-datebox" type="text" name="activeTimeEndSearch" id="activeTimeEndSearch" data-options="editable:false" value=""/>
					</td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="loginStatistics.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="loginStatistics.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="loginStatistics.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; height:auto; min-height: 120px;"></table>
	</div>
</div>

</body>
</html>
