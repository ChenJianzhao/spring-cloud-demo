package com.example.advisoraspectj.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CustomAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && (" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void pointCut() { }


    @Before("pointCut()")
    public void before() {
        log.info("Aspect: log before method invoke");
    }

    @After("pointCut()")
    public void after() {
        log.info("Aspect: log after method invoke");
    }
}
