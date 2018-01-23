/**
 * 上传图片公共js
 */
$(function() {
	upload.init();
});

var upload = {

	maxSize : 1024 * 1024,//限制上传图片大小1024kb,即1M 
	uploadDlogToolbar : "uploadDlogToolbar_",
	uploadDlogOk : "uploadDlogOk_",
	uploadDlogCancel : "uploadDlogCancel_",
	uploadDlog : "uploadDlog_",
	uploadDlogForm : "uploadDlogForm_",
	uploadSubmitBtn : "uploadSubmitBtn_",
	uploadIframe : "uploadIframe_",
	/**
	 * 初始化
	 */
	init:function(){
		
	},
	/**
	 * 验证
	 */
	validate:function(){
//		var fileInput = $("#fileName")[0];
//		var byteSize  = fileInput.files[0].fileSize;
//		if(byteSize > upload.maxSize){
//			$.messager.alert('提示', '上传文件不能超过1M', 'error');
//			return false;
//		}
		return true;
	},
	/**
	 * 通过iframe来实现无刷新的的文件上传，其实是有刷新的，只是在iframe里面隐藏了而已
	 * form里面的target要与iframe里面的id的值相等，指示是form相应了post事件，也就是post时间相应的时候刷新的是iframe而不是整个页面。
	 * @param action 上传form中action地址
	 * @param uploadDivId 弹出框外层DIV的ID
	 * @param dlogIdNo 弹出框ID序号，用于区分多个dlog
	 * @param title 弹出框标题
	 */
	uploadExcelFile : function (action, uploadDivId, dlogIdNo, title){
		var accept = ".xlsx";
		upload.uploadPub(action, uploadDivId, dlogIdNo, title, accept);
	},
	/**
	 * 通过iframe来实现无刷新的的文件上传，其实是有刷新的，只是在iframe里面隐藏了而已
	 * form里面的target要与iframe里面的id的值相等，指示是form相应了post事件，也就是post时间相应的时候刷新的是iframe而不是整个页面。
	 * @param action 上传form中action地址
	 * @param uploadDivId 弹出框外层DIV的ID
	 * @param dlogIdNo 弹出框ID序号，用于区分多个dlog
	 * @param title 弹出框标题
	 */
	uploadFile : function (action, uploadDivId, dlogIdNo, title){
		var accept = ".text,.txt";
		upload.uploadPub(action, uploadDivId, dlogIdNo, title, accept);
	},
	/**
	 * 通过iframe来实现无刷新的的文件上传，其实是有刷新的，只是在iframe里面隐藏了而已
	 * form里面的target要与iframe里面的id的值相等，指示是form相应了post事件，也就是post时间相应的时候刷新的是iframe而不是整个页面。
	 * @param action 上传form中action地址
	 * @param uploadDivId 弹出框外层DIV的ID
	 * @param dlogIdNo 弹出框ID序号，用于区分多个dlog
	 * @param title 弹出框标题
	 */
	uploadImg : function (action, uploadDivId, dlogIdNo, title){
		var accept = ".jpg,.jpeg,.bmp,.gif,.png";
		upload.uploadPub(action, uploadDivId, dlogIdNo, title, accept);
	},
	/**
	 * 通过iframe来实现无刷新的的文件上传，其实是有刷新的，只是在iframe里面隐藏了而已
	 * form里面的target要与iframe里面的id的值相等，指示是form相应了post事件，也就是post时间相应的时候刷新的是iframe而不是整个页面。
	 * @param action 上传form中action地址
	 * @param uploadDivId 弹出框外层DIV的ID
	 * @param dlogIdNo 弹出框ID序号，用于区分多个dlog
	 * @param title 弹出框标题
	 * @param accept 文件后缀
	 */
	uploadPub : function (action, uploadDivId, dlogIdNo, title, accept){
		upload.uploadDlogToolbar = "uploadDlogToolbar_" + dlogIdNo;
		upload.uploadDlogOk = "uploadDlogOk_" + dlogIdNo;
		upload.uploadDlogCancel = "uploadDlogCancel_" + dlogIdNo;
		upload.uploadDlog = "uploadDlog_" + dlogIdNo;
		upload.uploadDlogForm = "uploadDlogForm_" + dlogIdNo;
		upload.uploadSubmitBtn = "uploadSubmitBtn_" + dlogIdNo;
		upload.uploadIframe = "uploadIframe_" + dlogIdNo;
		
		var content = 
			"<div id=\""+upload.uploadDlogToolbar+"\">" 
			+ " <a id=\""+upload.uploadDlogOk+"\" href=\"javascript:void(0);\" class=\"easyui-linkbutton\""
			+ "		data-options=\"iconCls:'icon-upload'\" onclick=\"javacript:upload.uploadFileSubmit();\">上传</a>"
			+ "	<a id=\""+upload.uploadDlogCancel+"\" href=\"javascript:void(0);\" class=\"easyui-linkbutton\""
			+ "		data-options=\"iconCls:'icon-cancel'\" onclick=\"javacript:upload.uploadFileCancel();\">取消</a>"
			+ "</div>"
			+ "<div id=\""+upload.uploadDlog+"\">"
			+ "	<form id=\""+upload.uploadDlogForm+"\" class=\"commonForm\" method=\"post\" enctype=\"multipart/form-data\" action=\""+action+"\" target=\""+upload.uploadIframe+"\" >"
			+ " <iframe name=\""+upload.uploadIframe+"\" id=\""+upload.uploadIframe+"\" style=\"display:none\" src=\"\" ></iframe>"
			+ "		<table class=\"commonTable\">"
			+ "			<tr>"
			+ "				<th>请选择文件<span class=\"spanRed\">*</span></th>"
			+ "				<td>"
			+ "					<input type=\"file\" id=\"fileName\" name=\"fileName\" accept=\""+accept+"\"/>"
			+ "				</td>"
			+ "			</tr>"
			+ "			<tr>"
			+ "				<td colspan=\"2\">"
			+ "					<div class=\"tipText\">1、文件大小不能超过5M；<br></div>"
			+ "				</td>"
			+ "			</tr>"
			+ "		</table>"
			+ "		<div style=\"display: none;\">"
			+ "			<input type=\"submit\" id=\""+upload.uploadSubmitBtn+"\" value=\"上传 \" />"
			+ "		</div>"
			+ "	</form>"
			+ "</div>";
			
		// 每次上传文件将移除Dlog内容
		$("#" + upload.uploadDlogToolbar).remove();
		$("#" + upload.uploadDlog).remove();
		// 填充HTML到div
		$('#'+uploadDivId).append(content);
		// 为a标签添加easyui样式
		$('#'+upload.uploadDlogOk).linkbutton({
		    iconCls: 'icon-upload'
		});
		$('#'+upload.uploadDlogCancel).linkbutton({
		    iconCls: 'icon-cancel'
		});
		// 创建dialog
		$('#'+upload.uploadDlog).dialog({
			title: title,
			buttons: "#" + upload.uploadDlogToolbar,
		    modal: true,
		    bgiframe : true
		});
		$("#"+upload.uploadDlog).dialog('open').dialog('setTitle', title);
	},
	/**
	 * 提交上传文件
	 * @param dlogFormId
	 */
	uploadFileSubmit: function (dlogId){
		// 验证
		if(!upload.validate()){
			return false;
		}
		
		// 进度条
		$.messager.progress();
		
		// 上传文件校验
		
		// 关闭窗口
		$("#"+upload.uploadDlog).dialog('close');
		$("#"+upload.uploadSubmitBtn).click();
	},
	/**
	 * 取消上传文件
	 * @param dlogFormId
	 */
	uploadFileCancel: function (){
		$("#"+upload.uploadDlog).dialog('close');
	}
}


