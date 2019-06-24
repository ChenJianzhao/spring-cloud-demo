package com.example.feign.decoder;

import com.example.common.exception.BusinessException;
import com.example.common.exception.BusinessResponse;
import com.example.feign.support.ServiceContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;

/**
 * 全局业务异常转 HystrixBadRequestException ErrorDecoder
 */
@Configuration
public class FeignConfiguration {

	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignErrorDecoder();
	}

	@Slf4j
	static class FeignErrorDecoder implements ErrorDecoder {

		ObjectMapper objectMapper = new ObjectMapper();

		@Override
		public Exception decode(String s, Response response) {

			try {
				if (response.body() != null) {
					String body = Util.toString(response.body().asReader());
					log.error(body);
					BusinessResponse businessResponse = this.objectMapper.readValue(body.getBytes("UTF-8"),
							BusinessResponse.class);

					if (businessResponse.getCode() == 525) {
						BusinessException exception =
								new BusinessException(businessResponse.getCode(), businessResponse.getMessage());
						return new HystrixBadRequestException(exception.getMessage(), exception);
					} else {
						return new RuntimeException(businessResponse.getMessage());
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				return new RuntimeException("内部错误");
			}
			return new RuntimeException("内部错误");
		}
	}

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new TenantRequestInterceptor();
	}

	/**
	 * OpenFeign 提供的请求拦截器扩展
	 */
	@Slf4j
	static class TenantRequestInterceptor implements RequestInterceptor {

		@Override
		public void apply(RequestTemplate template) {

			// 获取跨线程传递的变量
			log.info("get tenant = {}", ServiceContext.getTenant());
//			log.info("ThreadLocal Tenant = {}", ServiceContext.THREAD_LOCAL_TENANT.get());
			// 设置到 RequestHeader 中
			template.header(ServiceContext.TENANT_HEADER, String.valueOf(ServiceContext.getTenant().getTenantId()));

			Map<String, Collection<String>> headers = template.headers();
			headers.forEach((header, values) -> {
				log.info("request header {} -> {}", header, values.toString());
			});
		}
	}
}
