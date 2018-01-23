$(function() {
	// 绑定输入框相关事件
	inputBindInit();

	// 点击验证码时，刷新验证码
	resCaptchaimg();
	
	// 判断是否是最顶层页面
	isTop();
	
	// 判断是否Chrome浏览器
	if(!isChrome()){
		$("#browserAlert").text("请使用Chrome浏览器 ! 其他浏览器会导致部份功能失效，无法正常操作。");
	}
	
	// 判断是否IE浏览器
//	if(isIe()){
//		$("#browserAlert").text("不要使用IE浏览器，IE浏览器会导致部份功能失效，无法正常操作。");
//	}
	
});

// 回车触发
function enterkey(){
	var e = e||event;
	if(e.keyCode==13){
    	login();
        return false;
    }
}

// 判断是否是最顶层页面
function isTop(){
	if (window != top){
		top.location.href = location.href;
	}
}

// 绑定输入框相关事件
function inputBindInit(){
	$("input").bind({
		focus: function(){
			var $self = $(this);
			// 不延时，可能选取不上，浏览器有默认行为
			setTimeout(function(){
				$self.select();
			}, 100)
			$self.parent().addClass("active");
		},
		blur: function(){
			$(this).parent().removeClass("active");
		}
	});
}

// 点击验证码时，刷新验证码
function resCaptchaimg(){
	$("#captchaimg").bind("click", function(){
		$("#captchaimg").attr("src", BASE_PATH + "LoginController/captcha?data=" + new Date().getTime());
	});
}

// 登陆提交
function login(){
	$("#loading").show();
	$("#errorMsg").text("");
	$.ajax({
		type : "post",
		dataType : "json",
		url: BASE_PATH + "LoginController/login",
		data : $('#form_login').serialize(),
		beforeSend: function(XMLHttpRequest){
	    },
		success : function(data) {
			$("#loading").hide();
			var resultCode = data.resultCode;
			var resultMsg = data.resultMsg;
			// 操作成功
			if(resultCode == 'success'){
				window.location.href = BASE_PATH + "LoginController/home";
			}else if(resultCode == 'exception'){
				$("#errorMsg").text(resultMsg);
				$("#captchaimg").click();
			}else{
				// 提示错误信息
				var errorInfoList = data.errorInfoList;
				var errorMsg = "";
				for(var i=0; i<errorInfoList.length; i++){
					errorMsg += errorInfoList[0].errorMsg + "；";
				}
				$("#errorMsg").text(errorMsg);
				$("#captchaimg").click();
			}
		}
	});
}

// 判断是否Chrome浏览器
function isChrome() {
    var ua = window.navigator.userAgent;
    if (ua.indexOf("Chrome") > -1){  
       return true;
    }else{
        return false;
    }
}

//判断是否IE浏览器
function isIe() {
	if (window.addEventListener) {
		return false;
	} else if (window.attachEvent) {
		return true;
	} else {
		// 这种情况发生在不支持DHTML的老版本浏览器（现在一般都支持）
		return true;
	}
}