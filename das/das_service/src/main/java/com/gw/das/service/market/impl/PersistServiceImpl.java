package com.gw.das.service.market.impl;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.PersistContext;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.token.TokenCache;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gw.das.dao.market.entity.AccountAnalyzeEntity;
import com.gw.das.service.market.PersistService;

/**
 * phantomJs调用service
 */
@Service
public class PersistServiceImpl implements PersistService{

	private static final Logger logger = LoggerFactory.getLogger(PersistServiceImpl.class);

    private static final ConcurrentMap<String, PersistContext> TASK_MAP = new ConcurrentHashMap<>();
    private static String scriptPath = new File(PersistServiceImpl.class.getResource("/phantom.js").getFile()).getPath();

    private static String systemIntranetUrl = "";
    private static String phantomjsPath = "";
    private static String type = "";
    private static String web = "";
    private static Runtime runtime = null;
    static{
    	systemIntranetUrl = SystemConfigUtil.getProperty(SystemConfigEnum.systemIntranetUrl);
    	phantomjsPath = SystemConfigUtil.getProperty(SystemConfigEnum.phantomjsPath);
    	type = SystemConfigUtil.getProperty(SystemConfigEnum.systemActive);
    	web = systemIntranetUrl + "/page/market/accountAnalyze/chart.html";
    	runtime = Runtime.getRuntime();
    }
    
    public PersistContext persist(String batchNo, String fileName, AccountAnalyzeEntity entity) {
        try {
            if(null != TASK_MAP.get(fileName)){
            	// 说明该逻辑已经正在处理，不需再次处理else{
				logger.info("批次号{}，文件名{}不需重复生成报告!", batchNo, fileName);
            	return TASK_MAP.get(fileName);
            }
            PersistContext context = new PersistContext(fileName);
            TASK_MAP.put(fileName, context);
            String phantomUrl = new StringBuffer()
                    .append(web)
                    .append("?persistId=").append(fileName)
                    .append(",").append(type)
                    .append(",").append(TokenCache.getToken(entity.getCompanyId()+""))
                    .append(",").append(CompanyEnum.getAPICompanyId(entity.getCompanyId()+""))
                    .append(",").append(entity.getAccountNo())
                    .append(",").append(entity.getPlatform()==null?"":entity.getPlatform())
                    .append(",").append(entity.getStartDate())
                    .append(",").append(entity.getEndDate())
                    .toString();
            String cmd = String.format("%s %s %s %s %s %s", phantomjsPath, scriptPath, phantomUrl, batchNo, fileName, type);
            logger.info("Run phantomjs command: {}", cmd);
            runtime.exec(cmd);
            synchronized (context) {
            	// 等待10分钟
                context.wait(10 * 60 * 1000);
            }
            TASK_MAP.remove(fileName);
            return context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void persistCallback(String persistId, String flag) {
    	if(StringUtils.isNotBlank(persistId)){
            PersistContext context = TASK_MAP.get(persistId);
            if(null != context ){
                synchronized (context) {
                	context.setFlag(flag);
                    context.notify();
                }
            }
    	}
    }
    
}
