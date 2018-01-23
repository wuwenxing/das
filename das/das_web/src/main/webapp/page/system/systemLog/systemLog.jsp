<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/system/systemLog/systemLog.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>操作时间</th>
						<td>
							<input class="easyui-datetimebox" type="text" name="startDateSearch" id="startDateSearch" data-options="editable:false" value="<das:date type="yesterdayStart" format="yyyy-MM-dd HH:mm:ss"/>" />
							至
							<input class="easyui-datetimebox" type="text" name="endDateSearch" id="endDateSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd HH:mm:ss"/>" />
						</td>
						<th>操作人</th>
						<td>
							<input type="text" id="updateUserSearch" name="updateUserSearch" maxlength="50"/>
						</td>
						<th>操作IP</th>
						<td>
							<input type="text" id="updateIpSearch" name="updateIpSearch" maxlength="50"/>
						</td>
					</tr>
					<tr>
						<th>业务类型</th>
						<td>
							<select id="companyIdSearch" name="companyIdSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${companyEnum}"/>
							</select>
						</td>
						<th>请求地址</th>
						<td colspan="3">
							<input type="text" id="urlSearch" name="urlSearch" maxlength="500" style="width: 300px;"/>
						</td>
					</tr>
					<tr>
						<th>系统模块</th>
						<td>
							<select id="logTypeSearch" name="logTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${systemLogEnum}"/>
							</select>
						</td>
						<th>请求参数</th>
						<td colspan="3">
							<input type="text" id="paramSearch" name="paramSearch" maxlength="500" style="width: 300px;"/>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="systemLog.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="systemLog.reset();"
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
