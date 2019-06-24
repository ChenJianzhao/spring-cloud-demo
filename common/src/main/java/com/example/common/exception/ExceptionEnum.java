package com.example.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ExceptionEnum {

	BUSINESS_EXCEPTION(525, "业务异常");

	private Integer code;
	private String msg;
}
