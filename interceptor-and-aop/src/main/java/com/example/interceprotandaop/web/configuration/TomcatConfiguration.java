package com.example.interceprotandaop.web.configuration;

import com.example.interceprotandaop.web.filter.RequestLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfiguration{

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean<RequestLogFilter> filterRegistration = new FilterRegistrationBean<>();
		RequestLogFilter requestLogFilter = new RequestLogFilter();
		filterRegistration.setFilter(requestLogFilter);
		return filterRegistration;
	}

}
