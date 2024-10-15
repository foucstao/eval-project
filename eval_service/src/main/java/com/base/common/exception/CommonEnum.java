package com.base.common.exception;

public enum CommonEnum implements BaseErrorInfoInterface {

	/** 成功 */
	SUCCESS(200, "成功!"),

	/** 参数错误 */
	BODY_NOT_MATCH(400, "请求的数据格式不符!"),

	/** 未授权的请求 */
	SIGNATURE_NOT_MATCH(401, "请求不合法!"),

	/** 方法找不到 */
	ERR_METHOD(405, "不支持当前请求方法!"),

	/** 500错误 */
	INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),

	/** 服务器无响应 */
	SERVER_BUSY(503, "服务器正忙，请稍后再试!");

	/** 错误码 */
	private int resultCode;

	/** 错误描述 */
	private String resultMsg;

	CommonEnum(int resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	@Override
	public int getResultCode() {
		return resultCode;
	}

	@Override
	public String getResultMsg() {
		return resultMsg;
	}
}
