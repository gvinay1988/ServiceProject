package com.servicecity.util;

/**
 * @author Yedukondalu K
 *       
 */

public class AppServiceException extends AppException {


	private static final long serialVersionUID = -185357589451110683L;

	public AppServiceException(String errCode, String errMsg){
		super(errCode, errMsg);
	}

	public AppServiceException(String errCode, String errMsg, Throwable e){
		super(errCode, errMsg, e);
	}

	public AppServiceException(Throwable e){
		super(e);
	}

}
