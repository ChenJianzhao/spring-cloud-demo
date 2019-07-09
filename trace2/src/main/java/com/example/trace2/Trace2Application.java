package com.example.trace2;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
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
	@Autowired
	Tracing tracing;

	public static void main(String[] args) {
		SpringApplication.run(Trace2Application.class, args);
	}

	@RequestMapping(value = "/trace-2", method = RequestMethod.GET)
	public String trace(HttpServletRequest request) throws InterruptedException {
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
		span.annotate("server receive");
		span.tag("foo", ExtraFieldPropagation.get(span.context(), "foo"));
		span.tag("UPPER_CASE", ExtraFieldPropagation.get(span.context(), "UPPER_CASE"));
		span.tag("x-trace-id", ExtraFieldPropagation.get(span.context(), "x-trace-id"));

		// 新范围
		Span newSpan = tracer.nextSpan();
		try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan)) {
			log.info("log with new span {}", newSpan.toString());
		} finally { // note the scope is independent of the span
			newSpan.finish();
		}

		log.info("return to span {}", span.toString());

		tracer.currentSpanCustomizer()
				.annotate("currentSpanCustomizer.annotation")
				.tag("currentSpanCustomizer.tag", "currentSpanCustomizer");

		doSomethingWithSpan();

		return "Trace";
	}

	private void doSomethingWithSpan() throws InterruptedException {
//		tracer.currentSpan().annotate("doSomethingWithSpan");
		Span span1 = tracer.newChild(tracer.currentSpan().context()).annotate("new child1").name("child1");
		Thread.sleep(100);
		span1.finish();

		Span span2 = tracer.newChild(tracer.currentSpan().context()).annotate("new child2").name("child2");
		Thread.sleep(100);
		span2.finish();
	}
}
