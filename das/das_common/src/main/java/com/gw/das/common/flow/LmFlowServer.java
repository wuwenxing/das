package com.gw.das.common.flow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * 乐免流量服务实现类
 * 
 * @author wayne
 */
public class LmFlowServer {
	private static final Logger logger = LoggerFactory.getLogger(LmFlowServer.class);
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	// 开发者账号信息
	private static String flowLmUrl = "";
	private static String flowLmNo = "";
	private static String flowLmAccount = "";
	private static String flowLmPassword = "";

	/**
	 * 流量监听器
	 */
	private List<FlowListener> flowListeners = new ArrayList<FlowListener>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
		flowLmUrl = SystemConfigUtil.getProperty(SystemConfigEnum.flowLmUrl);
		flowLmNo = SystemConfigUtil.getProperty(SystemConfigEnum.flowLmNo);
		flowLmAccount = SystemConfigUtil.getProperty(SystemConfigEnum.flowLmAccount);
		flowLmPassword = SystemConfigUtil.getProperty(SystemConfigEnum.flowLmPassword);
		logger.info(flowLmUrl + "," + flowLmNo + "," + flowLmAccount + "," + flowLmPassword);
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
	 * 充值单条流量
	 */
	public void send(final FlowInfo flowInfo) {
		theadPool.execute(new Runnable() {
			public void run() {
				FlowContext flowContext = new FlowContext();
				flowContext.setFlowInfo(flowInfo);
				doBefore(flowContext);
				try {
					// 基本参数
					String host = flowLmUrl;
					String stamp = new SimpleDateFormat("MMddHHmmss").format(new Date());

					String userId = flowLmNo; // 我公司提供用户编号
					String userName = java.net.URLEncoder.encode(flowLmAccount, "utf-8");// 我公司提供用户名
					String password = md5(flowLmPassword + stamp);// 我公司提供密码
					String mobile = flowInfo.getPhones();
					String flow = flowInfo.getFlowSizeLm(); // 流量包
					
					// 组合需要加密的字符串
					String strAes = String.format("%s,%s,%s,%s,%s,%s", userId, userName, password, mobile, flow, stamp);
					// 加密
					String secret = md5(strAes);
					String strParam = String.format(
							"UserId=%s&UserName=%s&Password=%s&mobile=%s&flow=%s&stamp=%s&secret=%s", userId, userName,
							password, mobile, flow, stamp, secret);

					String result = sendGet(host, strParam);
					logger.info("result:" + result);
					LmFlowResponse response = JacksonUtil.readValue(result, LmFlowResponse.class);
					flowContext.setLmFlowResponse(response);
					doAfter(flowContext);
				} catch (Exception e) {
					logger.error("lm-充值流量出现异常", e);
					flowContext.setThrowable(e);
					doAfterThrowable(flowContext);
				}
			}
		});
	}

	private final static String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				logger.info(key + "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("lm-发送GET请求出现异常！", e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
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
}
