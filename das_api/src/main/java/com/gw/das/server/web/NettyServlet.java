package com.gw.das.server.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gw.das.server.netty.NettyHttpServer;

public class NettyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private NettyHttpServer server;
	
	@Override
	public void init() throws ServletException {
		server = ApplicationContextFactory.getApplicationContext().getBean(NettyHttpServer.class);
		server.start();
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(server.isStarted()){
			server.stop();
		}else{
			server.start();
		}
		super.doGet(req, resp);
	}
	
}
