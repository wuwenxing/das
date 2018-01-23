<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/appDataAnalysis/appDataSource.js?version=20170306"></script>
<script type="text/javascript" src="<%=basePath%>page/appDataAnalysis/appDataSourceChart.js?version=20170306"></script>
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
			<input type="hidden" id="lessOneMonths" name="lessOneMonths" value="<das:date type="lessOneMonths" format="yyyy-MM-dd"/>">
			<input type="hidden" id="dayEnd" name="dayEnd" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>">
			<input type="hidden" id="monthEnd" name="monthEnd" value="<das:date type="monthEnd" format="yyyy-MM"/>">
			
			<input type="hidden" id="lessOneMonthsHour" name="lessOneMonthsHour" value="<das:date type="lessOneMonths" format="yyyy-MM-dd HH"/>">
			<input type="hidden" id="dayEndHour" name="dayEnd" value="<das:date type="dayEnd" format="yyyy-MM-dd HH"/>">
			
			<input type="hidden" id="yesterdayStartHour" name="yesterdayStartHour" value="<das:date type="yesterdayStart" format="yyyy-MM-dd HH"/>">
			<input type="hidden" id="beforeYesterdayStartHour" name="beforeYesterdayStartHour" value="<das:date type="beforeYesterdayStart" format="yyyy-MM-dd HH"/>">
			
			<input type="hidden" id="beforeYesterdayStart" name="beforeYesterdayStart" value="<das:date type="beforeYesterdayStart" format="yyyy-MM-dd"/>">
			<input type="hidden" id="halfaMonth" name="halfaMonth" value="<das:date type="halfaMonth" format="yyyy-MM-dd"/>">
			<input type="hidden" id="halfaWeek" name="halfaWeek" value="<das:date type="halfaWeek" format="yyyy-MM-dd"/>">
		    <input type="hidden" id="yesterdayStart" name="yesterdayStart" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>">
			<input type="hidden" id="lessThreeMonths" name="lessThreeMonths" value="<das:date type="lessThreeMonths" format="yyyy-MM"/>">
			<div>
				<table class="commonTable">
					<tr>
						<th>统计周期</th>
						<td>
							<select id="reportTypeSearch" name="reportTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false, onChange: function(newValue,oldValue){appDataSource.setDateboxAttr(newValue,oldValue)}">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${reportTypeEnum}"/>
							</select>
						</td> 
						
						<th>日期</th>
						<td id="dateTimeTd">
							<input class="easyui-datetimebox" type="text" name="startTimeSearchHour" id="startTimeSearchHour" data-options="editable:false, formatter:easyui.formatterYYYYMMDDHH" value="<das:date type="lessOneMonthsHour" format="yyyy-MM-dd"/>"/>
							至
							<input class="easyui-datetimebox" type="text" name="endTimeSearchHour" id="endTimeSearchHour" data-options="editable:false, formatter:easyui.formatterYYYYMMDDHH" value="<das:date type="dayEndHour" format="yyyy-MM-dd"/>"/>
						</td>
						
						<td id="dateTd">
							<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>"/>
						            至
						    <input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
						</td>
						
						<th class="more"><input type="checkbox" id="platformVersionChecked" name="platformVersionChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>版本</th>
						<td class="more"><input type="text" id="platformVersionSearch" name="platformVersionSearch" maxlength="50"/></td>
					    
					   
					</tr>
					<tr class="more">
					   
						 <th ><input type="checkbox" id="deviceTypeChecked" name="deviceTypeChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>设备类型</th>
						<td >
							<select id="deviceTypeSearch" name="deviceTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<option value="1">ANDROID</option>
								<option value="2">IOS</option>
								<option value="3">PCUI</option>
								<option value="4">WEBUI</option>
							</select>
						</td>
						<th><input type="checkbox" id="platformTypeChecked" name="platformTypeChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>交易平台</th>
						<td>
						    <select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${platformTypeEnum}"/>
							</select>
						</td>
						
						<th><input type="checkbox" id="platformNameChecked" name="platformNameChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>马甲包</th>
						<td><input type="text" id="platformNameSearch" name="platformNameSearch" maxlength="50"/></td>
					
					   
					
					</tr>
					
					<tr class="more">
					 
						<th><input type="checkbox" id="channelChecked" name="channelChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>渠道</th>
						<td>
							<select id="channelSearch" name="channelSearch" multiple="multiple" >
								<c:forEach var="channel" items="${channelList}">
									<option value="${channel}">${channel}</option>
								</c:forEach>
							</select>						
						</td>
						<th><input type="checkbox" id="userTypeChecked" name="userTypeChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>客户类型</th>
						<td>
							    <select id="userTypeSearch" name="userTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
									<option value="">---请选择---</option>
									<das:enumListTag dataList="${userTypeEnum}"/>
								</select>
						</td>
						
						<th><input disabled="disabled" type="checkbox" id="eventCategoryChecked" name="eventCategoryChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>事件类别</th>
                        <td><input type="text" id="eventCategorySearch" name="eventCategorySearch" maxlength="50"/></td>
					
					    
					
					</tr>
					<tr class="more">	
						
						<th><input disabled="disabled" type="checkbox" id="eventActionChecked" name="eventActionChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>事件操作</th>
						<td><input type="text" id="eventActionSearch" name="eventActionSearch" maxlength="50"/></td>
						<th><input disabled="disabled" type="checkbox" id="eventLabelChecked" name="eventLabelChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>事件标签</th>
						<td><input type="text" id="eventLabelSearch" name="eventLabelSearch" maxlength="50"/></td>
						
						<th><input disabled="disabled" type="checkbox" id="eventValueChecked" name="eventValueChecked" checked="checked" value="true" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>事件参数</th>
						<td><input type="text" id="eventValueSearch" name="eventValueSearch" maxlength="50"/></td>
					</tr>
					<tr>
						<td colspan="6">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataSource.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataSource.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataSource.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 700px; height:auto;"></table>
	</div>
</div>

<div class="common-box-style">
	<div class="divTitle">触发次数</div>
	<div class="divContent">
		<div class="deviceidNumChartLoadingDiv" style="padding:15px;"><img src = "<%=basePath%>img/icons/loading.gif" style="vertical-align: middle; padding-right: 5px;"/>正在处理，请稍待。。。</div>
		<div id="deviceidNumChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto"></div>
	</div>
</div>

<div class="common-box-style">
	<div class="divTitle">触发设备数</div>
	<div class="divContent">
		<div id="deviceidCountChartContainer" style="min-width: 600px; min-height: 400px; margin: 0 auto; padding-top: 20px;"></div>
	</div>
</div>





</body>
</html>
