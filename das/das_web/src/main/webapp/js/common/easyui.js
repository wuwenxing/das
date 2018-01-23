$(function() {
	easyui.init();
});

var easyui = {
	/**
	 * 参数初始化
	 */
	init : function() {

	},
	/**
	 * 默认datagrid 对象，可以进行扩展修改
	 */
	defaultOption : {
		fitColumns : true, // 列的宽度和自动大小
		singleSelect : true, // select时true单选false多选
		checkOnSelect : false, // select时true则check同样勾选，false则不勾选
		selectOnCheck : false, // check是否可多选选中
        showFooter: false, // 是否显示小计与总计
		fit : false, // 自动适屏
		nowrap : false, // 自动换行
		border : true,
		emptyMsg : '没有数据!',
		rownumbers : true, // 行号显示
		pagination : true, // 分页控件显示
		pageNumber : 1, // 初始页码
		pageSize : 20, // 初始大小
		pageList : [ 20, 50, 100, 200, 500 ],
		onDblClickRow : function(rowIndex, rowData) {
		},
		onLoadSuccess : function(data) {
			common.iFrameHeight();
		}
    },
	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @returns {String}
	 */
	myformatter : function(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
				+ (d < 10 ? ('0' + d) : d);
	},
	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @returns {String}
	 */
	myparser : function(s) {
		if (!s)
			return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	},
	formatterYYYYMM : function(date) {
		return date.Format("yyyy-MM");
	},
	parserYYYYMM : function(s) {
		var ss = (s.split("-"));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		if (!isNaN(y) && !isNaN(m)) {
			return new Date(y, m - 1);
		} else {
			return new Date();
		}
	},
	formatterYYYYMMDD : function(date) {
		return date.Format("yyyy-MM-dd");
	},
	parserYYYYMMDD : function(s) {
		var ss = (s.split("-"));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	},
	formatterYYYYMMDDHHMMSS : function(date) {
		return date.Format("yyyy-MM-dd HH:mm:ss");
	},
	parserYYYYMMDDHHMMSS : function(s) {
		return new Date(s)
	},
	formatterYYYYMMDDHH : function (date){
		return date.Format("yyyy-MM-dd HH");
    },
    parserYYYYMMDDHH : function(s) {
    	var ss = (s.split("-"));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		if (isNaN(y) && isNaN(m)) {
			var date = new Date();
			return date;
		}		
		var d = parseInt(ss[2].split(" ")[0], 10);
		var h = parseInt(ss[2].split(" ")[1], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h)) {
			return new Date(y, m - 1, d,h);
		} else {
			return new Date()
		}
	},
	formatterYYYYMMDD000000 : function(date) {
		return date.Format("yyyy-MM-dd")+" 00:00:00";
	},
	formatterYYYYMMDD235959 : function(date) {
		return date.Format("yyyy-MM-dd")+" 23:59:59";
	}

}