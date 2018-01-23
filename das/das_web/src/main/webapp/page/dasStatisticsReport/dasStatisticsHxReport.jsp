<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/dasStatisticsReport/dasStatisticsHxReport.js?version=20170306"></script>
</head>
<body>
    
<div class="common-box-style">
	<div class="divTitle">
		查询条件
	</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
		<div>
			<input type="hidden" id="addSixMonths" name="addSixMonths" value="<das:date type="addSixMonths" format="yyyy-MM-dd"/>">
			<input type="hidden" id="dayStart" name="dayStart" value="<das:date type="dayStart" format="yyyy-MM-dd"/>">
			<input type="hidden" id="halfaMonth" name="halfaMonth" value="<das:date type="halfaMonth" format="yyyy-MM-dd"/>">
			<input type="hidden" id="monthStart" name="monthStart" value="<das:date type="monthStart" format="yyyy-MM-dd"/>">
			<input type="hidden" id="dayEnd" name="dayEnd" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>">
			<table class="commonTable">
			   <tr>
					<th>报表类型:</th>
					<td colspan="3">
					<select id="reportFormType" name="reportFormType" class="easyui-combobox" data-options="panelHeight:'auto', editable:false, onChange: function(newValue,oldValue){dasStatisticsReport.reportFormTypeChange(newValue,oldValue)}" >
						<option value="0">日报总表</option>
						<option value="1">日报渠道表</option>
					</select>
				   </td>
				</tr>
				
				<tr>
				   <th colspan="4">&nbsp;</th>
				</tr>
				
			    <tr id="monthSearchTr" style="display: none;">
					<th>按月统计时间:</th>
					<td colspan="3">
						<input class="easyui-datebox" type="text" name="startmonthSearch" id="startmonthSearch" data-options="required:false, editable:false,formatter:easyui.formatterYYYYMM,parser:easyui.parserYYYYMM" value="<das:date type="addSixMonths" format="yyyy-MM"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endmonthSearch" id="endmonthSearch" data-options="required:false, editable:false,formatter:easyui.formatterYYYYMM,parser:easyui.parserYYYYMM" value="<das:date type="dayStart" format="yyyy-MM"/>"/>
					</td>
				</tr>	
				<tr id="daysSearchTr">
					<th>按日统计时间:</th>
					<td colspan="3">
						<input class="easyui-datebox" type="text" name="startDaysSearch" id="startDaysSearch" data-options="required:false, editable:false" value="<das:date type="halfaMonth" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endDaysSearch" id="endDaysSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
					</td>
				</tr>	
				<tr id="dayshideTr">
					<th>报表隐藏列:</th>
					<td colspan="3">
						<input type="checkbox" id="monthAllChecked" name="monthAllChecked" checked="checked" value="true" onclick=""/>全部
						<input type="checkbox" id="monthDeviceChecked" name="monthDeviceChecked" checked="checked" value="true" onclick=""/>设备类型
						<input type="checkbox" id="monthChannelChecked" name="monthChannelChecked" checked="checked" value="true" onclick=""/>渠道
					</td>					
				</tr>				
				<tr id="dailyChannelTr" class="">
					<th>日期:</th>
					<td>
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd"/>"/>
						至
						<input class="easyui-datebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
					</td>
											
					<th>访问客户端</th>
					<td>
						<select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<das:enumListTag defaultVal="${platformType}" dataList="${clientEnum}"/>
						</select>
					</td>
				</tr>
				<tr id="dailyChanneldowntr">					
				    <th>是否付费</th>
					<td>
						<select id="channeltype" name="channeltype" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false,onChange: function(newValue,oldValue){dasStatisticsReport.channeltypeChange(newValue,oldValue)}">
							<option value="-1">---请选择---</option>
							<option value="1">免费</option>
							<option value="2">付费</option>
							<option value="3">其他</option>
						</select>
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
					<td colspan="4">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasStatisticsReport.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasStatisticsReport.reset();"
								data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							
						</div>
					</td>
				</tr>
			</table>
		</div>
		</form>
	</div>
</div>

<div id="statisticsReportDiv">
	<div class="common-box-style dataGridMonth">
		<div id="toolbarMonth">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasStatisticsReport.exportExcel('months');"
				data-options="iconCls:'icon-export', plain:'true'">导出</a>
		</div>
		<div class="divTitle">月均报表</div>
		<div class="divContent">
			<table id="dataGridMonth" data-options="toolbar: '#toolbarMonth'" style="width:100%; min-height: 120px; height:auto;"></table>
		</div>
	</div>

	<div class="common-box-style dataGridDay">	
		<div id="toolbarDay">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasStatisticsReport.exportExcel('days');"
				data-options="iconCls:'icon-export', plain:'true'">导出</a>
		</div>
		<div class="divTitle">日报总表</div>
		<div class="divContent">
			<table id="dataGridDays" data-options="toolbar: '#toolbarDay'" style="width:100%; min-height: 120px; height:auto;"></table>	
		</div>
	</div>
</div>

<div id="statisticsDailyChannelDiv">
	<div class="common-box-style">
		<div id="toolbarDailyChannel">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasStatisticsReport.exportDailyChannel();"
				data-options="iconCls:'icon-export', plain:'true'">导出</a>
		</div>
		<div class="divTitle">日报渠道表</div>
		<div class="divContent">
			<table id="dataGridDailyChannel" data-options="toolbar: '#toolbarDailyChannel'" style="width:100%; min-height: 120px; height:auto;"></table>
		</div>
	</div>
</div>

</body>
</html>
