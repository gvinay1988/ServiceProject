package com.servicecity.util;

/**
 * @author Yedukondalu K
 *        
 */

public class AppException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorCode;

	public AppException(String errCode, String errMsg){
		super(errMsg);
		this.errorCode = errCode;
	}

	public AppException(String errCode, String errMsg, Throwable e){
		super(errMsg, e);
		this.errorCode = errCode;
	}

	public AppException(Throwable e){
		super(e);
	}

	public String getErrorCode() {
		return errorCode;
	}
}
