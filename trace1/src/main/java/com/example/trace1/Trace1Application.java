package com.example.trace1;

import brave.Span;
import brave.Tracer;
import brave.propagation.ExtraFieldPropagation;
import com.example.trace1.ipc.Trace2Client;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@RestController
@Slf4j
public class Trace1Application {

	@Autowired
	Tracer tracer;
	@Autowired
	Trace2Client trace2Client;

	public static void main(String[] args) {
		SpringApplication.run(Trace1Application.class, args);
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/trace-1", method = RequestMethod.GET)
	public String trace() {
		log.info(tracer.currentSpan().toString());
		log.info("===call trace-1===");
		return restTemplate().getForEntity("http://trace-2/trace-2", String.class).getBody();
	}

	@RequestMapping(value = "/trace-feign", method = RequestMethod.GET)
	public String traceFeign() {
//		Span initialSpan = this.tracer.nextSpan().name("span").start();
		Span initialSpan = tracer.currentSpan();
		ExtraFieldPropagation.set(initialSpan.context(), "foo", "bar");
		ExtraFieldPropagation.set(initialSpan.context(), "UPPER_CASE", "someValue");
		ExtraFieldPropagation.set(initialSpan.context(), "x-trace-id",
				String.valueOf(tracer.currentSpan().context().traceId()));

		initialSpan.tag("foo", ExtraFieldPropagation.get(initialSpan.context(), "foo"));
		initialSpan.tag("UPPER_CASE", ExtraFieldPropagation.get(initialSpan.context(), "UPPER_CASE"));

		return trace2Client.trace2();
	}
}
