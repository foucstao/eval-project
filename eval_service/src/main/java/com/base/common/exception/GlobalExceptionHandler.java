package com.base.common.exception;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.base.common.APIReturnData;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 处理自定义的业务异常
	 */
	@ExceptionHandler(value = BaseException.class)
	public APIReturnData bizExceptionHandler(BaseException e) {
		logger.error("发生业务异常！原因是：{}", e.getErrorMsg());
		return APIReturnData.error(e.getErrorCode(), e.getErrorMsg());
	}

	/**
	 * 参数空指针异常
	 */
	@ExceptionHandler(value = NullPointerException.class)
	public APIReturnData exceptionHandler(NullPointerException e) {
		logger.error("发生空指针异常:{}", e.getMessage());
		e.printStackTrace();
		return APIReturnData.error(CommonEnum.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 自定义验证异常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public APIReturnData handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		String message = e.getBindingResult().getFieldError().getDefaultMessage();
		logger.error("自定义验证异常," + message);
		e.printStackTrace();
		return APIReturnData.error(-1, "自定义验证异常:" + message);
	}

//	@ExceptionHandler(ConstraintViolationException.class)
//	public APIReturnData constraintViolationException(ConstraintViolationException e) {
//		String message = e.getMessage();
//		logger.error("参数验证失败," + message);
//		return APIReturnData.error(500, "参数验证失败:" + message);
//	}

	/**
	 * 自定义验证异常
	 */
	@ExceptionHandler(BindException.class)
	public APIReturnData handleBindException(BindException e) {
		logger.error("自定义验证异常:{}", e.getMessage());
		String message = e.getAllErrors().get(0).getDefaultMessage();
		e.printStackTrace();
		return APIReturnData.error(500, "异常原因是:" + message);
	}

	/**
	 * 下载文件异常
	 */
	@ExceptionHandler(UnsupportedEncodingException.class)
	public APIReturnData unSupportedEncodingException(UnsupportedEncodingException e) {
		logger.error("下载文件转码异常:{}", e.getMessage());
		return APIReturnData.error(-1, "下载文件转码异常");
	}

	/**
	 * 上传文件大小异常
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public APIReturnData maxUploadException(MaxUploadSizeExceededException e) {
		logger.error("上传文件大小超出限制" + e.getMessage() + ",最大文件大小" + e.getMaxUploadSize());
		return APIReturnData.error(-1, "上传文件大小超出限制");
	}

	/**
	 * 文件异常
	 */
	@ExceptionHandler(IOException.class)
	public APIReturnData fileException(IOException e) {
		logger.error("文件处理异常:{}", e.getMessage());
		e.printStackTrace();
		return APIReturnData.error(-1, "文件处理异常");
	}

	/**
	 * 请求方法不存在异常
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public APIReturnData handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.error("不支持当前请求方法:{}", e.getMessage());
		return APIReturnData.error(CommonEnum.ERR_METHOD);
	}

	@ExceptionHandler(SQLException.class)
	public APIReturnData handleSQLException(SQLException e) {
		e.printStackTrace();
		logger.error("操作数据库出现异常:{}", e.getLocalizedMessage());
		return APIReturnData.error(500, "操作数据库异常");
	}

//	@ExceptionHandler(Exception.class)
//	public APIReturnData handleException(Exception e) {
//		logger.error(e.getMessage(), e);
//		return APIReturnData.error(500, e.getMessage());
//	}

}
