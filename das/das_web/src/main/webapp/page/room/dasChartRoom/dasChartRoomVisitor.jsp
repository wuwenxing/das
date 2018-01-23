<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/room/dasChartRoom/dasChartRoomVisitor.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>昵称</th>
						<td><input type="text" id="nickNameSearch" name="nickNameSearch" maxlength="50"/></td>
						<th>手机号码</th>
						<td><input type="text" id="userTelSearch" name="userTelSearch" maxlength="50"/></td>
					</tr>
					<tr>
						<th>用户级别</th>
						<td>
							<select id="userTypeSearch" name="userTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${roomUserTypeEnum}"/>
							</select>
						</td>
						<th>在线时长</th>
						<td>
							<input class="easyui-timespinner" type="text" name="timeLengthStartSearch" id="timeLengthStartSearch" data-options="showSeconds:true"/>
							至
							<input class="easyui-timespinner" type="text" name="timeLengthEndSearch" id="timeLengthEndSearch" data-options="showSeconds:true"/>
						</td>
					</tr>
					<tr>
						<th>房间名称</th>
						<td><input type="text" id="roomNameSearch" name="roomNameSearch" maxlength="50"/></td>
						<th>注册时间</th>
						<td>
							<input class="easyui-datetimebox" type="text" name="regStartTimeSearch" id="regStartTimeSearch" data-options="required:false, editable:false" value=""/>
							至
							<input class="easyui-datetimebox" type="text" name="regEndTimeSearch" id="regEndTimeSearch" data-options="required:false, editable:false" value=""/>
						</td>
					</tr>
					<tr>
						<th>上线时间</th>
						<td>
							<input class="easyui-datetimebox" type="text" name="upStartTimeSearch" id="upStartTimeSearch" data-options="required:false, editable:false" value="<das:date type="yesterdayStart" format="yyyy-MM-dd HH:mm:ss"/>"/>
							至
							<input class="easyui-datetimebox" type="text" name="upEndTimeSearch" id="upEndTimeSearch" data-options="required:false, editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd HH:mm:ss"/>"/>
						</td>
						<th>下线时间</th>
						<td>
							<input class="easyui-datetimebox" type="text" name="downStartTimeSearch" id="downStartTimeSearch" data-options="required:false, editable:false" />
							至
							<input class="easyui-datetimebox" type="text" name="downEndTimeSearch" id="downEndTimeSearch" data-options="required:false, editable:false" />
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomVisitor.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomVisitor.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasChartRoomVisitor.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="dasChartRoomVisitor.viewDetail(this.id)">访客详情</a>
</div>

</body>
</html>
