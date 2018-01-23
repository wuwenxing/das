<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/tag/tag.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<table class="commonTable">
				<tr>
					<th>标签名称</th>
					<td><input type="text" id="tagNameSearch" name="tagNameSearch" maxlength="50"/></td>
					<th>标签链接</th>
					<td><input type="text" id="tagUrlSearch" name="tagUrlSearch" maxlength="500" style="width: 350px;"/></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tag.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tag.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tag.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tag.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="tag.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; height:auto; min-height: 120px;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="tag.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="tag.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="tag.deleteById(this.id)">删除</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="tag.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="tag.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="tagId" id="tagId">
		<table class="commonTable">
			<tr>
				<th>标签名称<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="tagName" id="tagName" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>标签链接<span class="spanRed">*</span></th>
				<td>
					<textarea name="tagUrl" id="tagUrl"
					 class="textarea easyui-validatebox"
					 data-options="required:true, validType:'length[0,500]'"
					 style="width: 400px; height: 50px;"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
