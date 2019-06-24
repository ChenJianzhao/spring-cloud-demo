package com.example.ribbon;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class RibbonApplication {

	@Autowired
	RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RibbonApplication.class, args);
	}

	@RestController
	public class ConsumerController {

		@Autowired
		private ComputeService computeService;

		@RequestMapping(value = "/add", method = RequestMethod.GET)
		public String add() {
			return computeService.addService();
		}

	}

	@Service
	public class ComputeService {

//		@HystrixCommand(fallbackMethod = "addServiceFallback")
		public String addService() {
			return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
		}
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
//		return new RestTemplate();
		SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(1000);	// 连接超时
		httpRequestFactory.setReadTimeout(1000);	// 请求超时
		return new RestTemplate(httpRequestFactory);
	}
}
