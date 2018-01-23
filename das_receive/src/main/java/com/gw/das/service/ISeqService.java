package com.gw.das.service;

import com.gw.das.mongodb.IdSeq;

/**
 * 获取主键ID接口
 * @author kirin.guan
 *
 */
public interface ISeqService {
	public String getSeqId(IdSeq seq);
}
