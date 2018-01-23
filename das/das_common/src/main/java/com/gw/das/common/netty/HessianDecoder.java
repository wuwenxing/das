package com.gw.das.common.netty;

import java.io.InputStream;

import com.caucho.hessian.io.Deflation;
import com.caucho.hessian.io.Hessian2Input;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Hessian decoder
 * @author charles.so
 * 2015年2月18日
 */
public class HessianDecoder extends LengthFieldBasedFrameDecoder{

	public HessianDecoder(){
		super(1048576, 0, 4, 0, 4);
	}
	
	
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        
        return decodeObj(frame);
    }
    
    /**
     * decode from ByteBuf
     * @param in
     * @return
     * @throws Exception
     */
    public static Object decodeObj(ByteBuf in) throws Exception{
        ByteBufInputStream bin = new ByteBufInputStream(in);
        return decodeObj(bin);
    }
    
    public static Object decodeObj(InputStream in) throws Exception{
        Deflation envelope = new Deflation();
        Hessian2Input hin = new Hessian2Input(in);
        hin = envelope.unwrap(hin);
        hin.setSerializerFactory(new MySerializerFactory());
        hin.startMessage();
        Object value = hin.readObject();
        hin.completeMessage();
        return value;
    }
	
}
