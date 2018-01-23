package com.gw.das.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gw.das.mongodb.IdSeq;
import com.gw.das.mongodb.MongoDBBaseDao;
import com.gw.das.service.ISeqService;

@Service
public class SeqServiceImpl implements ISeqService {
	@Resource(name = "mongoDBBaseDao")
	private MongoDBBaseDao mongoDBBaseDao;

	@Override
	public String getSeqId(IdSeq seq) {
		return mongoDBBaseDao.getNextSeqId(seq);
	}
}
