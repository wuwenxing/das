package com.gw.das.business.common.exception;

/**
 * 
 * 类功能:数据访问异常
 * <p>
 * 创建者:关博
 * </p>
 * 
 * <p>
 * 创建时间:2014-04-08
 * </p>
 * 
 * <p>
 * 修改者:
 * </p>
 * 
 * <p>
 * 修改时间:
 * </p>
 * 
 * <p>
 * 修改原因：
 * </p>
 * 
 * <p>
 * 审核者:
 * </p>
 * 
 * <p>
 * 审核时间:
 * </p>
 * 
 * <p>
 * 审核意见：
 * </p>
 * 
 */
public class DaoException extends BaseException {

	private static final long serialVersionUID = 3683410505240807189L;

	public DaoException(String errCode, String desc) {
		super(errCode, desc);
	}

	public DaoException(String errCode) {
		super(errCode, "");
	}

	public DaoException(String msg, Throwable t) {
		super(msg, t);
	}
}
