package com.gw.das.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class ValidateCodeUtil {

	/**
	 * 生成验证码
	 * @param request
	 * @return
	 */
	public static String captcha(){
		String code = "";
		String chars = "ABCDEFGHJKMNPQRSTUVWXYZ";//去除O、i、l,混淆
		Random random = new Random();
		for(int i=0; i<4; i++){
			if(random.nextInt(10)%2 == 0){
				code += random.nextInt(7) + 2;//2-9
			}else{
				code +=  chars.charAt((int)(Math.random()*chars.length()));
			}
		}
		return code;
	}

	/**
	 * 
	 * 将一个inputstream里面的数组全部读取到 一个数组中，这个能将数组数据全部读出来。
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static byte[] getBytes(InputStream is) {
		byte[] data = null;
		try {
			Collection chunks = new ArrayList();
			byte[] buffer = new byte[1024 * 1000];
			int read = -1;
			int size = 0;

			while ((read = is.read(buffer)) != -1) {
				if (read > 0) {
					byte[] chunk = new byte[read];
					System.arraycopy(buffer, 0, chunk, 0, read);
					chunks.add(chunk);
					size += chunk.length;
				}
			}

			if (size > 0) {
				ByteArrayOutputStream bos = null;
				try {
					bos = new ByteArrayOutputStream(size);
					for (Iterator itr = chunks.iterator(); itr.hasNext();) {
						byte[] chunk = (byte[]) itr.next();
						bos.write(chunk);
					}
					data = bos.toByteArray();
				} finally {
					if (bos != null) {
						bos.close();
					}
				}
			}
		} catch (IOException e) {
			return data;
		}
		return data;

	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
