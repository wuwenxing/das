package com.gw.das.netty;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gw.das.common.spring.ApplicationContextFactory;

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
		RequestDispatcher dispatcher = req.getRequestDispatcher("success.jsp");
		if(server.isStarted()){
			server.stop();
			req.setAttribute("msg", "netty stop success!");
		}else{
			server.start();
			req.setAttribute("msg", "netty start success!");
		}
		dispatcher.forward(req, resp);
	}
	
}
