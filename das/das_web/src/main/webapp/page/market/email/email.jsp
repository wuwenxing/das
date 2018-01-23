<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/market/email/email.js?version=20170306"></script>
</head>
<body>

<div class="common-box-style">
	<div class="divTitle">查询条件</div>
	<div class="divContent">
		<form id="searchForm" class="searchForm" action="">
			<div>
				<table class="commonTable">
					<tr>
						<th>邮件标题</th>
						<td><input type="text" id="titleSearch" name="titleSearch" maxlength="50"/></td>
						<th>发送类型</th>
						<td>
							<select id="sendTypeSearch" name="sendTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${sendTypeEnum}"/>
							</select>
						</td>
						<th>发送时间</th>
						<td>
							<input class="easyui-datetimebox" type="text" name="startDateSearch" id="startDateSearch" data-options="editable:false" value="<das:date type="monthStart" format="yyyy-MM-dd HH:mm:ss"/>"/>
							至
							<input class="easyui-datetimebox" type="text" name="endDateSearch" id="endDateSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd HH:mm:ss"/>"/>
						</td>
					</tr>
					<tr>
						<td colspan="8">
							<div class="searchButton">
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="email.find();"
									data-options="iconCls:'icon-search', plain:'true'">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="email.reset();"
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
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="email.addDlog(0, 0, 0);"
			data-options="iconCls:'icon-add', plain:'true'">即时发送邮件</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="email.addDlog_1(0, 0, 1);"
			data-options="iconCls:'icon-add', plain:'true'">定时发送邮件</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="email.exportExcel();"
			data-options="iconCls:'icon-export', plain:'true'">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" onclick="email.deleteBatch();"
			data-options="iconCls:'icon-remove', plain:'true'">删除</a>
	</div>
	<div class="divTitle">数据列表</div>
	<div class="divContent">
		<table id="dataGrid" data-options="toolbar: '#toolbar'" style="width:100%; min-height: 120px; height:auto;"></table>
	</div>
</div>

<!-- datagrid-操作按钮 -->
<div id="rowOperation" style="display:none;">
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="email.addDlog(2, this.id, this.type)">查看</a>
	<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="email.addDlog(1, this.id, this.type)">修改</a>
	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'icon-remove'" onclick="email.deleteById(this.id)">删除</a>
	<a class="easyui-linkbutton ok" data-options="plain:true,iconCls:'icon-ok'" onclick="email.reSendEmail(this.id)">重发</a>
	<br/>
	<a class="easyui-linkbutton ok" data-options="plain:true,iconCls:'icon-ok'" onclick="email.reSendEmailByFailPhone(this.id)">对失败重发</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="email.viewDetail(this.id)">邮件详情</a>
	<a class="easyui-linkbutton view" data-options="plain:true,iconCls:'icon-view'" onclick="email.preview(this.id)">邮件预览</a>
</div>

<!-- 上传文件框 -->
<div id ="uploadDiv"></div>

<!-- 即时发送邮件-新增框 -->
<div id="addDlogToolbar">
	<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="email.save();">发送邮件</a>
	<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="email.cancel();">取消</a>
</div>
<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:30, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm" class="commonForm" method="post">
		<input type="hidden" name="emailId" id="emailId" />
		<input type="hidden" name="sendType" value="sendNow" />
		<table class="commonTable">
			<tr>
				<th>邮件标题<span class="spanRed">*</span></th>
				<td>
					<input class="easyui-validatebox" type="text" name="title" data-options="required:true, validType:'length[0,500]'" style="width: 800px;"/>
				</td>
			</tr>
			<tr>
				<th>邮件内容<span class="spanRed">*</span></th>
				<td>
					<textarea id="content" name="content" style="width: 800px; height: 300px;"></textarea>
				</td>
			</tr>
			<tr>
				<th>接收人<span class="spanRed">*</span></th>
				<td>
					<div id="sourceTypeTabs" class="easyui-tabs" style="width:800px;height:260px">
				        <div title="手动输入或上传邮箱" data-options="" style="padding:10px">
							<textarea name="emails" id="emails"
							 class="textarea easyui-validatebox"
							 data-options="required:true, validType:'length[0,600000]'"
							 style="width: 782px; height: 100px;"></textarea>
							<div>
								<a class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:'true'" href="javascript:upload.uploadFile('<%=basePath%>UploadController/emailUploadText/email/callback', 'uploadDiv', '1', '上传文本文件');">上传文本文件</a>
							</div>
							<div class="tipText">
								1、多个邮箱逗号隔开；<br/>
								2、每次上传将覆盖之前邮箱；<br/>
								3、每次发送不能超过5万个邮箱；超过可分开多次发送；<br/>
							</div>
				        </div>
				        <div title="用户筛选邮箱" data-options="" style="padding:10px">
							<div style="height: 200px; max-height: 200px; overflow: auto;">
								<c:if test="${not empty dasUserScreenList}">
									<c:forEach items="${dasUserScreenList}" var="item">
										<div>
											<input type="checkbox" value="${item.screenId}" name="userScreenId" onclick="email.checkboxClick(this)"/>
											${item.screenName}
										</div>
									</c:forEach>
								</c:if>
							</div>
				        </div>
				        <div title="用户分组邮箱" data-options="" style="padding:10px">
							<div style="height: 200px; max-height: 200px; overflow: auto;">
								<c:if test="${not empty userGroupList}">
									<c:forEach items="${userGroupList}" var="item">
										<div>
											<input type="checkbox" value="${item.groupId}" name="userGroupId" onclick="email.checkboxClick(this)"/>
											${item.name}
										</div>
									</c:forEach>
								</c:if>
							</div>
				        </div>
				    </div>
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- 定时发送邮件-新增框 -->
<div id="addDlogToolbar_1">
	<a id="addDlogOk_1" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-ok'" onclick="email.save_1();">确定</a>
	<a id="addDlogCancel_1" href="javascript:void(0);" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" onclick="email.cancel_1();">取消</a>
