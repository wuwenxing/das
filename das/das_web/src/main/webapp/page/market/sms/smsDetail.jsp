<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/sms/smsDetail.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>短信ID</th>
						<td><input type="text" id="smsIdSearch" name="smsIdSearch" value="${smsId}" maxlength="50"/></td>
						<th>提交状态</th>
						<td>
							<select id="commitStatusSearch" name="commitStatusSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<c:forEach var="commitStatus" items="${commitStatus}">
									<option value="${commitStatus.labelKey}">${commitStatus.value}</option>
								</c:forEach>
							</select>
						</td>
						<th>发送状态</th>
						<td>
							<select id="sendStatusSearch" name="sendStatusSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<c:forEach var="sendStatus" items="${sendStatus}">
									<option value="${sendStatus.labelKey}">${sendStatus.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>手机号码</th>
						<td><input type="text" id="phoneSearch" name="phoneSearch" value="" maxlength="50"/></td>
						<th>发送时间</th>
						<td colspan="3">
							<input class="easyui-datetimebox" type="text" name="startDateSearch" id="startDateSearch" data-options="editable:false" value="${startDate}"/>
							至
							<input class="easyui-datetimebox" type="text" name="endDateSearch" id="endDateSearch" data-options="editable:false" value="${endDate}"/>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsDetail.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsDetail.reset();"
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
