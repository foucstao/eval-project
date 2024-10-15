package com.base.common;

import java.util.HashMap;
import java.util.Map;

import com.base.common.exception.BaseErrorInfoInterface;

import lombok.Data;

@Data
public class APIReturnData {

	/** 响应状态码 */
	private int status;

	/** 响应信息 */
	private String message;

	/** 响应结果 */
	private Map<String, Object> data = new HashMap<String, Object>();

	public APIReturnData() {

	}

	public APIReturnData(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public void success() {
		this.status = 200;
		this.message = "success";
	}

	public void error() {
		this.status = 500;
		this.message = "error";
	}

	public static APIReturnData error(int code, String message) {
		APIReturnData rb = new APIReturnData();
		rb.setStatus(code);
		rb.setMessage(message);
		rb.setData(null);
		return rb;
	}

	/**
	 * 失败
	 */
	public static APIReturnData error(BaseErrorInfoInterface errorInfo) {
		APIReturnData rb = new APIReturnData();
		rb.setStatus(errorInfo.getResultCode());
		rb.setMessage(errorInfo.getResultMsg());
		rb.setData(null);
		return rb;
	}

	public void success(String message) {
		this.status = 200;
		this.message = message;
	}

	public void error(String message) {
		this.status = 500;
		this.message = message;
	}

	public void putData(String key, Object value) {
		data.put(key, value);
	}

}
