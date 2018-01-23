package com.gw.das.server.netty;

import com.gw.das.server.web.ApplicationContextFactory;

/**
 * netty http server 启动类
 * 
 * @author wayne
 */
public class NettyHttpServerRunner {

	public static void main(String[] args) {
		NettyHttpServer server = ApplicationContextFactory.getApplicationContext().getBean(NettyHttpServer.class);
		server.start();
	}
}
