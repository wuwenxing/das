<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/accountAnalyze/accountAnalyze.js?version=20170306"></script>
</head>
<body>

<input type="hidden" id="accountAnalyzeDomainName" value="${accountAnalyzeDomainName}">

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>批次号</th>
						<td><input type="text" id="batchNoSearch" name="batchNoSearch" maxlength="50"/></td>
						<th>生成报告状态</th>
						<td>
							<select id="accountAnalyzeStatusSearch" name="accountAnalyzeStatusSearch" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${accountAnalyzeStatusEnum}"/>
							</select>
						</td>
						<th>生成报告时间</th>
						<td>
							<input class="easyui-datetimebox" type="text" name="generateTimeStartSearch" id="generateTimeStartSearch" data-options="editable:false" value=""/>
							至
							<input class="easyui-datetimebox" type="text" name="generateTimeEndSearch" id="generateTimeEndSearch" data-options="editable:false" value=""/>
						</td>
					</tr>
					<tr>
						<th>账号</th>
						<td><input type="text" id="accountNoSearch" name="accountNoSearch" maxlength="50"/></td>
						<th>邮件发送状态</th>
						<td>
							<select id="sendStatusSearch" name="sendStatusSearch" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${sendStatusEnum}"/>
							</select>
						</td>
						<th>邮件发送时间</th>
						<td>
							<input class="easyui-datetimebox" type="text" name="sendTimeStartSearch" id="sendTimeStartSearch" data-options="editable:false" value=""/>
							至
							<input class="easyui-datetimebox" type="text" name="sendTimeEndSearch" id="sendTimeEndSearch" data-options="editable:false" value=""/>
						</td>
					</tr>
					<tr>
						<th>邮箱</th>
						<td><input type="text" id="recEmailSearch" name="recEmailSearch" value="" maxlength="50"/></td>
						<th>更新时间</th>
						<td colspan="3">
							<input class="easyui-datetimebox" type="text" name="startDateSearch" id="startDateSearch" data-options="editable:false" value=""/>
							至
							<input class="easyui-datetimebox" type="text" name="endDateSearch" id="endDateSearch" data-options="editable:false" value=""/>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="accountAnalyze.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="accountAnalyze.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="accountAnalyze.addDlog_1();"
			data-options="iconCls:'icon-add', plain:'true'">导入账号数据</a>
<!-- 		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="accountAnalyze.exportExcel();" -->
<!-- 			data-options="iconCls:'icon-export', plain:'true'">导出</a> -->
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="accountAnalyze.generateReport('0', '', '');"
			data-options="iconCls:'icon-ok', plain:'true'">生成报告</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="accountAnalyze.sendEmail('0', '', '');"
			data-options="iconCls:'icon-ok', plain:'true'">发送邮件</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="accountAnalyze.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton ok" data-options="plain:true,iconCls:'icon-ok'" onclick="accountAnalyze.generateReport('1', this.id, this)">生成报告</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="accountAnalyze.previewReport(this)">预览报告</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="accountAnalyze.editReport(this.id, this)">编辑报告</a>
	<a class="easyui-linkbutton ok" data-options="plain:true,iconCls:'icon-ok'" onclick="accountAnalyze.sendEmail('1', this.id, this)">发送邮件</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="accountAnalyze.deleteById(this.id)">删除</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="accountAnalyze.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="accountAnalyze.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="batchId" id="batchId">
		<table class="commonTable">
			<tr>
				<th>批次号</th>
				<td id="batchNoTd"></td>
			</tr>
			<tr>
				<th>账号</th>
				<td id="accountNoTd"></td>
			</tr>
			<tr>
				<th>诊断报告内容</th>
				<td>
					<textarea id="content" name="content" style="width: 800px; height: 300px;"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- 上传文件框 -->
<div id ="uploadDiv"></div>

<!-- 导入账号诊断数据-新增框 -->
<div id="addDlogToolbar_1">
	<a id="addDlogCancel_1" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="accountAnalyze.cancel_1();">关闭</a>
</div>
<div id="addDlog_1" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar_1', top:50, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm_1" class="commonForm" method="post">
		<table class="commonTable">
			<tr>
				<th style="width: 120px;">导入账号诊断数据<span class="spanRed">*</span></th>
				<td>
					<div>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:'true'" href="javascript:accountAnalyze.submitAccountAnalyze();">上传Excel文件</a>
					</div>
					<div class="tipText">
						1、Excel第一行标题分别为（账号、平台、邮箱、诊断开始时间、诊断结束时间）；<br/>
						2、Excel第二行开始为需要导入的数据，依次填入；<br/>
						3、平台数据列为指定值(空、MT4、MT5、GTS、GTS2)；<br/>
						4、除了平台数据列，其他数据列不能为空且需为有效值；<br/>
						5、点此<a href="<%=basePath%>template/market/accountAnalyzeTemplate.xlsx">下载</a>模板；<br/>
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
