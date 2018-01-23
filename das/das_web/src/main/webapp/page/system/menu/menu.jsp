<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/system/menu/menu.js?version=20170306"></script>
</head>
<body>

<!-- 操作栏 -->
<div id="menuDivBox_1" style="width: 350px; float: left;">
	<div id="menuLeftDiv" class="common-box-style">	
		<div class="divTitle">菜单列表</div>
		<div class="divContent">
			<div>
				<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" onclick="menu.reloadTree();">刷新</a>
				<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="menu.appendTree();">增加</a>
				<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="menu.removeTree();">删除</a>
			    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="menu.expandAll();">展开</a>
			    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="menu.collapseAll();">收起</a>
			</div>
			<ul id="menuTree"></ul>
		</div>
	</div>
</div>

<div id="menuDivBox_2" style="margin-left: 360px;">
	<div id="menuDiv">
		<!-- 菜单详情 -->
		<div class="common-box-style">	
			<div class="divTitle">菜单详情</div>
			<div class="divContent">
				<form id="saveDlogForm" class="commonForm" method="post">
					<input type="hidden" name="menuId" id="menuId">
					<table class="commonTable">
						<tr>
							<th>上级名称/编号</th>
							<td colspan="3" style="height: 33px;"><span id="parentMenuName"></span></td>
						</tr>
						<tr>
							<th>类型<span class="spanRed">*</span></th>
							<td>
								<select id="menuType" name="menuType" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
									<option value="0" selected>顶部页签</option>
									<option value="1" selected>菜单</option>
									<option value="2">功能</option>
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
							<th>编号<span class="spanRed">*</span></th>
							<td><input class="easyui-validatebox" type="text" name="menuCode" id="menuCode" data-options="required:true, validType:'length[0,50]'" /></td>
							<th>名称<span class="spanRed">*</span></th>
							<td><input class="easyui-validatebox" type="text" name="menuName" id="menuName" data-options="required:true, validType:'length[0,50]'" /></td>
						</tr>
						<tr>
							<th>排序<span class="spanRed">*</span></th>
							<td colspan="3"><input class="easyui-numberspinner" type="text" name="orderCode" id="orderCode" data-options="required:true, min:0, max:10000" /></td>
						</tr>
						<tr>
							<th>请求url</th>
							<td colspan="3"><input class="easyui-validatebox" type="text" name="menuUrl" id="menuUrl" data-options="validType:'length[0,200]'" style="width: 400px;"/></td>
						</tr>
						<tr>
							<td colspan="8">
								<div class="searchButton">
									<a href="javascript:void(0);" class="easyui-linkbutton" onclick="menu.save();"
										data-options="iconCls:'icon-ok', plain:'true'">保存</a>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- 拥有此菜单权限的用户 -->
		<div class="common-box-style">	
			<div class="divTitle">拥有此菜单权限的用户</div>
			<div class="divContent">
				<table id="dataGrid" data-options="" style="width:100%; min-height: 90px; height:auto;"></table>
			</div>
		</div>
	</div>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="menu.add();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="menu.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="menuId" >
		<input type="hidden" name="parentMenuCode" id="parentMenuCode" >
		<table class="commonTable">
			<tr>
				<th>上级名称/编号</th>
				<td colspan="3" style="height: 33px;"><span id="parentMenuName_Add"></span></td>
			</tr>
			<tr>
				<th>类型<span class="spanRed">*</span></th>
				<td>
					<select name="menuType" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<option value="0" selected>顶部页签</option>
						<option value="1" selected>菜单</option>
						<option value="2">功能</option>
					</select>
				</td>
				<th>状态<span class="spanRed">*</span></th>
				<td>
					<select name="enableFlag" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<option value="Y" selected>启用</option>
						<option value="N">禁用</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>编号<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="menuCode" data-options="required:true, validType:'length[0,50]'" /></td>
				<th>名称<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="menuName" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>排序<span class="spanRed">*</span></th>
				<td colspan="3"><input class="easyui-numberspinner" type="text" name="orderCode" data-options="required:true, min:0, max:10000" /></td>
			</tr>
			<tr>
				<th>请求url</th>
				<td colspan="3"><input class="easyui-validatebox" type="text" name="menuUrl" data-options="validType:'length[0,200]'" style="width: 400px;"/></td>
			</tr>
		</table>
	</form>
</div>

</body>
</body>
</html>
