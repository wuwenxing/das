<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/report/dasFlowStatisticsTime.js?version=20170306"></script>
<script type="text/javascript" src="<%=basePath%>page/report/dasFlowStatisticsTimeChart.js?version=20170306"></script>
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
							<input class="easyui-datetimebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false, formatter:easyui.formatterYYYYMMDDHH" value="${startTime}"/>
							至
							<input class="easyui-datetimebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false, formatter:easyui.formatterYYYYMMDDHH" value="${endTime}"/>
						</td>
						<th class="more">来源(utmcsr)</th>
						<td class="more"><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50"/></td>
					</tr>	
					<tr class="more">
						<th style="width: 110px;">媒介(utmcmd)</th>
						<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50"/></td>
						<th>访问客户端</th>
						<td>
							<select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${clientEnum}"/>
							</select>
						</td>
						
					</tr>
					<tr>
						<td colspan="4">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasFlowStatisticsTime.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasFlowStatisticsTime.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasFlowStatisticsTime.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<div class="common-box-style">
	<div class="divTitle">访问</div>
	<div class="divContent">
		<div class="visitChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="visitChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>

<div class="common-box-style">
	<div class="divTitle">咨询</div>
	<div class="divContent">
		<div class="advisoryChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="advisoryChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>

<div class="common-box-style">
	<div class="divTitle">开户入金</div>
	<div class="divContent">
		<div class="accountChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="accountChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>

</body>
</html>
