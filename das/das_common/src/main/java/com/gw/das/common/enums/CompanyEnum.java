package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gw.das.common.token.TokenResult;
import com.gw.das.common.token.TokenUtil;
import com.gw.das.common.utils.SystemConfigUtil;

public enum CompanyEnum implements EnumIntf {

	gw("集团", "0"),
	fx("外汇", "1"),
	pm("贵金属", "2"),
	hx("恒信", "3"),
	cf("创富", "4")
	;
	
	private final String value;
	private final String labelKey;
	CompanyEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<CompanyEnum> getList(){
		List<CompanyEnum> result = new ArrayList<CompanyEnum>();
		for(CompanyEnum ae : CompanyEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static List<CompanyEnum> getList(String companyIds){
		List<CompanyEnum> result = new ArrayList<CompanyEnum>();
		if(StringUtils.isNotBlank(companyIds)){
			String[] companyIdAry = companyIds.split(",");
			for(String companyIdStr: companyIdAry){
				CompanyEnum ce = CompanyEnum.find(companyIdStr);
				if(null != ce){
					result.add(ce);
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取账户诊断API对应的companyId,即转换
	 * @return
	 */
	public static String getAPICompanyId(String companyId){
		if(CompanyEnum.gw.getLabelKey().equals(companyId)){
			return APICompanyEnum.gw.getLabelKey();
		}else if(CompanyEnum.fx.getLabelKey().equals(companyId)){
			return APICompanyEnum.fx.getLabelKey();
		}else if(CompanyEnum.hx.getLabelKey().equals(companyId)){
			return APICompanyEnum.hx.getLabelKey();
		}else if(CompanyEnum.pm.getLabelKey().equals(companyId)){
			return APICompanyEnum.pm.getLabelKey();
		}else if(CompanyEnum.cf.getLabelKey().equals(companyId)){
			return APICompanyEnum.cf.getLabelKey();
		}
		return companyId;
	}
	
	/**
	 * 获取账户诊断Token
	 * @return
	 */
	public static TokenResult getToken(String companyId){
		TokenResult token = null;
		if(CompanyEnum.gw.getLabelKey().equals(companyId)){
			token = TokenUtil.getToken(SystemConfigUtil.getProperty(SystemConfigEnum.gwAppid), SystemConfigUtil.getProperty(SystemConfigEnum.gwAppScret));
		}else if(CompanyEnum.fx.getLabelKey().equals(companyId)){
			token = TokenUtil.getToken(SystemConfigUtil.getProperty(SystemConfigEnum.fxAppid), SystemConfigUtil.getProperty(SystemConfigEnum.fxAppScret));
		}else if(CompanyEnum.hx.getLabelKey().equals(companyId)){
			token = TokenUtil.getToken(SystemConfigUtil.getProperty(SystemConfigEnum.hxAppid), SystemConfigUtil.getProperty(SystemConfigEnum.hxAppScret));
		}else if(CompanyEnum.pm.getLabelKey().equals(companyId)){
			token = TokenUtil.getToken(SystemConfigUtil.getProperty(SystemConfigEnum.pmAppid), SystemConfigUtil.getProperty(SystemConfigEnum.pmAppScret));
		}else if(CompanyEnum.cf.getLabelKey().equals(companyId)){
			token = TokenUtil.getToken(SystemConfigUtil.getProperty(SystemConfigEnum.cfAppid), SystemConfigUtil.getProperty(SystemConfigEnum.cfAppScret));
		}
		return token;
	}
	
	public static String getAllCompanyIds(){
		String companyIds = "";
		for(CompanyEnum ae : CompanyEnum.values()){
			companyIds += ae.getLabelKey() + ",";
		}
		if(StringUtils.isNotBlank(companyIds)){
			companyIds = companyIds.substring(0, companyIds.length()-1);
		}
		return companyIds;
	}
	
	public static CompanyEnum find(String labelKey){
		for(CompanyEnum ae : CompanyEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae;
			}
		}
		return null;
	}

	public static String format(String labelKey){
		for(CompanyEnum ae : CompanyEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public Long getLabelKeyLong() {
		return Long.parseLong(labelKey);
	}
	
}
