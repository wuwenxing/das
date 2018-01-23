<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoomStatisticsSourceCount/dasChartRoomStatisticsSourceCount.js?version=20170712"></script>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoomStatisticsSourceCount/dasChartRoomStatisticsSourceCountChart.js?version=20170712"></script>
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
		    <input type="hidden" id="channelInput" name="channelInput" value="${channel}">
		    <input type="hidden" id="devicetypeInput" name="devicetypeInput" value="${devicetype}">
			<div>
				<table class="commonTable">
					<tr>
					    <th>日期</th>
						<td>
							<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="${startTime}"/>
							至
							<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="${endTime}"/>
						</td>
						<th class="more">渠道</th>
						<td class="more">
							<select id="channelIds" name="channelIds" multiple="multiple" >
								<c:forEach var="channel" items="${channelList}">
									<option value="${channel}">${channel}</option>
								</c:forEach>
							</select>
						</td>
						<th class="more">访问客户端</th>
						<td class="more">
							<select id="devicetypeSearch" name="devicetypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag defaultVal="${devicetype}" dataList="${devicetypeEnumEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
					    <th>环比日期</th>
						<td>
							<input class="easyui-datebox" type="text" name="startTimeCompareSearch" id="startTimeCompareSearch" data-options="editable:false" value="${startTimeCompare}"/>
							至
							<input class="easyui-datebox" type="text" name="endTimeCompareSearch" id="endTimeCompareSearch" data-options="editable:false" value="${endTimeCompare}"/>
						</td>
						<th class="more" style="width: 110px;"><input type="checkbox" id="utmcmdChecked" name="utmcmdChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>媒介(utmcmd)</th>
						<td class="more"><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50" value="${utmcmd}"/></td>
						<th class="more"><input type="checkbox" id="utmcsrChecked" name="utmcsrChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>来源(utmcsr)</th>
						<td class="more"><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50" value="${utmcsr}"/></td>
					</tr>
					<tr class="more">
						<th><input type="checkbox" id="utmccnChecked" name="utmccnChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>系列(utmccn)</th>
						<td>
							<input type="text" id="utmccnSearch" name="utmccnSearch" maxlength="50" value=""/>
						</td>
						<th><input type="checkbox" id="utmcctChecked" name="utmcctChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>组(utmcct)</th>
						<td>
							<input type="text" id="utmcctSearch" name="utmcctSearch" maxlength="50" value=""/>
						</td>
					    <th style="width: 110px;"><input type="checkbox" id="utmctrChecked" name="utmctrChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>关键字(utmctr)</th>
						<td>
							<input type="text" id="utmctrSearch" name="utmctrSearch" maxlength="50" value=""/>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsSourceCount.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsSourceCount.reset();"
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
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsSourceCount.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divContent" id="dataGridDiv">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
	
	<div class="divContent" id="dataTreeGridDiv">
		<table id="dataTreeGrid" data-options="" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<div class="common-box-style">
	<div class="divTitle">开户入金</div>
	<div class="divContent">
		<div class="accountChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="accountChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>

<div class="common-box-style">
	<div class="divTitle">设备次数</div>
	<div class="divContent">
		<div class="devicecountChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="devicecountChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>

</body>
</html>
