<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/emailTemplate/emailTemplate.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>模板编号</th>
						<td><input type="text" id="codeSearch" name="codeSearch" maxlength="50"/></td>
						<th>模板名称</th>
						<td><input type="text" id="nameSearch" name="nameSearch" maxlength="50"/></td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="emailTemplate.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="emailTemplate.reset();"
									data-options="iconCls:'icon-empty', plain:'true'">重置</a>
		<!-- 								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="emailTemplate.test();" -->
		<!-- 									data-options="iconCls:'icon-empty', plain:'true'">Test</a> -->
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="emailTemplate.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="emailTemplate.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="emailTemplate.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="emailTemplate.viewLog('');"
			data-options="iconCls:'icon-view', plain:'true'">邮件请求日志</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="emailTemplate.viewDetail('');"
			data-options="iconCls:'icon-view', plain:'true'">邮件详情</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; height:auto; min-height: 120px;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="emailTemplate.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="emailTemplate.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="emailTemplate.deleteById(this.id)">删除</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="emailTemplate.viewLog(this.id)">请求日志</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="emailTemplate.viewDetail(this.id)">邮件详情</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="emailTemplate.preview(this.id)">邮件预览</a>
</div>

<!-- 上传文件框 -->
<div id ="uploadDiv"></div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="emailTemplate.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="emailTemplate.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:30, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="templateId" id="templateId">
		<table class="commonTable">
			<tr>
				<th>模板编号<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="code" id="code" data-options="required:true, validType:'length[0,50]'" style="width: 300px;"/></td>
			</tr>
			<tr>
				<th>模板名称<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="name" id="name" data-options="required:true, validType:'length[0,50]'" style="width: 300px;"/></td>
			</tr>
			<tr>
				<th>模板标题<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="title" id="title" data-options="required:true, validType:'length[0,50]'" style="width: 1000px;"/></td>
			</tr>
			<tr>
				<th>模板内容<span class="spanRed">*</span></th>
				<td>
					<div style="padding-top: 2px;"></div>
					<textarea name="content" id="content"
					 class="textarea easyui-validatebox"
					 data-options="required:true, validType:'length[0,20000]'"
					 style="width: 1000px; height: 450px;"></textarea>
					<div>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:'true'" href="javascript:upload.uploadImg('<%=basePath%>UploadController/uploadImg/emailTemplate/callback', 'uploadDiv', '1', '上传图片');">上传图片</a>
					</div>
					<div id="uploadFileUrl"></div>
					<div class="tipText">
						1、编辑框中可变参数写法，如：<%="${xxx}"%>，其中xxx为参数名称；<br/>
					</div>
				</td>
			</tr>
			<tr>
				<th>状态<span class="spanRed">*</span></th>
				<td>
					<select id="enableFlag" name="enableFlag" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<option value="Y" selected>启用</option>
						<option value="N">禁用</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
