<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/appDataAnalysis/appDataAnalysisDetails.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
			    <input type="hidden" id="reportTypeSearch" name="reportTypeSearch" value="${reportType}"/>			
				<input type="hidden" id="dtMonthSearch" name="dtMonthSearch" value="${dtMonth}"/>
				<input type="hidden" id="channelSearch" name="channelSearch" value="${channel}"/>
				<input type="hidden" id="fieldSearch" name="fieldSearch" value="${field}"/>
				<input type="hidden" id="devicetypeSearch" name="devicetypeSearch" value="${devicetype}"/>
				<input type="hidden" id="behaviorTypeSearch" name="behaviorTypeSearch" value="${behaviorType}"/>
				
				<input type="hidden" id="channelCheckedSearch" name="channelCheckedSearch" value="${channelChecked}"/>
				<input type="hidden" id="devicetypeCheckedSearch" name="devicetypeCheckedSearch" value="${devicetypeChecked}"/>
				
				<input type="hidden" id="paramValueSearch" name="paramValueSearch" value="${paramValue}"/>
				<table class="commonTable">
				   <tr>
				       <th>日期</th>
					   <td>
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false,formatter:easyui.formatterYYYYMM,parser:easyui.parserYYYYMM" value="${dtMonth}"/>						
					  </td>
					  <th>系统类型</th>
						<td>
							<select id="platformTypeSearch" name="platformTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${devicetypeEnum}"/>
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
						<td colspan="6">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataAnalysisDetails.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataAnalysisDetails.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appDataAnalysisDetails.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

</body>
</html>
