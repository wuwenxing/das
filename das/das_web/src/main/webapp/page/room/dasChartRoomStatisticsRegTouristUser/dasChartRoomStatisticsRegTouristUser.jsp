<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoomStatisticsRegTouristUser/dasChartRoomStatisticsRegTouristUser.js?version=20170306"></script>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoomStatisticsRegTouristUser/dasChartRoomStatisticsRegTouristUserChart.js?version=20170306"></script>
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
				<input type="hidden" id="lessOneMonths" name="lessOneMonths" value="<das:date type="lessOneMonths" format="yyyy-MM-dd"/>">
				<input type="hidden" id="dayEnd" name="dayEnd" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>">
				<input type="hidden" id="lessThreeMonths" name="lessThreeMonths" value="<das:date type="lessThreeMonths" format="yyyy-MM"/>">
				<input type="hidden" id="monthEnd" name="monthEnd" value="<das:date type="monthEnd" format="yyyy-MM"/>">
				<input type="hidden" id="lessOneYears" name="lessOneYears" value="<das:date type="lessOneYears" format="yyyy-MM"/>">
				<table class="commonTable">
					<tr>
						<th>统计周期</th>
						<td>
							<select id="reportTypeSearch" name="reportTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false, onChange: function(newValue,oldValue){dasChartRoomStatisticsRegTouristUser.setDateboxAttr(newValue,oldValue)}">
								<das:enumListTag dataList="${reportTypeEnum}"/>
							</select>
						</td>
						<th>日期</th>
						<td colspan="3">
							<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="lessOneMonths" format="yyyy-MM-dd"/>"/>
							至
							<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
						</td>
					</tr>
					<tr class="more">
						<th><input type="checkbox" id="roomNameChecked" name="roomNameChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>房间名称</th>
						<td><input type="text" id="roomNameSearch" name="roomNameSearch" maxlength="50"/></td>
						<th><input type="checkbox" id="courseNameChecked" name="courseNameChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>课程名称</th>
						<td><input type="text" id="courseNameSearch" name="courseNameSearch" maxlength="50"/></td>
						<th><input type="checkbox" id="teacherNameChecked" name="teacherNameChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>老师名称</th>
						<td><input type="text" id="teacherNameSearch" name="teacherNameSearch" maxlength="50"/></td>
					</tr>
					<tr class="more">
						<th>访问客户端</th>
						<td colspan="5">
							<select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${clientEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsRegTouristUser.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsRegTouristUser.reset();"
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
	<div class="divTitle">注册用户</div>
	<div class="divContent">
		<div class="statisticsRegTouristUserChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="statisticsRegTouristUserChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>
	
<div class="common-box-style">
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsRegTouristUser.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
