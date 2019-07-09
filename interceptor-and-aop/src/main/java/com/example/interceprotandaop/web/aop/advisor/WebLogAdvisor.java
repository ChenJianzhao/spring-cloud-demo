package com.example.interceprotandaop.web.aop.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.stereotype.Component;

@Component
public class WebLogAdvisor implements PointcutAdvisor {
	@Override
	public Pointcut getPointcut() {
		return null;
	}

	@Override
	public Advice getAdvice() {
		return null;
	}

	@Override
	public boolean isPerInstance() {
		return true;
	}


	class WebLogAdvice implements Advice{

	}
}
