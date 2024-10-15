package com.base.common.exception;

public interface BaseErrorInfoInterface {

	/**
	 * 	错误码
	 * @return
	 */
	int getResultCode();

	/**
	 * 	错误描述 
	 * @return
	 */
	String getResultMsg();
}
