package com.gw.das.common.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.lazy.LazyRefreshable;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * 恒信短信接口-短信发送-返回实体
 * 
 * @author wayne
 *
 */
public class HxSidCache {
	
	private static final Logger logger = LoggerFactory.getLogger(HxSidCache.class);

	/**
	 * hxSid缓存30分钟
	 */
	private static LazyRefreshable<String> hxSidLazy = null;
	
	// 恒信登录url
	private static String loginUrl = "/members/login/?";
	
	private static String hxApiUrl = "";
	private static String hxApiUsername = "";
	private static String hxApiPassword = "";
	
	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		hxApiUrl = SystemConfigUtil.getProperties().getProperty(SystemConfigEnum.hxApiUrl.getLabelKey());
		hxApiUsername = SystemConfigUtil.getProperties().getProperty(SystemConfigEnum.hxApiUsername.getLabelKey());
		hxApiPassword = SystemConfigUtil.getProperties().getProperty(SystemConfigEnum.hxApiPassword.getLabelKey());
	}
	
	/**
	 * 功能：提取字典列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return: SystemDictEntity
	 */
	public static String getHxSid() throws Exception{
		if(hxSidLazy == null){
			hxSidLazy = new LazyRefreshable<String>(30*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected String refresh() throws Exception{
					String hxSid = "";
					String postUrl = hxApiUrl + loginUrl + "apiLogin=" + hxApiUsername +"&apiPassword=" + hxApiPassword;
					String result = HxSidCache.post(postUrl, "");
					logger.error("result:" + result);
					HxApiResponse response = JacksonUtil.readValue(result, HxApiResponse.class);
					if(null != response && "0".equals(response.getStatus())){
						hxSid = response.getData();
					}
					return hxSid;
				}
			};
		}
		String hxSid = hxSidLazy.get();
		return hxSid;
	}
	
	/**
	 * post请求
	 * 
	 * @param url
	 *            功能和操作
	 * @param body
	 *            要post的数据
	 * @return
	 * @throws IOException
	 */
	private static String post(String url, String body) {
		logger.error("url:" + url);
		logger.error("body:" + body);

		String result = "";
		try {
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();

			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);

			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();

			// 读取返回数据
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true; // 读第一行不加换行符
			while ((line = in.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
				} else {
//					result += System.lineSeparator();
				}
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
