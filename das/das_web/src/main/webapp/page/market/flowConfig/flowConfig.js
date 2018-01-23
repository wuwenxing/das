$(function() {
	flowConfig.init();
});

var flowConfig = {
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
		if(!common.submitFormValidate(flowConfig.saveDlogFormId)){
			return false;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url: BASE_PATH + "FlowConfigController/save",
			data : $("#" + flowConfig.saveDlogFormId).serialize(),
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




















