<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/system/user/user.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>账号</th>
						<td><input type="text" id="userNoSearch" name="userNoSearch" maxlength="50"/></td>
						<th>姓名</th>
						<td><input type="text" id="userNameSearch" name="userNameSearch" maxlength="50"/></td>
						<th>业务权限</th>
						<td>
							<select id="companyIdSearch" name="companyIdSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${companyEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
						<th>状态</th>
						<td colspan="5">
							<select id="enableFlagSearch" name="enableFlagSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${enableFlagEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="user.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="user.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="user.addDlog(0, 0);"
			data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="user.exportExcel();"
				data-options="iconCls:'icon-export', plain:'true'">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="user.exportExcelAuth();"
				data-options="iconCls:'icon-export', plain:'true'">导出权限</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="user.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="user.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="user.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="user.deleteById(this.id)">删除</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="user.resetPassword(this.id)">重置密码</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-grant'" onclick="user.assignMenu(this.id)">菜单权限</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="user.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="user.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="userId" id="userId">
		<table class="commonTable">
			<tr>
				<th>账号<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="userNo" id="userNo" data-options="required:true, validType:'length[0,50]'" /></td>
				<th>姓名<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="userName" id="userName" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>业务权限<span class="spanRed">*</span></th>
				<td>
					<select id="companyIds" name="companyIds" multiple="multiple">
						<das:enumListTag dataList="${companyEnum}"/>
					</select>
				</td>
				<th>状态<span class="spanRed">*</span></th>
				<td>
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
		<div class="tipText">
			1、新增用户初始密码为：111；
		</div>
	</form>
</div>

<!-- 菜单权限 -->
<div id="assignMenuDlogToolbar">
	<a id="assignMenuDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="user.assignMenuSave();">确定</a>
	<a id="assignMenuDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="javascript:$('#assignMenuDlog').dialog('close');">取消</a>
</div>
<div id="assignMenuDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#assignMenuDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}, width: 400, height: 600">
	<form id="assignMenuDlogForm" class="commonForm" method="post">
		<input type="hidden" name="assignMenuUserId" id="assignMenuUserId">
		<input id="menuTreeCheckbox" type="checkbox" checked onchange="user.changeCascadeCheck();">复选框级联
		<div style="border-top: 1px solid #D0D0D0;"></div>
		<ul id="menuTree"></ul>
	</form>
</div>


</body>
</html>
