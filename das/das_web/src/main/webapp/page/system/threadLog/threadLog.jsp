<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/system/threadLog/threadLog.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>线程名称</th>
						<td>
							<select id="codeSearch" name="codeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${systemThreadEnum}"/>
							</select>
						</td>
						<th>执行状态</th>
						<td>
							<select id="statusSearch" name="statusSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<option value="Y">成功</option>
								<option value="N">失败</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>执行开始时间</th>
						<td colspan="3">
							<input class="easyui-datetimebox" type="text" name="startDateSearch" id="startDateSearch" data-options="editable:false" value="<das:date type="dayStart" format="yyyy-MM-dd HH:mm:ss"/>" />
							至
							<input class="easyui-datetimebox" type="text" name="endDateSearch" id="endDateSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd HH:mm:ss"/>" />
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="threadLog.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="threadLog.reset();"
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
