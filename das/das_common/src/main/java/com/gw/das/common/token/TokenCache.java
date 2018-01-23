package com.gw.das.common.token;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.lazy.LazyRefreshable;

/**
 * Token缓存
 * 
 * @author wayne
 *
 */
public class TokenCache {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenCache.class);

	/**
	 * 缓存Map<公司ID, 对应Token>
	 */
	private static Map<String, LazyRefreshable<String>> tokenLazyMap = new HashMap<String, LazyRefreshable<String>>();
	
	/**
	 * 先在缓存中提取，不存在再通过接口查询提取
	 */
	public static String getToken(String companyId) throws Exception{
		LazyRefreshable<String> tokenLazy = tokenLazyMap.get(companyId);
		if(tokenLazy == null){
			TokenResult token = CompanyEnum.getToken(companyId);
			if(null != token){
				int time = token.getData().getExpires();
				// 缓存时间小于有效期300秒,如果低于300秒不缓存
				if((time - 300) >= 0){
					time = time - 300;
					tokenLazy = new LazyRefreshable<String>(time*1000, token.getData().getToken()){
						private static final long serialVersionUID = 1L;
						@Override
						protected String refresh() throws Exception{
							String token = "";
							TokenResult tokenResult = CompanyEnum.getToken(companyId);
							if(null != tokenResult){
								token = tokenResult.getData().getToken();
							}
							logger.info(token);
							return token;
						}
					};
					tokenLazyMap.put(companyId, tokenLazy);
				}
				return token.getData().getToken();
			}
		}
		String token = tokenLazy.get();
		return token;
	}
	
}
