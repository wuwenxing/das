package com.gw.das.dao.custConsultation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;


@Entity
@Table(name = "t_custconsultation")
public class CustConsultationEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836855121805943908L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long consulttationId;
	
	@Column(name = "consulttationTime", length = 100)
	private String consulttationTime;// 标签名称
	
	@Column(name = "newConsulttationNum", length = 500)
	private int newConsulttationNum;// 标签链接
	
	@Column(name = "oldConsulttationNum", length = 500)
	private int oldConsulttationNum;// 标签链接
	
	@Column(name = "reportType", length = 500)
	private String reportType;// 标签链接
	
	public Long getConsulttationId()
	{
		return consulttationId;
	}
	
	public void setConsulttationId(Long consulttationId)
	{
		this.consulttationId = consulttationId;
	}


	public String getConsulttationTime()
	{
		return consulttationTime;
	}

	
	public void setConsulttationTime(String consulttationTime)
	{
		this.consulttationTime = consulttationTime;
	}

	
	public int getNewConsulttationNum()
	{
		return newConsulttationNum;
	}

	
	public void setNewConsulttationNum(int newConsulttationNum)
	{
		this.newConsulttationNum = newConsulttationNum;
	}

	
	public int getOldConsulttationNum()
	{
		return oldConsulttationNum;
	}

	
	public void setOldConsulttationNum(int oldConsulttationNum)
	{
		this.oldConsulttationNum = oldConsulttationNum;
	}

	public String getReportType()
	{
		return reportType;
	}

	
	public void setReportType(String reportType)
	{
		this.reportType = reportType;
	}
	
	
}
