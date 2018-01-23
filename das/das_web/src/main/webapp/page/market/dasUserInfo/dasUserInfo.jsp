<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/dasUserInfo/dasUserInfo.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>行为账号</th>
						<td><input type="text" id="behaviorDetailSearch" name="behaviorDetailSearch" maxlength="50"/></td>
						<th>行为类型</th>
						<td>
							<select id="behaviorTypeSearch" name="behaviorTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${behaviorTypeEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
						<th>来源(utmcsr)</th>
						<td><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50"/></td>
						<th style="width: 110px;">媒介(utmcmd)</th>
						<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50"/></td>
					</tr>
					<tr>
						<th>行为时间</th>
						<td width="400px;">
							<input class="easyui-datetimebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd HH:mm:ss"/>"/>
							至
							<input class="easyui-datetimebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="monthEnd" format="yyyy-MM-dd HH:mm:ss"/>"/>
						</td>
						<th>排重</th>
						<td>
							<span>模拟<input type="checkbox" name="isDemoSearch" id="isDemoSearch" value="N" onclick="if(checked==true) {value = 'Y'; checked='checked';} else {value = 'N'; checked='';}"/></span>
							<span>真实<input type="checkbox" name="isRealSearch" id="isRealSearch" value="N" onclick="if(checked==true) {value = 'Y'; checked='checked';} else {value = 'N'; checked='';}"/></span>
							<span>入金<input type="checkbox" name="isDepesitSearch" id="isDepesitSearch" value="N" onclick="if(checked==true) {value = 'Y'; checked='checked';} else {value = 'N'; checked='';}"/></span>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasUserInfo.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasUserInfo.reset();"
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
