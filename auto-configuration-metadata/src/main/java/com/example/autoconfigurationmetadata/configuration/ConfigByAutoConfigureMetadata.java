package com.example.autoconfigurationmetadata.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigByAutoConfigureMetadata {

	@Bean
	public TestBean testBean() {
		return new TestBean();
	}

	@Slf4j
	static class TestBean {
		TestBean() {
			log.info("auto configure by spring-autoconfigure-metadata.properties");
		}
	}
}


