<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/smsTemplate/smsTemplate.js?version=20170306"></script>
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
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsTemplate.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsTemplate.reset();"
									data-options="iconCls:'icon-empty', plain:'true'">重置</a>
		<!-- 						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsTemplate.test();" -->
		<!-- 							data-options="iconCls:'icon-empty', plain:'true'">Test</a> -->
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsTemplate.addDlog(0, 0);"
			data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsTemplate.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsTemplate.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsTemplate.viewLog('');"
			data-options="iconCls:'icon-view', plain:'true'">短信请求日志</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="smsTemplate.viewDetail('');"
			data-options="iconCls:'icon-view', plain:'true'">短信详情</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="smsTemplate.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="smsTemplate.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="smsTemplate.deleteById(this.id)">删除</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="smsTemplate.viewLog(this.id)">短信请求日志</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="smsTemplate.viewDetail(this.id)">短信详情</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="smsTemplate.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="smsTemplate.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="templateId" id="templateId">
		<table class="commonTable">
			<tr>
				<th>模板编号<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="code" id="code" data-options="required:true, validType:'length[0,50]'" onkeyup="value=value.replace(/[,.?!@#$%|\^&*(){}:;\\]/g,'')" /></td>
			</tr>
			<tr>
				<th>模板名称<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="name" id="name" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>模板内容<span class="spanRed">*</span></th>
				<td>
					<select id="smsSign" name="smsSign" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
						<c:forEach var="smsSign" items="${smsSignList}">
							<option value="${smsSign.value}">${smsSign.value}</option>
						</c:forEach>
					</select>
					<div style="padding-top: 2px;"></div>
					<textarea name="content" id="content"
					 class="textarea easyui-validatebox"
					 data-options="required:true, validType:'length[0,500]'"
					 style="width: 400px; height: 100px;"></textarea>
					<div class="tipText">
						1、短信编辑框中不需要加签名；<br/>
						2、短信编辑框中不需要加"退订回T"，系统会自动添加；<br/>
						3、字数小于等于70字，按1条扣费； 大于将按每67字扣1条计算；<br/>
						4、短信编辑框中可变参数写法，如：<%="${xxx}"%>，其中xxx为参数名称；<br/>
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
