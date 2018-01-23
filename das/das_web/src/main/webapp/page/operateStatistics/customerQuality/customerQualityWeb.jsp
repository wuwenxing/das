<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/operateStatistics/customerQuality/customerQualityWeb.js?version=20170306"></script>
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
							<select id="reportTypeSearch" name="reportTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false, onChange: function(newValue,oldValue){customerQualityWeb.setDateboxAttr(newValue,oldValue)}">
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
						
						<th class="more"><input type="checkbox" id="deviceTypeChecked" name="deviceTypeChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>设备类型</th>
						<td class="more">
						    <select id="deviceTypeSearch" name="deviceTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${deviceTypeEnum}"/>
							</select>
						</td>
					    
					   
					</tr>
					<tr class="more">
					   
						 <th ><input type="checkbox" id="channelGroupChecked" name="channelGroupChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>渠道分组</th>
						 <td >
							<select id="channelGroupSearch" name="channelGroupSearch" multiple="multiple" >
									<c:forEach var="channelgroup" items="${channelGroupList}">
										<option value="${channelgroup}">${channelgroup}</option>
								</c:forEach>
							</select>
						</td>
						<th><input type="checkbox" id="channelLevelChecked" name="channelLevelChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>渠道分级</th>
						<td>
						    <select id="channelLevelSearch" name="channelLevelSearch" multiple="multiple" >
									<c:forEach var="channelLevel" items="${channelLevelList}">
										<option value="${channelLevel}">${channelLevel}</option>
								</c:forEach>
							</select>
						</td>
						
						<th><input type="checkbox" id="channelChecked" name="channelChecked"  value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>渠道</th>
						<td>
							<select id="channelSearch" name="channelSearch" multiple="multiple" >
									<c:forEach var="channel" items="${channelNameList}">
										<option value="${channel}">${channel}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr class="more">
					 
						<th><input type="checkbox" id="utmcsrChecked" name="utmcsrChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>来源</th>
						<td><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50"/></td>
						
						<th><input type="checkbox" id="utmcmdChecked" name="utmcmdChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>媒件</th>
						<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50"/></td>
						
						<th><input type="checkbox" id="utmccnChecked" name="utmccnChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>系列</th>
                        <td><input type="text" id="utmccnSearch" name="utmccnSearch" maxlength="50"/></td>
					
					</tr>
					<tr class="more">	
						
						<th><input type="checkbox" id="utmcctChecked" name="utmcctChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>组</th>
						<td><input type="text" id="utmcctSearch" name="utmcctSearch" maxlength="50"/></td>
						<th><input type="checkbox" id="utmctrChecked" name="utmctrChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>关键字</th>
						<td><input type="text" id="utmctrSearch" name="utmctrSearch" maxlength="50"/></td>
						<th><input type="checkbox" id="landingPageChecked" name="landingPageChecked" value="false" onclick="if(checked==true) {value = 'true'; checked='checked';} else {value = 'false'; checked='';}"/>landingPage</th>
						<td><input type="text" id="landingPageSearch" name="landingPageSearch" maxlength="50"/></td>
						
					</tr>
					<tr>
						<td colspan="6">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="customerQualityWeb.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="customerQualityWeb.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="customerQualityWeb.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
