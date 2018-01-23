<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoom/dasChartRoomVisitorDetail.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<input type="hidden" id="touristId" name="touristId" value="${touristId}" />
				<table class="commonTable">
					<tr>
						<th>用户标示</th>
						<td><input type="text" id="rowKey" name="rowKey" maxlength="50" value="${rowKey}"/></td>
						<th>昵称</th>
						<td><input type="text" id="nickNameSearch" name="nickNameSearch" maxlength="50"/></td>
						<th>用户级别</th>
						<td>
							<select id="userTypeSearch" name="userTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${roomUserTypeEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
						<th>手机号码</th>
						<td><input type="text" id="userTelSearch" name="userTelSearch" maxlength="50" value="${userTel}"/></td>
						<th>房间名称</th>
						<td><input type="text" id="roomNameSearch" name="roomNameSearch" maxlength="50"/></td>
						<th>访问客户端</th>
						<td>
							<select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${clientEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
						<th>用户来源</th>
						<td><input type="text" id="userSourceSearch" name="userSourceSearch" maxlength="50"/></td>
						<th>在线时长</th>
						<td colspan="3">
							<input class="easyui-timespinner" type="text" name="timeLengthStartSearch" id="timeLengthStartSearch" data-options="showSeconds:true" />
							至
							<input class="easyui-timespinner" type="text" name="timeLengthEndSearch" id="timeLengthEndSearch" data-options="showSeconds:true" />
						</td>
					</tr>
					<tr>
						<th>上线时间</th>
							<td>
								<input class="easyui-datetimebox" type="text" name="upStartTimeSearch" id="upStartTimeSearch" data-options="required:false, editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd HH:mm:ss"/>"/>
								至
								<input class="easyui-datetimebox" type="text" name="upEndTimeSearch" id="upEndTimeSearch" data-options="required:false, editable:false" value="<das:date type="monthEnd" format="yyyy-MM-dd HH:mm:ss"/>"/>
							</td>
						<th>下线时间</th>
						<td colspan="3">
							<input class="easyui-datetimebox" type="text" name="downStartTimeSearch" id="downStartTimeSearch" data-options="required:false, editable:false" />
							至
							<input class="easyui-datetimebox" type="text" name="downEndTimeSearch" id="downEndTimeSearch" data-options="required:false, editable:false" />
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomVisitorDetail.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomVisitorDetail.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomVisitorDetail.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
