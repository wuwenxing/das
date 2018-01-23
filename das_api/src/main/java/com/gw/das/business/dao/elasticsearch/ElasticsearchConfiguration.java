package com.gw.das.business.dao.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * elasticsearch连接类
 * 
 * @author darren
 *
 */
@Configuration
public class ElasticsearchConfiguration implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfiguration.class);

	@Value("${elasticsearch.clusterName}")
	private String clusterName;

	@Value("${elasticsearch.ip}")
	private String ips;

	@Value("${elasticsearch.port}")
	private Integer port;

	private TransportClient transportClient;

	private PreBuiltTransportClient preBuiltTransportClient;

	@Override
	public void destroy() throws Exception {
		try {
			logger.info("Closing elasticSearch client");
			if (transportClient != null) {
				transportClient.close();
			}
		} catch (final Exception e) {
			logger.error("Error closing ElasticSearch client: ", e);
		}
	}

	@Override
	public TransportClient getObject() throws Exception {
		return transportClient;
	}

	@Override
	public Class<TransportClient> getObjectType() {
		return TransportClient.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		buildClient();
	}

	protected void buildClient() {
		try {
			preBuiltTransportClient = new PreBuiltTransportClient(settings());

			String[] clusterNodes = ips.split(",");
			for (int i = 0; i < clusterNodes.length; i++) {
				String ip = clusterNodes[i];
				preBuiltTransportClient
						.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
			}
			transportClient = preBuiltTransportClient;
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 初始化默认的client
	 */
	private Settings settings() {
		Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true)
				.build();
		return settings;
	}
}
