package com.gw.das.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gw.das.common.netty.HessianDecoder;

/**
 * 
 * @ClassName: HttpUtil
 * @Description: HTTP 工具类(基于HttpClient)
 * 
 */
@Component("httpUtil")
public class HttpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * httpUtil实例
	 */
	private static HttpUtil httpUtil;
	
	private static final String JSON_TYPE = "application/json";
	private static final String TEXT_TYPE = "text/xml";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final int CONNECTION_POOL_SIZE = 10;
	private static final int TIMEOUT_SECONDS = 10 * 60;//10分钟
	private HttpClient httpClient = null;

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * 不允许创建实例
	 */
	private HttpUtil() {
		
	}
	
	public static HttpUtil getInstance() {
		if (null == httpUtil) {
			httpUtil = new HttpUtil();
			httpUtil.init();
		}
		return httpUtil;
	}
	
	/**
	 * 始始化HttpClient,且为多线程安全.
	 */
	@PostConstruct
	public void init() {
		// Set connection pool
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		cm.setMaxTotal(CONNECTION_POOL_SIZE);
		httpClient = new DefaultHttpClient(cm);

		// set timeout
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_SECONDS *1000);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SECONDS * 1000);
	}

	/**
	 * 销毁HttpClient实例.
	 */
	@PreDestroy
	public void destroy() {
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String doGet(String url) throws IOException {
		String returnMsg = "";
		HttpGet httpget = new HttpGet(url);
		logger.info("get: " + httpget.getURI());
		HttpResponse response = httpClient.execute(httpget);
		logger.debug("status: " + response.getStatusLine());
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				returnMsg = EntityUtils.toString(entity);
				entity.consumeContent();
			}
		} else {
			throw new RuntimeException(response.getStatusLine().toString());
		}
		// System.out.println(html);
		return returnMsg;
	}

	/**
	 * post请求，参数为键值对
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public String doPost(String url, Map<String, String> parameters) throws IOException {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (parameters != null && !parameters.isEmpty()) {
			Iterator<Entry<String, String>> itr = parameters.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) itr.next();
				NameValuePair nvp = new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue());
				nvps.add(nvp);
			}
		}
		HttpPost httpPost = new HttpPost(url);
		StringEntity params = new UrlEncodedFormEntity(nvps, DEFAULT_ENCODING);	
		httpPost.setEntity(params);
		logger.info("post: " + httpPost.getURI());
		logger.info(nvps.toString());
		HttpEntity entity = null;
		try {
			HttpContext context = new BasicHttpContext();
			HttpResponse remoteResponse = httpClient.execute(httpPost, context);
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
			return IOUtils.toString(input, DEFAULT_ENCODING);
		} finally {
			// 保证InputStream的关闭.
			IOUtils.closeQuietly(input);
		}
	}

	/**
	 * post请求，参数为json串
	 * @param url
	 * @param jsonData
	 * @return
	 * @throws IOException
	 */
	public String doPostHessian(String url, String jsonData) throws Exception {
		StringEntity params = new StringEntity(jsonData, DEFAULT_ENCODING);
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", JSON_TYPE);
		httpPost.setEntity(params);

		logger.info("post: " + httpPost.getURI());
		logger.info(jsonData);

		HttpEntity entity = null;
		try {
			HttpContext context = new BasicHttpContext();
			HttpResponse remoteResponse = httpClient.execute(httpPost, context);
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
			Object obj = HessianDecoder.decodeObj(input);
			return obj.toString();
//			return IOUtils.toString(input, DEFAULT_ENCODING);
		} finally {
			// 保证InputStream的关闭.
			IOUtils.closeQuietly(input);
		}
	}	
	
	/**
	 * post请求，参数为json串
	 * @param url
	 * @param jsonData
	 * @return
	 * @throws IOException
	 */
	public String doPostJson(String url, String jsonData) throws IOException {
		StringEntity params = new StringEntity(jsonData, DEFAULT_ENCODING);
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", JSON_TYPE);
		httpPost.setEntity(params);

		logger.info("post: " + httpPost.getURI());
		logger.info(jsonData);

		HttpEntity entity = null;
		try {
			HttpContext context = new BasicHttpContext();
			HttpResponse remoteResponse = httpClient.execute(httpPost, context);
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
			return IOUtils.toString(input, DEFAULT_ENCODING);
		} finally {
			// 保证InputStream的关闭.
			IOUtils.closeQuietly(input);
		}
	}
	
	/**
	 * post请求，参数为字符串
	 * @param url
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public String doPostContent(String url, String content) throws IOException {
		StringEntity params = new StringEntity(content, DEFAULT_ENCODING);
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", TEXT_TYPE);
		httpPost.setEntity(params);

		logger.info("post: " + httpPost.getURI());
		logger.info(content);

		HttpEntity entity = null;
		try {
			HttpContext context = new BasicHttpContext();
			HttpResponse remoteResponse = httpClient.execute(httpPost, context);
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
			return IOUtils.toString(input, DEFAULT_ENCODING);
		} finally {
			// 保证InputStream的关闭.
			IOUtils.closeQuietly(input);
		}
	}

	public void close() {
		httpClient.getConnectionManager().shutdown();
	}

}
