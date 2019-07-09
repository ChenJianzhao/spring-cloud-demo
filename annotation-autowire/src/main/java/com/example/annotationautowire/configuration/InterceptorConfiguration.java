package com.example.annotationautowire.configuration;

import com.example.annotationautowire.mark.MarkAnnotation;
import com.example.annotationautowire.service.InjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AnnotationConfiguration.class)
@Slf4j
public class InterceptorConfiguration implements SmartInitializingSingleton {

    @MarkAnnotation
    @Autowired(required = false)
    private InjectService[] injectServices;


    @Override
    public void afterSingletonsInstantiated() {
        for (InjectService service : injectServices) {
            log.info(service.getClass().getName());
        }
    }
}
