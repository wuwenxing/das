<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%=basePath%>page/trade/riskBlacklist/riskBlacklist.js?version=20170306"></script>
</head>
<body>

<div id="sourceTypeTabs" class="easyui-tabs" style="padding-top: 5px;">
	<div title="风控要素" data-options="" style="padding:10px">
		<div class="common-box-style">
			<div class="divTitle">查询条件</div>
			<div class="divContent">
				<form id="searchForm_1" class="searchForm" action="">
					<table class="commonTable">
						<tr>
						    <th>标记时间</th>
							<td>
								<input class="easyui-datebox" type="text" name="startTimeSearch_1" id="startTimeSearch_1" data-options="editable:false" value="<das:date type="yesterdayStart" format="yyyy-MM-dd"/>"/>
								至
								<input class="easyui-datebox" type="text" name="endTimeSearch_1" id="endTimeSearch_1" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd"/>"/>
							</td>
							<th>类型</th>
						    <td>
							   <select id="typeSearch" name="typeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								  <option value="">---请选择---</option>
								  <das:enumListTag dataList="${dimBlackListTypeEnum}"/>
							   </select>
						    </td>
							<th>类型值</th>
							<td><input type="text" id="valueSearch" name="valueSearch" maxlength="50" /></td>
						</tr>
						<tr>
							<th>风险类型</th>
						    <td>
							   <select id="riskTypeSearch_1" name="riskTypeSearch_1" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								  <option value="">---请选择---</option>
								  <das:enumListTag dataList="${riskTypeEnum}"/>
							   </select>
						    </td>
							<th>来源标记</th>
						    <td>
							   <select id="sourceSearch" name="sourceSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								  <option value="">---请选择---</option>
								  <option value="1">自动</option>
								  <option value="2">手工</option>
								  <option value="3">自动&手工</option>
							   </select>
						    </td>
							<th>备注</th>
							<td><input type="text" id="remarkSearch" name="remarkSearch" maxlength="50" /></td>
						</tr>
						<tr>
							<td colspan="8">
								<div class="searchButton">
									<a href="javascript:void(0);" class="easyui-linkbutton" onclick="riskBlacklist.find_1();"
										data-options="iconCls:'icon-search', plain:'true'">查询</a>
									<a href="javascript:void(0);" class="easyui-linkbutton" onclick="riskBlacklist.reset_1();"
										data-options="iconCls:'icon-empty', plain:'true'">重置</a>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<div class="common-box-style">
			<div id="toolbar_1">
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="riskBlacklist.exportExcel_1();"
					data-options="iconCls:'icon-export', plain:'true'">导出</a>
			</div>
			<div class="divTitle">数据列表</div>
			<div class="divContent">
				<table id="dataGrid_1" data-options="toolbar:'#toolbar_1'" style="width:100%; height:auto; min-height: 120px;"></table>
			</div>
		</div>
	</div>
	<div title="上传风控要素" data-options="" style="padding:10px">
		<div class="common-box-style">
			<div class="divTitle">
				查询条件
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="common.showSearchCon();"
					data-options="iconCls:'icon-searchCon', plain:'true'">更多条件</a>
			</div>
			<div class="divContent">
				<form id="searchForm" class="searchForm" action="">
					<table class="commonTable">
						<tr>
						    <th>风险时间</th>
							<td>
								<input class="easyui-datetimebox" type="text" name="startTimeSearch" id="startTimeSearch" data-options="editable:false" value="<das:date type="yesterdayStart" format="yyyy-MM-dd HH:mm:ss"/>"/>
								至
								<input class="easyui-datetimebox" type="text" name="endTimeSearch" id="endTimeSearch" data-options="editable:false" value="<das:date type="dayEnd" format="yyyy-MM-dd HH:mm:ss"/>"/>
							</td>
							<th class="more">业务权限</th>
						    <td class="more">
							   <select id="companyIdSearch" name="companyIdSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								  <option value="">---请选择---</option>
								  <das:enumListTag dataList="${companyEnum}"/>
							   </select>
						    </td>
						    <th class="more">交易平台</th>
							<td class="more">
							   <select id="platformSearch" name="platformSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								  <option value="">---请选择---</option>
								  <das:enumListTag dataList="${platformtypeEnum}"/>
							   </select>
							</td>
						</tr>
						<tr class="more">
							<th>风险类型</th>
						    <td>
							   <select id="riskTypeSearch" name="riskTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								  <option value="">---请选择---</option>
								  <das:enumListTag dataList="${riskTypeEnum}"/>
							   </select>
						    </td>
						    <th>风险原因</th>
							<td><input type="text" id="riskReasonSearch" name="riskReasonSearch" maxlength="50" /></td>
							<th>风险备注</th>
						    <td><input type="text" id="riskRemarkSearch" name="riskRemarkSearch" maxlength="50" /></td>
						</tr>
						<tr class="more">
						    <th>账号</th>
							<td><input type="text" id="accountNoSearch" name="accountNoSearch" maxlength="50" /></td>
							<th>电话</th>
						    <td><input type="text" id="mobileSearch" name="mobileSearch" maxlength="50" /></td>
						    <th>邮箱</th>
							<td><input type="text" id="emailSearch" name="emailSearch" maxlength="50" /></td>
						</tr>
						<tr class="more">
						    <th>证件号码</th>
							<td><input type="text" id="idCardSearch" name="idCardSearch" maxlength="50" /></td>
							<th>IP</th>
						    <td colspan="3"><input type="text" id="ipSearch" name="ipSearch" maxlength="50" /></td>
						</tr>
						<tr class="more">
							<th>设备类型</th>
						    <td>
							   <select id="deviceTypeSearch" name="deviceTypeSearch" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								  <option value="">---请选择---</option>
								  <das:enumListTag dataList="${deviceTypeEnum}"/>
							   </select>
						    </td>
						    <th>设备信息</th>
							<td colspan="3"><input type="text" id="deviceInfoSearch" name="deviceInfoSearch" maxlength="50" /></td>
						</tr>
						<tr>
							<td colspan="8">
								<div class="searchButton">
									<a href="javascript:void(0);" class="easyui-linkbutton" onclick="riskBlacklist.find();"
										data-options="iconCls:'icon-search', plain:'true'">查询</a>
									<a href="javascript:void(0);" class="easyui-linkbutton" onclick="riskBlacklist.reset();"
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
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="riskBlacklist.addDlog_1();"
					data-options="iconCls:'icon-add', plain:'true'">导入数据</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="riskBlacklist.exportExcel();"
					data-options="iconCls:'icon-export', plain:'true'">导出</a>
			</div>
			<div class="divTitle">数据列表</div>
			<div class="divContent">
				<table id="dataGrid" data-options="toolbar:'#toolbar'" style="width:100%; height:auto; min-height: 120px;"></table>
			</div>
		</div>
		
		<!-- datagrid-操作按钮 -->
		<div id="rowOperation" style="display:none;">
			<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'icon-edit'" onclick="riskBlacklist.addDlog(1, this.id)">修改</a>
		</div>
		<!-- 新增框 -->
		<div id="addDlogToolbar">
			<a id="addDlogOk" href="javascript:void(0);" class="easyui-linkbutton"
				data-options="iconCls:'icon-ok'" onclick="riskBlacklist.save();">确定</a>
			<a id="addDlogCancel" href="javascript:void(0);" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'" onclick="riskBlacklist.cancel();">取消</a>
		</div>
		<div id="addDlog" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar', top:80, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
			<form id="addDlogForm" class="commonForm" method="post">
				<input type="hidden" name="riskBlacklistId" id="riskBlacklistId">
				<table class="commonTable">
					<tr>
						<th>事业部</th>
						<td>
							<select id="companyId" name="companyId" class="easyui-combobox" data-options="required:false, panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${companyEnum}"/>
							</select>
						</td>
						<th>风险类型<span class="spanRed">*</span></th>
						<td>
							<select id="riskType" name="riskType" class="easyui-combobox" data-options="required:true, panelHeight:'auto', editable:false">
								<das:enumListTag dataList="${riskTypeEnum}"/>
							</select>
						</td>
					</tr>
					<tr>
						<th>风险原因<span class="spanRed">*</span></th>
						<td>
							<input class="easyui-validatebox" type="text" name="riskReason" id="riskReason" data-options="required:true, validType:'length[0,50]'" />
						</td>
						<th>风险时间<span class="spanRed">*</span></th>
						<td>
							<input class="easyui-datetimebox" type="text" name="riskTime" id="riskTime" data-options="required:true, editable:false" />
						</td>
					</tr>
					<tr>
						<th>风险备注</th>
						<td colspan="3">
							<textarea id="riskRemark" name="riskRemark" style="width: 435px; height: 80px;" data-options="required:false, validType:'length[0,500]'"></textarea>
						</td>
					</tr>
					<tr>
						<th>平台</th>
						<td>
							<select id="platform" name="platform" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${platformtypeEnum}"/>
							</select>
						</td>
						<th>账户</th>
						<td>
							<input class="easyui-validatebox" type="text" name="accountNo" id="accountNo" data-options="required:false, validType:'length[0,50]'" />
						</td>
					</tr>
					<tr>
						<th>电话</th>
						<td>
							<input class="easyui-validatebox" type="text" name="mobile" id="mobile" data-options="required:false, validType:'length[0,50]'" />
						</td>
						<th>邮箱</th>
						<td>
							<input class="easyui-validatebox" type="text" name="email" id="email" data-options="required:false, validType:'length[0,50]'" />
						</td>
					</tr>
					<tr>
						<th>证件号码</th>
						<td>
							<input class="easyui-validatebox" type="text" name="idCard" id="idCard" data-options="required:false, validType:'length[0,50]'" />
						</td>
						<th>IP</th>
						<td>
							<input class="easyui-validatebox" type="text" name="ip" id="ip" data-options="required:false, validType:'length[0,50]'" />
						</td>
					</tr>
					<tr>
						<th>设备类型</th>
						<td>
							<select id="deviceType" name="deviceType" class="easyui-combobox" data-options="panelHeight:'auto', editable:false">
								<option value="">---请选择---</option>
								<das:enumListTag dataList="${deviceTypeEnum}"/>
							</select>
						</td>
						<th>设备信息</th>
						<td colspan="3">
							<input class="easyui-validatebox" type="text" name="deviceInfo" id="deviceInfo" data-options="required:false, validType:'length[0,50]'" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<!-- 上传文件框 -->
		<div id ="uploadDiv"></div>

		<!-- 导入Excel数据-新增框 -->
		<div id="addDlogToolbar_1">
			<a id="addDlogCancel_1" href="javascript:void(0);" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'" onclick="riskBlacklist.cancel_1();">关闭</a>
		</div>
		<div id="addDlog_1" class="easyui-dialog" data-options="closed:'true', buttons:'#addDlogToolbar_1', top:50, modal:true, bgiframe:true, resizable:true, onClose: function(){common.removeValidatebox();}">
			<form id="addDlogForm_1" class="commonForm" method="post">
				<table class="commonTable">
					<tr>
						<th style="width: 120px;">导入数据<span class="spanRed">*</span></th>
						<td>
							<div>
								<a class="easyui-linkbutton" data-options="iconCls:'icon-upload', plain:'true'" href="javascript:riskBlacklist.submitExcel();">上传Excel文件</a>
							</div>
							<div class="tipText">
								1、Excel第一行标题分别为（事业部、风险类型、风险原因、风险时间、平台、<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号、邮箱、证件号码、IP、设备类型、设备信息）；<br/>
								2、Excel第二行开始为需要导入的数据，按模板要求依次填入；<br/>
								3、请注意账户列必须为文本格式，否则会变为数值，改为文本格式的方法：<br/>
								（若内容不多，可以通过逐行双击回车的办法实现。）；<br/>
								（若内容较多，选定该列数据，点菜单“数据”——“分列”，连续按两次“下一步”，出现“列数据格式”，点中“文本”，完成。）；<br/>
								4、当上传数据中的行和已有数据重复时，会自动进行去重处理，并刷新“更新时间”；<br/>
								5、上传账号时，会自动匹配账号的电话、邮箱、身份证、等信息，这些信息会被标记同类风险；<br/>
								6、每天早上6点生效，如果需要紧急更新，请联系BI团队处理；<br/>
								7、点此<a href="<%=basePath%>template/trade/riskBlacklistTemplate.xlsx">下载</a>模板；<br/>
							</div>
						</td>
					</tr>
					<tr>
						<th>导入后提示信息</th>
						<td>
							<textarea id="msg" name="msg"
							 class="textarea easyui-validatebox"
							 data-options="required:false"
							 style="width: 650px; height: 100px;"></textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
	
</div>

</body>
</html>
