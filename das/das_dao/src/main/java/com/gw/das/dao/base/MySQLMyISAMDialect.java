package com.gw.das.dao.base;

import org.hibernate.dialect.MySQLDialect;

/**
 * MySQL MyISAM 方言
 * @author wayne
 *
 */
public class MySQLMyISAMDialect extends MySQLDialect {

	public String getTableTypeString() {
		return " ENGINE=MyISAM";
	}

	public boolean dropConstraints() {
		return false;
	}
}