</div>
<div id="addDlog_1" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar_1', top:10, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
	<form id="addDlogForm_1" class="commonForm" method="post">
		<input type="hidden" name="emailId" id="emailId_1" />
		<input type="hidden" name="sendType" value="setTime" />
		<table class="commonTable">
			<tr>
				<th>定时开关<span class="spanRed">*</span></th>
				<td>
					<select name="timeSwitch" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
						<option value="Y" selected>开启</option>
						<option value="N">关闭</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>发送时间<span class="spanRed">*</span></th>
				<td>
					<div id="timeDivBox" style="height: 78px; max-height: 78px; overflow: auto;">
						<div>
							日期：<input class="easyui-datebox" type="text" name="startDateList" data-options="required:true, editable:false" style="width: 100px;"/>
							至
							<input class="easyui-datebox" type="text" name="endDateList" data-options="required:true, editable:false" style="width: 100px;"/>
							小时：<input class="easyui-numberspinner" type="text" name="hourList" data-options="required:true, min:0, max:23" style="width: 60px;"/>
							分钟：<input class="easyui-numberspinner" type="text" name="minuteList" data-options="required:true, min:0, max:60" style="width: 60px;"/>
						</div>
					</div>
					<span>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="email.addTimeRow();"
							data-options="iconCls:'icon-add', plain:'true'">新增时间</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="email.removeTimeRow();"
							data-options="iconCls:'icon-remove', plain:'true'">移除</a>
					</span>
				</td>
			</tr>
			<tr>
				<th>邮件标题<span class="spanRed">*</span></th>
				<td>
					<input class="easyui-validatebox" type="text" name="title" data-options="required:true, validType:'length[0,500]'" style="width: 800px;"/>
				</td>
			</tr>
			<tr>
				<th>邮件内容<span class="spanRed">*</span></th>
				<td>
					<textarea id="content_1" name="content" style="width: 800px; height: 220px;"></textarea>
				</td>
			</tr>
			<tr>
				<th>接收人<span class="spanRed">*</span></th>
				<td>
					<div id="sourceTypeTabs_1" class="easyui-tabs" style="width:800px;height:260px">
				        <div title="手动输入或上传邮箱" data-options="" style="padding:10px">
							<textarea name="emails" id="emails_1"
							 class="textarea easyui-validatebox"
							 data-options="required:true, validType:'length[0,600000]'"
							 style="width: 782px; height: 100px;"></textarea>
							<div>
								<a class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:'true'" href="javascript:upload.uploadFile('<%=basePath%>UploadController/emailUploadText/email/callback_1', 'uploadDiv', '2', '上传文本文件');">上传文本文件</a>
							</div>
							<div class="tipText">
								1、多个邮箱逗号隔开；<br/>
								2、每次上传将覆盖之前邮箱；<br/>
								3、每次发送不能超过5万个邮箱；超过可分开多次发送；<br/>
							</div>
				        </div>
				        <div title="用户筛选邮箱" data-options="" style="padding:10px">
							<div style="height: 200px; max-height: 200px; overflow: auto;">
								<c:if test="${not empty dasUserScreenList}">
									<c:forEach items="${dasUserScreenList}" var="item">
										<div>
											<input type="checkbox" value="${item.screenId}" name="userScreenId" onclick="email.checkboxClick(this)"/>
											${item.screenName}
										</div>
									</c:forEach>
								</c:if>
							</div>
				        </div>
				        <div title="用户分组邮箱" data-options="" style="padding:10px">
							<div style="height: 200px; max-height: 200px; overflow: auto;">
								<c:if test="${not empty userGroupList}">
									<c:forEach items="${userGroupList}" var="item">
										<div>
											<input type="checkbox" value="${item.groupId}" name="userGroupId" onclick="email.checkboxClick(this)"/>
											${item.name}
										</div>
									</c:forEach>
								</c:if>
							</div>
				        </div>
				    </div>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
