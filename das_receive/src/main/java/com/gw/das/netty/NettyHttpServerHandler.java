package com.gw.das.netty;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.UriUtils;

import com.gw.das.common.context.Constants;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SystemConfigUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * netty http server handler
 * 
 * @author wayne
 */
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<Object> {

	private final static Logger logger = Logger.getLogger(NettyHttpServerHandler.class);

	private HttpRequest request = null;
	/** Buffer that stores the response content */
	private ByteBuf buf;
	// get请求时参数
	Map<String, String> params = new HashMap<String, String>();
	// 客户端api接口管理类与实现类对象的关系
	private Properties rpcProperties;
	// 类实例对象集合
	private Map<String, Object> instances = new HashMap<String, Object>();
	// 某个类实例对象的方法集合
	private Map<String, Map<String, Method>> methodMap = new HashMap<String, Map<String, Method>>();
	
	/**
	 * 获取接口对应的实现类，并将对象及对象的方法放入缓存对象
	 * 
	 * @param infName
	 *            类名
	 * @return
	 * @throws Exception
	 */
	private Object getRpcInstance(String infName) throws Exception {
		Object obj = instances.get(infName);
		if (obj == null) {
			return _getRpcInstance(infName);
		}
		return obj;
	}

	private synchronized Object _getRpcInstance(String infName) throws Exception {
		try {
			Object obj = instances.get(infName);
			// scan methods in the interface
			if (obj == null) {
				// 获取接口对应的实现类
				String className = rpcProperties.getProperty(infName);
				obj = Class.forName(className).newInstance();
				// 获取接口对应的方法
				Method[] infMethods = Class.forName(className).getMethods();
				Map<String, Method> methodMap = new HashMap<String, Method>();
				for (Method infmth : infMethods) {
					Method m = obj.getClass().getMethod(infmth.getName(), infmth.getParameterTypes());
					String methodName = m.getName();
					if (methodMap.containsKey(methodName)) {
						logger.debug("缓存已存在接口类[" + className + "]的方法[" + methodName + "]");
					} else {
						methodMap.put(m.getName(), m);
					}
				}
				// 放入缓存对象
				this.instances.put(infName, obj);
				this.methodMap.put(infName, methodMap);
			}
			return obj;
		} catch (Exception e) {
			logger.error("缓存接口方法异常", e);
			throw new Exception(e);
		}
	}

	/**
	 * 获取类中指定的方法
	 * 
	 * @param infName
	 *            类名
	 * @param methodName
	 *            方法名
	 * @return
	 */
	private Method getMethod(String infName, String methodName) {
		Map<String, Method> map = this.methodMap.get(infName);
		if (map != null) {
			return map.get(methodName);
		}
		return null;
	}

	/**
	 * 运行指定类中指定的方法，并将查询的数据，发送给客户端
	 * 
	 * @param infName
	 *            类名
	 * @param methodName
	 *            方法名
	 * @return
	 */
	private void invoke(HttpObject currentObj, ChannelHandlerContext ctx, String jsonStr) throws Exception {
		long state= System.currentTimeMillis();
		// 获取uri-格式为"/{className}/{methodName}",如/DasFlowDetail/DasFlowDetailListPage
		String uri = request.getUri();
		logger.info("request uri=[" + uri + "]");
		int start = uri.indexOf("?");
		if(start != -1){
			uri = uri.substring(0, start);
		}
		String infName = "";
		String methodName = "";
		if (StringUtils.isNotBlank(uri)) {
			// 原访问地址格式-测试场：http://localhost:8058/项目名/controller/方法名
			// 原访问地址格式-真实场：http://das.gwfx.com/controller/方法名
			// 对原访问地址做处理，跳转到新地址
			if(uri.indexOf("/put/") != -1){
				String[] str = uri.split("/");
				String tempVal = "";
				if (str.length == 3) { //真实环境
					tempVal = Constants.linkUrls.get(str[1] + "/" + str[2]);
				}else if(str.length == 4){ //测试环境
					tempVal = Constants.linkUrls.get(str[2] + "/" + str[3]);
				}
				if(StringUtils.isNotBlank(tempVal)){
					infName = tempVal.split("/")[0];
					methodName = tempVal.split("/")[1];
				}
			}else{
				// 新地址统一使用地址格式：http://localhost:8058/配置名(rpc.properties)/方法名
				String[] str = uri.split("/");
				if (str.length == 3) {
					infName = str[1];
					methodName = str[2];
				}
			}
		}
		
		try {
			if(StringUtils.isNotBlank(infName) && StringUtils.isNotBlank(methodName)){
				Object target = getRpcInstance(infName);
				Method method = getMethod(infName, methodName);
				if (method != null) {
					Object[] args = new Object[1];
					if(StringUtils.isBlank(jsonStr) && this.params.size() > 0){
						jsonStr = JacksonUtil.toJSon(this.params);
					}
					args[0] = jsonStr;
					Object obj = method.invoke(target, args);
					RpcResult result = null;
					if(obj instanceof String){
						result = new RpcResult(RpcStatusEnum.success, obj + "");
					}else{
						result = new RpcResult(RpcStatusEnum.success, JacksonUtil.toJSon(obj));
					}
					writeResponse(ctx, currentObj, result);
				}
			}else{
				logger.debug("接口地址错误，请检查");
				writeResponse(ctx, currentObj, new RpcResult(RpcFailEnum.interfaceUrlError));
			}
		} catch (Exception e) {
			logger.error("执行接口[/" + infName + "/" + methodName + "]出现异常：" + e.getMessage(), e);
			String result = "执行接口[/" + infName + "/" + methodName + "]出现异常：" + e.getMessage();
			writeResponse(ctx, currentObj, new RpcResult(result));
		} finally {
			logger.debug(infName + "-->" + methodName + ",执行时间:" + (System.currentTimeMillis()-state)/1000 + "s");
		}
		
	}

	/**
	 * 此方法会在client连接到server后被调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		rpcProperties = SystemConfigUtil.getProperties();
	}

	/**
	 * 此方法会在接收到client数据后调用
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {

		if (msg instanceof HttpRequest) { // HttpRequest
			HttpRequest request = (HttpRequest) msg;
			this.request = request;
			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(ctx);
			}
			this.buf = UnpooledByteBufAllocator.DEFAULT.buffer();
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
			Map<String, List<String>> params = queryStringDecoder.parameters();
			if (!params.isEmpty()) {
				for (Entry<String, List<String>> p : params.entrySet()) {
					String key = p.getKey();
					List<String> vals = p.getValue();
					for (String val : vals) {
						logger.debug("PARAM: " + key + " = " + val);
						this.params.put(key, val);
					}
				}
			}
		} else if (msg instanceof HttpContent) { // HttpContent
			HttpContent httpContent = (HttpContent) msg;
			ByteBuf content = httpContent.content();
			if (content.isReadable()) {
				this.buf.writeBytes(content); // 写入发送的数据到buf
			}
			if (msg instanceof LastHttpContent) {
				LastHttpContent trailer = (LastHttpContent) msg;
				int readable = buf.readableBytes();
				byte[] bytes = new byte[readable];
				buf.readBytes(bytes);
				String jsonStr = UriUtils.decode(new String(bytes, "UTF-8"), "UTF-8");
				logger.info("post请求参数:" + jsonStr);
				try{
					invoke(trailer, ctx, jsonStr);
				}catch(Exception e){
					logger.error("请求参数异常：" + e.getMessage(), e);
					String result = "请求参数异常：" + e.getMessage();
					writeResponse(ctx, trailer, new RpcResult(result));
				}
			}
		}
	}

	/**
	 * 返回消息给客户端，共用此方法
	 * 
	 * @param ctx
	 * @param currentObj
	 * @param result
	 */
	private void writeResponse(ChannelHandlerContext ctx, HttpObject currentObj, RpcResult result) {

		ByteBuf responseBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
		// encode return object
		@SuppressWarnings("resource")
		ByteBufOutputStream out = new ByteBufOutputStream(responseBuf);
		try {
			// 1、对象转json
			// logger.info("resultJson=" + result.getResult());
			// 1、Byte[]=8个二进制传输
			if(RpcStatusEnum.success.getLabelKey().equals(result.getStatus())){
				out.write(result.getResult().getBytes());
			}else{
				out.write(JacksonUtil.toJSon(result).getBytes());
			}
			// 2、二进制传输
			// HessianEncoder.encodeObj(resultJson, out);
			
			// Decide whether to close the connection or not.
			boolean keepAlive = HttpHeaders.isKeepAlive(request);
			// Build the response object.
			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
					currentObj.getDecoderResult().isSuccess() ? OK : BAD_REQUEST, responseBuf);
			response.headers().set("Access-Control-Allow-Origin", "*");
			response.headers().set("Access-Control-Allow-Headers", "X-Requested-With");
			response.headers().set("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
			response.headers().set("X-Powered-By", "3.2.1");
			response.headers().set("P3P", "CP=CAO PSA OUR");//处理ie跨域问题
			response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
			
			if (keepAlive) {
				// Add 'Content-Length' header only for a keep-alive connection.
				response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

				// Add keep alive header as per:
				// -
				// http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
				response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			}

			// Write the response.
			ctx.writeAndFlush(response);

			if (!keepAlive) {
				// If keep-alive is off, close the connection once the content
				// is fully written.
				ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
		ctx.write(response);
	}

	/**
	 * 读取完毕清空缓冲区
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		ctx.flush();
	}

	/**
	 * 捕获异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("Exception thrown, close connection.", cause);
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}
	
}
