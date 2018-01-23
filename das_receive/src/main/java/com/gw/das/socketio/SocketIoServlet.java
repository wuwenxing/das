package com.gw.das.socketio;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gw.das.common.spring.ApplicationContextFactory;

public class SocketIoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private SocketIoClient client;

	@Override
	public void init() throws ServletException {
		client = ApplicationContextFactory.getApplicationContext().getBean(SocketIoClient.class);
		client.connect();
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("success.jsp");
		if(client.isConnected()){
			client.disConnect();
			req.setAttribute("msg", "websocket stop success!");
		}else{
			client.connect();
			req.setAttribute("msg", "websocket start success!");
		}
		dispatcher.forward(req, resp);
	}
}
