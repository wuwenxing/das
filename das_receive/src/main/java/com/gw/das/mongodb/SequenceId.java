package com.gw.das.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：自增序列
 * @author Gavin.guo
 * @date   2015-04-07
 */
@Document
public class SequenceId{
	@Id
	private String id;
	private long seq;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
}
