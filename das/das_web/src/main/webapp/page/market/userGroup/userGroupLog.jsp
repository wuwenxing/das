<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/userGroup/userGroupLog.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<input type="hidden" id="type" value="${type}" />
			<div>
				<table class="commonTable">
					<tr>
						<th>分组ID</th>
						<td><input type="text" id="groupIdSearch" name="groupIdSearch" value="${groupId}" maxlength="50"/></td>
						<th>请求状态</th>
						<td>
							<select id="statusCodeSearch" name="statusCodeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<c:forEach var="statusCode" items="${statusCode}">
									<option value="${statusCode.labelKey}">${statusCode.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroupLog.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroupLog.reset();"
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
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="" style="width:100%; min-height: 91px; height:auto;"></table>
	</div>
</div>

</body>
</html>
