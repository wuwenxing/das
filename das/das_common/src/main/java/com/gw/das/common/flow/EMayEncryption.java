package com.gw.das.common.flow;

import org.apache.commons.codec.digest.DigestUtils;

public class EMayEncryption {

	public static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}

}
