package com.gw.das.server.netty;

public interface NettyUserValidator {

	public boolean validate(String timestamp, String sign, String sid) throws Exception;

}
