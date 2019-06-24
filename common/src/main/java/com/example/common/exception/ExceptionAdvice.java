package com.example.common.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ExceptionAdvice {

	/**
	 * 因为业务异常不能触发 Hystrix 的断路器，因此重写了 Decoder 将业务异常包装成 HystrixBadRequestException 抛出
	 * 这里判断 HystrixBadRequestException 包装的是否是业务异常，如果是则取出业务异常原样抛出，其他异常直接抛出
	 */
	@ExceptionHandler(HystrixBadRequestException.class)
	@ResponseStatus
	public BusinessResponse handlerHystrixBadRequestException(HystrixBadRequestException exception) {
		if (exception.getCause() instanceof BusinessException) {
			BusinessException cause = (BusinessException) exception.getCause();
			return new BusinessResponse(cause.getCode(), cause.getMessage());
		} else {
			throw exception;
		}
	}

	/**
	 * 将业务异常包装成对象返回给前端
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus
	public BusinessResponse handlerBusinessException(BusinessException exception) {
		return new BusinessResponse(exception.getCode(), exception.getMessage());
	}


	/**
	 * 处理其他异常
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus
	public BusinessResponse handlerBusinessException(Exception exception, HttpServletResponse response) {
		return new BusinessResponse(500, exception.getMessage());
	}
}
