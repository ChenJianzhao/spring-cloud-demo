package com.example.trace2;

import brave.Span;
import brave.Tracer;
import brave.propagation.ExtraFieldPropagation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@RestController
@Slf4j
public class Trace2Application {

	@Autowired
	Tracer tracer;

	public static void main(String[] args) {
		SpringApplication.run(Trace2Application.class, args);
	}

	@RequestMapping(value = "/trace-2", method = RequestMethod.GET)
	public String trace(HttpServletRequest request) {
//		span.annotate("server receive");
//		span.tag("test", "test");
		log.info("===<call trace-2, TraceId={}, SpanId={}>===",
				request.getHeader("X-B3-TraceId"),
				request.getHeader("X-B3-SpanId"));
		log.info("===<call trace-2, foo={}, UPPER_CASE={}，x-trace-id={}>===",
				request.getHeader("baggage-foo"),
				request.getHeader("baggage-UPPER_CASE"),
				request.getHeader("x-trace-id"));
		log.info("===<call trace-2>===");

		Span span = tracer.currentSpan();
		span.tag("foo", ExtraFieldPropagation.get(span.context(), "foo"));
		span.tag("UPPER_CASE", ExtraFieldPropagation.get(span.context(), "UPPER_CASE"));
		span.tag("x-trace-id", ExtraFieldPropagation.get(span.context(), "x-trace-id"));
		return "Trace";
	}
}
