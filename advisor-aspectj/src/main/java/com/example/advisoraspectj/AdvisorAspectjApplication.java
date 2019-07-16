package com.example.advisoraspectj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
//@EnableAspectJAutoProxy
@Slf4j
public class AdvisorAspectjApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvisorAspectjApplication.class, args);
	}

	@GetMapping("/advisor")
	public String advisor() {
		log.info("advisor method invoke");
		return "advisor";
	}

}
