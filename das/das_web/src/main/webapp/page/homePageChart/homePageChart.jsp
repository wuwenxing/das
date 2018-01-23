<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/homePageChart/homePageChart.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>行为时间</th>
						<td>
							<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="${startTime}"/>
							至
							<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="${endTime}"/>
						</td>
						<th>来源(utmcsr)</th>
						<td><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50" value="${utmcsr}"/></td>
						<th style="width: 110px;">媒介(utmcmd)</th>
						<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50" value="${utmcmd}"/></td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="homePageChart.findChartData();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="homePageChart.reset();"
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
	<div class="divTitle">首次入金</div>
	<div class="divContent">
		<div class="depositChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="depositChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>
<div class="common-box-style">
	<div class="divTitle">真实开户</div>
	<div class="divContent">
		<div class="realChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="realChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>
<div class="common-box-style">
	<div class="divTitle">模拟开户</div>
	<div class="divContent">
		<div class="demoChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="demoChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
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
	<div class="divTitle">访问</div>
	<div class="divContent">
		<div class="visitChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="visitChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>

</body>
</html>
