package com.example.interceprotandaop.web.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class WebLogAspect {

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
			"|| @annotation(org.springframework.web.bind.annotation.GetMapping)" +
			"|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
			"|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
			"|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	public void pointCut() { }

	@Before("pointCut()")
	public void doBefore(JoinPoint joinPoint) {
		log.info("WebLogAspect @Before {}", joinPoint.getSignature());
	}

	@After("pointCut()")
	public void doAfter(JoinPoint joinPoint) {
		log.info("WebLogAspect @After {}", joinPoint.getSignature());
	}

	@AfterReturning("pointCut()")
	public void doAfterReturning(JoinPoint joinPoint) {
		log.info("WebLogAspect @AfterReturning from {}", joinPoint.getSignature());
	}

	@Around("pointCut()")
	public void doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		log.info("WebLogAspect @Around before {}", proceedingJoinPoint.getSignature());
		proceedingJoinPoint.proceed();
		log.info("WebLogAspect @Around after {}", proceedingJoinPoint.getSignature());
	}
}
