<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/trade/dimAccountBlackList/dimAccountBlackList.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<table class="commonTable">
				<tr>
				    <th>行为日期</th>
					<td>
						<input class="easyui-datetimebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value=""/>
						至
						<input class="easyui-datetimebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value=""/>
					</td>
					<th>业务权限</th>
				    <td>
					   <select id="companyIdSearch" name="companyIdSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
						  <option value="">---请选择---</option>
						  <das:enumListTag dataList="${companyEnum}"/>
					   </select>
				    </td>
					<th>平台</th>
				    <td>
					   <select id="platformSearch" name="platformSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
						  <option value="">---请选择---</option>
						  <das:enumListTag dataList="${platformtypeEnum}"/>
					   </select>
				    </td>
				</tr>
				<tr>
				    <th>标记日期</th>
					<td>
						<input class="easyui-datebox" type="text" name="startMarkTimeSearch" id="startMarkTimeSearch" data-options="editable:false" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endMarkTimeSearch" id="endMarkTimeSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
					</td>
				    <th>账户</th>
					<td><input type="text" id="accountNoSearch" name="accountNoSearch" maxlength="50" /></td>
				    <th>手机号码</th>
					<td><input type="text" id="mobileSearch" name="mobileSearch" maxlength="20" /></td>
				</tr>
				<tr>
				    <th>客户姓名</th>
					<td><input type="text" id="accountNameCnSearch" name="accountNameCnSearch" maxlength="50" /></td>
				    <th>身份证号</th>
					<td><input type="text" id="idCardSearch" name="idCardSearch" maxlength="50" /></td>
				    <th>IP</th>
					<td colspan="3"><input type="text" id="createIpSearch" name="createIpSearch" maxlength="50" /></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dimAccountBlackList.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dimAccountBlackList.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dimAccountBlackList.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; height:auto; min-height: 120px;"></table>
	</div>
</div>

</body>
</html>
