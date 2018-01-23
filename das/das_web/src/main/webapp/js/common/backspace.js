$(document).ready(
	function() {
		// 阻止用户点Backspace返回上一页
		$(document).keydown(
			function(e) {
				var target = e.target;
				var tag = e.target.tagName.toUpperCase();
				if (e.keyCode == 8) {
					// 阻止退格事件
					if ((tag == 'INPUT' && !$(target).attr("readonly")) || (tag == 'TEXTAREA' && !$(target).attr("readonly")) || $(target).attr("contenteditable") == "true") {
						if ((target.type != null && target.type.toUpperCase() == "RADIO") || (target.type != null && target.type.toUpperCase() == "CHECKBOX")) {
							return false;
						} else {
							return true;
						}
					} else {
						return false;
					}
				}
				if (e.keyCode == 13) {
					// 阻止回车事件
					if (tag == 'TEXTAREA' || $(target).attr("contenteditable") == "true") {
						return true;
					}
					return false;
				}
			}
		);
	}
);
