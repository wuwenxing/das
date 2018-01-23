<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/appDataAnalysis/appOdsStatistics.js?version=20170306"></script>
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
			<table class="commonTable">
				<tr>				    
					<th>日期</th>
					<td>
						<input class="easyui-datetimebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd HH:mm:ss"/>"/>
						至
						<input class="easyui-datetimebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
                    <th class="more">账户</th>
					<td class="more">
						<input class="easyui-validatebox" type="text" name="accountSearch" id="accountSearch" data-options="required:false, validType:'length[0,50]'" />
					</td>
					<%-- <th class="more">操作类型</th>
				    <td class="more">
						<select id="operationTypeSearch" name="operationTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<das:enumListTag defaultVal="${operationType}" dataList="${operationTypeEnum}"/>
						</select>
				   </td> --%>	
				   <th class="more">交易平台</th>
				   <td class="more">
						<select id="platformSearch" name="platformSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<das:enumListTag defaultVal="${platformType}" dataList="${platformtypeEnum}"/>
						</select>
					 </td>	
				</tr>
				<tr class="more">
					<th>设备类型</th>
					<td>
						<select id="deviceTypeTypeSearch" name="deviceTypeTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<option value="ANDROID">ANDROID</option>
							<option value="IOS">IOS</option>
							<option value="PCUI">PCUI</option>
							<option value="WEBUI">WEBUI</option>
						</select>
					 </td>	
				    <th>用户类型</th>
				    <td>
						<select id="userTypeSearch" name="userTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<das:enumListTag defaultVal="${userType}" dataList="${userTypeEnum}"/>
						</select>
				    </td>				
					<th>账号等级</th>
					 <td>
						<select id="accountTypeSearch" name="accountTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
							<option value="">---请选择---</option>
							<das:enumListTag defaultVal="${accountType}" dataList="${dataAccountTypeEnum}"/>
						</select>
					</td>			
				</tr>
				<tr class="more">
                     
					<th>IDFA</th>
					<td><input type="text" id="idfa" name="idfa" maxlength="50"/></td>	
                    <th class="more">渠道</th>
					<td class="more">
						<select id="channelSearch" name="channelSearch" multiple="multiple" >
							<c:forEach var="channel" items="${channelList}">
								<option value="${channel}">${channel}</option>
							</c:forEach>
						</select>						
					</td>
					<th>设备唯一标识</th>
					<td><input type="text" id="deviceid" name="deviceid" maxlength="50"/></td>	
				</tr>
				
				<tr class="more">
					
                   	<th>设备厂商</th>
					<td><input type="text" id="carrier" name="carrier" maxlength="50"/></td>	
					<th>设备型号</th>
					<td><input type="text" id="model" name="model" maxlength="50"/></td>
					<th>应用名称</th>
					<td><input type="text" id="platformName" name="platformName" maxlength="50"/></td>	
				</tr>
				
				<tr class="more">                  
					
                   	<th>版本号</th>
					<td><input type="text" id="platformVersion" name="platformVersion" maxlength="50"/></td>
					<th>事件类别</th>
					<td><input type="text" id="eventCategory" name="eventCategory" maxlength="50"/></td>	
					<th>事件操作</th>
					<td><input type="text" id="eventAction" name="eventAction" maxlength="50"/></td>
				</tr>
				<tr class="more">                  
					
					<th>事件标签</th>
					<td><input type="text" id="eventLabel" name="eventLabel" maxlength="50"/></td>	
					<th>事件参数</th>
					<td><input type="text" id="eventValue" name="eventValue" maxlength="50"/></td>
					<th colspan="2"></th>
				</tr>
								
				
				<tr>
					<td colspan="6">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appOdsStatistics.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appOdsStatistics.reset();"
								data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div class="common-box-style">
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="appOdsStatistics.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>


</body>
</html>
