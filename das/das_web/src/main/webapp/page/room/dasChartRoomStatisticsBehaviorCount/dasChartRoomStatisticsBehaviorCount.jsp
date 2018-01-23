<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoomStatisticsBehaviorCount/dasChartRoomStatisticsBehaviorCount.js?version=20170712"></script>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoomStatisticsBehaviorCount/dasChartRoomStatisticsBehaviorCountChart.js?version=20170712"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>日期</th>
						<td >
							<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="<das:date type="lessOneMonths" format="yyyy-MM-dd"/>"/>
							至
							<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
						</td>
						<th>访问客户端</th>
						<td colspan="5">
							<select id="devicetypeSearch" name="devicetypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${devicetypeEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsBehaviorCount.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsBehaviorCount.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomStatisticsBehaviorCount.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
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
