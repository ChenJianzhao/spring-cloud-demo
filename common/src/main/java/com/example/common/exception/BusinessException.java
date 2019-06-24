package com.example.common.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

	private Integer code;
	private String message;
	public BusinessException() {
	}
	public BusinessException(String errmsg) {
		super(errmsg);
		this.code=525;
		this.message=errmsg;
	}
	public BusinessException(ExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg());
		this.code=exceptionEnum.getCode();
		this.message=exceptionEnum.getMsg();
	}

	public BusinessException(int code ,String errmsg) {
		super(errmsg);
		this.code=code;
		this.message=errmsg;
	}


	@Override
	public String toString() {
		return "{" +
				"\"code\":" + code +
				", \"message\":\"" + message + "\"" +"}";
	}
}