package com.gw.das.common.context;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.LoginClientEnum;

/**
 * 当前登录用户上下文对象 登录后设置对应的信息，用于后续调用
 * @author wayne
 */
public class UserContext implements java.io.Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(UserContext.class);

	private static final long serialVersionUID = -8265522652490711669L;

	private static transient ThreadLocal<UserContext> instance = new ThreadLocal<UserContext>();

	private String loginIp;
	private String loginNo;
	private String loginName;
	private Date loginDate;
	private String sessionId;
	private String clientType;
	private Long companyId;

	/**
	 * 设置为当前登录用户执行
	 */
	public static void setSystemUser(String loginIp, String loginNo
			, String loginName, Date loginDate, String sessionId
			, String clientType, Long companyId) {
		UserContext userContext = new UserContext();
		userContext.setLoginIp(loginIp);
		userContext.setLoginNo(loginNo);
		userContext.setLoginName(loginName);
		userContext.setLoginDate(loginDate);
		userContext.setSessionId(sessionId);
		userContext.setClientType(LoginClientEnum.systemUserLogin.getLabelKey());
		userContext.setCompanyId(companyId);
		UserContext.set(userContext);
	}
	
	/**
	 * 设置为当前系统线程执行
	 */
	public static void setSystemThread(Long companyId) {
		UserContext userContext = new UserContext();
		userContext.setLoginName(LoginClientEnum.systemThread.getLabelKey());
		userContext.setLoginNo(LoginClientEnum.systemThread.getLabelKey());
		userContext.setLoginIp("127.0.0.1");
		userContext.setCompanyId(companyId);
		userContext.setSessionId(LoginClientEnum.systemThread.getLabelKey());
		userContext.setClientType(LoginClientEnum.systemThread.getLabelKey());
		userContext.setLoginDate(new Date());
		UserContext.set(userContext);
	}
	
	/**
	 * 设置为当前外部调用系统接口执行
	 */
	public static void setSystemInterface(String loginIp, Long companyId) {
		UserContext userContext = new UserContext();
		userContext.setLoginName(LoginClientEnum.systemInterface.getLabelKey());
		userContext.setLoginNo(LoginClientEnum.systemInterface.getLabelKey());
		userContext.setLoginIp(loginIp);
		userContext.setCompanyId(companyId);
		userContext.setSessionId(LoginClientEnum.systemInterface.getLabelKey());
		userContext.setClientType(LoginClientEnum.systemInterface.getLabelKey());
		userContext.setLoginDate(new Date());
		UserContext.set(userContext);
	}
	
	private static void set(UserContext userContext) {
		instance.set(userContext);
		logger.info(userContext.toString());
	}

	public static UserContext get() {
		UserContext userContext = instance.get();
		if (userContext != null) {
			return userContext;
		}
		return null;
	}

	@Override
	public String toString() {
		return "UserContext [loginIp=" + loginIp + ", loginNo=" + loginNo + ", loginName=" + loginName + ", sessionId=" + sessionId
				+ ", clientType=" + clientType + ", companyId=" + companyId + "]";
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

}
