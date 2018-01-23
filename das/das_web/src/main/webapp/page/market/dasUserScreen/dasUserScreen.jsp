<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/dasUserScreen/dasUserScreen.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>筛选名称</th>
						<td><input type="text" id="screenNameSearch" name="screenNameSearch" maxlength="50"/></td>
						<th>行为类型</th>
						<td>
							<select id="behaviorTypeSearch" name="behaviorTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${behaviorTypeEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasUserScreen.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasUserScreen.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasUserScreen.addDlog(0, 0);"
		data-options="iconCls:'icon-add', plain:'true'">新增</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasUserScreen.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="dasUserScreen.addDlog(2, this.id)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="dasUserScreen.addDlog(1, this.id)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="dasUserScreen.deleteById(this.id)">删除</a>
</div>

<!-- 新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="dasUserScreen.save();">确定</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="dasUserScreen.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top: 50, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="screenId" id="screenId">
		<table class="commonTable">
			<tr>
				<th>筛选名称<span class="spanRed">*</span></th>
				<td colspan="3"><input class="easyui-validatebox" type="text" name="screenName" id="screenName" data-options="required:true, validType:'length[0,50]'" /></td>
			</tr>
			<tr>
				<td colspan="4" style="font-weight: bold;">》行为类型及时间筛选</td>
			</tr>
			<tr>
				<th>行为类型</th>
				<td colspan="3">
					<select id="behaviorType" name="behaviorType" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false, onChange: function(newValue,oldValue){dasUserScreen.getSilenceList(newValue,oldValue)}">
						<das:enumListTag dataList="${behaviorTypeEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<th>计划时间</th>
				<td>
					<select id="planTimeType" name="planTimeType" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
						<option value="">---关闭---</option>
						<das:enumListTag dataList="${planTimeTypeEnum}"/>
					</select>
				</td>
				<th>筛选时间</th>
				<td style="width: 400px;">
					<input class="easyui-datetimebox" type="text" name="startTime" id="startTime" data-options="required:false, editable:false"/>
					至
					<input class="easyui-datetimebox" type="text" name="endTime" id="endTime" data-options="required:false, editable:false"/>
				</td>
			</tr>
			<tr>
				<th>沉默无行为及天数</th>
				<td colspan="3">
					<select id="silence" name="silence" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
						<option value="">---请选择---</option>
						<das:enumListTag dataList="${silenceEnum}"/>
					</select>
					大于<input class="easyui-numberspinner" type="text" name="startSilenceDays" id="startSilenceDays" data-options="required:false, min:0, max:10000" />
					小于<input class="easyui-numberspinner" type="text" name="silenceDays" id="silenceDays" data-options="required:false, min:0, max:10000" />
				</td>
			</tr>
			<tr>
				<td colspan="4" style="font-weight: bold;">》开户信息筛选</td>
			</tr>
			<tr>
				<th>性别</th>
				<td>
					<select id="gender" name="gender" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
						<option value="">---全部---</option>
						<das:enumListTag dataList="${genderEnum}"/>
					</select>
				</td>
				<th>渠道</th>
				<td>
					<select id="channelIds" name="channelIds" multiple="multiple" >
						<c:forEach var="channel" items="${channelList}">
							<option value="${channel}">${channel}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>标签</th>
				<td>
					<select id="tagIds" name="tagIds" multiple="multiple" >
						<c:forEach var="tag" items="${tagList}">
							<option value="${tag.tagId}">${tag.tagName}</option>
						</c:forEach>
					</select>
				</td>
				<th>地域</th>
				<td>
					<select id="areas" name="areas" multiple="multiple">
						<das:enumListTag dataList="${areaEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<th>开户客户端</th>
				<td>
					<select id="client" name="client" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
						<option value="">---全部---</option>
						<das:enumListTag dataList="${clientEnum}"/>
					</select>
				</td>
				<th style="width: 120px;">开户浏览器</th>
				<td>
					<select id="browsers" name="browsers" multiple="multiple">
						<das:enumListTag dataList="${browserEnum}"/>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="font-weight: bold;">》行为类型次数筛选</td>
			</tr>
			<tr>
				<th>访问类型及次数</th>
				<td colspan="3">
					<select id="visitClient" name="visitClient" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
						<option value="">---全部---</option>
						<das:enumListTag dataList="${clientEnum}"/>
					</select>
					大于<input class="easyui-numberspinner" type="text" name="visitCountGt" id="visitCountGt" data-options="required:false, min:0, max:10000" />
					小于<input class="easyui-numberspinner" type="text" name="visitCountLt" id="visitCountLt" data-options="required:false, min:0, max:10000" />
				</td>
			</tr>
			<tr>
				<th>咨询类型及次数</th>
				<td colspan="3">
					<select id="advisory" name="advisory" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
						<option value="">---全部---</option>
						<das:enumListTag dataList="${advisoryEnum}"/>
					</select>
					大于<input class="easyui-numberspinner" type="text" name="advisoryCountGt" id="advisoryCountGt" data-options="required:false, min:0, max:10000" />
					小于<input class="easyui-numberspinner" type="text" name="advisoryCountLt" id="advisoryCountLt" data-options="required:false, min:0, max:10000" />
				</td>
			</tr>
			<tr>
				<th style="width: 140px;">短信行为类型及次数</th>
				<td colspan="3">
					<select id="smsSendStatus" name="smsSendStatus" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
						<option value="">---全部---</option>
						<das:enumListTag dataList="${sendStatusEnum}"/>
					</select>
					大于<input class="easyui-numberspinner" type="text" name="smsSendCountGt" id="smsSendCountGt" data-options="required:false, min:0, max:10000" />
					小于<input class="easyui-numberspinner" type="text" name="smsSendCountLt" id="smsSendCountLt" data-options="required:false, min:0, max:10000" />
				</td>
			</tr>
			<tr>
				<td colspan="4" style="font-weight: bold;">》排重</td>
			</tr>
			<tr>
				<th>排重</th>
				<td colspan="3">
					<span>模拟<input type="checkbox" name="isDemo" id="isDemo" value="N" onclick="if(checked==true) {value = 'Y'; checked='checked';} else {value = 'N'; checked='';}"/></span>
					<span>真实<input type="checkbox" name="isReal" id="isReal" value="N" onclick="if(checked==true) {value = 'Y'; checked='checked';} else {value = 'N'; checked='';}"/></span>
					<span>入金<input type="checkbox" name="isDepesit" id="isDepesit" value="N" onclick="if(checked==true) {value = 'Y'; checked='checked';} else {value = 'N'; checked='';}"/></span>
					<span>黑名单<input type="checkbox" name="isBacklist" id="isBacklist" value="N" onclick="if(checked==true) {value = 'Y'; checked='checked';} else {value = 'N'; checked='';}"/></span>
					<span>免扰<input type="checkbox" name="isFreeAngry" id="isFreeAngry" value="N" onclick="if(checked==true) {value = 'Y'; checked='checked';} else {value = 'N'; checked='';}"/></span>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="font-weight: bold;">》统计</td>
			</tr>
			<tr>
				<td colspan="4">
					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="dasUserScreen.showResultCount();"
								data-options="iconCls:'icon-search', plain:'true'">统计</a>
					<span id="resultSmsCountText" style="padding-left: 5px;"></span>
					<span id="resultEmailCountText" style="padding-left: 5px;"></span>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
