package com.gw.das.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.das.task.SyncDepositInfoTask;

@Controller
@RequestMapping("/SystemThreadLogController")
public class SystemThreadLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemThreadLogController.class);

	@Autowired
	private SyncDepositInfoTask syncDepositInfoTask;

	/**
	 * 手动调用线程
	 */
	@RequestMapping("/call/{className}")
	@ResponseBody
	public String call(@PathVariable String className) {
		String msg = "call success!";
		try {
			if ("syncDepositInfoTask".equals(className)) {
				syncDepositInfoTask.initDate();
			}
		} catch (Exception e) {
			msg = "call error:" + e.getMessage();
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "系统出现异常:" + e.getMessage();
		}
		return msg;
	}

}
