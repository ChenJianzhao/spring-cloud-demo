package com.example.interceprotandaop.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//直接将Filter声明为组件，Spirng容器会自动注册到Web容器中
//@Component
public class RequestLogFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		log.info("RequestLogFilter before {}", request.getServletPath());
		filterChain.doFilter(request, response);
		log.info("RequestLogFilter after {}", request.getServletPath());
	}
}
