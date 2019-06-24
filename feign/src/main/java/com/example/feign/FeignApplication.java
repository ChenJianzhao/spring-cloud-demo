package com.example.feign;

import com.example.common.exception.ExceptionAdvice;
import com.example.feign.ipc.ComputeClient;
import com.example.feign.support.ServiceContext;
import com.example.feign.support.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import(ExceptionAdvice.class)
@Slf4j
public class FeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignApplication.class, args);
	}

	@RestController
	public class ConsumerController {

		@Autowired
		private ComputeClient computeClient;

		@RequestMapping(value = "/add", method = RequestMethod.GET)
		public String add() {
			// 使用上下文存储租户信息
			ServiceContext.setTenant(new Tenant(1L));
			log.info("set tenant = {}", ServiceContext.getTenant());
			ServiceContext.THREAD_LOCAL_TENANT.set(2L);
			return computeClient.addService(10, 20);
		}

		@RequestMapping(value = "/divide", method = RequestMethod.GET)
		public String divide() {
			return computeClient.divideService(10, 0);
		}

	}
}
