$(function() {
	common.init();
});

var common = {
	sessionTimeOut : "sessionTimeOut",
	noPermission : "noPermission",
	exceptionCode : "exception",
	successCode: {
		success:  "操作成功",
		addSuccess:  "新增成功",
		saveSuccess:  "保存成功",
		updateSuccess:  "更新成功",
		deleteSuccess:  "删除成功"
	},
	divMask : "<div class=\"datagrid-mask\" style=\"display:block\"></div>",
	divMaskMsg : "<div class=\"datagrid-mask-msg\" style=\"display: block; left: 50%; height: 16px; margin-left: -85.5px; line-height: 16px;\">正在处理，请稍待。。。</div>",	
	/**
	 * 参数初始化
	 */
	init: function(){
		common.setEasyUiCss();
		// 全局的AJAX访问,处理AJAX清求时SESSION超时
		common.ajaxSetup();
	},
	/**
	 * easyui css
	 */
	setEasyUiCss: function(){
		// easyui numberbox 样式修改
		$("input.textbox-text").css('border','0px');
		$("input.textbox-text").css('height','22px');
		$("span.textbox").css('height','22px');
		$("a.textbox-icon.combo-arrow").css('height','22px');
		$("a.textbox-icon.spinner-arrow").css('height','22px');
	},
	/**
	 * 添加loading效果
	 * parentObj需要加到的节点
	 * flag addOrRemove
	 */
	addOrRemoveLoading: function(parentObjId, flag){
		if(flag){
    		// 追加
    		$("#" + parentObjId).append(common.divMask);
    		$("#" + parentObjId).append(common.divMaskMsg);
		}else{
    		// 移除
    		$("#" + parentObjId + " div.datagrid-mask").remove();
    		$("#" + parentObjId + " div.datagrid-mask-msg").remove();
		}
	},
	/**
	 * 全局的AJAX访问,处理AJAX清求时 SESSION超时或无权限跳转
	 */
	ajaxSetup : function(){
		$.ajaxSetup({
		    contentType: "application/x-www-form-urlencoded;charset=utf-8",
		    beforeSend: function(XMLHttpRequest){
		    	// 表格加载消息框
		    	var datagridMsg = $("div.datagrid-mask-msg");
		    	if(null != datagridMsg){
			    	if(!datagridMsg.is(":visible")){
			    		 $.messager.progress();
			    	}
		    	}else{
					$.messager.progress();
		    	}
		    },
		    complete:function(XMLHttpRequest, textStatus){
				$.messager.progress('close');
		    	//通过XMLHttpRequest取得响应头
		    	var sessionTimeOut = XMLHttpRequest.getResponseHeader(common.sessionTimeOut);
		    	var noPermission = XMLHttpRequest.getResponseHeader(common.noPermission);
				if(sessionTimeOut == common.sessionTimeOut){
					// session超时-跳转登录页面
					window.location.href = BASE_PATH + "page/common/sessionTimeOut.jsp";
				}else if(noPermission == common.noPermission){
					// 无权限
					$.messager.alert('提示', "提醒：用户权限不足，请联系管理员!", 'error');
					// window.location.href = BASE_PATH + "page/common/noAuth.jsp";
				}else{
					if(textStatus != 'success'){
						$.messager.alert("出错提示", "服务器响应超时,请联系系统管理员！", "error");
					}
				}
			}
		});
	},
	/**
	 * reload表格,页码不变
	 * @param gridId
	 */
	reloadGrid: function(gridId) {
		$("#" + gridId).datagrid('unselectAll');
		$("#" + gridId).datagrid('uncheckAll');
		$("#" + gridId).datagrid('reload');
	},
	/**
	 * load表格,页码为1
	 * @param gridId
	 */
	loadGrid: function(gridId) {
		$("#" + gridId).datagrid('unselectAll');
		$("#" + gridId).datagrid('uncheckAll');
		$("#" + gridId).datagrid('load');
	},
	/**
	 * 移除验证消息框
	 * @param formId
	 */
	removeValidatebox: function() {
		//解决弹出窗口关闭后，验证消息还显示在最上面
        $('.validatebox-tip').remove();
	},
	/**
	 * 提交表单验证
	 * @param formId
	 */
	submitFormValidate: function(formId) {
		$.messager.progress();
		var isValid = $("#" + formId).form('validate');
		if (!isValid) {
			$.messager.progress('close');
		}
		return isValid;
	},
	/**
	 * ajax请求,判断是否成功
	 */
	isSuccess: function(data){
		// 表格加载消息框
		var datagridMsg = $("div.datagrid-mask-msg");
    	if(null != datagridMsg){
	    	if(!datagridMsg.is(":visible")){
	    		$.messager.progress('close');
	    	}
    	}
		
		var resultCode = data.resultCode;
		var resultMsg = data.resultMsg;
		
		// 操作成功
		for(var key in common.successCode) {
			if(resultCode == key){
				$.messager.show({
					title : '成功提示',
					msg : resultMsg,
					timeout: 1000,
//					showType : 'show'
					showType:'slide',
	                style:{
	                    right:'',
	                    top: 5,
	                    bottom:''
	                }
				});
				return true;
			}
		}
		
		// 系统异常
		if(resultCode == common.exceptionCode){
			$.messager.alert('提示', resultMsg, 'error');
			return false;
		}

		// 提示错误信息
		var errorInfoList = data.errorInfoList;
		var errorMsg = "";
		for(var i=0; i<errorInfoList.length; i++){
			errorMsg += errorInfoList[i].errorMsg + "；";
		}
		$.messager.alert('提示', errorMsg, 'error');
		return false;
	},
	/**
	 * ajax请求,提示error信息
	 */
	error: function(data){
	},
	isBlank: function(v) {
	    return v == undefined || v == null || $.trim(v) == '' || v == 'undefined' || v == 'null' || v == 'NULL';
	},
	isValid: function(obj) {
	    return !common.isBlank(obj);
	},
	validObj: function(obj) {
	    return (common.isValid(obj) && !$.isEmptyObject(obj));
	},
	/**
	 * 参数组合
	 */
	getParams: function(params){
	    if (params == null) {
	        return null;
	    }
	    var result = "";
	    for (var item in params) {
	        if (result.length > 0) {
	            result += "&";
	        }
	        if (params[item] == null || params[item] == undefined) {
	            result += (item + "=");
	        } else result += (item + "=" + params[item]);
	    }
	    result = encodeURI(result);
	    return result;
	},
	/**
	 * 格式url
	 */
	formatUrl: function(url, params){
		if(common.validObj(params)){
			url += "&" + common.getParams(params);
		}
		return url;
	},
	/**
	 * 导出记录数判断
	 */
	exportExcelValidate : function(datagridId){
		var options = $("#"+datagridId).datagrid("getPager").data("pagination").options;
		var total = options.total;
		if(total > 300000){
			$.messager.alert("提示","导出记录数不能超过30万条",'info');
			return false;
		}
		return true;
	},
	/**
	 * 计算当前日期在本月份的周数
	 */
	getWeekNumber : function (date) { 
		/*  a = d = 当前日期  
		    b = 6 - w = 当前周的还有几天过完(不算今天) 
		    a + b 的和在除以7 就是当天是当前月份的第几周 
		 */ 
		var a = date.getYear(); 
		var b = date.getMonth()+1; 
		var c = date.getDate(); 
		var date = new Date(a, parseInt(b) - 1, c), w = date.getDay(), d = date.getDate(); 
		return Math.ceil((d + 6 - w) / 7);  
	},
	/**
	 * 判断是否IE浏览器
	 */
	isIe : function() {
		if (window.addEventListener) {
			return false;
		} else if (window.attachEvent) {
			return true;
		} else {
			// 这种情况发生在不支持DHTML的老版本浏览器（现在一般都支持）
			return true;
		}
	},
	/**
	 * 显示/隐藏更多条件
	 */
	showSearchCon : function() {
		var obj = $(".more");
		if(obj.is(":hidden")){
			obj.show();
		}else{
			obj.hide();
		}
		
		// 需重新设置iframe的高度
		common.iFrameHeight();
	},
	/**
	 * 自适应iframe的高度
	 */
	iFrameHeight: function(){
		var parentIndex = parent.index;
		if(parentIndex){
			parentIndex.iFrameHeight();
		}
	},
	/**
	 * 保留两位小数 功能：将浮点数四舍五入，取小数点后2位
	 * 
	 * @param x
	 * @returns
	 */
	toDecimal : function(x) {
		var f = parseFloat(x);
		if (isNaN(f)) {
			return;
		}
		f = Math.round(x * 100) / 100;
		return f;
	},
	
	browserType : function(u_agent){
		var browser_name = "";
		if(!common.isBlank(u_agent)){
			if(u_agent.indexOf('Firefox')>-1){   
				browser_name='Firefox';   
			}else if(u_agent.indexOf('Chrome')>-1){   
				browser_name='Chrome';   
			}else if(u_agent.indexOf('Trident')>-1){   
				browser_name='IE';   
			}else if(u_agent.indexOf('Opera')>-1){   
				browser_name='Opera';   
			}else if(u_agent.indexOf('Android') > -1 || u_agent.indexOf('Linux') > -1){
				browser_name='android'; 
			}else if(u_agent.indexOf('iPhone') > -1 || u_agent.indexOf('Mac') > -1){
				browser_name='iPhone'; 
			}else if(u_agent.indexOf('iPad') > -1){
				browser_name='iPad'; 
			}else if(u_agent.indexOf('Safari') == -1){
				browser_name='webApp'; 
			}
		}
       return browser_name;
	},
	/**
	 * 数值为负数时,并且数值千分位表示,div调用，红色标记
	 * objId:对象ID
	 * value:数值
	 */
	isAppendColor : function(objId, value){
		if(common.isBlank(value)){
			return "";
		}
		if(value < 0){
			$("#"+objId).css("color", "red");
		}else{
			$("#"+objId).css("color", "");
		}
		value = common.fmoney(value);
		$("#"+objId).html(value);
	},
	/**
	 * 数值为负数时,easyui 表格调用，红色标记
	 * value:数值
	 */
	isAppendColor2 : function(value){
		if(common.isBlank(value)){
			return "";
		}
		if(value < 0){
			value = common.fmoney(value);
			return "<span style=\"color:red\">"+value+"</span>";
		}else{
			value = common.fmoney(value);
			return value;
		}
	},
	/**
	 * 数值为负数时,easyui 表格调用，红色标记
	 * value:数值*-1
	 */
	isAppendColor3 : function(value){
		if(common.isBlank(value)){
			return "";
		}
		value = parseFloat(value) * -1;
		if(value < 0){
			value = common.fmoney(value);
			return "<span style=\"color:red\">"+value+"</span>";
		}else{
			value = common.fmoney(value);
			return value;
		}
	},
	/**
	 * 数值千分位函数 s：数值
	 */
	fmoney: function (s){
		var tempStr = s + "";
		if((tempStr).indexOf(".") < 0){
			var re=/(?=(?!(\b))(\d{3})+$)/g;
			tempStr = tempStr.replace(re, ",");
			return tempStr;
		}else{
			var num_1 = tempStr.split('.') [0];
			var num_2 = tempStr.split('.') [1];
			
			var re=/(?=(?!(\b))(\d{3})+$)/g;
			num_1 = num_1.replace(re, ",");
			return num_1 + "." + num_2;
		}
	}
}

