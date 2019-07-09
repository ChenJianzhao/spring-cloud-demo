package com.example.annotationautowire.configuration;

import com.example.annotationautowire.mark.MarkAnnotation;
import com.example.annotationautowire.service.BarService;
import com.example.annotationautowire.service.FooService;
import com.example.annotationautowire.service.InjectService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationConfiguration {

    @Bean
    public InjectService barService() {
        return new BarService();
    }

    @MarkAnnotation
    @Bean
    public InjectService fooService() {
        return new FooService();
    }
}
