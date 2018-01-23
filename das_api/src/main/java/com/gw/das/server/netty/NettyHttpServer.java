package com.gw.das.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import org.apache.log4j.Logger;

/**
 * netty http server
 * 
 * @author wayne
 */
public class NettyHttpServer {

	private static final Logger logger = Logger.getLogger(NettyHttpServer.class);

	private int port;
	private int bossThreads;
	private int workThreads;

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	private boolean started = false;
	private String status = "";

	/**
	 * 注入用户验证接口
	 */
	private NettyUserValidator validator;

	/**
	 * 启动入口
	 */
	public void start() {
		
		if (started) {
			logger.info("Server already started!");
			return;
		}

		bossGroup = new NioEventLoopGroup(bossThreads);// 接受连接事件组
		workerGroup = new NioEventLoopGroup(workThreads);// 处理每个连接业务事件组

		new Thread() {

			@Override
			public void run() {
				try {
					ServerBootstrap b = new ServerBootstrap();
					// 可以只使用一个group，分成两个group的好处是：业务耗时较长导致阻塞时，不会对接受连接造成影响。
					b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
							.handler(new LoggingHandler(LogLevel.INFO))
							.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
						    p.addLast("readTimeoutHandler", new ReadTimeoutHandler(60 * 10));
							p.addLast(
									// idle handler
									new IdleStateHandler(60, 0, 0) {
								@Override
								public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
									if (evt instanceof IdleStateEvent) {
										IdleStateEvent e = (IdleStateEvent) evt;
										if (e.state() == IdleState.READER_IDLE) {
											ctx.close();
										} else if (e.state() == IdleState.WRITER_IDLE) {
											// ctx.writeAndFlush(new
											// PingMessage());
										}
									}
								}
							}, new HttpRequestDecoder(), new HttpResponseEncoder(),
									new NettyHttpServerHandler(validator)); // handler
						}
					});
					// Bind and start to accept incoming connections.
					logger.info("Init Netty HTTP server, bind port " + port);
					status = NettyConstants.STARTED;
					b.bind(port).sync().channel().closeFuture().sync();
				} catch (Exception e) {
					logger.error("", e);
					status = NettyConstants.FAIL + ":" + e.getMessage();
				} finally {
					bossGroup.shutdownGracefully();
					workerGroup.shutdownGracefully();
					if (!status.startsWith(NettyConstants.FAIL)) {
						status = NettyConstants.STOPED;
					}
				}
			}

		}.start();
		status = NettyConstants.WAITING;
		started = true;
	}

	/**
	 * 停止服务
	 */
	public void stop() {
		if (started) {
			logger.info("Stop netty server port " + port);
			started = false;
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			status = NettyConstants.STOPED;
		}
	}

	// get set
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getBossThreads() {
		return bossThreads;
	}

	public void setBossThreads(int bossThreads) {
		this.bossThreads = bossThreads;
	}

	public int getWorkThreads() {
		return workThreads;
	}

	public void setWorkThreads(int workThreads) {
		this.workThreads = workThreads;
	}

	public EventLoopGroup getBossGroup() {
		return bossGroup;
	}

	public void setBossGroup(EventLoopGroup bossGroup) {
		this.bossGroup = bossGroup;
	}

	public EventLoopGroup getWorkerGroup() {
		return workerGroup;
	}

	public void setWorkerGroup(EventLoopGroup workerGroup) {
		this.workerGroup = workerGroup;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public NettyUserValidator getValidator() {
		return validator;
	}

	public void setValidator(NettyUserValidator validator) {
		this.validator = validator;
	}

}
