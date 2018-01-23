<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/userGroup/userGroup.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>分组编号</th>
						<td><input type="text" id="codeSearch" name="codeSearch" maxlength="50"/></td>
						<th>分组名称</th>
						<td><input type="text" id="nameSearch" name="nameSearch" maxlength="50"/></td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroup.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroup.reset();"
									data-options="iconCls:'icon-empty', plain:'true'">重置</a>
		<!-- 								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroup.test();" -->
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroup.addDlog(0, 0);"
			data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroup.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroup.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroup.viewLog('');"
			data-options="iconCls:'icon-view', plain:'true'">分组请求日志</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="userGroup.viewDetail('');"
			data-options="iconCls:'icon-view', plain:'true'">分组详情</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="userGroup.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="userGroup.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="userGroup.deleteById(this.id)">删除</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="userGroup.viewLog(this.id)">请求日志</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="userGroup.viewDetail(this.id)">分组详情</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="userGroup.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="userGroup.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="groupId" id="groupId">
		<table class="commonTable">
			<tr>
				<th>分组编号<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="code" id="code" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>分组名称<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="name" id="name" data-options="required:true, validType:'length[0,50]'" /></td>
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
