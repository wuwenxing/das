$(function() {
	smsConfig.init();
});

var smsConfig = {
	saveDlogFormId: "saveDlogForm",
	/**
	 * 初始化
	 */
	init:function(){
		
	},
	/**
	 * 保存菜单提交
	 */
	save: function(){
		if(!common.submitFormValidate(smsConfig.saveDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "SmsConfigController/save",
			data : $("#" + smsConfig.saveDlogFormId).serialize(),
			success : function(data) {
				if(common.isSuccess(data)){
				}
			},
			error: function(data){
				common.error();
	        }
		});
	}
}




















