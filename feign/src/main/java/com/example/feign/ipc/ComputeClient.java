package com.example.feign.ipc;

import feign.Feign;
import feign.Logger;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "compute-service",
		/*fallback = ComputeFallback.class,*/
		configuration = FeignConfiguration.class,
		fallbackFactory = ComputeFallbackFactory.class
		)
public interface ComputeClient {

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	String addService(@RequestParam Integer a, @RequestParam Integer b);

	@RequestMapping(value = "/divide", method = RequestMethod.GET)
	String divideService(@RequestParam Integer a, @RequestParam Integer b);

}

//@Component
class ComputeFallback implements ComputeClient {

	@Override
	public String addService(Integer a, Integer b) {
		return null;
	}

	@Override
	public String divideService(Integer a, Integer b) {
		return "1";
	}
}

@Component
@Slf4j
class ComputeFallbackFactory implements FallbackFactory<ComputeClient> {

	@Override
	public ComputeClient create(Throwable throwable) {
		log.warn(throwable.getMessage());
		return new ComputeClient() {
			@Override
			public String addService(Integer a, Integer b) {
				return null;
			}

			@Override
			public String divideService(Integer a, Integer b) {
				return "2";
			}
		};
	}
}

/**
 * 单个 FeignClient 内引入的配置
 */
class FeignConfiguration {

	/**
	 * 单 FeignClient 范围禁用 hystrix
	 * @return
	 */
	/*@Bean
	@Scope("prototype")
	public Feign.Builder feignBuilder() {
		return Feign.builder();
	}*/

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

}