package com.example.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 业务异常定义
 * 全局处理业务异常
 * 业务异常不触发 Hystrix 断路，包装为 HystrixBadRequestException，再从其中解析出业务异常
 */
@SpringBootApplication
public class CommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonApplication.class, args);
	}

}
