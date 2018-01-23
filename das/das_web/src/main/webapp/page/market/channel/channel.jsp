<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/channel/channel.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>渠道名称</th>
						<td><input type="text" id="channelNameSearch" name="channelNameSearch" maxlength="50"/></td>
						<th>渠道类型</th>
						<td>
							<select id="channelTypeSearch" name="channelTypeSearch" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${channelTypeEnum}"/>
							</select>
						</td>
						<th>是否付费</th>
						<td>
							<select id="isPaySearch" name="isPaySearch" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<option value="1">免费</option>
								<option value="2">付费</option>
								<option value="3">其他</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>来源(utmcsr)</th>
						<td><input type="text" id="utmcsrSearch" name="utmcsrSearch" maxlength="50"/></td>
						<th style="width: 110px;">媒介(utmcmd)</th>
						<td><input type="text" id="utmcmdSearch" name="utmcmdSearch" maxlength="50"/></td>
					</tr>
					<tr>
						<th>渠道分组</th>
						<td><input type="text" id="channelGroupSearch" name="channelGroupSearch" maxlength="50"/></td>
						<th>渠道分级</th>
						<td colspan="3"><input type="text" id="channelLevelSearch" name="channelLevelSearch" maxlength="50"/></td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="channel.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="channel.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="channel.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="channel.addDlog_1();"
			data-options="iconCls:'icon-add', plain:'true'">导入渠道</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="channel.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="channel.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="channel.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="channel.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="channel.deleteById(this.id)">删除</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="channel.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="channel.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="channelId" id="channelId">
		<table class="commonTable">
			<tr>
				<th>渠道名称<span class="spanRed">*</span></th>
				<td><input class="easyui-validatebox" type="text" name="channelName" id="channelName" data-options="required:true, validType:'length[0,50]'" /></td>
				<th>渠道类型<span class="spanRed">*</span></th>
				<td>
					<select name="channelType" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<option value="">---请选择---</option>
						<das:enumListTag dataList="${channelTypeEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<th>渠道分组</th>
				<td>
					<input class="easyui-validatebox" type="text" name="channelGroup" id="channelGroup" data-options="required:false, validType:'length[0,50]'" />
					<div class="tipText">
						不填写则默认为：Direct<br/>
					</div>
				</td>
				<th>渠道分级</th>
				<td>
					<input class="easyui-validatebox" type="text" name="channelLevel" id="channelLevel" data-options="required:false, validType:'length[0,50]'" />
					<div class="tipText">
						不填写则默认为：Direct<br/>
					</div>
				</td>
			</tr>
			<tr>
				<th>来源(utmcsr)</th>
				<td>
					<input class="easyui-validatebox" type="text" name="utmcsr" id="utmcsr" data-options="validType:'length[0,50]'" />
				</td>
				<th style="width: 110px;">媒介(utmcmd)</th>
				<td>
					<input class="easyui-validatebox" type="text" name="utmcmd" id="utmcmd" data-options="validType:'length[0,50]'" />
				</td>
			</tr>
			<tr>
				<th>是否付费</th>
				<td colspan="3">
					<select id="isPay" name="isPay" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<option value="1" selected>免费</option>
						<option value="2">付费</option>
						<option value="3">其他</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- 上传文件框 -->
<div id ="uploadDiv"></div>

<!-- 导入渠道-新增框 -->
<div id="addDlogToolbar_1">
	<a id="addDlogCancel_1" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="channel.cancel_1();">关闭</a>
</div>
<div id="addDlog_1" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar_1', top:50, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm_1" class="commonForm" method="post">
		<table class="commonTable">
			<tr>
				<th>渠道类型<span class="spanRed">*</span></th>
				<td>
					<select id="channelType" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<option value="">---请选择---</option>
						<das:enumListTag dataList="${channelTypeEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<th>导入渠道<span class="spanRed">*</span></th>
				<td>
					<div>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:'true'" href="javascript:channel.submitChannel();">上传Excel文件</a>
					</div>
					<div class="tipText">
						1、上传Excel后，会先删除以前的渠道，在新增Excel的渠道；<br/>
						2、Excel第一行标题分别为（广告来源、广告媒介、渠道名称、是否免付费、渠道分组、渠道分级）；<br/>
						3、Excel第二行开始为需要导入的数据，依次填入；<br/>
						4、渠道名称不能为空；<br/>
						5、是否免付费列为指定类型(免费、付费、其他)；<br/>
						6、点此<a href="<%=basePath%>template/market/channelTemplate.xlsx">下载</a>模板；<br/>
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
