package com.base.common.exception;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	protected int errorCode;
	/**
	 * 错误信息
	 */
	protected String errorMsg;

	public BaseException() {
		super();
	}

	public BaseException(BaseErrorInfoInterface errorInfoInterface) {
		super(errorInfoInterface.getResultCode() + "");
		this.errorCode = errorInfoInterface.getResultCode();
		this.errorMsg = errorInfoInterface.getResultMsg();
	}

	public BaseException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
		super(errorInfoInterface.getResultCode() + "", cause);
		this.errorCode = errorInfoInterface.getResultCode();
		this.errorMsg = errorInfoInterface.getResultMsg();
	}

	public BaseException(String errorMsg) {
		super(errorMsg);
		this.errorCode=500;
		this.errorMsg = errorMsg;
	}

	public BaseException(int errorCode, String errorMsg) {
		super(errorCode + "");
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public BaseException(int errorCode, String errorMsg, Throwable cause) {
		super(errorCode + "", cause);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
