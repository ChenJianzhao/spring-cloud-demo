package com.example.advisoraspectj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class AdvisorAspectjApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvisorAspectjApplication.class, args);
	}

	@GetMapping("/advisor")
	public String advisor() {
		return "advisor";
	}

}
