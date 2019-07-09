package com.example.interceprotandaop.web.configuration;

import com.example.interceprotandaop.web.interceptor.WebLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebLogInterceptor interceptor = new WebLogInterceptor();
		registry.addInterceptor(interceptor);
	}

}
