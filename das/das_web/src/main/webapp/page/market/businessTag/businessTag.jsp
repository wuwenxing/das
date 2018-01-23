<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/businessTag/businessTag.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<table class="commonTable">
				<tr>
					<th>标签内容</th>
					<td>
					    <select id="tagContentSearch" name="tagContentSearch" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						   <das:enumListTag dataList="${busTagContentEnum}"/>
					    </select>
					</td>
					<th>URL</th>
					<td><input type="text" id="tagUrlSearch" name="tagUrlSearch" maxlength="500" style="width: 350px;"/></td>
					<th>Event字段</th>
					<td><input type="text" id="tagEventSearch" name="tagEventSearch" maxlength="50"/></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="businessTag.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="businessTag.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="businessTag.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="businessTag.addDlog_1();"
			data-options="iconCls:'icon-add', plain:'true'">导入标签</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="businessTag.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="businessTag.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; height:auto; min-height: 120px;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="businessTag.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="businessTag.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="businessTag.deleteById(this.id)">删除</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="businessTag.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="businessTag.cancel();">取消</a>
</div>

<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="tagId" id="tagId">
		<table class="commonTable">
			<tr>
				<th>标签内容<span class="spanRed">*</span></th>
				<td>
				    <select name="tagContent" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<das:enumListTag dataList="${busTagContentEnum}"/>
					</select>
				</td>
				<th>标签类型<span class="spanRed">*</span></th>
				<td>
					<select name="tagType" class="easyui-combobox tabTypeCombobox" data-options="required:true, panelHeight:'auto', editable:false,onChange: function(newValue,oldValue){businessTag.onchange(newValue,oldValue)}">
						<das:enumListTag dataList="${busTagTypeEnum}"/>
					</select>
				</td>
			</tr>
			<tr id="add1">
				<th>标签链接<span class="spanRed">*</span></th>
				<td>
					<textarea id="tagUrl" name="tagUrl" class="textarea easyui-validatebox" data-options="required:false, validType:'length[0,500]'" style="width: 400px; height: 50px;"></textarea>
				</td>
			</tr>
			
			<tr id="add2" style="display: none;">
				<th>EventCategory</th>
				<td><input class="easyui-validatebox" type="text" name="eventCategory" id="eventCategory" data-options="required:false, validType:'length[0,50]'" /></td>
				<th>EventAction</th>
				<td><input class="easyui-validatebox" type="text" name="eventAction" id="eventAction" data-options="required:false, validType:'length[0,50]'" /></td>
			</tr>
			<tr id="add3" style="display: none;">
				<th>EventLabel</th>
				<td><input class="easyui-validatebox" type="text" name="eventLabel" id="eventLabel" data-options="required:false, validType:'length[0,50]'" /></td>
				<th>EventValue</th>
				<td><input class="easyui-validatebox" type="text" name="eventValue" id="eventValue" data-options="required:false, validType:'length[0,50]'" /></td>
			</tr>
			
		</table>
	</form>
</div>

<!-- 上传文件框 -->
<div id ="uploadDiv"></div>

<!-- 导入标签-新增框 -->
<div id="addDlogToolbar_1">
	<a id="addDlogCancel_1" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="businessTag.cancel_1();">关闭</a>
</div>
<div id="addDlog_1" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar_1', top:50, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm_1" class="commonForm" method="post">
		<table class="commonTable">
		    <tr>
				<th>标签内容<span class="spanRed">*</span></th>
				<td>
				    <select id="tagContent" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<das:enumListTag dataList="${busTagContentEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<th>标签类型<span class="spanRed">*</span></th>
				<td>
					<select id="tagType" class="easyui-combobox uploadTabTypeCombobox" data-options="required:true, panelHeight:'auto', editable:false,onChange: function(newValue,oldValue){businessTag.onChangeExport(newValue,oldValue)}">
						<das:enumListTag dataList="${busTagTypeEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<th>导入渠道<span class="spanRed">*</span></th>
				<td>
					<div>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:'true'" href="javascript:businessTag.submitChannel();">上传Excel文件</a>
					</div>
					<div class="tipText">
						1、上传Excel后，会先删除以前的标签，在新增Excel的标签；<br/>
						2、标签类型为进入页面时，Excel第一行标题分别为URL,标签类型为触发事件时, Excel第一行标题分别为（eventCategory、eventAction、eventLabel、eventValue）；<br/>
						3、Excel第二行开始为需要导入的数据，依次填入；<br/>
						4、标签内容、标签类型、标签URL不能为空；<br/>
						6、点此<a id="exportHref" href="<%=basePath%>template/market/businessPageTagTemplate.xlsx">下载</a>模板；<br/>
					</div>
				</td>
			</tr>
			<tr>
				<th>导入后提示信息</th>
				<td>
					<textarea id="msg" name="msg"
					 class="textarea easyui-validatebox"
					 data-options="required:false"
					 style="width: 400px; height: 100px;"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
