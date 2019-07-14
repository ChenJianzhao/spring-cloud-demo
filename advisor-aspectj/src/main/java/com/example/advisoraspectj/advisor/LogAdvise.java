package com.example.advisoraspectj.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class LogAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.info("log before method invoke");
		Object retVal = invocation.proceed();
		log.info("log before method invoke");
		return retVal;
	}
}

