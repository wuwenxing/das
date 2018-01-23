package com.gw.das.dao.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gw.das.dao.base.BaseEntity;

/**
 * 数据字典
 * @author wayne
 */
@Entity
@Table(name = "t_system_dict")
public class SystemDictEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "dict_id", nullable = false)
	private Long dictId; // 数据字典ID

	@Column(name = "parent_dict_code", length = 25)
	private String parentDictCode; // 父数据字典编码

	@Column(name = "dict_code", length = 25)
	private String dictCode; // 数据字典编码

	@Column(name = "dict_type", length = 25)
	private String dictType; // 数据字典类型(1字典分组,2数据字典)

	@Column(name = "dict_name", length = 50)
	private String dictName; // 数据字典名称

	@Column(name = "order_code")
	private Long orderCode; // 排序号

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getParentDictCode() {
		return parentDictCode;
	}

	public void setParentDictCode(String parentDictCode) {
		this.parentDictCode = parentDictCode;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public Long getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Long orderCode) {
		this.orderCode = orderCode;
	}
}
