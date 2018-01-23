<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/system/dict/dict.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>编号</th>
						<td><input type="text" id="dictCodeSearch" name="dictCodeSearch" maxlength="20"/></td>
						<th>名称</th>
						<td><input type="text" id="dictNameSearch" name="dictNameSearch" maxlength="20"/></td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dict.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dict.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dict.addDlog(0, 0);"
			data-options="iconCls:'icon-add', plain:'true'">新增字典分组</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dict.addDlog(3, 0);"
			data-options="iconCls:'icon-add', plain:'true'">新增字典</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-type=1-操作按钮 -->
<div id="rowOperation_1" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="dict.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="dict.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="dict.deleteById(this.id)">删除</a>
	<a class="easyui-linkbutton reload" data-options="plain:true,iconCls:'icon-reload'" onclick="dict.refresh(this.id)">刷新</a>
</div>
<!-- datagrid-type=2-操作按钮 -->
<div id="rowOperation_2" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="dict.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="dict.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="dict.deleteById(this.id)">删除</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="dict.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="dict.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="dictId" id="dictId">
		<input type="hidden" id="parentDictCode" name="parentDictCode" value="" />
		<input type="hidden" id="dictType" name="dictType" value="" />
		<table class="commonTable">
			<tr id="ShowParentDictCode">
				<th>上级编号</th>
				<td colspan="3">
					<span id="parentDictCodeSpan"></span>
				</td>
			</tr>
			<tr>
				<th>编号<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="dictCode" id="dictCode" data-options="required:true, validType:'length[0,50]'" /></td>
				<th>名称<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="dictName" id="dictName" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>排序<span class="spanRed">*</span></th>
				<td><input class="easyui-numberspinner" type="text" name="orderCode" data-options="required:true, min:0, max:10000" /></td>
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
