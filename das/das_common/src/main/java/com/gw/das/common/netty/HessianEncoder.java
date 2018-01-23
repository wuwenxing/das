package com.gw.das.common.netty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import com.caucho.hessian.io.Deflation;
import com.caucho.hessian.io.Hessian2Output;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Hessian encoder
 * @author charles.so
 * 2015年2月18日
 */
public class HessianEncoder extends MessageToByteEncoder<Serializable>{
	
	private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
		int startIdx = out.writerIndex();
		
		ByteBufOutputStream bos = new ByteBufOutputStream(out);
		bos.write(LENGTH_PLACEHOLDER);
		encodeObj(msg, bos);
		
        int endIdx = out.writerIndex();
        out.setInt(startIdx, endIdx - startIdx - 4);
	}
	
	/**
	 * Encode object
	 * @param obj
	 * @param out
	 * @throws Exception
	 */
	public static void encodeObj(Object obj, OutputStream out) throws Exception{
		Deflation envelope = new Deflation();
		Hessian2Output hout = new Hessian2Output(out);
		hout = envelope.wrap(hout);
		hout.setSerializerFactory(new MySerializerFactory());
		hout.startMessage();
		hout.writeObject(obj);
		hout.completeMessage();
		hout.close();
	}
	
	
	public static void main(String[] args) throws Exception {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		HessianEncoder.encodeObj(BigDecimal.ONE, bo);
		bo.close();
		byte[] bytes = bo.toByteArray();
		
		System.out.println(bytes.length);
		
		Object bg = HessianDecoder.decodeObj(new ByteArrayInputStream(bytes));
		System.out.println(bg);
	}
}
