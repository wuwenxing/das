package com.gw.das.dao.website.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 首页图表数据模型
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomePageDataModel {
	
	private String year;//年
    private String month;//月
    private String day;//日
    private String hours;// 小时（0-23）
	private Double visitCount = new Double("0.0");//访问总数
	private Double advisoryCount = new Double("0.0");//咨询次数
	private Double advisoryCountQQ = new Double("0.0");//咨询次数 qq
	private Double advisoryCountLIVE800 = new Double("0.0");//咨询次数 live800
	private Double demoCount = new Double("0.0");//模拟开户总数
	private Double realCount = new Double("0.0");//真实开户总数
    private Double depositCount = new Double("0.0");//首次入金数
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof HomePageDataModel){
    		HomePageDataModel model = (HomePageDataModel) obj;
    		if(model.getHours().equals(this.hours)){
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
	public String toString() {
    	if(year==null){
    		year = "";
    	}
    	if(month==null){
    		month = "";
    	}
    	if(day==null){
    		day = "";
    	}
    	if(hours==null){
    		hours = "";
    	}
		return year+month+day+hours;
	}
    
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}

	public Double getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Double visitCount) {
		this.visitCount = visitCount;
	}

	public Double getAdvisoryCount() {
		return advisoryCount;
	}

	public void setAdvisoryCount(Double advisoryCount) {
		this.advisoryCount = advisoryCount;
	}

	public Double getDemoCount() {
		return demoCount;
	}

	public void setDemoCount(Double demoCount) {
		this.demoCount = demoCount;
	}

	public Double getRealCount() {
		return realCount;
	}

	public void setRealCount(Double realCount) {
		this.realCount = realCount;
	}

	public Double getDepositCount() {
		return depositCount;
	}

	public void setDepositCount(Double depositCount) {
		this.depositCount = depositCount;
	}

	public Double getAdvisoryCountQQ() {
		return advisoryCountQQ;
	}

	public void setAdvisoryCountQQ(Double advisoryCountQQ) {
		this.advisoryCountQQ = advisoryCountQQ;
	}

	public Double getAdvisoryCountLIVE800() {
		return advisoryCountLIVE800;
	}

	public void setAdvisoryCountLIVE800(Double advisoryCountLIVE800) {
		this.advisoryCountLIVE800 = advisoryCountLIVE800;
	}
	
}
