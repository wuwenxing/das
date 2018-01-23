package com.gw.das.common.flow;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.context.Constants;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.MD5;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * 容联流量服务实现类
 * 
 * @author wayne
 */
public class RlFlowServer {
	
	private static final Logger logger = LoggerFactory.getLogger(RlFlowServer.class);
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	private static String callbackUrl = "http://dmp.gwghk.com/FlowLogController/syncFlowStatusByRl";
	
	// 开发者账号信息
	private static String flowRlUrl = "";
	private static String flowRlAppid = "";
	private static String flowRlSid = "";
	private static String flowRlToken = "";
	private static String systemActive = "";

	private static HttpClient httpClient = null;
	private static final int CONNECTION_POOL_SIZE = 3;
	private static final int TIMEOUT_SECONDS = 10 * 60;//10分钟
	
	/**
	 * 流量监听器
	 */
	private List<FlowListener> flowListeners = new ArrayList<FlowListener>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		// Set connection pool
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		cm.setMaxTotal(CONNECTION_POOL_SIZE);
		httpClient = new DefaultHttpClient(cm);
		// set timeout
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_SECONDS *1000);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SECONDS * 1000);
		
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
		flowRlUrl = SystemConfigUtil.getProperty(SystemConfigEnum.flowRlUrl);
		flowRlAppid = SystemConfigUtil.getProperty(SystemConfigEnum.flowRlAppid);
		flowRlSid = SystemConfigUtil.getProperty(SystemConfigEnum.flowRlSid);
		flowRlToken = SystemConfigUtil.getProperty(SystemConfigEnum.flowRlToken);
		systemActive = SystemConfigUtil.getProperty(SystemConfigEnum.systemActive);
		logger.info(flowRlUrl + "," + flowRlAppid + "," + flowRlSid + "," + flowRlToken + "," + systemActive);
	}

	/**
	 * 充值多条流量
	 */
	public void send(List<FlowInfo> flowInfos) {
		for (FlowInfo flowInfo : flowInfos) {
			send(flowInfo);
		}
	}

	/**
	 * 充值单条流量;
	 */
	public void send(final FlowInfo flowInfo) {
		theadPool.execute(new Runnable() {
			public void run() {
				FlowContext flowContext = new FlowContext();
				flowContext.setFlowInfo(flowInfo);
				doBefore(flowContext);
				try {
					Date date = new Date();
					String url = flowRlUrl + "/2013-12-26/Accounts/" + flowRlSid + "/flowPackage/flowRecharge?sig=" + getSig(date);
					// 电话号码，最多50个，必选
					String phones = flowInfo.getPhones();
					// 批次唯一标识
					String taskNo = flowInfo.getFlowLogId() + "";
					// 第三方交易id
					String customId = flowInfo.getCustomIdRl();
					// 流量包档位编码
					String sn = flowInfo.getFlowNoRl();
					// 流量包大小
					String packet = flowInfo.getFlowSizeRl();
					
					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put("appId", flowRlAppid); // 应用id
					parameters.put("phoneNum", phones); // 业务定制手机号
					parameters.put("sn", sn); // 流量包档位编码
					parameters.put("packet", packet); // 流量包大小
					parameters.put("customId", customId); // 第三方交易id,长度不超过32为非中文、非特殊字符、要求唯一
					parameters.put("reason", ""); // 第三方扩展参数,不必填
					parameters.put("callbackUrl", callbackUrl); // 回调第三方的地址,不必填
					// 提交请求
					String result = "";
					if(Constants.real.equals(systemActive)){
						result = doPost(url, parameters, getAuthorization(date));
					}else{
						// 不是真实环境不充值
						result = "{\"statusCode\":\"000000\",\"customId\":\"a8cc4f6fde4b996d87d6655a03ed57d5\",\"statusMsg\":\"提交成功\",\"rechargeId\":\"2654612cccb44177a8b1c967b6038468\",\"status\":\"1\"}";
					}
					/**
					 * result: { "statusCode":"000000", "statusMsg":"获取成功"
					 * "rechargeId":"695136f5028d11e5a1610050568e55bd",
					 * "status":1, “customId”:“695136f5028d11e5a1610050568e5500”
					 * }
					 */
					logger.info("result:" + result);
					RlFlowResponse response = JacksonUtil.readValue(result, RlFlowResponse.class);
					flowContext.setRlFlowResponse(response);
					doAfter(flowContext);
				} catch (Exception e) {
					logger.error("rl-充值流量出现异常", e);
					flowContext.setThrowable(e);
					doAfterThrowable(flowContext);
				}
			}
		});
	}

	/**
	 * 给发送器添加监听器
	 * 
	 * @param flowListener
	 */
	public void addFlowListener(FlowListener flowListener) {
		this.flowListeners.add(flowListener);
	}

	private void doBefore(FlowContext flowContext) {
		for (FlowListener flowListener : this.flowListeners) {
			flowListener.updateBefore(flowContext);
		}
	}

	private void doAfter(FlowContext flowContext) {
		for (FlowListener flowListener : this.flowListeners) {
			flowListener.updateAfter(flowContext);
		}
	}

	private void doAfterThrowable(FlowContext flowContext) {
		for (FlowListener flowListener : this.flowListeners) {
			flowListener.updateAfterThrowable(flowContext);
		}
	}

	/**
	 * 获取签名
	 * 
	 * @return
	 */
	private String getSig(Date date) {
		return MD5.getMd5(flowRlSid + flowRlToken + DateUtil.formatDateToString(date, "yyyyMMddHHmmss"))
				.toUpperCase();
	}

	/**
	 * 获取账号鉴权验证信息
	 * 
	 * @return
	 */
	private String getAuthorization(Date date) {
		String temp = flowRlSid + ":" + DateUtil.formatDateToString(date, "yyyyMMddHHmmss");
		return new String(Base64.encodeBase64(temp.getBytes()));
	}
	
	/**
	 * post请求，参数为键值对
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public String doPost(String url, Map<String, String> parameters, String authorization) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
		httpPost.setHeader("Authorization", authorization);
		HttpEntity entity = null;
		try {
			String requsetbody = JacksonUtil.toJSon(parameters);
			logger.info("post: " + httpPost.getURI());
			logger.info(requsetbody);
			BasicHttpEntity requestBody = new BasicHttpEntity();
			requestBody.setContent(new ByteArrayInputStream(requsetbody.getBytes("UTF-8")));
			requestBody.setContentLength(requsetbody.getBytes("UTF-8").length);
			httpPost.setEntity(requestBody);
			HttpResponse remoteResponse = httpClient.execute(httpPost);
			logger.info(remoteResponse.getStatusLine().toString());
			entity = remoteResponse.getEntity();
		} catch (Exception e) {
			logger.error("fetch remote content" + url + "  error", e);
			httpPost.abort();
			return null;
		}

		// 404错误
		if (entity == null) {
			throw new RuntimeException(url + " is not found");
		}

		InputStream input = entity.getContent();
		try {
			return IOUtils.toString(input, "UTF-8");
		} finally {
			// 保证InputStream的关闭.
			IOUtils.closeQuietly(input);
		}
	}
	
}
