package com.gw.das.api.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * 
 * @author darren
 *
 */
public class MD5 {

	/**
	 * MD5 加密
	 * 
	 * @param source
	 * @return
	 */
	public static String encrypt(String source) {
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(source.getBytes());
	        byte[] messageDigest = md.digest();
	        BigInteger msgInt = new BigInteger(1, messageDigest);
	        String hashString = msgInt.toString(16);
	        while(hashString.length() < 32) {
	        	hashString = "0"+hashString;
	        }
	        return hashString;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        return null;
	}
}
