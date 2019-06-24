package com.example.trace1.ipc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("trace-2")
public interface Trace2Client {

	@GetMapping("/trace-2")
	String trace2();
}
