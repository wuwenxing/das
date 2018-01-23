<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/flowLog/flowLog.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>请求状态</th>
						<td>
							<select id="statusCodeSearch" name="statusCodeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<c:forEach var="statusCode" items="${statusCode}">
									<option value="${statusCode.labelKey}">${statusCode.value}</option>
								</c:forEach>
							</select>
						</td>
						<th>请求时间</th>
						<td>
							<input class="easyui-datetimebox" type="text" name="startDateSearch" id="startDateSearch" data-options="editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd HH:mm:ss"/>"/>
							至
							<input class="easyui-datetimebox" type="text" name="endDateSearch" id="endDateSearch" data-options="editable:false" value="<das:date type="monthEnd" format="yyyy-MM-dd HH:mm:ss"/>"/>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="flowLog.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="flowLog.reset();"
									data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="flowLog.viewDetail(this.id)">流量充值详情</a>
</div>

<div class="common-box-style">
	<div id="toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="flowLog.addDlog();"
			data-options="iconCls:'icon-add', plain:'true'">流量充值</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="flowLog.viewDetail('');"
			data-options="iconCls:'icon-view', plain:'true'">流量充值详情</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- 上传文件框 -->
<div id ="uploadDiv"></div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="flowLog.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="flowLog.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<table class="commonTable">
			<tr>
				<th>流量大小<span class="spanRed">*</span></th>
				<td>
					<select id="flowPackage" name="flowPackage" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
						<das:enumListTag dataList="${flowPackageEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<th>手机号<span class="spanRed">*</span></th>
				<td>
					<textarea name="phones" id="phones"
					 class="textarea easyui-validatebox"
					 data-options="required:true, validType:'length[0,600000]'"
					 style="width: 500px; height: 150px;"></textarea>
					<div>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:'true'" href="javascript:upload.uploadExcelFile('<%=basePath%>UploadController/flowUploadExcel/flowLog/callback', 'uploadDiv', '1', '上传Excel文件');">上传Excel文件</a>
					</div>
					<div class="tipText">
						1、多个手机号逗号隔开；<br/>
						2、每次发送不能超过5万个手机号；超过可分开多次操作；<br/>
						3、请注意手机号列必须为文本格式，否则会变为数值，改为文本格式的方法：<br/>
						（若内容不多，可以通过逐行双击回车的办法实现。）；<br/>
						（若内容较多，选定该列数据，点菜单“数据”——“分列”，连续按两次“下一步”，出现“列数据格式”，点中“文本”，完成。）；<br/>
						4、点此<a href="<%=basePath%>template/market/flowTemplate.xlsx">下载</a>模板；<br/>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>


</body>
</html>
