window.onload = function() {
	var pageType = $("#pageType").val();
	if (pageType == "home") {
		$("#nav_home").addClass("li-on");
		$("#nav_statistics").removeClass("li-on");
		$("#nav_system").removeClass("li-on");
	}
	if (pageType == "index") {
		$("#nav_home").removeClass("li-on");
		$("#nav_statistics").addClass("li-on");
		$("#nav_system").removeClass("li-on");
	}
	if (pageType == "system") {
		$("#nav_home").removeClass("li-on");
		$("#nav_statistics").removeClass("li-on");
		$("#nav_system").addClass("li-on");
	}
};

$(function() {
	$(".nav li").click(function() {
		// $(this).addClass("li-on").siblings().removeClass("li-on");
	})
	topObj.init();
});

var topObj = {
	/**
	 * 初始化
	 */
	init : function() {
		// 业务权限change事件
		$("#companyId").combobox({
			onChange : function(newValue, oldValue) {
				topObj.changeCompanyId(newValue, oldValue);
			}
		});
	},
	/**
	 * 跳转新地址
	 */
	linkUrl : function(menuUrl, menuCode) {
		if('CboardLoginController/login' == menuUrl){
			window.open(BASE_PATH + menuUrl);
			return;
		}if('Cboard/login' == menuUrl){
			window.open($("#cboardLinkUrl").val());
			return;
		}else{
			window.location.href = BASE_PATH + menuUrl;
		}
	},
	/**
	 * 修改密码
	 */
	changePassword : function() {
		$("#changePasswordDlog").css("display", "");
		$("#changePasswordDlogForm").form('reset');
		$("#changePasswordDlog").dialog('open').dialog('setTitle', '修改密码');
	},
	/**
	 * 修改密码-保存
	 */
	changePasswordSave : function() {
		if (!common.submitFormValidate("changePasswordDlogForm")) {
			return false;
		}
		if ($("#newPassword").val() != $("#newPasswordAgin").val()) {
			$.messager.alert('提示', '新密码两次输入不一次', 'error');
			$.messager.progress('close');
			return false;
		}
		if ($("#newPassword").val() == $("#oldPassword").val()) {
			$.messager.alert('提示', '新密码与原密码相同', 'error');
			$.messager.progress('close');
			return false;
		}

		$.ajax({
			type : "post",
			dataType : "json",
			url : BASE_PATH + "LoginController/updatePassword",
			data : $("#changePasswordDlogForm").serialize(),
			success : function(data) {
				if (common.isSuccess(data)) {
					$("#changePasswordDlog").dialog('close');
				}
			},
			error : function(data) {
				common.error();
			}
		});
	},
	/**
	 * 关于
	 */
	about : function() {
		$("#aboutDlog").css("display", "");
		$("#aboutDlog").dialog('open').dialog('setTitle', '关于');
	},
	/**
	 * 问题反馈
	 */
	problem : function() {
		$("#problemDlog").css("display", "");
		$("#problemDlog").dialog('open').dialog('setTitle', '问题反馈');
	},
	/**
	 * 帮助
	 */
	help : function() {
		$("#helpDlog").css("display", "");
		$("#helpDlog").dialog('open').dialog('setTitle', '帮助');
	},
	/**
	 * 退出
	 */
	loginOut : function() {
		window.location.href = BASE_PATH + "LoginController/loginOut";
	},
	/**
	 * 业务权限改变事件
	 */
	changeCompanyId : function(newValue, oldValue) {
		$.ajax({
			type : "post",
			dataType : "json",
			url : BASE_PATH + "LoginController/changeCompanyId",
			data : {
				companyId : newValue
			},
			success : function(data) {
				var resultCode = data.resultCode;
				var resultMsg = data.resultMsg;
				if (resultCode == 'success') {
					var pageType = $("#pageType").val();
					if (pageType == "home") {
						window.location.href = BASE_PATH
								+ "LoginController/home";
					} else if (pageType == "index") {
						window.location.href = BASE_PATH
								+ "LoginController/index";
					} else if (pageType == "system") {
						window.location.href = BASE_PATH
								+ "LoginController/system";
					}
					// index.removeMenuAccordion();
					// index.loadMenuAccordion();
				} else {
					// 系统异常
					if (resultCode == common.exceptionCode) {
						$.messager.alert('提示', resultMsg, 'error');
					} else {
						// 提示错误信息
						var errorInfoList = data.errorInfoList;
						var errorMsg = "";
						for (var i = 0; i < errorInfoList.length; i++) {
							errorMsg += errorInfoList[0].errorMsg + "；";
						}
						$.messager.alert('提示', errorMsg, 'error');
					}
				}
			},
			error : function(data) {
				common.error();
			}
		});
	}

}
