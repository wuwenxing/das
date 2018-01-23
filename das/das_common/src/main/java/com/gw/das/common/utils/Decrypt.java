package com.gw.das.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gwghk.cipher.client.CipherClient;

/**
 * 身份证解密
 * 
 * @author guanbo
 */
public class Decrypt {

	private static final Logger logger = LoggerFactory.getLogger(Decrypt.class);

	// 真实
	private static String idCardDecryptUrl = "";

	private static String id = "24k";

	private static transient CipherClient client;

	private static String key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAL93kz+ftRHV9PDl98VTYA6OzLSR"
			+ "1w1dVvl28TQMihrT7X+3oX0l2nBMCFPpyydA2q11Aw+ESTMu0UKQVoBbdyvZEzZuGDXTXYKDEblj"
			+ "C1cF9QtNx2/nNVk0yHc1qGq1FaOABzxkh1WK5I7rYVvzwfaFcS1i+k8pyidIWT1XgOkrAgMBAAEC"
			+ "gYAFofbrAD/LbofuLlXDsFg7FWhgR4oUEpLkc+NUrKnDcEikYTqW++4ZL6NvFImtdfL492BHrJSa"
			+ "tN3jH2vsCsTOsEtP7DYAvuhRWC4YQeoC44qZsXjNVFl0peQYANB9wVsiroh8PvGB4et6ICZTcdEN"
			+ "3XETEhIEkSHoJEa26l4mwQJBAPrf3Z2ElfXJvVZFjiQhsoKwigSb6gQZHw1a/pp/lC48th3Fgtqj"
			+ "q5g5fmnBHYVYV1jyR8bL5sQHukahxp1ohtUCQQDDYP6Wbcm0AjW01ru1lRZ/Wh306/KdKedi0IoD"
			+ "MYadCUEUSup6QQgIQ31vggYdFb89oeIBexHwDaz7fr3A26//AkBeO8K5zTiq91lYU44dwk6USo29"
			+ "R3dyjKEeWiSykeNuLr3VlwAc8kedSVNTlAdrtBAzR+ZwJN0Mmz58E35QWBf1AkBqQ84UiWAkbPND"
			+ "CWwM5irXV29lsBNEEe+M2jnGZOB/dvITlG+V0NsKi5Kk3IZwuUxLvEmNXp+x/e4w141jYNmHAkAR"
			+ "wX0Y0A6xD/X4fqlE/hpKs+I0zwG+btCyJPmSkDp24jYiMaP3tNZuJ5hdla4Nkg9Sz7K9CQmjVOnU" + "IqBfgSMW";

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		idCardDecryptUrl = SystemConfigUtil.getProperty(SystemConfigEnum.idCardDecryptUrl);
	}

	public static CipherClient getCipherClient() throws IOException {
		if (client == null && key != null) {
			try {
				client = CipherClient.getInstance(idCardDecryptUrl, id,
						new ByteArrayInputStream(key.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("never throw", e);
			}
		}
		return client;
	}

	public static String decrypt(String code) {
		try {
			if (StringUtils.isEmpty(code)) {
				return "";
			}
			CipherClient client = getCipherClient();
			return client.decrypt(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) throws IOException {
		String idCard = Decrypt.getCipherClient().decrypt("FazHcJ3Pmdava9vY6l+tOTWDgdHnd4Alvxni6JgHYRE=");
		logger.info(idCard);
	}

}