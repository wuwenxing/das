<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/custConsultation/custConsultation.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
		    <input type="hidden" id="yearStart" name="yearStart" value="<das:date type="yearStart" format="yyyy-MM-dd"/>">
			<table class="commonTable">
				<tr>
					<th>时间类型</th>
					<td>
						<select id="reportTypeSearch" name="reportTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false,onChange: function(newValue,oldValue){custConsultation.setDateboxAttr(newValue,oldValue,1)}">
							<das:enumListTag dataList="${reportTypeEnum}"/>
						</select>
					</td>
					<th>时间</th>
					<td>
						<input class="easyui-datebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="required:false, editable:false" value="<das:date type="yearStart" format="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="searchButton">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="custConsultation.find();"
								data-options="iconCls:'icon-search', plain:'true'">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="custConsultation.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="custConsultation.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="custConsultation.addDlog_1();"
			data-options="iconCls:'icon-add', plain:'true'">导入新客咨询数</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="custConsultation.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="custConsultation.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; height:auto; min-height: 120px;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="custConsultation.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="custConsultation.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="custConsultation.deleteById(this.id)">删除</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="custConsultation.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="custConsultation.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="consulttationId" id="consulttationId">
		<table class="commonTable">
			<tr>
				<th>时间<span class="spanRed">*</span></th>
				<td>
				 <input class="easyui-datebox" type="text" name="consulttationTime" id="consulttationTime" data-options="required:false, editable:false" value="<das:date type="dayStart" format="yyyy-MM-dd"/>"/>
				</td>
			</tr>
			<tr>
				<th>新客咨询数<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="newConsulttationNum" id="newConsulttationNum" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>老客咨询数<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="oldConsulttationNum" id="oldConsulttationNum" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<th>时间类型<span class="spanRed">*</span></th>
				<td>
				    <select id="reportType" name="reportType" class="easyui-combobox" data-options="panelHeight:'auto', editable:false,onChange: function(newValue,oldValue){custConsultation.setDateboxAttr(newValue,oldValue,2)}">
						<das:enumListTag dataList="${reportTypeEnum}"/>
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>


<!-- 上传文件框 -->
<div id ="uploadDiv"></div>

<!-- 导入新客咨询数-新增框 -->
<div id="addDlogToolbar_1">
	<a id="addDlogCancel_1" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="custConsultation.cancel_1();">关闭</a>
</div>
<div id="addDlog_1" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar_1', top:50, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm_1" class="commonForm" method="post">
		<table class="commonTable">
			<tr>
				<th>时间类型<span class="spanRed">*</span></th>
				<td>
					<select id="reportTypeSearchImport" name="reportTypeSearchImport" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
								<das:enumListTag dataList="${reportTypeEnum}"/>
						</select>
				</td>
			</tr>
			<tr>
				<th>导入新客咨询数<span class="spanRed">*</span></th>
				<td>
					<div>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:'true'" href="javascript:custConsultation.submitChannel();">上传Excel文件</a>
					</div>
					<div class="tipText">
						1、上传Excel后，会先删除以前的客咨询数，在新增Excel的新客咨询数；<br/>
						2、Excel第一行标题分别为（序号、咨询类型、新客咨询数、老客咨询数、咨询时间）；<br/>
						3、Excel第二行开始为需要导入的数据，依次填入；<br/>
						4、咨询类型、咨询数、咨询时间列不能为空；<br/>
						5、咨询类型列为指定类型(日统计、月统计)；<br/>
					</div>
				</td>
			</tr>
			<tr>
				<th>导入后提示信息</th>
				<td>
					<textarea id="msg" name="msg"
					 class="textarea easyui-validatebox"
					 data-options="required:false, validType:'length[0,500]'"
					 style="width: 400px; height: 100px;"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
