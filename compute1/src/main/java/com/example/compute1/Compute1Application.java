package com.example.compute1;

import com.example.common.exception.BusinessException;
import com.example.common.exception.ExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@RestController
@Import(ExceptionAdvice.class)
public class Compute1Application {

	@Value("${spring.application.name}")
	String serviceId;

	public static void main(String[] args) {
		SpringApplication.run(Compute1Application.class, args);
	}

	@Autowired
	private DiscoveryClient client;

	@RequestMapping(value = "/add" ,method = RequestMethod.GET)
	public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
		ServiceInstance instance = client.getInstances(serviceId).get(0);
		Integer r = a + b;
		log.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
//		BusinessException exception = new BusinessException("业务异常");
//		throw exception;
		return r;
	}

	@RequestMapping(value = "/divide" ,method = RequestMethod.GET)
	public Integer divide(@RequestParam Integer a, @RequestParam Integer b) {
//		ServiceInstance instance = client.getInstances(serviceId).get(0);
		Integer r = a / b;
//		log.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
		return r;
	}
}
