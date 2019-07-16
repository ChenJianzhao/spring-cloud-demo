package com.example.advisoraspectj.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Component
public class CustomAdvisor extends DefaultPointcutAdvisor {

	CustomAdvisor() {
		setAdvice(createAdvice());
		setPointcut(createPointCut());
	}

	private Pointcut createPointCut() {
		return new AnnotationMatchingPointcut(RestController.class, GetMapping.class, true);
	}

	private Advice createAdvice() {
		return new LogAdvice();
	}

	public class LogAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("Advisor: log before method invoke");
			Object retVal = invocation.proceed();
			log.info("Advisor: log after method invoke");
			return retVal;
		}
	}
}
