package com.example.interceprotandaop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class InterceptorAndAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterceptorAndAopApplication.class, args);
	}

	@GetMapping("/aop")
	public void aop() {
		log.info("real web request!");
	}
}
