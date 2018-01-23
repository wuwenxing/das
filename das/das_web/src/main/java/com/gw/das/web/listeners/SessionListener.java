package com.gw.das.web.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * session监听器
 * @author wayne
 */
public class SessionListener implements HttpSessionListener {

	private final static Logger logger = Logger.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {

	}

	/**
	 * 当Session失效时，删除在线用户
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		try {

		} catch (Exception e) {
			logger.error("session失效删除在线用户错误！", e);
		}
	}

}
