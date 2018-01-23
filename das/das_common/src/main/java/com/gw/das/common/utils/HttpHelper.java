package com.gw.das.common.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class HttpHelper {

	/**
	 * 从request中获取post过来的字节数据
	 */
	public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		/* 当无请求参数时，request.getContentLength()返回-1 */
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	/**
	 * 从request中获取post过来的String数据
	 */
	public static String getRequestPostStr(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		if(null != buffer){
			return new String(buffer, charEncoding);
		}else{
			return "";
		}
	}

}
