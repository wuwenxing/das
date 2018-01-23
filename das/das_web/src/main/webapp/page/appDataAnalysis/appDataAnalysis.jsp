<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/appDataAnalysis/appDataAnalysis.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">
		查询条件
	</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
		    <input type="hidden" id="yesterdayStart" name="yesterdayStart" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>">
			<input type="hidden" id="lessOneMonths" name="lessOneMonths" value="<das:date type="lessOneMonths" format="yyyy-MM-dd"/>">
			<input type="hidden" id="dayEnd" name="dayEnd" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>">
			<input type="hidden" id="lessThreeMonths" name="lessThreeMonths" value="<das:date type="lessThreeMonths" format="yyyy-MM"/>">
			<input type="hidden" id="monthEnd" name="monthEnd" value="<das:date type="monthEnd" format="yyyy-MM"/>">
			<input type="hidden" id="lessOneYears" name="lessOneYears" value="<das:date type="lessOneYears" format="yyyy-MM"/>">
			<div>
				<table class="commonTable">
					<tr>
						<th>统计周期</th>
					<td>
						<select id="reportTypeSearch" name="reportTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false, onChange: function(newValue,oldValue){appDataAnalysis.setDateboxAttr(newValue,oldValue)}">
							<das:enumListTag dataList="${reportTypeEnum}"/>
						</select>
					</td>
					<th>日期</th>
					<td>
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="lessOneMonths" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
					</td>
						<th>渠道</th>
						<td>
							<select id="channelIds" name="channelIds" multiple="multiple" >
								<c:forEach var="channel" items="${channelList}">
									<option value="${channel}">${channel}</option>
								</c:forEach>
							</select>
						</td>
						
					</tr>
					<tr>
					    <th>设备类型</th>
						<td>
							<select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${devicetypeEnum}"/>
							</select>
						</td>
						
						<th>报表隐藏列</th>
						<td colspan="3">
						   <input type="checkbox" id="channelChecked" name="channelChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>渠道
						   <input type="checkbox" id="devicetypeChecked" name="devicetypeChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>设备类型
						   <input type="checkbox" id="demoRateChecked" name="demoRateChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>模拟率
						   <input type="checkbox" id="realRateChecked" name="realRateChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>真实率
						
						</td>
						
					</tr>
					<tr>
						<td colspan="6">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataAnalysis.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataAnalysis.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataAnalysis.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
