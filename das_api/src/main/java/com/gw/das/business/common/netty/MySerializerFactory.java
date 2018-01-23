package com.gw.das.business.common.netty;

import java.math.BigDecimal;

import com.caucho.hessian.io.BigDecimalDeserializer;
import com.caucho.hessian.io.Deserializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.Serializer;
import com.caucho.hessian.io.SerializerFactory;
import com.caucho.hessian.io.StringValueSerializer;

public class MySerializerFactory extends SerializerFactory {

	private StringValueSerializer bigDecimalSerializer = new StringValueSerializer();
	private BigDecimalDeserializer bigDecimalDeserializer = new BigDecimalDeserializer();

	@SuppressWarnings("rawtypes")
	@Override
	public Serializer getSerializer(Class cl) throws HessianProtocolException {
		if (BigDecimal.class.isAssignableFrom(cl)) {
			return bigDecimalSerializer;
		}
		return super.getSerializer(cl);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Deserializer getDeserializer(Class cl)
			throws HessianProtocolException {
		if (BigDecimal.class.isAssignableFrom(cl)) {
			return bigDecimalDeserializer;
		}
		return super.getDeserializer(cl);
	}
}
