<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/trade/gts2/dealprofitdetail/dealprofitdetailStatistics.js?version=20170306"></script>
<%-- <script type="text/javascript" src="<%=basePath%>page/trade/gts2/dealprofitdetail/dealprofitdetailStatisticsChart.js?version=20170306"></script> --%>
</head>
<body>

<div class="common-box-style">
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<input type="hidden" id="lessOneMonths" name="lessOneMonths" value="<das:date type="lessOneMonths" format="yyyy-MM-dd"/>">
			<input type="hidden" id="dayEnd" name="dayEnd" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>">
			<input type="hidden" id="monthStart" name="monthStart" value="<das:date type="monthStart" format="yyyy-MM-dd"/>">
			<table class="commonTable">
				<tr>					
					<th>日期</th>
					<td>
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
					</td>
					<th>平台类型</th>
					<td>
						<select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<das:enumListTag dataList="${platformtypeEnum}"/>
						</select>
					</td>
				</tr>								
				<tr>
					<td colspan="4">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dealprofitdetailStatistics.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dealprofitdetailStatistics.reset();"
								data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<%-- <div class="common-box-style">
	<div class="divTitle">下单途径比例图表</div>
	<div class="divContent">
		<div class="dealprofitdetailStatisticsDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="dealprofitdetailStatisticsChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div> --%>

<div class="common-box-style">
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dealprofitdetailStatistics.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>


</body>
</html>
