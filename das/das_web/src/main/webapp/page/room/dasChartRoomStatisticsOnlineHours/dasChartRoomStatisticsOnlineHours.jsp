<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoomStatisticsOnlineHours/dasChartRoomStatisticsOnlineHours.js?version=20170306"></script>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoomStatisticsOnlineHours/dasChartRoomStatisticsOnlineHoursChart.js?version=20170306"></script>
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
						<th>日期</th>
						<td>
							<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="lessOneYears" format="yyyy-MM-dd"/>"/>
							至
							<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
						</td>
						<th class="more">用户级别</th>
						<td class="more">
							<select id="userTypeSearch" name="userTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${roomUserTypeEnum}"/>
							</select>
						</td>
						<th class="more">访问客户端</th>
						<td class="more">
							<select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${clientEnum}"/>
							</select>
						</td>
					</tr>
					<tr class="more">
						<th><input type="checkbox" id="courseNameChecked" name=courseNameChecked value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>课程名称</th>
						<td><input type="text" id="courseNameSearch" name="courseNameSearch" maxlength="50"/></td>
						<th><input type="checkbox" id="teacherNameChecked" name=teacherNameChecked value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>老师名称</th>
						<td><input type="text" id="teacherNameSearch" name="teacherNameSearch" maxlength="50"/></td>
						<th><input type="checkbox" id="roomNameChecked" name=roomNameChecked value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>房间名称</th>
						<td><input type="text" id="roomNameSearch" name="roomNameSearch" maxlength="50"/></td>
					</tr>
					<tr class="more">
						<th>用户来源</th>
						<td colspan="5"><input type="text" id="userSourceSearch" name="userSourceSearch" maxlength="50"/></td>
					</tr>
					<tr>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsOnlineHours.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsOnlineHours.reset();"
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
	<div class="divTitle">今日在线时长</div>
	<div class="divContent">
		<div class="chartLoadingDiv_1" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="chartContainer_2" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>

<div class="common-box-style">
	<div class="divTitle">往日在线时长</div>
	<div class="divContent">
		<div id="chartContainer_1" style="min-width: 600px; min-height: 400px; margin: 0 auto; padding-top: 20px;"></div>
	</div>
</div>

<div class="common-box-style">
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsOnlineHours.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
