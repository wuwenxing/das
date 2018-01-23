package com.gw.das.common.kafka;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.gw.das.common.enums.MessageTypeEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.flume.FlumeSendMsg;
import com.gw.das.common.utils.SystemConfigUtil;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaSendMsg {

	final static Logger logger = Logger.getLogger(FlumeSendMsg.class);

	private static Producer<String, String> producer = null;
	
	static {
		Properties props = new Properties();
		props.put("metadata.broker.list", SystemConfigUtil.getProperty(SystemConfigEnum.kafkaBrokerIP_1));
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// props.put("partitioner.class", "com.colobu.kafka.SimplePartitioner");
		props.put("producer.type", "async");
		ProducerConfig config = new ProducerConfig(props);
		producer = new Producer<String, String>(config);
	}

	public static void sendMessage(String content, String messageType) {
		if (messageType.equals(MessageTypeEnum.web.getLabelKey())) {
//			KeyedMessage<String, String> data = new KeyedMessage<String, String>(MessageTypeEnum.web.getTopic(), content);
//			producer.send(data);
		} else if (messageType.equals(MessageTypeEnum.room.getLabelKey())) {
//			KeyedMessage<String, String> data = new KeyedMessage<String, String>(MessageTypeEnum.room.getTopic(), content);
//			producer.send(data);
		} else if (messageType.equals(MessageTypeEnum.deposit.getLabelKey())) {
//			KeyedMessage<String, String> data = new KeyedMessage<String, String>(MessageTypeEnum.deposit.getTopic(), content);
//			producer.send(data);
		} else if (messageType.equals(MessageTypeEnum.app.getLabelKey())) {
//			KeyedMessage<String, String> data = new KeyedMessage<String, String>(MessageTypeEnum.app.getTopic(), content);
//			producer.send(data);
		}
	}

}
