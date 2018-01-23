<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/system/uploadLog/uploadLog.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>文件类型</th>
						<td>
							<select id="fileTypeSearch" name="fileTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${uploadFileTypeEnum}"/>
							</select>
						</td>
						<th>文件路径</th>
						<td>
							<input type="text" id="filePathSearch" name="filePathSearch" maxlength="500" style="width: 350px;"/>
						</td>
					</tr>
					<tr>
						<th>文件名称</th>
						<td>
							<input type="text" id="fileNameSearch" name="fileNameSearch" maxlength="200" style="width: 350px;"/>
						</td>
						<th>文件访问地址</th>
						<td>
							<input type="text" id="fileUrlSearch" name="fileUrlSearch" maxlength="500" style="width: 350px;"/>
						</td>
					</tr>
					<tr>
						<th>上传时间</th>
						<td colspan="1">
							<input class="easyui-datetimebox" type="text" name="startDateSearch" id="startDateSearch" data-options="editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd HH:mm:ss"/>" />
							至
							<input class="easyui-datetimebox" type="text" name="endDateSearch" id="endDateSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd HH:mm:ss"/>" />
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="uploadLog.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="uploadLog.reset();"
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
	<div class="divContent">
		<table id="dataGrid" data-options="" style="width:100%; min-height: 91px; height:auto;"></table>
	</div>
</div>

</body>
</html>
