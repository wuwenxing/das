<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/system/role/role.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>角色编号</th>
						<td><input type="text" id="roleCodeSearch" name="roleCodeSearch" maxlength="20"/></td>
						<th>角色名称</th>
						<td><input type="text" id="roleNameSearch" name="roleNameSearch" maxlength="20"/></td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="systemRole.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="systemRole.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="systemRole.addDlog(0, 0);"
			data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="systemRole.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="systemRole.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="systemRole.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="systemRole.deleteById(this.id)">删除</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-grant'" onclick="systemRole.assignUser(this.id)">分配用户</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-grant'" onclick="systemRole.assignMenu(this.id)">菜单权限</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="systemRole.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="systemRole.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="roleId" id="roleId">
		<table class="commonTable">
			<tr>
				<th>角色编号<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="roleCode" id="roleCode" data-options="required:true, validType:'length[0,50]'" /></td>
				<th>角色名称<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="roleName" id="roleName" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>业务权限<span class="spanRed">*</span></th>
				<td>
					<select id="companyIds" name="companyIds" multiple="multiple">
						<das:enumListTag dataList="${companyEnum}"/>
					</select>
				</td>
				<th>状态<span class="spanRed">*</span></th>
				<td colspan="3">
					<select id="enableFlag" name="enableFlag" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<option value="Y" selected>启用</option>
						<option value="N">禁用</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3">
					<textarea name="remark" id="remark"
					 class="textarea easyui-validatebox" data-options="validType:'length[0,100]'"
					 style="width: 400px; height: 50px;"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- 分配用户 -->
<div id="assignUserDlogToolbar">
	<a id="assignUserDlogRefresh" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-refresh'" onclick="systemRole.assignUserRefresh();">刷新为编辑前状态</a>
	<span class="spanRed">(备注：只保存当前页)</span>
	<a id="assignUserDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="systemRole.assignUserSave();">保存</a>
	<a id="assignUserDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="javascript:$('#assignUserDlog').dialog('close');">关闭</a>
</div>
<div id="assignUserDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#assignUserDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}, width: 500, height: 600">
	<div id="userToolbar">
		<div class="searchTitle">查询条件</div>
		<form id="userSearchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>帐号</th>
						<td><input type="text" id="userNoSearch" name="userNoSearch" maxlength="20"/></td>
						<th>姓名</th>
						<td><input type="text" id="userNameSearch" name="userNameSearch" maxlength="20"/></td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="systemRole.assignUserFind();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="javascript:$('#userSearchForm').form('reset');"
									data-options="iconCls:'icon-empty', plain:'true'">重置</a>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<div class="resultTitle">数据列表</div>
	</div>
	<input type="hidden" name="assignUserRoleId" id="assignUserRoleId">
	<table id="userDataGrid" data-options="toolbar:'#userToolbar'"></table>
</div>

<!-- 菜单权限 -->
<div id="assignMenuDlogToolbar">
	<a id="assignMenuDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="systemRole.assignMenuSave();">确定</a>
	<a id="assignMenuDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="javascript:$('#assignMenuDlog').dialog('close');">取消</a>
</div>
<div id="assignMenuDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#assignMenuDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}, width: 400, height: 600">
	<form id="assignMenuDlogForm" class="commonForm" method="post">
		<input type="hidden" name="assignMenuRoleId" id="assignMenuRoleId">
		<input id="menuTreeCheckbox" type="checkbox" checked onchange="systemRole.changeCascadeCheck();">复选框级联
		<div style="border-top: 1px solid #D0D0D0;"></div>
		<ul id="menuTree"></ul>
	</form>
</div>
	

</body>
</html>
